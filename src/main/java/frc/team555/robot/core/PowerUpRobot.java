package frc.team555.robot.core;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team555.robot.auto.PettingAutoMode;
import frc.team555.robot.PettingZooRobot;
import frc.team555.robot.auto.*;
import frc.team555.robot.components.CubeIntake;
import frc.team555.robot.utils.Side;
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
import org.montclairrobotics.sprocket.motors.CurrentMonitor;
import org.montclairrobotics.sprocket.motors.Module;
import org.montclairrobotics.sprocket.motors.Motor;
import org.montclairrobotics.sprocket.pipeline.Step;
import org.montclairrobotics.sprocket.states.StateMachine;
import org.montclairrobotics.sprocket.utils.Debug;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.PID;
import org.montclairrobotics.sprocket.utils.Togglable;

import java.util.ArrayList;

public class PowerUpRobot extends SprocketRobot {
    PettingZooRobot robot = new PettingZooRobot();

    DriveTrain driveTrain;
    public static GyroCorrection correction;
    GyroLock lock;
    boolean manualLock;
    public static CubeIntake intake;

    public static SendableChooser<Side> startSidesChooser;

    private static final int IMG_WIDTH = 320;
    private static final int IMG_HEIGHT = 240;


    private static final double oldOverNew=17.1859 * 1.25/(6544.0/143.0);

    //private double centerX = 0.0;
    //private VisionThread visionThread;

    //private final Object imgLock = new Object();

    @Override
    public void robotInit(){

        DriveEncoders.TOLLERANCE=/*45.5363/17.1859*/6;
        TurnGyro.TURN_SPEED=0.3;
        TurnGyro.tolerance=new Degrees(3);
        //40 ft 5.5 in
        Hardware.init();
        Control.init();
        DriveModule[] modules = new DriveModule[2];
        intake = new CubeIntake();

        modules[0] = new DriveModule(new XY(-1, 0),
                new XY(0, 1),
                Hardware.leftDriveEncoder,
                new PID(0.5,0,0),
                Module.MotorInputType.PERCENT,
                new Motor(Hardware.motorDriveBL),
                new Motor(Hardware.motorDriveFL));

        modules[1] = new DriveModule(new XY(1, 0),
                new XY(0, 1),
                Hardware.rightDriveEncoder,
                new PID(0.5,0,0),
                Module.MotorInputType.PERCENT,
                new Motor(Hardware.motorDriveBR),
                new Motor(Hardware.motorDriveFR));

        new CurrentMonitor("Front Right Drive Motor", Hardware.motorDriveFR,  new Input<Boolean>() {
            @Override
            public Boolean get() {
                return Control.driveStick.getMagnitude() > .1;
            }
        }).setEncoder(Hardware.rightDriveEncoder);

        new CurrentMonitor("Back Right Drive Motor", Hardware.motorDriveBR,  new Input<Boolean>() {
            @Override
            public Boolean get() {
                return Control.driveStick.getMagnitude() > .1;
            }
        }).setEncoder(Hardware.rightDriveEncoder);

        new CurrentMonitor("Front Left Drive Motor", Hardware.motorDriveFL,  new Input<Boolean>() {
            @Override
            public Boolean get() {
                return Control.driveStick.getMagnitude() > .1;
            }
        }).setEncoder(Hardware.leftDriveEncoder);

        new CurrentMonitor("Back Left Drive Motor", Hardware.motorDriveBL,  new Input<Boolean>() {
            @Override
            public Boolean get() {
                return Control.driveStick.getMagnitude() > .1;
            }
        }).setEncoder(Hardware.leftDriveEncoder);


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

        correction = new GyroCorrection(Hardware.navx, new PID(1.5, 0, 0.0015), 90, 1);
        lock = new GyroLock(correction);
        steps.add(new Deadzone());
        steps.add(new Sensitivity(1));
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
        Togglable fieldInput = new FieldCentricDriveInput(Control.driveStick,correction);
        new ToggleButton(Control.driveStick,Control.FieldCentricID,fieldInput);

        Button resetButton=new JoystickButton(Control.driveStick,Control.ResetID);
        resetButton.setPressAction(new ButtonAction() {
            @Override
            public void onAction() {
                correction.reset();
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

        //addAutoMode(new AutoMode("Switch Auto", new SwitchAuto(correction, intake)));
        sendAutoModes();

        StateMachine shootCube = new StateMachine(false, new SetIntakeRotation(intake, intake.middlePos),
                new CubeOuttake(intake, 1), new SetIntakeRotation(intake, intake.downPos));

        Control.intakeSubroutine.setHeldAction(new ButtonAction() {
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
                intake.rotationalMotor.set(intake.downPos);
            }
        });

        startSidesChooser = new SendableChooser<>();
        for(Side side :  Side.values()){
            startSidesChooser.addObject(side.toString(), side);
        }
        SmartDashboard.putData(startSidesChooser);

    }

    @Override
    public void userAutonomousSetup(){
        workshop();

        addAutoMode(new PettingAutoMode(robot));
    }











    /**
     * Welcome to Robotics Petting Zoo!
     *
     * We're Team 555, and we compete in the FIRST Robotics Competition (the big boi robots) and
     * today only, you can get a taste of what is like to program a --
     *
     * Wait. WAIT! Please don't leave! Coding can be fun, we promise. Let us introduce you to our friend.
     *
     * Meet "robot". robot is a robot who really, REALLY likes grammar -- no, not English. He speaks a language called
     * Java, and once you learn it, you can tell robot to do all sorts of things.
     *
     * To give robot something to do, just write a sentence in Java:
     *  - His name, (sup "robot"?)
     *  - A period, "."
     *  - Whatever the heck you want him to do (go "forward", turn "left", etc).
     *  - Parentheses, "()"
     *  - A semicolon, ";"
     *
     * In the end, your code might look something like this:
     *
     *    robot.forward();
     *    robot.left();
     *    robot.right(2);
     *
     * robot will go forward, turn left, then turn right 2 times (ooh, aah).
     *
     * Welp, that's about it. Please sign up for the Montclair Robotics Club this fall, see if you like it.
     *
     *
     * Peace, love, and Java,
     * The Code Division
     *
     */
    public void workshop() {
        // Put your fantabulous code here...

    }











    @Override
    public void update(){
        SmartDashboard.putNumber("Distance", driveTrain.getDistance().getY());
        SmartDashboard.putNumber("Left Encoder", Hardware.leftDriveEncoder.getInches().get());
        SmartDashboard.putNumber("Right Encoder", Hardware.rightDriveEncoder.getInches().get());
        Debug.msg("Intake Rotation", Hardware.intakeRotationEncoder.get());

        gyroLocking();

    }

    private void gyroLocking(){
        boolean autoLock = ((Math.abs(Control.driveInput.getTurn().toDegrees())<10) &&
                (Math.abs(Control.driveInput.getDir().getY())>0.5));
        if (autoLock || manualLock){
            lock.enable();
        } else{
            lock.disable();
        }
        //lock.update();
    }

    @Override
    public void userDisabledPeriodic(){
        SmartDashboard.putData(startSidesChooser) ;
    }
}
