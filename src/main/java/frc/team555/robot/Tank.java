package frc.team555.robot;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;

public class Tank {

    CANTalon drivetrainFL;
    CANTalon drivetrainFR;
    CANTalon drivetrainBL;
    CANTalon drivetrainBR;

    public Tank(CANTalon frontL, CANTalon frontR, CANTalon backL, CANTalon backR){
        this.drivetrainFL = frontL;
        this.drivetrainFR = frontR;
        this.drivetrainBL = backL;
        this.drivetrainBR = backR;
    }

    public void init(int frontLeftDeviceNumber, int frontRightDeviceNumber, int backLeftDeviceNumber, int backRightDeviceNumber){
        drivetrainFL = new CANTalon(frontLeftDeviceNumber);
        drivetrainFR = new CANTalon(frontRightDeviceNumber);
        drivetrainBL = new CANTalon(backLeftDeviceNumber);
        drivetrainBR = new CANTalon(backRightDeviceNumber);
    }

    public void arcadeDrive(Joystick joystick, boolean yInvert){
        double power;
        if(!yInvert){
            power = -joystick.getY();
        }else{
            power = joystick.getY();
        }

        double turn = joystick.getX();
        double leftPower  = power + turn;
        double rightPower = power - turn;

        drivetrainFL.set(leftPower);
        drivetrainFR.set(rightPower);
        drivetrainBL.set(leftPower);
        drivetrainBR.set(rightPower);
    }

    private final double TICKS_PER_INCH = 0000/0000; //TODO: GET VALUE
    public void autonomousDrive(double inch, double speed) {
        //TODO: FINISH
    }

    public void autonomousTurn(double turn, double speed){
        //TODO: Turning math
    }
}
