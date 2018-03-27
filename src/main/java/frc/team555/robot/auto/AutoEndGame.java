package frc.team555.robot.auto;

import frc.team555.robot.components.MainLift;
import frc.team555.robot.core.PowerUpRobot;
import org.montclairrobotics.sprocket.auto.states.DriveEncoderGyro;
import org.montclairrobotics.sprocket.geometry.*;
import org.montclairrobotics.sprocket.states.StateMachine;


public class AutoEndGame extends StateMachine{

    public AutoEndGame(MainLift lift){
        super(
                new DriveEncoderGyro(
                        Math.sqrt(Math.pow(PowerUpRobot.position.getX()-161.69,2)+Math.pow(PowerUpRobot.position.getY()-256,2)),
                        1,
                        new Radians(Math.atan2(PowerUpRobot.position.getY()-256, PowerUpRobot.position.getX()-161.69)),
                        true,
                        PowerUpRobot.correction),

                new DriveEncoderGyro(
                        60,
                        1,
                        Angle.ZERO,
                        false,
                        PowerUpRobot.correction),

                new AutoClimbSequence(lift)
        );
    }
}
