package org.usfirst.frc.team2609.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;

import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import com.kauailabs.navx.frc.AHRS;

public class RobotMap {
	public static CANTalon driveTalonLeft1;
	public static CANTalon driveTalonLeft2;
	public static CANTalon driveTalonRight1;
	public static CANTalon driveTalonRight2;
	
	public static CANTalon prototype;
	public static Encoder driveEncLeft;
	public static Encoder driveEncRight;
	public static AHRS ahrs;
	public static SerialPort serialport;
	public static Relay ringLED;
	public static DigitalInput dio4;
	
	public static ADXRS450_Gyro FRCGyro;
	

	public static void init() {
		// DONT DEFINE THE OBJECT TYPE HERE!!1111!ONE ex. Victor  driveVictorLeft1 = new Victor(0);
		try {
            ahrs = new AHRS(SPI.Port.kMXP);
            LiveWindow.addSensor("Drivetrain", "AHRS", ahrs);
        } catch (RuntimeException ex ) {
            DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
        }
    	//serialport = new SerialPort(9600, SerialPort.Port.kUSB);
    	//SerialPort serial = new SerialPort(115200, SerialPort.Port.kUSB);
		driveTalonLeft1 = new CANTalon(3);
		driveTalonLeft2 = new CANTalon(4);
		driveTalonRight1 = new CANTalon(1);
		driveTalonRight2 = new CANTalon(2);
		driveTalonRight2.changeControlMode(TalonControlMode.Follower);
		driveTalonLeft2.changeControlMode(TalonControlMode.Follower);
		driveTalonRight2.set(1); // Follows talon 1
		driveTalonLeft2.set(3); // Follows talon 3
		driveTalonLeft1.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		driveTalonRight1.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		driveTalonLeft1.configEncoderCodesPerRev(250);
		driveTalonRight1.configEncoderCodesPerRev(250);
		driveTalonLeft1.configNominalOutputVoltage(+0f, -0f);
		driveTalonRight1.configNominalOutputVoltage(+0f, -0f);
		driveTalonLeft1.configPeakOutputVoltage(+12f, -12f);
		driveTalonRight1.configPeakOutputVoltage(+12f, -12f);
//		prototype = new CANTalon(5);
//		prototype.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);	
//		prototype.reverseSensor(false);
		
		//driveEncLeft = new Encoder(0, 2);
		//driveEncRight = new Encoder(1, 3);
		//driveEncLeft.setDistancePerPulse(1);
		//driveEncRight.setDistancePerPulse(1.4);
		ringLED = new Relay(0);
		ringLED.setDirection(Relay.Direction.kForward);
		dio4 = new DigitalInput(9);
		
		FRCGyro = new ADXRS450_Gyro();
		
	}
}
