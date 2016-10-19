package org.usfirst.frc.team2609.robot.commands;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team2609.robot.*;
import org.usfirst.frc.team2609.robot.subsystems.SimPID;

public class DriveEncoder extends Command {
	private double driveTarget;
	private double driveHeading;
	private double drivePower;
	private SimPID drivePID;
	private SimPID headingPID;
	private SimPID steeringPID;
	private int doneRange = 0;
	private double gyroP = 0;
	double gyroI;
	double gyroD;
	double driveP;
	double driveI;
	double driveD;
	//private double driveTarget;
	// Number of minutes wasted on 1s vs ls:
	// 30min 10/18/2016
	// 30min total
	
	public DriveEncoder(double driveTarget, double drivePower, double driveHeading) {
        requires(Robot.drivetrain);
        gyroP = (double)SmartDashboard.getNumber("Gyro P: ");
        gyroI = (double)SmartDashboard.getNumber("Gyro I: ");
        gyroD = (double)SmartDashboard.getNumber("Gyro D: ");
        driveP = (double)SmartDashboard.getNumber("Drive P: ");
        driveI = (double)SmartDashboard.getNumber("Drive I: ");
        driveD = (double)SmartDashboard.getNumber("Drive D: ");
        this.steeringPID = new SimPID();
        this.steeringPID.setDesiredValue(0);
        this.steeringPID.setConstants(gyroP,gyroI, driveD);
        this.steeringPID.setMaxOutput(0.6);
        this.drivePID = new SimPID();
        this.drivePID.setDesiredValue(driveTarget);
        this.drivePID.setConstants(driveP, driveI, driveD);
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
		System.out.println("initialize.drivePower " + drivePower);
    }

    protected void execute() {
    	//Robot.drivetrain.encoderDriveStraight(driveTarget, drivePower, driveHeading); //init=False
    	//double encError = Math.abs((Math.abs(RobotMap.driveEncLeft.getRate()) - Math.abs(RobotMap.driveEncRight.getRate())));
    	double gyroYaw = RobotMap.ahrs.getYaw();
    	Robot.drivetrain.driveStraight(RobotMap.driveEncLeft.get(), RobotMap.driveEncRight.get(), gyroYaw, drivePID, steeringPID);	
    }

    protected boolean isFinished() {
    	return drivePID.isDone();
    	
    }

    protected void end() {
    	Robot.drivetrain.stopDrive();
    }

    protected void interrupted() {
    }
}
