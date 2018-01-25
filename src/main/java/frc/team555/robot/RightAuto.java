package frc.team555.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.montclairrobotics.sprocket.auto.AutoState;
import org.montclairrobotics.sprocket.auto.states.Delay;
import org.montclairrobotics.sprocket.auto.states.Disable;
import org.montclairrobotics.sprocket.auto.states.Enable;
import org.montclairrobotics.sprocket.states.State;
import org.montclairrobotics.sprocket.states.StateMachine;
import org.montclairrobotics.sprocket.utils.Input;

import java.util.ArrayList;

public class RightAuto implements State {
    CubeIntake intake;
    Lift lift;
    StateMachine rightAuto;
    ArrayList<State> states = new ArrayList<State>();
    Input<Boolean> crossover;
    Input<Boolean> prioritizeScale;


    public RightAuto(CubeIntake intake, Lift lift){
        this.intake = intake;
        this.lift = lift;

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
        if(Side.fromDriverStation()[0] == Side.RIGHT){
            // Todo: drive to the right switch
            states.add(new Enable(intake));
            states.add(new Delay(1));
            states.add(new Disable(intake));
        }else if(crossover.get()){
            // Todo: drive to the left switch
            states.add(new Enable(intake));
            states.add(new Delay(1));
            states.add(new Disable(intake));
        }else if(Side.fromDriverStation()[1] == Side.RIGHT){
            // Todo: drive to the right scale
            // Todo: Lift Code
        }else{
            // Todo: Drive to the left scale
            // Todo: Lift Code
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
