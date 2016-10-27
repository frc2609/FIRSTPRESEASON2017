package org.usfirst.frc.team2609.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Swivel extends CommandGroup {
    
    public  Swivel() {

    	addSequential(new DriveEncoder(1300, 0.6, 0));
    	addSequential(new GyroTurn(0.3,-90));
    	addSequential(new EncReset());
    	addSequential(new DriveEncoder(850, 0.6,-90));
    	addSequential(new GyroTurn(0.3,0));
    	addSequential(new EncReset());
    	addSequential(new DriveEncoder(1540, 0.6, 0));
    }
}
