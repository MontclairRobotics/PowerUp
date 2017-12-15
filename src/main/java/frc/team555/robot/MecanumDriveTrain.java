package frc.team555.robot;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.control.ArcadeDriveInput;
import org.montclairrobotics.sprocket.drive.DriveModule;
import org.montclairrobotics.sprocket.drive.DriveTrainBuilder;
import org.montclairrobotics.sprocket.drive.DriveTrainType;
import org.montclairrobotics.sprocket.drive.InvalidDriveTrainException;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.motors.Motor;
import org.montclairrobotics.sprocket.utils.Debug;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Joystick;

public class MecanumDriveTrain {

    JoystickControls joystick;

    //drive train motors
    CANTalon frontLeft     = new CANTalon(0);
    CANTalon frontRight    = new CANTalon(1);
    CANTalon backLeft      = new CANTalon(2);
    CANTalon backRight     = new CANTalon(3);

    //Drive modules //TODO: Test Force Vectors
    DriveModule frontLeftModule  = new DriveModule(new XY(-1, 1),  new XY(1, 1),  new Motor(frontLeft));
    DriveModule frontRightModule = new DriveModule(new XY(1, 1),   new XY(-1, 1), new Motor(frontRight));
    DriveModule backLeftModule   = new DriveModule(new XY(-1, -1), new XY(-1, 1), new Motor(backLeft));
    DriveModule backRightModule  = new DriveModule(new XY(1, -1),  new XY(1, 1),  new Motor(backRight));

    public void driveTrainBuild() {

        //Drive train builder
        DriveTrainBuilder builder = new DriveTrainBuilder();
        builder.addDriveModule(frontLeftModule);
        builder.addDriveModule(frontRightModule);
        builder.addDriveModule(backLeftModule);
        builder.addDriveModule(backRightModule);
        builder.setDriveTrainType(DriveTrainType.MECANUM);
        builder.setInput(joystick.driveStickInput);
        try {
            builder.build();
        } catch (InvalidDriveTrainException e) {
            Debug.msg("Status","Drive Train Builder FAILED");
        }

    }

}
