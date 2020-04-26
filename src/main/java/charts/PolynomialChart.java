package charts;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.JFrame;

import static java.lang.Math.pow;

public class PolynomialChart {
    public static void show(double a2, double a1, double a0){
        XYSeries series = new XYSeries(a2 + "*x²+" + a1 + "*x+(" + a0 + ")");
        for (float i = 2f; i <= 9f; i+=0.7f) {
            series.add(i, a2 * pow(i,2) + a1 * i + a0);
        }

        XYDataset xyDataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory
                .createXYLineChart(a2 + "*x²+" + a1 + "*x+(" + a0 + ")", "x", "y",
                        xyDataset, PlotOrientation.VERTICAL,
                        true,true, true);
        JFrame frame = new JFrame("StaticChart");
        //Помещаем график на фрейм
        frame.getContentPane()
                .add(new ChartPanel(chart));
        frame.setSize(800,800);
        frame.show();
    }
}
