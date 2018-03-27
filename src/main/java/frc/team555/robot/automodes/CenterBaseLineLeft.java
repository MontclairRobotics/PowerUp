package frc.team555.robot.automodes;

import org.montclairrobotics.sprocket.auto.AutoMode;
import org.montclairrobotics.sprocket.auto.states.DriveEncoderGyro;
import org.montclairrobotics.sprocket.auto.states.ResetGyro;
import org.montclairrobotics.sprocket.geometry.Degrees;

import static frc.team555.robot.core.PowerUpRobot.correction;

public class CenterBaseLineLeft extends AutoMode {

    public CenterBaseLineLeft() {
        super("Center Base Line Left",
                new ResetGyro(correction),
                new DriveEncoderGyro(60, .5, new Degrees(0), false, correction),
                new DriveEncoderGyro(12, .5, new Degrees(-90), false, correction),
                new DriveEncoderGyro(80, .5, new Degrees(0), false, correction)
        );
    }
}
