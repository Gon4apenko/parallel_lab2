package fox;

import models.Matrix;
import models.Result;

class MultiplierThreadFox implements Runnable {
    private Matrix matrix1;
    private Matrix matrix2;
    private Result result;
    private int row;
    private int column;
    private int blockSize;

    public MultiplierThreadFox(Matrix matrix1, Matrix matrix2, Result result, int row, int column, int blockSize) {
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
        this.result = result;
        this.row = row;
        this.column = column;
        this.blockSize = blockSize;
    }

    @Override
    public void run() {
        int n = matrix1.getRows();

        for (int i = 0; i < n; i++) {
            Matrix temp = multiplyBlocks(matrix1, matrix2);
            addToResult(result, temp, row * blockSize, column * blockSize);

            matrix1 = shiftMatrix(matrix1, 1, 0, blockSize);
            matrix2 = shiftMatrix(matrix2, 0, 1, blockSize);
        }
    }

    private Matrix multiplyBlocks(Matrix matrix1, Matrix matrix2) {
        int[][] resultData = new int[blockSize][blockSize];

        for (int i = 0; i < blockSize; i++) {
            for (int j = 0; j < blockSize; j++) {
                int value = 0;
                for (int k = 0; k < blockSize; k++) {
                    value += matrix1.getValueAt(i, k) * matrix2.getValueAt(k, j);
                }
                resultData[i][j] = value;
            }
        }

        return new Matrix(resultData);
    }

    private Matrix shiftMatrix(Matrix matrix, int shiftRows, int shiftColumns, int blockSize) {
        int n = matrix.getRows();
        int[][] newData = new int[n][n];

        for (int i = 0; i < blockSize; i++) {
            for (int j = 0; j < blockSize; j++) {
                int newRow = (row + shiftRows + i) % n;
                int newColumn = (column + shiftColumns + j) % n;
                newData[newRow][newColumn] = matrix.getValueAt(i, j);
            }
        }

        return new Matrix(newData);
    }

    private void addToResult(Result result, Matrix blockResult, int startRow, int startColumn) {
        for (int i = 0; i < blockSize; i++) {
            for (int j = 0; j < blockSize; j++) {
                result.setValueAt(startRow + i, startColumn + j, blockResult.getValueAt(i, j));
            }
        }
    }
}