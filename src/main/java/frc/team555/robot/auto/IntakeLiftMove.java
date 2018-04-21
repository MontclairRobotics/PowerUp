package frc.team555.robot.auto;

import frc.team555.robot.core.Hardware;
import org.montclairrobotics.sprocket.states.State;

public class IntakeLiftMove implements State{
    private double height;
    private double power;
    private boolean up;

    public IntakeLiftMove(double height, double power,boolean up)
    {
        this.height=height;
        this.power=power;
        this.up=up;
    }


    @Override
    public void start() {

    }

    @Override
    public void stop()
    {
        Hardware.motorLiftIntake.set(0);
    }

    @Override
    public void stateUpdate() {
        Hardware.motorLiftIntake.set(power);

    }

    @Override
    public boolean isDone() {
        return Hardware.intakeLiftEncoder.getInches().get()>height==up;
    }
}
