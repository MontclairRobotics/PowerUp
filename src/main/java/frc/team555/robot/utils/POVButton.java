package frc.team555.robot.utils;


import edu.wpi.first.wpilibj.Joystick;
import org.montclairrobotics.sprocket.control.Button;

public class POVButton extends Button {

    Joystick stick;
    int pov;
    public POVButton(Joystick stick,int pov)
    {
        this.stick=stick;
        this.pov=pov;
    }

    @Override
    public Boolean get() {
        return stick.getPOV()==pov;
    }
}
