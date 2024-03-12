package org.firstinspires.ftc.teamcode;

import static java.lang.Math.round;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Park")
public class park extends LinearOpMode {
    //Declare motors and other variables
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        waitForStart();
        Park();
    }

    void Movement(float fL, float fR, float bL, float bR, int timeoutS, double speed) {
        int ntfl;
        int ntfr;
        int ntbl;
        int ntbr;

        if(opModeIsActive()){
            ntfl = frontLeft.getCurrentPosition() + round(fL*1000);
            ntfr = frontRight.getCurrentPosition() + round(fR*1000);
            ntbl = backLeft.getCurrentPosition() + round(bL*1000);
            ntbr = backRight.getCurrentPosition() + round(bR*1000);
            frontLeft.setTargetPosition(ntfl);
            frontRight.setTargetPosition(ntfr);
            backLeft.setTargetPosition(ntbl);
            backRight.setTargetPosition(ntbr);
            frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontLeft.setPower(speed);
            frontRight.setPower(speed);
            backLeft.setPower(speed);
            backRight.setPower(speed);

            runtime.reset();
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (frontLeft.isBusy() && frontRight.isBusy() &&
                            backLeft.isBusy() && backRight.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Goal",  "Running to %7d :%7d :%7d :%7d", ntfl,  ntfr, ntbl, ntbr);
                telemetry.addData("Actual distance moved",  "Running at %7d :%7d :%7d :%7d",
                        frontLeft.getCurrentPosition(),
                        frontRight.getCurrentPosition(), backLeft.getCurrentPosition(), backRight.getCurrentPosition());
                telemetry.addData("Speed", speed);
                telemetry.update();
            }


            frontLeft.setPower(0);
            frontRight.setPower(0);
            backLeft.setPower(0);
            backRight.setPower(0);
            frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    void PlacePixel() {
        //Move to board using Movement()
        //Sweep horizontally a specific distance (half the width of the backdrop) until the correct april tag is detected
        //move corresponding motors to place pixel
    }

    void Park() {
        Movement(-1.6f,1.6f,-1.6f,1.6f,5,0.5);
        Movement(-3.3f,-3.3f,3.3f, 3.3f, 5, 0.5);
    }
}
