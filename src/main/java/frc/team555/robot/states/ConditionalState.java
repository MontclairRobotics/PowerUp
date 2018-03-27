package frc.team555.robot.states;

import org.montclairrobotics.sprocket.states.State;
import org.montclairrobotics.sprocket.utils.Input;

public class ConditionalState implements State {

    State state;
    Input<Boolean> conditional;

    public ConditionalState(State state, Input<Boolean> conditional){
        this.state = state;
        this.conditional = conditional;
    }

    @Override
    public void start() {
        if(conditional.get()){
            state.start();
        }
    }

    @Override
    public void stop() {
        if(conditional.get()){
            state.stop();
        }
    }

    @Override
    public void stateUpdate() {
        if(conditional.get()){
            state.stateUpdate();
        }
    }

    @Override
    public boolean isDone() {
        if(conditional.get()){
            return state.isDone();
        }else {
            return true;
        }
    }
}
