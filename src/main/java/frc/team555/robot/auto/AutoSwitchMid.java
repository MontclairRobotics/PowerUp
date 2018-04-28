package frc.team555.robot.auto;

import frc.team555.robot.core.PowerUpRobot;
import frc.team555.robot.utils.Side;
import org.montclairrobotics.sprocket.auto.states.DriveEncoderGyro;
import org.montclairrobotics.sprocket.auto.states.ResetGyro;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.utils.Input;
/*
class AutoSwitchMid extends AutoSwitch {

    AutoSwitchMid(Side target) {
        super("Cube to " + target.toString() + " Switch (M)",
                /* Reset gyro
                new ResetGyro(PowerUpRobot.correction),
                /* Drive forward 12 inches
                new DriveEncoderGyro(12, POWER, Angle.ZERO, true, PowerUpRobot.correction),
                /* Turn π/2 radians (in the correct directon)
                new DriveEncoderGyro(0,(target == Side.RIGHT ? +1 : -1) * POWER, Angle.QUARTER, true, PowerUpRobot.correction),
                /* Drive forward 36 inches
                new DriveEncoderGyro(36, POWER, Angle.ZERO, true, PowerUpRobot.correction),
                /* Move the lift, turn, and drop the cube into the switch
                new DropCubeSwitch(new Input<Side>(){public Side get(){return target;}}, PowerUpRobot.correction)
        );
    }
}*/
