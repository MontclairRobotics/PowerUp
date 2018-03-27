package frc.team555.robot.states;

import frc.team555.robot.components.Lift;
import org.montclairrobotics.sprocket.states.State;

public class MoveLift implements State
{
    private Lift lift;
    private double height;
    private double power;
    private boolean up;

    public MoveLift(Lift lift, double height, double power, boolean up)
    {
        this.lift=lift;
        this.height=height;
        this.power=power;
        this.up=up;
    }


    @Override
    public void start() {
        power=Math.abs(power);
        if(!up)
        {
            power=-power;
        }
    }

    @Override
    public void stop()
    {
        lift.setAuto(0);
    }

    @Override
    public void stateUpdate() {
        lift.setAuto(power);
    }

    @Override
    public boolean isDone() {
        return lift.getEncoder().getInches().get()>height==up;
    }

}
