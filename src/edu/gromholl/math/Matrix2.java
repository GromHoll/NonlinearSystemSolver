package edu.gromholl.math;

/**
 * @author GromHoll
 */
public class Matrix2 {

    private static final int SIZE = 2;

    double[][] values = new double[SIZE][SIZE];

    public double getValueAt(int rowIndex, int columnIndex) {
        return values[rowIndex][columnIndex];
    }

    public void setValueAt(int rowIndex, int columnIndex, double value) {
        values[rowIndex][columnIndex] = value;
    }

    public Matrix2 mult(double scalar) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                values[i][j] *= scalar;
            }
        }
        return this;
    }

    public Vector2 multOnColumn(Vector2 vector) {
        Vector2 result = new Vector2();
        result.x = values[0][0]*vector.x + values[0][1]*vector.y;
        result.y = values[1][0]*vector.x + values[1][1]*vector.y;
        return result;
    }

    public Matrix2 sum(Matrix2 matrix) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                values[i][j] += matrix.values[i][j];
            }
        }
        return this;
    }

    public Matrix2 inverse() {
        Matrix2 result = new Matrix2();

        result.values[0][0] = values[1][1];
        result.values[1][1] = values[0][0];
        result.values[0][1] = -values[1][0];
        result.values[1][0] = -values[0][1];
        result.mult(1d/determinant());

        return result;
    }

    public double determinant() {
        return values[0][0]*values[1][1] - values[0][1]*values[1][0];
    }

    public static Matrix2 CreateE() {
        Matrix2 e = new Matrix2();
        e.values[0][0] = e.values[1][1] = 1;
        e.values[0][1] = e.values[1][0] = 0;
        return e;
    }
}
