package packModelo.packEstado;

public class Desmarcada implements Estado
{
	public Desmarcada() 
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