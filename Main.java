import models.Matrix;
import models.Result;
import fox.FoxMatrixMultiplier;
import stripe.StripeMatrixMultiplier;

import java.time.Duration;
import java.time.Instant;

//public class Main {
//    public static void main(String[] args) {
//        MatrixGenerator matrixGenerator = new MatrixGenerator();
//        StripeMatrixMultiplier stripeMultiplier = new StripeMatrixMultiplier();
//        FoxMatrixMultiplier foxMultiplier = new FoxMatrixMultiplier();
//
//        int[] matrixSizes = {100, 500, 1000, 2000, 3000};
//
//        for (int size : matrixSizes) {
//            Matrix matrix1 = new Matrix(matrixGenerator.generate(size, size, 5));
//            Matrix matrix2 = new Matrix(matrixGenerator.generate(size, size, 5));
//
//            Instant startStripe = Instant.now();
//            Result resultStripe = stripeMultiplier.multiply(matrix1, matrix2);
//            long stripeTime = Duration.between(startStripe, Instant.now()).toMillis();
//
//            Instant startFox = Instant.now();
//            Result resultFox = foxMultiplier.multiply(matrix1, matrix2);
//            long foxTime = Duration.between(startFox, Instant.now()).toMillis();
//
//            System.out.println("Matrix size: " + size);
//            System.out.println("Stripe method time: " + stripeTime + " ms");
//            System.out.println("Fox method time: " + foxTime + " ms");
//            System.out.println();
//        }
//    }
//}


import fox.FoxMatrixMultiplier;
import models.Matrix;
import models.Result;
import stripe.StripeMatrixMultiplier;

import java.time.Duration;
import java.time.Instant;

public class Main {
    public static void main(String[] args) {
        MatrixGenerator matrixGenerator = new MatrixGenerator();
        StripeMatrixMultiplier stripeMultiplier = new StripeMatrixMultiplier();
        FoxMatrixMultiplier foxMultiplier = new FoxMatrixMultiplier();

        int size = 500;

        Matrix matrix1 = new Matrix(matrixGenerator.generate(size, size, 5));
        Matrix matrix2 = new Matrix(matrixGenerator.generate(size, size, 5));

        System.out.println("Size: " + size);

        // Vary the number of threads
        for (int threadCount = 1; threadCount <= 8; threadCount++) {
            System.out.println("Thread Count: " + threadCount);

            Instant startStripe = Instant.now();
            Result resultStripe = stripeMultiplier.multiply(matrix1, matrix2, threadCount);
            long stripeTime = Duration.between(startStripe, Instant.now()).toMillis();
            System.out.println("Stripe Method: " + stripeTime + " ms");

            Instant startFox = Instant.now();
            Result resultFox = foxMultiplier.multiply(matrix1, matrix2, threadCount);
            long foxTime = Duration.between(startFox, Instant.now()).toMillis();
            System.out.println("Fox Method: " + foxTime + " ms");

            double efficiencyRatio = (double) stripeTime / foxTime;
            System.out.println("Efficiency Ratio (Stripe / Fox): " + efficiencyRatio);
            System.out.println();
        }
    }
}