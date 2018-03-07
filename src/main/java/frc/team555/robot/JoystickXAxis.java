//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package frc.team555.robot;

import edu.wpi.first.wpilibj.Joystick;
import org.montclairrobotics.sprocket.utils.Input;

public class JoystickXAxis implements Input<Double> {
    private Joystick stick;

    public JoystickXAxis(int port) {
        this.stick = new Joystick(port);
    }

    public JoystickXAxis(Joystick stick) {
        this.stick = stick;
    }

    public Double get() {
        return -this.stick.getX();
    }
}
