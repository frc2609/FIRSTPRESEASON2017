package org.usfirst.frc.team2609.robot;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

import org.usfirst.frc.team2609.robot.subsystems.BallDoor;
import org.usfirst.frc.team2609.robot.subsystems.BallIntake;
import org.usfirst.frc.team2609.robot.subsystems.Drivetrain;
import org.usfirst.frc.team2609.robot.subsystems.Logger;
import org.usfirst.frc.team2609.robot.subsystems.R03;
import org.usfirst.frc.team2609.robot.subsystems.Shifter;
import org.usfirst.frc.team2609.robot.subsystems.Tsunami;
import org.usfirst.frc.team2609.robot.subsystems.VulcanClaw;
import org.usfirst.frc.team2609.robot.commands.TsunamiControl;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;

import org.usfirst.frc.team2609.enums.DriveSide;
import org.usfirst.frc.team2609.enums.TalonState;
import org.usfirst.frc.team2609.enums.VulcanClawState;
import org.usfirst.frc.team2609.enums.VulcanDeployState;
import org.usfirst.frc.team2609.robot.commands.*;

import org.usfirst.frc.team2609.robot.commands.autons.*;
import org.usfirst.frc.team2609.robot.commands.vulcanClaw.VulcanGearGrab;

public class Robot extends IterativeRobot {

	public static Drivetrain drivetrain;
	public static VulcanClaw vulcanclaw;
	public static OI oi;
	public static Tsunami tsunami;
	public static BallIntake ballIntake;
	public static R03 r03;
//	public static VulcanGearGrab vulcangeargrab = new VulcanGearGrab();
	boolean gearSensorOld;
    Command autonomousCommand;
    SendableChooser chooser;
    public static NetworkTable table;
	double autonDistance1 = 0;
	double autonDistance2 = 0;
	double autonDistance3 = 0;
	double autonAngle1 = 0;
	double autonAngle2 = 0;
	double autonAngle3 = 0;
//	double autonHeading1 = 0;
//	double autonHeading2 = 0;
//	double autonHeading3 = 0;
    //public static double centerX = 0;
	public static double angleToDriveInitial = 0;
	public static double distanceToDriveInitial = 0;
    
    public void robotInit() {
		RobotMap.init();// put this here when imports don't work / robots don't quit
		oi = new OI();

		SmartDashboard.putNumber("Drive P: ", 0.02); //low gear 0.02, 0.0005 for 30" to 100"
    	SmartDashboard.putNumber("Drive I: ", 0.0005);
    	SmartDashboard.putNumber("Drive D: ", 0.0);
    	SmartDashboard.putNumber("Drive Max: ", 0.8);
    	SmartDashboard.putNumber("Drive Eps: ", 1.0);
    	SmartDashboard.putNumber("Drive DR: ", 1.0);
    	SmartDashboard.putNumber("Drive DC: ", 5);
    	
		SmartDashboard.putNumber("Gyro P: ", 0.05);
    	SmartDashboard.putNumber("Gyro I: ", 0.000);
    	SmartDashboard.putNumber("Gyro D: ", 0.0);
    	SmartDashboard.putNumber("Gyro Max: ", 0.1);
//    	SmartDashboard.putNumber("Gyro Eps: ", 0.2); If needed later
		
        SmartDashboard.putNumber("turn P: ",0.02);
        SmartDashboard.putNumber("turn I: ",0.002);
        SmartDashboard.putNumber("turn D: ",0.0);
        SmartDashboard.putNumber("turn Max: ",1.0);
        SmartDashboard.putNumber("turn Eps: ",1.0);
        SmartDashboard.putNumber("turn DR: ",1.0);
        SmartDashboard.putNumber("turn DC: ",5);
        
        SmartDashboard.putNumber("UltraDistance: ",5);

        SmartDashboard.putNumber("LED Colour: ", 1);
        SmartDashboard.putBoolean("LED Flash: ", false);
        
        boolean valueVision = false;
        SmartDashboard.putBoolean("vision", valueVision);
    	SmartDashboard.putNumber("climber speed", 1);
		drivetrain = new Drivetrain();
		vulcanclaw = new VulcanClaw();
		ballIntake = new BallIntake();
        chooser = new SendableChooser();
        r03 = new R03();
        chooser.addDefault("Straight Peg Red", new StraightPegRed());
        chooser.addObject("Straight Peg Blue", new StraightPegBlue());
        chooser.addObject("Left peg Red", new LeftPRed());
        chooser.addObject("Right peg Red", new RightPRed());
        chooser.addObject("Left peg Blue", new LeftPBlue());
        chooser.addObject("Right peg Blue", new RightPBlue());
        SmartDashboard.putData("Auto mode", chooser);
        table = NetworkTable.getTable("RaspberryPi");
        table.putNumber("display", 0); //1 will Enable display outputs on the raspberry pi, 
        RobotMap.vulcanClaw.set(DoubleSolenoid.Value.kReverse); //Closed Claw
        RobotMap.vulcanDeploy.set(DoubleSolenoid.Value.kReverse); //Claw up
        RobotMap.vulcanClaw.set(DoubleSolenoid.Value.kReverse);
        RobotMap.vulcanDeploy.set(DoubleSolenoid.Value.kReverse);
    }
	
