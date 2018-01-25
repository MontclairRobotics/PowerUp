package frc.team555.robot;

import org.montclairrobotics.sprocket.motors.Motor;
import org.montclairrobotics.sprocket.utils.Input;

public class Lift {
    private Motor right;
    private Motor left;
    private Input<Double> power;


    public Lift(){
        right = new Motor(Hardware.motorLiftR);
        left  = new Motor(Hardware.motorLiftL);
        power = Control.liftInput;
    }



}
