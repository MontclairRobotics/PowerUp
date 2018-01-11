package frc.team555.PowerUp;

import edu.wpi.first.wpilibj.Joystick;
import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.control.SquaredDriveInput;
import org.montclairrobotics.sprocket.control.ToggleButton;
import org.montclairrobotics.sprocket.drive.DriveTrainBuilder;
import org.montclairrobotics.sprocket.drive.DriveTrainType;
import org.montclairrobotics.sprocket.drive.InvalidDriveTrainException;
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.drive.utils.GyroLock;
import org.montclairrobotics.sprocket.utils.PID;

public class Robot extends SprocketRobot{

    SprocketHardwareConfig hwConfig;
    GyroLock gLock;


    //CONSTANTS
    public static final int driveStickPort = 0;
    public static final int auxStickPort   = 1;

    public static final int buttonGLock    = 1;

    public static final double pidP        = 0;
    public static final double pidI        = 0;
    public static final double pidD        = 0;
    public static final double pidError    = 0;
    public static final double pidFarP     = 0;

    @Override
    public void robotInit() {

        // JOYSTICK
        Joystick driveStick = new Joystick(driveStickPort);
        Joystick auxStick   = new Joystick(auxStickPort);

        // DRIVETRAIN & GYRO LOCK
        hwConfig = new SprocketHardwareConfig();

        DriveTrainBuilder dtBuilder = new DriveTrainBuilder();
        dtBuilder.addDriveModule(hwConfig.dtL);
        dtBuilder.addDriveModule(hwConfig.dtR);
        dtBuilder.setDriveTrainType(DriveTrainType.TANK);
        dtBuilder.setInput(new SquaredDriveInput(driveStick));

        PID gyroPID = new PID(pidP, pidI, pidD);
        gyroPID.setInput(hwConfig.navXYawInput);
        GyroCorrection gCorrect=new GyroCorrection(hwConfig.navXYawInput, gyroPID,pidError,pidFarP);
        dtBuilder.addStep(gCorrect);
        gLock = new GyroLock(gCorrect);
        new ToggleButton(driveStick, buttonGLock, gLock);

        try {
            dtBuilder.build();
        } catch (InvalidDriveTrainException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update() {
        gLock.update();
    }
}
