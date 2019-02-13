package frc.robot;

import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.*;

public class DriveSystem {
    TalonSRX leftMotor1, leftMotor2, rightMotor1;
    VictorSPX leftMotor3, rightMotor2, rightMotor3;

    public DriveSystem() {
        leftMotor1 = new TalonSRX(1);
        leftMotor1.configFactoryDefault();
        leftMotor2 = new TalonSRX(2);
        leftMotor2.configFactoryDefault();
        leftMotor3 = new VictorSPX(3);
        leftMotor3.configFactoryDefault();
        leftMotor2.follow(leftMotor1);
        leftMotor3.follow(leftMotor1);

        rightMotor1 = new TalonSRX(4);
        rightMotor1.configFactoryDefault();
        rightMotor2 = new VictorSPX(5);
        rightMotor2.configFactoryDefault();
        rightMotor3 = new VictorSPX(6);
        rightMotor3.configFactoryDefault();
        rightMotor2.follow(rightMotor1);
        rightMotor3.follow(rightMotor1);

        /* Config sensor used for Primary PID [Velocity] */
        leftMotor1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,
            Constants.kPIDLoopIdx, 
            Constants.kTimeoutMs);
        rightMotor1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,
            Constants.kPIDLoopIdx, 
            Constants.kTimeoutMs);

        /**
		 * Phase sensor accordingly. 
         * Positive Sensor Reading should match Green (blinking) Leds on Talon
         */
        leftMotor1.setSensorPhase(false);
        rightMotor1.setSensorPhase(false);
        leftMotor1.setInverted(true);
        leftMotor2.setInverted(true);
        leftMotor3.setInverted(true);

		/* Config the peak and nominal outputs */
		leftMotor1.configNominalOutputForward(0, Constants.kTimeoutMs);
		leftMotor1.configNominalOutputReverse(0, Constants.kTimeoutMs);
		leftMotor1.configPeakOutputForward(1, Constants.kTimeoutMs);
		leftMotor1.configPeakOutputReverse(-1, Constants.kTimeoutMs);

		rightMotor1.configNominalOutputForward(0, Constants.kTimeoutMs);
		rightMotor1.configNominalOutputReverse(0, Constants.kTimeoutMs);
		rightMotor1.configPeakOutputForward(1, Constants.kTimeoutMs);
		rightMotor1.configPeakOutputReverse(-1, Constants.kTimeoutMs);

		/* Config the Velocity closed loop gains in slot0 */
		leftMotor1.config_kF(Constants.kPIDLoopIdx, Constants.kGains_Velocit_left.kF, Constants.kTimeoutMs);
		leftMotor1.config_kP(Constants.kPIDLoopIdx, Constants.kGains_Velocit_left.kP, Constants.kTimeoutMs);
		leftMotor1.config_kI(Constants.kPIDLoopIdx, Constants.kGains_Velocit_left.kI, Constants.kTimeoutMs);
		leftMotor1.config_kD(Constants.kPIDLoopIdx, Constants.kGains_Velocit_left.kD, Constants.kTimeoutMs);

		rightMotor1.config_kF(Constants.kPIDLoopIdx, Constants.kGains_Velocit_right.kF, Constants.kTimeoutMs);
		rightMotor1.config_kP(Constants.kPIDLoopIdx, Constants.kGains_Velocit_right.kP, Constants.kTimeoutMs);
		rightMotor1.config_kI(Constants.kPIDLoopIdx, Constants.kGains_Velocit_right.kI, Constants.kTimeoutMs);
		rightMotor1.config_kD(Constants.kPIDLoopIdx, Constants.kGains_Velocit_right.kD, Constants.kTimeoutMs);

        System.out.println("FRC6880: Completed initializing drive system for tuning\n");
    }
}