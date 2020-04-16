import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

import charts.*;

//import static charts.LinearChart.show;
import static java.lang.Math.*;


@SuppressWarnings("DuplicatedCode")
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("./src/main/resources/file.txt"));
        String x = reader.readLine();
        String y = reader.readLine();
        double[] arrX = Arrays.stream(x.split(" ")).mapToDouble(Double::parseDouble).toArray();
        double[] arrY = Arrays.stream(y.split(" ")).mapToDouble(Double::parseDouble).toArray();
        //linear(arrX,arrY);
        polynomial(arrX,arrY);
        //exponential(arrX,arrY);
        //logarithmic(arrX,arrY);
        //power(arrX,arrY);
    }

    public static double sko(double[] fi, double[] y){
        double answer = 0;
        for (int i = 0; i < fi.length; i++) {
            answer += pow((fi[i] - y[i]),2);
        }
        answer = sqrt(answer/fi.length);
        return answer;
    }

    //Линейная ф-ия
    public static void linear(double[] arrX, double[] arrY){
        int n = arrX.length;
        double sx = DoubleStream.of(arrX).sum();
        double sy = DoubleStream.of(arrY).sum();
        double sxx = DoubleStream.of(arrX).map(num -> pow(num,2)).sum();
        double sxy = IntStream.range(0,n).mapToDouble(i -> arrX[i] * arrY[i]).sum();
        double a = (sxy * n - sx * sy)/(sxx * n - sx * sx);
        double b = (sxx * sy - sx * sxy)/(sxx * n - sx * sx);
        double[] yLinear = new double[n];
        Arrays.setAll(yLinear,i -> a * arrX[i] + b);

        double sLinear = 0;
        for (int i = 0; i < n; i++) {
            sLinear += pow((yLinear[i] - arrY[i]),2);
        }
        //вывод графика
        LinearChart.show(a,b);
    }

    //Квадратичная ф-ия
    public static void polynomial(double[] arrX, double[] arrY){
        int n = arrX.length;
        double sx = DoubleStream.of(arrX).sum();
        double sy = DoubleStream.of(arrY).sum();
        double sxx = DoubleStream.of(arrX).map(num -> pow(num,2)).sum();
        double sxy = IntStream.range(0,n).mapToDouble(i -> arrX[i] * arrY[i]).sum();
        double sxxx = DoubleStream.of(arrX).map(num -> pow(num,3)).sum();
        double sxxxx = DoubleStream.of(arrX).map(num -> pow(num,4)).sum();
        double sxxy = IntStream.range(0,n).mapToDouble(i -> pow(arrX[i],2) * arrY[i]).sum();

        double[][] sendX = {{n,sx,sxx},{sx,sxx,sxxx},{sxx,sxxx,sxxxx}};
        double[] sendC = {sy,sxy,sxxy};
        double[] answer = Kramer.solve(sendX,sendC);

        double[] yPolynomial = new double[n];
        Arrays.setAll(yPolynomial,i -> answer[2] * pow(arrX[i],2) + answer[1] * arrX[i] + answer[0]);

        double sPolynomial = 0;
        for (int i = 0; i < n; i++) {
            sPolynomial += (pow(yPolynomial[i] - arrY[i],2));
        }
        //вывод графика
        PolynomialChart.show(answer[2],answer[1],answer[0]);
    }

    //Экспоненциальная ф-ия
    public static void exponential(double[] arrX, double[] arrY){
        int n = arrX.length;
        double sx = DoubleStream.of(arrX).sum();
        double sy = DoubleStream.of(arrY).sum();
        double sxx = DoubleStream.of(arrX).map(num -> pow(num,2)).sum();
        double sxy = IntStream.range(0,n).mapToDouble(i -> arrX[i] * arrY[i]).sum();
        double a = exp((sxy * n - sx * sy)/(sxx * n - sx * sx));
        double b = exp((sxx * sy - sx * sxy)/(sxx*n-sx*sx));
        double[] yExponential = new double[n];
        Arrays.setAll(yExponential,i -> a * exp(b * arrX[i]));

        double sExponential = 0;
        for (int i = 0; i < n; i++) {
            sExponential += (pow(yExponential[i] - arrY[i],2));
        }

        for (double huy :
                yExponential) {
            System.out.println(huy);
        }
        //Вывод графика
        ExponentialChart.show(a,b);
    }

    //Логарифмическая ф-ия
    public static void logarithmic(double[] arrX, double[] arrY){
        int n = arrX.length;
        double sx = DoubleStream.of(arrX).map(num -> log(num)).sum();
        double sy = DoubleStream.of(arrY).sum();
        double sxx = DoubleStream.of(arrX).map(num -> pow(log(num),2)).sum();
        double sxy = IntStream.range(0,n).mapToDouble(i -> log(arrX[i]) * arrY[i]).sum();
        double a = (sxy * n - sx * sy)/(sxx * n - sx * sx);
        double b = (sxx * sy - sx * sxy)/(sxx * n - sx * sx);
        double[] yLogarithmic = new double[n];
        Arrays.setAll(yLogarithmic,i -> a * log(arrX[i]) + b);

        double sLogarithmic = 0;
        for (int i = 0; i < n; i++) {
            sLogarithmic += (pow(yLogarithmic[i] - arrY[i],2));
        }

        LogarithmicChart.show(a,b);
    }

    //Степенная ф-ия
    public static void power(double[] arrX, double[] arrY){
        int n = arrX.length;
        double slnx = DoubleStream.of(arrX).map(num -> log(num)).sum();
        double slny = DoubleStream.of(arrY).map(num -> log(num)).sum();
        double slnxx = DoubleStream.of(arrX).map(num -> log(num) * log(num)).sum();
        double slnxlny = IntStream.range(0,n).mapToDouble(i -> log(arrX[i]) * log(arrY[i])).sum();
        double b = (n * slnxlny - slnx * slny)/(n * slnxx - slnx * slnx);
        double a = exp(0.091 * slny - b/n * slnx);

        double[] yPower = new double[11];
        Arrays.setAll(yPower,i -> a * pow(arrX[i],b));

        double sPower = 0;
        for (int i = 0; i < n; i++) {
            sPower += (pow(yPower[i] - arrY[i],2));
        }

        PowerChart.show(a,b);
    }
}