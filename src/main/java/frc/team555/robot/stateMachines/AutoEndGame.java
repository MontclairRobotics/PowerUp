package frc.team555.robot.stateMachines;

import frc.team555.robot.components.MainLift;
import org.montclairrobotics.sprocket.auto.states.DriveEncoderGyro;
import org.montclairrobotics.sprocket.geometry.*;
import org.montclairrobotics.sprocket.states.StateMachine;

import static frc.team555.robot.core.PowerUpRobot.correction;
import static frc.team555.robot.core.PowerUpRobot.position;


public class AutoEndGame extends StateMachine{

    public AutoEndGame(MainLift lift){
        super(
                new DriveEncoderGyro(
                        Math.sqrt(Math.pow(position.getX()-161.69,2)+Math.pow(position.getY()-256,2)),
                        1,
                        new Radians(Math.atan2(position.getY()-256, position.getX()-161.69)),
                        true,
                        correction),

                new DriveEncoderGyro(
                        60,
                        1,
                        Angle.ZERO,
                        false,
                        correction),

                new AutoClimbSequence(lift)
        );
    }
}
