package packVista;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import packControlador.ControladorLogin;
import packModelo.Nivel;

public class VentanaLogin extends JDialog
{
	private ControladorLogin controladorLogin;
	
	private final JPanel contentPanel;
	private JPanel panelVersion;
	private JPanel panelUsuario;
	private JLabel lblNombre;
	private JTextField textField;
	private final ButtonGroup buttonGroup;
	private JRadioButton rdbtnPrincipiante;
	private JRadioButton rdbtnIntermedio;
	private JRadioButton rdbtnExperto;
	
	private JButton aceptarButton;
	private JButton cancelarButton;
	
	private static VentanaLogin mVentanaLogin;
		
	public static VentanaLogin getInstance()
	{
		if ( mVentanaLogin == null )
			mVentanaLogin = new VentanaLogin();
		
		return mVentanaLogin;
	}
	
	private VentanaLogin() 
	{
		contentPanel = new JPanel();
		buttonGroup = new ButtonGroup();
		controladorLogin = new ControladorLogin();
		
		initialize();
	}
	
	private void initialize() 
	{
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(
			VentanaLogin.class.getResource("/packImagenes/logo.png")));
		
		setTitle("Bienvenid@");
		setBounds(100, 100, 270, 266);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			panelVersion = new JPanel();
			panelVersion.setBounds(10, 11, 254, 24);
			contentPanel.add(panelVersion);
			{
				JLabel lblVersion = new JLabel(
										"Buscaminas UPV/EHU");
				panelVersion.add(lblVersion);
			}
		}
		contentPanel.add(getPanelUsuario());
		{
			JPanel panelNiveles = new JPanel();
			panelNiveles.setBorder(new TitledBorder(UIManager.getBorder(
												"TitledBorder.border"), 
												"Seleccione Nivel:", 
												TitledBorder.LEADING,
												TitledBorder.TOP, 
												null, 
												new Color(0, 0, 0)));
			panelNiveles.setBounds(10, 76, 234, 111);
			contentPanel.add(panelNiveles);
			panelNiveles.setLayout(null);
			{
				rdbtnPrincipiante = new JRadioButton("Principiante");
				rdbtnPrincipiante.setBounds(20, 22, 109, 23);
				buttonGroup.add(rdbtnPrincipiante);
				panelNiveles.add(rdbtnPrincipiante);
				rdbtnPrincipiante.setSelected(true);
			}
			{
				rdbtnIntermedio = new JRadioButton("Intermedio");
				rdbtnIntermedio.setBounds(20, 48, 109, 23);
				buttonGroup.add(rdbtnIntermedio);
				panelNiveles.add(rdbtnIntermedio);
			}
			{
				rdbtnExperto = new JRadioButton("Experto");
				rdbtnExperto.setBounds(20, 74, 109, 23);
				buttonGroup.add(rdbtnExperto);
				panelNiveles.add(rdbtnExperto);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			{
				aceptarButton = new JButton("Aceptar");
				aceptarButton.addActionListener( controladorLogin );
				aceptarButton.setActionCommand("Aceptar");
				buttonPane.add(aceptarButton);
				getRootPane().setDefaultButton(aceptarButton);
				aceptarButton.setEnabled(false);
			}
			{
				cancelarButton = new JButton("Cancelar");
				cancelarButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) 
					{
						dispose();
					}
				});
				cancelarButton.setActionCommand("Cancelar");
				buttonPane.add(cancelarButton);
			}
		}
		
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
	
	private JPanel getPanelUsuario()
	{
		if (panelUsuario == null) 
		{
			panelUsuario = new JPanel();
			panelUsuario.setBounds(10, 35, 234, 30);
			panelUsuario.setLayout(null);
			panelUsuario.add(getLblNombre());
			{
				textField = new JTextField();
				textField.addKeyListener(new KeyAdapter() 
				{
					@Override
					public void keyTyped(KeyEvent arg0) 
					{
						String texto = "";
						
						if( textField.getText().length() > 9 )
							textField.setText( texto );
						
						aceptarButton.setEnabled(true);
					}
				});
				textField.setBounds(67, 5, 157, 20);
				panelUsuario.add(textField);
				textField.setColumns(10);
			}
		}
		return panelUsuario;
	}
	
	private JLabel getLblNombre()
	{
		if (lblNombre == null) 
		{
			lblNombre = new JLabel("Nombre:");
			lblNombre.setBounds(10, 8, 59, 14);
		}
		return lblNombre;
	}
	
	public Nivel getNivelSeleccionado()
	{
		if( rdbtnPrincipiante.isSelected() )
			return Nivel.NIVEL1;
		else if( rdbtnIntermedio.isSelected() )
			return Nivel.NIVEL2;
		else
			return Nivel.NIVEL3;
	}
	
	public String getNombreJugador()
	{
		return textField.getText();
	}

	public void borrarNombreJugador() 
	{
		textField.setText("");
		textField.requestFocus();
	}
}