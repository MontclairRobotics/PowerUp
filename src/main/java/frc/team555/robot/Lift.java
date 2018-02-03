package frc.team555.robot;

import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.motors.Module;
import org.montclairrobotics.sprocket.motors.Motor;
import org.montclairrobotics.sprocket.utils.PID;


/**
 * The Lift class manages the motors, positions, and PID for the lift on the PowerUp robot.
 */
public class Lift extends Module {
    public final int liftUpPosition = 1000;
    public final int liftDownPosition = 0;
    private TargetMotor rightMotor;
    private TargetMotor leftMotor;
    private int targetPosition;
    boolean toggle;
    /**
     * Constructor for Lift Class with default position
     * @param position Default position for lift upon construction
     */
    public Lift(int position, boolean toggle) {
        super(Hardware.leftLiftEncoder, new PID(0, 0, 0), MotorInputType.PERCENT, new Motor(Hardware.motorLiftL), new Motor(Hardware.motorLiftR));
        targetPosition = position;
        leftMotor = new TargetMotor(Hardware.motorLiftL, Hardware.leftLiftEncoder, new PID(0, 0, 0));
        rightMotor = new TargetMotor(Hardware.motorLiftR, Hardware.rightLiftEncoder, new PID(0, 0, 0));
        this.toggle = toggle;
    }

    /**
     * Constructs a Lift object with a position of 0
     */
    public Lift(){
        this(0, false);
    }


    public void setPosition(int position) {
        rightMotor.setTarget(position);
        leftMotor.setTarget(position);
    }

    public void setPower(double power){
        super.set(power);
    }

    public void setUpPosition() {
        setPosition(liftUpPosition);
    }

    public void setDownPosition() {
        setPosition(liftDownPosition);
    }

    public void start() {
        toggle = true;
    }

    public void stop() {
        toggle = false;
    }


    public boolean isDone() {
        return Math.abs(targetPosition - super.getEnc().getTicks()) < 20;
    }
}
