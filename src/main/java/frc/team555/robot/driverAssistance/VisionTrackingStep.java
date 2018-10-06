package frc.team555.robot.driverAssistance;

import frc.team555.robot.core.Control;
import frc.team555.robot.utils.BangBang;
import org.montclairrobotics.sprocket.control.DashboardInput;
import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.pipeline.Step;
import org.montclairrobotics.sprocket.utils.Debug;

public class VisionTrackingStep implements Step<DTTarget> {

    private BangBang visionCorrection;

    public VisionTrackingStep(BangBang visionCorrection){
        this.visionCorrection =  visionCorrection;
    }

    @Override
    public DTTarget get(DTTarget dtTarget) {
        if(Control.visionOn.get()){
            Debug.msg("Correcting", true);
            Debug.msg("Correction", visionCorrection.get());
            DTTarget out = new DTTarget(dtTarget.getDirection(), new Degrees(dtTarget.getTurn().toDegrees() - visionCorrection.get()));
            Debug.msg("Vision Out: ", out);
            return out;
        }else{
            Debug.msg("Correcting", false);
            Debug.msg("Correction", visionCorrection.get());
            Debug.msg("Vision out: ", dtTarget);
            return dtTarget;
        }
    }
}
