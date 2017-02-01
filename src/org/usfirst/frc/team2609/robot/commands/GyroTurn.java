package org.usfirst.frc.team2609.robot.commands;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team2609.robot.*;

import com.ctre.CANTalon.TalonControlMode;

public class GyroTurn extends Command {
	
	double driveTarget;
	
	public GyroTurn(double driveTarget, double drivePower, double driveHeading) {
        requires(Robot.drivetrain);
        driveTarget = this.driveTarget;
    }

    protected void initialize() {
    	RobotMap.driveTalonRight1.setProfile(1);
    	RobotMap.driveTalonRight1.setP(0.25);
    	RobotMap.driveTalonRight1.setI(0.00000);
    	RobotMap.driveTalonRight1.setD(0.000);
    	RobotMap.driveTalonRight1.setCloseLoopRampRate(24);
    	RobotMap.driveTalonRight1.changeControlMode(TalonControlMode.Speed);
    	RobotMap.driveTalonRight1.setAllowableClosedLoopErr(0);
		RobotMap.driveTalonRight1.configEncoderCodesPerRev(250);
    	RobotMap.driveTalonRight1.reverseOutput(true);
    	RobotMap.driveTalonRight1.reverseSensor(false);
    	
    	RobotMap.driveTalonLeft1.setProfile(1);
    	RobotMap.driveTalonLeft1.setP(0.25);
    	RobotMap.driveTalonLeft1.setI(0.00000);
    	RobotMap.driveTalonLeft1.setD(0.000);
    	RobotMap.driveTalonLeft1.setCloseLoopRampRate(24);
    	RobotMap.driveTalonLeft1.changeControlMode(TalonControlMode.Speed);
    	RobotMap.driveTalonLeft1.setAllowableClosedLoopErr(0);
		RobotMap.driveTalonLeft1.configEncoderCodesPerRev(250);
    	RobotMap.driveTalonLeft1.reverseOutput(false);
    	RobotMap.driveTalonLeft1.reverseSensor(true);
    	
    }
    
    protected void execute() {
        RobotMap.driveTalonRight1.set(driveTarget - (-RobotMap.ahrs.getYaw()));
        RobotMap.driveTalonLeft1.set(driveTarget - RobotMap.ahrs.getYaw());
    }

    protected boolean isFinished() {
    	return ((driveTarget - RobotMap.ahrs.getYaw())<0.1);
    }

    protected void end() {
    	Robot.drivetrain.stopDrive();
    }

    protected void interrupted() {
    	Robot.drivetrain.stopDrive();
    }
}
