import java.util.Random;

public class MatrixGenerator {
    private Random random;

    public MatrixGenerator() {
        this.random = new Random();
    }

    public int[][] generate(int rows, int columns, int maxValue) {
        int[][] matrix = new int[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = random.nextInt(maxValue + 1);
            }
        }

        return matrix;
    }
}
