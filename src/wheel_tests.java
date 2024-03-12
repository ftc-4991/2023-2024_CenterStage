package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "Laedon's Manual Control (Test)")
public class wheel_tests extends LinearOpMode {

    float chassis_speed = 1;
    boolean yB;
    boolean xB;
    boolean bB;
    boolean aB;
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;

    @Override
    public void runOpMode(){
        frontLeft = hardwareMap.get(DcMotor.class, "fL");
        frontRight = hardwareMap.get(DcMotor.class, "fR");
        backLeft = hardwareMap.get(DcMotor.class, "bL");
        backRight = hardwareMap.get(DcMotor.class, "bR");

        waitForStart();
        while(opModeIsActive()){
            aB = gamepad1.a;
            bB = gamepad1.b;
            xB = gamepad1.x;
            yB = gamepad1.y;

            if(aB){
                frontLeft.setPower(1);
            } else if (bB){
                frontRight.setPower(1);
            } else if (xB){
                backLeft.setPower(1);
            } else if (yB){
                backRight.setPower(1);
            } else{
                frontLeft.setPower(0);
                frontRight.setPower(0);
                backLeft.setPower(0);
                backRight.setPower(0);
            }
        }
    }
}
