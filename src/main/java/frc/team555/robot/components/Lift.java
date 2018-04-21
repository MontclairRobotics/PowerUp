package frc.team555.robot.components;

import org.montclairrobotics.sprocket.motors.SEncoder;

public interface Lift {
    public SEncoder getEncoder();
    public void setAuto(double power);
}
