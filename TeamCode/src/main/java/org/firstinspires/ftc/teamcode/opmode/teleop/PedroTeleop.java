package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.ManualDrive;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@TeleOp(name = "Example TeleOp")
public class PedroTeleop extends OpMode {

    private Follower follower;
    private DcMotor intake;
    private DcMotor index;
    private DcMotor launch;



    @Override
    public void init() {
        follower = Constants.create(hardwareMap);
        intake = hardwareMap.get(DcMotor.class, "intake");
        index = hardwareMap.get(DcMotor.class, "index");
        launch = hardwareMap.get(DcMotor.class, "launch");
    }

    @Override
    public void loop() {
        DrivePowers powers = ManualDrive.fieldCentric(
                -gamepad1.left_stick_y,
                gamepad1.left_stick_x,
                gamepad1.right_stick_x,
                follower.pose().heading()
        );
        if (gamepad1.a) {
            intake.setPower(1.0); // Full power forward
        } else {
            intake.setPower(0.0); // Stop motor
        }
        if (gamepad1.x) {
            index.setPower(1.0); // Full power forward
        } else {
            index.setPower(0.0); // Stop motor
        }
        if (gamepad1.b) {
            launch.setPower(0.75); // Full power forward
        } else {
            launch.setPower(0.0); // Stop motor
        }

        follower.manual(powers);
        follower.update();
    }
}