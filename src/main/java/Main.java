import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

import charts.*;
import com.opencsv.CSVWriter;

import static java.lang.Math.*;


@SuppressWarnings("DuplicatedCode")
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("./src/main/resources/file.txt"));
        String x = reader.readLine();
        String y = reader.readLine();
        double[] arrX = Arrays.stream(x.split(" ")).mapToDouble(Double::parseDouble).toArray();
        double[] arrY = Arrays.stream(y.split(" ")).mapToDouble(Double::parseDouble).toArray();
        double[] linear = linear(arrX,arrY);
        double[] polynomial = polynomial(arrX,arrY);
        double[] exponential = exponential(arrX,arrY);
        double[] logarithmic = logarithmic(arrX,arrY);
        double[] power = power(arrX,arrY);

        String[] arrXS = x.split(" ");
        String[] arrYS = y.split(" ");

        //оформление CSV
        String csv = "data.csv";
        CSVWriter writer = new CSVWriter(new FileWriter(csv,true));
        String[] zag = {"X", "Y", "Линейная", "Полиномиальная", "Экспоненциальная", "Логарифмическая", "Степенная"};
        writer.writeNext(zag);
        for (int i = 0; i < arrX.length; i++) {
            String[] pos = {arrXS[i], arrYS[i]
                    , linearF(linear[0],linear[1], arrX[i])
                    , polynomF(polynomial[0], polynomial[1], polynomial[2], arrX[i])
                    , expF(exponential[0], exponential[1], arrX[i])
                    , logF(logarithmic[0], logarithmic[1], arrX[i])
                    , powF(power[0],power[1],arrX[i])};
            writer.writeNext(pos);
        }
        String[] S = {"S", " "
                ,Double.toString(linear[2])
                ,Double.toString(polynomial[3])
                ,Double.toString(exponential[2])
                ,Double.toString(logarithmic[2])
                ,Double.toString(power[2])};
        writer.writeNext(S);

        String[] δ = {"δ", " "
                ,Double.toString(linear[3])
                ,Double.toString(polynomial[4])
                ,Double.toString(exponential[3])
                ,Double.toString(logarithmic[3])
                ,Double.toString(power[3])};
        writer.writeNext(δ);

        writer.writeNext(new String[] {"a", " "
                ,Double.toString(linear[0])
                ,Double.toString(polynomial[0])
                ,Double.toString(exponential[0])
                ,Double.toString(logarithmic[0])
                ,Double.toString(power[0])
        });

        writer.writeNext(new String[] {"b", " "
                ,Double.toString(linear[1])
                ,Double.toString(polynomial[1])
                ,Double.toString(exponential[1])
                ,Double.toString(logarithmic[1])
                ,Double.toString(power[1])
        });

        writer.writeNext(new String[] {"c", "", "", Double.toString(polynomial[2]), "", "", ""});
        writer.close();

        //Класcический файлрайтер .txt
        FileWriter fileWriter = new FileWriter("data.txt",true);
        fileWriter.write("X \t Y \t \t Линейная \t \t \t Полиномиальная \t  Экспоненциальная \t  Логарифмическая \t  Степенная");
        fileWriter.write("\n");
        for (int i = 0; i < arrX.length; i++) {
            fileWriter.write(arrX[i] + "\t"
                    + arrY[i] + "\t"
                    + linearF(linear[0],linear[1], arrX[i]) + "\t"
                    + polynomF(polynomial[0], polynomial[1], polynomial[2], arrX[i]) + "\t"
                    + expF(exponential[0], exponential[1], arrX[i]) + "\t"
                    + logF(logarithmic[0], logarithmic[1], arrX[i]) + "\t"
                    + powF(power[0],power[1],arrX[i]));
            fileWriter.write("\n");
        }
        fileWriter.write("S\t\t\t" + linear[2] + "\t"
                + polynomial[3] + "\t"
                + exponential[2] + "\t"
                + logarithmic[2] + "\t"
                + power[2] + "\t" );
        fileWriter.write("\n");

        fileWriter.write("δ\t\t\t" + linear[3] + "\t"
                + polynomial[4] + "\t"
                + exponential[3] + "\t"
                + logarithmic[3] + "\t"
                + power[3] + "\t" );
        fileWriter.write("\n");

        fileWriter.write("a\t\t\t" + linear[0] + "\t"
                + polynomial[0] + "\t"
                + exponential[0] + "\t"
                + logarithmic[0] + "\t"
                + power[0] + "\t" );
        fileWriter.write("\n");

        fileWriter.write("b\t\t\t" + linear[1] + "\t"
                + polynomial[1] + "\t"
                + exponential[1] + "\t"
                + logarithmic[1] + "\t"
                + power[1] + "\t" );
        fileWriter.write("\n");

        fileWriter.write("c\t\t\t\t\t\t\t\t" + polynomial[3]);
        fileWriter.write("\n");

        fileWriter.close();
    }

    //СКО
    public static double sko(double[] fi, double[] y){
        double answer = 0;
        for (int i = 0; i < fi.length; i++) {
            answer += pow((fi[i] - y[i]),2);
        }
        answer = sqrt(answer/fi.length);
        return answer;
    }

    //Линейная ф-ия
    public static double[] linear(double[] arrX, double[] arrY){
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

        double sko = sko(yLinear, arrY);

        //вывод графика
        LinearChart.show(a,b);

        return new double[] {a,b,sLinear,sko};
    }

    //Квадратичная ф-ия
    public static double[] polynomial(double[] arrX, double[] arrY){
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

        double sko = sko(yPolynomial, arrY);

        //вывод графика
        PolynomialChart.show(answer[2],answer[1],answer[0]);


        return new double[] {answer[2], answer[1], answer[0], sPolynomial,sko};
    }

    //Экспоненциальная ф-ия
    public static double[] exponential(double[] arrX, double[] arrY){
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

        double sko = sko(yExponential, arrY);

        //Вывод графика
        ExponentialChart.show(a,b);

        return new double[] {a,b,sExponential,sko};
    }

    //Логарифмическая ф-ия
    public static double[] logarithmic(double[] arrX, double[] arrY){
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

        double sko = sko(yLogarithmic, arrY);

        LogarithmicChart.show(a,b);

        return new double[] {a,b,sLogarithmic,sko};
    }

    //Степенная ф-ия
    public static double[] power(double[] arrX, double[] arrY){
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

        double sko = sko(yPower, arrY);

        PowerChart.show(a,b);

        return new double[] {a,b,sPower,sko};
    }

    //Блок функций
    public static String linearF(double a, double b, double x){
        return Double.toString(a * x + b);
    }

    public static String polynomF(double a, double b, double c, double x){
        return Double.toString(a * pow(x,2) + b * x + c);
    }

    public static String expF(double a, double b, double x){
        return Double.toString(a * exp(b * x));
    }

    public static String logF(double a, double b, double x){
        return Double.toString(a * log(x) + b);
    }

    public static String powF(double a, double b, double x){
        return Double.toString(a * pow(x,b));
    }
}