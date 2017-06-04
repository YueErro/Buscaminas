package packModelo;

public class Jugador 
{
	private String nombre;
	private int tiempo;
	
	public Jugador( String pNombre, int pTiempo ) 
	{
		if ( pNombre == null )
			throw new IllegalArgumentException();
		
		else if ( pNombre.equals("") )
			throw new IllegalArgumentException();

		else if ( pTiempo < 0 )
			throw new IllegalArgumentException();
		
		else
		{
			nombre = pNombre;
			tiempo = pTiempo;
		}
	}

	public String getNombre() 
	{
		return nombre;
	}
	
	public int getTiempo() 
	{
		return tiempo;
	}
}