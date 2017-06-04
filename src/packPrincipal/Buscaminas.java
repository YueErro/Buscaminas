package packPrincipal;

import java.awt.EventQueue;

import packVista.VentanaTablero;

public class Buscaminas 
{
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try
				{					
					VentanaTablero.getInstance();
				} 
				catch (Exception pE) 
				{
					pE.printStackTrace();
				}
			}
		});
	}
}