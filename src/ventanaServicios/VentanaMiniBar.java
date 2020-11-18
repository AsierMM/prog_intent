package ventanaServicios;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import hotel.Cliente;

public class VentanaMiniBar extends JFrame{

	JComboBox<String> comboMiniBar;
	JLabel miniBar;
	JButton continuar;
	
	public VentanaMiniBar(Cliente cliente, int pago) {
		setLayout(new GridLayout(3, 1));
		
		comboMiniBar = new JComboBox<String>();
		
		miniBar = new JLabel("MINI BAR: ");
		continuar = new JButton("CONTINUAR");
		
		comboMiniBar.addItem("VODKA ---> 30�");
		comboMiniBar.addItem("GINEBRA ---> 30�");
		comboMiniBar.addItem("WHISKEY ---> 50�");
		comboMiniBar.addItem("TEQUILA ---> 45�");
		comboMiniBar.addItem("RON ---> 30�");
		comboMiniBar.addItem("ELECCION DE DOS (DIFERENTES) ---> 90�");
		comboMiniBar.addItem("ELECCION DE TRES (DIFERENTES) ---> 140�");
		comboMiniBar.addItem("TODOS ---> 180�");
		
		continuar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		add(miniBar);
		add(comboMiniBar);
		add(continuar);
					
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Servicio Mini Bar");
		setSize(800, 200);
		setVisible(true);

	}
}
