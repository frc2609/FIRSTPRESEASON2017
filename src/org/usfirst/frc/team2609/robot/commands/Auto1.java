package org.usfirst.frc.team2609.robot.commands;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class Auto1 extends CommandGroup {

    public Auto1() {
        // Use requires() here to declare subsystem dependencies
        //requires(Robot.exampleSubsystem);
        addSequential(new GyroCameraTurn(0.5));
        
    }
}
