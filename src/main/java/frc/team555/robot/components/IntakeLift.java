package frc.team555.robot.components;

import frc.team555.robot.core.Control;
import frc.team555.robot.core.Hardware;
import frc.team555.robot.utils.BangBang;
import frc.team555.robot.utils.TargetMotor;
import org.montclairrobotics.sprocket.control.ButtonAction;
import org.montclairrobotics.sprocket.motors.CurrentMonitor;
import org.montclairrobotics.sprocket.motors.Motor;
import org.montclairrobotics.sprocket.motors.SEncoder;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.PID;
import org.montclairrobotics.sprocket.utils.Utils;


/**
 * The IntakeLift class manages the motors, positions, and PID for the intakeLift on the PowerUp robot.
 */
public class IntakeLift implements Lift{
/*  public final int liftUpPosition = 1000;
    public final int liftDownPosition = 0;*/

    private boolean auto=false;


    public double MANUAL_POWER_UP=.5;
    public double MANUAL_POWER_DOWN=-.3;
    public final double[] positions = {0D, 49-15}; // Todo: test values
    private int pos;
    private TargetMotor motors;
    public SEncoder encoder;
    /**
     * Constructor for IntakeLift Class with default position of 0
     */
    public IntakeLift() {

        motors = new TargetMotor(Hardware.intakeLiftEncoder, new BangBang(1,1),new Motor(Hardware.motorLiftIntake)); // Todo: Needs Tuninng

        encoder=Hardware.intakeLiftEncoder;

        //=================
        //Buttons below!!!!
        //=================

        //IntakeLift up one step
        /*Control.intakeLiftIncrement.setPressAction(new ButtonAction() {
            @Override
            public void onAction() {
                liftUp();
            }
        });

        //IntakeLift down one step
        Control.intakeLiftDecrement.setPressAction(new ButtonAction(){

            @Override
            public void onAction() {
                liftDown();
            }
        });

        //IntakeLift to bottom
        Control.intakeLiftBottom.setPressAction(new ButtonAction() {
            @Override
            public void onAction() {
                setPosition(0);
            }
        });

        //IntakeLift to top
        Control.intakeLiftTop.setPressAction(new ButtonAction() {
            @Override
            public void onAction() {
                setPosition(positions.length-1);
            }
        });
*/
        //Start the motors in manual mode
        //IntakeLift up (manual backup control)
        Control.intakeLiftManualUp.setPressAction(new ButtonAction() {
            @Override
            public void onAction() {
                motors.setPower(MANUAL_POWER_UP);
            }
        });

        //IntakeLift down (manual backup control)
        Control.intakeLiftManualDown.setPressAction(new ButtonAction() {
            @Override
            public void onAction() {
                motors.setPower(MANUAL_POWER_DOWN);

            }
        });

        //Stop the motors in manual mode
        ButtonAction stop=new ButtonAction() {
            @Override
            public void onAction() {
                motors.setPower(0);
            }
        };

        Control.intakeLiftManualUp.setReleaseAction(stop);
        Control.intakeLiftManualDown.setReleaseAction(stop);
        motors.setPower(0);
    }


    public void setPosition(int p) {
        p = (int)Utils.constrain(p, 0, positions.length - 1);
        //motors.setTarget(positions[p]);
        pos = p;
    }

    public void setPositionRaw(double p)
    {
        //motors.setTarget(p);
    }


    @Override
    public SEncoder getEncoder() {
        return encoder;
    }

    @Override
    public void setAuto(double power) {
        auto=true;
        setPower(power);
    }

    public void setPower(double power){
        motors.set(power);
    }

    public void liftUp() {
        pos++;
        setPosition(pos);
    }

    public void liftDown() {
        pos--;
        setPosition(pos);
    }

    public void reset()
    {
        encoder.reset();
    }

    public boolean atTarget() {
        //return Math.abs(positions[pos] - super.getEnc().getTicks()) < 20;
        return Math.abs(motors.pid.getError()) < 3;
    }
}
