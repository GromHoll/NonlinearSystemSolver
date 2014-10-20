package edu.gromholl.math;


import java.util.Arrays;

import static edu.gromholl.math.Polynomial.Variable;

public class NewtonPolynomialSolver implements PolynomialSolver {

    private final double error;
    private final LinearSystemSolver linearSystemSolver;

    public NewtonPolynomialSolver(LinearSystemSolver linearSystemSolver, double error) {
        this.linearSystemSolver = linearSystemSolver;
        this.error = error;
    }

    @Override
    public double[] solve(Polynomial[] polynomials, Variable[] variables) {
        assert polynomials.length == variables.length;

        final int dimension = polynomials.length;

        double[] solution = new double[dimension];
        double[] newSolution = new double[dimension];
        Arrays.fill(solution, 0);
        Arrays.fill(newSolution, 0);

        do {
            double[][] matrix = new double[dimension][dimension];
            double[] rightPart = new double[dimension];

            solution = Arrays.copyOf(newSolution, solution.length);

            for (int polynomialIndex = 0; polynomialIndex < polynomials.length; polynomialIndex++) {
                Polynomial polynomial = polynomials[polynomialIndex];
                for (int variableIndex = 0; variableIndex < variables.length; variableIndex++) {
                    Variable variable = variables[variableIndex];
                    matrix[polynomialIndex][variableIndex] = polynomial.derivative(variable).calculate(solution);
                }
                rightPart[polynomialIndex] = -polynomial.calculate(solution);
            }

            double[] linearSolution = linearSystemSolver.solve(matrix, rightPart);
            for (int i = 0; i < linearSolution.length; i++) {
                newSolution[i] = solution[i] + linearSolution[i];
            }
        } while (Math.abs(norm(solution) - norm(newSolution)) >= error);

        return newSolution;
    }

}
