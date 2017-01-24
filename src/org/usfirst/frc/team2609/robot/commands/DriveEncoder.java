package org.usfirst.frc.team2609.robot.commands;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team2609.robot.*;
import org.usfirst.frc.team2609.robot.subsystems.SimPID;

import com.ctre.CANTalon.TalonControlMode;

public class DriveEncoder extends Command {
	
	double driveTarget;
	
	public DriveEncoder(double driveTarget, double drivePower, double driveHeading) {
        requires(Robot.drivetrain);
        driveTarget = this.driveTarget;
    }

    protected void initialize() {
    	RobotMap.driveTalonRight1.setProfile(1);
    	RobotMap.driveTalonRight1.setP(1.0);
    	RobotMap.driveTalonRight1.setI(0.001);
    	RobotMap.driveTalonRight1.setD(0.0);
    	//RobotMap.driveTalonRight1.setCloseLoopRampRate(1);
    	RobotMap.driveTalonRight1.changeControlMode(TalonControlMode.Position);
    	RobotMap.driveTalonRight1.setAllowableClosedLoopErr(0);
    	
    	RobotMap.driveTalonLeft1.setProfile(1);
    	RobotMap.driveTalonLeft1.setP(1.0);
    	RobotMap.driveTalonLeft1.setI(0.001);
    	RobotMap.driveTalonLeft1.setD(0.0);
    	//RobotMap.driveTalonLeft1.setCloseLoopRampRate(1);
    	RobotMap.driveTalonLeft1.changeControlMode(TalonControlMode.Position);
    	RobotMap.driveTalonLeft1.setAllowableClosedLoopErr(0);
    	
    }
    
    protected void execute() {
    	Robot.drivetrain.driveStraight(driveTarget, driveTarget, 0);
    	//double encError = Math.abs((Math.abs(RobotMap.driveEncLeft.getRate()) - Math.abs(RobotMap.driveEncRight.getRate())));
    	
    	//double gyroYaw = RobotMap.ahrs.getYaw();
    	//Robot.drivetrain.driveStraight(RobotMap.driveTalonLeft1.getPosition(), RobotMap.driveTalonRight1.getPosition(), gyroYaw);	
    }

    protected boolean isFinished() {
    	return false;
    }

    protected void end() {
    	Robot.drivetrain.stopDrive();
    }

    protected void interrupted() {
    	Robot.drivetrain.stopDrive();
    }
}
