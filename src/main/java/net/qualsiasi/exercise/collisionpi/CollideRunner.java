package net.qualsiasi.exercise.collisionpi;

public class CollideRunner {

    private static double spaceResolution;

    public static void main(String[] args) {

        double smallMass = 1.0;
        double bigMass = Math.pow(100, 2);

        double smallMassSpeed = 0.0;
        double bigMassSpeed = -1.0; // positive goes right

        spaceResolution = 1e-12;

        double smallMassPosition = 1; // wall is in 0.0
        double bigMassPosition = 10;

        double timePassed = 0;

        long collisionCount = 0;
        long iterationCount = 0;


        while (canCollide(smallMassSpeed, bigMassSpeed)) {
            iterationCount++;

            double minDistance = Math.min(Math.abs(smallMassPosition - bigMassPosition), Math.abs(0 - smallMassPosition));
            double maxSpeed = Math.max(Math.abs(smallMassSpeed), Math.abs(bigMassSpeed));

            double timeResolution = Math.max(minDistance / maxSpeed, 1e-12);

            timePassed += timeResolution;
            smallMassPosition = smallMassPosition + smallMassSpeed * timeResolution;
            bigMassPosition = bigMassPosition + bigMassSpeed * timeResolution;

            if (smallMassSpeed >= 0 && isCollision(smallMassPosition, bigMassPosition)) {

                double newSmallMassSpeed = ( (smallMass - bigMass) * smallMassSpeed + 2 * bigMass * bigMassSpeed ) / (smallMass + bigMass);
                double newBigMassSpeed = ( (bigMass - smallMass) * bigMassSpeed + 2 * smallMass * smallMassSpeed ) / (smallMass + bigMass);

                smallMassSpeed = newSmallMassSpeed;
                bigMassSpeed = newBigMassSpeed;

                collisionCount++;

                reportCollision(smallMassSpeed, bigMassSpeed);
            }

            if (smallMassSpeed < 0 && isCollision(0, smallMassPosition)) {

                smallMassSpeed = -smallMassSpeed;
                collisionCount++;

                reportCollision(smallMassSpeed, bigMassSpeed);

            }

        }

        System.out.println("Collisions: " + collisionCount + " iteration count is " + iterationCount + ", time simulated is: " + timePassed + "s");

    }

    private static void reportCollision(double smallMassSpeed, double bigMassSpeed) {
        System.out.println(smallMassSpeed + ", " + bigMassSpeed);
    }

    private static boolean isCollision(double x, double y) {
        if (x > y)
            return true;
        return Math.abs(x - y) < spaceResolution;
    }

    private static boolean canCollide(double smallMassSpeed, double bigMassSpeed) {

        if (smallMassSpeed < 0) // going towards the wall
            return true;

        if (smallMassSpeed > 0 && smallMassSpeed > bigMassSpeed) // eventually gets to bigMass and bounces back
            return true;

        if (bigMassSpeed < 0) // bigMass goes left
            return true;

        return false;
    }

}
