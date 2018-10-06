package frc.team555.robot.test;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team555.robot.core.Control;
import frc.team555.robot.core.Hardware;
import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.control.JoystickButton;
import org.montclairrobotics.sprocket.motors.Motor;

public class VisionTest extends SprocketRobot {

    Motor r1, r2;
    Motor l1, l2;
    @Override
    public void robotInit() {
        Hardware.init();
        Control.init();

        r1 = new Motor(Hardware.motorDriveFR);
        r2 = new Motor(Hardware.motorDriveBR);
        l1 = new Motor(Hardware.motorDriveFL);
        l2 = new Motor(Hardware.motorDriveBL);


    }

    @Override
    public void update() {
        double power = .1;
        double x = SmartDashboard.getNumber("Cube X", 140);
        SmartDashboard.putNumber("Result", x);
        double target = 150;
        double tolerance = 10;
        if(Control.driveStick.getRawButton(1)) {
            SmartDashboard.putBoolean("Running", true);
            if (x < target - tolerance) {
                Hardware.motorDriveFR.set(power);
                Hardware.motorDriveBR.set(power);

                Hardware.motorDriveFL.set(-power);
                Hardware.motorDriveBL.set(-power);
                SmartDashboard.putBoolean("Correcting", true);
            } else if (x > target + tolerance) {
                Hardware.motorDriveFR.set(-power);
                Hardware.motorDriveBR.set(-power);

                Hardware.motorDriveFL.set(power);
                Hardware.motorDriveBL.set(power);
                SmartDashboard.putBoolean("Correcting", true);
            } else {
                Hardware.motorDriveFR.set(0);
                Hardware.motorDriveBR.set(0);

                Hardware.motorDriveFL.set(0);
                Hardware.motorDriveBL.set(0);
                SmartDashboard.putBoolean("Correcting", false);
            }
        }else {
            SmartDashboard.putBoolean("Running", false);
        }
    }
}
