package frc.team555.robot;

import edu.wpi.first.wpilibj.Joystick;
import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.auto.AutoMode;
import org.montclairrobotics.sprocket.auto.states.DriveEncoders;
import org.montclairrobotics.sprocket.auto.states.TurnGyro;
import org.montclairrobotics.sprocket.control.Button;
import org.montclairrobotics.sprocket.control.JoystickYAxis;
import org.montclairrobotics.sprocket.drive.ControlledMotor;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.states.State;
import org.montclairrobotics.sprocket.utils.Input;

public class TestRobot extends SprocketRobot{
    Lift lift;
    CubeIntake intake;

    public void robotInit() {
        intake = new CubeIntake();

        this.addAutoMode(new AutoMode("Test lift"));
        this.sendAutoModes();

    }
}
