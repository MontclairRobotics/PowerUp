package frc.team555.robot.auto;

import frc.team555.robot.components.CubeIntake;
import frc.team555.robot.utils.Side;
import org.montclairrobotics.sprocket.auto.states.DriveEncoderGyro;
import org.montclairrobotics.sprocket.auto.states.DriveTime;
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.states.StateMachine;
import org.montclairrobotics.sprocket.utils.Input;

public class DropCubeTop extends StateMachine {
    public DropCubeTop(CubeIntake intake, GyroCorrection correction, Input<Side> side){
        super(
                new DriveEncoderGyro(140, .5, Angle.ZERO, false, correction),
                new SideTurn(correction, false, side),
                // new MainLiftMove(12,.5,true),
                new DriveTime(2, .75)
        );
    }
}
