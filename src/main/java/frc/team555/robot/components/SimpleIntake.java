package frc.team555.robot.components;

import frc.team555.robot.core.Control;
import frc.team555.robot.core.Hardware;
import org.montclairrobotics.sprocket.control.ButtonAction;
import org.montclairrobotics.sprocket.motors.Motor;


public class SimpleIntake {

    public final Motor clamp;

    public SimpleIntake() {
        this.clamp = new Motor(Hardware.motorIntakeClamp);


        Control.openButton.setHeldAction(new ButtonAction() {
            @Override
            public void onAction() {
                clamp.set(1);
            }
        });
        Control.openButton.setReleaseAction(new ButtonAction() {
            @Override
            public void onAction() {
                clamp.set(0);
            }
        });

        Control.closeButton.setHeldAction(new ButtonAction() {
            @Override
            public void onAction() {
                clamp.set(-1);
            }
        });
        Control.closeButton.setReleaseAction(new ButtonAction() {
            @Override
            public void onAction() {
                clamp.set(0);
            }
        });

        //Updater.add(this, Priority.CALC);
    }

/*
    @Override
    public void update() {
        double p =
        Vector p = power.get();
        if(p.getMagnitude() < tolerance){
            openClamp();
        }else{
            closeClamp();
        }
        left.set(p.getX());
        right.set(p.getY());
        updateClamp();
    }

    @Override
    public void enable() {
        left.set(-.5);
        right.set(-.5);
    }

    @Override
    public void disable() {
        left.set(0);
        right.set(0);
    }

    private void openClamp(){
        if(!clampOpen)
        {
            clampOpen=true;
            startClamp();
        }
    }

    private void closeClamp(){
        if(clampOpen)
        {
            clampOpen=false;
            startClamp();
        }
    }

    private void startClamp()
    {
        //Guess how far we have moved, and set our start time correctly
        //clampStart=System.currentTimeMillis()-(clampTime-(System.currentTimeMillis()-clampStart)));
        clampStart=System.currentTimeMillis()*2-clampTime-clampStart;
        if(clampStart>System.currentTimeMillis())
        {
            clampStart=System.currentTimeMillis();
        }
    }

    private void updateClamp()
    {
        if(System.currentTimeMillis()-clampStart<clampTime)
        {
            if(clampOpen)
            {
                clamp.set(clampPower);
            }
            else
            {
                clamp.set(-clampPower);
            }
        }
        else
        {
            clamp.set(0);
        }
    }*/
}

