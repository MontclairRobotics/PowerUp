package frc.team555.robot.core;

<<<<<<< HEAD
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
=======
>>>>>>> josh-auto-switch
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team555.robot.auto.*;
import frc.team555.robot.components.CubeIntake;
<<<<<<< HEAD
import frc.team555.robot.components.IntakeLift;
import frc.team555.robot.components.MainLift;
import frc.team555.robot.utils.CoastMotor;
=======
import frc.team555.robot.utils.MotorMonitor;
>>>>>>> josh-auto-switch
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
    DriveTrain driveTrain;
    public static GyroCorrection correction;
<<<<<<< HEAD
    Sensitivity sensitivity;
    GyroLock lock;
    boolean manualLock;
    CubeIntake intake;
    MainLift mainLift;
    // IntakeLift intakeLift;
    StateMachine autoClimb;
    public static Side startSide;
    //SendableChooser<Side> startSideChooser;
    static Input<Boolean> switchOnSide;
=======
    GyroLock lock;
    boolean manualLock;
    public static CubeIntake intake;

    public static SendableChooser<Side> startSidesChooser;
>>>>>>> josh-auto-switch


    //vision stuff
    private static final int IMG_WIDTH = 320;
    private static final int IMG_HEIGHT = 240;



    private static final double oldOverNew=17.1859 * 1.25/(6544.0/143.0);

    //private double centerX = 0.0;
    //private VisionThread visionThread;

    //private final Object imgLock = new Object();

    @Override
    public void robotInit(){
        //startSideChooser = new SendableChooser<>();
        //startSideChooser.addObject("Right", Side.RIGHT);
        //startSideChooser.addObject("Right", Side.LEFT);
        /*switchOnSide = new Input<Boolean>() {
            @Override
            public Boolean get() {
                return Side.fromDriverStation()[0] == startSide;
            }
        };*/


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



        //VISION
        DTStep vision =new DTStep() {
            @Override
            public DTTarget get(DTTarget dtTarget) {
                if(!Control.driveStick.getRawButton(7))
                {
                    return dtTarget;
                }
                double turn=0;
                double x=SmartDashboard.getNumber("cubeX",600)-600;
                if(x<-100)
                {
                    turn=-.1;
                }
                else if(x>100)
                {
                    turn=.1;
                }
                return new DTTarget(dtTarget.getDirection(),new Radians(turn));
            }
        };

        new DashboardInput("auto Selection");




        ArrayList<Step<DTTarget>> steps = new ArrayList<>();
        sensitivity=new Sensitivity(1,0.6);
        lock = new GyroLock(correction);
        steps.add(new Deadzone());
        steps.add(sensitivity);
        steps.add(correction);
        steps.add(vision);
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

        AutoMode turn90 = new AutoMode("Turn 90",
                new TurnGyro(new Degrees(90),correction,true));

        AutoMode square = new AutoMode("Square",
                new ResetGyro(correction),
                new DriveEncoders(4*12,0.25),
                new TurnGyro(new Degrees(90),correction,false),
                new DriveEncoders(4*12,0.25),
                new TurnGyro(new Degrees(180),correction,false),
                new DriveEncoders(4*12,0.25),
                new TurnGyro(new Degrees(270),correction,false),
                new DriveEncoders(4*12,0.25),
                new TurnGyro(new Degrees(0),correction,false));

        AutoMode square2 = new AutoMode("Square 2",
                new ResetGyro(correction),
                new DriveEncoderGyro(4*12,0.25,new Degrees(0),false,correction),
                new DriveEncoderGyro(4*12,0.25,new Degrees(90),false,correction),
                new DriveEncoderGyro(4*12,0.25,new Degrees(180),false,correction),
                new DriveEncoderGyro(4*12,0.25,new Degrees(270),false,correction));


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
        addAutoMode(new AutoMode("Switch Using Intake", new SwitchAuto(mainLift,correction, intake)));
        addAutoMode(mainLiftUp);
        addAutoMode(turnQuarter);
        addAutoMode(new AutoMode("Switch Using Lift", new TopCubeAuto(mainLift, intake, correction)));
        //addAutoMode(new AutoMode("Switch Auto", new SwitchAuto(correction, intake)));
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
                intake.rotationalMotor.set(intake.downPos);
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
<<<<<<< HEAD
        // intakeLift.setPower(0);
=======

        startSidesChooser = new SendableChooser<>();
        for(Side side :  Side.values()){
            startSidesChooser.addObject(side.toString(), side);
        }
        SmartDashboard.putData(startSidesChooser);

>>>>>>> josh-auto-switch
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

        double cubeCenterX=SmartDashboard.getNumber("cubeCenterX",0.0);
        SmartDashboard.putNumber("x rebroadcasted",cubeCenterX);
        SmartDashboard.putNumber("Distance", driveTrain.getDistance().getY());
        SmartDashboard.putNumber("Left Encoder", Hardware.leftDriveEncoder.getInches().get());
        SmartDashboard.putNumber("Right Encoder", Hardware.rightDriveEncoder.getInches().get());
<<<<<<< HEAD
        SmartDashboard.putBoolean("Lift Limit Switch", Hardware.liftLimitSwitch.get());
        SmartDashboard.putNumber("POV",Control.auxStick.getPOV());
        debugCurrent("Main Lift Front",Hardware.motorLiftMainFront);
        debugCurrent("Main Lift Back",Hardware.motorLiftMainBack);
        debugCurrent("Intake Lift",Hardware.motorLiftIntake);
        SmartDashboard.putNumber("Main Lift Encoder Value",Hardware.liftEncoder.getInches().get());
        //SmartDashboard.putNumber("Intake Lift Encoder",in.getInches().get());
=======
        Debug.msg("Intake Rotation", Hardware.intakeRotationEncoder.get());

>>>>>>> josh-auto-switch
        gyroLocking();
        //startSide = startSideChooser.getSelected();
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
<<<<<<< HEAD
        //SmartDashboard.putData(startSideChooser);
        //startSide = startSideChooser.getSelected();
        SwitchAuto.disabled();
=======
        SmartDashboard.putData(startSidesChooser) ;
>>>>>>> josh-auto-switch
    }
}
