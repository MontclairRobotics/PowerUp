package frc.team555.robot.states;

import frc.team555.robot.components.CubeIntake;
import frc.team555.robot.components.MainLift;
import frc.team555.robot.utils.Side;
import org.montclairrobotics.sprocket.auto.states.DriveEncoderGyro;
import org.montclairrobotics.sprocket.auto.states.ResetGyro;
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.states.StateMachine;
import org.montclairrobotics.sprocket.utils.Input;

public class TopCubeAuto extends StateMachine {
    public TopCubeAuto(MainLift mainLift, CubeIntake intake, GyroCorrection correction) {
        super(new ResetGyro(correction),
                //new MoveLift(mainLift, MainLift.TOP*0.6,1,true),

                new ConditionalState(new DropCubeTop(intake, correction, new Input<Side>(){
                    @Override
                    public Side get() {
                        return SwitchAuto.startSidesChooser.getSelected();
                    }
                }), SwitchAuto.startSide),
                new ConditionalState(new DriveEncoderGyro(180,0.5,Angle.ZERO,false,correction),new Input<Boolean>(){
                    @Override
                    public Boolean get() {
                        return !SwitchAuto.startSide.get();
                    }
                }));
    }
}
