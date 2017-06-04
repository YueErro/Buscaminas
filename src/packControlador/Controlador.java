package packControlador;

import packVista.*;
import packModelo.Tablero;
import packModelo.packEstado.Desmarcada;

import java.awt.event.*;
import javax.swing.*;

import packExcepciones.ColumnaException;
import packExcepciones.FilaException;

public class Controlador extends MouseAdapter implements ActionListener
{
	@Override
	public void mouseClicked(MouseEvent pMe) 
	{			
		int i;
		int fila;
		int columna;
		
		if( !Tablero.obtenerTablero().finalDeJuego() )
		{
			JLabel referencia = ((JLabel)pMe.getSource());
							
			i = VentanaTablero.getInstance().buscarEtiqueta(referencia);
			fila = i / Tablero.obtenerTablero().nivel().getColumnas();
			columna = i % Tablero.obtenerTablero().nivel().getColumnas();
			
			if( pMe.getButton() == MouseEvent.BUTTON1 )
			{				
				Tablero.obtenerTablero().clickIzquierdo( fila , columna );
				Tablero.obtenerTablero().setChanged();
				Tablero.obtenerTablero().notifyObservers();
			}
			else if( pMe.getButton() == MouseEvent.BUTTON3 )
			{
				Tablero.obtenerTablero().clickDerecho( fila , columna );
				Tablero.obtenerTablero().setChanged();
				Tablero.obtenerTablero().notifyObservers();
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent pAe)
	{
		if( pAe.getActionCommand().equalsIgnoreCase("Nuevo juego") )
		{
			VentanaTablero.getInstance().setVisible(false);
			VentanaLogin.getInstance().setVisible(true);
			VentanaLogin.getInstance().borrarNombreJugador();
			VentanaTablero.getInstance().getLblCara().
								setIcon(new ImageIcon(VentanaTablero.class.
									getResource("/packImagenes/cara.gif")));
		}
		else if( pAe.getActionCommand().equalsIgnoreCase("Ranking") )
		{
			VentanaRanking vp = VentanaRanking.getInstance();
		
			vp.setModal(true);
			vp.setVisible(true);
		}
		else if( pAe.getActionCommand().equalsIgnoreCase("Cerrar") )	
		{
			VentanaTablero.getInstance().dispose();
			System.exit(0);
		}
		else if ( pAe.getActionCommand().equalsIgnoreCase("Mostrar minas"))
		{
			for( int fila = 0 ; fila < Tablero.obtenerTablero().nivel().getFilas() ; fila++ )
			{
				for( int col = 0 ; col < Tablero.obtenerTablero().nivel().getColumnas() ; col++)
				{
					try
					{
						if( Tablero.obtenerTablero().hayMina(fila,col) &&
							Tablero.obtenerTablero().consultarEstado(fila, col) instanceof Desmarcada )
							
							Tablero.obtenerTablero().clickDerecho(fila, col);
							
						else if( !Tablero.obtenerTablero().hayMina(fila,col) &&
								!(Tablero.obtenerTablero().consultarEstado(fila, col) instanceof Desmarcada) )
							
							Tablero.obtenerTablero().clickDerecho(fila, col);
							
					} 
					catch (FilaException e1) 
					{
						e1.printStackTrace();
					} 
					catch (ColumnaException e1) 
					{
						e1.printStackTrace();
					}
				}
			}
		}	
	}
}