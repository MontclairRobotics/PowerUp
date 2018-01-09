package frc.team555.robot;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.control.SquaredDriveInput;
import org.montclairrobotics.sprocket.drive.*;
import org.montclairrobotics.sprocket.drive.steps.Deadzone;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.motors.Module;
import org.montclairrobotics.sprocket.motors.Motor;
import org.montclairrobotics.sprocket.pipeline.Pipeline;
import org.montclairrobotics.sprocket.pipeline.Step;

import java.util.ArrayList;

public class PowerUpRobot extends SprocketRobot {

    Hardware hardware;
    DriveTrain driveTrain;

    @Override
    public void robotInit(){
        Hardware.init();
        DriveModule[] modules = new DriveModule[4];
        modules[0] = new DriveModule(new XY(-1, -1), Vector.ZERO, new Motor(Hardware.motorDriveBL));
        modules[1] = new DriveModule(new XY(1, -1), Vector.ZERO, new Motor(Hardware.motorDriveBR));
        modules[2] = new DriveModule(new XY(-1, 1), Vector.ZERO, new Motor(Hardware.motorDriveFL));
        modules[3] = new DriveModule(new XY(1, 1), Vector.ZERO, new Motor(Hardware.motorDriveFL));

        driveTrain = new DriveTrain(modules);
        driveTrain.setMapper(new TankMapper());
        driveTrain.setDefaultInput(new SquaredDriveInput(Hardware.driveStick));
        ArrayList<Step<DTTarget>> steps = new ArrayList<>();
        steps.add(new Deadzone());
        DTPipeline pipeline;
        driveTrain.setPipeline(new DTPipeline(steps));
    }
}
