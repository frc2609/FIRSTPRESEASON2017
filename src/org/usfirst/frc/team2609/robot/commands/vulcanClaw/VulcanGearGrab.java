package org.usfirst.frc.team2609.robot.commands.vulcanClaw;

import org.usfirst.frc.team2609.robot.commands.SetLED;
import org.usfirst.frc.team2609.robot.commands.TimerDelay;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class VulcanGearGrab extends CommandGroup {

    public VulcanGearGrab() {
        addSequential(new ClawClose());
        addSequential(new TimerDelay(0.2));
        addSequential(new ClawUpWithGear());
        
    }
}
