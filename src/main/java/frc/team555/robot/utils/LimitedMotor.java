package frc.team555.robot.utils;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedController;
import org.montclairrobotics.sprocket.motors.Motor;
import org.montclairrobotics.sprocket.utils.Input;

public class LimitedMotor extends Motor {
    private final DigitalInput upperBound;
    private final DigitalInput lowerBound;

    public LimitedMotor(SpeedController motor, boolean brakeMode, DigitalInput lowerBound, DigitalInput upperBound) {
        super(motor, brakeMode);
        this.lowerBound=lowerBound;
        this.upperBound=upperBound;
    }

    @Override
    public void set(double power)
    {
        if(power>0 && upperBound.get())
        {
            power=0;
        }
        if(power<0 && lowerBound.get())
        {
            power=0;
        }
        super.set(power);
    }
}
