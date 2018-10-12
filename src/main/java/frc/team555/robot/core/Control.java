package frc.team555.robot.core;

import edu.wpi.first.wpilibj.Joystick;
import frc.team555.robot.utils.JoystickButton2;
import org.montclairrobotics.sprocket.control.ArcadeDriveInput;
import org.montclairrobotics.sprocket.control.Button;
import org.montclairrobotics.sprocket.drive.DTInput;


public class Control {

    // Port Configuration
    public static final int driveStickID = 0;
    public static final int auxStickID   = 1;

    // Drive Inputs
    public static DTInput driveInput;
    public static Button lock,
            intakeLiftManualUp,
            intakeLiftManualDown,
            mainLiftAutoUp,
            mainLiftAutoDown,
            visionOn,
            vaultAlign,
            autoClimb,
            autoOuttake;

    // Joystick Declaration
    public static Joystick driveStick;
    public static Joystick auxStick;

    public static void init() {

        // ==================
        // Joystick Creation
        // ==================

        // Instantiate joysticks using joystick ID's
        driveStick = new Joystick(driveStickID);
        auxStick   = new Joystick(auxStickID);

        // Drive Input creation
        //driveInput = new SquaredDriveInput(driveStick);
        driveInput=new ArcadeDriveInput(driveStick);

        // ==================
        // Button Creation
        // ==================

        lock = new JoystickButton2(driveStick,3);

        // Main Lift Controls
        mainLiftAutoUp = new JoystickButton2(auxStick, 2);
        mainLiftAutoDown = new JoystickButton2(auxStick,3);

        // Intake Lift Controls
        intakeLiftManualUp = new JoystickButton2(auxStick,4);
        intakeLiftManualDown = new JoystickButton2(auxStick,5);

        //Driver Assisted Controls
        visionOn = new JoystickButton2(driveStick,3);
        autoClimb = new JoystickButton2(auxStick, 9);
        autoOuttake = new JoystickButton2(driveStick,1);
        vaultAlign = new JoystickButton2(driveStick,4);
    }
}



