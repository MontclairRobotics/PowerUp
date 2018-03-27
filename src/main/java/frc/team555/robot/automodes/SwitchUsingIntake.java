package frc.team555.robot.automodes;

import frc.team555.robot.states.SwitchAuto;
import org.montclairrobotics.sprocket.auto.AutoMode;

import static frc.team555.robot.core.PowerUpRobot.*;

public class SwitchUsingIntake extends AutoMode {

    public SwitchUsingIntake(){
        super("Switch Using Intake",
                new SwitchAuto(mainLift,correction, intake, intakeLift)
        );
    }
}
