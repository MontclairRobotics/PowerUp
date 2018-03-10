package frc.team555.robot.auto;

import frc.team555.robot.components.MainLift;
import frc.team555.robot.core.Hardware;
import org.montclairrobotics.sprocket.auto.AutoState;
import org.montclairrobotics.sprocket.states.State;

public class MainLiftUp implements State {

    private MainLift lift;
    private double height;
    private double power;

    public MainLiftUp(MainLift lift, double height,double power)
    {
        this.lift=lift;
        this.height=height;
        this.power=power;
    }


    @Override
    public void start() {

    }

    @Override
    public void stop()
    {

    }

    @Override
    public void stateUpdate() {
        lift.setPower(power);
    }

    @Override
    public boolean isDone() {
        return Hardware.liftEncoder.getInches().get()>height;
    }
}
