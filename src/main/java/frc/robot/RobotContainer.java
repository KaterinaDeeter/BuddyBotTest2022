/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.Autonomous;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;


/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
 
  // subsystems
  private final Drive m_Drive = new Drive();
  private final TestMotors m_TestMotors = new TestMotors();
  private final Pistons m_Pistons = new Pistons();
  @SuppressWarnings("unused") // we never directly use this subsystem but it has code in periodic
  private final LimitSwitches m_limitSwitches = new LimitSwitches();
  
  // commands
  public final Autonomous m_Auto = new Autonomous();
  //private final ParallelCommandGroup m_Auto;

  // OI
  private final Joystick m_Gamepad = new Joystick(0);
 
 


  public RobotContainer() {
    SmartDashboard.putData("Autonomous Run", new Autonomous());
    //m_Auto = new ParallelCommandGroup();
    
    /*//Sample Drive Command:
    double speed = -m_Gamepad.getRawAxis(1)* 0.6;
    double turn = m_Gamepad.getRawAxis(4)* 0.3;

    double left = speed + turn;
    double right = speed - turn;

    leftMotor = new CANSparkMax(DriveConstants.kLeftDriveMotorCANID,MotorType.kBrushed); //Initiates leftMotor
    rightMotor = new CANSparkMax(DriveConstants.kLeftDriveMotorCANID,MotorType.kBrushed); //Initiates rightMotor
    leftMotor.set(left); // Sets speed
    rightMotor.set(-right); // Sets speed
    */


    // Configure the button bindings
    configureButtonBindings();

    // m_drive default command to drive arcade with the right joystick
    m_Drive.setDefaultCommand(
      new RunCommand(()-> m_Drive.driveArcade
        (m_Gamepad.getRawAxis(5) * -1, m_Gamepad.getRawAxis(4)), m_Drive));
    
    // m_TestMotors default command left joystick X runs motor1 Y runs motor 2
    m_TestMotors.setDefaultCommand(new RunCommand(() -> m_TestMotors.setMotors(m_Gamepad.getRawAxis(0), m_Gamepad.getRawAxis(1) * -1), m_TestMotors));

  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    // Extend upper piston when left bumper is down
    new JoystickButton(m_Gamepad, 5).whenPressed(new InstantCommand(m_Pistons::extendUpper, m_Pistons))
                                    .whenReleased(new InstantCommand(m_Pistons::retractUpper, m_Pistons));

    // Extend lower piston when left bumper is down
    new JoystickButton(m_Gamepad, 6).whenPressed(new InstantCommand(m_Pistons::extendLower, m_Pistons))
                                    .whenReleased(new InstantCommand(m_Pistons::retractLower, m_Pistons));                                    

  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
     // An ExampleCommand will run in autonomous
    return m_Auto;  
  }
}
