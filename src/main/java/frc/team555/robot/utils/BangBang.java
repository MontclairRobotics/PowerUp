package frc.team555.robot.utils;


import org.montclairrobotics.sprocket.utils.PID;

public class BangBang extends PID {
    public double tolerance=1,power=1;
    public BangBang(double tolerance,double power)
    {
        this.tolerance=tolerance;
        this.power=power;
    }

    public double get()
    {
        double error=super.getTarget()-super.getCurInput();
        if(error>tolerance)
            return power;
        if(error<tolerance)
            return -power;
        return 0.0;
    }

}
