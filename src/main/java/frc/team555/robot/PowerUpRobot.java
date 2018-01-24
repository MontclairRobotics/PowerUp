package frc.team555.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.auto.AutoMode;
import org.montclairrobotics.sprocket.auto.states.DriveEncoderGyro;
import org.montclairrobotics.sprocket.auto.states.DriveTime;
import org.montclairrobotics.sprocket.control.ButtonAction;
import org.montclairrobotics.sprocket.control.SquaredDriveInput;
import org.montclairrobotics.sprocket.drive.*;
import org.montclairrobotics.sprocket.drive.steps.Deadzone;
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.drive.utils.GyroLock;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;
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

    @Override
    public void robotInit(){
        Hardware.init();
        Control.init();
        DriveModule[] modules = new DriveModule[2];

        modules[0] = new DriveModule(new XY(-1, 0),
                new XY(0, 1),
                Hardware.backLeftEncoder,
                new PID(0,0,0),
                Module.MotorInputType.PERCENT,
                new Motor(Hardware.motorDriveBL),
                new Motor(Hardware.motorDriveFL));

        modules[1] = new DriveModule(new XY(1, 0),
                new XY(0, 1),
                Hardware.frontRightEncoder,
                new PID(0,0,0),
                Module.MotorInputType.PERCENT,
                new Motor(Hardware.motorDriveBR),
                new Motor(Hardware.motorDriveFR));

        DriveTrainBuilder dtBuilder = new DriveTrainBuilder();
        dtBuilder.addDriveModule(modules[0]).addDriveModule(modules[1]);

        /* Build Drive Train */

        dtBuilder.setInput(Control.driveInput);
        dtBuilder.setDriveTrainType(DriveTrainType.TANK);
        try {
            driveTrain = dtBuilder.build();
        } catch (InvalidDriveTrainException e) {
            e.printStackTrace();
        }

        /* Drive Train Configurations: Tank, Control */
        
        driveTrain.setMapper(new TankMapper());
        driveTrain.setDefaultInput(Control.driveInput);
        
        /* Drive Train Pipeline: GyroCorrection, Deadzone */
        

        ArrayList<Step<DTTarget>> steps = new ArrayList<>();

        correction = new GyroCorrection(Hardware.navx, new PID(0, 0, 0), 90, 1);
        
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

        //Auto
        AutoMode autoDrive = new AutoMode("Auto Drive",
                new DriveEncoderGyro(new Distance(120),
                        Angle.ZERO,
                        true,
                        1,
                        0,
                        0,
                        correction));

        AutoMode centerRight = new AutoMode("Center Right",
                new DriveEncoderGyro(new Distance(30),
                        Angle.ZERO,
                        true,
                        1,
                        0,
                        0,
                        correction),
                new DriveEncoderGyro(new Distance(88),
                        Angle.QUARTER,
                        true,
                        1,
                        0,
                        0,
                        correction),
                new DriveEncoderGyro(new Distance(90),
                        Angle.QUARTER.negative(),
                        true,
                        1,
                        0,
                        0,
                        correction));

        super.addAutoMode(autoDrive);
        super.addAutoMode(centerRight);
        sendAutoModes();
    }


    @Override
    public void update() {
        lock.update();

        SmartDashboard.putNumber("Left Encoder", Hardware.backLeftEncoder.getTicks());
        SmartDashboard.putNumber("Right Encoder", Hardware.frontRightEncoder.getTicks());
    }
}
