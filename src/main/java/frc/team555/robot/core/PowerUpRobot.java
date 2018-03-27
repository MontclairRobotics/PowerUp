package frc.team555.robot.core;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team555.robot.automodes.*;
import frc.team555.robot.states.*;
import frc.team555.robot.components.CubeIntake;
import frc.team555.robot.components.IntakeLift;
import frc.team555.robot.components.MainLift;
import frc.team555.robot.utils.CoastMotor;
import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.auto.AutoMode;
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

    double oldDistance;
    double distance;
    double oldSec;
    public static Vector position;


    //vision stuff
    private static final int IMG_WIDTH = 320;
    private static final int IMG_HEIGHT = 240;



    private static final double oldOverNew=17.1859 * 1.25/(6544.0/143.0);

    //private double centerX = 0.0;
    //private VisionThread visionThread;

    //private final Object imgLock = new Object();

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

        /*super.addAutoMode(new AutoMode("Dynamic auto", new DynamicAutoState()));
new DriveEncoderGyro(12*30,.5,new Degrees(0),false,correction);
*/
        //Togglable fieldInput = new FieldCentricDriveInput(Control.driveStick,correction);
        //new ToggleButton(Control.driveStick,Control.FieldCentricID,fieldInput);

        //Button resetButton=new JoystickButton(Control.driveStick,Control.ResetID);
        /*resetButton.setPressAction(new ButtonAction() {
            @Override
            public void onAction() {
                correction.reset();
            }
        });*/

        /*Control.halfSpeed.setPressAction(new ButtonAction() {
            @Override
            public void onAction() {
                sensitivity.set(0.5,0.3);
            }
        });

        Control.halfSpeed.setReleaseAction(new ButtonAction() {
            @Override
            public void onAction() {
                sensitivity.set(1,0.6);
            }
        });
*/




        //auto
        addAutoMode(new AutoDrive());
        addAutoMode(new BaseLineLR());
        addAutoMode(new CenterBaseLineLeft());
        addAutoMode(new CenterBaseLineRight());
        addAutoMode(new Square());
        addAutoMode(new SwitchUsingIntake());
        addAutoMode(new SwitchUsingLift());
        sendAutoModes();

        StateMachine shootCube = new StateMachine(false, new SetIntakeRotation(intake, intake.middlePos), new CubeOuttake(intake, 1), new SetIntakeRotation(intake, intake.downPos));

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

        Control.autoClimb.setReleaseAction(new ButtonAction() {
            @Override
            public void onAction() {
                autoClimb.stop();
            }
        });

        // vision stuff
//        CameraServer.getInstance().startAutomaticCapture();
        /*UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
        camera.setResolution(IMG_WIDTH, IMG_HEIGHT);

        visionThread = new VisionThread(camera, new DrivePipeline(), pipeline -> {
            if (!pipeline.filterContoursOutput().isEmpty()) {
                org.opencv.core.Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
                synchronized (imgLock) {
                    centerX = r.x + (r.width / 2);
                }
            }
        });
        visionThread.start();*/
        intakeLift.setPower(0);

        //pos stuff
        oldDistance = 0;
        distance = 0;
        oldSec = System.currentTimeMillis()/1000.0;
        position = Vector.ZERO;
    }

    @Override
    public void userAutonomousSetup(){

    }


    @Override
    public void update(){
        /*double centerX;
        synchronized (imgLock) {
            centerX = this.centerX;
        }
        double turn = centerX - (IMG_WIDTH / 2);

        SmartDashboard.putNumber("turn", turn);
*/
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
        position();
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

    //TODO: TEST AND SWITCH TO FLOW IF WE HAVE IT IN TIME
    private void position(){
        double pastSec = System.currentTimeMillis()/1000.0-oldSec;
        if(pastSec > 0.25) {
            distance = ((Hardware.leftDriveEncoder.getInches().get() +
                    Hardware.rightDriveEncoder.getInches().get()) / 2);

            double diffDistance = distance - oldDistance;

            Angle heading = correction.getCurrentAngleReset();
            Polar coord = new Polar(diffDistance, heading);
            position = position.add(coord);

            SmartDashboard.putNumber("Current X", position.getX());
            SmartDashboard.putNumber("Current Y", position.getY());
            SmartDashboard.putNumber("Current Heading", heading.toDegrees());
            oldDistance = distance;
            oldSec = System.currentTimeMillis()/1000.0;
        }

    }

    @Override
    public void userDisabledPeriodic(){
        SwitchAuto.disabled();
    }
}
