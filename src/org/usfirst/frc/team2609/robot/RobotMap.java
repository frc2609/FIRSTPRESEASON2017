package org.usfirst.frc.team2609.robot;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.usfirst.frc.team2609.enums.TalonState;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import com.kauailabs.navx.frc.AHRS;

public class RobotMap {
	public static boolean disableHumanDrive = false;
	public static CANTalon driveTalonLeft1;
	public static CANTalon driveTalonLeft2;
	public static CANTalon driveTalonRight1;
	public static CANTalon driveTalonRight2;
	public static CANTalon ballIntake;
	public static CANTalon tsunamiMotor;
	
    public static DoubleSolenoid shifter;
    public static DoubleSolenoid vulcanDeploy;
    public static DoubleSolenoid vulcanClaw;
    public static DoubleSolenoid ballDoor;
    
	public static CANTalon prototype;
	public static Encoder driveEncLeft;
	public static Encoder driveEncRight;
	public static AHRS ahrs;
	public static SerialPort serialport;
	public static Relay ringLED;
	public static DigitalInput dio9;
	public static DigitalInput gearSensor;
	public static Ultrasonic ultra;
	
    public static DriverStation ds;
    
    public static DigitalInput clawCloseSensor;
    public static DigitalInput clawMissSensor;
    public static DigitalInput clawOpenSensor;
    public static DigitalInput clawUpSensor;
    public static DigitalInput clawDownSensor;
    public static Joystick Dandyboy;
    
    public static AxisState axisState = AxisState.BALL;
    public static TalonState talonState = TalonState.ARCADE;
    
    

	public static void init() {
		// DONT DEFINE THE OBJECT TYPE HERE!!1111! actually you cant define an object that is part of a spectrum!
		//ONE ex. Victor  driveVictorLeft1 = new Victor(0);
		try {
            ahrs = new AHRS(SPI.Port.kMXP);
            LiveWindow.addSensor("Drivetrain", "AHRS", ahrs);
        } catch (RuntimeException ex ) {
            DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
        }
    	//serialport = new SerialPort(9600, SerialPort.Port.kUSB);
    	//SerialPort serial = new SerialPort(115200, SerialPort.Port.kUSB);
		driveTalonRight1 = new CANTalon(3);
		driveTalonRight2 = new CANTalon(4);
		driveTalonLeft1 = new CANTalon(1);
		driveTalonLeft2 = new CANTalon(2);
		ballIntake = new CANTalon(5);
		tsunamiMotor = new CANTalon(6);
		
		driveTalonRight2.changeControlMode(TalonControlMode.Follower);
		driveTalonLeft2.changeControlMode(TalonControlMode.Follower);
		driveTalonRight2.set(3); // Follows talon 3 talon is the second best talon
		driveTalonLeft2.set(1); // Follows talon 1 talon 3 is not the best talon
		
		driveTalonRight1.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		driveTalonRight1.configEncoderCodesPerRev(53);//this is haram
		driveTalonRight1.setInverted(true);
//		driveTalonRight1.configEncoderCodesPerRev(611); // TODO: Enable if Motion profiling
		driveTalonRight1.reverseSensor(false);
		driveTalonRight1.reverseOutput(false);
		driveTalonLeft1.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		driveTalonLeft1.configEncoderCodesPerRev(53);
		driveTalonLeft1.setInverted(false);
		driveTalonLeft1.reverseSensor(true);
//		driveTalonLeft1.configEncoderCodesPerRev(611); // TODO: Enable if Motion profiling
//		driveTalonLeft1.reverseSensor(false); // TODO: Enable if Motion profiling
		driveTalonLeft1.reverseOutput(false);
		
		
		tsunamiMotor.changeControlMode(TalonControlMode.PercentVbus);

//		prototype = new CANTalon(5);
//		prototype.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);	
//		prototype.reverseSensor(false);
        shifter = new DoubleSolenoid(0, 1, 0);
        vulcanClaw = new DoubleSolenoid(0, 7, 6);
        vulcanDeploy = new DoubleSolenoid(0, 4, 5);
        ballDoor = new DoubleSolenoid(0,2,3);
        	
		ringLED = new Relay(0);
		ringLED.set(Relay.Value.kOff);
		dio9 = new DigitalInput(9);
		gearSensor = new DigitalInput(3);
		ultra = new Ultrasonic(6,7);
		ultra.setAutomaticMode(true);
		
		clawCloseSensor = new DigitalInput(0);
		clawMissSensor = new DigitalInput(4);
		clawOpenSensor = new DigitalInput(5);
		clawUpSensor = new DigitalInput(1);
		clawDownSensor = new DigitalInput(2);
		
        ds = DriverStation.getInstance();
        Dandyboy = new Joystick(0);
	}
}
