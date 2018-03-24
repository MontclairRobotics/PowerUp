package frc.team555.robot.auto;

import frc.team555.robot.components.CubeIntake;
import frc.team555.robot.components.MainLift;
import org.montclairrobotics.sprocket.auto.AutoMode;
import org.montclairrobotics.sprocket.auto.states.DriveEncoderGyro;
import org.montclairrobotics.sprocket.auto.states.ResetGyro;
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.states.State;
import org.montclairrobotics.sprocket.states.StateMachine;

public class TopCubeAuto extends StateMachine {
    public TopCubeAuto(MainLift mainLift, CubeIntake intake, GyroCorrection correction) {
        super(new ResetGyro(correction),
                //new MoveLift(mainLift, MainLift.TOP*0.6,1,true),
                new DriveEncoderGyro(150, .1, Angle.ZERO, false, correction),
                new ConditionalState(new DropCubeTop(intake, correction, SwitchAuto.startSidesChooser.getSelected()), SwitchAuto.startSide));
    }
}
