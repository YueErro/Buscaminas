package packModeloTests;

import static org.junit.Assert.*;

import org.junit.*;

import packExcepciones.*;
import packModelo.*;
import packModelo.packEstado.*;

public class CasillaTest 
{
	private Casilla casillaMina;
	private Casilla casilla0;
	private Casilla casilla1;
	private Casilla casilla2;
	private Casilla casilla3;
	private Casilla casilla4;
	private Casilla casilla5;
	private Casilla casilla6;
	private Casilla casilla7;
	private Casilla casilla8;
	
	@Before
	public void setUp() throws Exception
	{
		casillaMina = new Casilla();
		casilla0 = new Casilla(0);
		casilla1 = new Casilla(1);
		casilla2 = new Casilla(2);
		casilla3 = new Casilla(3);
		casilla4 = new Casilla(4);
		casilla5 = new Casilla(5);
		casilla6 = new Casilla(6);
		casilla7 = new Casilla(7);
		casilla8 = new Casilla(8);
	}

	@Test
	public void testCasilla()
	{
		assertNotNull(casillaMina);
		assertTrue(casillaMina.hayMina());
	}

	@Test
	public void testCasillaInt()
	{
		assertNotNull(casilla0);
		assertNotNull(casilla1);
		assertNotNull(casilla2);
		assertNotNull(casilla3);
		assertNotNull(casilla4);
		assertNotNull(casilla5);
		assertNotNull(casilla6);
		assertNotNull(casilla7);
		assertNotNull(casilla8);
	
		try 
		{
			new Casilla(-1);
			
			fail("Debe lanzarse la excepcion NumVecinosException al pasar un valor negativo al constructor de Casilla");
		
		} 
		catch( NumVecinosException pNVE ) 
		{
			System.out.println("Parametro al constructor no valido");
		
		}
		catch( Exception pE )
		{
			fail("El constructor Casilla(int) lanza una excepcion inadecuada");
		}
		try 
		{
			new Casilla(9);
			
			fail("Debe lanzarse la excepcion NumVecinosException cuando se pasa un valor mayor que 8 al contructor de casilla");
		
		} 
		catch( NumVecinosException pNVE ) 
		{
			pNVE.printStackTrace();
		
		} 
		catch( Exception pE )
		{
			fail("El constructor Casilla(int) lanza una excepcion no adecuada");
		}
	}

	@Test
	public void testNumMinasVecinas()
	{
		assertEquals( 0, casilla0.numMinasVecinas() );
		assertEquals( 1, casilla1.numMinasVecinas() );
		assertEquals( 2, casilla2.numMinasVecinas() );
		assertEquals( 3, casilla3.numMinasVecinas() );
		assertEquals( 4, casilla4.numMinasVecinas() );
		assertEquals( 5, casilla5.numMinasVecinas() );
		assertEquals( 6, casilla6.numMinasVecinas() );
		assertEquals( 7, casilla7.numMinasVecinas() );
		assertEquals( 8, casilla8.numMinasVecinas() );
	}
	
	@Test
	public void testhayMina()
	{
		assertTrue( casillaMina.hayMina() );
		assertFalse( casilla0.hayMina() );
		assertFalse( casilla1.hayMina() );
		assertFalse( casilla2.hayMina() );
		assertFalse( casilla3.hayMina() );
		assertFalse( casilla4.hayMina() );
		assertFalse( casilla5.hayMina() );
		assertFalse( casilla6.hayMina() );
		assertFalse( casilla7.hayMina() );
		assertFalse( casilla8.hayMina() );
	}
	
	@Test
	public void testConsultarEstado()
	{
		assertEquals( Desmarcada.class, casillaMina.consultarEstado().getClass() );
		assertEquals( Desmarcada.class, casilla0.consultarEstado().getClass() );
		assertEquals( Desmarcada.class, casilla1.consultarEstado().getClass() );
		assertEquals( Desmarcada.class, casilla2.consultarEstado().getClass() );
		assertEquals( Desmarcada.class, casilla3.consultarEstado().getClass() );
		assertEquals( Desmarcada.class, casilla4.consultarEstado().getClass() );
		assertEquals( Desmarcada.class, casilla5.consultarEstado().getClass() );
		assertEquals( Desmarcada.class, casilla6.consultarEstado().getClass() );
		assertEquals( Desmarcada.class, casilla7.consultarEstado().getClass() );
		assertEquals( Desmarcada.class, casilla8.consultarEstado().getClass() );
		
	}
	
	@Test
	public void testCambiarEstado() 
	{		
		casilla0.cambiarEstado(true);
		assertEquals( Abierta.class, casilla0.consultarEstado().getClass() );
		
		casilla1.cambiarEstado(false);
		assertEquals( Marcada.class, casilla1.consultarEstado().getClass() );
		
		casilla2.cambiarEstado(false);
		casilla2.cambiarEstado(false);
		assertEquals( Desmarcada.class, casilla2.consultarEstado().getClass() );
		
		casilla3.cambiarEstado(false);
		casilla3.cambiarEstado(true);
		casilla3.cambiarEstado(false);
		assertEquals( Desmarcada.class, casilla3.consultarEstado().getClass() );
		
		casilla4.cambiarEstado(false);
		casilla4.cambiarEstado(true);
		assertEquals( Marcada.class, casilla4.consultarEstado().getClass() );
		
	}
}
