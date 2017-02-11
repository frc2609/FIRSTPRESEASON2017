package org.usfirst.frc.team2609.robot.subsystems;
import org.usfirst.frc.team2609.robot.Robot;
import org.usfirst.frc.team2609.robot.RobotMap;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Drivetrain extends Subsystem {
	private static double drivePIDOutputLeft = 0;
	private static double drivePIDOutputRight = 0;
	private static double steerPIDOutput = 0;
	private static double throttle = 0;
	private static double turnValue = 0;
	private static double leftMtr = 0;
	private static double rightMtr = 0;
	private static double turningGain = 0;  // 0 is no change, larger has greater effect
	private static double deadZone = 0.15; //Deadband for the controller
	double[] defaultval = new double[0];
    
	public void toggleLED(){
    	if (RobotMap.ringLED.get() == Relay.Value.kOff){
    		RobotMap.ringLED.set(Relay.Value.kOn);
    	}
    	else{
    		RobotMap.ringLED.set(Relay.Value.kOff);
    	}
	}
	public void trackLED(boolean state){
    	if (state){
    		RobotMap.ringLED.set(Relay.Value.kOn);
    	}
    	else{
    		RobotMap.ringLED.set(Relay.Value.kOff);
    	}
	}
	
    public void humanDrive(){
    throttle = -RobotMap.Dandyboy.getRawAxis(1); 
    turnValue = RobotMap.Dandyboy.getRawAxis(0);
    deadZone = 0.15;
    turningGain = 0.0;
    if ((Math.abs(throttle)<deadZone) && (Math.abs(turnValue)<deadZone)){
    	throttle = 0;
    	turnValue = 0;
    }
	leftMtr = throttle + turnValue;
	rightMtr = throttle - turnValue;

	if (leftMtr > 1.0) {
        leftMtr = leftMtr + (-((leftMtr - 1.0) * turningGain)*(rightMtr));
    } else if (leftMtr < -1.0) {
    	leftMtr = leftMtr + (-((leftMtr + 1.0) * turningGain)*(rightMtr));
    }else { leftMtr = leftMtr; 
    }	
	
	if (rightMtr > 1.0) {
		rightMtr = rightMtr + (-((rightMtr - 1.0) * turningGain)*(leftMtr));
    } else if (rightMtr < -1.0) {
    	rightMtr = rightMtr + (-((rightMtr + 1.0) * turningGain)*(leftMtr));
    }
	RobotMap.driveTalonLeft1.changeControlMode(TalonControlMode.PercentVbus);
	RobotMap.driveTalonRight1.changeControlMode(TalonControlMode.PercentVbus);
	RobotMap.driveTalonLeft1.setVoltageRampRate(10000);
	RobotMap.driveTalonRight1.setVoltageRampRate(10000);
    RobotMap.driveTalonLeft1.set(leftMtr);
    RobotMap.driveTalonRight1.set(rightMtr);

    }
    
    
    
    public void humanDriveII(){
		double deadZone = 0.15;
		double X = -RobotMap.Dandyboy.getRawAxis(0);
        double Y = RobotMap.Dandyboy.getRawAxis(1);
        if ((Math.abs(RobotMap.Dandyboy.getRawAxis(0))<deadZone) && (Math.abs(RobotMap.Dandyboy.getRawAxis(1))<deadZone)){
        	X = 0;
        	Y = 0;
        }
        /*if (Math.abs(-driveStick.getRawAxis(1))<deadZone){
        	Y = 0;
        }*/
        double leftOutput;
        double rightOutput;
        if (X>0){
        	X = Math.pow(X, 2); //Square X input
        }
        else{
        	X = Math.pow(X, 2)*-1;
        }
        if (Y > 0) {
            if (X > 0.0) {
                leftOutput = Math.pow(Y, 1) - Math.pow(X, 1);
                rightOutput = Math.max(Math.pow(Y, 1), Math.pow(X, 1));
            } else {
                leftOutput = Math.max(Math.pow(Y, 1), -(Math.pow(X, 1)));
                rightOutput = Math.pow(Y, 1) + (Math.pow(X, 1));
            }
        } else{
            if (X > 0.0) {
                leftOutput = -Math.max(-(Math.pow(Y, 1)), Math.pow(X, 1));
                rightOutput = (Math.pow(Y, 1)) + Math.pow(X, 1);
            } else {
            	//this is also vvv imborktant
                leftOutput = (Math.pow(Y, 1)) - (Math.pow(X, 1));
                rightOutput = -Math.max(-(Math.pow(Y, 1)), -(Math.pow(X, 1)));
            }
            
        	RobotMap.driveTalonLeft1.changeControlMode(TalonControlMode.PercentVbus);
        	RobotMap.driveTalonRight1.changeControlMode(TalonControlMode.PercentVbus);
        	RobotMap.driveTalonLeft1.setVoltageRampRate(10000);
        	RobotMap.driveTalonRight1.setVoltageRampRate(10000);
        	RobotMap.driveTalonLeft1.set(-leftOutput);
        	RobotMap.driveTalonRight1.set(rightOutput);
        }
    }
    
    
    public void driveTank(double left, double right){
    	RobotMap.driveTalonLeft1.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.driveTalonRight1.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.driveTalonLeft1.setVoltageRampRate(10000);
    	RobotMap.driveTalonRight1.setVoltageRampRate(10000);
		RobotMap.driveTalonRight1.set(right);
		RobotMap.driveTalonLeft1.set(left);
    }
    /*public void encoderDriveStraight(double driveTarget, double drivePower, double driveHeading){
		double encAvg = ((RobotMap.driveEncLeft.getDistance()+(RobotMap.driveEncRight.getDistance()*-1))*0.5);
    } */   
    public void stopDrive(){
		RobotMap.driveTalonRight1.set(0);
		RobotMap.driveTalonLeft1.set(0);
    }
    public void disableDrive(){
		RobotMap.driveTalonRight1.disable();
		RobotMap.driveTalonLeft1.disable();
    }
    public void driveStraight(double encLeft, double encRight, double steerInput, SimPID encPIDLeft, SimPID encPIDRight, SimPID steerPID){
    	steerPIDOutput = -steerPID.calcPID(steerInput);
    	drivePIDOutputLeft = encPIDLeft.calcPID(encLeft);
    	drivePIDOutputRight = encPIDLeft.calcPID(encLeft);
    	System.out.println("drivePIDOutput " + drivePIDOutputLeft + drivePIDOutputRight + " steerPIDOutput " + steerPIDOutput);
    	RobotMap.driveTalonLeft1.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.driveTalonRight1.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.driveTalonLeft1.setVoltageRampRate(24);
    	RobotMap.driveTalonRight1.setVoltageRampRate(24);
    	RobotMap.driveTalonLeft1.set(drivePIDOutputLeft-steerPIDOutput);
        RobotMap.driveTalonRight1.set(drivePIDOutputLeft+steerPIDOutput);

    }
    public void driveLeft(double encLeft, SimPID encPIDLeft){
    	drivePIDOutputLeft = encPIDLeft.calcPID(encLeft);
    	System.out.println("drivePIDOutput " + drivePIDOutputLeft + drivePIDOutputRight + " steerPIDOutput " + steerPIDOutput);
    	RobotMap.driveTalonLeft1.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.driveTalonLeft1.setVoltageRampRate(24);
    	RobotMap.driveTalonLeft1.set(drivePIDOutputLeft);

    }
    public void driveRight(double encRight, SimPID encPIDRight){
    	drivePIDOutputLeft = encPIDRight.calcPID(encRight);
    	System.out.println("drivePIDOutput " + drivePIDOutputLeft + drivePIDOutputRight + " steerPIDOutput " + steerPIDOutput);
    	RobotMap.driveTalonRight1.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.driveTalonRight1.setVoltageRampRate(24);
    	RobotMap.driveTalonRight1.set(drivePIDOutputRight);

    }
    public void resetDriveEncoders(){
    	RobotMap.driveTalonLeft1.setEncPosition(0);
    	RobotMap.driveTalonRight1.setEncPosition(0);
    }
    
    public void gyroCameraTurn(SimPID rightPID, SimPID leftPID)
    {
    	RobotMap.driveTalonLeft1.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.driveTalonRight1.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.driveTalonLeft1.setVoltageRampRate(24);
    	RobotMap.driveTalonRight1.setVoltageRampRate(24);
    	double rightValue = rightPID.calcPID(RobotMap.ahrs.getYaw());
    	double leftValue = leftPID.calcPID(RobotMap.ahrs.getYaw());
    	driveTank(leftValue, rightValue);
    }
    
    public void gyroTurn(SimPID rightPID, SimPID leftPID)
    {
    	RobotMap.driveTalonLeft1.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.driveTalonRight1.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.driveTalonLeft1.setVoltageRampRate(24);
    	RobotMap.driveTalonRight1.setVoltageRampRate(24);
    	double leftValue = leftPID.calcPID(RobotMap.ahrs.getYaw());
    	double rightValue = -rightPID.calcPID(RobotMap.ahrs.getYaw());
    	driveTank(leftValue, rightValue);
    }
    
    public void gyroYawZero(){
    	RobotMap.ahrs.zeroYaw();
    }
    
    public void initDefaultCommand() {
    }
}

