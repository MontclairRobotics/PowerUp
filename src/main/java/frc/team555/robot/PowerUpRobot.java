package frc.team555.robot;

import edu.wpi.first.wpilibj.Joystick;
import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.control.SquaredDriveInput;
import org.montclairrobotics.sprocket.drive.DriveTrainBuilder;
import org.montclairrobotics.sprocket.drive.DriveTrainType;
import org.montclairrobotics.sprocket.drive.InvalidDriveTrainException;

public class PowerUpRobot extends SprocketRobot{

    SprocketHardwareConfig hwConfig;


    //CONSTANTS
    public static final int driveStickPort = 0;


    @Override
    public void robotInit() {

        // JOYSTICK
        Joystick driveStick = new Joystick(driveStickPort);

        // DRIVETRAIN
        hwConfig = new SprocketHardwareConfig();
        DriveTrainBuilder dtBuilder = new DriveTrainBuilder();
        dtBuilder.addDriveModule(hwConfig.frontL);
        dtBuilder.addDriveModule(hwConfig.frontR);
        dtBuilder.addDriveModule(hwConfig.backL);
        dtBuilder.addDriveModule(hwConfig.backR);
        dtBuilder.setDriveTrainType(DriveTrainType.TANK);
        dtBuilder.setInput(new SquaredDriveInput(driveStick));
        try {
            dtBuilder.build();
        } catch (InvalidDriveTrainException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update() {

    }
}
