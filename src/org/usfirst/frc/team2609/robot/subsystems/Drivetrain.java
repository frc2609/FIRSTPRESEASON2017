package org.usfirst.frc.team2609.robot.subsystems;
import org.usfirst.frc.team2609.robot.Robot;
import org.usfirst.frc.team2609.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drivetrain extends Subsystem {
	private static double drivePIDOutput = 0;
	private static double steerPIDOutput = 0;
	
    public void humanDrive(){
    }
    public void driveTank(double left, double right){
		RobotMap.driveVictorRight1.set(right);
		RobotMap.driveVictorRight2.set(right);
		RobotMap.driveVictorLeft1.set(left);
		RobotMap.driveVictorLeft2.set(left);
    }
    public void encoderDriveStraight(double driveTarget, double drivePower, double driveHeading){
    	double distError = 0;
		double power = 0;
		double pEnc = SmartDashboard.getNumber("pEnc", 0.0022);
		double pSteer = SmartDashboard.getNumber("pSteer", 0.2);
		System.out.println("DrivePower " + drivePower);
		System.out.println("pEnc " + pEnc);
		double encAvg = ((RobotMap.driveEncLeft.getDistance()+(RobotMap.driveEncRight.getDistance()*-1))*0.5);
		//double encRateAvg = ((Math.abs(RobotMap.driveEncLeft.getRate())+Math.abs(RobotMap.driveEncRight.getRate())*0.5));

		if (Math.abs(drivePower)==drivePower){
			distError = driveTarget - encAvg;
			power = Math.min(drivePower,distError*pEnc); //0.0025 for i?
		}
		else{
			distError = (encAvg - driveTarget)*-1;
			power = Math.max(drivePower,distError*pEnc); //0.0025 for i?
		}
		System.out.println("power " + power);
		double encError = Math.min(Math.abs((Math.abs(RobotMap.driveEncLeft.getRate()) - (RobotMap.driveEncRight.getRate())) * pSteer), power*.5);
		System.out.println("encError " + encError);
		if (Math.abs(RobotMap.driveEncRight.getRate()) > Math.abs(Math.abs(RobotMap.driveEncLeft.getRate()))) {
			RobotMap.driveVictorRight1.set(-power + encError);
			RobotMap.driveVictorRight2.set(-power + encError);
			RobotMap.driveVictorLeft1.set(power);
			RobotMap.driveVictorLeft2.set(power);
		} else {
			RobotMap.driveVictorRight1.set(-power);
			RobotMap.driveVictorRight2.set(-power);
			RobotMap.driveVictorLeft1.set(power - encError);
			RobotMap.driveVictorLeft2.set(power - encError);
		}
		/*RobotMap.driveVictorRight1.set(-power);
		RobotMap.driveVictorRight2.set(-power);
		RobotMap.driveVictorLeft1.set(power);
		RobotMap.driveVictorLeft2.set(power);*/
    	}
    	//drivetrain4.tankDrive(rightDrive,rightDrive,squaredTank);
    	//RobotMap.Drivetrain4.tankDrive(rightDrive+((driveHeading-RobotMap.ahrs.getYaw())*gyroCorrectionPvalue/2), rightDrive-((driveHeading-RobotMap.ahrs.getYaw())*gyroCorrectionPvalue/2),false);
    	//drives forward while using gyro to keep straight, use driveheading to set what the "forward" angle is relative to the robot's front
    
    public void stopDrive(){
		RobotMap.driveVictorRight1.set(0);
		RobotMap.driveVictorRight2.set(0);
		RobotMap.driveVictorLeft1.set(0);
		RobotMap.driveVictorLeft2.set(0);
    }
    public void driveStraight(int encLeft, int encRight, double steerInput, SimPID encPID, SimPID steerPID){
    	steerPIDOutput = steerPID.calcPID(steerInput);
    	drivePIDOutput = encPID.calcPID(encLeft);
    	System.out.println("drivePIDOutput " + drivePIDOutput + " steerPIDOutput " + steerPIDOutput);
    	Robot.drivetrain.driveTank(drivePIDOutput+steerPIDOutput, -drivePIDOutput+steerPIDOutput);
    }
    public void resetDriveEncoders(){
    	RobotMap.driveEncLeft.reset();
    	RobotMap.driveEncRight.reset();
    }
    
    public void gyroTurn(double turnHeading, double gyroPvalue)
    {
    	/*rotateValue = ((turnHeading-RobotMap.ahrs.getYaw())*gyroPvalue);
    	if (rotateValue > 0.25){
    		rotateValue = 0.25;
    	}
    	else if (rotateValue < -0.25){
    		rotateValue = -0.25;
    	}*/
    	//RobotMap.Drivetrain4.tankDrive(rotateValue,-rotateValue,squaredTank);
    }
    
    public void gyroYawZero(){
    	RobotMap.ahrs.zeroYaw();
    }
    public void tankDrive(double speed){
    	//RobotMap.Drivetrain4.tankDrive(speed,speed,squaredTank);
    }
    public void tankTurn(double speed){
    	//RobotMap.Drivetrain4.tankDrive(speed,-speed,squaredTank);
    }
    
    public void initDefaultCommand() {
    }
}

