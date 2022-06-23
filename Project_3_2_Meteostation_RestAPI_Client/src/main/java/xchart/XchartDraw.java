package xchart;

import dto.MeasurementDTO;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import java.util.List;

// рисуем диаграмму распределения температуры с помощью библиотеки xchart
public class XchartDraw {

    public static void draw( List<MeasurementDTO> measurements) {

        double[] xData = new double[measurements.size()];
        double[] yData = new double[measurements.size()];

        for (int i = 0; i < measurements.size(); i++) {
            xData[i] = measurements.get(i).getValue();
            yData[i] = i;
        }

        XYChart chart = QuickChart.getChart("Meteo measurements", "x", "y", "x(y)", xData, yData);

        new SwingWrapper<>(chart).displayChart();

    }

}
