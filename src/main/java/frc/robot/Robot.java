/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.util.LogitechF310;
import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.*;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    /* Hardware */
    DriveSystem driveSys; 
    LogitechF310 gamepad;

    
    /* String for output */
    StringBuilder _sb = new StringBuilder();
    
    /* Loop tracker for prints */
	int _loops = 0;

	public void robotInit() {
    driveSys = new DriveSystem();
    gamepad =  new LogitechF310(0);
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
    /* Get gamepad axis */

		double leftYstick = -1 * gamepad.leftStickY();

		/* Get Talon/Victor's current output percentage */
		double motorOutput = _talon.getMotorOutputPercent();
		
		/* Prepare line to print */
		_sb.append("\tout:");
		/* Cast to int to remove decimal places */
		_sb.append((int) (motorOutput * 100));
		_sb.append("%");	// Percent

		_sb.append("\tspd:");
		_sb.append(_talon.getSelectedSensorVelocity(Constants.kPIDLoopIdx));
		_sb.append("u"); 	// Native units

        /** 
		 * When button 1 is held, start and run Velocity Closed loop.
		 * Velocity Closed Loop is controlled by joystick position x500 RPM, [-500, 500] RPM
		 */
		if (_joy.getRawButton(1)) {
			/* Velocity Closed Loop */

			/**
			 * Convert 500 RPM to units / 100ms.
			 * 4096 Units/Rev * 500 RPM / 600 100ms/min in either direction:
			 * velocity setpoint is in units/100ms
			 */
			double targetVelocity_UnitsPer100ms = leftYstick * 500.0 * 4096 / 600;
			/* 500 RPM in either direction */
			_talon.set(ControlMode.Velocity, targetVelocity_UnitsPer100ms);

			/* Append more signals to print when in speed mode. */
			_sb.append("\terr:");
			_sb.append(_talon.getClosedLoopError(Constants.kPIDLoopIdx));
			_sb.append("\ttrg:");
			_sb.append(targetVelocity_UnitsPer100ms);
		} else {
			/* Percent Output */

			_talon.set(ControlMode.PercentOutput, leftYstick);
		}

        /* Print built string every 10 loops */
		if (++_loops >= 10) {
			_loops = 0;
			System.out.println(_sb.toString());
        }
        /* Reset built string */
		_sb.setLength(0);
  }
  
  private void initDriveSystem() {

  }

}
