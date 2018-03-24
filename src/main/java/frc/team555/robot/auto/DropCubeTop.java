package frc.team555.robot.auto;

import frc.team555.robot.components.CubeIntake;
import frc.team555.robot.utils.Side;
import org.montclairrobotics.sprocket.auto.AutoState;
import org.montclairrobotics.sprocket.auto.states.DriveTime;
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.states.StateMachine;

public class DropCubeTop extends StateMachine {
    public DropCubeTop(CubeIntake intake, GyroCorrection correction, Side side){
        super(
                new SideTurn(correction, false, side),
                // new MainLiftMove(12,.5,true),
                new DriveTime(3, .9)
        );
    }
}
