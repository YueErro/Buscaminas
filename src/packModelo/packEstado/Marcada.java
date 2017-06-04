package packModelo.packEstado;

public class Marcada implements Estado 
{
	public Marcada()
	{
		super();
	}
	
	@Override
	public boolean consultarEstado() 
	{
		return true;
	}

	@Override
	public void cambiarEstado() 
	{
		
	}
}