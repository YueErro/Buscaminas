package packModelo;

import java.util.Random;

import packExcepciones.*;
import packModelo.packEstado.*;

public class MatrizCasillas extends MatrizCasillasBuilder
{
	private Casilla[][] matriz;
	
	public MatrizCasillas( int pFilas, int pColumnas, int pMinas ) throws IllegalArgumentException
	{
		if( (pFilas != 7 && pColumnas != 10 && pMinas != 10 ) && 
			(pFilas != 10 && pColumnas != 15 && pMinas != 30 ) &&
		    (pFilas != 12 && pColumnas != 25 && pMinas != 75 )	) 
		{
			throw new IllegalArgumentException();
		}
		else 
		{
			matriz = new Casilla[pFilas][pColumnas];
			crearMinas( pFilas, pColumnas, pMinas );
			crearNumeros( pFilas, pColumnas, pMinas );
		}
	}

	public void crearMinas( int pFilas, int pColumnas, int pMinas ) throws IllegalArgumentException
	{
		if( (pFilas != 7 && pColumnas != 10 && pMinas != 10 ) &&
			(pFilas != 10 && pColumnas != 15 && pMinas != 30 ) &&
		    (pFilas != 12 && pColumnas != 25 && pMinas != 75 )	) 
		{
			throw new IllegalArgumentException();
		}
		else
		{
			int contMinas = 0;
			int f;
			int c;
					
			while( contMinas < pMinas )
			{
				f = new Random().nextInt(pFilas);		
				c = new Random().nextInt(pColumnas);
						
				if( matriz[f][c] == null )
				{
					matriz[f][c] = new Casilla();
					contMinas++;
				}
			}
		}
	}
	
	public void crearNumeros( int pFilas, int pColumnas, int pMinas ) throws IllegalArgumentException
	{
		if( (pFilas != 7 && pColumnas != 10 && pMinas != 10 ) &&
			(pFilas != 10 && pColumnas != 15 && pMinas != 30 ) &&
			(pFilas != 12 && pColumnas != 25 && pMinas != 75 )	) 
		{
			throw new IllegalArgumentException();
		}
		else
		{
			for(int i = 0 ; i < pFilas ; i++ )
			{
				for(int j = 0 ; j < pColumnas ; j++ )
				{
					if( matriz[i][j] == null )
					{
						try
						{
							int mv = minasVecinas(i, j);
							matriz[i][j] = new Casilla( mv );
						}
						catch( NumVecinosException pNVE )
						{
							pNVE.printStackTrace();
						}
						catch( FilaException pFE )
						{
							pFE.printStackTrace();
						}
						catch( ColumnaException pFE )
						{
							pFE.printStackTrace();
						}
					}
				}
			}
		}

	}
	
	public Estado consultarEstado( int pFila , int pColumna ) throws FilaException, ColumnaException
	{
		if ( pFila < 0 || pFila > matriz.length )
            throw new FilaException();
		
        if ( pColumna < 0 || pColumna > matriz[pFila].length )
            throw new ColumnaException();
   
    	return matriz[pFila][pColumna].consultarEstado();
	}
	
	public void cambiarEstado(int pFila , int pColumna, boolean pClicIzq ) throws FilaException, ColumnaException
	{
		if ( pFila < 0 || pFila > matriz.length )
            throw new FilaException();
		
        if ( pColumna < 0 || pColumna > matriz[pFila].length )
            throw new ColumnaException();
       
        matriz[pFila][pColumna].cambiarEstado( pClicIzq );
	}
	
	public boolean hayMina( int pFila, int pColumna ) throws FilaException, ColumnaException
	{
		if( pFila < 0 || pFila >= matriz.length )
			throw new FilaException(); 
		
		if( pColumna < 0 || pColumna >= matriz[pFila].length )
			throw new ColumnaException();
		
		return matriz[pFila][pColumna].hayMina();
	}
	
	public int casillasAbiertas()
	{
		int abiertas = 0;
		int filas = matriz.length;
		int columnas = matriz[0].length;
		
		for( int f = 0; f < filas; f++ )
		{
			for( int c = 0; c < columnas; c++ )
			{
				if( matriz[f][c].consultarEstado() instanceof Abierta &&
						( (Abierta) matriz[f][c].consultarEstado()).
												consultarEstado() == true ) 
				
					abiertas++;
			}
		}
		return abiertas;
	}
	
	public int minasVecinas( int pFila, int pColumna ) throws FilaException, ColumnaException
	{
		if ( pFila < 0 || pFila > matriz.length )
            throw new FilaException();
		
        if ( pColumna < 0 || pColumna > matriz[0].length )
            throw new ColumnaException();
 
		int minasVecinas = 0;
		int filas = matriz.length;
		int cols = matriz[0].length;
			
		for( int f = pFila-1 ; f <= pFila + 1 ; f++ )
		{
			for( int c = pColumna-1 ; c <= pColumna + 1 ; c++ )
			{
				if( f >= 0 && f < filas && 
					c >= 0 && c < cols && 
					matriz[f][c] != null && 
					matriz[f][c].hayMina() )
				{
					minasVecinas++;
				}
			}
		}
		return minasVecinas;
	}
}