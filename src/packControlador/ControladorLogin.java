package packControlador;

import java.awt.event.*;

import packModelo.*;
import packVista.*;

public class ControladorLogin implements ActionListener
{
	@Override
	public void actionPerformed( ActionEvent pAe ) 
	{	
		Nivel nivel = VentanaLogin.getInstance().getNivelSeleccionado();
		Tablero.obtenerTablero().setNivel( nivel );
		Tablero.obtenerTablero().inicializarJuego();
		VentanaLogin.getInstance().setVisible( false );
		VentanaTablero.getInstance().setVisible( true );
	}	
}