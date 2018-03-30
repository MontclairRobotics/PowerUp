package frc.team555.robot.stateMachines.automodes;

import org.montclairrobotics.sprocket.auto.AutoMode;
import org.montclairrobotics.sprocket.auto.states.DriveEncoderGyro;
import org.montclairrobotics.sprocket.auto.states.ResetGyro;
import org.montclairrobotics.sprocket.geometry.Degrees;

import static frc.team555.robot.core.PowerUpRobot.correction;

public class CenterBaseLineRight extends AutoMode{

    public CenterBaseLineRight(){
        super("Center Base Line Right",
                new ResetGyro(correction),
                new DriveEncoderGyro(60, .5, new Degrees(0), false, correction),
                new DriveEncoderGyro(122, .5, new Degrees(90), false, correction),
                new DriveEncoderGyro(80, .5, new Degrees(0), false, correction)
        );
    }
}
