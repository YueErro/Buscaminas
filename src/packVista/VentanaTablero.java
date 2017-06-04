package packVista;

import packBD.BaseDeDatos;
import packControlador.*;
import packExcepciones.*;
import packModelo.*;
import packModelo.packEstado.*;

import java.util.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

public class VentanaTablero extends JFrame implements Observer
{
	private Controlador controlador;
	private ControladorTablero controladorTablero;
	private ArrayList<JLabel> listaEtiquetas;

	private JMenuBar menuBar;
	private JMenu mnJuego;
	private JMenuItem mntmRanking;
	private JMenu mnAyuda;
	private JMenuItem mntmMM;
	
	private JPanel contentPane;
	private JPanel panelMarcador;
	private JPanel panelMinas;
	private JLabel lblMIzda;
	private JLabel lblMCentro;
	private JLabel lblMDecha;
	private JLabel lblCara;
	private JPanel panelTiempo;
	private JLabel lblTIzda;
	private JLabel lblTCentro;
	private JLabel lblTDecha;
	private JPanel panelCasillas;
		
	private static VentanaTablero mVentanaTablero;
	
	public static VentanaTablero getInstance()
	{
		if ( mVentanaTablero == null )
			mVentanaTablero = new VentanaTablero();
		
		return mVentanaTablero;
	}

	private VentanaTablero() 
	{
		setResizable(false);
		controlador = new Controlador();
		controladorTablero = new ControladorTablero();
		listaEtiquetas = new ArrayList<JLabel>();
		
		initialize();		
	}
	
	public int buscarEtiqueta( JLabel pReferencia )
	{
		int i = 0;
		boolean enc = false;
		while( !enc )
		{
			if ( pReferencia == listaEtiquetas.get(i) )
				enc = true;
			else
				i++;
		}
		return i;
	}

