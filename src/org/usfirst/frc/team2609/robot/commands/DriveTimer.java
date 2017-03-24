package org.usfirst.frc.team2609.robot.commands;

import org.usfirst.frc.team2609.robot.Robot;
import org.usfirst.frc.team2609.robot.RobotMap;
import org.usfirst.frc.team2609.robot.subsystems.SimPID;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTimer extends Command {
	double time;
	double speed;
	SimPID steeringPID;
	double gyroP = 0;
	double gyroI = 0;
	double gyroD = 0;
	double gyroMax = 0;

    public DriveTimer(double speed, double time) {
    	this.time = time;
    	this.speed = speed;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        this.steeringPID = new SimPID();
    	steeringPID.resetPreviousVal();
        this.steeringPID.setDesiredValue(0);
        gyroP = (double)SmartDashboard.getNumber("Gyro P: ",0);
        gyroI = (double)SmartDashboard.getNumber("Gyro I: ",0);
        gyroD = (double)SmartDashboard.getNumber("Gyro D: ",0);
        gyroMax = (double)SmartDashboard.getNumber("Gyro Max: ",0);
        this.steeringPID.setConstants(gyroP, gyroI, gyroD);
        this.steeringPID.setMaxOutput(gyroMax);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double gyroYaw = RobotMap.ahrs.getYaw();
    	Robot.drivetrain.driveStraightTimer(gyroYaw, steeringPID, speed);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return timeSinceInitialized()>time;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.stopDrive();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
