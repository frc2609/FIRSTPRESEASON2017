package org.usfirst.frc.team2609.robot.commands;
import org.usfirst.frc.team2609.robot.commands.vulcanClaw.ClawOpen;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class Auto1 extends CommandGroup {

    public Auto1() {
        // Use requires() here to declare subsystem dependencies
        //requires(Robot.exampleSubsystem);
    	//addSequential(new CameraPointTurn(0.3));
    	/*
    	addSequential(new RingLED(true));
    	addSequential(new TimerDelay(0.5));
        addSequential(new GearLeft());
        addSequential(new RingLED(false));*/
    	/*
    	addSequential(new DriveEncoder(-6.8, 0.3, 0));
    	addSequential(new ClawOpen());
    	addSequential(new TimerDelay(0.2));
    	addSequential(new EncReset());
    	addSequential(new DriveEncoder(3, 0.3, 0));*/
    	
    	//left gear auton
    	/*
    	addSequential(new DriveEncoder(58.0,0.8,0.0));
    	addSequential(new EncReset());
    	addSequential(new TimerDelay(0.2));
    	addSequential(new GyroTurn(1,30));
    	addSequential(new TimerDelay(0.2));
    	addSequential(new DriveEncoder(80.0,0.8,30.0));
    	addSequential(new TimerDelay(0.2));
    	addSequential(new ClawOpen());
    	addSequential(new DriveEncoder(-40.0,0.8,30.0));
    	*/
    	
    	//right gear auton
    	
    	addSequential(new DriveEncoder(80.0,0.5,0.0));
    	addSequential(new EncReset());
    	addSequential(new TimerDelay(0.2));
    	addSequential(new GyroTurn(1,-60));
    	addSequential(new TimerDelay(0.2));
    	addSequential(new DriveEncoder(45.0,0.5,-60.0));
    	addSequential(new TimerDelay(0.2));
    	addSequential(new ClawOpen());
    	addSequential(new EncReset());
    	addSequential(new TimerDelay(0.2));
    	addSequential(new DriveEncoder(-50.0,0.5,-60.0));

    	//right gear auton with vision
    	
//    	addSequential(new DriveEncoder(80.0,0.5,0.0));
//    	addSequential(new EncReset());
//    	addSequential(new TimerDelay(0.2));
//    	addSequential(new GyroTurn(1,-60));
//    	addSequential(new TimerDelay(0.2));
    	
    	/*
    	addSequential(new DriveEncoderLeft(5.0,0.8));
    	addSequential(new DriveEncoderRight(5.0,0.8));
    	addSequential(new DriveEncoderLeft(5.0,0.8));
    	addSequential(new DriveEncoderRight(5.0,0.8));
    	addSequential(new DriveEncoderLeft(5.0,0.8));
    	addSequential(new DriveEncoderRight(5.0,0.8));
    	*/
    }
}
