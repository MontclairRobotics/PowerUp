package frc.team555.robot.auto;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team555.robot.components.Lift;
import frc.team555.robot.components.MainLift;
import frc.team555.robot.core.PowerUpRobot;
import org.montclairrobotics.sprocket.auto.AutoMode;
import org.montclairrobotics.sprocket.auto.states.DriveEncoderGyro;
import org.montclairrobotics.sprocket.auto.states.DriveTime;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.states.State;
import org.montclairrobotics.sprocket.states.StateMachine;

import static frc.team555.robot.components.MainLift.TOP;
@Deprecated
public class AutoClimbSequence extends StateMachine {

    private Lift lift;
    private double height;
    private double power;
    private boolean up;

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

    State MoveLift = new State() {
        @Override
        public void start() {
            power=Math.abs(power);
            if(!up)
            {
                power=-power;
            }
        }

        @Override
        public void stop()
        {
            lift.setAuto(0);
        }

        @Override
        public void stateUpdate() {
            lift.setAuto(power);
        }

        @Override
        public boolean isDone() {
            return lift.getEncoder().getInches().get()>height==up;
        }
    };


}
