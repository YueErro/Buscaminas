package packModelo.packEstado;

public class Abierta implements Estado
{
	private boolean estaAbierta;
	
	public Abierta()
	{
		estaAbierta = false;
	}
	
	@Override
	public boolean consultarEstado() 
	{
		return estaAbierta;
	}

	@Override
	public void cambiarEstado()
	{
		estaAbierta = true;
	}
}