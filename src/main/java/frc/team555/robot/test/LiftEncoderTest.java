package frc.team555.robot.test;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team555.robot.core.Control;
import frc.team555.robot.core.Hardware;
import org.montclairrobotics.sprocket.SprocketRobot;

public class LiftEncoderTest extends SprocketRobot {
    @Override
    public void robotInit() {
        Hardware.init();
        Control.init();
    }

    @Override
    public void update(){
        SmartDashboard.putNumber("Lift Encoder", Hardware.intakeLiftEncoder.get());
        Hardware.motorLiftIntake.set(Control.auxStick.getY());
    }
}
