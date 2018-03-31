package frc.team555.robot.core;

import edu.wpi.first.wpilibj.Joystick;
import org.montclairrobotics.sprocket.control.ArcadeDriveInput;
import org.montclairrobotics.sprocket.control.Button;
import org.montclairrobotics.sprocket.control.JoystickButton;
import org.montclairrobotics.sprocket.control.SquaredDriveInput;
import org.montclairrobotics.sprocket.drive.DTInput;


public class Control {

    // HID Port Numbers
    public static final int driveStickID  = 0;
    public static final int auxStickID    = 1;
    public static final int buttonBoardID = 2;

    //Button Assignments (Driver)
    private static final int lockButtonID      = 1;
    private static final int halfSpeedButtonID = 2;
    private static final int autoClimbID       = 4;
    private static final int autoIntakeID      = 3;

    //Button Assignments (Aux)
    private static final int mainLiftAutoUpID       = 1;
    private static final int mainLiftManualUpID     = 4;
    private static final int mainLiftManualDownID   = 3;
    private static final int intakeLiftManualUpID   = 6;
    private static final int intakeLiftManualDownID = 5;

    //Button Assignments (Button Board)


    // Drive Inputs
    public static DTInput driveInput;
    public static Button lock,
            halfSpeed,
            //intakeLiftIncrement,
            //intakeLiftDecrement,
            intakeLiftManualUp,
            intakeLiftManualDown,
            //intakeLiftTop,
            //intakeLiftBottom,
            mainLiftAutoUp,
            mainLiftManualUp,
            mainLiftManualDown,
            // mainLiftTop,
            // mainLiftBottom,
            //openButton,
            //closeButton,
            //intakeRotateUp,
            //intakeRotateDown,
            //intakeRotateMiddle,
            //intakeRotateUpManual,
            //intakeRotateDownManual;
            //intakeSubroutine;
            autoClimb,
            autoIntake;


    //public static int FieldCentricID=2;
    //public static int ResetID=3;

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

        lock = new JoystickButton(driveStick,lockButtonID);

        //IntakeLift Buttons

        // intakeLiftIncrement = new JoystickButton(auxStick,12);
        // intakeLiftDecrement = new JoystickButton(auxStick,12);
        // mainLiftTop = new JoystickButton(auxStick,9);
        // mainLiftBottom = new JoystickButton(auxStick,8);

        halfSpeed            = new JoystickButton(driveStick, halfSpeedButtonID);
        autoIntake           = new JoystickButton(driveStick, autoIntakeID);
        mainLiftManualUp     = new JoystickButton(auxStick, mainLiftManualUpID);
        mainLiftManualDown   = new JoystickButton(auxStick, mainLiftManualDownID);
        intakeLiftManualUp   = new JoystickButton(auxStick, intakeLiftManualUpID);
        intakeLiftManualDown = new JoystickButton(auxStick, intakeLiftManualDownID);
        mainLiftAutoUp       = new JoystickButton(auxStick, mainLiftAutoUpID);
        autoClimb            = new JoystickButton(auxStick, autoClimbID);


        // intakeLiftTop = new JoystickButton(auxStick, 12);
        // intakeLiftBottom = new JoystickButton(auxStick, 13);
        // intakeSubroutine = new JoystickButton(auxStick, 14);


        // openButton=new JoystickButton(Control.auxStick,8);
        // closeButton=new JoystickButton(Control.auxStick,9);

        //Intake Buttons
        // intakeRotateUp = new JoystickButton(auxStick, 12);
        // intakeRotateDown = new JoystickButton(auxStick, 4);
        // intakeRotateMiddle=new JoystickButton(auxStick,5);
        // intakeRotateUpManual = new JoystickButton(auxStick, 3);
        // intakeRotateDownManual = new JoystickButton(auxStick, 2);

                // Auxiliary Buttons

    }
}



