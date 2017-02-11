package org.usfirst.frc.team2609.robot.commands;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team2609.robot.*;
import org.usfirst.frc.team2609.robot.subsystems.SimPID;

public class DriveEncoderLeft extends Command {
	SimPID drivePIDLeft;
	double driveP = 0;
	double driveI = 0;
	double driveD = 0;
	double drivePower = 0;
	double driveEps = 0;
	double driveTarget = 0;
	//private double driveTarget;
	// Number of minutes wasted on 1s vs ls:
	// 30min 10/18/2016
	// 30min total
	
	public DriveEncoderLeft(double driveTarget, double drivePower) {
        requires(Robot.drivetrain);
        this.driveTarget = driveTarget;
        this.drivePower = drivePower;
    }

    protected void initialize(){
        this.drivePIDLeft = new SimPID();
    	drivePIDLeft.resetPreviousVal();
        this.drivePIDLeft.setDesiredValue(driveTarget/(Math.PI*6));
        driveP = (double)SmartDashboard.getNumber("Drive P: ",0);
        driveI = (double)SmartDashboard.getNumber("Drive I: ",0);
        driveD = (double)SmartDashboard.getNumber("Drive D: ",0);
        driveEps = (double)SmartDashboard.getNumber("Drive Eps: ",0);
        this.drivePIDLeft.setConstants(driveP, driveI, driveD);
        this.drivePIDLeft.setMaxOutput(drivePower);
        this.drivePIDLeft.setDoneRange(1/(Math.PI*6));
        this.drivePIDLeft.setMinDoneCycles(100);
        this.drivePIDLeft.setErrorEpsilon(driveEps);
        }

    protected void execute() {
    	Robot.drivetrain.driveLeft(RobotMap.driveTalonLeft1.getPosition(), drivePIDLeft);	
    }

    protected boolean isFinished() {
    	return drivePIDLeft.isDone();
    }

    protected void end() {
    	Robot.drivetrain.stopDrive();
    }

    protected void interrupted() {
    	Robot.drivetrain.stopDrive();
    }
}
