package frc.team555.robot.auto;

import frc.team555.robot.components.IntakeLift;
import frc.team555.robot.core.Hardware;
import frc.team555.robot.core.PowerUpRobot;
import frc.team555.robot.utils.Side;
import org.montclairrobotics.sprocket.auto.states.DriveEncoderGyro;
import org.montclairrobotics.sprocket.auto.states.DriveEncoderLock;
import org.montclairrobotics.sprocket.auto.states.ResetGyro;
import org.montclairrobotics.sprocket.geometry.Angle;

class AutoSwitchMid extends AutoSwitch {

    AutoSwitchMid(Side target) {
        super("Cube to " + target.toString() + " Switch (M)",
                /* Reset gyro */
                new ResetGyro(PowerUpRobot.correction),
                /* Drive forward 12 inches */
                new DriveEncoderGyro(12, 0.75, Angle.ZERO, true, PowerUpRobot.correction),
                /* Turn π/2 radians (in the correct directon) */
                new DriveEncoderGyro(0,(target == Side.RIGHT ? +1 : -1) * 0.75, Angle.QUARTER, true, PowerUpRobot.correction),
                /* Drive forward 36 inches */
                new DriveEncoderGyro(36, 0.75, Angle.ZERO, true, PowerUpRobot.correction),
                /* Move intake lift up 8 inches */
                new IntakeLiftMove(8, 0.75, true),
                /* Turn π/2 radians (in the opposite direction) */
                new DropCubeSwitch(target, PowerUpRobot.correction)
        );
    }
}
