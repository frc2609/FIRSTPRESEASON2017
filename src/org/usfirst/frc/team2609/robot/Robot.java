
package org.usfirst.frc.team2609.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.usfirst.frc.team2609.robot.commands.Auto1;
import org.usfirst.frc.team2609.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team2609.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	public static Drivetrain drivetrain;
	//public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	public static OI oi;

    Command autonomousCommand;
    SendableChooser chooser;

    public void robotInit() {
		oi = new OI();
		RobotMap.init();// put this here when imports don't work / robots don't quit
		SmartDashboard.putNumber("Gyro P: ", 0.01);
    	SmartDashboard.putNumber("Gyro I: ", 0.001);
    	SmartDashboard.putNumber("Gyro D: ", 0.0);
    	SmartDashboard.putNumber("Gyro Max: ", 0.2);
		SmartDashboard.putNumber("Drive P: ", 0.002);
    	SmartDashboard.putNumber("Drive I: ", 0.001);
    	SmartDashboard.putNumber("Drive D: ", 0.0);
    	SmartDashboard.putNumber("Drive Max: ", 0.8);
    	SmartDashboard.putNumber("Drive Eps: ", 1);
		drivetrain = new Drivetrain();
        chooser = new SendableChooser();
        chooser.addDefault("Default Auto", new Auto1());
		SmartDashboard.putData("Auto mode", chooser);
//      chooser.addObject("My Auto", new MyAutoCommand());

    }
	
    public void disabledInit(){

    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		SmartDashboard.putNumber("Gyro getAngle", RobotMap.ahrs.getAngle());
		SmartDashboard.putNumber("Gyro getYaw", RobotMap.ahrs.getYaw());
		SmartDashboard.putNumber("driveEncLeft.getDistance()", RobotMap.driveEncLeft.getDistance());
		SmartDashboard.putNumber("driveEncRight.getDistance()", RobotMap.driveEncRight.getDistance());
	}
    public void autonomousInit() {
        autonomousCommand = (Command) chooser.getSelected();
        Robot.drivetrain.gyroYawZero();
        Robot.drivetrain.resetDriveEncoders();
        String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		switch(autoSelected) {
		case "My Auto":
			autonomousCommand = new Auto1();
			break;
		case "Default Auto":
		default:
			autonomousCommand = new Auto1();
			break;
		} 
    	
    	// schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();
    }

    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        SmartDashboard.putNumber("Gyro getYaw", RobotMap.ahrs.getYaw());
        SmartDashboard.putNumber("driveEncLeft.getDistance()", RobotMap.driveEncLeft.getDistance());
		SmartDashboard.putNumber("driveEncRight.getDistance()", RobotMap.driveEncRight.getDistance());
		SmartDashboard.putNumber("driveEncLeft.getRate()", RobotMap.driveEncLeft.getRate());
		SmartDashboard.putNumber("driveEncRight.getRate()", RobotMap.driveEncRight.getRate());
		SmartDashboard.putNumber("driveVictorLeft1.get()", RobotMap.driveVictorLeft1.get());
		SmartDashboard.putNumber("driveVictorRight1.get()", RobotMap.driveVictorRight1.get());
		
    }

    public void teleopInit() {
        if (autonomousCommand != null) autonomousCommand.cancel();
        //EncReset(); todo
        Robot.drivetrain.resetDriveEncoders();
        Robot.drivetrain.gyroYawZero();
        //RobotMap.serialport.reset();
		//RobotMap.serialport.writeString(":85");
		
    }

    public void teleopPeriodic() {
    	SmartDashboard.putNumber("Gyro getYaw", RobotMap.ahrs.getYaw());
		SmartDashboard.putNumber("driveEncLeft.getDistance()", RobotMap.driveEncLeft.getDistance());
		SmartDashboard.putNumber("driveEncRight.getDistance()", RobotMap.driveEncRight.getDistance());
        Scheduler.getInstance().run();
        Joystick driveStick = new Joystick(0);
		double deadZone = 0.1;
        double X = -driveStick.getRawAxis(0);
        double Y = -driveStick.getRawAxis(1);
        if (Math.abs(X)<deadZone){
        	X = 0;
        }
        if (Math.abs(Y)<deadZone){
        	Y = 0;
        }
        double leftOutput;
        double rightOutput;
        if (Y > 0) {
            if (X > 0.0) {
                leftOutput = Y - X;
                rightOutput = Math.max(Y, X);
            } else {
                leftOutput = Math.max(Y, -X);
                rightOutput = Y + X;
            }
        } else{
            if (X > 0.0) {
                leftOutput = -Math.max(-Y, X);
                rightOutput = Y + X;
            } else {
                leftOutput = Y - X;
                rightOutput = -Math.max(-Y, -X);
            }
            	

        }

        	
        
            RobotMap.driveVictorLeft1.set(leftOutput);
            RobotMap.driveVictorLeft2.set(leftOutput);
            RobotMap.driveVictorRight1.set(-rightOutput);
            RobotMap.driveVictorRight2.set(-rightOutput);
}
    
    public void testPeriodic() {
        LiveWindow.run();
    }
}
