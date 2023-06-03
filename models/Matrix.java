package models;

public class Matrix {
    private int rows;
    private int columns;
    private int[][] data;

    public Matrix(int[][] data) {
        this.rows = data.length;
        this.columns = data[0].length;
        this.data = data;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getValueAt(int row, int column) {
        return data[row][column];
    }

    public void setValueAt(int row, int column, int value) {
        data[row][column] = value;
    }
}