package org.usfirst.frc.team2609.robot.commands;
import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team2609.robot.*;

public class DriveEncoder extends Command {
	private double driveTarget;
	private double driveHeading;
	private double drivePower;
	private SimPID drivePID;
	private SimPID headingPID;
	private SimPID steeringPID;
	private int doneRange = 0;
	public static double drivePIDOutput = 0;
	public static double steerPIDOutput = 0;
	//private double driveTarget;
	// Number of minutes wasted on 1s vs ls:
	// 30min 10/18/2016
	// 30min total
	
	public DriveEncoder(double driveTarget, double drivePower, double driveHeading) {
        requires(Robot.drivetrain);
        this.steeringPID = new SimPID();
        this.steeringPID.setDesiredValue(0);
        this.steeringPID.setConstants(0.0005, 0.0001, 0);
        this.steeringPID.setMaxOutput(0.6);
        this.drivePID = new SimPID();
        this.drivePID.setDesiredValue(driveTarget);
        this.drivePID.setConstants(0.0022, 0.001, 0);
        this.drivePID.setMaxOutput(0.6);
        this.drivePID.setDoneRange(10);
        
        this.driveTarget = driveTarget;
        this.drivePower = drivePower;
        this.driveHeading = driveHeading;
        
    }

    protected void initialize() {
    	steeringPID.resetPreviousVal();
    	drivePID.resetPreviousVal();
    	//Robot.drivetrain.encoderDriveStraight(driveTarget, drivePower, driveHeading, init=True);
    	System.out.println("initialize.drivePower" + drivePower);
		/*if (driveTarget < RobotMap.driveEncLeft.getDistance()) {
			drivePower = Math.abs(drivePower)*-1;
		}
		else{
			drivePower = Math.abs(drivePower);
		}*/
		System.out.println("initialize.drivePower " + drivePower);
		
    }

    protected void execute() {
    	
    	//Robot.drivetrain.encoderDriveStraight(driveTarget, drivePower, driveHeading); //init=False
    	double encError = Math.abs((Math.abs(RobotMap.driveEncLeft.getRate()) - Math.abs(RobotMap.driveEncRight.getRate())));
    	steerPIDOutput = this.steeringPID.calcPID(encError);
    	drivePIDOutput = this.drivePID.calcPID(RobotMap.driveEncLeft.getDistance());
    	System.out.println("drivePIDOutput " + drivePIDOutput);
    	System.out.println("steerPIDOutput " + steerPIDOutput);
    	Robot.drivetrain.test1(drivePIDOutput-steerPIDOutput, -drivePIDOutput+steerPIDOutput);
    }

    protected boolean isFinished() {
    	return drivePID.isDone();
    	/*double distTravel = (RobotMap.driveEncLeft.getDistance()+((RobotMap.driveEncRight.getDistance()*-1)))*0.5;
    	System.out.println("distTravel " + distTravel);
    	if ( (distTravel > driveTarget -5) && (distTravel < driveTarget +5)){
    		System.out.println("!!!!!!!!!distTravel " + distTravel);
    		return true;
    	}
		else{
			
			return false;
		}
    	/*distanceTraveled = Math.min(Math.abs(RobotMap.wheelEncoderRight.getDistance()),Math.abs(RobotMap.wheelEncoderLeft.getDistance()));
    	if (distanceTraveled > Math.abs(driveDistanceSetpoint)-1){//Find the encoder with the least distance traveled
    		return true;
    	}
		else{
			return false;
		}*/
    }

    protected void end() {
    	Robot.drivetrain.stopDrive();
    }

    protected void interrupted() {
    }
}
