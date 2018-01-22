package frc.team555.robot;

import edu.wpi.first.wpilibj.Joystick;
import org.montclairrobotics.sprocket.control.Button;
import org.montclairrobotics.sprocket.control.SquaredDriveInput;
import org.montclairrobotics.sprocket.drive.DTInput;

public class Control {

    // Port Configuration
    public static int lockButtonID = 1;
    public static final int driveStickID = 1;
    public static final int auxStickID   = 2;

    // Drive Inputs
    public static DTInput driveInput;
    public static Button lock;

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

        // Drive Buttons
        lock = new Button() {
            @Override
            public Boolean get() {
                return driveStick.getRawButton(lockButtonID);
            }
        };


        // Auxiliary Buttons

    }
}
