package org.usfirst.frc.team2609.robot;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

import org.usfirst.frc.team2609.robot.subsystems.BallIntake;
import org.usfirst.frc.team2609.robot.subsystems.Drivetrain;
import org.usfirst.frc.team2609.robot.subsystems.LedControl;
import org.usfirst.frc.team2609.robot.subsystems.Logger;
import org.usfirst.frc.team2609.robot.subsystems.MotionProfileSubsystem;
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
import org.usfirst.frc.team2609.robot.commands.*;

import org.usfirst.frc.team2609.robot.commands.AutoGear;
import org.usfirst.frc.team2609.robot.commands.SetLED;
import org.usfirst.frc.team2609.robot.commands.autons.*;
import org.usfirst.frc.team2609.robot.commands.vulcanClaw.VulcanGearGrab;

public class Robot extends IterativeRobot {
	public static LedControl LedControl;
	public static Drivetrain drivetrain;
	public static Shifter shifter;
	public static VulcanClaw vulcanclaw;
	public static OI oi;
	public static Tsunami tsunami;
	public static BallIntake ballIntake;
//	public static VulcanGearGrab vulcangeargrab = new VulcanGearGrab();
	private Logger logger;
	boolean gearSensorOld;
    Command autonomousCommand;
    SendableChooser chooser;
    public static NetworkTable table;
    
    //public static double centerX = 0;
    
    public void robotInit() {
		RobotMap.init();// put this here when imports don't work / robots don't quit
		oi = new OI();

		SmartDashboard.putNumber("Drive P: ", 0.02); //low gear 0.02, 0.0005 for 30" to 100"
    	SmartDashboard.putNumber("Drive I: ", 0.0005);
    	SmartDashboard.putNumber("Drive D: ", 0.0);
    	SmartDashboard.putNumber("Drive Max: ", 0.8);
    	SmartDashboard.putNumber("Drive Eps: ", 1.0);
    	SmartDashboard.putNumber("Drive DR: ", 1.0);
    	SmartDashboard.putNumber("Drive DC: ", 20);
    	
		SmartDashboard.putNumber("Gyro P: ", 0.05);
    	SmartDashboard.putNumber("Gyro I: ", 0.000);
    	SmartDashboard.putNumber("Gyro D: ", 0.0);
    	SmartDashboard.putNumber("Gyro Max: ", 0.2);
//    	SmartDashboard.putNumber("Gyro Eps: ", 0.2); If needed later
		
        SmartDashboard.putNumber("turn P: ",0.02);
        SmartDashboard.putNumber("turn I: ",0.002);
        SmartDashboard.putNumber("turn D: ",0.0);
        SmartDashboard.putNumber("turn Max: ",1.0);
        SmartDashboard.putNumber("turn Eps: ",1.0);
        SmartDashboard.putNumber("turn DR: ",1.0);
        SmartDashboard.putNumber("turn DC: ",20);
        
    	SmartDashboard.putNumber("auton distance 1: ", 80);
    	SmartDashboard.putNumber("auton distance 2: ", 45);
    	SmartDashboard.putNumber("auton distance 3: ", -30);
    	SmartDashboard.putNumber("auton heading 1: ", 0);
    	SmartDashboard.putNumber("auton heading 2: ", -60);
    	SmartDashboard.putNumber("auton heading 3: ", -60);
    	SmartDashboard.putNumber("auton angle 1: ", -60);
    	SmartDashboard.putNumber("auton angle 2:", 0);
    	SmartDashboard.putNumber("auton angle 3: ", 0);
        
        boolean valueVision = false;
        SmartDashboard.putBoolean("vision", valueVision);

    	SmartDashboard.putNumber("climber speed", 1);

        shifter = new Shifter();
		drivetrain = new Drivetrain();
		vulcanclaw = new VulcanClaw();
		LedControl = new LedControl();
		ballIntake = new BallIntake();
        chooser = new SendableChooser();
        chooser.addDefault("Default Auto - Dont move", new Auto1());
        chooser.addObject("Straight Peg", new StraightPeg());
        chooser.addObject("Left peg Red", new LeftPRed());
        chooser.addObject("Right peg Red", new RightPRed());
        chooser.addObject("Left peg Blue", new LeftPBlue());
        chooser.addObject("Right peg Blue", new RightPBlue());
        SmartDashboard.putData("Auto mode", chooser);
        this.logger = Logger.getInstance(); // Changed from logger.getInstance to Logger.getInstance at Eclipse insistence
        table = NetworkTable.getTable("RaspberryPi");
        table.putNumber("display", 0); //1 will Enable display outputs on the raspberry pi, will crash if no monitor connected to it.
        RobotMap.shifter.set(DoubleSolenoid.Value.kForward); //Low gear
        RobotMap.vulcanClaw.set(DoubleSolenoid.Value.kReverse); //Closed Claw
        RobotMap.vulcanDeploy.set(DoubleSolenoid.Value.kReverse); //Claw up
        RobotMap.vulcanClaw.set(DoubleSolenoid.Value.kReverse);
        RobotMap.vulcanDeploy.set(DoubleSolenoid.Value.kReverse);
    }
	
