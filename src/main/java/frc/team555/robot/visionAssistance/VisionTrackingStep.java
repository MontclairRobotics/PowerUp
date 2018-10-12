package frc.team555.robot.visionAssistance;

import org.montclairrobotics.sprocket.control.Button;
import org.montclairrobotics.sprocket.control.DashboardInput;
import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.pipeline.Step;
import org.montclairrobotics.sprocket.utils.Debug;
import org.montclairrobotics.sprocket.utils.PID;

public class VisionTrackingStep implements Step<DTTarget> {

    private final PID distCorrection = new PID(0.8, 0, 0);
    private final PID angleCorrection = new PID(7,0,0);
    private Button button;

    public VisionTrackingStep(Button button){
        this.button = button;
    }

    @Override
    public DTTarget get(DTTarget dtTarget) {
        if(button.get()){

            distCorrection.setInput(new DashboardInput("Cube Y"));
            distCorrection.setTarget(225);

            angleCorrection.setInput(new DashboardInput("Cube X"));
            angleCorrection.setTarget(170);

            Debug.msg("Vision Correcting", true);
            Debug.msg("Vision Distance Correction", distCorrection.get());
            Debug.msg("Vision Angle Correction", angleCorrection.get());
            DTTarget out = new DTTarget(dtTarget.getDirection().add(new XY(0,distCorrection.get())),
                    new Degrees(dtTarget.getTurn().toDegrees() - angleCorrection.get()));
            Debug.msg("Vision Out: ", out);

            return out;
        }else{
            Debug.msg("Vision Correcting", false);
            Debug.msg("Vision Distance Correction", distCorrection.get());
            Debug.msg("Vision Angle Correction", angleCorrection.get());
            Debug.msg("Vision out: ", dtTarget);
            return dtTarget;
        }
    }

}
