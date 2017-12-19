package frc.team555.robot;

import edu.wpi.first.wpilibj.Joystick;
import org.montclairrobotics.sprocket.control.ArcadeDriveInput;
import org.montclairrobotics.sprocket.control.Button;

public class JoystickControls {

    // Control Variables
    public static final int DRIVE_STICK_PORT = 0;
    public static final int AUX_STICK_PORT = 1;

    // Drive Controls
    Joystick driveStick = new Joystick(DRIVE_STICK_PORT);

    // AUX Controls
    Joystick auxStick = new Joystick(AUX_STICK_PORT);

    public JoystickControls(){
        //Buttons
    }

}
