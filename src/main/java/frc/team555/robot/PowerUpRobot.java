package frc.team555.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.auto.AutoMode;
import org.montclairrobotics.sprocket.auto.states.*;
import org.montclairrobotics.sprocket.control.ButtonAction;
import org.montclairrobotics.sprocket.control.DashboardInput;
import org.montclairrobotics.sprocket.control.SquaredDriveInput;
import org.montclairrobotics.sprocket.drive.*;
import org.montclairrobotics.sprocket.drive.steps.Deadzone;
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.drive.utils.GyroLock;
import org.montclairrobotics.sprocket.geometry.*;
import org.montclairrobotics.sprocket.motors.Module;
import org.montclairrobotics.sprocket.motors.Motor;
import org.montclairrobotics.sprocket.motors.SEncoder;
import org.montclairrobotics.sprocket.pipeline.Pipeline;
import org.montclairrobotics.sprocket.pipeline.Step;
import org.montclairrobotics.sprocket.utils.Debug;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.PID;

import java.io.IOException;
import java.util.ArrayList;

public class PowerUpRobot extends SprocketRobot {
    DriveTrain driveTrain;
    GyroCorrection correction;
    GyroLock lock;
    //CubeIntake intake;

    @Override
    public void robotInit(){

        DriveEncoders.TOLLERANCE=3;
        TurnGyro.TURN_SPEED=0.3;
        TurnGyro.tolerance=new Degrees(5);

        Hardware.init();
        Control.init();
        DriveModule[] modules = new DriveModule[2];

        modules[0] = new DriveModule(new XY(-1, 0),
                new XY(0, 1),
                Hardware.leftEncoder,
                new PID(0.5,0,0),
                Module.MotorInputType.PERCENT,
                new Motor(Hardware.motorDriveBL),
                new Motor(Hardware.motorDriveFL));

        modules[1] = new DriveModule(new XY(1, 0),
                new XY(0, 1),
                Hardware.rightEncoder,
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
        

        new DashboardInput("Auto Selection");

        ArrayList<Step<DTTarget>> steps = new ArrayList<>();
        
        correction = new GyroCorrection(Hardware.navx, new PID(1.5, 0, 0.0015), 90, 1);
        lock = new GyroLock(correction);
        steps.add(correction);
        steps.add(new Deadzone());
        driveTrain.setPipeline(new DTPipeline(steps));

        /* Enabling and Disabling GyroLock */
        
        Control.lock.setHeldAction(new ButtonAction() {
            @Override public void onAction() { lock.enable(); }
        });

        Control.lock.setOffAction(new ButtonAction() {
        		@Override public void onAction() { lock.disable(); }
        });
        //this.intake = new CubeIntake();

        super.addAutoMode(new AutoMode("Dynamic Auto", new DynamicAutoState()));


        Debug.msg("Check 1", "Okay");
        //Auto
        final double driveSpeed = 0.4;
        final int maxEncAccel = 10;
        final int maxTicksPerSec = 10;
        AutoMode autoDrive = new AutoMode("Auto Drive",
                new DriveEncoderGyro(120,
                        0.25,
                        Angle.ZERO,
                        true,
                        correction));
        Debug.msg("Check 4", "Okay");

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
        Debug.msg("Check 2", "Okay");

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
        Debug.msg("Check 3", "Okay");
        addAutoMode(baseLine);
        addAutoMode(centerBaseLineLeft);
        addAutoMode(centerBaseLineRight);
        addAutoMode(square2);
        addAutoMode(encoder);
        addAutoMode(autoDrive);
        addAutoMode(turn90);
        addAutoMode(square);
        sendAutoModes();
        sendAutoModes();
    }

    @Override
    public void userAutonomousSetup(){

    }


    @Override

    public void update(){
        lock.update();
        SmartDashboard.putNumber("Distance", driveTrain.getDistance().getY());
        SmartDashboard.putNumber("Left Encoder", Hardware.leftEncoder.getInches().get());
        SmartDashboard.putNumber("Right Encoder", Hardware.rightEncoder.getInches().get());
    }
}
