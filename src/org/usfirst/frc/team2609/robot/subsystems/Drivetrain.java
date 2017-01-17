package org.usfirst.frc.team2609.robot.subsystems;
import org.usfirst.frc.team2609.robot.Robot;
import org.usfirst.frc.team2609.robot.RobotMap;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Drivetrain extends Subsystem {
	private static double drivePIDOutput = 0;
	private static double steerPIDOutput = 0;
	double[] defaultval = new double[0];
    
	public void trackLED(boolean state){
    	if (state){
    		RobotMap.ringLED.set(Relay.Value.kOn);
    	}
    	else{
    		RobotMap.ringLED.set(Relay.Value.kOff);
    	}
	}
    public void humanDrive(){
    }
    public void driveTank(double left, double right){
		RobotMap.driveVictorRight1.set(right);
		RobotMap.driveVictorRight2.set(right);
		RobotMap.driveVictorLeft1.set(left);
		RobotMap.driveVictorLeft2.set(left);
    }
    /*public void encoderDriveStraight(double driveTarget, double drivePower, double driveHeading){
		double encAvg = ((RobotMap.driveEncLeft.getDistance()+(RobotMap.driveEncRight.getDistance()*-1))*0.5);
    } */   
    public void stopDrive(){
		RobotMap.driveVictorRight1.set(0);
		RobotMap.driveVictorRight2.set(0);
		RobotMap.driveVictorLeft1.set(0);
		RobotMap.driveVictorLeft2.set(0);
    }
    public void disableDrive(){
		RobotMap.driveVictorRight1.disable();
		RobotMap.driveVictorRight2.disable();
		RobotMap.driveVictorLeft1.disable();
		RobotMap.driveVictorLeft2.disable();
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
    
    public void gyroCameraTurn(SimPID rightPID, SimPID leftPID)
    {
    	double rightValue = rightPID.calcPID(RobotMap.ahrs.getYaw());
    	double leftValue = leftPID.calcPID(RobotMap.ahrs.getYaw());
    	driveTank(leftValue, rightValue);
    }
    
    public void cameraPivotTurn(SimPID rightPID, SimPID pivotPID, double centerX)
    {
    	double rightValue = rightPID.calcPID(centerX);
    	double leftValue = pivotPID.calcPID(RobotMap.driveEncLeft.getDistance());
    	driveTank(leftValue, rightValue);
    }
    
    public void cameraPointTurn(SimPID turnPID, double centerX)
    {
    	double rightValue = turnPID.calcPID(centerX);
    	double leftValue = turnPID.calcPID(centerX);
    	driveTank(leftValue, rightValue);
    }
    
    public void gyroTurn(SimPID rightPID, SimPID leftPID)
    {
    	double leftValue = leftPID.calcPID(RobotMap.ahrs.getYaw());
    	double rightValue = rightPID.calcPID(RobotMap.ahrs.getYaw());
    	//double leftValue = leftPID.calcPID(RobotMap.driveEncLeft.getDistance());
    	driveTank(leftValue, rightValue);
    }
    
    public void gyroYawZero(){
    	RobotMap.ahrs.zeroYaw();
    }
    
    public void initDefaultCommand() {
    }
}

