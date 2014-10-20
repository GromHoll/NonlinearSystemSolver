package edu.gromholl.math;


public interface LinearSystemSolver {
    public double[] solve(double[][] matrix, double[] rightPart);
}
