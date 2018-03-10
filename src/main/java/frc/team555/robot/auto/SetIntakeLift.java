package frc.team555.robot.auto;

import frc.team555.robot.components.IntakeLift;
import org.montclairrobotics.sprocket.states.State;

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
        lift.setPower(power);
        lift.setPosition(position);
    }

    @Override
    public void stop() {

    }

    @Override
    public void stateUpdate() {

    }

    @Override
    public boolean isDone() {
        return false;
    }
}
