package frc.team555.robot.core;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team555.robot.auto.SwitchAuto;
import frc.team555.robot.auto.SwitchAuto2;
import frc.team555.robot.components.CubeIntake;
import frc.team555.robot.components.MainLift;
import frc.team555.robot.components.SimpleIntake;
import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.auto.AutoMode;
import org.montclairrobotics.sprocket.auto.states.*;
import org.montclairrobotics.sprocket.control.*;
import org.montclairrobotics.sprocket.drive.*;
import org.montclairrobotics.sprocket.drive.steps.AccelLimit;
import org.montclairrobotics.sprocket.drive.steps.Deadzone;
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.drive.steps.Sensitivity;
import org.montclairrobotics.sprocket.drive.utils.GyroLock;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.motors.Module;
import org.montclairrobotics.sprocket.motors.Motor;
import org.montclairrobotics.sprocket.pipeline.Step;
import org.montclairrobotics.sprocket.utils.Debug;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.PID;
import org.montclairrobotics.sprocket.utils.Togglable;

import java.awt.image.renderable.ContextualRenderedImageFactory;
import java.util.ArrayList;

public class PIDLoopTest extends SprocketRobot {
    public static final boolean SWITCHES=false;


    DriveTrain driveTrain;
    GyroCorrection correction;
    GyroLock lock;
    boolean manualLock;
    //SimpleIntake intake;
    //MainLift lift;
    Sensitivity sensitivity;

    private double dir=1.0;
    private AccelLimit limit;

    public static final double sD=1,sT=.7;//sensitivity Direction and Turn

    //vision stuff
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
        SwitchAuto2.init();
        DriveModule[] modules = new DriveModule[2];
        //intake = new SimpleIntake();
        //lift=new MainLift();
        limit=new AccelLimit(3,5);


        //Temp Controls
        //Lift
        Input<Double> liftInput=new JoystickYAxis(Control.auxStick);
        ControlledMotor testMotor2 = new ControlledMotor(Hardware.motorLiftMainBack, liftInput);
        ControlledMotor testMotor3 = new ControlledMotor(Hardware.motorLiftMainFront, liftInput);

        //Intake
        Button intakeLiftUp=new JoystickButton(Control.auxStick,3);
        Button intakeLiftDown=new JoystickButton(Control.auxStick,2);
        intakeLiftUp.setHeldAction(new ButtonAction() {
            @Override
            public void onAction() {
                Hardware.motorLiftIntake.set(1);
            }
        });
        intakeLiftDown.setHeldAction(new ButtonAction() {
            @Override
            public void onAction() {
                Hardware.motorLiftIntake.set(-1);
            }
        });
        ButtonAction stopIntakeLift=new ButtonAction() {
            @Override
            public void onAction() {
                Hardware.motorLiftIntake.set(0);
            }
        };
        intakeLiftUp.setReleaseAction(stopIntakeLift);
        intakeLiftDown.setReleaseAction(stopIntakeLift);

        //Clamp
        Button clampOut=new JoystickButton(Control.auxStick,5);
        Button clampIn=new JoystickButton(Control.auxStick,4);
        clampOut.setHeldAction(new ButtonAction() {
            @Override
            public void onAction() {
                if(SWITCHES && Hardware.intakeOpenSwitch.get()) {
                    Hardware.motorIntakeClamp.set(0);
                }
                else
                {
                    Hardware.motorIntakeClamp.set(1);
                }
            }
        });
        clampIn.setHeldAction(new ButtonAction() {
            @Override
            public void onAction() {
                if(SWITCHES && Hardware.intakeOpenSwitch.get()) {
                    Hardware.motorIntakeClamp.set(0);
                }
                else
                {
                    Hardware.motorIntakeClamp.set(-1);
                }
            }
        });
        ButtonAction stopClamp=new ButtonAction() {
            @Override
            public void onAction() {
                Hardware.motorIntakeClamp.set(0);
            }
        };
        clampOut.setReleaseAction(stopClamp);
        clampIn.setReleaseAction(stopClamp);


        //Auto lift
        JoystickButton autoLift=new JoystickButton(Control.auxStick,1);
        autoLift.setHeldAction(new ButtonAction() {
            @Override
            public void onAction() {
                if(Hardware.liftEncoder.getInches().get()<31600)
                {
                    Hardware.motorLiftMainFront.set(1);
                    Hardware.motorLiftMainBack.set(1);
                }
                else
                {
                    Hardware.motorLiftMainFront.set(0);
                    Hardware.motorLiftMainBack.set(0);
                }
                testMotor2.disable();
                testMotor3.disable();
            }
        });
        autoLift.setReleaseAction(new ButtonAction() {
            @Override
            public void onAction() {
                Hardware.motorLiftMainFront.set(0);
                Hardware.motorLiftMainBack.set(0);

                testMotor2.enable();
                testMotor3.enable();
            }
        });


