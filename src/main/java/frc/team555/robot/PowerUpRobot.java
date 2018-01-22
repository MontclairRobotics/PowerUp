package frc.team555.robot;

import edu.wpi.first.wpilibj.interfaces.Gyro;
import org.montclairrobotics.sprocket.SprocketRobot;
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
    CubeIntake intake;

    @Override
    public void robotInit(){
        Hardware.init();
        Control.init();
        DriveModule[] modules = new DriveModule[4];
        modules[0] = new DriveModule(new XY(-1, -1), Vector.ZERO, new Motor(Hardware.motorDriveBL));
        modules[1] = new DriveModule(new XY(1, -1), Vector.ZERO, new Motor(Hardware.motorDriveBR));
        modules[2] = new DriveModule(new XY(-1, 1), Vector.ZERO, new Motor(Hardware.motorDriveFL));
        modules[3] = new DriveModule(new XY(1, 1), Vector.ZERO, new Motor(Hardware.motorDriveFL));
        DriveTrainBuilder dtBuilder = new DriveTrainBuilder();
        dtBuilder.addDriveModule(modules[0]).addDriveModule(modules[1]).addDriveModule(modules[2]).addDriveModule(modules[3]);
        dtBuilder.setInput(Control.driveInput);
        dtBuilder.setDriveTrainType(DriveTrainType.TANK);
        try {
            this.driveTrain = dtBuilder.build();
        } catch (InvalidDriveTrainException e) {
            e.printStackTrace();
        }
        ArrayList<Step<DTTarget>> steps = new ArrayList<>();
        correction = new GyroCorrection(new Input<Double>() {
            @Override
            public Double get() {
                return (double)Hardware.navx.getYaw();
            }
        }, new PID(0, 0, 0), 90, 1);
        lock = new GyroLock(correction);
        steps.add(correction);
        steps.add(new Deadzone());
        DTPipeline pipeline;
        driveTrain.setPipeline(new DTPipeline(steps));


        Control.lock.setHeldAction(new ButtonAction() {
            @Override
            public void onAction() {
                lock.enable();
            }
        });

        Control.lock.setOffAction(new ButtonAction() {
            @Override
            public void onAction() {
                lock.disable();
            }
        });
        
        this.intake = new CubeIntake();
    }

    @Override
    public void update(){
    		driveTrain.update();
    		intake.update();
        lock.update();
    }
}
