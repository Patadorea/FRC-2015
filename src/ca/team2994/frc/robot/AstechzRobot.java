package ca.team2994.frc.robot;

import ca.team2994.frc.autonomous.AutoMode;
import ca.team2994.frc.autonomous.CalibrationManager;
import ca.team2994.frc.autonomous.modes.TestAutoMode;
import ca.team2994.frc.mechanism.RobotArm;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class AstechzRobot extends IterativeRobot {
	
	public int counter = 0; // Unused
	public CalibrationManager calibration;
	public AutoMode currentAutoMode;
	public SmartDash smartdash;
	
	private boolean gamepadManual = false;
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
	public void robotInit() {
    	Constants.readConstantPropertiesFromFile();
    	Subsystems.initialize();
    	
    	Subsystems.leftDriveEncoder.reset();
    	Subsystems.rightDriveEncoder.reset();
    	
    	Subsystems.driveJoystick.enableButton(6);
    	Subsystems.driveJoystick.enableButton(7);
    	smartdash = new SmartDash();
    	
    	currentAutoMode = new TestAutoMode();
    	
    	Subsystems.readEncoderValues();
    }
    
    /**
     * This function is run once each time the robot enters autonomous mode
     */
    @Override
	public void autonomousInit() {
    	currentAutoMode.initialize();
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
	public void autonomousPeriodic() {
    	currentAutoMode.tick();
    }
    
    /**
     * This function is called once each time the robot enters tele-operated mode
     */
    @Override
	public void teleopInit(){
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
	public void teleopPeriodic() {
    	Subsystems.driveJoystick.update();
    	Subsystems.controlGamepad.update();
    	
    	Subsystems.robotDrive.arcadeDrive(Subsystems.driveJoystick, false);
    	
    	RobotArm.robotArm();
    }
    
    @Override
	public void testInit() {
    	calibration = new CalibrationManager();
    	calibration.calibrateInit();    	
    }
    
    /**
     * This function is called periodically during test mode
     */
    @Override
	public void testPeriodic() {
    	calibration.calibrateTick();
    	LiveWindow.run();
    }
    
    /**
     * This function is called by default when the robot is disabled
     */
    @Override
    public void disabledInit() {
	}
}
