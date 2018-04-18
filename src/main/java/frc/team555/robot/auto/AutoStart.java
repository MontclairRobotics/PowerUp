package frc.team555.robot.auto;

import org.montclairrobotics.sprocket.auto.states.ResetGyro;
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.states.StateMachine;

public class AutoStart extends StateMachine {
    public AutoStart(GyroCorrection correction){
        super(new ResetGyro(correction));
    }
}
