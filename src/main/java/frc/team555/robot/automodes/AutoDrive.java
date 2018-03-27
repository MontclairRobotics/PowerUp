package frc.team555.robot.automodes;

import org.montclairrobotics.sprocket.auto.AutoMode;
import org.montclairrobotics.sprocket.auto.states.DriveEncoderGyro;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.states.State;

import static frc.team555.robot.core.PowerUpRobot.correction;

public class AutoDrive extends AutoMode {

    public AutoDrive() {
        super("Auto Drive",
                new DriveEncoderGyro(120,
                        0.25,
                        Angle.ZERO,
                        true,
                        correction)
        );

    }
}
