package frc.team555.robot.test;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Joystick;
import frc.team555.robot.utils.TalonEncoder;
import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.drive.ControlledMotor;
import org.montclairrobotics.sprocket.utils.Debug;
import org.montclairrobotics.sprocket.utils.Input;

public class EncoderTestRobot extends SprocketRobot {

    TalonEncoder testEncoder;
    WPI_TalonSRX testMotorController;
    ControlledMotor testMotor;
    Joystick testJoystick;

    @Override
    public void robotInit() {
        testMotorController = new WPI_TalonSRX(0);
        testEncoder = new TalonEncoder(testMotorController, 1);
        testJoystick = new Joystick(0);
        testMotor = new ControlledMotor(testMotorController, new Input<Double>() {
            @Override
            public Double get() {
                return testJoystick.getY();
            }
        });
    }
    @Override
    public void update() {
        Debug.msg("Motor Position",testEncoder.get());
        Debug.msg("Motor Speed",testEncoder.getSpeed());
    }

}