        //Half Speed
        Button halfSpeedButton=new JoystickButton(Control.auxStick,4);
        halfSpeedButton.setPressAction(new ButtonAction() {
            @Override
            public void onAction() {
                sensitivity.set(sD*0.5*dir, sT*0.5);
            }
        });

        halfSpeedButton.setReleaseAction(new ButtonAction() {
            @Override
            public void onAction() {
                sensitivity.set(sD*dir, sT);
            }
        });

        //Backwards Button
        Button backwardsButton=new JoystickButton(Control.driveStick,12);
        backwardsButton.setPressAction(new ButtonAction() {
            @Override
            public void onAction() {
                dir=-1;
            }
        });

        //Forwards Button
        Button forwardsButton=new JoystickButton(Control.driveStick,11);
        backwardsButton.setPressAction(new ButtonAction() {
            @Override
            public void onAction() {
                dir=1;
            }
        });

        //Limiter Off
        Button accelLimitOff=new JoystickButton(Control.driveStick,7);
        accelLimitOff.setPressAction(new ButtonAction() {
            @Override
            public void onAction() {
                limit.disable();
            }
        });

        //Limiter Off
        Button accelLimitOn=new JoystickButton(Control.driveStick,7);
        accelLimitOn.setPressAction(new ButtonAction() {
            @Override
            public void onAction() {
                limit.enable();
            }
        });
        Motor mBL=new Motor(Hardware.motorDriveBL, false);
        Motor mBR=new Motor(Hardware.motorDriveBR, false);
        Motor mFL=new Motor(Hardware.motorDriveFL, false);
        Motor mFR=new Motor(Hardware.motorDriveFR, false);


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
        sensitivity=new Sensitivity(sD*dir,sT);
        steps.add(new Deadzone());
        steps.add(limit);
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
        /*AutoMode autoDrive = new AutoMode("auto Drive",
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

*/
        AutoMode baseLine = new AutoMode("Base Line",
                new ResetGyro(correction),
                new DriveEncoderGyro(150, .5 , new Degrees(0), false, correction));
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

        AutoMode switchAuto2=new AutoMode("Switch",new SwitchAuto2(correction));

/*
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
*/      addAutoMode(new AutoMode("nothing", new Delay(1)));
        addAutoMode(baseLine);
        addAutoMode(centerBaseLineLeft);
        addAutoMode(centerBaseLineRight);
        //addAutoMode(new AutoMode("Switch Auto", new SwitchAuto(correction, intake)));
        addAutoMode(switchAuto2);
        addAutoMode(new AutoMode("Drive Time",new DriveTime(4,.5)));
        sendAutoModes();

        CameraServer.getInstance().startAutomaticCapture();


        // vision stuff
        /*UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
        camera.setResolution(IMG_WIDTH, IMG_HEIGHT);

6gtyj              visionThread = new VisionThread(camera, new DrivePipeline(), pipeline -> {
            if (!pipeline.filterContoursOutput().isEmpty()) {
                org.opencv.core.Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
                synchronized (imgLock) {
                    centerX = r.x + (r.width / 2);
                }
            }
        });
        visionThread.start();*/


/*
        AutoMode driveBack20Mode=new AutoMode("DriveBack20",new DriveEncoderGyro(-23,0.5,Angle.ZERO,true,correction));

        JoystickButton driveBack20=new JoystickButton(Control.driveStick,4);
        driveBack20.setPressAction(new ButtonAction() {
            @Override
            public void onAction() {
                driveBack20Mode.start();
            }
        });
        driveBack20.setReleaseAction(new ButtonAction() {
            @Override
            public void onAction() {
                driveBack20Mode.stop();
            }
        });
*/
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
        SmartDashboard.putNumber("Intake Lift Encoder", Hardware.intakeLiftEncoder.getInches().get());
        SmartDashboard.putNumber("Lift",Hardware.liftEncoder.getInches().get());
        if(SWITCHES) {
            SmartDashboard.putBoolean("Intake Open", Hardware.intakeOpenSwitch.get());
            SmartDashboard.putBoolean("Intake Closed", Hardware.intakeClosedSwitch.get());
        }
        Debug.msg("Intake Position",Hardware.motorLiftIntake.getSensorCollection().getQuadraturePosition());
        Debug.msg("Intake Velocity",Hardware.motorLiftIntake.getSensorCollection().getQuadraturePosition());
        Hardware.motorLiftIntake.getSensorCollection().getQuadratureVelocity();


        gyroLocking();

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
    }

    @Override
    public void userDisabledPeriodic(){
        SwitchAuto2.disabled();
    }
}
