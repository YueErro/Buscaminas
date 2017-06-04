package packModelo;

import java.util.Comparator;

public class ComparadorJugadores implements Comparator<Jugador>
{
	@Override
	public int compare( Jugador pJ1, Jugador pJ2 ) 
	{
		return pJ1.getTiempo() - pJ2.getTiempo();
	}	
}