package frc.team555.robot.auto;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team555.robot.components.CubeIntake;
import frc.team555.robot.components.IntakeLift;
import frc.team555.robot.utils.Side;
import org.montclairrobotics.sprocket.auto.states.*;
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.states.State;
import org.montclairrobotics.sprocket.states.StateMachine;
import org.montclairrobotics.sprocket.utils.Debug;
import org.montclairrobotics.sprocket.utils.Input;

import java.util.ArrayList;

@Deprecated
public class OldSideAuto implements State {
    CubeIntake intake;
    IntakeLift intakeLift;
    StateMachine machine;
    ArrayList<State> states = new ArrayList<State>();
    Input<Boolean> crossover;
    Input<Boolean> prioritizeScale;
    GyroCorrection correction;


    public OldSideAuto(CubeIntake intake, IntakeLift intakeLift, GyroCorrection gyroCorrection){
        this.intake = intake;
        this.intakeLift = intakeLift;
        correction = gyroCorrection;


        SmartDashboard.putBoolean("Crossover", false);
        SmartDashboard.putBoolean("Prioritize Scale", false);

        //SmartDashboard.putData(new SendableChooser<Side>().addObject().addObject(Side.LEFT););

        crossover = new Input<Boolean>() {
            @Override
            public Boolean get() {
                return SmartDashboard.getBoolean("Crossover", false);
            }
        };

        prioritizeScale = new Input<Boolean>() {
            @Override
            public Boolean get() {
                return SmartDashboard.getBoolean("Prioritize Scale", false);
            }
        };
    }


    @Override
    public void start() {
        states.add(new ResetGyro(correction));
        states.add(new SetIntakeRotation(intake, intake.downPos));

        if(Side.fromDriverStation()[0] == Side.RIGHT){
            states.add(new DriveEncoderGyro(168, .75, Angle.ZERO, false, correction));
            states.add(new TurnGyro(new Degrees(90), correction, false));
            Debug.msg("Target", "RightSwitch");
            //states.add(new Enable(intake));
            //states.add(new Delay(1));
            //states.add(new Disable(intake));
        }else if(crossover.get()){
        	states.add(new DriveEncoderGyro(100, .75, Angle.ZERO, false, correction));
        	states.add(new DriveEncoderGyro(150, .75, new Degrees(90), false, correction));
        	states.add(new DriveEncoderGyro(68, .75, Angle.ZERO, false, correction));
            states.add(new TurnGyro(new Degrees(-90), correction, false));
        	states.add(new Enable(intake));
            states.add(new Delay(1));
            states.add(new Disable(intake));
        }else if(Side.fromDriverStation()[1] == Side.RIGHT){
            Debug.msg("Target", "RightScale");
        	states.add(new DriveEncoderGyro(300, .75, Angle.ZERO, false, correction));
            states.add(new TurnGyro(new Degrees(90), correction, false));
        	states.add(new LiftState(4)); // Todo: make sure right state/position
            states.add(new Enable(intake));
            states.add(new Delay(1));
            states.add(new Disable(intake));
        }else{
        	states.add(new DriveEncoderGyro(100, .75, Angle.ZERO, false, correction));
        	states.add(new DriveEncoderGyro(150, .75, new Degrees(90), false, correction));
        	states.add(new DriveEncoderGyro(200, .75, Angle.ZERO, false, correction));
            states.add(new TurnGyro(new Degrees(-90), correction, false));
            states.add(new LiftState(4)); // Todo: make sure right state/position
            states.add(new Enable(intake));
            states.add(new Delay(1));
            states.add(new Disable(intake));
        }
        int stateSize = states.size();
        machine = new StateMachine(false, states.toArray(new State[stateSize]));
        states = new ArrayList<State>();
        machine.start();
    }

    @Override
    public void stop() {
        machine.stop();
    }

    @Override
    public void stateUpdate() {
        machine.update();
    }

    @Override
    public boolean isDone() {
        return machine.isDone();
    }
}
