package frc.team555.robot.auto;

import frc.team555.robot.components.IntakeLift;
import frc.team555.robot.core.Hardware;
import org.montclairrobotics.sprocket.states.State;

@Deprecated
public class SetIntakeLift implements State {

    static final double power = .5;
    static IntakeLift lift;
    int position;

    public static void setLift(IntakeLift lift){
        SetIntakeLift.lift = lift;
    }


    public SetIntakeLift(int position, IntakeLift lift){
        this.lift = lift;
        this.position = position;
    }

    @Override
    public void start() {
        // lift.setPower(power);
    }

    @Override
    public void stop() {
        lift.setPower(0);
    }

    @Override
    public void stateUpdate() {
        lift.setPower(power);
    }

    @Override
    public boolean isDone() {
        return Hardware.intakeLiftEncoder.getInches().get() > position;
    }
}
