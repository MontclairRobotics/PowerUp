package frc.team555.robot.auto;

import frc.team555.robot.utils.Side;
import org.montclairrobotics.sprocket.states.State;

class AutoSwitchLR extends AutoSwitch {
    AutoSwitchLR(Side pos, Side target) {
        super("Cube to " + target.toString() + " Switch (" + pos.toString() + ")",

                );
    }
}
