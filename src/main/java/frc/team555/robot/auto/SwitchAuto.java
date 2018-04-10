package frc.team555.robot.auto;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team555.robot.components.CubeIntake;
import frc.team555.robot.components.MainLift;
import frc.team555.robot.components.SimpleIntake;
import frc.team555.robot.core.PowerUpRobot;
import frc.team555.robot.utils.Side;
import org.montclairrobotics.sprocket.auto.states.DriveEncoderGyro;
import org.montclairrobotics.sprocket.auto.states.ResetGyro;
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.states.StateMachine;
import org.montclairrobotics.sprocket.utils.Input;
@Deprecated
public class SwitchAuto extends StateMachine{

    static Input<Boolean> startSide;

    public static void init(){
        startSide = new Input<Boolean>() {
            @Override
            public Boolean get() {
                return Side.fromDriverStation()[0] == PowerUpRobot.startSidesChooser.getSelected();
            }
        };
    }

    public static Boolean getStartSide(){
        return startSide.get();
    }

    public SwitchAuto(GyroCorrection correction, CubeIntake intake, MainLift lift){
        super(new ResetGyro(correction),
                new DriveEncoderGyro(150, .75, Angle.ZERO, false, correction));
                // new ConditionalState(new DropCube(intake, lift, PowerUpRobot.startSidesChooser.getSelected(), startSide));
    }
}
