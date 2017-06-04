package packModelo;

import packExcepciones.*;
import packModelo.packEstado.*;

public class Casilla 
{
	private int numMinasVecinas;
	private boolean mina;
	private Estado estado;
	
	public Casilla()
	{
		mina = true;
		estado = new Desmarcada();
	}
	
	public Casilla( int pNumVecinos ) throws NumVecinosException
	{
		if( pNumVecinos < 0 || pNumVecinos > 8 )
			throw new NumVecinosException();
		
		else 
		{
			numMinasVecinas = pNumVecinos;
			mina = false;
			estado = new Desmarcada();
		}
	}

	public int numMinasVecinas()
	{
		return numMinasVecinas;
	}
	
	public boolean hayMina()
	{
		return mina;
	}
	
	public Estado consultarEstado()
	{
		return estado;
	}
			
	public void cambiarEstado( boolean pClicIzq )
	{
		if( pClicIzq && estado instanceof Desmarcada )
		{
			estado = new Abierta();		
			estado.cambiarEstado();
		}
		else if( !pClicIzq && estado instanceof Desmarcada )
		{
			estado = new Marcada();
		}
		else if( !pClicIzq && estado instanceof Marcada )
		{
			estado = new Desmarcada();
		}
	}
}