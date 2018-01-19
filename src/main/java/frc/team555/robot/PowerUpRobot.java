package frc.team555.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.*;
import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.control.SquaredDriveInput;
import org.montclairrobotics.sprocket.drive.*;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.motors.Motor;

public class PowerUpRobot extends SprocketRobot {

    // DT IDs
    public static final int frontLeftDeviceNumber  = 4;
    public static final int frontRightDeviceNumber = 3;
    public static final int backLeftDeviceNumber   = 2;
    public static final int backRightDeviceNumber  = 1;

    // Joysticks IDs
    public static final int driveStickDeviceNumber = 0;

    // Drive Train Motors
    WPI_TalonSRX drivetrainFL;
    WPI_TalonSRX drivetrainFR;
    WPI_TalonSRX drivetrainBL;
    WPI_TalonSRX drivetrainBR;

    @Override
    public void robotInit() {
        Joystick driveStick = new Joystick(driveStickDeviceNumber);

        // ========= DRIVETRAIN ========= //

        drivetrainFL = new WPI_TalonSRX(frontLeftDeviceNumber);
        drivetrainFR = new WPI_TalonSRX(frontRightDeviceNumber);
        drivetrainBL = new WPI_TalonSRX(backLeftDeviceNumber);
        drivetrainBR = new WPI_TalonSRX(backRightDeviceNumber);

        // Drive train setup
        DriveModule dtLeft  = new DriveModule(new XY(-1,0), new XY(0,1), new Motor(drivetrainFL), new Motor(drivetrainBL));
        DriveModule dtRight = new DriveModule(new XY( 1,0), new XY(0, 1), new Motor(drivetrainFR), new Motor(drivetrainBR));

        DriveTrainBuilder dtBuilder = new DriveTrainBuilder();
        dtBuilder.addDriveModule(dtLeft);
        dtBuilder.addDriveModule(dtRight);

        //Drive train control
        dtBuilder.setDriveTrainType(DriveTrainType.TANK);
        dtBuilder.setInput(new SquaredDriveInput(driveStick));

        // Create drive train
        try { dtBuilder.build(); } catch (InvalidDriveTrainException e) { e.printStackTrace(); }

    }



    @Override

    public void update() {

    }

}