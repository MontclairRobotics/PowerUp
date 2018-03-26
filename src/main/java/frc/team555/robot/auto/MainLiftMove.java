package frc.team555.robot.auto;

import frc.team555.robot.components.MainLift;
import frc.team555.robot.core.Hardware;
import org.montclairrobotics.sprocket.auto.AutoState;
import org.montclairrobotics.sprocket.states.State;

@Deprecated
public class MainLiftMove implements State {

    private double height;
    private double power;
    private boolean up;

    public MainLiftMove(double height, double power,boolean up)
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

        Hardware.motorLiftMainBack.set(0);
        Hardware.motorLiftMainFront.set(0);
    }

    @Override
    public void stateUpdate() {
        Hardware.motorLiftMainBack.set(power);
        Hardware.motorLiftMainFront.set(power);
    }

    @Override
    public boolean isDone() {
        return Hardware.liftEncoder.getInches().get()>height==up;
    }

}
