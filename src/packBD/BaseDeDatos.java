package packBD;

import packModelo.*;

import java.sql.*;

import javax.swing.JOptionPane;

public class BaseDeDatos
{
	private Connection con;
	private Statement st;
	private String url;
	
	private static BaseDeDatos mBaseDeDatos;
	
	public static BaseDeDatos getInstance() 
	{
		if ( mBaseDeDatos == null )
		{
			mBaseDeDatos = new BaseDeDatos();
		}
		return mBaseDeDatos;
	}
	
	private BaseDeDatos()
    {
       url = "Ranking.sqlite";
    }
	
    private void conectar()
    {
    	try 
    	{
			Class.forName("org.sqlite.JDBC");
		}
		catch( ClassNotFoundException pCNFE ) 
    	{
			System.out.println( pCNFE.getMessage() );
		}
		try 
		{			
			con = DriverManager.getConnection("jdbc:sqlite:" + url);	
			st = con.createStatement();		
		}
		catch( SQLException pEx ) 
		{
			System.out.println( pEx.getMessage() );
			System.out.println( "Estado:" + pEx.getSQLState() );
		}	
	}    
    
    private void desconectar()
    {
    	try
        {    
             st.close();  
             con.close();  
        }
        catch( Exception e )
        {                 
        	e.printStackTrace();  
        }  
    }
    
    private boolean insertar( String pSql )
    {
        boolean valor = true;
        conectar();
        
        try 
        {
            st.executeUpdate(pSql);
        } 
        catch( SQLException pEx ) 
        {
        	valor = false;
            JOptionPane.showMessageDialog( null, pEx.getMessage() );
        }      
        finally
        {  
            desconectar();
        }
        return valor;
    }
   
    private ResultSet consultar( String pSql )
    {
        conectar();
        ResultSet resultado = null;
        
        try 
        {
            resultado = st.executeQuery(pSql);
        } 
        catch( SQLException e ) 
        {
        	System.out.println( "Mensaje:" + e.getMessage() );
            System.out.println( "Estado:" + e.getSQLState() );
            System.out.println( "Codigo del error:" + e.getErrorCode() );
            
            JOptionPane.showMessageDialog( null, "" + e.getMessage() );
        }    
        return resultado;
    }
    
    public ArrayListOrdenado<Jugador> cargarDatos( String pTabla )
    {
    	String s = "SELECT * FROM "+ pTabla +"";
    	ResultSet rs = consultar( s );
    
    	ArrayListOrdenado<Jugador> alo = 
    					new ArrayListOrdenado<>(new ComparadorJugadores());
    	
    	try
    	{ 		
    		while( rs.next() )
	    	{
	    		String nombre = rs.getString("Nombre");
	    		int tiempo = rs.getInt("Tiempo");
	    		
	    		alo.add( new Jugador( nombre, tiempo ) );
	    	}    		    			
    	}
    	catch( SQLException pEx )
    	{
    		pEx.printStackTrace();
    	}   	
    	finally
    	{
    		desconectar();
    	}
    	return alo;
    }
    
    
    public void guardarDatos( String pNombre , int pTiempo, String pTabla )
    {

    	try
    	{
			String s = "SELECT Nombre, Tiempo FROM "+ pTabla +" WHERE Nombre = " + "'" + pNombre + "'";
			ResultSet rs = consultar(s);
			
			if( rs.next() == false )
			{
				String insto = "INSERT INTO "+ pTabla +" ( 'Nombre', 'Tiempo' ) VALUES ( '" + pNombre + "', " + pTiempo + " )";
				insertar( insto );
			}
			else
			{
				int tiempo = rs.getInt("Tiempo");
				
				if( pTiempo < tiempo )
				{
					String upd = "UPDATE "+ pTabla +" SET Tiempo =" + pTiempo + " WHERE Nombre = " + "'" + pNombre + "'";
					st.executeUpdate( upd );
				}
			}
    	}
    	catch( SQLException pEx )
    	{
    		pEx.printStackTrace();
    	} 	
    }  
}