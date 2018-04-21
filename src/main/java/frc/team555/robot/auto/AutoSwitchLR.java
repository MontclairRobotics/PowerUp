package frc.team555.robot.auto;

import frc.team555.robot.core.PowerUpRobot;
import frc.team555.robot.utils.Side;
import org.montclairrobotics.sprocket.auto.states.DriveEncoderGyro;
import org.montclairrobotics.sprocket.auto.states.ResetGyro;
import org.montclairrobotics.sprocket.geometry.Angle;

class AutoSwitchLR extends AutoSwitch {
    AutoSwitchLR(Side pos, Side target) {
<<<<<<< HEAD
        super("Cube to " + target.toString() + " Switch (" + pos.toString() + ")"

=======
        super("Cube to " + target.toString() + " Switch (" + pos.toString() + ")",
                /* Reset gyro */
                new ResetGyro(PowerUpRobot.correction),
                /* Drive forward 12 inches */
                new DriveEncoderGyro(12, POWER, Angle.ZERO, true, PowerUpRobot.correction),
                /* Turn π/2 radians (toward the nearest wall) */
                new DriveEncoderGyro(0,(pos == Side.RIGHT ? +1 : -1) * POWER, Angle.QUARTER,true, PowerUpRobot.correction),
                /* Drive forward 36 inches */
                new DriveEncoderGyro(36, POWER, Angle.ZERO, true, PowerUpRobot.correction),
                /* Turn π/2 radians (in the opposite direction) */
                new DriveEncoderGyro(0,(pos == Side.LEFT ? +1 : -1) * POWER, Angle.QUARTER,true, PowerUpRobot.correction),
                /* Drive forward 36 inches */
                new DriveEncoderGyro(36, POWER, Angle.ZERO, true, PowerUpRobot.correction),
                /* Turn π/2 radians (in the opposite direction) */
                new DriveEncoderGyro(0,(pos == Side.LEFT ? +1 : -1) * POWER, Angle.QUARTER,true, PowerUpRobot.correction),
                /* Drive forward 12 inches OR 72 inches, depending on the target */
                new DriveEncoderGyro(pos == target ? 12 : 72, POWER, Angle.ZERO, true, PowerUpRobot.correction),
                /* Move the lift, turn, and drop the cube into the switch */
                new DropCubeSwitch(pos, PowerUpRobot.correction)
>>>>>>> 5d54d05487e29bdc2cabd1be43e64b5723d14495
        );
    }
}
