package frc.team555.robot;


import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.states.State;
import org.montclairrobotics.sprocket.utils.Debug;


public class DashboardInput implements State {
    private String dashboardMessage;
    private Side[] sides;
    private double startTime = 0;

    @Override
    public void start() {
        startTime = Updater.getTime();
        dashboardMessage = DriverStation.getInstance().getGameSpecificMessage();
        sides = Side.fromString(dashboardMessage);
    }

    @Override
    public void stop() {

    }

    @Override
    public void stateUpdate() {
        Debug.msg("Starting Side", sides[0]);
        Debug.msg("", sides[1]);
    }

    @Override
    public boolean isDone() {
        return Updater.getTime() - startTime > 30;
    }
}