    public void disabledInit(){
		shifter.high();
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		LedControl.setLed(255, 255, 0);
		this.logger.close();
//        if (RobotMap.ds.getAlliance() == DriverStation.Alliance.Red) {
//        	RobotMap.frameLights.showRGB(255, 0, 0);
//        } else if (RobotMap.ds.getAlliance() == DriverStation.Alliance.Blue) {
//        	RobotMap.frameLights.showRGB(0, 0, 255);
//        } else if (RobotMap.ds.getAlliance() == DriverStation.Alliance.Invalid) {
//        	RobotMap.frameLights.showRGB(255, 200, 0); // yellow
//        }
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
		
		SmartDashboard.putBoolean("RobotMap.clawCloseSensor.get()", RobotMap.clawCloseSensor.get());
		SmartDashboard.putBoolean("RobotMap.clawUpSensor.get()", RobotMap.clawUpSensor.get());
		SmartDashboard.putBoolean("RobotMap.clawDownSensor.get()", RobotMap.clawDownSensor.get());
		SmartDashboard.putBoolean("little miss claw", RobotMap.clawMissSensor.get());
		
		SmartDashboard.putBoolean("avalanche limit Fwd", RobotMap.tsunamiMotor.isFwdLimitSwitchClosed());
		SmartDashboard.putBoolean("avalanche limit Rev", RobotMap.tsunamiMotor.isRevLimitSwitchClosed());

	}
    public void autonomousInit() {
        Robot.drivetrain.gyroYawZero();
        Robot.drivetrain.resetDriveEncoders();
        RobotMap.shifter.set(DoubleSolenoid.Value.kForward); //Low gear
        RobotMap.vulcanClaw.set(DoubleSolenoid.Value.kReverse); //Closed Claw
        RobotMap.vulcanDeploy.set(DoubleSolenoid.Value.kReverse); //Claw up
        autonomousCommand = (Command) chooser.getSelected();
        this.logger.openFile();

        RobotMap.vulcanClaw.set(DoubleSolenoid.Value.kReverse);
        RobotMap.vulcanDeploy.set(DoubleSolenoid.Value.kReverse);
		shifter.low();
        if (autonomousCommand != null) autonomousCommand.start();
        
        RobotMap._MotionPLeft = new MotionProfileSubsystem(RobotMap.driveTalonLeft1, DriveSide.LEFT);
        RobotMap._MotionPRight = new MotionProfileSubsystem(RobotMap.driveTalonRight1, DriveSide.RIGHT);
 

    	
    }

    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        SmartDashboard.putNumber("Gyro getYaw", RobotMap.ahrs.getYaw());
    	SmartDashboard.putNumber("driveEncLeft.getDistance()", RobotMap.driveTalonLeft1.getPosition());
    	SmartDashboard.putNumber("driveEncRight.getDistance()", RobotMap.driveTalonRight1.getPosition());
    	SmartDashboard.putNumber("driveEncLeft.getOutputVoltage()", RobotMap.driveTalonLeft1.getOutputVoltage());
    	SmartDashboard.putNumber("driveEncRight.getOutputVoltage()", RobotMap.driveTalonRight1.getOutputVoltage());

        table.putNumber("gyro.getYaw", RobotMap.ahrs.getYaw());
    	//RobotMap.ringLED.set(Relay.Value.kReverse);
//        this.logger.logAll(); // write to logs
        //SmartDashboard.putNumber("driveEncLeft.getDistance()", RobotMap.driveEncLeft.getDistance());
		//SmartDashboard.putNumber("driveEncRight.getDistance()", RobotMap.driveEncRight.getDistance());
		//SmartDashboard.putNumber("driveEncLeft.getRate()", RobotMap.driveEncLeft.getRate());
		//SmartDashboard.putNumber("driveEncRight.getRate()", RobotMap.driveEncRight.getRate());
		SmartDashboard.putNumber("driveTalonLeft1.getEncPosition()", RobotMap.driveTalonLeft1.getEncPosition());
		SmartDashboard.putNumber("driveTalonRight1.getEncPosition()", RobotMap.driveTalonRight1.getEncPosition());
		SmartDashboard.putNumber("driveVictorLeft1.get()", RobotMap.driveTalonLeft1.get());
		SmartDashboard.putNumber("driveVictorRight1.get()", RobotMap.driveTalonRight1.get());
		

