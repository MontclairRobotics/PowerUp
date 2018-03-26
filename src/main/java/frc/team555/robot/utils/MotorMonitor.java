package frc.team555.robot.utils;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.motors.Motor;
import org.montclairrobotics.sprocket.motors.SEncoder;
import org.montclairrobotics.sprocket.utils.Input;

public class MotorMonitor implements Input<Boolean>, Updatable {
    private WPI_TalonSRX motor;
    private Input<Double> input;
    private SEncoder encoder;
    private boolean status;
    private double tolerance;
    private double targetVoltage;
    private double voltageTolerance;


    public MotorMonitor(WPI_TalonSRX motor, Input<Double> input, SEncoder encoder) {
        this.motor = motor;
        this.input = input;
        this.encoder = encoder;
    }

    private void setTolerance(double tolerance){
        this.tolerance = tolerance;
    }

    @Override
    public Boolean get() {
        return null;
    }

    @Override
    public void update() {

    }
}
