package org.usfirst.frc.team2609.robot.commands;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class Auto1 extends CommandGroup {

    public Auto1() {
        // Use requires() here to declare subsystem dependencies
        //requires(Robot.exampleSubsystem);
    	//addSequential(new RingLED(true));
        addSequential(new DriveEncoder(4,1,45));
    	addSequential(new TimerDelay(0.5));
        addSequential(new GyroTurn(60,1,0));
    	addSequential(new TimerDelay(0.5));
        addSequential(new DriveEncoder(4,1,60));
        //addSequential(new RingLED(false));
    }
}
