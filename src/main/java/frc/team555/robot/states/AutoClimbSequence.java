package frc.team555.robot.states;

import frc.team555.robot.components.MainLift;
import frc.team555.robot.core.PowerUpRobot;
import org.montclairrobotics.sprocket.auto.states.DriveEncoderGyro;
import org.montclairrobotics.sprocket.auto.states.DriveTime;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.states.StateMachine;

import static frc.team555.robot.components.MainLift.TOP;

public class AutoClimbSequence extends StateMachine {

    public AutoClimbSequence(MainLift lift){
        super(
                new DriveEncoderGyro(10,
                        0.4,
                        Angle.ZERO,
                        true,
                        PowerUpRobot.correction

                ),

                new MoveLift(lift,
                        TOP,
                        1,
                        true
                ),

                new DriveTime(3,-0.5),
                new MoveLift(lift,
                        TOP*0.3,
                        1,
                        false
                )

        );
    }


}
