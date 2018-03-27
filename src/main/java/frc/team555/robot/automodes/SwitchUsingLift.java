package frc.team555.robot.automodes;

import frc.team555.robot.states.TopCubeAuto;
import org.montclairrobotics.sprocket.auto.AutoMode;

import static frc.team555.robot.core.PowerUpRobot.correction;
import static frc.team555.robot.core.PowerUpRobot.intake;
import static frc.team555.robot.core.PowerUpRobot.mainLift;

public class SwitchUsingLift extends AutoMode {

    public SwitchUsingLift(){
        super("Switch Using Lift", new TopCubeAuto(mainLift, intake, correction)
        );
    }
}
