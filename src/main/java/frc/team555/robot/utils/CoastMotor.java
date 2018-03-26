package frc.team555.robot.utils;

import edu.wpi.first.wpilibj.SpeedController;
import org.montclairrobotics.sprocket.motors.Motor;
import org.montclairrobotics.sprocket.utils.Utils;

public class CoastMotor extends Motor{
    public CoastMotor(SpeedController motor, boolean brakeMode) {
        super(motor, brakeMode);
    }

    public void set(double power) {

            super.getMotor().set(power);

    }
}
