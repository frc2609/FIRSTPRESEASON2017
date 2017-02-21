package org.usfirst.frc.team2609.robot.commands;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team2609.robot.*;
import org.usfirst.frc.team2609.robot.subsystems.SimPID;

public class DriveEncoderVariable2 extends Command {
	SimPID drivePIDLeft;
	SimPID drivePIDRight;
	SimPID steeringPID;
	double gyroP = 0;
	double gyroI = 0;
	double gyroD = 0;
	double gyroMax = 0;
	double driveP = 0;
	double driveI = 0;
	double driveD = 0;
	double driveMax = 0;
	double drivePower = 0;
	double driveEps = 0;
	double autonDistance2 = 0;
	double autonHeading2 = 0;
	double driveDR = 0;
	int driveDC = 0;
	//private double driveTarget;
	// Number of minutes wasted on 1s vs ls:
	// 30min 10/18/2016
	// 30min total
	
	public DriveEncoderVariable2(double drivePower) {
        requires(Robot.drivetrain);
        this.drivePower = drivePower;
    }

    protected void initialize(){
        this.drivePIDLeft = new SimPID();
        this.drivePIDRight = new SimPID();
        this.steeringPID = new SimPID();
		autonDistance2 = (double)SmartDashboard.getNumber("auton distance 2: ",0);
		autonHeading2 = (double)SmartDashboard.getNumber("auton heading 2: ",0);
    	steeringPID.resetPreviousVal();
    	drivePIDLeft.resetPreviousVal();
    	drivePIDRight.resetPreviousVal();
        this.steeringPID.setDesiredValue(autonHeading2);
        this.drivePIDLeft.setDesiredValue(autonDistance2);
        this.drivePIDRight.setDesiredValue(autonDistance2);
        gyroP = (double)SmartDashboard.getNumber("Gyro P: ",0);
        gyroI = (double)SmartDashboard.getNumber("Gyro I: ",0);
        gyroD = (double)SmartDashboard.getNumber("Gyro D: ",0);
        gyroMax = (double)SmartDashboard.getNumber("Gyro Max: ",0);
        this.steeringPID.setConstants(gyroP, gyroI, gyroD);
        this.steeringPID.setMaxOutput(gyroMax);
        driveP = (double)SmartDashboard.getNumber("Drive P: ",0);
        driveI = (double)SmartDashboard.getNumber("Drive I: ",0);
        driveD = (double)SmartDashboard.getNumber("Drive D: ",0);
        driveMax = (double)SmartDashboard.getNumber("Drive Max: ",0);
;
;
        driveDC = (int)SmartDashboard.getNumber("Drive DC: ",0);
        this.drivePIDLeft.setConstants(driveP, driveI, driveD);
        this.drivePIDLeft.setMaxOutput(drivePower*driveMax);
        this.drivePIDLeft.setDoneRange(driveDR);
        this.drivePIDLeft.setMinDoneCycles(driveDC);
        this.drivePIDLeft.setErrorEpsilon(driveEps);
        this.drivePIDRight.setConstants(driveP, driveI, driveD);
        this.drivePIDRight.setMaxOutput(drivePower*driveMax);
        this.drivePIDRight.setDoneRange(driveDR);
        this.drivePIDRight.setMinDoneCycles(driveDC);
        this.drivePIDRight.setErrorEpsilon(driveEps);
        }

    protected void execute() {
    	//double encError = Math.abs((Math.abs(RobotMap.driveEncLeft.getRate()) - Math.abs(RobotMap.driveEncRight.getRate())));
    	double gyroYaw = RobotMap.ahrs.getYaw();
    	Robot.drivetrain.driveStraight(RobotMap.driveTalonLeft1.getPosition(), RobotMap.driveTalonRight1.getPosition(), gyroYaw, drivePIDLeft, drivePIDRight, steeringPID);	
    }

    protected boolean isFinished() {
    	return drivePIDLeft.isDone();//&& drivePIDRight.isDone(); //Should wait for both PID to finish
    }

    protected void end() {
    	Robot.drivetrain.stopDrive();
    }

    protected void interrupted() {
    	Robot.drivetrain.stopDrive();
    }
}