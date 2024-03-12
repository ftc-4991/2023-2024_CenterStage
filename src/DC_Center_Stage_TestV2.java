package org.firstinspires.ftc.teamcode;
/*Teleop Program for the Center Stage Competition. Functions include:
Robot Movement (Forward, Backward, Strafing, Turning)
Intake mechanism (conveyer)
Lift Control
Box (open/close)
Drone Launch
Hanging mechanism (Not Implemented at current time)
 */
import android.media.MediaCodecInfo;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp(name="DC_Center_Stage_TestV2")

public class DC_Center_Stage_TestV2 extends LinearOpMode{
    DcMotor Front_Right_Wheel = null; //Dc Motor Variable for Front Right Wheel
    DcMotor Front_Left_Wheel  = null; //Dc Motor Variable for Front Left Wheel
    DcMotor Back_Right_Wheel  = null; //Dc Motor Variable for Back Right Wheel
    DcMotor Back_Left_Wheel   = null; //Dc Motor Variable for Back Left Wheel
    DcMotor Intake_Motor_1    = null; //Dc Motor Variable for Intake Motor
    DcMotor Lift_Motor_1      = null; //Dc Motor Variable for Lift Motor
    Servo   Box_Servo_1       = null; //180 degree Servo Variable for Box Mechanism
    DcMotor Drone_Motor_1     = null; //Dc Motor Variable for Drone Launch Mechanism
    CRServo   Drone_Servo_1   = null; //Continuous Servo Variable for Drone Launch Mechanism
    DcMotor Hang_Motor_1      = null; //Dc Motor Variable for Hanging Mechanism
    double IPower = 0; //Intake power variable
    float LPower = 0;  //Lift power variable
    float BPosition = 0;  //Box position variable
    float DroneS = 0; //Servo Power Variable for Drone
    float DroneM = 0; //Motor Power Variable for Drone
    float HPower = 0; //Hanging mechanism Power Variable
    @Override
    public void runOpMode() {

        //Config names below
        Front_Right_Wheel = hardwareMap.get(DcMotor.class, "Fr_motor");
        Front_Right_Wheel.setDirection(DcMotorSimple.Direction.REVERSE);
        Front_Left_Wheel  = hardwareMap.get(DcMotor.class, "Fl_motor");
        Back_Right_Wheel  = hardwareMap.get(DcMotor.class, "Br_motor");
        Back_Right_Wheel.setDirection(DcMotorSimple.Direction.REVERSE);
        Back_Left_Wheel   = hardwareMap.get(DcMotor.class, "Bl_motor");
        Intake_Motor_1    = hardwareMap.get(DcMotor.class, "Intake_Motor_1");
        Lift_Motor_1      = hardwareMap.get(DcMotor.class, "Lift_1");
        Box_Servo_1       = hardwareMap.get(Servo.class, "Box_Servo");
        Drone_Motor_1     = hardwareMap.get(DcMotor.class, "Drone_Motor");
        Drone_Servo_1     = hardwareMap.get(CRServo.class, "Drone_Servo");
        Hang_Motor_1      = hardwareMap.get(DcMotor.class, "Hang_Motor");
        Lift_Motor_1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Lift_Motor_1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        telemetry.addData("Pos: ", Lift_Motor_1.getCurrentPosition());
        telemetry.update();
        waitForStart();
        while(opModeIsActive()) {

            //Equations for Robot wheels for the left & right joysticks on gamepad 1
            double y = gamepad1.left_stick_y; //reverses the y axis allowing for only forward and backwards movement
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;


            Front_Right_Wheel.setPower(y + x + rx);
            Front_Left_Wheel.setPower((y - x - rx));
            Back_Left_Wheel.setPower((y + x -  rx));
            Back_Right_Wheel.setPower(y - x + rx);

            //5th motor controls intake via the left and right bumpers
            // right bumper on gamepad 1 adds power to intake motor to draw in pixels
            if (gamepad1.right_bumper) {
                IPower = 1;  //Starts intake Mechanism; pulls pixels in
                Intake_Motor_1.setPower(IPower);
            }
            //left bumper on gamepad 1 pushes pixels out to prevent control of too many pixels at once
            else if (gamepad1.left_bumper) {
                IPower = -1;  //Reverses intake Mechanism; pushes pixels out
                Intake_Motor_1.setPower(IPower);
            }
            if (gamepad1.x) { //x on the gamepad 1 changes the power in the intake motor to 0, stopping the intake motor
                IPower = 0;  //stops intake
                Intake_Motor_1.setPower(IPower);
            }
            //raise and lower the lift to be able to reach higher on the backdrop
            if (gamepad2.left_trigger >= 0.5 && Lift_Motor_1.getCurrentPosition() > 0) { //left trigger on gamepad 2 moves the lift up includes position statements to prevent lift from going too far up
                LPower = 1; //test speed then change appropriately
                Lift_Motor_1.setPower(LPower);
            } else if (gamepad2.right_trigger >= 0.5 && Lift_Motor_1.getCurrentPosition() <= 2950) { //right trigger on gamepad 2 moves lift down
            LPower = -1; //test speed then change appropriately
            Lift_Motor_1.setPower(LPower);
            }else{
            Lift_Motor_1.setPower(0);
            }


            //Open and close the box function to drop pixels into backdrop
            if (gamepad2.left_bumper) {  //left bumper on gamepad 2 opens the box
                BPosition = 90;
                Box_Servo_1.setPosition(BPosition);
                sleep(250);
            }
            if (gamepad2.right_bumper) {  //right bumper on gamepad 2 closes the box
               BPosition = 0;
               Box_Servo_1.setPosition(BPosition);

            }
            //Launch the drone from the robot using a servo and a motor
            if (gamepad2.dpad_right) { //launches drone when pressed using the right dpad
                DroneM = 0.5f;
                Drone_Motor_1.setPower(DroneM);
                sleep(300);
                DroneS = 1;
                Drone_Servo_1.setPower(DroneS);
                sleep(300);
                DroneM = 0;
                DroneS = 0;
                Drone_Motor_1.setPower(DroneM);
                Drone_Servo_1.setPower(DroneS);
            }
            if (gamepad2.dpad_up) { //Raises the hanging lift using up on the dpad
                HPower = 0.5f; //Check Speed
                Hang_Motor_1.setPower(HPower);
            }
            else if (gamepad2.dpad_down) { //Lowers the Hanging lift using down on the dpad
                HPower = -0.5f; //Check Speed
                Hang_Motor_1.setPower(HPower);
            }





            telemetry.addData("Pos: ", Lift_Motor_1.getCurrentPosition()); //adds telemetry data to the Driver Station
            telemetry.update();
        }
    }
}
