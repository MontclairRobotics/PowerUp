package frc.team555.robot;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.control.Button;
import org.montclairrobotics.sprocket.control.ButtonAction;
import org.montclairrobotics.sprocket.control.JoystickButton;
import org.montclairrobotics.sprocket.control.SquaredDriveInput;
import org.montclairrobotics.sprocket.drive.*;
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.drive.utils.GyroLock;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.motors.Motor;
import org.montclairrobotics.sprocket.utils.PID;

public class TankRobot extends SprocketRobot {


    PID pid;

    public final int driveStickDeviceNumber = 0;
    public final int auxStickDeviceNumber   = 1;

    public final int buttonModule1 = 1;
    public final int buttonModule2 = 2;

    SprocketHardwareConfig hw;

    @Override
    public void robotInit() {
        hw = new SprocketHardwareConfig();

        Joystick driveStick = new Joystick(driveStickDeviceNumber);
        Joystick auxStick = new Joystick(auxStickDeviceNumber);

        pid = new PID();
        PID gyroPID = new PID(0.18*13.75,0,.0003*13.75);
        gyroPID.setInput(hw.navX);
        GyroCorrection gCorrect=new GyroCorrection(hw.navX,gyroPID,20,0.3*20);
        GyroLock gLock = new GyroLock(gCorrect);


        // in the case of a physical robot, this could be part of
        // the hardware config since it couldn't change reasonably
        DriveModule dtLeft  = new DriveModule(new XY(-1,0), new XY(0,1), new Motor(hw.drivetrainFL), new Motor(hw.drivetrainBL));
        DriveModule dtRight = new DriveModule(new XY( 1,0), new XY(0,1), new Motor(hw.drivetrainFR), new Motor(hw.drivetrainBR));

        DriveTrainBuilder dtBuilder = new DriveTrainBuilder()
                .addDriveModule(dtLeft)
                .addDriveModule(dtRight)
                .setDriveTrainType(DriveTrainType.TANK)
                .setInput(new SquaredDriveInput(driveStick));
        try {
            dtBuilder.build();
        } catch (InvalidDriveTrainException e) {
            e.printStackTrace();
        }
        // end of drivetrain hardware config


        // Module 1
        Button module1 = new JoystickButton(driveStick, buttonModule1);
        module1.setHeldAction(new ButtonAction() {
            @Override
            public void onAction() {
            }
        });

        module1.setOffAction(new ButtonAction() {
            @Override
            public void onAction() {
            }
        });

        //Module 2
        Button module2 = new JoystickButton(driveStick, buttonModule2);
        module2.setHeldAction(new ButtonAction() {
            @Override
            public void onAction() {

            }
        });
        module1.setOffAction(new ButtonAction() {
            @Override
            public void onAction() {
            }
        });

    }

    @Override
    public void update() {
        checkCurrentDT();
    }
    
    private void checkCurrentDT(){
        // Calculate Averages and standard deviations for drive train current draws
        double tempLeftCurrentAvg = avg(hw.pdb.getCurrent(hw.drivetrainFL.getDeviceID()) + hw.pdb.getCurrent(hw.drivetrainBL.getDeviceID()));
        double tempRightCurrentAvg = avg(hw.pdb.getCurrent(hw.drivetrainFR.getDeviceID())+ hw.pdb.getCurrent(hw.drivetrainBR.getDeviceID()));
        double dtLeftCurrentStdDev  = stdDiv(hw.pdb.getCurrent(hw.drivetrainFR.getDeviceID()), hw.pdb.getCurrent(hw.drivetrainBR.getDeviceID()));
        double dtRightCurrentStdDev  =  stdDiv(hw.pdb.getCurrent(hw.drivetrainFL.getDeviceID()), hw.pdb.getCurrent(hw.drivetrainBL.getDeviceID()));
        
        // Check if the motor current draw is withing 1 standard deviation
        boolean checkFL = check(hw.pdb.getCurrent(hw.drivetrainFL.getDeviceID()), tempLeftCurrentAvg,  dtLeftCurrentStdDev);
        boolean checkFR = check(hw.pdb.getCurrent(hw.drivetrainFR.getDeviceID()), tempRightCurrentAvg, dtRightCurrentStdDev);
        boolean checkBL = check(hw.pdb.getCurrent(hw.drivetrainBL.getDeviceID()), tempLeftCurrentAvg, dtLeftCurrentStdDev);
        boolean checkBR = check(hw.pdb.getCurrent(hw.drivetrainBR.getDeviceID()), tempLeftCurrentAvg, dtRightCurrentStdDev);
        
        // Debug motor checks
        SmartDashboard.putBoolean("FL within STD Dev",checkFL);
        SmartDashboard.putBoolean("FR within STD Dev",checkFR);
        SmartDashboard.putBoolean("BL within STD Dev",checkBL);
        SmartDashboard.putBoolean("BR within STD Dev",checkBR);
    }
    
    private double stdDiv(double ... values){
        double avg = avg(values);
        double total = 0;
        for(double value : values){
            total += Math.pow(value - avg, 2);
        }
        return Math.sqrt(total / values.length);
        
    }
    
    private double avg(double ... values){
        double total = 0;
        for(double value : values){
            total += value;
        }
        return total / values.length;
    }
    
    private boolean check(double current, double avg, double stdDiv){
        return Math.abs(current - avg) < stdDiv;
    }

}
