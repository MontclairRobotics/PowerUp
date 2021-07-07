package frc.team555.robot.test;

import frc.team555.robot.components.CubeIntake;
import frc.team555.robot.components.IntakeLift;
import frc.team555.robot.core.Control;
import frc.team555.robot.core.Hardware;
import frc.team555.robot.utils.JoystickXAxis;
import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.auto.AutoMode;
import org.montclairrobotics.sprocket.control.JoystickYAxis;
import org.montclairrobotics.sprocket.drive.ControlledMotor;
import org.montclairrobotics.sprocket.utils.Input;

public class TestRobot extends SprocketRobot{
    IntakeLift intakeLift;
    CubeIntake intake;

    public void robotInit() {
        Hardware.init();
        Control.init();



        /*Input<Double> yAxis = new JoystickYAxis(0);
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
        };*/

        Input<Double> input=new JoystickYAxis(Control.auxStick);
        ControlledMotor testMotor1 = new ControlledMotor(Hardware.motorLiftMainFront, input);
        ControlledMotor testMotor2 = new ControlledMotor(Hardware.motorLiftMainBack, input);
        testMotor2.setInverted(true);

        super.addAutoMode(new AutoMode("name"));
        super.sendAutoModes();
    }
}
