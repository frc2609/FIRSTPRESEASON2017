package org.usfirst.frc.team2609.robot.commands.vulcanClaw;

import org.usfirst.frc.team2609.robot.commands.gearRollerSetSpeed;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class VulcanBallMode extends CommandGroup {

    public VulcanBallMode() {
        addSequential(new ClawClose());
        addSequential(new ClawUp());
        addSequential(new gearRollerSetSpeed(0.8));
        
    }
}
