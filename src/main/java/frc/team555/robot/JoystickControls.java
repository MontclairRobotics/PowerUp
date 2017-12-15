package frc.team555.robot;

import edu.wpi.first.wpilibj.Joystick;
import org.montclairrobotics.sprocket.control.ArcadeDriveInput;

public class JoystickControls {
    //Finals
    public static final int DRIVE_STICK_PORT = 0;

    Joystick driveStick    = new Joystick(DRIVE_STICK_PORT);
    ArcadeDriveInput driveStickInput = new ArcadeDriveInput(driveStick);
}
