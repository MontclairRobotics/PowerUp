package frc.team555.robot.states;

import frc.team555.robot.components.CubeIntake;
import org.montclairrobotics.sprocket.states.State;

public class SetIntakeRotation implements State {
    int position;
    CubeIntake intake;

    public SetIntakeRotation(CubeIntake intake, int position) {
        this.position = position;
        this.intake = intake;
    }

    @Override
    public void start() {
        intake.roationalMotor.setTarget(position);
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
