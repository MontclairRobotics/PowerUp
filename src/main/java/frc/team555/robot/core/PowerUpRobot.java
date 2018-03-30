package frc.team555.robot.core;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team555.robot.stateMachines.AutoClimbSequence;
import frc.team555.robot.stateMachines.SwitchAuto;
import frc.team555.robot.stateMachines.automodes.*;
import frc.team555.robot.components.CubeIntake;
import frc.team555.robot.components.IntakeLift;
import frc.team555.robot.components.MainLift;
import frc.team555.robot.utils.CoastMotor;
import frc.team555.robot.utils.NavXInput;
import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.auto.states.*;
import org.montclairrobotics.sprocket.control.*;
import org.montclairrobotics.sprocket.drive.*;
import org.montclairrobotics.sprocket.drive.steps.Deadzone;
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.drive.steps.Sensitivity;
import org.montclairrobotics.sprocket.drive.utils.GyroLock;
import org.montclairrobotics.sprocket.geometry.*;
import org.montclairrobotics.sprocket.motors.Module;
import org.montclairrobotics.sprocket.pipeline.Step;
import org.montclairrobotics.sprocket.states.StateMachine;
import org.montclairrobotics.sprocket.utils.PID;

import java.util.ArrayList;

public class PowerUpRobot extends SprocketRobot {
    DriveTrain driveTrain;
    public static GyroCorrection correction;
    Sensitivity sensitivity;
    GyroLock lock;
    boolean manualLock;
    public static CubeIntake intake;
    public static MainLift mainLift;
    public static IntakeLift intakeLift;
    StateMachine autoClimb;

    @Override
    public void robotInit(){
        CameraServer.getInstance().startAutomaticCapture();
        DriveEncoders.TOLLERANCE=/*45.5363/17.1859*/6;
        TurnGyro.TURN_SPEED=0.3;
        TurnGyro.tolerance=new Degrees(3);
        //40 ft 5.5 in
        Hardware.init();
        Control.init();
        SwitchAuto.init();
        DriveModule[] modules = new DriveModule[2];
        intake = new CubeIntake();
        mainLift=new MainLift();
        intakeLift=new IntakeLift();
        correction = new GyroCorrection(Hardware.navx, new PID(1.5, 0, 0.0015), 90, 1);
        autoClimb = new AutoClimbSequence(mainLift);
        //SetIntakeLift.setLift(intakeLift);

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


        new DashboardInput("Auto Selection");

        ArrayList<Step<DTTarget>> steps = new ArrayList<>();
        sensitivity=new Sensitivity(1,0.6);
        lock = new GyroLock(correction);
        steps.add(new Deadzone());
        steps.add(sensitivity);
        steps.add(correction);
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
        //this.intake =
        //new CubeIntake();

        //Togglable fieldInput = new FieldCentricDriveInput(Control.driveStick,correction);
        //new ToggleButton(Control.driveStick,Control.FieldCentricID,fieldInput);

        //auto
        addAutoMode(new AutoDrive());
        addAutoMode(new BaseLineLR());
        addAutoMode(new CenterBaseLineLeft());
        addAutoMode(new CenterBaseLineRight());
        addAutoMode(new Square());
        addAutoMode(new SwitchUsingIntake());
        addAutoMode(new SwitchUsingLift());
        sendAutoModes();

        Control.autoClimb.setPressAction(new ButtonAction() {
            @Override
            public void onAction() {
                autoClimb.start();
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

        SmartDashboard.putNumber("Distance", driveTrain.getDistance().getY());
        SmartDashboard.putNumber("Left Encoder", Hardware.leftDriveEncoder.getInches().get());
        SmartDashboard.putNumber("Right Encoder", Hardware.rightDriveEncoder.getInches().get());
        SmartDashboard.putBoolean("Lift Limit Switch", Hardware.liftLimitSwitch.get());
        debugCurrent("Main Lift Front",Hardware.motorLiftMainFront);
        debugCurrent("Main Lift Back",Hardware.motorLiftMainBack);
        debugCurrent("Intake Lift",Hardware.motorLiftIntake);
        SmartDashboard.putNumber("Main Lift Encoder Value",Hardware.liftEncoder.getInches().get());
        SmartDashboard.putNumber("Pitch", Hardware.navx.getPitch());
        SmartDashboard.putBoolean("Pitch Correction", pitchCorrection(Hardware.navx,60));
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

    //TODO: NEEDS Robot TESTING
    private boolean pitchCorrection(NavXInput navXInput, double threshold){
        if(navXInput.getPitch() > threshold){
            sensitivity.set(1,0.6);
            return false;
        }else{
            sensitivity.set(0.5,0.6);
            return true;
        }

    }



    @Override
    public void userDisabledPeriodic(){
        SwitchAuto.disabled();
    }
}
