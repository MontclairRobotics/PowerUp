package frc.team555.robot.driverAssistance;

import Robot.NavRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team555.robot.components.VisionGuidedCubeIntake;
import org.montclairrobotics.sprocket.control.Button;
import org.montclairrobotics.sprocket.control.DashboardInput;
import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.pipeline.Step;
import org.montclairrobotics.sprocket.utils.Debug;
import org.montclairrobotics.sprocket.utils.PID;

public class AutoVaultStep implements Step<DTTarget> {

    //Navigation re-alignment
    private final NavRobot navigationSys;
    private final PID navDistPID = new PID(0,0,0); //TODO: TUNE PID
    private final PID navAnglePID = new PID(0,0,0); //TODO: TUNE PID
    private final PID aimVaultPID = new PID(0,0,0); //TODO: TUNE PID
    private final XY vaultPos = new XY(0,0); //TODO: GET LOCATION
    private final Angle vaultAngle = new Degrees(0); //TODO: GET ANGLE
    private final double navThreshold = 3; // Inches

    //Vision re-alignment
    private final PID distCorrection = new PID(0.8, 0, 0);
    private final PID angleCorrection = new PID(7,0,0);

    //Button for start
    private Button button;

    //intake
    private VisionGuidedCubeIntake intake;

    public AutoVaultStep(NavRobot navigationSys, VisionGuidedCubeIntake intake, Button button){
        this.navigationSys = navigationSys;
        this.button = button;
    }

    @Override
    public DTTarget get(DTTarget dtTarget) {

        // step true
        if (button.get()){

            // If cube not detected
            if(!SmartDashboard.getBoolean("Cube Detected", false)) {

                // Correction Calc
                distCorrection.setInput(new DashboardInput("Cube Y"));
                distCorrection.setTarget(225);
                angleCorrection.setInput(new DashboardInput("Cube X"));
                angleCorrection.setTarget(170);
                DTTarget out = new DTTarget(dtTarget.getDirection().add(new XY(0,distCorrection.get())),
                        new Degrees(dtTarget.getTurn().toDegrees() - angleCorrection.get()));

                // Vision Status Update
                Debug.msg("Vision Correcting", true);
                Debug.msg("Vision Distance Correction", distCorrection.get());
                Debug.msg("Vision Angle Correction", angleCorrection.get());
                Debug.msg("Vision Out: ", out);

                return out;
            }else{

                // Vision Status Update
                Debug.msg("Vision Correcting", false);
                Debug.msg("Vision Distance Correction", distCorrection.get());
                Debug.msg("Vision Angle Correction", angleCorrection.get());
                Debug.msg("Vision out: ", dtTarget);

                // Calc Distance to Vault Pos
                double distance = Math.sqrt(Math.pow((navigationSys.getPositon().getX()-vaultPos.getY()),2)
                        + Math.pow((navigationSys.getPositon().getX()-vaultPos.getY()),2));

                // Drive to Vault Pos
                if(distance > navThreshold) {
                    Angle angle = new Degrees(
                            Math.toDegrees(
                                    Math.atan2(
                                            Math.abs(navigationSys.getPositon().getX()-vaultPos.getY()),
                                            Math.abs(navigationSys.getPositon().getX()-vaultPos.getY())
                                    )
                            )
                    );

                    navDistPID.setTarget(distance);
                    navAnglePID.setTarget(angle.toDegrees());

                    DTTarget out = new DTTarget(dtTarget.getDirection().add(new XY(0,navDistPID.get())),
                            new Degrees(dtTarget.getTurn().toDegrees() - navAnglePID.get()));

                    Debug.msg("Vault Align Out: ", out);
                    return out;
                }else{

                    // Turn facing the Vault
                    aimVaultPID.setTarget(vaultAngle.toDegrees());
                    DTTarget out = new DTTarget(dtTarget.getDirection(),
                            new Degrees(dtTarget.getTurn().toDegrees() - navAnglePID.get()));

                    //release cube
                    //TODO: fix

                    return out;
                }
            }
        }


        return null;
    }
}
