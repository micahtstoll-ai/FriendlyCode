package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.math.Vector2D;
import com.pedropathing.utils.Angle;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.ManualDrive;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@TeleOp(name = "Example TeleOp")
public class PedroTeleop extends OpMode {

    // Below this stick deflection the driver is not asking to turn, so the heading lock takes over.
    private static final double TURN_DEADBAND = 0.05;
    // Only capture a new heading to hold once the robot has stopped spinning (rad/s),
    // otherwise the lock snaps the robot back to where the driver let go of the stick.
    private static final double SETTLED_OMEGA = 0.5;
    // Cap on how hard the lock may turn, so it corrects drift without fighting the driver.
    private static final double MAX_LOCK_TURN = 0.3;

    private Follower follower;
    private DcMotor intake;
    private DcMotor index;
    private DcMotor launch;

    private Double heldHeading = null;

    @Override
    public void init() {
        follower = Constants.create(hardwareMap);
        // The Pinpoint keeps its pose between OpModes, so a stale heading from the last run
        // would skew field-centric from the first loop. Start at zero with the robot facing away.
        follower.setPose(new Pose(0, 0, 0));
        intake = hardwareMap.get(DcMotor.class, "intake");
        index = hardwareMap.get(DcMotor.class, "index");
        launch = hardwareMap.get(DcMotor.class, "launch");
    }

    @Override
    public void loop() {
        // Update first so field-centric uses this loop's heading, not last loop's.
        follower.update();

        // Re-zero field-centric: point the robot away from the driver and press dpad down.
        if (gamepad1.dpadDownWasPressed()) {
            follower.setHeading(0);
            heldHeading = 0.0;
        }

        // Pedro's frame is +strafe = LEFT and +turn = COUNTER-CLOCKWISE (see Mecanum.computeWheelPowersUnnormalized),
        // but the gamepad reports +x for RIGHT. Both stick x axes have to be negated.
        double forward = -gamepad1.left_stick_y;
        double strafe = -gamepad1.left_stick_x;
        double turn = -gamepad1.right_stick_x;

        double heading = follower.pose().heading();
        double omega = follower.velocity().omega;

        if (Math.abs(turn) > TURN_DEADBAND) {
            heldHeading = null;
        } else {
            if (heldHeading == null && Math.abs(omega) < SETTLED_OMEGA) heldHeading = heading;
            if (heldHeading != null) turn = headingLockTurn(heldHeading, heading, omega);
        }

        DrivePowers powers = ManualDrive.fieldCentric(forward, strafe, turn, heading);

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

        telemetry.addData("X (in)", follower.pose().x());
        telemetry.addData("Y (in)", follower.pose().y());
        telemetry.addData("Heading (deg)", Math.toDegrees(heading));
        telemetry.addData("Held heading (deg)", heldHeading == null ? "none" : Math.toDegrees(heldHeading));
        telemetry.update();
    }

    /**
     * Turn power that holds the robot on target, using the tuned Foresight heading controller and
     * heading brake model so the correction anticipates coast instead of overshooting.
     * Same math as ManualDrive.headingLock's predictive overload, but with a symmetric clamp:
     * the library clamps to [-0.3, 1.0], which corrects drift harder in one direction than the other.
     */
    private double headingLockTurn(double target, double heading, double omega) {
        Vector2D brake = Constants.foresightConfig.headingBrakeCoefficients.get();
        double brakeDistance = brake.x() * omega + brake.y() * omega * omega * Math.signum(omega);
        double error = Angle.normalizeSigned(target - heading) - brakeDistance;
        double power = Constants.foresightConfig.headingFeedback.get().calculate(0, error);
        return Math.max(-MAX_LOCK_TURN, Math.min(MAX_LOCK_TURN, power));
    }
}
