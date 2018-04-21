package frc.team555.robot.auto;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team555.robot.components.CubeIntake;
<<<<<<< HEAD
import frc.team555.robot.components.IntakeLift;
import frc.team555.robot.components.MainLift;
=======
import frc.team555.robot.components.MainLift;
import frc.team555.robot.components.SimpleIntake;
>>>>>>> josh-auto-switch
import frc.team555.robot.core.PowerUpRobot;
import frc.team555.robot.utils.Side;
import org.montclairrobotics.sprocket.auto.states.Delay;
import org.montclairrobotics.sprocket.auto.states.DriveEncoderGyro;
import org.montclairrobotics.sprocket.auto.states.DriveTime;
import org.montclairrobotics.sprocket.auto.states.ResetGyro;
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.states.StateMachine;
import org.montclairrobotics.sprocket.utils.Input;
@Deprecated
public class SwitchAuto extends StateMachine{

    static Input<Boolean> startSide;

    public static void init(){
<<<<<<< HEAD
        startSidesChooser = new SendableChooser<>();
        for(Side side :  Side.values()){
            startSidesChooser.addObject(side.toString(), side);
        }
        //SmartDashboard.putData(startSidesChooser);
=======
>>>>>>> josh-auto-switch
        startSide = new Input<Boolean>() {
            @Override
            public Boolean get() {
                return Side.fromDriverStation()[0] == PowerUpRobot.startSidesChooser.getSelected();
            }
        };
    }

<<<<<<< HEAD
    public static void disabled(){
        SmartDashboard.putData("Start Position", startSidesChooser);
    }

    public static void loop(){
        SmartDashboard.putBoolean("Correct Side", startSide.get());
    }

    public SwitchAuto(MainLift mainLift, GyroCorrection correction, CubeIntake intake){
        super(new ResetGyro(correction),
                new ConditionalState(new DropCube(mainLift,intake, correction, new Input<Side>(){
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
=======
    public static Boolean getStartSide(){
        return startSide.get();
    }

    public SwitchAuto(GyroCorrection correction, CubeIntake intake, MainLift lift){
        super(new ResetGyro(correction),
                new DriveEncoderGyro(150, .75, Angle.ZERO, false, correction));
                // new ConditionalState(new DropCube(intake, lift, PowerUpRobot.startSidesChooser.getSelected(), startSide));
>>>>>>> josh-auto-switch
    }
}
