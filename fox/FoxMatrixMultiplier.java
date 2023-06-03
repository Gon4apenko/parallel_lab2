package fox;

import models.Matrix;
import models.Result;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FoxMatrixMultiplier {
    private static final int THREAD_COUNT = 2;

    public Result multiply(Matrix matrix1, Matrix matrix2, int threadCount) {
        int n = matrix1.getRows();
        int blockSize = n / THREAD_COUNT;

        if (matrix1.getColumns() != matrix2.getRows()) {
            throw new IllegalArgumentException("The number of columns in the first matrix must be equal to the number of rows in the second matrix.");
        }

        Matrix[][] matrix1Blocks = splitMatrix(matrix1, blockSize);
        Matrix[][] matrix2Blocks = splitMatrix(matrix2, blockSize);

        Result result = new Result(n, n);
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            for (int j = 0; j < THREAD_COUNT; j++) {
                executor.submit(new MultiplierThreadFox(matrix1Blocks[i][j], matrix2Blocks[i][j], result, i, j, blockSize));
            }
        }

        try {
            executor.shutdown();
            executor.awaitTermination(100L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }

    private Matrix[][] splitMatrix(Matrix matrix, int blockSize) {
        int n = matrix.getRows();
        Matrix[][] blocks = new Matrix[THREAD_COUNT][THREAD_COUNT];

        for (int i = 0; i < THREAD_COUNT; i++) {
            for (int j = 0; j < THREAD_COUNT; j++) {
                int startRow = i * blockSize;
                int startColumn = j * blockSize;
                int[][] blockData = new int[blockSize][blockSize];

                for (int row = 0; row < blockSize; row++) {
                    for (int column = 0; column < blockSize; column++) {
                        blockData[row][column] = matrix.getValueAt(startRow + row, startColumn + column);
                    }
                }

                blocks[i][j] = new Matrix(blockData);
            }
        }

        return blocks;
    }
}