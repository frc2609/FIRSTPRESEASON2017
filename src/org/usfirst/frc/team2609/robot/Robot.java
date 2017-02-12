package org.usfirst.frc.team2609.robot;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import org.usfirst.frc.team2609.robot.subsystems.Drivetrain;
import org.usfirst.frc.team2609.robot.subsystems.LedControl;
import org.usfirst.frc.team2609.robot.subsystems.Logger;
import org.usfirst.frc.team2609.robot.subsystems.Shifter;
import org.usfirst.frc.team2609.robot.subsystems.Tsunami;
import org.usfirst.frc.team2609.robot.subsystems.VulcanClaw;
import com.ctre.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import org.usfirst.frc.team2609.robot.commands.Auto1;
import org.usfirst.frc.team2609.robot.commands.vulcanClaw.VulcanGearGrab;

public class Robot extends IterativeRobot {
	public static LedControl LedControl;
	public static Drivetrain drivetrain;
	public static Shifter shifter;
	public static VulcanClaw vulcanclaw;
	public static OI oi;
	public static Tsunami tsunami;
//	public static VulcanGearGrab vulcangeargrab = new VulcanGearGrab();
	private Logger logger;
	boolean gearSensorOld;
    Command autonomousCommand;
    SendableChooser<Auto1> chooser;
    public static NetworkTable table;
    
    //public static double centerX = 0;
    
    public void robotInit() {
		RobotMap.init();// put this here when imports don't work / robots don't quit
		oi = new OI();

		SmartDashboard.putNumber("Drive P: ", 0.2);
    	SmartDashboard.putNumber("Drive I: ", 0.002);
    	SmartDashboard.putNumber("Drive D: ", 0.0);
    	SmartDashboard.putNumber("Drive Max: ", 0.5);
    	SmartDashboard.putNumber("Drive Eps: ", 0.001);
    	
		SmartDashboard.putNumber("Gyro P: ", 0.05);
    	SmartDashboard.putNumber("Gyro I: ", 0.000);
    	SmartDashboard.putNumber("Gyro D: ", 0.0);
    	SmartDashboard.putNumber("Gyro Max: ", 0.2);
//    	SmartDashboard.putNumber("Gyro Eps: ", 0.2); If needed later
		
        SmartDashboard.putNumber("turn P: ",0.02);
        SmartDashboard.putNumber("turn I: ",0.002);
        SmartDashboard.putNumber("turn D: ",0.0);
        SmartDashboard.putNumber("turn Max: ",1.0);
        SmartDashboard.putNumber("turn Eps: ",0.01);
        
    	SmartDashboard.putNumber("auton distance 1", 0);
    	SmartDashboard.putNumber("auton distance 2", 0);
    	SmartDashboard.putNumber("auton distance 3", 0);
    	SmartDashboard.putNumber("auton heading 1", 0);
    	SmartDashboard.putNumber("auton heading 2", 0);
    	SmartDashboard.putNumber("auton heading 3", 0);
    	SmartDashboard.putNumber("auton angle 1", 0);
    	SmartDashboard.putNumber("auton angle 2", 0);
    	SmartDashboard.putNumber("auton angle 3", 0);
        
        boolean valueVision = false;
        SmartDashboard.putBoolean("vision", valueVision);

    	SmartDashboard.putNumber("climber speed", 0);

        shifter = new Shifter();
		drivetrain = new Drivetrain();
		vulcanclaw = new VulcanClaw();
        chooser = new SendableChooser();
        chooser.addDefault("Default Auto", new Auto1());
        SmartDashboard.putData("Auto mode", chooser);
        this.logger = logger.getInstance();
        //chooser.addObject("My Auto", new MyAutoCommand());  
        table = NetworkTable.getTable("RaspberryPi");
        table.putNumber("display", 0); //1 will Enable display outputs on the raspberry pi, will crash if no monitor connected to it.
        RobotMap.shifter.set(DoubleSolenoid.Value.kForward); //Low gear
        RobotMap.vulcanClaw.set(DoubleSolenoid.Value.kReverse); //Closed Claw
        RobotMap.vulcanDeploy.set(DoubleSolenoid.Value.kReverse); //Claw up
    }
	
