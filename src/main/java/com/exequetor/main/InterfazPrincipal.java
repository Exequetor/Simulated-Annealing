package com.exequetor.main;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;

/**
 * Clase que contiene las instrucciones para generar la interfaz principal del programa
 * para configurar los datos de entrada, iniciar el algoritmo y navegar entre los
 * diferentes resultados de los hilos.
 * 
 * @author Carlos Montellano (Exequetor)
 * 
 */
public class InterfazPrincipal implements ActionListener{

	public JFrame frame;
	public JTextField fieldIteraciones;
	public JTextField fieldTamPob;
	public JTextField fieldTemp;
	public JTextField fieldEnfriamiento;
	public JTextField fieldNumHilos;
	public JButton btnMostrarMejorResultado;
	public JButton btnEjecutar;
	public static List<JButton> btnHilos = new ArrayList<JButton> ();;
	public int numHilos, tamPob, iteraciones;
	public double temperatura, enfriamiento;
	
	private JLabel lblHazClickPara;
	
	public InterfazPrincipal() {
		initialize();
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Problema del agente viajero utilizando el algoritmo de recocido simulado. Metaheuristicas. Hecho por Carlos Montellano. 902-A UTM. 2018.");
		frame.setBounds(100, 100, 557, 360);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[][grow][][][][][][]", "[][][][][][][][][][][][][]"));
		
		JLabel lblIteraciones = new JLabel("Iteraciones");
		frame.getContentPane().add(lblIteraciones, "cell 0 0,alignx left");
		
		fieldIteraciones = new JTextField();
		fieldIteraciones.setText("100");
		frame.getContentPane().add(fieldIteraciones, "cell 1 0,alignx left,aligny baseline");
		fieldIteraciones.setColumns(10);
		
		lblHazClickPara = new JLabel("Haz click para mostrar los resultados de uno de los hilos.");
		lblHazClickPara.setVisible(false);
		frame.getContentPane().add(lblHazClickPara, "cell 2 0 6 1");
		
		JLabel lblCantidadDeHilos = new JLabel("Cantidad de hilos");
		frame.getContentPane().add(lblCantidadDeHilos, "cell 0 4,alignx left");
		JLabel lblTamaoPoblacin = new JLabel("Cantidad de ciudades");
		frame.getContentPane().add(lblTamaoPoblacin, "cell 0 1,alignx trailing");
		JLabel lblTemperatura = new JLabel("Temperatura");
		frame.getContentPane().add(lblTemperatura, "cell 0 2,alignx left");
		JLabel lblEnfriamiento = new JLabel("Enfriamiento");
		frame.getContentPane().add(lblEnfriamiento, "cell 0 3,alignx left");
		
		fieldTamPob = new JTextField();
		fieldTamPob.setText("10");
		frame.getContentPane().add(fieldTamPob, "cell 1 1,alignx left");
		fieldTamPob.setColumns(10);
		
		fieldEnfriamiento = new JTextField();
		fieldEnfriamiento.setText("0.99995");
		frame.getContentPane().add(fieldEnfriamiento, "cell 1 3,alignx left");
		fieldEnfriamiento.setColumns(10);
		
		fieldNumHilos = new JTextField();
		fieldNumHilos.setText("10");
		frame.getContentPane().add(fieldNumHilos, "cell 1 4,alignx left");
		fieldNumHilos.setColumns(10);
		
		fieldTemp = new JTextField();
		fieldTemp.setText("10000000");
		frame.getContentPane().add(fieldTemp, "cell 1 2");
		fieldTemp.setColumns(10);
		
		btnMostrarMejorResultado = new JButton("Mostrar mejor resultado");
		btnMostrarMejorResultado.setEnabled(false);
		frame.getContentPane().add(btnMostrarMejorResultado, "cell 0 11 2 1,growx,aligny center");
		btnMostrarMejorResultado.addActionListener(this);
		
		btnEjecutar = new JButton("Ejecutar");
		frame.getContentPane().add(btnEjecutar, "cell 0 12 2 1,growx,aligny center");
		btnEjecutar.addActionListener(this);
		
		for (int i = 0 ; i < 12 ; i++)
			for (int j = 0 ; j < 12 ; j++) {
				JButton button = new JButton("Hilo " + (i*12 + j + 1));
				frame.getContentPane().add(button, "cell " + (i+3) + " " + (j+1));
				button.addActionListener(this);
				button.setVisible(false);
				btnHilos.add(button);
			}
		frame.pack();
		frame.setLocationRelativeTo(null);
	}

	public void actionPerformed(ActionEvent e) {
		Object btnEvent = e.getSource();
		if(btnEvent.equals(btnEjecutar)) {
			numHilos = Integer.parseInt(fieldNumHilos.getText());
			iteraciones = Integer.parseInt(fieldIteraciones.getText());
			tamPob = Integer.parseInt(fieldTamPob.getText());
			temperatura = Double.parseDouble(fieldTemp.getText());
			enfriamiento = Double.parseDouble(fieldEnfriamiento.getText());
			
			generarBotones();
			Main.ejecutar(numHilos, tamPob, iteraciones, temperatura, enfriamiento);
			btnMostrarMejorResultado.setEnabled(true);
		}
		for (int i = 0 ; i < numHilos ; i++) {
			if (btnEvent.equals(btnHilos.get(i))) {
				Main.plotArray.get(i).setVisible(true);;
			}
		}
		
		if (btnEvent.equals(btnMostrarMejorResultado)) {
			Main.plotArray.get(Main.indiceDelMejorHilo).setVisible(true);;
		}
		
	}
	
	public void generarBotones () {
		lblHazClickPara.setVisible(true);
		for (JButton button : btnHilos) {
			button.setVisible(false);
		}
		for (int i = 0 ; i < numHilos ; i++) {
			btnHilos.get(i).setVisible(true);
		}
		frame.pack();
	}
}
