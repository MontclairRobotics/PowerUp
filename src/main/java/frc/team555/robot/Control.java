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
    liftUpButton,
    liftDownButton,
    liftTopButton,
    liftBottomButton,
    liftManualUp,
    liftManualDown;


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

        //Lift Buttons
        //liftUpButton=new JoystickButton(auxStick,10);
        //liftDownButton=new JoystickButton(auxStick,10);
        //liftTopButton=new JoystickButton(auxStick,10);
        //liftBottomButton=new JoystickButton(auxStick,10);
        //liftManualUp=new JoystickButton(auxStick,10);
        //liftManualDown=new JoystickButton(auxStick,10);

        // Auxiliary Buttons

    }
}
