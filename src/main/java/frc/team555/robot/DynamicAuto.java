package frc.team555.robot;

import edu.wpi.first.wpilibj.DriverStation;
import org.montclairrobotics.sprocket.auto.AutoMode;
import org.montclairrobotics.sprocket.auto.states.DriveTime;
import org.montclairrobotics.sprocket.states.State;
import org.montclairrobotics.sprocket.states.StateMachine;

import java.util.ArrayList;

public class DynamicAuto implements State {
    ArrayList<State> states = new ArrayList<>();
    AutoMode rightAuto;


    @Override
    public void start() {
        switch (DriverStationInput.sides[0]){
            case RIGHT:
                states.add(new DriveTime(1, .1));
                break;
            case LEFT:
                states.add(new DriveTime(1, -.1));
                break;
        }

        rightAuto = new AutoMode("Right Auto", (State[])states.toArray());
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
