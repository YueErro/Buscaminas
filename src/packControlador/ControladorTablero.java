package packControlador;

import java.awt.event.*;

import javax.swing.*;

import packVista.VentanaTablero;

public class ControladorTablero extends WindowAdapter
{
	public void windowClosing(WindowEvent pWe) 
	{
			VentanaTablero.getInstance().dispose();
			System.exit(0);
	}
}