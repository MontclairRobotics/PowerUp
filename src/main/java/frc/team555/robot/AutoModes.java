package frc.team555.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.montclairrobotics.sprocket.auto.AutoMode;
import org.montclairrobotics.sprocket.states.State;

import java.util.ArrayList;

public class AutoModes {
    public static SmartDashboard dashboard;

    public static AutoMode buildDashboardAuto(){
        ArrayList<State> states = new ArrayList<>();
        dashboard = new SmartDashboard();

        return null;
    }
}
