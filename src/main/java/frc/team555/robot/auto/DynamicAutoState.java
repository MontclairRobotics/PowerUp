package frc.team555.robot.auto;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.montclairrobotics.sprocket.auto.AutoMode;
import org.montclairrobotics.sprocket.auto.states.*;
import org.montclairrobotics.sprocket.geometry.Degrees;
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
        /*
        switch (Side.fromDriverStation()[0]){
            case RIGHT:
                states.add(new DriveTime(1, .25));
                Debug.msg("Value","Right");
                break;
            case LEFT:
                states.add(new DriveTime(1, -.25));
                Debug.msg("Value", "Left");
                break;
        }*/
        double selection = SmartDashboard.getNumber("auto Selection", 0);
        if (selection == 1) {
            states.add(new DriveEncoders(100, .25));
        }else if(selection == 2){
            states.add(new DriveEncoders(-100, .25));
        }else{
            states.add(new TurnEncoders(new Degrees(180), new Degrees(90)));
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
