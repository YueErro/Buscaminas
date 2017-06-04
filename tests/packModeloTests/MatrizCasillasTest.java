package packModeloTests;

import static org.junit.Assert.*;

import org.junit.*;

import packExcepciones.*;
import packModelo.*;

public class MatrizCasillasTest
{
	private MatrizCasillas mc1;
	private MatrizCasillas mc2;
	private MatrizCasillas mc3;
	
	@Before
	public void setUp() throws Exception
	{		
		mc1 = new MatrizCasillas( 7, 10, 10);
		mc2 = new MatrizCasillas( 10, 15, 30 );
		mc3 = new MatrizCasillas( 12, 25, 75 );
	}
	
	@Test
	public void testMatrizCasillas() 
	{
		assertNotNull(mc1);
		assertNotNull(mc2);
		assertNotNull(mc3);
		
		try 
		{
			new MatrizCasillas( -7, -10, -10 );
			fail("Debe lanzarse la excepcion IllegalArgumentException al pasar un valor negativo al constructor de Casilla");
		
		} 
		catch( IllegalArgumentException e ) 
		{
			System.out.println("Parametro al constructor no valido");
		}
		try 
		{
			new MatrizCasillas( 13, 16, 101 );
			fail("Debe lanzarse la excepcion IllegalArgumentException al pasar un valor incorrecto al constructor de Casilla");
		
		} 
		catch( IllegalArgumentException e ) 
		{
			System.out.println("Parametro al constructor no valido");
		}
	}
	
	@Test
	public void testCasillasAbiertas() 
	{	
		try
		{
			assertEquals( 0, mc1.casillasAbiertas() );
			mc1.cambiarEstado(0, 0, true);
			assertEquals( 1, mc1.casillasAbiertas() );	
			mc1.cambiarEstado(1, 1, false);
			assertEquals( 1, mc1.casillasAbiertas() );
		}
		catch (FilaException | ColumnaException e) 
		{
			e.printStackTrace();
		}
		try 
		{
			mc1.cambiarEstado(0, -1, true);
			fail("Debe lanzarse la excepcion por pasar un valor incorrecto por parametros");
		
		} 
		catch (FilaException e) 
		{
			System.out.println( "Fila incorrecta" );
		} 
		catch (ColumnaException e)
		{
			System.out.println( "Columna incorrecta" );
		}
	}
}
