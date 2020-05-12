package charts;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;

import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleInsets;

import java.awt.*;

import static java.lang.Math.*;

public class LineChart extends ApplicationFrame
{
    private static final long serialVersionUID = 1L;

    public LineChart(final String title, double[] a, double[] b, double c)
    {
        super(title);

        JFreeChart chart      = createChart(a, b, c);
        ChartPanel chartPanel = new ChartPanel(chart);

        chartPanel.setPreferredSize(new java.awt.Dimension(560, 480));
        setContentPane(chartPanel);
    }

    private JFreeChart createChart(double[] a, double[] b, double c)
    {
        final JFreeChart chart = ChartFactory.createXYLineChart(
                "Chart",
                null,                        // x axis label
                null,                        // y axis label
                null,                        // data
                PlotOrientation.VERTICAL,
                true,                        // include legend
                false,                       // tooltips
                false                        // urls
        );

        chart.setBackgroundPaint(Color.white);

        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(new Color(232, 232, 232));

        plot.setDomainGridlinePaint(Color.gray);
        plot.setRangeGridlinePaint (Color.gray);

        // Определение отступа меток делений
        plot.setAxisOffset(new RectangleInsets(1.0, 1.0, 1.0, 1.0));

        // Скрытие осевых линий и меток делений
        ValueAxis axis = plot.getDomainAxis();
        axis.setAxisLineVisible (false);    // осевая линия

        // Настройка NumberAxis
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setAxisLineVisible (false);
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // Настройка XYSplineRenderer
        // Precision: the number of line segments between 2 points [default: 5]
        XYSplineRenderer r0 = new XYSplineRenderer();
        r0.setSeriesShapesVisible (0, false);

        XYSplineRenderer r1 = new XYSplineRenderer();
        r1.setSeriesShapesVisible (0, false);
        r1.setSeriesPaint         (0, Color.blue);

        XYSplineRenderer r2 = new XYSplineRenderer();
        r2.setSeriesShapesVisible (0, false);
        r2.setSeriesPaint         (0, Color.orange);

        XYSplineRenderer r3 = new XYSplineRenderer();
        r3.setSeriesShapesVisible (0, false);
        r3.setSeriesPaint         (0, Color.green);

        XYSplineRenderer r4 = new XYSplineRenderer();
        r4.setSeriesShapesVisible (0, false);
        r4.setSeriesPaint         (0, Color.yellow);



        // Наборы данных
        XYSeries seriesExp = new XYSeries(a[2] + "*e^" + b[2] + "x");
        for (float i = 2f; i <= 9f; i+=0.7f) {
            seriesExp.add(i, a[2] * exp(b[2] * i));
        }
        XYDataset datasetExp = new XYSeriesCollection(seriesExp);

        XYSeries seriesLin = new XYSeries(a[0] + "*x" + b[0]);
        for (float i = 2f; i <= 9f; i+=0.7f) {
            seriesLin.add(i, a[0] * i + b[0]);
        }
        XYDataset datasetLin = new XYSeriesCollection(seriesLin);

        XYSeries seriesPol = new XYSeries(a[1] + "*x²+" + b[1] + "*x+(" + c + ")");
        for (float i = 2f; i <= 9f; i+=0.7f) {
            seriesLin.add(i, a[1] * pow(i,2) + b[1] * i + c);
        }
        XYDataset datasetPol = new XYSeriesCollection(seriesPol);

        XYSeries seriesPow = new XYSeries(a[4] + "x^" + b[4]);
        for (float i = 2f; i <= 9f; i+=0.7f) {
            seriesPow.add(i, a[4]*pow(i,b[4]));
        }
        XYDataset datasetPow = new XYSeriesCollection(seriesPow);

        XYSeries seriesLog = new XYSeries(a[3] + "log(x)+" + b[3]);
        for (float i = 2f; i <= 9f; i+=0.7f) {
             seriesLog.add(i, a[3] * log(i) + b[3]);
        }
        XYDataset datasetLog = new XYSeriesCollection(seriesLog);

        plot.setDataset(0, datasetLin);
        plot.setDataset(1, datasetPol);
        plot.setDataset(2, datasetExp);
        plot.setDataset(3, datasetLog);
        plot.setDataset(4, datasetPow);

        plot.setRenderer(0, r0);
        plot.setRenderer(1, r1);
        plot.setRenderer(2, r2);
        plot.setRenderer(3, r3);
        plot.setRenderer(4, r4);

        return chart;
    }
}
