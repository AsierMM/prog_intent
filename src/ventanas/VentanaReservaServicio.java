package ventanas;
import javax.swing.*;

import com.toedter.calendar.JCalendar;

import hotel.BD;
import hotel.BDException;
import hotel.Cliente;
import ventanaServicios.VentanaReservaPista;
import ventanaServicios.VentanaReservaReunion;
import ventanaServicios.VentanaReservaSpa;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class VentanaReservaServicio extends JFrame{
	
	BD bd;
	
	JCalendar calendario;
	JTextField fecha;
	JButton fechaSeleccion;
	Date d1;
	Date d2;
	Date hoy;
	String fechaRegistro;
	
	public VentanaReservaServicio(Cliente cliente, int precio, String tipo) {			
		Cliente nuevo = cliente;
		bd = new BD();
		try {
			bd.connect();
		} catch (BDException e2) {
			e2.printStackTrace();
		}
		
		if(tipo.equals("MASAJE FACIAL") || tipo.equals("MASAJE CORPORAL") || tipo.equals("MASAJE TOTAL") || tipo.equals("JACUZZI") || tipo.equals("SALES MINERALES") || tipo.equals("MASAJE PIEDRAS") || tipo.equals("TRATAMIENTO EST�TICO")) {
			ArrayList<String> spa = new ArrayList<String>();
			spa = cliente.getSpa();
			spa.add(tipo);
			cliente.setSpa(spa);
        }else if(tipo.equals("SALA CONVENCION") || tipo.equals("SALA JUNTA") || tipo.equals("SALA PETIT COMIT�") || tipo.equals("SALA CONVENCION EQUIPADA") || tipo.equals("SALA JUNTA EQUIPADA") || tipo.equals("SALA PETIT COMIT� EQUIPADA")) {
        	ArrayList<String> salaReunion = new ArrayList<String>();
    		salaReunion = cliente.getSalaReunion();
    		salaReunion.add(tipo);
    		cliente.setSalaReunion(salaReunion);
        }else if(tipo.equals("VODKA") || tipo.equals("GINEBRA") || tipo.equals("WHISKEY") || tipo.equals("TEQUILA") || tipo.equals("RON") || tipo.equals("ELECCION DE DOS") || tipo.equals("ELECCION DE TRES") || tipo.equals("TODOS")) {
        	ArrayList<String> miniBar = new ArrayList<String>();
    		miniBar = cliente.getMiniBar();
    		miniBar.add(tipo);
    		cliente.setMiniBar(miniBar);
        }else if(tipo.equals("MCDONALDS") || tipo.equals("BURGER KING") || tipo.equals("FOSTERS HOLLYWOOD") || tipo.equals("POMODORO") || tipo.equals("FOODOO") || tipo.equals("DONGA") || tipo.equals("MENU DEL DIA HOTEL") || tipo.equals("BUFFET HOTEL")) {
        	ArrayList<String> comida = new ArrayList<String>();
    		comida = cliente.getComida();
    		comida.add(tipo);
    		cliente.setComida(comida);
        }else{
        	ArrayList<String> deporte = new ArrayList<String>();
    		deporte = cliente.getDeporte();
    		deporte.add(tipo);
    		cliente.setDeporte(deporte);
        }
		
		HashMap<String, Integer> hashmap = new HashMap<String, Integer>();
		hashmap = cliente.getHashmap();
		hashmap.put(tipo, precio);
			
		nuevo.setHashmap(hashmap);
		
		setLayout(new GridLayout(2, 1));
		
		calendario = new JCalendar();
		fecha = new JTextField(30);
		fechaSeleccion = new JButton("Fecha eleccion");
		
		ArrayList<String> nuevosDatos = new ArrayList<String>();
		String linea = null;
		String[] campos = null;
		String fechaInicio = null;
		String fechaFin = null;
    	
    	try {
			Scanner sc1 = new Scanner(new FileInputStream("fechas"));
			
			while(sc1.hasNext()) {
				linea = sc1.nextLine();
				campos = linea.split(";");
				nuevosDatos.add(linea);
				fechaInicio = campos[0];
				fechaFin = campos[1];
			}
			
		}catch(FileNotFoundException e1) {
			System.err.println("ERROR");
		}finally{
			//borrar fichero
		}
    	
    	Date fi = null;
		try {
			fi = new SimpleDateFormat("yyyy-MM-dd").parse(fechaInicio);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
    	Date ff = null;
		try {
			ff = new SimpleDateFormat("yyyy-MM-dd").parse(fechaFin);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		//IMPEDIMOS QUE SE PUEDAN HACER RESERVAS ANTERIORES AL DIA DE HOY
		hoy = calendario.getDate();
		calendario.setSelectableDateRange(fi, ff);		
		//A CONTINUACION SELECCIONAS OTRA FECHA PARA SELECCIONAR EL DIA DE SALIDA DEL HOTEL
		fechaSeleccion.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String year = Integer.toString(calendario.getCalendar().get(java.util.Calendar.YEAR));
		    	String mes = Integer.toString(calendario.getCalendar().get(java.util.Calendar.MONTH) + 1);
		    	String dia = Integer.toString(calendario.getCalendar().get(java.util.Calendar.DATE));
		    	fecha.setText(year + "-" + mes + "-" + dia);
		    	d1 = calendario.getDate();

		    	if (Integer.parseInt(mes) < 10 && Integer.parseInt(dia) < 10) {
		    		fechaRegistro = year + "-0" + mes + "-0" + dia;
		    	 } else if (Integer.parseInt(mes) < 10 && Integer.parseInt(dia) >= 10) {
		    		 fechaRegistro = year + "-0" + mes + "-" + dia;
		    	 } else if (Integer.parseInt(mes) >= 10 && Integer.parseInt(dia) < 10) {
		    		 fechaRegistro = year + "-" + mes + "-0" + dia;
		    	 } else {
		    		 fechaRegistro = year + "-" + mes + "-" + dia;
		    	 }
		    	
		    	//CREAMOS LA RESTRICCION DE NO PODER VOLVER A ESCOGER LA FECHA INICIO PARA LA FECHA FINAL
		    	int minYear = Integer.parseInt(year);
		    	int minMes = Integer.parseInt(mes);
		    	int minDia = Integer.parseInt(dia);
		    	
		    	//SE RESTA 1900 PORQUE LA LIBRER�A EMPIEZA A CONTAR DESDE 1900
		    	//SE RESTA 1 AL MES PORQUE EMPIEZA A CONTAR DESDE 0 Y ANTES LE HEMOS SUMADO 1 PARA ESCRIBIR BIEN LA FECHA
		    	Date minNoche = new Date(Date.UTC(minYear-1900, minMes-1, minDia +1, 0, 0, 0));
		    	
		    	if (tipo == "CLASE PADDLE" || tipo == "CLASE NATACION" || tipo == "CLASE FUTBOL-SALA" || tipo == "CLASE BALONCESTO") {
					bd.eleccionClaseDeporte(cliente, fechaRegistro, tipo);
					VentanaContinuacion vc = new VentanaContinuacion(cliente);
					dispose();
		    	
				} else if (tipo.contains("PADDLE") == true || tipo.contains("NATACION") == true  || tipo.contains("FUTBOL-SALA") == true || tipo.contains("BALONCESTO") == true) {
		    		VentanaReservaPista vrp = new VentanaReservaPista(cliente, fechaRegistro, tipo, precio);
		    		dispose();
				}else if(tipo.contains("MASAJE FACIAL") == true || tipo.contains("MASAJE CORPORAL") == true || tipo.contains("MASAJE TOTAL") == true || tipo.contains("JACUZZI") == true || tipo.contains("SALES MINERALES") == true || tipo.contains("MASAJE PIEDRAS") == true || tipo.contains("TRATAMIENTO EST�TICO") == true) {
					VentanaReservaSpa vrs = new VentanaReservaSpa(cliente, fechaRegistro, tipo, precio);
		    		dispose();
				}else{
					VentanaReservaReunion vrr = new VentanaReservaReunion(cliente, fechaRegistro, tipo, precio);
					dispose();
				}	    	
		    	
			}
		});
		
		JPanel p = new JPanel();
		
		JPanel pfecha = new JPanel();
		pfecha.add(new JLabel("Fecha Seleccionada"));
		pfecha.add(fecha);
		
		pfecha.add(fechaSeleccion);
		
		p.add(pfecha);
		p.add(calendario);
		add(p);
		
		// Cambiar color de letra del numero de d�a 
		calendario.setForeground(Color.BLACK);
		 
		// Cambiar color de letra del dia domingo
		calendario.setSundayForeground(Color.RED);
		 
		// Cambiar color de letra de semana
		calendario.setWeekdayForeground(Color.BLUE);
		
		// No mostramos la semana del a�o que es
		calendario.setWeekOfYearVisible(false);
		
		add(calendario);
					
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Calendario");
		setSize(800, 600);
		setVisible(true);
		
	}
	
}