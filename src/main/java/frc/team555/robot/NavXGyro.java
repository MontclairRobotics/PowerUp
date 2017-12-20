package frc.team555.robot;

import com.kauailabs.navx.frc.AHRS;

public class NavXGyro {

    private AHRS navX;

    public NavXGyro(AHRS navX) {
        this.navX = navX;
        navX.reset();
    }

    public float getPitch(){
        return navX.getPitch();
    }

    public float getRoll(){
        return navX.getRoll();
    }

    public float getYaw(){
        return navX.getYaw();
    }

    public void zeroYaw(){
        navX.zeroYaw();
    }

}