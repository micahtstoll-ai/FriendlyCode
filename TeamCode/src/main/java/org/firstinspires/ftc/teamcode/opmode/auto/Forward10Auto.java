package org.firstinspires.ftc.teamcode.opmode.auto;

import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous(name = "Forward 10in Auto")
public class Forward10Auto extends LinearOpMode {
    private static final double DISTANCE_INCHES = 10.0;
    // Fraction of full drive power. Raise for a faster drive.
    private static final double DRIVE_POWER = 0.25;

    @Override
    public void runOpMode() {
        Follower follower = Constants.create(hardwareMap);

        waitForStart();

        // Robot-relative: start at the origin, +x is forward.
        follower.setPose(Pose.zero());
        follower.update();

        // Drive forward at a fixed low power until the localizer reads the target
        // distance. No path following, so nothing pulls the robot back afterwards.
        DrivePowers forward = new DrivePowers(DRIVE_POWER, 0, 0);
        while (opModeIsActive() && follower.pose().x() < DISTANCE_INCHES) {
            follower.manual(forward);
            follower.update();
            telemetry.addData("Pose", follower.pose());
            telemetry.update();
        }

        // Stop and stay stopped until the OpMode ends.
        DrivePowers stop = new DrivePowers(0, 0, 0);
        while (opModeIsActive()) {
            follower.manual(stop);
            follower.update();
            telemetry.addData("Pose", follower.pose());
            telemetry.update();
        }
    }
}
