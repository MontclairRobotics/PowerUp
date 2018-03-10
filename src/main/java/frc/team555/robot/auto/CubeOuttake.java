package frc.team555.robot.auto;

import frc.team555.robot.components.CubeIntake;
import org.montclairrobotics.sprocket.auto.states.Delay;
import org.montclairrobotics.sprocket.auto.states.Disable;
import org.montclairrobotics.sprocket.auto.states.Enable;
import org.montclairrobotics.sprocket.states.StateMachine;

public class CubeOuttake extends StateMachine {

    public CubeOuttake(CubeIntake intake, double time){
        super(
                new Enable(intake),
                new Delay(time),
                new Disable(intake)
        );
    }
}
