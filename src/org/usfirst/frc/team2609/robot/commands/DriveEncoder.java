package org.usfirst.frc.team2609.robot.commands;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2609.robot.*;

public class DriveEncoder extends Command {
	private double driveTarget;
	private double driveHeading;
	private double drivePower;
	//private double driveTarget;
	// Number of minutes wasted on 1s vs l's:
	// 30min 10/18/2016
	// 30min total
	
	public DriveEncoder(double driveTarget, double drivePower, double driveHeading) {
        requires(Robot.drivetrain);
        this.driveTarget = driveTarget;
        this.drivePower = drivePower;
        this.driveHeading = driveHeading;
    }

    protected void initialize() {
    	//Robot.drivetrain.encoderDriveStraight(driveTarget, drivePower, driveHeading, init=True);
    	System.out.println("initialize.drivePower" + drivePower);
		if (driveTarget < RobotMap.driveEncLeft.getDistance()) {
			drivePower = Math.abs(drivePower)*-1;
		}
		else{
			drivePower = Math.abs(drivePower);
		}
		System.out.println("initialize.drivePower " + drivePower);
    }

    protected void execute() {
    	Robot.drivetrain.encoderDriveStraight(driveTarget, drivePower, driveHeading); //init=False
    }

    protected boolean isFinished() {
    	double distTravel = (RobotMap.driveEncLeft.getDistance()+((RobotMap.driveEncRight.getDistance()*-1)))*0.5;
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
