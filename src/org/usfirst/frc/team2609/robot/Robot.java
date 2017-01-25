
package org.usfirst.frc.team2609.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
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
    
    public static double t1 = Timer.getFPGATimestamp();
    public static double t2 = 0;
    public static double d1 = 0;
    public static double d2 = 0;
    public static double gyroErrorSum = 0;
    public static double gyroTarget = 0;
    
    //public static double centerX = 0;
    
    public void robotInit() {
		oi = new OI();
		RobotMap.init();// put this here when imports don't work / robots don't quit
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
        
        RobotMap.FRCGyro.calibrate();

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
        //autonomousCommand = (Command) chooser.getSelected();
        //this.logger.openFile();
        if (autonomousCommand != null) autonomousCommand.start();
        
    	RobotMap.driveTalonRight1.setProfile(1);
    	RobotMap.driveTalonRight1.setP(0.2);
    	RobotMap.driveTalonRight1.setI(0.00);
    	RobotMap.driveTalonRight1.setD(0.0);
    	RobotMap.driveTalonRight1.setVoltageRampRate(24);
    	RobotMap.driveTalonRight1.changeControlMode(TalonControlMode.Position);
    	RobotMap.driveTalonRight1.setAllowableClosedLoopErr(0);
    	RobotMap.driveTalonRight1.setEncPosition(0);
    	RobotMap.driveTalonRight1.reverseSensor(true);

    	RobotMap.driveTalonLeft1.setProfile(1);
    	RobotMap.driveTalonLeft1.setP(0.2);
    	RobotMap.driveTalonLeft1.setI(0.00);
    	RobotMap.driveTalonLeft1.setD(0.0);
    	RobotMap.driveTalonLeft1.setVoltageRampRate(24);
    	RobotMap.driveTalonLeft1.changeControlMode(TalonControlMode.Position);
    	RobotMap.driveTalonLeft1.setAllowableClosedLoopErr(0);
    	RobotMap.driveTalonLeft1.setEncPosition(0);
    	RobotMap.driveTalonLeft1.reverseSensor(true);
        
    }

    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        SmartDashboard.putNumber("Gyro getYaw", RobotMap.ahrs.getYaw());
        
        
        SmartDashboard.putNumber("driveTalonLeft1.get()", RobotMap.driveTalonLeft1.get());
		SmartDashboard.putNumber("driveTalonRight1.get()", RobotMap.driveTalonRight1.get());
		SmartDashboard.putNumber("pid mystery value left", RobotMap.driveTalonLeft1.pidGet());
		SmartDashboard.putNumber("pid mystery value right", RobotMap.driveTalonRight1.pidGet());
		SmartDashboard.putNumber("driveTalonLeft1.getClosedLoopError()", RobotMap.driveTalonLeft1.getClosedLoopError());
		SmartDashboard.putNumber("driveTalonRight1.getClosedLoopError()", RobotMap.driveTalonRight1.getClosedLoopError());

    	SmartDashboard.putNumber("driveTalonLeft1.getAnalogInPosition()", RobotMap.driveTalonLeft1.getAnalogInPosition());
    	SmartDashboard.putNumber("driveTalonLeft1.getAnalogInRaw()", RobotMap.driveTalonLeft1.getAnalogInRaw());
    	SmartDashboard.putNumber("driveTalonRight1.getOutputVoltage()", RobotMap.driveTalonRight1.getOutputVoltage());
    	SmartDashboard.putNumber("driveTalonLeft1.getOutputVoltage()", RobotMap.driveTalonLeft1.getOutputVoltage());
    	
    	SmartDashboard.putNumber("driveTalonLeft1.getEncPosition()", RobotMap.driveTalonLeft1.getEncPosition());
    	SmartDashboard.putNumber("driveTalonRight1.getEncPosition()", RobotMap.driveTalonRight1.getEncPosition());
        //this.logger.logAll(); // write to logs
    	
        
    	t2 = Timer.getFPGATimestamp();
    	gyroTarget = 0;
    	d2 = gyroTarget - RobotMap.ahrs.getYaw();
    	
    	
    	if (Math.abs(gyroTarget - RobotMap.ahrs.getYaw()) < 0.5){
    		gyroErrorSum = 0;
    	}
    	else{
        	gyroErrorSum = gyroErrorSum + (((d2 + d1)/2)*(t2 - t1));
    	}
    	
    	RobotMap.driveTalonRight1.setF(-gyroErrorSum*0.002);
    	RobotMap.driveTalonLeft1.setF(gyroErrorSum*0.002);
        RobotMap.driveTalonRight1.set(-10);
        RobotMap.driveTalonLeft1.set(10);
        
    	t1 = t2;
    	d1 = d2;
    	
    	SmartDashboard.putNumber("dt", t2 - t1);
    	SmartDashboard.putNumber("gyroErrorSum", gyroErrorSum);
    	
    	
		
    }

    public void teleopInit() {
        if (autonomousCommand != null) autonomousCommand.cancel();
        //EncReset(); todo
        Robot.drivetrain.resetDriveEncoders();
        Robot.drivetrain.gyroYawZero();
        this.logger.openFile();
        //RobotMap.serialport.reset();
		//RobotMap.serialport.writeString(":85");
        RobotMap.driveTalonLeft1.changeControlMode(TalonControlMode.Voltage);
        RobotMap.driveTalonRight1.changeControlMode(TalonControlMode.Voltage);

    	RobotMap.driveTalonLeft1.setVoltageRampRate(1000);
    	RobotMap.driveTalonRight1.setVoltageRampRate(1000);
    	
    	RobotMap.FRCGyro.reset();
		
    }

    public void teleopPeriodic() {
    	SmartDashboard.putNumber("Gyro getYaw", RobotMap.ahrs.getYaw());
    	SmartDashboard.putNumber("driveTalonLeft1.get()", RobotMap.driveTalonLeft1.get());
		SmartDashboard.putNumber("driveTalonRight1.get()", RobotMap.driveTalonRight1.get());
		SmartDashboard.putNumber("pid mystery value left", RobotMap.driveTalonLeft1.pidGet());
		SmartDashboard.putNumber("pid mystery value right", RobotMap.driveTalonRight1.pidGet());
    	SmartDashboard.putNumber("driveTalonLeft1.getEncPosition()", RobotMap.driveTalonLeft1.getEncPosition());
    	SmartDashboard.putNumber("driveTalonRight1.getEncPosition()", RobotMap.driveTalonRight1.getEncPosition());
    	SmartDashboard.putNumber("ADXRS450_Gyro.getAngle()", RobotMap.FRCGyro.getAngle());
    	
    	Scheduler.getInstance().run();
        this.logger.logAll(); // write to logs
        Joystick driveStick = new Joystick(0);
		double deadZone = 0.15;
		double X = -driveStick.getRawAxis(0);
        double Y = -driveStick.getRawAxis(1);
        if ((Math.abs(-driveStick.getRawAxis(0))<deadZone) && (Math.abs(-driveStick.getRawAxis(1))<deadZone)){
        	X = 0;
        	Y = 0;
        }
        /*if (Math.abs(-driveStick.getRawAxis(1))<deadZone){
        	Y = 0;
        }*/
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

        	
        
            RobotMap.driveTalonLeft1.set(leftOutput*10);
            RobotMap.driveTalonRight1.set(-rightOutput*10);
//            RobotMap.launcherVictor.set(SmartDashboard.getNumber("Launcher Speed", 0));
            
        	
            
            
}
    
    public void testPeriodic() {
        LiveWindow.run();
    }
}
