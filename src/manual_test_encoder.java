package org.firstinspires.ftc.teamcode;

import static java.lang.Math.round;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "encoder test")
public class manual_test_encoder extends LinearOpMode {
    float chassis_speed = 0.5f;
    //y value of left stick on gamepad1
    boolean y;
    //x value of left stick on gamepad1
    boolean x;
    //x value of right stick on gamepad1
    boolean a;
    boolean b;

    boolean up;
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;

    DcMotor lift;

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode(){
        //access hardware map
        frontLeft = hardwareMap.get(DcMotor.class, "Fl_motor");
        frontRight = hardwareMap.get(DcMotor.class, "Fr_motor");
        backLeft = hardwareMap.get(DcMotor.class, "Bl_motor");
        backRight = hardwareMap.get(DcMotor.class, "Br_motor");
        lift = hardwareMap.get(DcMotor.class, "Lift_1");

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();
        while(opModeIsActive()){
            //get input from joystick(s)
            y = gamepad1.y;
            x = gamepad1.x;
            a = gamepad1.a;
            b = gamepad1.b;
            up = gamepad1.dpad_up;

            //Move robot based on mathematical function which is based on input
            if(y){
                Movement(384,0,0,0,0,5,0.5);
            }
            if(x){
                Movement(0,384,0,0,0,5,0.5);
            }
            if(a){
                Movement(0,0,384,0,0,5,0.5);
            }
            if(b){
                Movement(0,0,0,384,0,5,0.5);
            }
            if(up){
                Movement(0,0,0,0,-384,5,0.5);
            }
        }
    }
    void Movement(float fL, float fR, float bL, float bR, float l, int timeoutS, double speed) {
        int ntfl;
        int ntfr;
        int ntbl;
        int ntbr;
        int ntl;


        if(opModeIsActive()){
            ntfl = frontLeft.getCurrentPosition() + round(fL);
            ntfr = frontRight.getCurrentPosition() + round(fR);
            ntbl = backLeft.getCurrentPosition() + round(bL);
            ntbr = backRight.getCurrentPosition() + round(bR);
            ntl = lift.getCurrentPosition() + round(l);
            frontLeft.setTargetPosition(ntfl);
            frontRight.setTargetPosition(ntfr);
            backLeft.setTargetPosition(ntbl);
            backRight.setTargetPosition(ntbr);
            lift.setTargetPosition(ntl);
            frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontLeft.setPower(speed);
            frontRight.setPower(speed);
            backLeft.setPower(speed);
            backRight.setPower(speed);
            lift.setPower(speed);

            runtime.reset();
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    ((frontLeft.isBusy() || frontRight.isBusy() ||
                            backLeft.isBusy() || backRight.isBusy() || lift.isBusy()))) {

                // Display it for the driver.
                telemetry.addData("Goal",  "Running to %7d :%7d :%7d :%7d :%7d", ntfl,  ntfr, ntbl, ntbr, ntl);
                telemetry.addData("Actual distance moved",  "Running at %7d :%7d :%7d :%7d :%7d",
                        frontLeft.getCurrentPosition(),
                        frontRight.getCurrentPosition(), backLeft.getCurrentPosition(), backRight.getCurrentPosition(), lift.getCurrentPosition());
                telemetry.addData("Speed", speed);
                telemetry.update();
            }


            frontLeft.setPower(0);
            frontRight.setPower(0);
            backLeft.setPower(0);
            backRight.setPower(0);
            lift.setPower(0);
            frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }
}
