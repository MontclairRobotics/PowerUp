package frc.team555.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.montclairrobotics.sprocket.auto.states.Delay;
import org.montclairrobotics.sprocket.auto.states.Disable;
import org.montclairrobotics.sprocket.auto.states.DriveEncoderGyro;
import org.montclairrobotics.sprocket.auto.states.Enable;
import org.montclairrobotics.sprocket.auto.states.TurnGyro;
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.states.State;
import org.montclairrobotics.sprocket.states.StateMachine;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.PID;

import java.util.ArrayList;

public class LeftAuto implements State {
    CubeIntake intake;
    Lift lift;
    StateMachine rightAuto;
    ArrayList<State> states = new ArrayList<State>();
    Input<Boolean> crossover;
    Input<Boolean> prioritizeScale;
    GyroCorrection correction;


    public LeftAuto(CubeIntake intake, Lift lift){
        this.intake = intake;
        this.lift = lift;
        correction = new GyroCorrection(Hardware.navx, new PID(1.5, 0, 0.0015), 90, 1);


        SmartDashboard.putBoolean("Crossover", false);
        SmartDashboard.putBoolean("Prioritize Scale", false);

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
        if(Side.fromDriverStation()[0] == Side.LEFT){
            states.add(new DriveEncoderGyro(168, .75, Angle.ZERO, false, correction));
            states.add(new TurnGyro(new Degrees(-90), correction, false));
            //states.add(new Enable(intake));
            //states.add(new Delay(1));
            //states.add(new Disable(intake));
        }else if(crossover.get()){
            states.add(new DriveEncoderGyro(100, .75, Angle.ZERO, false, correction));
            states.add(new DriveEncoderGyro(150, .75, new Degrees(-90), false, correction));
            states.add(new DriveEncoderGyro(68, .75, Angle.ZERO, false, correction));
            states.add(new TurnGyro(new Degrees(90), correction, false));
            //states.add(new Enable(intake));
            //states.add(new Delay(1));
            //states.add(new Disable(intake));
        }else if(Side.fromDriverStation()[1] == Side.LEFT){
            states.add(new DriveEncoderGyro(300, .75, Angle.ZERO, false, correction));
            //states.add(new LiftState(100));
            //states.add(new Enable(intake));
            //states.add(new Delay(1));
            //states.add(new Disable(intake));
        }else{
            states.add(new DriveEncoderGyro(100, .75, Angle.ZERO, false, correction));
            states.add(new DriveEncoderGyro(150, .75, new Degrees(90), false, correction));
            states.add(new DriveEncoderGyro(200, .75, Angle.ZERO, false, correction));
            //states.add(new LiftState(100));
            //states.add(new Enable(intake));
            //states.add(new Delay(1));
            //states.add(new Disable(intake));
        }
    }

    @Override
    public void stop() {

    }

    @Override
    public void stateUpdate() {

    }

    @Override
    public boolean isDone() {
        return false;
    }
}