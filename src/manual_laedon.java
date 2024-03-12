package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "Laedon's Manual Control")
public class manual_laedon extends LinearOpMode {

    //define variables
    //speed of robot. Pretty self-explanatory.
    float chassis_speed = 1;
    //y value of left stick on gamepad1
    float y;
    //x value of left stick on gamepad1
    float x;
    //x value of right stick on gamepad1
    float x1;
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;

    @Override
    public void runOpMode(){
        //access hardware map
        frontLeft = hardwareMap.get(DcMotor.class, "fL");
        frontRight = hardwareMap.get(DcMotor.class, "fR");
        backLeft = hardwareMap.get(DcMotor.class, "bL");
        backRight = hardwareMap.get(DcMotor.class, "bR");

        waitForStart();
        while(opModeIsActive()){
            //get input from joystick(s)
            y = gamepad1.left_stick_y;
            x = gamepad1.left_stick_x;
            x1 = gamepad1.right_stick_x;

            //Move robot based on mathematical function which is based on input
            frontLeft.setPower(Range.clip(y*chassis_speed - x*chassis_speed - x1*chassis_speed, -1.0, 1.0));
            frontRight.setPower(Range.clip(-y*chassis_speed - x*chassis_speed - x1*chassis_speed, -1.0, 1.0));
            backLeft.setPower(Range.clip(y*chassis_speed + x*chassis_speed - x1*chassis_speed, -1.0, 1.0));
            backRight.setPower(Range.clip(-y*chassis_speed + x*chassis_speed - x1*chassis_speed, -1.0, 1.0));
        }
    }
}
