package frc.team555.robot;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.auto.AutoMode;
import org.montclairrobotics.sprocket.auto.states.DriveEncoderGyro;
import org.montclairrobotics.sprocket.auto.states.DriveEncoderLock;
import org.montclairrobotics.sprocket.control.DashboardInput;
import org.montclairrobotics.sprocket.control.ToggleButton;
import org.montclairrobotics.sprocket.drive.*;
import org.montclairrobotics.sprocket.drive.steps.Deadzone;
import org.montclairrobotics.sprocket.drive.steps.EncoderCorrection;
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.drive.steps.Sensitivity;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Distance;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.motors.Module;
import org.montclairrobotics.sprocket.motors.Motor;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.PID;

public class EncoderCorrectionTest extends SprocketRobot{
    DriveTrain driveTrain;

    @Override
    public void robotInit(){
        Hardware.init();
        Control.init();
        DriveModule[] modules = new DriveModule[2];

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

        Input<Double> P = new DashboardInput("P tune");
        Input<Double> I = new DashboardInput("I tune");
        Input<Double> D = new DashboardInput("D tune");


        EncoderCorrection correction = new EncoderCorrection(new PID(-0.1, 0, 0));
        driveTrain.setPipeline(new DTPipeline(correction));
        new ToggleButton(0, 1, correction);
        addAutoMode(new AutoMode("Work", new DriveEncoderLock(.5, new PID(-0.5, 0, 0), SprocketRobot.getDriveTrain(), 500)));
        sendAutoModes();
    }
}