        RobotMap._MotionPLeft.control();
        RobotMap._MotionPRight.control();
		
//        this.logger.logAll(); // write to logs
        if(!RobotMap.drivetrainMPActive){
        	RobotMap._MotionPLeft.reset();
        	RobotMap._MotionPRight.reset();
        }
        else{
        	this.logger.logAll();
        	SmartDashboard.putNumber("MPLeft", RobotMap.driveTalonLeft1.getPosition());
        	SmartDashboard.putNumber("MPRight", RobotMap.driveTalonRight1.getPosition());
        	
        	RobotMap.driveTalonLeft1.changeControlMode(TalonControlMode.MotionProfile);
        	RobotMap.driveTalonRight1.changeControlMode(TalonControlMode.MotionProfile);
        	CANTalon.SetValueMotionProfile rightSetOutput = RobotMap._MotionPRight.getSetValue();
            CANTalon.SetValueMotionProfile leftSetOutput = RobotMap._MotionPLeft.getSetValue();
            RobotMap.driveTalonLeft1.set(leftSetOutput.value);
            RobotMap.driveTalonRight1.set(rightSetOutput.value);
//            RobotMap._MotionPRight.startMotionProfile();
//            RobotMap._MotionPLeft.startMotionProfile();
            System.out.println("MP Running");
            
        }
        
		
    }

    public void teleopInit() {
        if (autonomousCommand != null) autonomousCommand.cancel();
        Robot.drivetrain.resetDriveEncoders();
        Robot.drivetrain.gyroYawZero();
        RobotMap.frameLights.showRGB(255, 0, 0);//set led's to red for start of match
        this.logger.openFile();
        //RobotMap.serialport.reset();
		//RobotMap.serialport.writeString(":85");
    	RobotMap.driveTalonLeft1.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.driveTalonLeft1.setVoltageRampRate(10000);
    	RobotMap.driveTalonRight1.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.driveTalonRight1.setVoltageRampRate(10000);
    	
        RobotMap.vulcanClaw.set(DoubleSolenoid.Value.kReverse);
        RobotMap.vulcanDeploy.set(DoubleSolenoid.Value.kReverse);
		shifter.high();

        RobotMap.driveTalonLeft1.setEncPosition(0);
        RobotMap.driveTalonRight1.setEncPosition(0);
        
        RobotMap._MotionPLeft = new MotionProfileSubsystem(RobotMap.driveTalonLeft1, DriveSide.LEFT);
        RobotMap._MotionPRight = new MotionProfileSubsystem(RobotMap.driveTalonRight1, DriveSide.RIGHT);
    }

	public void teleopPeriodic() {
		Scheduler.getInstance().run();
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
    	SmartDashboard.putNumber("RobotMap.tsunamiMotor.getBusVoltage()", RobotMap.tsunamiMotor.getOutputVoltage());
    	SmartDashboard.putNumber("RobotMap.tsunamiMotor.getOutputCurrent()", RobotMap.tsunamiMotor.getOutputCurrent());
        table.putNumber("gyro.getYaw", RobotMap.ahrs.getYaw());
		
    	SmartDashboard.putNumber("RobotMap.tsunamiMotor.getBusVoltage()", RobotMap.ballIntake.getOutputVoltage());
    	SmartDashboard.putNumber("RobotMap.tsunamiMotor.getOutputCurrent()", RobotMap.ballIntake.getOutputCurrent());
		

		SmartDashboard.putNumber("driveTalonLeft1.getEncPosition()", RobotMap.driveTalonLeft1.getEncPosition());
		SmartDashboard.putNumber("driveTalonRight1.getEncPosition()", RobotMap.driveTalonRight1.getEncPosition());
    	
		Scheduler.getInstance().run();
		if (!gearSensorOld){
			if (RobotMap.gearSensor.get() && !RobotMap.clawDownSensor.get()){
				new VulcanGearGrab().start();
			}
		}
		if (RobotMap.gearSensor.get()){
			LedControl.setLed(0,255,0);
		}
		else{
			LedControl.setLed(255,0,0);
		}
		gearSensorOld = RobotMap.gearSensor.get();
		/*Double readyVulcanClaw = table.getNumber("readyVulcanClaw", 0);
		if (readyVulcanClaw == 1) {
			new SetLED(255, 200, 0).start(); // yellow For the peanut gallery
		}
		else{
//			new SetLED(156,39,176).start();//set led's to red otherwise yes this is good
			new SetLED(255,0,0).start();//set led's to red otherwise yes this is good
		}*/
		
//        this.logger.logAll(); // write to logs
        //drivetrain.driveTank(-RobotMap.Dandyboy.getRawAxis(1),-RobotMap.Dandyboy.getRawAxis(3));
        if(RobotMap.axisState == AxisState.SCALER){
        	RobotMap.tsunamiMotor.set(RobotMap.Dandyboy.getRawAxis(3));
        }
        else{
        	RobotMap.ballIntake.set(-RobotMap.Dandyboy.getRawAxis(3));
        }
    	
//        if(OI.driverStick.getPOV() == 180){
//        	RobotMap.tsunamiMotor.set(-SmartDashboard.getNumber("climber speed", 0));
//        }
//        else if (OI.driverStick.getPOV() == 0){
//        	RobotMap.tsunamiMotor.set(SmartDashboard.getNumber("climber speed", 0));
//        }
//        else{
//        	RobotMap.tsunamiMotor.set(0);
//        }
    	//RobotMap.ringLED.set(Relay.Value.kReverse);
        if(RobotMap.axisState == AxisState.SCALER){
//        	RobotMap.tsunamiMotor.set(driveStick.getRawAxis(3));
        }
        else{
        	//RobotMap.ballIntake.set(-driveStick.getRawAxis(3));
        }
        
        
    	RobotMap.tsunamiMotor.changeControlMode(TalonControlMode.PercentVbus);
//        if(OI.driverStick.getPOV() == 180){
//        	RobotMap.tsunamiMotor.set(-SmartDashboard.getNumber("climber speed", 0));
//        }
//        else if (OI.driverStick.getPOV() == 0){
//        	RobotMap.tsunamiMotor.set(SmartDashboard.getNumber("climber speed", 0));
//        }
//        else{
//        	RobotMap.tsunamiMotor.set(0);
//        }
        
        
        RobotMap._MotionPLeft.control();
        RobotMap._MotionPRight.control();
//        System.out.println("Left: " + RobotMap.driveTalonLeft1.getEncPosition());
//        System.out.println("Right: " + RobotMap.driveTalonRight1.getEncPosition());
//        System.out.println("LeftOutput: " + leftOutput);
//        System.out.println("RightOutput: " + rightOutput);
        if(!RobotMap.drivetrainMPActive){
    		drivetrain.humanDrive();
        	RobotMap._MotionPLeft.reset();
        	RobotMap._MotionPRight.reset();
        }
        else{
        	this.logger.logAll();
        	SmartDashboard.putNumber("MPLeft", RobotMap.driveTalonLeft1.getPosition());
        	SmartDashboard.putNumber("MPRight", RobotMap.driveTalonRight1.getPosition());
        	
        	RobotMap.driveTalonLeft1.changeControlMode(TalonControlMode.MotionProfile);
        	RobotMap.driveTalonRight1.changeControlMode(TalonControlMode.MotionProfile);
        	CANTalon.SetValueMotionProfile rightSetOutput = RobotMap._MotionPRight.getSetValue();
            CANTalon.SetValueMotionProfile leftSetOutput = RobotMap._MotionPLeft.getSetValue();
            RobotMap.driveTalonLeft1.set(leftSetOutput.value);
            RobotMap.driveTalonRight1.set(rightSetOutput.value);
//            RobotMap._MotionPRight.startMotionProfile();
//            RobotMap._MotionPLeft.startMotionProfile();
            System.out.println("MP Running");
            
        }
//        
//            RobotMap.launcherVictor.set(SmartDashboard.getNumber("Launcher Speed", 0));
            
            
}
    
    public void testPeriodic() {
        LiveWindow.run();
    }
    public static void eStopMP(){
    	RobotMap.MPLeftDisabled = true;
    	RobotMap.MPRightDisabled = true;
    	RobotMap.drivetrainMPActive = false;
    	RobotMap._MotionPLeft.reset();
    	RobotMap._MotionPRight.reset();
    }
}
