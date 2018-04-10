package frc.team555.robot.auto;

import frc.team555.robot.utils.Side;
import org.montclairrobotics.sprocket.states.State;

class AutoSwitchMid extends AutoSwitch {
    AutoSwitchMid(Side target) {
        super("Cube to " + target.toString() + " Switch (M)",
                );
    }
}
