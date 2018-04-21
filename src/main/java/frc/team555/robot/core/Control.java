package frc.team555.robot.core;

import edu.wpi.first.wpilibj.Joystick;
import frc.team555.robot.utils.POVButton;
import org.montclairrobotics.sprocket.control.ArcadeDriveInput;
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
            intakeRotateUp,
            intakeRotateDown,
            intakeRotateMiddle,
            intakeRotateUpManual,
            intakeRotateDownManual,
            //intakeSubroutine;
            autoClimb;


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



        /*
        OLD
        mainLiftManualUp = new JoystickButton(auxStick,3);
        mainLiftManualDown = new JoystickButton(auxStick,2);
        intakeLiftManualUp = new JoystickButton(auxStick,5);
        intakeLiftManualDown = new JoystickButton(auxStick,4);
        mainLiftAutoUp = new JoystickButton(auxStick, 1);
        */
        mainLiftManualUp = new JoystickButton(auxStick,4);
        mainLiftManualDown = new JoystickButton(auxStick,3);
        intakeLiftManualUp = new JoystickButton(auxStick,6);
        intakeLiftManualDown = new JoystickButton(auxStick,5);
        mainLiftAutoUp = new JoystickButton(auxStick, 1);
        halfSpeed=new JoystickButton(driveStick,2);
        autoClimb = new JoystickButton(auxStick, 2);


        // intakeLiftTop = new JoystickButton(auxStick, 12);
        // intakeLiftBottom = new JoystickButton(auxStick, 13);
        // intakeSubroutine = new JoystickButton(auxStick, 14);


        // openButton=new JoystickButton(Control.auxStick,8);
        // closeButton=new JoystickButton(Control.auxStick,9);

        //Intake Buttons
        intakeRotateUp = new POVButton(auxStick, 0);
        intakeRotateDown = new POVButton(auxStick, 180);
        intakeRotateMiddle=new POVButton(auxStick,90);
        intakeRotateUpManual = new JoystickButton(auxStick, 7);
        intakeRotateDownManual = new JoystickButton(auxStick, 11);

                // Auxiliary Buttons

    }
}



