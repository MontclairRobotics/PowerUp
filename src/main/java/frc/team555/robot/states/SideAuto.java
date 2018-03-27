package frc.team555.robot.states;

import frc.team555.robot.components.CubeIntake;
import frc.team555.robot.components.IntakeLift;
import frc.team555.robot.utils.Side;
import org.montclairrobotics.sprocket.states.StateMachine;

@Deprecated
public class SideAuto extends StateMachine {

    CubeIntake intake;
    IntakeLift lift;
    Side side;

    public SideAuto(Side side){

    }


    @Override
    public void start() {

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
