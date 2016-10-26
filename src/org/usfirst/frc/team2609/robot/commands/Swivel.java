package org.usfirst.frc.team2609.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Swivel extends CommandGroup {
    
    public  Swivel() {

    	addSequential(new DriveEncoder(1335, 0.6, 0));
    	addSequential(new GyroTurn(0.3,-90));
    	addSequential(new EncReset());
    	addSequential(new DriveEncoder(650, 0.6,-90));
    }
}
