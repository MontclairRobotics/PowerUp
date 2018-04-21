package frc.team555.robot.auto;

import frc.team555.robot.utils.Side;
import org.montclairrobotics.sprocket.auto.AutoMode;
import org.montclairrobotics.sprocket.states.State;

public abstract class AutoSwitch extends AutoMode {
    public AutoSwitch(String name, State... states) {
        super(name, states);
    }

    public static AutoSwitch fromMiddle() {
        return new AutoSwitchMid(Side.fromDriverStation()[0]);
    }

    public static AutoSwitch fromSide(Side pos) {
        return new AutoSwitchLR(pos, Side.fromDriverStation()[0]);
    }
}
