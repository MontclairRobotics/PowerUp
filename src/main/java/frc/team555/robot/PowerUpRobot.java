package frc.team555.robot;

import edu.wpi.first.wpilibj.interfaces.Gyro;
import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.auto.AutoMode;
import org.montclairrobotics.sprocket.control.ButtonAction;
import org.montclairrobotics.sprocket.control.SquaredDriveInput;
import org.montclairrobotics.sprocket.drive.*;
import org.montclairrobotics.sprocket.drive.steps.Deadzone;
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.drive.utils.GyroLock;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.motors.Module;
import org.montclairrobotics.sprocket.motors.Motor;
import org.montclairrobotics.sprocket.pipeline.Pipeline;
import org.montclairrobotics.sprocket.pipeline.Step;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.PID;

import java.util.ArrayList;

public class PowerUpRobot extends SprocketRobot {
    Hardware hardware;
    DriveTrain driveTrain;
    Gyro navx;
    GyroCorrection correction;
    GyroLock lock;

    @Override
    public void robotInit(){
        Hardware.init();
        Control.init();
        
        /* Build Drive Train */
        
        DriveTrainBuilder dtBuilder = new DriveTrainBuilder();
        dtBuilder.addDriveModule(new DriveModule(new XY(-1, -1), Vector.ZERO, new Motor(Hardware.motorDriveBL)));
        dtBuilder.addDriveModule(new DriveModule(new XY(1, -1), Vector.ZERO, new Motor(Hardware.motorDriveBR)));
        dtBuilder.addDriveModule(new DriveModule(new XY(-1, 1), Vector.ZERO, new Motor(Hardware.motorDriveFL)));
        dtBuilder.addDriveModule(new DriveModule(new XY(1, 1), Vector.ZERO, new Motor(Hardware.motorDriveFL)));
        
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
        
        correction = new GyroCorrection(new Input<Double>() {
            	@Override
            public Double get() {
                return (double) Hardware.navx.getYaw();
            }
        }, new PID(0, 0, 0), 90, 1);
        
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
        
        /* Add and Send AutoModes */
        
        super.addAutoMode(new AutoMode("Right Auto"));
        super.sendAutoModes();
    }

    @Override
    public void userAutonomousSetup(){
        
    }


    @Override
    public void update() {
        lock.update();
    }
}
