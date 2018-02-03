package frc.team555.robot;

import edu.wpi.first.wpilibj.SpeedController;
import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.motors.Motor;
import org.montclairrobotics.sprocket.motors.SEncoder;
import org.montclairrobotics.sprocket.utils.PID;


/**
 * Target motor is an extention of the motor class that allows the
 * motors to be set to encoder positions
 * This class uses PID control to set the motor positions
 */
public class TargetMotor extends Motor implements Updatable{
    /**
     * The encoder attached to the motor
     */
    SEncoder encoder;

    /**
     * A pid controller that is used to set the position
     */
    PID pid;


    /**
     * Create a new Target motor using a speed controller, encoder, and pid controller
     *
     * @param motor The motor controller
     * @param encoder The encoder for the motor
     * @param pid The PID controller that will be used to set the position
     */
    public TargetMotor(SpeedController motor, SEncoder encoder, PID pid) {
        super(motor);
        this.encoder = encoder;
        this.pid = pid;
        this.pid.setInput(encoder);
        this.pid.setTarget();
        Updater.add(this, Priority.CALC);
    }

    /**
     * Set the target encoder ticks
     *
     * @param target Target encoder ticks that the motor will be set to
     */
    public void setTarget(double target){
        pid.setTarget(target);
    }

    /**
     * Reset the encoder and PID, this will set both the encoder values
     * and target values to 0
     */
    public void reset(){
        pid.setTarget(0);
        encoder.reset();
    }

    /**
     * Update the PID and set the according motor power
     */
    @Override
    public void update() {
        pid.update();
        super.getMotor().set(pid.get());
    }
}
