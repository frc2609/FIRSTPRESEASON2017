package org.usfirst.frc.team2609.robot.commands.vulcanClaw;

import org.usfirst.frc.team2609.robot.commands.*;
import org.usfirst.frc.team2609.robot.commands.TimerDelay;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class VulcanGearMode extends CommandGroup {

    public VulcanGearMode() {
        addSequential(new ClawClose());
        addSequential(new ClawDown());
//        addSequential(new TimerDelay(0.5));
        addSequential(new ClawOpen());
        
    }
}
