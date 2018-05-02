package frc.team555.robot.utils;


import edu.wpi.first.wpilibj.SpeedController;
import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.motors.Module;
import org.montclairrobotics.sprocket.motors.Motor;
import org.montclairrobotics.sprocket.motors.SEncoder;
import org.montclairrobotics.sprocket.utils.PID;


/**
 * Target motor is an extention of the motor class that allows the
 * motors to be set to encoder positions
 * This class uses PID control to set the motor positions
 */
public class TargetMotor extends Module implements Updatable{
    public enum Mode {POSITION,POWER};
    private double power=0;
    public Input<Boolean> upperBound,lowerBound;
    /**
     * The encoder attached to the motor
     */
    public SEncoder encoder;

    /**
     * A pid controller that is used to set the position
     */
    public PID pid;

    public Mode mode;


    /**
     * Create a new Target motor using a speed controller, encoder, and pid controller
     *
     * @param motors The motor controllers
     * @param encoder The encoder for the motor
     * @param pid The PID controller that will be used to set the position
     */
    public TargetMotor(SEncoder encoder, PID pid,Motor... motors)
    {
        super(motors);
        this.encoder = encoder;
        this.pid = pid;
        this.pid.setInput(encoder);
        this.pid.setTarget();
        this.mode= Mode.POSITION;
        Updater.add(this, Priority.CALC);
    }

    /**
     * Set the target encoder ticks
     *
     * @param target Target encoder ticks that the motor will be set to
     */
    public void setTarget(double target){
        mode = Mode.POSITION;
        pid.setTarget(target);
    }

    public void setPower(double pow){
        mode = Mode.POWER;
        this.power=pow;
        safeSet(pow);
    }

    /**
     * Reset the encoder and PID, this will set both the encoder values
     * and target values to 0
     */
    public void reset(){
        pid.setTarget(0);
        encoder.reset();
    }
    
    public void stop()
    {
        setPower(0);
    }

    /**
     * Update the PID and set the according motor power
     */
    @Override
    public void update() {
        if(mode==Mode.POSITION) {
            safeSet(pid.get());
        }
        else
        {
            safeSet(power);
        }
    }
    
    public void safeSet(double pow)
    {
        if(pow>0 && upperBound.get() || pow<0 && lowerBound.get())
        {
            pow=0;
        }
        super.set(pow);
    }

    public double getTarget(){
        return pid.getTarget();
    }
}
