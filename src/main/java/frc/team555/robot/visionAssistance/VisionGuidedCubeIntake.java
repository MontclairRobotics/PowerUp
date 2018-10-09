package frc.team555.robot.visionAssistance;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team555.robot.components.IntakeLift;
import frc.team555.robot.core.Control;
import frc.team555.robot.core.Hardware;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.motors.Motor;
import org.montclairrobotics.sprocket.utils.Debug;

public class VisionGuidedCubeIntake implements Updatable{

    private final Motor left;
    private final Motor right;

    //TODO: Tune default x and y
    private final double defaultX = 140;
    private final double defaultY = 140;

    //Values taken: 10/09/2018:0755
    private final double X_MAX = 190;
    private final double X_MIN = 140;
    private final double Y_MAX = 230;
    private final double Y_MIN = 200;

    //Power Vectors
    Vector intakeVector  = new XY(1,0);
    Vector outtakeVector = new XY(-1,0);
    Vector idleVector    = new XY(0.3,0);

    public VisionGuidedCubeIntake() {
        this.left = new Motor(Hardware.motorIntakeL);
        this.right = new Motor(Hardware.motorIntakeR);

        Updater.add(this, Priority.CALC);
    }

    @Override
    public void update() {
        Vector p = calcVector();
        left.set(p.getY() - p.getX());
        right.set(p.getY() + p.getX());
    }

    private Vector calcVector(){
        double cubeX = SmartDashboard.getNumber("Cube X",defaultX);
        double cubeY = SmartDashboard.getNumber("Cube Y", defaultY);
        boolean inIntake = (cubeX < X_MAX) && (cubeX > X_MIN) && (cubeY < Y_MAX) && (cubeY > Y_MIN);
        Debug.msg("Cube In Intake", inIntake);

        if(Control.autoOuttake.get()){
            return outtakeVector;
        }else if(inIntake){
            return idleVector;
        }else{
            return intakeVector;
        }
    }
}
