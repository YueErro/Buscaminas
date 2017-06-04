package packVista;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import packBD.BaseDeDatos;
import packModelo.*;

public class VentanaRanking extends JDialog 
{
	private final JPanel contentPanel;
	private JScrollPane scrollPane;
	
	private JList<String> list;
	private DefaultListModel<String> listModel;

	private static VentanaRanking mVentanaPuntuaciones;
	private JPanel panel;
	private JLabel lblNombre;
	private JLabel lblTiempo;
		
	public static VentanaRanking getInstance()
	{
		if ( mVentanaPuntuaciones == null )
			mVentanaPuntuaciones = new VentanaRanking();
		
		return mVentanaPuntuaciones;
	}
	
	private VentanaRanking() 
	{
		listModel = new DefaultListModel<>();
		list = new JList<>( listModel );
		scrollPane = new JScrollPane();
		contentPanel = new JPanel();
		
		initialize();
	}
	
	private void initialize() 
	{
		addWindowListener(new WindowAdapter() 
		{
			@Override
			public void windowOpened( WindowEvent arg0 ) 
			{
				Nivel n = Tablero.obtenerTablero().nivel();
				String tabla;
				
				if( n.ordinal() == 0 )
					tabla = "Rprincipiante";
				
				else if ( n.ordinal() == 1 )
					tabla = "Rintermedio";
				
				else
					tabla = "Rexperto";
				
				ArrayListOrdenado<Jugador> datos = BaseDeDatos.
												getInstance().cargarDatos(tabla);

				int j = 0 ;
				
				while( j < datos.size() && j < 10 )
				{	
					listModel.addElement( String.format( "%-10s%6d", 
											datos.get(j).getNombre(),
											datos.get(j).getTiempo() ) );
					j++;
				}
			}
		});
		setTitle("Ranking");
		setIconImage(Toolkit.getDefaultToolkit().getImage(
		VentanaRanking.class.getResource("/packImagenes/logo.png")));
		
		setBounds(100, 100, 173, 222);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		contentPanel.add(scrollPane, BorderLayout.CENTER);
		list.setValueIsAdjusting(true);
		list.setFont(new Font("Courier New", Font.PLAIN, 11));
		scrollPane.setViewportView(list);
		
		panel = new JPanel();
		contentPanel.add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{55, 37, 34, 0};
		gbl_panel.rowHeights = new int[]{14, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		lblNombre = new JLabel("Nombre");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblNombre.insets = new Insets(0, 0, 0, 5);
		gbc_lblNombre.gridx = 0;
		gbc_lblNombre.gridy = 0;
		panel.add(lblNombre, gbc_lblNombre);
		
		lblTiempo = new JLabel("Tiempo");
		GridBagConstraints gbc_lblTiempo = new GridBagConstraints();
		gbc_lblTiempo.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblTiempo.gridx = 2;
		gbc_lblTiempo.gridy = 0;
		panel.add(lblTiempo, gbc_lblTiempo);
	
		JPanel buttonPane = new JPanel();
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
	
		JButton cerrarButton = new JButton("Cerrar");
		cerrarButton.addActionListener(new ActionListener() {
	
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cerrarButton.setActionCommand("Cerrar");
		buttonPane.add(cerrarButton);
		getRootPane().setDefaultButton(cerrarButton);
		
		centrarVentana();
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
	
		setLocation( ( screenSize.width - windowSize.width ) / 2,
					( screenSize.height - windowSize.height ) / 2 );	
	}
}