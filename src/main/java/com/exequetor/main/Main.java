/**
 * Problema del agente viajero utilizando el algoritmo de recocido simulado e hilos.
 * 
 * Hecho por Carlos Hernández Montellano.
 * Grupo 902-A.
 * 
 * Ingeniería en Computación.
 * Universidad Tecnológica de la Mixteca.
 * Sábado 8 de diciembre de 2018.
 */
package com.exequetor.main;

import java.util.ArrayList;
import java.util.List;

import org.jfree.ui.RefineryUtilities;

/**
 * Clase principal que contiene el método estático main.
 * 
 * @author Carlos Montellano (Exequetor)
 *
 */
public class Main {
	private static ArrayList<Recocido> threads; 
	public static List<PlotRecocido> plotArray;
	public static int indiceDelMejorHilo = 0;
	public static ArrayList<double[]> grafoInicial;
	
	public static void main(String[] args) {
		InterfazPrincipal interfaz = new InterfazPrincipal ();
		interfaz.frame.setVisible(true);
	}
	
	/**
	 * Método estático que se encarga de instanciar los hilos que ejecutan
	 * el algoritmo de recocido simulado. Es llamado
	 * de forma estática desde la interfaz principal.
	 */
	public static void ejecutar (int threadNumber, int pobTam, int iteraciones, double temp, double enfriamiento) {
		threads = new ArrayList<Recocido> ();
		plotArray = new ArrayList<PlotRecocido> ();
		PlotRecocido plot = null;
		double mejorAptitudTotal = Double.MAX_VALUE;
		
		//Generar el grafo sobre el que trabajará el algoritmo.
		grafoInicial = generarGrafoInicial(pobTam);
		Recocido recocido;
		for (int i = 0 ; i < threadNumber ; i++) {
			recocido = new Recocido (i, grafoInicial, iteraciones, temp, enfriamiento);
			threads.add(recocido);
		}
		//Iniciar los hilos
		for(Recocido thread : threads) {
			thread.start();
		}
		//Esperar el cómputo de todos los hilos
		for(Recocido thread : threads) {
			while(!thread.finished)
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}
	
		//Obtener el mejor resultado de todos los hilos
		for(int i = 0 ; i < threads.size() ; i++) {
			if (threads.get(i).mejorAptitud < mejorAptitudTotal) {
				mejorAptitudTotal = threads.get(i).mejorAptitud;
				indiceDelMejorHilo = i;
			}
		}
		//Generar las gráficas de todos los hilos.
		for(int i = 0 ; i < threads.size() ; i++) {
			String mainTitle;
			Recocido thread = threads.get(i);
			if (i == indiceDelMejorHilo)
				mainTitle = "¡Mejor resultado de todos los hilos! Hilo #" + (i + 1) + ". Mejor aptitud: " + thread.mejorAptitud;
			else
				mainTitle = "Resultados del hilo #" + (i + 1) + ". Mejor aptitud: " + thread.mejorAptitud;
			plot = new PlotRecocido (mainTitle, thread.mejorCamino.ciudades, thread.historial);
			plot.pack();
			RefineryUtilities.centerFrameOnScreen(plot);
			plotArray.add(plot);
		}
	}
	/**
	 * Función que genera el grafo sobre el que se trabajará.
	 */
	private static ArrayList<double[]> generarGrafoInicial (int tamanioPob) {
		ArrayList<double[]> poblacion = new ArrayList<double[]> ();
		for (int i = 0 ; i < tamanioPob ; i++) {
			double[] tupla = new double [2];
			tupla[0] = Math.random()*100;
			tupla[1] = Math.random()*100;
			poblacion.add(tupla);
		}
		return poblacion;
	}
}
