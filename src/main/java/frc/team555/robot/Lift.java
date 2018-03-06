package frc.team555.robot;

import org.montclairrobotics.sprocket.control.ButtonAction;
import org.montclairrobotics.sprocket.motors.Motor;
import org.montclairrobotics.sprocket.motors.SEncoder;
import org.montclairrobotics.sprocket.utils.PID;


/**
 * The Lift class manages the motors, positions, and PID for the lift on the PowerUp robot.
 */
public class Lift {
/*    public final int liftUpPosition = 1000;
    public final int liftDownPosition = 0;*/

    public double MANUAL_POWER = 1;
    public final double[] positions = {0D, 333D, 667D, 1000D};
    private int pos;
    private TargetMotor motors;
    SEncoder encoder;
    
    /**
     * Constructor for Lift Class with default position of 0
     */
    public Lift() {
        this.motors = new TargetMotor(Hardware.liftEncoder, new PID(0, 0, 0), new Motor(Hardware.motorLiftR), new Motor(Hardware.motorLiftL));
        this.encoder = Hardware.liftEncoder;
        
        // Up one step
        Control.liftUpButton.setPressAction(new ButtonAction() {
            @Override public void onAction() { liftUp(); }
        });

        // Down one step
        Control.liftDownButton.setPressAction(new ButtonAction() {
            @Override public void onAction() { liftDown(); }
        });

        // To bottom
        Control.liftBottomButton.setPressAction(new ButtonAction() {
            @Override public void onAction() { setPosition(0); }
        });

        // To top
        Control.liftTopButton.setPressAction(new ButtonAction() {
            @Override public void onAction() { setPosition(positions.length - 1); }
        });

        //Start the motors in manual mode
        
        // Up (manual backup control)
        Control.liftManualUp.setHeldAction(new ButtonAction() {
            @Override public void onAction() { setPower(MANUAL_POWER); }
        });

        // Down (manual backup control)
        Control.liftManualDown.setHeldAction(new ButtonAction() {
            @Override
            public void onAction() {
            		encoder.getInches();
                if (Hardware.liftLimitSwitch.get()) {
                    setPower(0.0);
                    reset();
                } else {
                    setPower(-MANUAL_POWER);
                }
            }
        });

        //Stop the motors in manual mode
        ButtonAction stop = new ButtonAction() {
            @Override
            public void onAction() {
                setPower(0);
            }
        };
        
        Control.liftManualUp.setOffAction(stop);
        Control.liftManualDown.setOffAction(stop);

    }

    public void setPosition(int p) {
        if (p < 0)
        		pos = 0;
        else if (p > positions.length - 1)
        		pos = positions.length - 1;
        else
        		pos = p;
        
        motors.setTarget(positions[pos]);
    }

    public void setPositionRaw(double p) {
        motors.setTarget(p);
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

    public void reset() {
        encoder.reset();
    }

    public boolean atTarget() {
        //return Math.abs(positions[pos] - super.getEnc().getTicks()) < 20;
        return Math.abs(motors.pid.getError()) < 3;
    }
}
