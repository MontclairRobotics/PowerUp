package frc.team555.robot;

import org.montclairrobotics.sprocket.control.ButtonAction;
import org.montclairrobotics.sprocket.motors.Module;
import org.montclairrobotics.sprocket.motors.Motor;
import org.montclairrobotics.sprocket.motors.SEncoder;
import org.montclairrobotics.sprocket.utils.PID;

public class MainLift extends TargetMotor{

    private final double speed = 0.5;

    private int upPosition;
    private int downPosition;

    public MainLift(){
        super(Hardware.liftEncoder, new PID(0, 0, 0), new Motor(Hardware.motorLiftMainA), new Motor(Hardware.motorLiftMainB));

        mode = Mode.POWER;

        // Manual Up
        Control.mainLiftManualUp.setPressAction(new ButtonAction() {
            @Override
            public void onAction() {
                MainLift.super.set(speed);
            }
        });

        Control.mainLiftManualUp.setReleaseAction(new ButtonAction() {
            @Override
            public void onAction() {
                MainLift.super.set(0);
            }
        });

        // Manual Down
        Control.mainLiftManualDown.setPressAction(new ButtonAction() {
            @Override
            public void onAction() {
                MainLift.super.set(-speed);
            }
        });

        Control.mainLiftManualDown.setReleaseAction(new ButtonAction() {
            @Override
            public void onAction() {
                if(encoder != null) {
                    MainLift.super.setTarget(upPosition);
                }
            }
        });

        // Lift Top
        Control.mainLiftTop.setPressAction(new ButtonAction() {
            @Override
            public void onAction() {
                if(encoder != null){
                    MainLift.super.setTarget(downPosition);
                }
            }
        });


        // Lift Bottom



    }



}
