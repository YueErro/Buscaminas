package packModelo;

import java.util.*;

import packExcepciones.*;
import packModelo.packEstado.*;
import packVista.*;

public class Tablero extends Observable
{
	private MatrizCasillas matrizCasillas;
	private boolean finalDeJuego;
	private boolean finalizacionCorrecta;
	private Nivel nivel;
	private int tiempo;
	private Timer timer;
	TimerTask timerTask;
	
	private static Tablero mTablero;
	
	private Tablero()
	{
		finalDeJuego = false;
		finalizacionCorrecta = false;
		nivel = Nivel.NIVEL1;
		tiempo = 0;
		timerTask = new TimerTask()
		{
			@Override
			public void run() 
			{
				actualizarSegundero();
			}		
		};;
	}
	
	public static Tablero obtenerTablero()
	{
		if( mTablero == null )
			mTablero = new Tablero();
		
		return mTablero;
	}

	private void actualizarSegundero() 
	{
		if( !finalDeJuego )
		{
			tiempo++;
			if ( tiempo == 1000 )
			{
				tiempo = 0;
			}
			setChanged();
			notifyObservers();
		}
	}
	
	public void inicializarJuego()
	{
		int filas = nivel.getFilas();
		int columnas = nivel.getColumnas();
		int minas = nivel.getMinas();
	
		matrizCasillas = new MatrizCasillas( filas, columnas , minas );
		
		finalDeJuego = false;
		finalizacionCorrecta = false;
		tiempo = 0;
		
		if( timer == null )
		{
			timer = new Timer();
			timer.scheduleAtFixedRate(timerTask, 1000, 1000);
		}
		
		setChanged();
		notifyObservers();
	}

	public void clickDerecho( int pFila, int pColumna )
	{
		try 
		{
			if( matrizCasillas.consultarEstado( pFila, pColumna ) 
												instanceof Desmarcada && 
				estanMarcadas() < nivel.getMinas() )
				
				matrizCasillas.cambiarEstado( pFila, pColumna, false );
	
			else if(  matrizCasillas.consultarEstado( pFila, pColumna ) 
													instanceof Marcada )
			
				matrizCasillas.cambiarEstado( pFila, pColumna, false );
		}
		catch( FilaException pFE ) 
		{
			pFE.printStackTrace();
		} 
		catch( ColumnaException pCE ) 
		{
			pCE.printStackTrace();
		}
		
		setChanged();
		notifyObservers();		
	}
	
	public void clickIzquierdo( int pFila, int pColumna )
	{
		try 
		{
			if( matrizCasillas.consultarEstado( pFila, pColumna ) 
												instanceof Desmarcada )
			{
				matrizCasillas.cambiarEstado( pFila, pColumna, true );
				
				if( matrizCasillas.hayMina(pFila, pColumna) )
				{					
					finalDeJuego = true;
					finalizacionCorrecta = false;

					setChanged();
					notifyObservers();
				}
				else
				{				
					if( matrizCasillas.minasVecinas(pFila, pColumna) == 0 )
						descubrirCasillas( pFila, pColumna );
					
					else if( matrizCasillas.casillasAbiertas() ==
							( nivel.getFilas() * nivel.getColumnas() - 
													nivel.getMinas() ) )
					{
						finalDeJuego = true;
						finalizacionCorrecta = true;
					}
				}
			}
			
			if( finalDeJuego )
			{
				VentanaRanking vr = VentanaRanking.getInstance();
				vr.setVisible(true);
			}
		} 
		catch( FilaException pFE )
		{
			pFE.printStackTrace();
		} 
		catch( ColumnaException pCE) 
		{
			pCE.printStackTrace();
		}	
	}
	
	private void descubrirCasillas( int pFila, int pColumna )
	{
		try 
		{
			int fInicio = Math.max( 0, pFila - 1 );
			int fFin = Math.min( nivel.getFilas() - 1, pFila + 1 );
			int cInicio = Math.max( 0, pColumna - 1 );
			int cFin = Math.min( nivel.getColumnas() - 1, pColumna + 1 );
			
			for( int f = fInicio; f <= fFin; f++ )
	        {
	            for( int c = cInicio; c <= cFin; c++ )
	            {
	            	if( matrizCasillas.consultarEstado(f, c) 
	            								instanceof Desmarcada &&
	            		!matrizCasillas.hayMina(f, c)  )
	            		
	            		clickIzquierdo(f, c);
	            }
	        }
		}
		catch( FilaException pFE )
		{
			pFE.printStackTrace();
		} 
		catch( ColumnaException pCE) 
		{
			pCE.printStackTrace();
		}
	}
		
	public Estado consultarEstado(int pFila, int pColumna ) throws FilaException, ColumnaException
	{
		if( pFila < 0 || pFila > nivel.getFilas() )
			throw new FilaException();
		
		if( pColumna < 0 && pColumna > nivel.getColumnas() )
			throw new ColumnaException();

		return matrizCasillas.consultarEstado( pFila, pColumna );
	}
	
	public boolean hayMina( int pFila, int pColumna ) throws FilaException, ColumnaException
	{
		if( pFila < 0 || pFila > nivel.getFilas() )
			throw new FilaException();
		
		if( pColumna < 0 && pColumna > nivel.getColumnas() )
			throw new ColumnaException();
			
		return matrizCasillas.hayMina( pFila, pColumna );
	}
	
	public int casillasAbiertas()
	{
		return matrizCasillas.casillasAbiertas();
	}
	
	public int minasVecinas( int pFila, int pColumna )
	{					
		int ma = -1; 
		
		try
		{
			ma = matrizCasillas.minasVecinas(pFila, pColumna);
		}
		catch( FilaException pFE )
		{
			pFE.printStackTrace();
		} 
		catch( ColumnaException pCE) 
		{
			pCE.printStackTrace();
		}
		return ma;
	}
	
	public int estanMarcadas() 
	{
		int minasMarcadas = 0;
		
		for( int f = 0 ; f < nivel.getFilas(); f++ )
		{
			for( int c = 0 ; c < nivel.getColumnas(); c++ )
			{
				try 
				{
					if( matrizCasillas.consultarEstado(f, c) 
													instanceof Marcada )
						
						minasMarcadas++;
				} 
				catch( FilaException pFE )
				{
					pFE.printStackTrace();
				} 
				catch( ColumnaException pCE) 
				{
					pCE.printStackTrace();
				}
			}
		}
		return minasMarcadas;
	}
	
	@Override
	public void setChanged()
	{
		super.setChanged();
	}
	
	public boolean finalDeJuego()
	{
		return finalDeJuego;
	}
	
	public boolean finalizacionCorrecta()
	{
		return finalizacionCorrecta;
	}
	
	public Nivel nivel()
	{
		return nivel;
	}
	
	public void setNivel( Nivel pNivel ) throws NullPointerException
	{
		if( pNivel == null )
			throw new NullPointerException();
		
		nivel = pNivel;
	}
	
	public int tiempo()
	{
		return tiempo;
	}
}