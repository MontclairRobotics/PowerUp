package frc.team555.robot.auto;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team555.robot.components.CubeIntake;
import frc.team555.robot.utils.Side;
import org.montclairrobotics.sprocket.auto.states.DriveEncoderGyro;
import org.montclairrobotics.sprocket.auto.states.ResetGyro;
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.states.StateMachine;
import org.montclairrobotics.sprocket.utils.Input;

public class SwitchAuto extends StateMachine{

    static Input<Boolean> startSide;
    public static SendableChooser<Side> startSidesChooser;

    public static void init(){
        startSidesChooser = new SendableChooser<>();
        for(Side side :  Side.values()){
            startSidesChooser.addObject(side.toString(), side);
        }
        SmartDashboard.putData(startSidesChooser);
        startSide = new Input<Boolean>() {
            @Override
            public Boolean get() {
                return Side.fromDriverStation()[0] == startSidesChooser.getSelected();
            }
        };
    }

    public static void disabled(){
        SmartDashboard.putData(startSidesChooser);
    }

    public SwitchAuto(GyroCorrection correction, CubeIntake intake){
        super(new ResetGyro(correction),
                new DriveEncoderGyro(150, .75, Angle.ZERO, false, correction));
                // new ConditionalState(new DropCube(intake, correction, startSidesChooser.getSelected()), startSide));
    }
}
