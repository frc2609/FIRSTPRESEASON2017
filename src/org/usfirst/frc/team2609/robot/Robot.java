
package org.usfirst.frc.team2609.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

import java.io.IOException;

import org.usfirst.frc.team2609.robot.commands.Auto1;
import org.usfirst.frc.team2609.robot.commands.HockeyStick;
import org.usfirst.frc.team2609.robot.commands.Swivel;
import org.usfirst.frc.team2609.robot.subsystems.Drivetrain;
import org.usfirst.frc.team2609.robot.subsystems.Logger;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	public static Drivetrain drivetrain;
	public static OI oi;
	private Logger logger;
	
    Command autonomousCommand;
    SendableChooser chooser;
    public static NetworkTable table;
    
    //public static double centerX = 0;
    
    public void robotInit() {
		oi = new OI();
		RobotMap.init();// put this here when imports don't work / robots don't quit
		RobotMap.driveTalonLeft2.changeControlMode(TalonControlMode.Follower);
		RobotMap.driveTalonRight2.changeControlMode(TalonControlMode.Follower);
		RobotMap.driveTalonLeft2.set(4);
		RobotMap.driveTalonRight2.set(1);
		SmartDashboard.putNumber("Drive P: ", 0.003);
    	SmartDashboard.putNumber("Drive I: ", 0.001);
    	SmartDashboard.putNumber("Drive D: ", 0.01);
    	SmartDashboard.putNumber("Drive Max: ", 0.8);
    	SmartDashboard.putNumber("Drive Eps: ", 1);
    	
		SmartDashboard.putNumber("Gyro P: ", 0.02);
    	SmartDashboard.putNumber("Gyro I: ", 0.000);
    	SmartDashboard.putNumber("Gyro D: ", 0.0);
    	SmartDashboard.putNumber("Gyro Max: ", 0.2);
		
        SmartDashboard.putNumber("turn P: ",0.03);
        SmartDashboard.putNumber("turn I: ",0.001);
        SmartDashboard.putNumber("turn D: ",0.0);
        SmartDashboard.putNumber("turn Max: ",0.25);
        SmartDashboard.putNumber("turn Eps: ",1);
        
        SmartDashboard.putNumber("camera P: ",0.01);
        SmartDashboard.putNumber("camera I: ",0.000);
        SmartDashboard.putNumber("camera D: ",0.0);
        SmartDashboard.putNumber("camera Max: ",0.3);
        SmartDashboard.putNumber("camera Eps: ",1);
        SmartDashboard.putNumber("camera Delay: ",0.01);
        
        SmartDashboard.putNumber("gyroCamera P: ",0.03);
        SmartDashboard.putNumber("gyroCamera I: ",0.001);
        SmartDashboard.putNumber("gyroCamera D: ",0.0);
        SmartDashboard.putNumber("gyroCamera Max: ",0.5);
        SmartDashboard.putNumber("gyroCamera Eps: ",1);
        boolean valueVision = false;
        SmartDashboard.putBoolean("vision", valueVision);
        SmartDashboard.putNumber("Launcher Speed", 0);

        
        
		drivetrain = new Drivetrain();
        chooser = new SendableChooser();
        chooser.addDefault("Default Auto", new Auto1());
        chooser.addObject("Hockey Stick", new HockeyStick());
        chooser.addObject("Swivel", new Swivel());
        SmartDashboard.putData("Auto mode", chooser);
        this.logger = logger.getInstance();
//      chooser.addObject("My Auto", new MyAutoCommand());
        
        table = NetworkTable.getTable("RaspberryPi");
        

    }
	
    public void disabledInit(){

    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		this.logger.close();
		SmartDashboard.putBoolean("DIO4", RobotMap.dio4.get());
		SmartDashboard.putNumber("Gyro getAngle", RobotMap.ahrs.getAngle());
		SmartDashboard.putNumber("Gyro getYaw", RobotMap.ahrs.getYaw());
		SmartDashboard.putNumber("driveEncLeft.getDistance()", RobotMap.driveTalonLeft1.getPosition());
		SmartDashboard.putNumber("driveEncRight.getDistance()", RobotMap.driveTalonRight1.getPosition());
	}
    public void autonomousInit() {
        Robot.drivetrain.gyroYawZero();
        Robot.drivetrain.resetDriveEncoders();
        autonomousCommand = (Command) chooser.getSelected();
        this.logger.openFile();
        if (autonomousCommand != null) autonomousCommand.start();
        
    }

    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        SmartDashboard.putNumber("Gyro getYaw", RobotMap.ahrs.getYaw());
        //SmartDashboard.putNumber("driveEncLeft.getDistance()", RobotMap.driveEncLeft.getDistance());
		//SmartDashboard.putNumber("driveEncRight.getDistance()", RobotMap.driveEncRight.getDistance());
		//SmartDashboard.putNumber("driveEncLeft.getRate()", RobotMap.driveEncLeft.getRate());
		//SmartDashboard.putNumber("driveEncRight.getRate()", RobotMap.driveEncRight.getRate());
		SmartDashboard.putNumber("driveVictorLeft1.get()", RobotMap.driveTalonLeft1.get());
		SmartDashboard.putNumber("driveVictorRight1.get()", RobotMap.driveTalonRight1.get());
		
        this.logger.logAll(); // write to logs
        
		
    }

    public void teleopInit() {
        if (autonomousCommand != null) autonomousCommand.cancel();
        //EncReset(); todo
        Robot.drivetrain.resetDriveEncoders();
        Robot.drivetrain.gyroYawZero();
        this.logger.openFile();
        //RobotMap.serialport.reset();
		//RobotMap.serialport.writeString(":85");
		
    }

    public void teleopPeriodic() {
    	SmartDashboard.putNumber("Gyro getYaw", RobotMap.ahrs.getYaw());
    	SmartDashboard.putNumber("driveEncLeft.getDistance()", RobotMap.driveTalonLeft1.getPosition());
		SmartDashboard.putNumber("driveEncRight.getDistance()", RobotMap.driveTalonRight1.getPosition());
		Scheduler.getInstance().run();
        this.logger.logAll(); // write to logs
        Joystick driveStick = new Joystick(0);
		double deadZone = 0.15;
		double X = -driveStick.getRawAxis(0);
        double Y = -driveStick.getRawAxis(1);
        if (Math.abs(-driveStick.getRawAxis(0))<deadZone){
        	X = 0;
        }
        if (Math.abs(-driveStick.getRawAxis(1))<deadZone){
        	Y = 0;
        }
        double leftOutput;
        double rightOutput;
        if (Y > 0) {
            if (X > 0.0) {
                leftOutput = Math.pow(Y, 1) - Math.pow(X, 1);
                rightOutput = Math.max(Math.pow(Y, 1), Math.pow(X, 1));
            } else {
                leftOutput = Math.max(Math.pow(Y, 1), -(Math.pow(X, 1)));
                rightOutput = Math.pow(Y, 1) + (Math.pow(X, 1));
            }
        } else{
            if (X > 0.0) {
                leftOutput = -Math.max(-(Math.pow(Y, 1)), Math.pow(X, 1));
                rightOutput = (Math.pow(Y, 1)) + Math.pow(X, 1);
            } else {
                leftOutput = (Math.pow(Y, 1)) - (Math.pow(X, 1));
                rightOutput = -Math.max(-(Math.pow(Y, 1)), -(Math.pow(X, 1)));
            }
            	

        }

        	
        
            RobotMap.driveTalonLeft1.set(leftOutput);
            RobotMap.driveTalonRight1.set(-rightOutput);
//            RobotMap.launcherVictor.set(SmartDashboard.getNumber("Launcher Speed", 0));
            
            
}
    
    public void testPeriodic() {
        LiveWindow.run();
    }
}
