package frc.team555.robot.stateMachines.automodes;

import org.montclairrobotics.sprocket.auto.AutoMode;
import org.montclairrobotics.sprocket.auto.states.DriveEncoderGyro;
import org.montclairrobotics.sprocket.geometry.Angle;

import static frc.team555.robot.core.PowerUpRobot.correction;

public class BaseLineLR extends AutoMode {
    public BaseLineLR() {
        super("Base Line LR",
                new DriveEncoderGyro(
                        120,
                        1,
                        Angle.ZERO,
                        false,
                        correction)
        );
    }
}
