package frc.team555.robot.utils;

import org.montclairrobotics.sprocket.drive.DTStep;
import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.PID;
import org.montclairrobotics.sprocket.utils.Togglable;

public class VisionCorrection implements DTStep, Togglable {
    boolean enabled = false;
    Input<Double> xPos;
    PID correction;

    public VisionCorrection(Input<Double> xPos, PID correction, double target) {
        this.xPos = xPos;
        this.correction = correction.copy();
        correction.setInput(xPos);
    }

    @Override
    public DTTarget get(DTTarget dtTarget) {
        if(enabled){
            return new DTTarget(dtTarget.getDirection(), new Degrees(dtTarget.getTurn().toDegrees() + correction.get()));
        }else{
            return dtTarget;
        }
    }

    @Override
    public void enable() {
        enabled = true;
    }

    @Override
    public void disable() {
        enabled = false;
    }

}
