package frc.team555.robot.core;

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
            mainLiftBottom,
            openButton,
            closeButton,
            intakeRotationUp,
            intakeRotationDown,
            intakeRotationMiddle,
            intakeSubroutine,
            intakeRotationManualUp,
            intakeRotationManualDown;


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

        // intakeLiftIncrement = new JoystickButton(auxStick,12);
        // intakeLiftDecrement = new JoystickButton(auxStick,12);
        // mainLiftTop = new JoystickButton(auxStick,12);
        // mainLiftBottom = new JoystickButton(auxStick,12);
        mainLiftManualUp = new JoystickButton(auxStick,11);
        mainLiftManualDown = new JoystickButton(auxStick,10);
        intakeLiftManualUp = new JoystickButton(auxStick,3);
        intakeLiftManualDown = new JoystickButton(auxStick,2);
        intakeLiftTop = new JoystickButton(auxStick, 6);
        intakeLiftBottom = new JoystickButton(auxStick, 7);
        intakeSubroutine = new JoystickButton(auxStick, 14);
        intakeRotationUp = new JoystickButton(auxStick,8);
        intakeRotationManualUp = new JoystickButton(auxStick, 9);
        intakeRotationManualDown = new JoystickButton(auxStick, 8);

         openButton=new JoystickButton(Control.auxStick,8);
         closeButton=new JoystickButton(Control.auxStick,9);
        // Auxiliary Buttons

    }
}
