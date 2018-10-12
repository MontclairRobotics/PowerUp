package frc.team555.robot.core;

import Robot.NavRobot;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team555.robot.auto.MoveLift;
import frc.team555.robot.auto.SwitchAuto;
import frc.team555.robot.components.IntakeLift;
import frc.team555.robot.components.MainLift;
import frc.team555.robot.driverAssistance.AutoClimbSequence;
import frc.team555.robot.driverAssistance.VaultAlignmentStep;
import frc.team555.robot.utils.BangBang;
import frc.team555.robot.utils.CoastMotor;
import frc.team555.robot.visionAssistance.VisionGuidedCubeIntake;
import frc.team555.robot.visionAssistance.VisionTrackingStep;
import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.auto.AutoMode;
import org.montclairrobotics.sprocket.auto.states.*;
import org.montclairrobotics.sprocket.control.ButtonAction;
import org.montclairrobotics.sprocket.control.DashboardInput;
import org.montclairrobotics.sprocket.drive.*;
import org.montclairrobotics.sprocket.drive.steps.Deadzone;
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.drive.steps.Sensitivity;
import org.montclairrobotics.sprocket.drive.utils.GyroLock;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.motors.Module;
import org.montclairrobotics.sprocket.pipeline.Step;
import org.montclairrobotics.sprocket.states.StateMachine;
import org.montclairrobotics.sprocket.utils.PID;

import java.io.IOException;
import java.util.ArrayList;

public class PowerUpRobot extends SprocketRobot {
    DriveTrain driveTrain;
    public static GyroCorrection correction;
    Sensitivity sensitivity;
    GyroLock lock;
    boolean manualLock;
    VisionGuidedCubeIntake intake;
    MainLift mainLift;
    IntakeLift intakeLift;
    StateMachine autoClimb;
    NavRobot navigation;

    private static final double oldOverNew=17.1859 * 1.25/(6544.0/143.0);

