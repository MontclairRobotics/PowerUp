package frc.team555.robot.auto;

import frc.team555.robot.PettingZooRobot;
import frc.team555.robot.core.PowerUpRobot;
import org.montclairrobotics.sprocket.auto.states.Delay;
import org.montclairrobotics.sprocket.auto.states.DriveEncoderGyro;
import org.montclairrobotics.sprocket.auto.states.ResetGyro;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.auto.AutoMode;
import org.montclairrobotics.sprocket.states.State;

import java.util.List;
import java.util.ArrayList;

public class PettingAutoMode extends AutoMode {
    private static double POW  = 0.50;
    private static double DIST = 36.0;

    private PettingZooRobot robot;

    public PettingAutoMode(PettingZooRobot r) {
        super("Petting Zoo: Team 555", new ResetGyro(PowerUpRobot.correction));

        this.robot = r;
        List<State> states = new ArrayList<State>();

        for (PettingZooRobot.Move m : robot.getMoves()) {
            switch (m) {
                case FORWARD:
                    states.add(new DriveEncoderGyro(DIST, POW, Angle.ZERO, true, PowerUpRobot.correction));
                    break;
                case BACK:
                    states.add(new DriveEncoderGyro(-1 * DIST, POW, Angle.ZERO, true, PowerUpRobot.correction));
                    break;
                case LEFT:
                    states.add(new DriveEncoderGyro(0.0, POW, new Degrees(-90), true, PowerUpRobot.correction));
                    break;
                case RIGHT:
                    states.add(new DriveEncoderGyro(0.0, POW, new Degrees(90), true, PowerUpRobot.correction));
                    break;
                case STOP:
                    states.add(new Delay(1));
                    break;
            }
        }

        this.setStates(states.toArray(new State[states.size()]));
    }
}