    public void disabledInit(){
		SmartDashboard.putNumber("LED Colour: ", 4);
        Robot.drivetrain.resetDriveEncoders();
		
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		//LedControl.setLed(255, 255, 0);
		Double readyVulcanClaw = table.getNumber("readyVulcanClaw",2);
        if (readyVulcanClaw == 1) {
        	//RobotMap.frameLights.showRGB(255, 200, 0); // yellow For the peanut gallery
			//RobotMap.frameLights.showRGB(0, 255, 0);//set led's to green when ready i think that yellow means go
		}
		else{
			//RobotMap.frameLights.showRGB(156,39,176);//set led's to purple otherwise yes this is good
		}
        table.putNumber("gyro.getYaw", RobotMap.ahrs.getYaw());
        
		SmartDashboard.putBoolean("gearSensor", RobotMap.gearSensor.get());
		SmartDashboard.putNumber("Gyro getYaw", RobotMap.ahrs.getYaw());
    	SmartDashboard.putNumber("driveEncLeft.getDistance()", RobotMap.driveTalonLeft1.getPosition());
    	SmartDashboard.putNumber("driveEncRight.getDistance()", RobotMap.driveTalonRight1.getPosition());
    	SmartDashboard.putNumber("driveEncLeft.getEncPosition()", RobotMap.driveTalonLeft1.getEncPosition());
    	SmartDashboard.putNumber("driveEncRight.getEncPosition()", RobotMap.driveTalonRight1.getEncPosition());
    	SmartDashboard.putNumber("ultra.getRangeInches()", RobotMap.ultra.getRangeInches());
		
		SmartDashboard.putBoolean("RobotMap.clawCloseSensor.get()", RobotMap.clawCloseSensor.get());
		SmartDashboard.putBoolean("RobotMap.clawUpSensor.get()", RobotMap.clawUpSensor.get());
		SmartDashboard.putBoolean("RobotMap.clawDownSensor.get()", RobotMap.clawDownSensor.get());
		SmartDashboard.putBoolean("little miss claw", RobotMap.clawMissSensor.get());
		
//		SmartDashboard.putBoolean("avalanche limit Fwd", RobotMap.tsunamiMotor.isFwdLimitSwitchClosed());
//		SmartDashboard.putBoolean("avalanche limit Rev", RobotMap.tsunamiMotor.isRevLimitSwitchClosed());

	}
    public void autonomousInit() {
        Robot.drivetrain.gyroYawZero();
        RobotMap.vulcanClaw.set(DoubleSolenoid.Value.kReverse); //Closed Claw
        RobotMap.vulcanDeploy.set(DoubleSolenoid.Value.kReverse); //Claw up
        autonomousCommand = (Command) chooser.getSelected();

        RobotMap.vulcanClaw.set(DoubleSolenoid.Value.kReverse);
        RobotMap.vulcanDeploy.set(DoubleSolenoid.Value.kReverse);
        if (autonomousCommand != null) autonomousCommand.start();
 

    	
    }

    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        drivetrain.handleTalonState();
        SmartDashboard.putNumber("Gyro getYaw", RobotMap.ahrs.getYaw());
    	SmartDashboard.putNumber("driveEncLeft.getDistance()", RobotMap.driveTalonLeft1.getPosition());
    	SmartDashboard.putNumber("driveEncRight.getDistance()", RobotMap.driveTalonRight1.getPosition());
    	SmartDashboard.putNumber("driveEncLeft.getOutputVoltage()", RobotMap.driveTalonLeft1.getOutputVoltage());
    	SmartDashboard.putNumber("driveEncRight.getOutputVoltage()", RobotMap.driveTalonRight1.getOutputVoltage());
    	SmartDashboard.putNumber("ultra.getRangeInches()", RobotMap.ultra.getRangeInches());

