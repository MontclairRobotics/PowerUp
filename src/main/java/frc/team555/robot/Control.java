package frc.team555.robot;

import edu.wpi.first.wpilibj.Joystick;
import org.montclairrobotics.sprocket.control.Button;
import org.montclairrobotics.sprocket.control.JoystickButton;
import org.montclairrobotics.sprocket.control.SquaredDriveInput;
import org.montclairrobotics.sprocket.drive.DTInput;


public class Control {

    // Port Configuration
    public static int lockButtonID = 1;
    public static final int driveStickID = 0;
    public static final int auxStickID   = 1;

    // Drive Inputs
    public static DTInput driveInput;
    public static Button lock,
            intakeLiftIncrement,
            intakeLiftDecrement,
            intakeLiftManualUp,
            intakeLiftManualDown,
            intakeLiftTop,
            intakeLiftBottom,
            mainLiftManualUp,
            mainLiftManualDown,
            mainLiftTop,
            mainLiftBottom;


    public static int FieldCentricID=2;
    public static int ResetID=3;
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
        driveInput = new SquaredDriveInput(driveStick);

        // ==================
        // Button Creation
        // ==================

        lock = new JoystickButton(driveStick,lockButtonID);

        //IntakeLift Buttons

        intakeLiftIncrement = new JoystickButton(auxStick,5);
        intakeLiftDecrement = new JoystickButton(auxStick,4);
        mainLiftTop = new JoystickButton(auxStick,6);
        mainLiftBottom = new JoystickButton(auxStick,7);
        mainLiftManualUp = new JoystickButton(auxStick,11);
        mainLiftManualDown = new JoystickButton(auxStick,10);
        intakeLiftManualUp = new JoystickButton(auxStick,3);
        intakeLiftManualDown = new JoystickButton(auxStick,2);
        intakeLiftTop = new JoystickButton(auxStick, 9);
        intakeLiftBottom = new JoystickButton(auxStick, 8);
        // Auxiliary Buttons

    }
}
