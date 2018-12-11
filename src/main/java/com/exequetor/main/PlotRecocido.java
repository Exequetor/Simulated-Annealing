package com.exequetor.main;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.exequetor.main.Recocido.Historial;

/**
 * Clase que se encarga de generar la interfaz de las gráficas de cada hilo.
 * 
 * @author Carlos Montellano (Exequetor)
 *
 */
public class PlotRecocido extends JFrame{
	private static final long serialVersionUID = 7949914046045690806L;

	XYSeries series;
	XYPlot plot;
	XYLineAndShapeRenderer renderer;
	ChartPanel chartPanel;
	XYSeriesCollection data;
	JFreeChart chart;
	
	public PlotRecocido(String mainTitle, ArrayList<double[]> dataSet, ArrayList<Historial> historia) {
		super(mainTitle);
		setLayout(new GridLayout(0,2));
		
	    series = new XYSeries("Grafo del mejor camino", false);
	    
	    for (double[] tupla : dataSet) {
	    	series.add(tupla[0], tupla[1]);
	    }
	    
	    data = new XYSeriesCollection(series);
	    chart = ChartFactory.createXYLineChart(
	    	"Grafo del mejor camino",
	        "X", 
	        "Y", 
	        data,
	        PlotOrientation.VERTICAL,
	        true,
	        true,
	        false
	    );

	    plot = (XYPlot) chart.getPlot();
	    renderer = new XYLineAndShapeRenderer();
	    renderer.setSeriesLinesVisible(0, true);
	    renderer.setSeriesShape(0, new Ellipse2D.Double(-3d, -3d, 6d, 6d));
	    plot.setRenderer(renderer);
	    chartPanel = new ChartPanel(chart);
	    chartPanel.setPreferredSize(new java.awt.Dimension(500, 500));
	 
	    add(chartPanel);
	    
	    series = new XYSeries("Valor de error por iteración", false);
	    
	    for (int i = 0 ; i < historia.size() ; i++) {
	    	series.add(i, historia.get(i).apt);
	    }
	    
	    data = new XYSeriesCollection(series);
	    chart = ChartFactory.createXYLineChart(
	    	"Valor de error por iteración",
	        "Iteración", 
	        "Valor de error", 
	        data,
	        PlotOrientation.VERTICAL,
	        true,
	        true,
	        false
	    );

	    plot = (XYPlot) chart.getPlot();
	    renderer = new XYLineAndShapeRenderer();
	    renderer.setSeriesLinesVisible(0, true);
	    renderer.setSeriesShape(0, new Ellipse2D.Double(-3d, -3d, 6d, 6d));
	    renderer.setSeriesPaint(0, Color.BLUE);
	    plot.setRenderer(renderer);
	    chartPanel = new ChartPanel(chart);
	    chartPanel.setPreferredSize(new java.awt.Dimension(500, 500));
	    
	    add(chartPanel);
	}

}
