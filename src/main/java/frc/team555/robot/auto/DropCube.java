package frc.team555.robot.auto;

import frc.team555.robot.components.CubeIntake;
import frc.team555.robot.components.MainLift;
import frc.team555.robot.utils.Side;
import org.montclairrobotics.sprocket.auto.states.DriveTime;
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.states.StateMachine;

public class DropCube extends StateMachine{
    public DropCube(CubeIntake intake, MainLift lift, GyroCorrection correction, Side side){
        super(
                new SideTurn(correction, false, side),
                new MainLiftMove(12,.5,true),
                new DriveTime(3,.3),
                new CubeOuttake(intake, 5)
        );
    }
}
