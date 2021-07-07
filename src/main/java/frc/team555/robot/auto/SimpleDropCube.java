package frc.team555.robot.auto;

import frc.team555.robot.components.CubeIntake;
import frc.team555.robot.components.MainLift;
import frc.team555.robot.utils.Side;
import org.montclairrobotics.sprocket.auto.AutoState;
import org.montclairrobotics.sprocket.auto.states.DriveEncoderGyro;
import org.montclairrobotics.sprocket.auto.states.DriveTime;
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.states.MultiState;
import org.montclairrobotics.sprocket.states.State;
import org.montclairrobotics.sprocket.states.StateMachine;
import org.montclairrobotics.sprocket.utils.Input;


public class SimpleDropCube extends StateMachine{
    public SimpleDropCube(MainLift mainLift, CubeIntake intake, GyroCorrection correction, Input<Side> side){
        super(
                new MultiState(
                    new MoveLift(mainLift,MainLift.TOP*0.5,1,true),
                    new DriveEncoderGyro(140, .7, Angle.ZERO, false, correction)
                ),
                new SideTurn(correction, false, side),
                // new MainLiftMove(12,.5,true),
                new DriveTime(1, .5),
                new AutoState() {
                    @Override
                    public void stateUpdate() {
                        intake.setAutoPower(.3);
                    }

                    @Override
                    public boolean isDone() {
                        return super.timeInState()>4;
                    }
                }
        );
    }
}
