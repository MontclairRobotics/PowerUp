package frc.team555.robot.driverAssistance;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team555.robot.core.Control;
import frc.team555.robot.core.Hardware;
import frc.team555.robot.utils.BangBang;
import frc.team555.robot.utils.TargetMotor;
import org.montclairrobotics.sprocket.control.ButtonAction;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.motors.Motor;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.PID;
import org.montclairrobotics.sprocket.utils.Togglable;

public class AutoCubeIntake implements Updatable, Togglable{

    private boolean auto;

    public final Motor left;
    public final Motor right;

    public final Input<Vector> power;

    //TODO: Tune default x and y
    private final double defaultX = 140;
    private final double defaultY = 140;

    //TODO: Get thresholds
    private final double X_MAX = 150;
    private final double X_MIN = 130;
    private final double Y_MAX = 150;
    private final double Y_MIN = 130;


    //Vector States
    Vector intakeVector  = new XY(-1,0);
    Vector outtakeVector = new XY(1,0);
    Vector idleVector    = new XY(-0.1,0);


    public AutoCubeIntake() {
        this.left = new Motor(Hardware.motorIntakeL);
        this.right = new Motor(Hardware.motorIntakeR);
        right.setInverted(true);

        double cubeX = SmartDashboard.getNumber("Cube X",defaultX);
        double cubeY = SmartDashboard.getNumber("Cube Y", defaultY);

        this.power = new Input<Vector>() {
            @Override
            public Vector get() {
                if(cubeX < X_MAX && cubeX > X_MIN && cubeY < Y_MAX && cubeY > Y_MIN){
                    return idleVector;
                }else if(!Control.autoOuttake.get()){
                    return outtakeVector;
                }else{
                    return intakeVector;
                }
            }
        };

        Updater.add(this, Priority.CALC);
    }


    @Override
    public void update() {
        Vector p = power.get();

        if(!auto) {
            left.set(p.getY() - p.getX());
            right.set(p.getY() + p.getX());
        }
        auto=false;
    }


    public void auto()
    {
        auto=true;
        enable();
    }
    @Override
    public void enable() {
        left.set(1);
        right.set(1);
    }

    @Override
    public void disable() {
        left.set(0);
        right.set(0);
    }

}
