package frc.team555.robot;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.auto.AutoMode;
import org.montclairrobotics.sprocket.auto.states.DriveEncoders;
import org.montclairrobotics.sprocket.auto.states.TurnGyro;
import org.montclairrobotics.sprocket.control.JoystickYAxis;
import org.montclairrobotics.sprocket.drive.ControlledMotor;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.utils.Input;

public class TestRobot extends SprocketRobot{
    public void robotInit() {

        DriveEncoders.TOLLERANCE = 3;
        TurnGyro.TURN_SPEED = 0.3;
        TurnGyro.tolerance = new Degrees(5);

        Hardware.init();
        Control.init();



        Input<Double> yAxis = new JoystickYAxis(0);
        Input<Double> xAxisA = new JoystickXAxis(0);
        Input<Double> xAxis= new Input<Double>(){

            @Override
            public Double get() {
                if(Math.abs(xAxisA.get())<0.25)
                {
                    return 0.0;
                }
                return xAxisA.get();
            }
        };
        Input<Double> leftInput = new Input<Double>() {
            @Override
            public Double get() {
                return yAxis.get() - xAxis.get();
            }
        };
        Input<Double> rightInput = new Input<Double>() {
            @Override
            public Double get() {
                return yAxis.get() + xAxis.get();
            }
        };
        ControlledMotor testMotor1 = new ControlledMotor(Hardware.testMotor1, leftInput);
        ControlledMotor testMotor2 = new ControlledMotor(Hardware.testMotor2, rightInput);

        AutoMode encoder = new AutoMode("encoder",
                new DriveEncoders(100,.25));
        addAutoMode(encoder);
        sendAutoModes();
    }
}
