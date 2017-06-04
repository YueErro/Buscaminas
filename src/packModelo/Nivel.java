package packModelo;

public enum Nivel 
{
	NIVEL1(7, 10, 10, 182, 222),
	NIVEL2(10, 15, 30, 262, 271),
	NIVEL3(12, 25, 75, 422, 302);
	
	private int filas;
	private int columnas;
	private int minas;
	private int altura;
	private int anchura;
	
	private Nivel(int pFilas , int pColumnas , int pMinas, 
								int pAltura, int pAnchura )
	{
		filas = pFilas;
		columnas = pColumnas;
		minas = pMinas;
		altura = pAltura;
		anchura = pAnchura;
	}
	
	public int getFilas()
	{
		return filas;
	}
	
	public int getColumnas()
	{
		return columnas;
	}
	
	public int getMinas()
	{
		return minas;
	}

	public int getAltura() 
	{
		return altura;
	}

	public int getAnchura() 
	{
		return anchura;
	}
}