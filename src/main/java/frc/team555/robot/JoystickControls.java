package frc.team555.robot;

import edu.wpi.first.wpilibj.Joystick;
import org.montclairrobotics.sprocket.control.ArcadeDriveInput;
import org.montclairrobotics.sprocket.control.Button;

public class JoystickControls {

    private int drivePort;
    private int auxPort;

    public JoystickControls(int driveStickPort, int auxStickPort){
        this.drivePort = driveStickPort;
        this.auxPort = auxStickPort;
    }
    // Drive Controls
    Joystick driveStick = new Joystick(drivePort);

    // AUX Controls
    Joystick auxStick = new Joystick(auxPort);

    public void buttonControls(){
        //Buttons

        while(driveStick.getTrigger()){
            // something
        }

    }

}
