package frc.team555.robot.auto;

import frc.team555.robot.components.CubeIntake;
import frc.team555.robot.utils.Side;
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.states.StateMachine;

public class DropCube extends StateMachine{
    public DropCube(CubeIntake intake, GyroCorrection correction, Side side){
        super(
                new SideTurn(correction, true, side),
                new CubeOuttake(intake, 5)
        );
    }
}
