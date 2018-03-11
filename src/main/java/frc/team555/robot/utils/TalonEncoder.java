package frc.team555.robot.utils;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import org.montclairrobotics.sprocket.motors.SEncoder;

public class TalonEncoder extends SEncoder {
    private WPI_TalonSRX motor;
    private int zero = 0;
    public TalonEncoder(WPI_TalonSRX motor,double ticksPerInch) {
        super(null, ticksPerInch);
        this.motor = motor;
    }



    @Override
    public int getTicks() {
        return this.motor.getSensorCollection().getQuadraturePosition() - zero;
    }

    @Override
    public double getTickRate() {
        return this.motor.getSensorCollection().getQuadratureVelocity();
    }

    @Override
    public void reset()
    {
        zero = getTicks() + zero;
    }
}
