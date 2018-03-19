package frc.team555.robot.components;

import frc.team555.robot.core.Control;
import frc.team555.robot.core.Hardware;
import frc.team555.robot.utils.MotorMonitor;
import frc.team555.robot.utils.TargetMotor;
import org.montclairrobotics.sprocket.control.ButtonAction;
import org.montclairrobotics.sprocket.motors.CurrentMonitor;
import org.montclairrobotics.sprocket.motors.Motor;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.PID;

public class MainLift extends TargetMotor {

    private final double speed = 1.0;

    private int upPosition;
    private int downPosition;

    public MainLift(){
        super(Hardware.liftEncoder, new PID(0, 0, 0), new Motor(Hardware.motorLiftMainFront), new Motor(Hardware.motorLiftMainBack));

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

        new CurrentMonitor("Main Lift Front", Hardware.motorLiftMainFront, new Input<Boolean>() {
            @Override
            public Boolean get() {
                return Math.abs(getTarget() - getDistance().get()) > 20 || speed != 0;
            }
        }).setEncoder(Hardware.liftEncoder);

        new CurrentMonitor("Main Lift Back", Hardware.motorLiftMainBack, new Input<Boolean>() {
            @Override
            public Boolean get() {
                return Math.abs(getTarget() - getDistance().get()) > 20 || speed != 0;
            }
        }).setEncoder(Hardware.liftEncoder);


        // Lift Bottom



    }



}
