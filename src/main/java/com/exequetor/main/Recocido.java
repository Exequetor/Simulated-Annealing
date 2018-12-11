package com.exequetor.main;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que contiene el algoritmo de recocido simulado. La clase es instanciada
 * como un hilo el cual ejecuta un proceso completo del algoritmo de recocido
 * simulado.
 * 
 * @author Carlos Montellano (Exequetor)
 *
 */
public class Recocido extends Thread {
	public ArrayList<double[]> ciudades = new ArrayList<double[]> ();
	private ArrayList<double[]> grafoInicial = new ArrayList<double[]> ();
	public int threadNumber, cityNum;
	public double apt;
	public boolean finished = false;
	
	public ArrayList<Historial> historial = new ArrayList<Historial> ();
	public Historial mejorCamino;
	public List<Double> historialTemp= new ArrayList<Double>();
	public double mejorAptitud;
	private int iteraciones;
	private double temperatura;
	private double enfriamiento;
	
	public Recocido(int threadNumber, ArrayList<double[]> grafoInicial, int iteraciones, double temperatura, double enfriamiento) {
		this.threadNumber = threadNumber;
		this.grafoInicial.addAll(grafoInicial);
		this.temperatura = temperatura;
		this.enfriamiento = enfriamiento;
		this.iteraciones = iteraciones;
	}
	
	public void run() {
		double nuevaApt;
		ArrayList<double[]> ciudadesNuevas;
		double w;
		double umbral;
		//Generación de la solución inicial.
		ciudades.addAll(grafoInicial);
		apt = aptitud(ciudades);
		historial.add(new Historial (ciudades, apt));
		mejorCamino = new Historial (ciudades, apt);
		mejorAptitud = apt;
		historialTemp.add(temperatura);
		//Iniciar proceso de iteraciones.
		for(int i = 0 ; i < iteraciones ; i++) {
			ciudadesNuevas = new ArrayList<double[]> ();
			//Generar nueva ruta realizando un swap de dos elementos.
			ciudadesNuevas.addAll(swap(ciudades));
			//Calcular la nueva aptitud o margen de error.
			nuevaApt = aptitud(ciudadesNuevas);
			//Guardar la solución.
			historial.add(new Historial (ciudades, apt));
			//Verificar si la nueva ruta es mejor que la anterior.
			if (nuevaApt < apt) {
				ciudades = new ArrayList<double[]> ();
				ciudades.addAll(ciudadesNuevas);
				apt = nuevaApt;
				if (apt < mejorAptitud) {
					mejorAptitud = apt;
					mejorCamino = new Historial (ciudades, apt);
				}
			} else {
				//Implementación de algoritmo de recocido simulado para
				//decidir si cambiar de estado o no y así poder evitar
				//posibles estancamientos en mínimos locales.
				w = Math.exp((apt - nuevaApt)/temperatura); //Probabilidad de aceptación.
				umbral = Math.random() + 0.5; //Umbral de aceptación.
				if (w < umbral) { 
					//Se aceptó el nuevo estado
					ciudades = new ArrayList<double[]> ();
					ciudades.addAll(ciudadesNuevas);
					apt = nuevaApt;
				}
				//Se reduce la temperatura por el factor de enfriamiento.
				temperatura *= enfriamiento;
			}
			historialTemp.add(temperatura);
		}
		finished = true;
	}
	/**
	 * Método para calcular la distancia del recorrido total del grafo para
	 * obtener la aptitud o el margen de error.
	 * 
	 */
	private double aptitud (ArrayList<double[]> dataSet) {
		double acumulator = 0.0f;
		for (int i = 0 ; i < dataSet.size()-1 ; i++) {
			acumulator += Math.sqrt(Math.pow(dataSet.get(i)[0] - dataSet.get(i+1)[0], 2) +
								    Math.pow(dataSet.get(i)[1] - dataSet.get(i+1)[1], 2));
		}
		
		return acumulator;
	}
	
	/**
	 * Método para realizar un cambio de ruta intercambiando el orden
	 * de dos ciudades arbitrarias.
	 */
	private ArrayList<double[]> swap (ArrayList<double[]> ciudades) {
		ArrayList<double[]> nuevoCamino = new ArrayList<double[]> ();
		nuevoCamino.addAll(ciudades);
		int index1 = 0;
		int index2 = 0;
		while (index1 == index2) {
			index1 = (int)(Math.random()*(nuevoCamino.size() - 1));
			index2 = (int)(Math.random()*(nuevoCamino.size() - 1));
		}
		
		double[] temp = nuevoCamino.get(index1); 
		nuevoCamino.set(index1, nuevoCamino.get(index2));
		nuevoCamino.set(index2, temp);
		
		return nuevoCamino;
	}
	
	class Historial {
		public ArrayList<double[]> ciudades = new ArrayList<double[]> ();
		public double apt;
		
		public Historial (ArrayList<double[]> ciudades, double apt) {
			this.ciudades.addAll(ciudades);
			this.apt = apt;
		}
	}
	
	public String toString () {
		StringBuilder str = new StringBuilder ();
		str.append("Thread ").append(threadNumber).append("\nPoblación: \n");
		for(double[] ciudad : ciudades)
			str.append("[").append(ciudad[0]).append(", ").append(ciudad[1]).append("]\n");
		str.append("apt: " + apt);
		return str.toString();
	}
}
