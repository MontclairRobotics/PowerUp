package frc.team555.robot.auto;

import frc.team555.robot.components.CubeIntake;
import org.montclairrobotics.sprocket.states.State;

@Deprecated
public class SetIntakeRotation implements State {
    int position;
    CubeIntake intake;

    public SetIntakeRotation(CubeIntake intake, int position) {
        this.position = position;
        this.intake = intake;
    }

    @Override
    public void start() {
<<<<<<< HEAD
        // intake.roationalMotor.setTarget(position);
=======
        intake.rotationalMotor.setTarget(position);
>>>>>>> josh-auto-switch
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
