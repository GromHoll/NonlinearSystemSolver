package edu.gromholl.math;


import java.util.Arrays;
import java.util.stream.IntStream;

public class GaussLinearSystemSolver implements LinearSystemSolver {

    @Override
    public double[] solve(double[][] matrixOrigin, double[] rightPartOrigin) {
        assert matrixOrigin.length == rightPartOrigin.length;
        assert matrixOrigin[0].length == rightPartOrigin.length;

        int dimension = rightPartOrigin.length;

        double[][] matrix = new double[dimension][dimension];
        IntStream.range(0, dimension).forEach(index -> matrix[index] = Arrays.copyOf(matrixOrigin[index], dimension));

        double[] rightPart =  Arrays.copyOf(rightPartOrigin, dimension);

        IntStream.range(0, dimension).forEach(diagonalIndex -> {

            int maxIndex = diagonalIndex;
            for (int row = diagonalIndex; row < dimension; row++) {
                if (matrix[row][diagonalIndex] > matrix[row][maxIndex]) {
                    maxIndex = row;
                }
            }

            double[] tempRow = matrix[diagonalIndex];
            matrix[diagonalIndex] = matrix[maxIndex];
            matrix[maxIndex] = tempRow;

            double temp = rightPart[diagonalIndex];
            rightPart[diagonalIndex] = rightPart[maxIndex];
            rightPart[maxIndex] = temp;

            double leadValue = matrix[diagonalIndex][diagonalIndex];
            for(int column = diagonalIndex; column < dimension; column++) {
                matrix[diagonalIndex][column] /= leadValue;
            }
            rightPart[diagonalIndex] /= leadValue;

            for(int row = diagonalIndex + 1; row < dimension; row++) {
                double toDivide = matrix[row][diagonalIndex]/matrix[diagonalIndex][diagonalIndex];
                for(int column = diagonalIndex; column < dimension; column++) {
                    matrix[row][column] -= matrix[diagonalIndex][column]*toDivide;
                }
                rightPart[row] -= rightPart[diagonalIndex]*toDivide;
            }
        });

        double[] solution = new double[dimension];

        solution[dimension - 1] = rightPart[dimension - 1];
        for (int row = dimension - 2; row >= 0; row--) {
            double sum = rightPart[row];
            for (int column = row + 1; column < dimension; column++) {
                sum -= matrix[row][column]*solution[column];
            }
            solution[row] = sum;
        }

        return solution;
    }

}