    @Override
    public void robotInit(){
        CameraServer.getInstance().startAutomaticCapture();

        DriveEncoders.TOLLERANCE = 6; /*45.5363/17.1859*/
        TurnGyro.TURN_SPEED=0.3;
        TurnGyro.tolerance=new Degrees(3);
        //40 ft 5.5 in
        Hardware.init();
        Control.init();
        SwitchAuto.init();
        DriveModule[] modules = new DriveModule[2];
        intake = new VisionGuidedCubeIntake();
        mainLift=new MainLift();
        intakeLift=new IntakeLift();
        correction = new GyroCorrection(Hardware.navx, new PID(1.5, 0, 0.0015), 90, 1);
        autoClimb = new AutoClimbSequence(mainLift);

        modules[0] = new DriveModule(new XY(-1, 0),
                new XY(0, 1),
                Hardware.leftDriveEncoder,
                new PID(0.5,0,0),
                Module.MotorInputType.PERCENT,
                new CoastMotor(Hardware.motorDriveBL,false),
                new CoastMotor(Hardware.motorDriveFL,false));

        modules[1] = new DriveModule(new XY(1, 0),
                new XY(0, 1),
                Hardware.rightDriveEncoder,
                new PID(0.5,0,0),
                Module.MotorInputType.PERCENT,
                new CoastMotor(Hardware.motorDriveBR,false),
                new CoastMotor(Hardware.motorDriveFR,false));



        DriveTrainBuilder dtBuilder = new DriveTrainBuilder();
        dtBuilder.addDriveModule(modules[0]).addDriveModule(modules[1]);

        /* Build Drive Train */

        dtBuilder.setInput(Control.driveInput);
        dtBuilder.setDriveTrainType(DriveTrainType.TANK);
        try {
            this.driveTrain = dtBuilder.build();
        } catch (InvalidDriveTrainException e) {
            e.printStackTrace();
        }

        /* Drive Train Configurations: Tank, Control */

        driveTrain.setMapper(new TankMapper());
        driveTrain.setDefaultInput(Control.driveInput);

        /* Drive Train Pipeline: GyroCorrection, Deadzone */

        new DashboardInput("auto Selection");

        ArrayList<Step<DTTarget>> steps = new ArrayList<>();
        sensitivity=new Sensitivity(1,0.6);
        lock = new GyroLock(correction);
        steps.add(new Deadzone());
        steps.add(sensitivity);
        steps.add(correction);

        // Vision Angle Correction
        BangBang visionAngleCorrection = new BangBang(0.5, 10);
        visionAngleCorrection.setInput(new DashboardInput("Cube X"));
        visionAngleCorrection.setTarget(170);

        // Vision Dist Correction
        BangBang visionDistanceCorrection = new BangBang(10, 0.25);
        visionDistanceCorrection.setInput(new DashboardInput("Cube Y"));
        visionDistanceCorrection.setTarget(225);

        steps.add(new VisionTrackingStep(visionDistanceCorrection, visionAngleCorrection));

        // Navigation System
        navigation = new NavRobot(new AHRS(SPI.Port.kMXP),
                Hardware.leftDriveEncoder.getWPIEncoder(),
                Hardware.rightDriveEncoder.getWPIEncoder(),
                Hardware.ticksPerInch);

        //TODO: make it changeable
        navigation.resetMiddle();
        navigation.startServer();

        steps.add(new VaultAlignmentStep(
                navigation,
                Control.vaultAlign,
                new PID(0,0,0), //TODO: TUNE DIST PID
                new PID(0,0,0), //TODO: TUNE ANGLE PID
                new XY(0,0),
                new Degrees(90)));

        driveTrain.setPipeline(new DTPipeline(steps));

        /* Enabling and Disabling GyroLock */

        Control.lock.setPressAction(new ButtonAction() {
            @Override
            public void onAction() {
                manualLock = true;
            }
        });

        Control.lock.setReleaseAction(new ButtonAction() {
            @Override
            public void onAction() {
                manualLock = false;
            }
        });

        //auto
        final double driveSpeed = 0.4;
        final int maxEncAccel = 10;
        final int maxTicksPerSec = 10;

        AutoMode autoDrive = new AutoMode("auto Drive",
                new DriveEncoderGyro(120,
                        0.25,
                        Angle.ZERO,
                        true,
                        correction));


        AutoMode encoder = new AutoMode("encoder",
                new DriveEncoders(100,.25));

        AutoMode baseLine = new AutoMode("Base Line",
                new ResetGyro(correction),
                new DriveEncoderGyro(140, .5 , new Degrees(0), false, correction));
        AutoMode centerBaseLineRight = new AutoMode("Center Base Line Right",
                new ResetGyro(correction),
                new DriveEncoderGyro(60, .5, new Degrees(0), false, correction),
                new DriveEncoderGyro(12, .5, new Degrees(90), false, correction),
                new DriveEncoderGyro(80, .5, new Degrees(0), false, correction));
        AutoMode centerBaseLineLeft = new AutoMode("Center Base Line Left",
                new ResetGyro(correction),
                new DriveEncoderGyro(60, .5, new Degrees(0), false, correction),
                new DriveEncoderGyro(122, .5, new Degrees(-90), false, correction),
                new DriveEncoderGyro(80, .5, new Degrees(0), false, correction));


        AutoMode twentyFeet=new AutoMode("Twenty Feet",
                new ResetGyro(correction),
                new DriveEncoderGyro(12*20,.5,new Degrees(0),false,correction));
        addAutoMode(twentyFeet);

        AutoMode tenFeet=new AutoMode("ten Feet",
                new ResetGyro(correction),
                new DriveEncoderGyro(12*10,.5,new Degrees(0),false,correction));
        addAutoMode(tenFeet);

        AutoMode thirtyFeet=new AutoMode("thirty Feet",
                new ResetGyro(correction),
                new DriveEncoderGyro(12*30,.5,new Degrees(0),false,correction));
        addAutoMode(thirtyFeet);
        AutoMode farAuto = new AutoMode("Rich Mode",
                new ResetGyro(correction),
                new DriveEncoderGyro(12*4*oldOverNew, .5,new Degrees(0),false,correction),
                new DriveEncoderGyro((12*20+4)*oldOverNew,.5,new Degrees(90),false,correction),
                new DriveEncoderGyro((12*4)*oldOverNew, .5,new Degrees(180),false,correction),
                new Delay(5),
                new DriveEncoderGyro((-12*4)*oldOverNew, .5,new Degrees(180),false,correction),
                new DriveEncoderGyro((-12*20-4)*oldOverNew,.5,new Degrees(90),false,correction),
                new DriveEncoderGyro((-12*4)*oldOverNew, .5,new Degrees(0),false,correction));

        AutoMode backTen = new AutoMode("Back Ten",
                new ResetGyro(correction),
                new DriveEncoderGyro(-12*10,.5,new Degrees(0),false,correction));


        AutoMode mainLiftUp= new AutoMode("Main Lift Up",new MoveLift(mainLift,MainLift.TOP*0.5,1,true));
        AutoMode turnQuarter=new AutoMode("Turn Quarter",new TurnGyro(Angle.QUARTER,correction,true));

        // addAutoMode(new AutoClimbSequence(mainLift));
        addAutoMode(autoDrive);
        addAutoMode(baseLine);
        addAutoMode(centerBaseLineLeft);
        addAutoMode(centerBaseLineRight);
        /*Control.intakeSubroutine.setHeldAction(new ButtonAction() {
            @Override
            public void onAction() {
                shootCube.start();
            }
        });


        Control.intakeSubroutine.setHeldAction(new ButtonAction() {
            @Override
            public void onAction() {
                shootCube.stateUpdate();
            }
        });

        Control.intakeSubroutine.setReleaseAction(new ButtonAction() {
            @Override
            public void onAction() {
                shootCube.stop();
                intake.disable();
                intake.roationalMotor.set(intake.downPos);
            }
        });
*/

        Control.autoClimb.setPressAction(new ButtonAction() {
            @Override
            public void onAction() {
                autoClimb.start();
            }
        });

        Control.autoClimb.setHeldAction(new ButtonAction() {
            @Override
            public void onAction() {
                //autoClimb.stateUpdate();
            }
        });

        Control.autoClimb.setReleaseAction(new ButtonAction() {
            @Override
            public void onAction() {
                autoClimb.stop();
            }
        });

        intakeLift.setPower(0);
    }

