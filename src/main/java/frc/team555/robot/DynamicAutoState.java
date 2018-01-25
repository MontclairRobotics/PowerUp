package frc.team555.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.montclairrobotics.sprocket.auto.AutoMode;
import org.montclairrobotics.sprocket.auto.states.DriveTime;
import org.montclairrobotics.sprocket.auto.states.Enable;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.states.State;
import org.montclairrobotics.sprocket.states.StateMachine;
import org.montclairrobotics.sprocket.utils.Debug;

import java.util.ArrayList;
import java.util.Arrays;

public class DynamicAutoState implements State {
    ArrayList<State> states = new ArrayList<State>();
    StateMachine rightAuto;


    @Override
    public void start() {
        SmartDashboard.putString("I got here","Yes I did");
        switch (Side.fromDriverStation()[0]){
            case RIGHT:
                states.add(new DriveTime(1, .1));
                Debug.msg("Value","Right");
                break;
            case LEFT:
                states.add(new DriveTime(1, -.1));
                Debug.msg("Value", "Left");
                break;
        }
        int stateSize = states.size();
        rightAuto = new StateMachine(false,  states.toArray(new State[stateSize]));
        states = new ArrayList<State>();
        rightAuto.start();
    }

    @Override
    public void stop() {
        rightAuto.stop();
    }

    @Override
    public void stateUpdate() {
        rightAuto.update();
    }

    @Override
    public boolean isDone() {
        return rightAuto.isDone();
    }
}