	private void initialize() 
	{
		addWindowListener( controladorTablero );
		setBackground(new Color(65, 105, 225));
		setTitle("Buscaminas");
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(VentanaTablero.class.
								getResource("/packImagenes/logo.png")));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		setJMenuBar(getMenuBar_1());
		contentPane = new JPanel();
		contentPane.setBackground(new Color(192, 192, 192));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 7));
		setContentPane(contentPane);
		contentPane.add(getPanelMarcador(), BorderLayout.NORTH);
		contentPane.add(getPanelCasillas(), BorderLayout.CENTER);
		
		VentanaLogin dialog = VentanaLogin.getInstance();
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		dialog.setVisible(true);
				
		Tablero.obtenerTablero().addObserver( this );
		centrarVentana();
	}
	
	@Override
	public void update(Observable pObs, Object pObj) 
	{
		Nivel n = Tablero.obtenerTablero().nivel();
		int filas = n.getFilas();
		int columnas = n.getColumnas();
		int altura = n.getAltura();
		int anchura = n.getAnchura();
		
		setSize( altura, anchura );
		dibujarMarcador();
		dibujarCasillas( filas, columnas );
		
		if( Tablero.obtenerTablero().finalizacionCorrecta() )
		{
			lblCara.setIcon(new ImageIcon(VentanaTablero.class.
						getResource("/packImagenes/caraGafas.gif")));
			
			String nombre = VentanaLogin.getInstance().getNombreJugador();
			int tiempo = Tablero.obtenerTablero().tiempo();
			
			String tabla;
		
			if( n.ordinal() == 0 )
				tabla = "Rprincipiante";
			
			else if ( n.ordinal() == 1 )
				tabla = "Rintermedio";
			
			else
				tabla = "Rexperto";

			BaseDeDatos.getInstance().guardarDatos( nombre, tiempo,
															tabla );
		}
	}

	private void dibujarCasillas(int pFilas , int pColumnas )
	{	
		try
		{
			panelCasillas.removeAll();
			listaEtiquetas.clear();
				
			JLabel etiqueta = null;
			int f;
			int c;
					
			for( int i = 0 ; i < pFilas * pColumnas ; i++ )
			{
				f = i / pColumnas;
				c = i % pColumnas;
				etiqueta = new JLabel("");
					
				if( Tablero.obtenerTablero().consultarEstado(f, c) 
													instanceof Desmarcada )
				{
					if( Tablero.obtenerTablero().finalDeJuego() &&  
						!Tablero.obtenerTablero().finalizacionCorrecta() && 
						Tablero.obtenerTablero().hayMina(f, c) )
					{
						etiqueta.setIcon(new ImageIcon(VentanaTablero
						.class.getResource("/packImagenes/mina.gif")));
					}
					else
					{
						etiqueta.setIcon(new ImageIcon(VentanaTablero
						.class.getResource("/packImagenes/casilla.gif")));
					}
				}
				else if( Tablero.obtenerTablero().consultarEstado(f, c) 
													instanceof Marcada )
				{
					if( Tablero.obtenerTablero().finalDeJuego() && 
						!Tablero.obtenerTablero().hayMina(f, c) )								
					{
						etiqueta.setIcon(new ImageIcon(VentanaTablero
						.class.getResource("/packImagenes/minaX.gif")));
					}
					else
					{
						etiqueta.setIcon(new ImageIcon(VentanaTablero
						.class.getResource("/packImagenes/bandera.gif")));
					}
				}
				else
				{
					if( ((Abierta)Tablero.obtenerTablero()
						.consultarEstado(f, c)).consultarEstado() == true )
					{
						if( Tablero.obtenerTablero().hayMina(f, c) )
						{
							lblCara.setIcon(new ImageIcon(VentanaTablero
							.class.getResource("/packImagenes/caraX.gif")));
							
							etiqueta.setIcon(new ImageIcon(VentanaTablero
							.class.getResource("/packImagenes/minaRojo.gif")));
						}
						else
						{
							int mv = Tablero.obtenerTablero().minasVecinas(f, c);
							
							etiqueta.setIcon( new ImageIcon( VentanaTablero
							.class.getResource("/packImagenes/n" + mv + ".gif") ) );
						}
					}
				}
				panelCasillas.add(etiqueta);			
				listaEtiquetas.add( etiqueta );
				etiqueta.addMouseListener( controlador );
			}				
			this.validate();
		}
		catch( FilaException pFE ) 
		{
			pFE.printStackTrace();
		} 
		catch( ColumnaException pCE ) 
		{
			pCE.printStackTrace();
		}
	}

	private void centrarVentana() 
	{	
		Dimension screenSize = 
				Toolkit.getDefaultToolkit().getScreenSize();

		Dimension windowSize =	this.getSize();

		if( windowSize.height > screenSize.height )
			windowSize.height = screenSize.height;
		
		if( windowSize.width > screenSize.width )
			windowSize.	width = screenSize.width;
		
		setLocation( ( ( screenSize.width - windowSize.width ) / 2 ) - 150,
					( ( screenSize.height - windowSize.height ) / 2 ) - 150 );		
	}
	
	private void dibujarMarcador()
	{
		int minas = Tablero.obtenerTablero().nivel().getMinas();
		
		minas = minas - Tablero.obtenerTablero().estanMarcadas();
		
		int m1 = 0;
					
		int m2 = Math.abs(minas) / 10;
		int mResto = Math.abs(minas)  % 10;
						
		int m3 = mResto;
		
		switch( m1 )
		{
			case 0: lblMIzda.setIcon(new ImageIcon(VentanaTablero.class.
							getResource("/packImagenes/d0.gif")));break;
			case 1: lblMIzda.setIcon(new ImageIcon(VentanaTablero.class.
							getResource("/packImagenes/d1.gif")));break;
			case 2: lblMIzda.setIcon(new ImageIcon(VentanaTablero.class.
							getResource("/packImagenes/d2.gif")));break;
			case 3: lblMIzda.setIcon(new ImageIcon(VentanaTablero.class.
							getResource("/packImagenes/d3.gif")));break;
			case 4: lblMIzda.setIcon(new ImageIcon(VentanaTablero.class.
							getResource("/packImagenes/d4.gif")));break;
			case 5: lblMIzda.setIcon(new ImageIcon(VentanaTablero.class.
							getResource("/packImagenes/d5.gif")));break;
			case 6: lblMIzda.setIcon(new ImageIcon(VentanaTablero.class.
							getResource("/packImagenes/d6.gif")));break;
			case 7: lblMIzda.setIcon(new ImageIcon(VentanaTablero.class.
							getResource("/packImagenes/d7.gif")));break;
			case 8: lblMIzda.setIcon(new ImageIcon(VentanaTablero.class.
							getResource("/packImagenes/d8.gif")));break;
			case 9: lblMIzda.setIcon(new ImageIcon(VentanaTablero.class.
							getResource("/packImagenes/d9.gif")));break;
		}
		switch( m2 )
		{
			case 0: lblMCentro.setIcon(new ImageIcon(VentanaTablero.class.
							getResource("/packImagenes/d0.gif")));break;
			case 1: lblMCentro.setIcon(new ImageIcon(VentanaTablero.class.
							getResource("/packImagenes/d1.gif")));break; 
			case 2: lblMCentro.setIcon(new ImageIcon(VentanaTablero.class.
							getResource("/packImagenes/d2.gif")));break;
			case 3: lblMCentro.setIcon(new ImageIcon(VentanaTablero.class.
							getResource("/packImagenes/d3.gif")));break;
			case 4: lblMCentro.setIcon(new ImageIcon(VentanaTablero.class.
							getResource("/packImagenes/d4.gif")));break;
			case 5: lblMCentro.setIcon(new ImageIcon(VentanaTablero.class.
							getResource("/packImagenes/d5.gif")));break;
			case 6: lblMCentro.setIcon(new ImageIcon(VentanaTablero.class.
							getResource("/packImagenes/d6.gif")));break;
			case 7: lblMCentro.setIcon(new ImageIcon(VentanaTablero.class.
							getResource("/packImagenes/d7.gif")));break;
			case 8: lblMCentro.setIcon(new ImageIcon(VentanaTablero.class.
							getResource("/packImagenes/d8.gif")));break;
			case 9: lblMCentro.setIcon(new ImageIcon(VentanaTablero.class.
							getResource("/packImagenes/d9.gif")));break;	
		}
		switch( m3 )
		{
			case 0: lblMDecha.setIcon(new ImageIcon(VentanaTablero.class.
					getResource("/packImagenes/d0.gif")));break;
			case 1: lblMDecha.setIcon(new ImageIcon(VentanaTablero.class.
					getResource("/packImagenes/d1.gif")));break; 
			case 2: lblMDecha.setIcon(new ImageIcon(VentanaTablero.class.
					getResource("/packImagenes/d2.gif")));break;
			case 3: lblMDecha.setIcon(new ImageIcon(VentanaTablero.class.
					getResource("/packImagenes/d3.gif")));break;
			case 4: lblMDecha.setIcon(new ImageIcon(VentanaTablero.class.
					getResource("/packImagenes/d4.gif")));break;
			case 5: lblMDecha.setIcon(new ImageIcon(VentanaTablero.class.
					getResource("/packImagenes/d5.gif")));break;
			case 6: lblMDecha.setIcon(new ImageIcon(VentanaTablero.class.
					getResource("/packImagenes/d6.gif")));break;
			case 7: lblMDecha.setIcon(new ImageIcon(VentanaTablero.class.
					getResource("/packImagenes/d7.gif")));break;
			case 8: lblMDecha.setIcon(new ImageIcon(VentanaTablero.class.
					getResource("/packImagenes/d8.gif")));break;
			case 9: lblMDecha.setIcon(new ImageIcon(VentanaTablero.class.
					getResource("/packImagenes/d9.gif")));break;	
		}
		
		int t1 = Tablero.obtenerTablero().tiempo() / 100;
		int tResto = Tablero.obtenerTablero().tiempo()  % 100;
		
		int t2 = tResto / 10;
		tResto = tResto % 10;
		
		int t3 = tResto;
				
		switch( t1 )
		{
			case 0: lblTIzda.setIcon(new ImageIcon(VentanaTablero.class.
							getResource("/packImagenes/d0.gif")));break;
			case 1: lblTIzda.setIcon(new ImageIcon(VentanaTablero.class.
							getResource("/packImagenes/d1.gif")));break; 
			case 2: lblTIzda.setIcon(new ImageIcon(VentanaTablero.class.
							getResource("/packImagenes/d2.gif")));break;
			case 3: lblTIzda.setIcon(new ImageIcon(VentanaTablero.class.
							getResource("/packImagenes/d3.gif")));break;
			case 4: lblTIzda.setIcon(new ImageIcon(VentanaTablero.class.
							getResource("/packImagenes/nd.gif")));break;
			case 5: lblTIzda.setIcon(new ImageIcon(VentanaTablero.class.
							getResource("/packImagenes/d5.gif")));break;
			case 6: lblTIzda.setIcon(new ImageIcon(VentanaTablero.class.
							getResource("/packImagenes/d6.gif")));break;
			case 7: lblTIzda.setIcon(new ImageIcon(VentanaTablero.class.
							getResource("/packImagenes/d7.gif")));break;
			case 8: lblTIzda.setIcon(new ImageIcon(VentanaTablero.class.
							getResource("/packImagenes/d8.gif")));break;
			case 9: lblTIzda.setIcon(new ImageIcon(VentanaTablero.class.
							getResource("/packImagenes/d9.gif")));break;	
		}
		switch( t2 )
		{
			case 0: lblTCentro.setIcon(new ImageIcon(VentanaTablero.class
							.getResource("/packImagenes/d0.gif")));break;
			case 1: lblTCentro.setIcon(new ImageIcon(VentanaTablero.class
							.getResource("/packImagenes/d1.gif")));break; 
			case 2: lblTCentro.setIcon(new ImageIcon(VentanaTablero.class
							.getResource("/packImagenes/d2.gif")));break;
			case 3: lblTCentro.setIcon(new ImageIcon(VentanaTablero.class
							.getResource("/packImagenes/d3.gif")));break;
			case 4: lblTCentro.setIcon(new ImageIcon(VentanaTablero.class
							.getResource("/packImagenes/d4.gif")));break;
			case 5: lblTCentro.setIcon(new ImageIcon(VentanaTablero.class
							.getResource("/packImagenes/d5.gif")));break;
			case 6: lblTCentro.setIcon(new ImageIcon(VentanaTablero.class
							.getResource("/packImagenes/d6.gif")));break;
			case 7: lblTCentro.setIcon(new ImageIcon(VentanaTablero.class
							.getResource("/packImagenes/d7.gif")));break;
			case 8: lblTCentro.setIcon(new ImageIcon(VentanaTablero.class
							.getResource("/packImagenes/d8.gif")));break;
			case 9: lblTCentro.setIcon(new ImageIcon(VentanaTablero.class
							.getResource("/packImagenes/d9.gif")));break;	
		}
		switch( t3 )
		{
			case 0: lblTDecha.setIcon(new ImageIcon(VentanaTablero.class
							.getResource("/packImagenes/d0.gif")));break;
			case 1: lblTDecha.setIcon(new ImageIcon(VentanaTablero.class
							.getResource("/packImagenes/d1.gif")));break; 
			case 2: lblTDecha.setIcon(new ImageIcon(VentanaTablero.class
							.getResource("/packImagenes/d2.gif")));break;
			case 3: lblTDecha.setIcon(new ImageIcon(VentanaTablero.class
							.getResource("/packImagenes/d3.gif")));break;
			case 4: lblTDecha.setIcon(new ImageIcon(VentanaTablero.class
							.getResource("/packImagenes/d4.gif")));break;
			case 5: lblTDecha.setIcon(new ImageIcon(VentanaTablero.class
							.getResource("/packImagenes/d5.gif")));break;
			case 6: lblTDecha.setIcon(new ImageIcon(VentanaTablero.class
							.getResource("/packImagenes/d6.gif")));break;
			case 7: lblTDecha.setIcon(new ImageIcon(VentanaTablero.class
							.getResource("/packImagenes/d7.gif")));break;
			case 8: lblTDecha.setIcon(new ImageIcon(VentanaTablero.class
							.getResource("/packImagenes/d8.gif")));break;
			case 9: lblTDecha.setIcon(new ImageIcon(VentanaTablero.class
							.getResource("/packImagenes/d9.gif")));break;	
		}
	}
	
	private JMenuBar getMenuBar_1() 
	{
		if (menuBar == null) 
		{
			menuBar = new JMenuBar();
			menuBar.setBackground(new Color(255, 250, 240));
			menuBar.add(getMnJuego());
			menuBar.add(getMnAyuda());
		}
		return menuBar;
	}
	
	private JMenu getMnJuego() {
		if (mnJuego == null) {
			mnJuego = new JMenu("Juego");
			mnJuego.add(getMntmRanking());
		}
		return mnJuego;
	}
	
	private JMenuItem getMntmRanking() 
	{
		if (mntmRanking == null) 
		{
			mntmRanking = new JMenuItem("Ranking");
			mntmRanking.addActionListener( controlador );
		}
		return mntmRanking;
	}
	
	private JMenu getMnAyuda() {
		if (mnAyuda == null) {
			mnAyuda = new JMenu("Ayuda");
			mnAyuda.add(getMntmMM());
		}
		return mnAyuda;
	}
	
	private JMenuItem getMntmMM() 
	{
		if (mntmMM == null) 
		{
			mntmMM = new JMenuItem("Mostrar minas");
			mntmMM.addActionListener( controlador );
		}
		return mntmMM;
	}
	
	private JPanel getPanelMarcador() 
	{
		if (panelMarcador == null) 
		{
			panelMarcador = new JPanel();
			panelMarcador.setBackground(new Color(192, 192, 192));
			panelMarcador.setBorder(new SoftBevelBorder(
							BevelBorder.LOWERED, null, null, null, null));
			panelMarcador.setLayout(new BoxLayout(panelMarcador,
													BoxLayout.X_AXIS));
			panelMarcador.add(getPanelMinas());
			panelMarcador.add(getLblCara());
			panelMarcador.add(getPanelTiempo());
		}
		return panelMarcador;
	}
	
	private JPanel getPanelMinas()
	{
		if (panelMinas == null)
		{
			panelMinas = new JPanel();
			panelMinas.setBackground(new Color(192, 192, 192));
			panelMinas.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 2));
			panelMinas.add(getLblMIzda());
			panelMinas.add(getLblMCentro());
			panelMinas.add(getLblMDecha());
		}
		return panelMinas;
	}
	
	private JLabel getLblMIzda()
	{
		if (lblMIzda == null) 
		{
			lblMIzda = new JLabel("");
		}
		return lblMIzda;
	}
	
	private JLabel getLblMCentro()
	{
		if (lblMCentro == null) 
		{
			lblMCentro = new JLabel("");
		}
		return lblMCentro;
	}
	
	private JLabel getLblMDecha()
	{
		if (lblMDecha == null) 
		{
			lblMDecha = new JLabel("");
		}
		return lblMDecha;
	}
	
	public JLabel getLblCara()
	{
		if (lblCara == null)
		{
			lblCara = new JLabel("");
			lblCara.setIcon(new ImageIcon(VentanaTablero.class.
								getResource("/packImagenes/cara.gif")));
		}
		return lblCara;
	}
	
	private JPanel getPanelTiempo()
	{
		if (panelTiempo == null) 
		{
			panelTiempo = new JPanel();
			panelTiempo.setBackground(new Color(192, 192, 192));
			FlowLayout flowLayout = (FlowLayout) panelTiempo.getLayout();
			flowLayout.setHgap(0);
			flowLayout.setVgap(2);
			panelTiempo.add(getLblTIzda());
			panelTiempo.add(getLblTCentro());
			panelTiempo.add(getLblTDecha());
		}
		return panelTiempo;
	}

	private JLabel getLblTIzda()
	{
		if (lblTIzda == null)
		{
			lblTIzda = new JLabel("");
			lblTIzda.setIcon(new ImageIcon(VentanaTablero.class.
									getResource("/packImagenes/d0.gif")));
		}
		return lblTIzda;
	}
	
	private JLabel getLblTCentro() 
	{
		if (lblTCentro == null) 
		{
			lblTCentro = new JLabel("");
			lblTCentro.setIcon(new ImageIcon(VentanaTablero.class.
									getResource("/packImagenes/d0.gif")));
		}
		return lblTCentro;
	}
	
	private JLabel getLblTDecha() 
	{
		if (lblTDecha == null)
		{
			lblTDecha = new JLabel("");
			lblTDecha.setIcon(new ImageIcon(VentanaTablero.class.
								getResource("/packImagenes/d0.gif")));
		}
		return lblTDecha;
	}
	
	private JPanel getPanelCasillas() 
	{
		if (panelCasillas == null) 
		{
			panelCasillas = new JPanel();
			panelCasillas.setBackground(new Color(192, 192, 192));
			panelCasillas.setBorder(new SoftBevelBorder(
							BevelBorder.LOWERED, null, null, null, null));
			FlowLayout flowLayout = 
								(FlowLayout) panelCasillas.getLayout();
			flowLayout.setVgap(0);
			flowLayout.setHgap(0);
		}
		return panelCasillas;
	}
}