        table.putNumber("gyro.getYaw", RobotMap.ahrs.getYaw());
        //SmartDashboard.putNumber("driveEncLeft.getDistance()", RobotMap.driveEncLeft.getDistance());
		//SmartDashboard.putNumber("driveEncRight.getDistance()", RobotMap.driveEncRight.getDistance());
		//SmartDashboard.putNumber("driveEncLeft.getRate()", RobotMap.driveEncLeft.getRate());
		//SmartDashboard.putNumber("driveEncRight.getRate()", RobotMap.driveEncRight.getRate());
		SmartDashboard.putNumber("driveTalonLeft1.getEncPosition()", RobotMap.driveTalonLeft1.getEncPosition());
		SmartDashboard.putNumber("driveTalonRight1.getEncPosition()", RobotMap.driveTalonRight1.getEncPosition());
		SmartDashboard.putNumber("driveVictorLeft1.get()", RobotMap.driveTalonLeft1.get());
		SmartDashboard.putNumber("driveVictorRight1.get()", RobotMap.driveTalonRight1.get());
		

        
		
    }

    public void teleopInit() {
        if (autonomousCommand != null) autonomousCommand.cancel();
        Robot.drivetrain.resetDriveEncoders();
        Robot.drivetrain.gyroYawZero();
//        RobotMap.frameLights.showRGB(255, 0, 0);//set led's to red for start of match
        //RobotMap.serialport.reset();
		//RobotMap.serialport.writeString(":85");
    	RobotMap.driveTalonLeft1.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.driveTalonLeft1.setVoltageRampRate(10000);
    	RobotMap.driveTalonRight1.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.driveTalonRight1.setVoltageRampRate(10000);
    	
        RobotMap.vulcanClaw.set(DoubleSolenoid.Value.kReverse);
        RobotMap.vulcanDeploy.set(DoubleSolenoid.Value.kReverse);

        RobotMap.driveTalonLeft1.setEncPosition(0);
        RobotMap.driveTalonRight1.setEncPosition(0);
        
        RobotMap.talonState = TalonState.ARCADE;
        
    }

	public void teleopPeriodic() {
		Scheduler.getInstance().run();
        drivetrain.handleTalonState();
    	SmartDashboard.putNumber("Gyro getYaw", RobotMap.ahrs.getYaw());
    	SmartDashboard.putNumber("driveEncLeft.getDistance()", RobotMap.driveTalonLeft1.getPosition());
    	SmartDashboard.putNumber("driveEncRight.getDistance()", RobotMap.driveTalonRight1.getPosition());
    	SmartDashboard.putNumber("driveEncLeft.getOutputVoltage()", RobotMap.driveTalonLeft1.getOutputVoltage());
    	SmartDashboard.putNumber("driveEncRight.getOutputVoltage()", RobotMap.driveTalonRight1.getOutputVoltage());
    	SmartDashboard.putNumber("driveEncLeft.getEncPosition()", RobotMap.driveTalonLeft1.getEncPosition());
    	SmartDashboard.putNumber("driveEncRight.getEncPosition()", RobotMap.driveTalonRight1.getEncPosition());
		SmartDashboard.putBoolean("RobotMap.clawCloseSensor.get()", RobotMap.clawCloseSensor.get());
		SmartDashboard.putBoolean("RobotMap.clawUpSensor.get()", RobotMap.clawUpSensor.get());
		SmartDashboard.putBoolean("RobotMap.clawDownSensor.get()", RobotMap.clawDownSensor.get());
		SmartDashboard.putNumber("RobotMap.climber.voltage", RobotMap.tsunamiMotor.getOutputVoltage());
		SmartDashboard.putNumber("RobotMap.climber.current", RobotMap.tsunamiMotor.getOutputCurrent());
//		SmartDashboard.putBoolean("ClawClosedSolenoid", RobotMap.vulcanClaw.get());
        table.putNumber("gyro.getYaw", RobotMap.ahrs.getYaw());
        
        if (RobotMap.vulcanClaw.get() == DoubleSolenoid.Value.kForward){
        	SmartDashboard.putBoolean("ClawClosedSolenoid", true);
        }
        else{
        	SmartDashboard.putBoolean("ClawClosedSolenoid", false);
        }

		SmartDashboard.putBoolean("gearSensor", RobotMap.gearSensor.get());
		
//    	SmartDashboard.putNumber("RobotMap.tsunamiMotor.getBusVoltage()", RobotMap.ballIntake.getOutputVoltage());
//    	SmartDashboard.putNumber("RobotMap.tsunamiMotor.getOutputCurrent()", RobotMap.ballIntake.getOutputCurrent());
//		
//
//		SmartDashboard.putNumber("driveTalonLeft1.getEncPosition()", RobotMap.driveTalonLeft1.getEncPosition());
//		SmartDashboard.putNumber("driveTalonRight1.getEncPosition()", RobotMap.driveTalonRight1.getEncPosition());
    	
		if (!gearSensorOld){
			if (RobotMap.gearSensor.get()&& this.vulcanclaw.getDeployState() == VulcanDeployState.DOWN){//claw down
				new VulcanGearGrab().start();
			}
		} 	
		Tsunami.pullUp(-OI.opStick.getRawAxis(3));
		
		gearSensorOld = RobotMap.gearSensor.get();
		
    	
        
        
        
//        System.out.println("Left: " + RobotMap.driveTalonLeft1.getEncPosition());
//        System.out.println("Right: " + RobotMap.driveTalonRight1.getEncPosition());
//        System.out.println("LeftOutput: " + leftOutput);
//        System.out.println("RightOutput: " + rightOutput);
		drivetrain.humanDrive();
//        
//            RobotMap.launcherVictor.set(SmartDashboard.getNumber("Launcher Speed", 0));
            
            
}
    
    public void testPeriodic() {
        LiveWindow.run();
    }
    public static void eStopMP(){
    	RobotMap.talonState = TalonState.ARCADE;
    }
}
