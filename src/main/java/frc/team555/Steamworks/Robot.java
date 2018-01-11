package frc.team555.Steamworks;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team555.PowerUp.MathAlgorithms;
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

    DrivetrainConfig dtConfig;
    IntakeConfig intakeConfig;
    GyroLock gLock;


    //CONSTANTS
    public static final int driveStickPort = 0;
    public static final int auxStickPort   = 1;

    public static final int buttonIntake   = 1;
    public static final int buttonGLock    = 2;

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
        dtConfig = new DrivetrainConfig();

        DriveTrainBuilder dtBuilder = new DriveTrainBuilder();
        dtBuilder.addDriveModule(dtConfig.dtL);
        dtBuilder.addDriveModule(dtConfig.dtR);
        dtBuilder.setDriveTrainType(DriveTrainType.TANK);
        dtBuilder.setInput(new SquaredDriveInput(driveStick));

        PID gyroPID = new PID(pidP, pidI, pidD);
        gyroPID.setInput(dtConfig.navXYawInput);
        GyroCorrection gCorrect=new GyroCorrection(dtConfig.navXYawInput, gyroPID,pidError,pidFarP);
        dtBuilder.addStep(gCorrect);
        gLock = new GyroLock(gCorrect);
        new ToggleButton(driveStick, buttonGLock, gLock);

        intakeConfig = new IntakeConfig(driveStick, buttonIntake);


        try {
            dtBuilder.build();
        } catch (InvalidDriveTrainException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update() {
        gLock.update();
        dtConfig.checkCurrentDT();
    }

}