    @Override
    public void userAutonomousSetup(){

    }


    @Override
    public void update(){

        // nav sys
        try {
            navigation.updatePosition();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SmartDashboard.putNumber("Distance", driveTrain.getDistance().getY());
        SmartDashboard.putNumber("Left Encoder", Hardware.leftDriveEncoder.getInches().get());
        SmartDashboard.putNumber("Right Encoder", Hardware.rightDriveEncoder.getInches().get());
        SmartDashboard.putBoolean("Lift Limit Switch", Hardware.liftLimitSwitch.get());
        debugCurrent("Main Lift Front",Hardware.motorLiftMainFront);
        debugCurrent("Main Lift Back",Hardware.motorLiftMainBack);
        debugCurrent("Intake Lift",Hardware.motorLiftIntake);
        SmartDashboard.putNumber("Main Lift Encoder Value",Hardware.liftEncoder.getInches().get());
        //SmartDashboard.putNumber("Intake Lift Encoder",in.getInches().get());
        gyroLocking();
        SwitchAuto.loop();
    }

    private void debugCurrent(String name,WPI_TalonSRX motor) {
        SmartDashboard.putNumber(name + " Current", motor.getOutputCurrent());
    }

    private void gyroLocking(){
        boolean autoLock = ((Math.abs(Control.driveInput.getTurn().toDegrees())<10) &&
                (Math.abs(Control.driveInput.getDir().getY())>0.5));
        if(autoLock || manualLock){
            lock.enable();
        }else{
            lock.disable();
        }
        //lock.update();
        if(Hardware.liftEncoder.getInches().get()>MainLift.TOP*0.5&&!Control.driveStick.getRawButton(3)||Control.driveStick.getRawButton(2))
        {
            sensitivity.set(0.4,0.3);
        }
        else
        {
            sensitivity.set(1,0.6);
        }
    }

    @Override
    public void userDisabledPeriodic(){
        SwitchAuto.disabled();
    }
}
