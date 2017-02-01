package org.usfirst.frc.team2609.robot.commands;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team2609.robot.RobotMap;
import org.usfirst.frc.team2609.robot.Robot;


import com.ctre.CANTalon.TalonControlMode;

public class DriveEncoder extends Command {
	
	double driveTarget;
	double driveHeading;
	double t1 = Timer.getFPGATimestamp();
    double t2 = 0;
    double d1 = 0;
    double d2 = 0;
    double gyroErrorSum = 0;
	
	public DriveEncoder(double driveTarget, double drivePower, double driveHeading) {
        requires(Robot.drivetrain);
        driveTarget = this.driveTarget;
        driveHeading = this.driveHeading;
    }

    protected void initialize() {
    	RobotMap.driveTalonRight1.setProfile(1);
    	RobotMap.driveTalonRight1.setP(0.25);
    	RobotMap.driveTalonRight1.setI(0.00001);
    	RobotMap.driveTalonRight1.setD(0.001);
    	RobotMap.driveTalonRight1.setCloseLoopRampRate(24);
    	RobotMap.driveTalonRight1.changeControlMode(TalonControlMode.Position);
    	RobotMap.driveTalonRight1.setAllowableClosedLoopErr(0);
		RobotMap.driveTalonRight1.configEncoderCodesPerRev(250);
    	RobotMap.driveTalonRight1.reverseOutput(true);
    	RobotMap.driveTalonRight1.reverseSensor(false);
    	
    	RobotMap.driveTalonLeft1.setProfile(1);
    	RobotMap.driveTalonLeft1.setP(0.25);
    	RobotMap.driveTalonLeft1.setI(0.00001);
    	RobotMap.driveTalonLeft1.setD(0.001);
    	RobotMap.driveTalonLeft1.setCloseLoopRampRate(24);
    	RobotMap.driveTalonLeft1.changeControlMode(TalonControlMode.Position);
    	RobotMap.driveTalonLeft1.setAllowableClosedLoopErr(0);
		RobotMap.driveTalonLeft1.configEncoderCodesPerRev(250);
    	RobotMap.driveTalonLeft1.reverseOutput(false);
    	RobotMap.driveTalonLeft1.reverseSensor(true);
    	
    	t1 = Timer.getFPGATimestamp();
        t2 = 0;
        d1 = 0;
        d2 = 0;
        gyroErrorSum = 0;
        
    }
    
    protected void execute() {
    	t2 = Timer.getFPGATimestamp();
    	d2 = driveHeading - RobotMap.ahrs.getYaw();
    	
    	
    	gyroErrorSum = gyroErrorSum + (((d2 + d1)/2)*(t2 - t1));
    	
    	
    	RobotMap.driveTalonRight1.setF(-(gyroErrorSum*0.002 + d2*0.005));
    	RobotMap.driveTalonLeft1.setF(gyroErrorSum*0.002 + d2*0.005);
        RobotMap.driveTalonRight1.set(4); //70'' 85'' 60deg
        RobotMap.driveTalonLeft1.set(4);
        
    	t1 = t2;
    	d1 = d2;
    	
    	SmartDashboard.putNumber("dt", t2 - t1);
    	SmartDashboard.putNumber("gyroErrorSum", gyroErrorSum);
    	//double gyroYaw = RobotMap.ahrs.getYaw();
    	//Robot.drivetrain.driveStraight(RobotMap.driveTalonLeft1.getPosition(), RobotMap.driveTalonRight1.getPosition(), gyroYaw);	
    }

    protected boolean isFinished() {
    	return false;//(RobotMap.driveTalonLeft1.getClosedLoopError()<1 && RobotMap.driveTalonRight1.getClosedLoopError()<1);
    }

    protected void end() {
    	Robot.drivetrain.stopDrive();
    }

    protected void interrupted() {
    	Robot.drivetrain.stopDrive();
    }
}
