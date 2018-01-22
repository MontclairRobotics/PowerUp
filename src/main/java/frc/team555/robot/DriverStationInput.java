package frc.team555.robot;


import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.states.State;
import org.montclairrobotics.sprocket.utils.Debug;


public class DriverStationInput implements State {
    private String dashboardMessage;
    public static Side[] sides;
    private double startTime = 0;

<<<<<<< HEAD:src/main/java/frc/team555/robot/DashboardInput.java
=======


>>>>>>> 2d9c55aca1a2010615632761bb1af310f3be3d40:src/main/java/frc/team555/robot/DriverStationInput.java
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
        return true;
    }
}