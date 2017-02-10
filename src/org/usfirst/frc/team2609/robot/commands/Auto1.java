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
    	addSequential(new DriveEncoder(1.0,1.0,0.0));
    	//addSequential(new GyroTurn(1,0));
    	
    }
}
