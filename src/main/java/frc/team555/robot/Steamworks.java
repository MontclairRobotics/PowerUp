package frc.team555.robot;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.drive.DriveModule;
import org.montclairrobotics.sprocket.drive.DriveTrainBuilder;
import org.montclairrobotics.sprocket.drive.DriveTrainType;
import org.montclairrobotics.sprocket.drive.InvalidDriveTrainException;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.motors.Motor;

public class Steamworks  extends SprocketRobot{
    public final int frontLeftDeviceNumber = 3;
    public final int frontRightDeviceNumber = 1;
    public final int backLeftDeviceNumber = 4;
    public final int backRightDeviceNumber = 2;

    public final int driveStickDeviceNumber = 0;
    public final int auxStickDeviceNumber = 1;
    Joystick joystick;

    @Override
    public void robotInit() {
        joystick = new Joystick(driveStickDeviceNumber);
        CANTalon drivetrainFL = new CANTalon(frontLeftDeviceNumber);
        CANTalon drivetrainFR = new CANTalon(frontRightDeviceNumber);
        CANTalon drivetrainBL = new CANTalon(backLeftDeviceNumber);
        CANTalon drivetrainBR = new CANTalon(backRightDeviceNumber);

        DriveModule dtLeft  = new DriveModule(new XY(-1,0), new XY(0,1), new Motor(drivetrainFL), new Motor(drivetrainBL));
        DriveModule dtRight = new DriveModule(new XY( 1,0), new XY(0,1), new Motor(drivetrainFR), new Motor(drivetrainBR));

        DriveTrainBuilder dtBuilder = new DriveTrainBuilder();
        dtBuilder.addDriveModule(dtLeft);
        dtBuilder.addDriveModule(dtRight);
        dtBuilder.setDriveTrainType(DriveTrainType.TANK);
        dtBuilder.setArcadeDriveInput(joystick);

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
