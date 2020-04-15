package charts;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.JFrame;

import static java.lang.Math.exp;

public class ExponentialChart {
    public static void show(double a, double b){
        XYSeries series = new XYSeries(a + "*e^" + b + "x");
        for (float i = 2f; i <= 9f; i+=0.7f) {
            series.add(i, a * exp(b * i));
        }

        XYDataset xyDataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory
                .createXYLineChart(a + "*e^" + b + "x", "x", "y",
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
