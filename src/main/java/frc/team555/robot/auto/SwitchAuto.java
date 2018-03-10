package frc.team555.robot.auto;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team555.robot.components.CubeIntake;
import frc.team555.robot.utils.Side;
import org.montclairrobotics.sprocket.auto.states.DriveEncoderGyro;
import org.montclairrobotics.sprocket.auto.states.TurnGyro;
import org.montclairrobotics.sprocket.control.DashboardInput;
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.states.StateMachine;
import org.montclairrobotics.sprocket.utils.Input;

public class SwitchAuto extends StateMachine{

    static Input<Boolean> startSide;
    public static SendableChooser<Side> startSides;

    public static void init(){
        startSides = new SendableChooser<>();
        for(Side side :  Side.values()){
            startSides.addObject(side.toString(), side);
        }
        SmartDashboard.putData(startSides);
        startSide = new Input<Boolean>() {
            @Override
            public Boolean get() {
                return Side.fromDriverStation()[0] == startSides.getSelected();
            }
        };
    }

    public SwitchAuto(GyroCorrection correction, CubeIntake intake, ){
        super(new DriveEncoderGyro(150, .75, Angle.ZERO, false, correction), 
                new ConditionalState(new DropCube(intake, correction, startSides.getSelected()), startSide));
    }
}
