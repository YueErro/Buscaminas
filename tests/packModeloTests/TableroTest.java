package packModeloTests;

import static org.junit.Assert.*;

import org.junit.*;

import packExcepciones.*;
import packModelo.*;
import packModelo.packEstado.*;

public class TableroTest 
{
	private Tablero t1;
		 	
	@Before
	public void setUp() throws Exception
	{
		t1 = Tablero.obtenerTablero();
	}

	@Test
	public void testObtenerTablero() 
	{
		assertNotNull( t1 );	
	}

	@Test
	public void testInicializarJuego() 
	{
		t1.inicializarJuego();
		assertFalse( t1.finalDeJuego() );
		assertFalse( t1.finalizacionCorrecta() );
		assertEquals( 0, t1.casillasAbiertas() );
	}

	@Test
	public void testClickDerecho() 
	{
		try
		{
			t1.clickDerecho(0, 0);
			assertEquals( Marcada.class, t1.consultarEstado(0, 0).getClass() );
			t1.clickDerecho(0, 0);
			assertEquals( Desmarcada.class, t1.consultarEstado(0, 0).getClass() );
		}
		catch (FilaException | ColumnaException e) 
		{
			fail("Parámetro no válido");
		}
	}

	@Test
	public void testClickIzquierdo() 
	{
		try
		{
			t1.clickIzquierdo(0, 0);
			assertTrue( t1.consultarEstado(0, 0).consultarEstado() );
			t1.clickIzquierdo(0, 0);
			assertTrue( t1.consultarEstado(0, 0).consultarEstado() );
		}
		catch (FilaException | ColumnaException e) 
		{
			fail("Parámetro no válido");
		}
	}

	@Test
	public void testEstanMarcadas() 
	{
		t1.clickDerecho(0, 0);
		t1.clickDerecho(1, 0);
		t1.clickDerecho(2, 0);
		t1.clickDerecho(3, 0);
		assertEquals( 4, t1.estanMarcadas() );
		t1.clickDerecho(0, 0);
		assertEquals( 3, t1.estanMarcadas() );
		t1.clickIzquierdo(0, 0);
		t1.clickIzquierdo(3, 0);
		assertEquals( 3, t1.estanMarcadas() );
	}

	@Test
	public void testFinalDeJuego() 
	{
		boolean mina = false;
		
		while( !mina )
		{
			for( int f = 0; f < t1.nivel().getFilas(); f++ )
			{
				for( int c = 0; c < t1.nivel().getColumnas(); c++ )
				{
					try 
					{
						if( t1.hayMina( f, c ) )
						{
							t1.clickIzquierdo(f, c);
							mina = true;
						}
					} 
					catch (FilaException | ColumnaException e) 
					{
						fail("Parámetro no válido");
					}
				}
			}
			assertTrue( t1.finalDeJuego() );
		}
	}

	@Test
	public void testFinalizacionCorrecta() 
	{
		assertFalse( t1.finalizacionCorrecta() );
		boolean mina = false;
		
		while( !mina )
		{
			for( int f = 0; f < t1.nivel().getFilas(); f++ )
			{
				for( int c = 0; c < t1.nivel().getColumnas(); c++ )
				{
					try 
					{
						if( t1.hayMina( f, c ) )
						{
							t1.clickIzquierdo(f, c);
							mina = true;
						}
					} 
					catch (FilaException | ColumnaException e) 
					{
						fail("Parámetro no válido");
					}
				}
			}
			assertFalse( t1.finalizacionCorrecta() );
		}
	}

	@Test
	public void testNivel() 
	{
		assertEquals( Nivel.NIVEL1, t1.nivel() );
	}

	@Test
	public void testSetNivel() 
	{
		t1.setNivel(Nivel.NIVEL1);
		assertEquals( Nivel.NIVEL1, t1.nivel() );
		
		t1.setNivel(Nivel.NIVEL2);
		assertEquals( Nivel.NIVEL2, t1.nivel() );
		
		t1.setNivel(Nivel.NIVEL3);
		assertEquals( Nivel.NIVEL3, t1.nivel() );
	}

	@Test
	public void testTiempo() 
	{
		t1.inicializarJuego();
		assertEquals( 0, t1.tiempo() );
	}

}