    public void disabledInit(){
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
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
        
        
		SmartDashboard.putBoolean("gearSensor", RobotMap.gearSensor.get());
		SmartDashboard.putNumber("Gyro getYaw", RobotMap.ahrs.getYaw());
    	SmartDashboard.putNumber("driveEncLeft.getDistance()", drivetrain.encoderInchLeft());
    	SmartDashboard.putNumber("driveEncRight.getDistance()", drivetrain.encoderInchRight());
//    	SmartDashboard.putNumber("driveEncLeft.getDistance()", (Math.PI*6)*RobotMap.driveTalonLeft1.getPosition());
//		SmartDashboard.putNumber("driveEncRight.getDistance()", (Math.PI*6)*RobotMap.driveTalonRight1.getPosition());
		
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
        if (autonomousCommand != null) autonomousCommand.start();

    	
    }

    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        SmartDashboard.putNumber("Gyro getYaw", RobotMap.ahrs.getYaw());
    	SmartDashboard.putNumber("driveEncLeft.getDistance()", drivetrain.encoderInchLeft());
    	SmartDashboard.putNumber("driveEncRight.getDistance()", drivetrain.encoderInchRight());
//    	SmartDashboard.putNumber("driveEncLeft.getDistance()", (Math.PI*6)*RobotMap.driveTalonLeft1.getPosition());
//		SmartDashboard.putNumber("driveEncRight.getDistance()", (Math.PI*6)*RobotMap.driveTalonRight1.getPosition());
    	SmartDashboard.putNumber("driveTalonLeft1.getOutputVoltage()", RobotMap.driveTalonLeft1.getOutputVoltage());
		SmartDashboard.putNumber("driveTalonRight1.getOutputVoltage()", RobotMap.driveTalonRight1.getOutputVoltage());

    	RobotMap.ringLED.set(Relay.Value.kReverse);
//        this.logger.logAll(); // write to logs
        
		
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
    	
    	
    }

	public void teleopPeriodic() {
    	
    	SmartDashboard.putNumber("Gyro getYaw", RobotMap.ahrs.getYaw());
    	SmartDashboard.putNumber("driveEncLeft.getDistance()", drivetrain.encoderInchLeft());
    	SmartDashboard.putNumber("driveEncRight.getDistance()", drivetrain.encoderInchRight());
//    	SmartDashboard.putNumber("driveEncLeft.getDistance()", (Math.PI*6)*RobotMap.driveTalonLeft1.getPosition());
//		SmartDashboard.putNumber("driveEncRight.getDistance()", (Math.PI*6)*RobotMap.driveTalonRight1.getPosition());
		
		SmartDashboard.putBoolean("RobotMap.clawCloseSensor.get()", RobotMap.clawCloseSensor.get());
		SmartDashboard.putBoolean("RobotMap.clawUpSensor.get()", RobotMap.clawUpSensor.get());
		SmartDashboard.putBoolean("RobotMap.clawDownSensor.get()", RobotMap.clawDownSensor.get());
		
    	SmartDashboard.putNumber("RobotMap.tsunamiMotor.getBusVoltage()", RobotMap.tsunamiMotor.getOutputVoltage());
    	SmartDashboard.putNumber("RobotMap.tsunamiMotor.getOutputCurrent()", RobotMap.tsunamiMotor.getOutputCurrent());
		
		Scheduler.getInstance().run();
		if (!gearSensorOld){
			if (RobotMap.gearSensor.get() && !RobotMap.clawDownSensor.get()){
				new VulcanGearGrab().start();
			}
		}
		gearSensorOld = RobotMap.gearSensor.get();
		Double readyVulcanClaw = table.getNumber("readyVulcanClaw", 0);
		if (readyVulcanClaw == 1) {
			RobotMap.frameLights.showRGB(255, 200, 0); // yellow For the peanut gallery
			//RobotMap.frameLights.showRGB(0, 255, 0);//set led's to green when ready i think that yellow means go
		}
		else{
			RobotMap.frameLights.showRGB(156,39,176);//set led's to red otherwise yes this is good
		}
//        this.logger.logAll(); // write to logs
		
        //drivetrain.driveTank(-RobotMap.Dandyboy.getRawAxis(1),-RobotMap.Dandyboy.getRawAxis(3));
		drivetrain.humanDrive();
//        if(RobotMap.axisState == AxisState.SCALER){
//        	RobotMap.tsunamiMotor.set(RobotMap.Dandyboy.getRawAxis(3));
//        }
//        else{
//        	RobotMap.ballIntake.set(-RobotMap.Dandyboy.getRawAxis(3));
//        }
    	
        if(OI.driverStick.getPOV() == 180){
        	RobotMap.tsunamiMotor.set(-SmartDashboard.getNumber("climber speed", 0));
        }
        else if (OI.driverStick.getPOV() == 0){
        	RobotMap.tsunamiMotor.set(SmartDashboard.getNumber("climber speed", 0));
        }
        else{
        	RobotMap.tsunamiMotor.set(0);
        }

    	//RobotMap.ringLED.set(Relay.Value.kReverse);
        	
}
    
    public void testPeriodic() {
        LiveWindow.run();
    }
}
