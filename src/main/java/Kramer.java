public class Kramer {
    public static double[] solve(double[][] x, double[] c){
        double delta = x[0][0] * x[1][1] * x[2][2] +
                x[1][0] * x[2][1] * x[0][2] +
                x[0][1] * x[1][2] * x[2][0] -
                x[0][2] * x[1][1] * x[2][0] -
                x[0][1] * x[1][0] * x[2][2] -
                x[1][2] * x[2][1] * x[0][0];
        double delta1 = c[0] * (x[1][1] * x[2][2] - x[2][1] * x[1][2]) -
                c[1] * (x[0][1] * x[2][2] - x[2][1] * x[0][2]) +
                c[2] * (x[0][1] * x[1][2] - x[1][1] * x[0][2]);
        double delta2 = x[0][0] * (c[1] * x[2][2] - c[2] * x[1][2]) -
                x[1][0] * (c[0] * x[2][2] - c[2] * x[0][2]) +
                x[2][0] * (c[0] * x[1][2] - c[1] * x[0][2]);
        double delta3 = x[0][0] * (x[1][1] * c[2] - x[2][1] * c[1]) -
                x[1][0] * (x[0][1] * c[2] - x[2][1] * c[0]) +
                x[2][0] * (x[0][1] * c[1] - x[1][1] * c[0]);

        double[] answer = {delta1/delta, delta2/delta, delta3/delta};
        return answer;
     }
}
