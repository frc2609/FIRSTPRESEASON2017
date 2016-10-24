package org.usfirst.frc.team2609.robot.commands;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class Auto1 extends CommandGroup {

    public Auto1() {
        // Use requires() here to declare subsystem dependencies
        //requires(Robot.exampleSubsystem);
        addSequential(new DriveEncoder(1500, 0.6, 0));
        addSequential(new TimerDelay(2));
        addSequential(new DriveEncoder(10, 0.6, 0));
        
    }
}
