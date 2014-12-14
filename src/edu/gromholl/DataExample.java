package edu.gromholl;

import edu.gromholl.math.Polynomial;

/**
 * @author GromHoll
 */
public class DataExample {

    public static final double ALPHA = 3;
    public static final double BETA  = 7;

    public static final double A1 =  3;
    public static final double B1 =  8;
    public static final double C1 = -4;
    public static final double D1 = -2;
    public static final double A2 =  1;
    public static final double B2 =  4;
    public static final double C2 =  4;
    public static final double D2 = -4;
    public static final double F1 =  9;
    public static final double F2 = 13;

    public static Polynomial firstFunction(Polynomial.Variable x1, Polynomial.Variable x2) {
        Polynomial polynomial = new Polynomial(x1, x2);
        return polynomial.addPart(A1, x1, ALPHA).addPart(B1, x1).addPart(C1, x2).addPart(D1).addPart(-F1);
    }

    public static Polynomial secondFunction(Polynomial.Variable x1, Polynomial.Variable x2) {
        Polynomial polynomial = new Polynomial(x1, x2);
        return polynomial.addPart(A2, x2, BETA).addPart(B2, x1).addPart(C2, x2).addPart(D2).addPart(-F2);
    }
}
