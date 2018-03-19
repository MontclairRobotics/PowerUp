package frc.team555.robot.auto;

import frc.team555.robot.components.CubeIntake;
import frc.team555.robot.components.IntakeLift;
import frc.team555.robot.utils.Side;
import org.montclairrobotics.sprocket.auto.states.DriveEncoderGyro;
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.states.MultiState;
import org.montclairrobotics.sprocket.states.State;
import org.montclairrobotics.sprocket.states.StateMachine;

public class DropCubeSwitch extends StateMachine {

    private CubeIntake intake;
    private IntakeLift lift;

    public DropCubeSwitch(CubeIntake intake, IntakeLift lift, Side side, GyroCorrection correction){
        super(new MultiState(0,
                new SideTurn(correction,false, side),
                new IntakeLiftMove(10, .75, true)
        ),
                new DriveEncoderGyro(3, .5, Angle.ZERO, true, correction),
                new CubeOuttake(intake, 1));
    }
}
