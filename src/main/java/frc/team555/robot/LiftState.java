package frc.team555.robot;

import org.montclairrobotics.sprocket.auto.AutoState;

public class LiftState extends AutoState {

    Lift lift;
    int position;

    public LiftState(int position){
        this.position = position;
    }
    @Override
    public void stateUpdate() {

        lift.setPosition(position);

        isDone();

    }

    @Override
    public boolean isDone() { return true; }
}
