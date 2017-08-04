package ar.com.magm.teaching.http.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import ar.com.magm.teaching.http.HttpRequest;
import ar.com.magm.teaching.http.HttpResponse;
import ar.com.magm.teaching.http.HttpServer;
import ar.com.magm.teaching.http.HttpServerListener;
import ar.com.magm.teaching.http.ServerCommands;
/**
 * Interface gráfica simple
 * 
 * @author magm
 * @version 1.0
 */
public class CustomHttpResponser extends JFrame implements HttpServerListener {

	private static final long serialVersionUID = 7372865329454228690L;

	private HttpServer server;
	private JFormattedTextField txtPort;
	private JPanel panelContenido;
	private JTextArea plainRequest;
	private JTabbedPane tabbedPane;
	private JPanel panel_1;
	private JPanel panel_2;
	private JTextArea plainResponse;
	private JPanel panel_3;
	private JButton btnSendResponse;
	private JPanel panel_4;
	private JLabel lblEnDesarrollo;

	public static void main(String[] args) {
		new CustomHttpResponser().setVisible(true);
	}

	public CustomHttpResponser() {
		setTitle("Custom HTTP Responser (by Magm 2017) - Email: magm3333@gmail.com - Twitter: @magm3333");
		setSize(800, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		getContentPane().add(panel, BorderLayout.NORTH);

		JLabel lblServerPort = new JLabel("Server port:");
		panel.add(lblServerPort);
		NumberFormat integerFieldFormatter = NumberFormat.getIntegerInstance();
		integerFieldFormatter.setGroupingUsed(false);
		txtPort = new JFormattedTextField(integerFieldFormatter);
		txtPort.setValue(new Integer(30000));

		panel.add(txtPort);

		final CustomHttpResponser instance=this;
		JButton btnIniciar = new JButton("Iniciar");
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				panelContenido.setVisible(false);
				JButton b = (JButton) evt.getSource();
				if (b.getText().equals("Iniciar")) {
					try {
						server = new HttpServer(Integer.parseInt(getTxtPort().getText()), instance);
						server.start();
						getTxtPort().setEnabled(false);
						b.setText("Parar");
					} catch (IOException e) {
						JOptionPane.showMessageDialog(instance, e.getMessage(), "Error Iniciando Server",
								JOptionPane.ERROR_MESSAGE);
					}
				} else {
					server.forceShutdown();
					b.setText("Iniciar");
					getTxtPort().setEnabled(true);
				}
			}
		});
		panel.add(btnIniciar);
		
		panelContenido = new JPanel();
		panelContenido.setVisible(false);
		getContentPane().add(panelContenido, BorderLayout.CENTER);
		panelContenido.setLayout(new BorderLayout(0, 0));
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		panelContenido.add(tabbedPane, BorderLayout.CENTER);
		
		panel_1 = new JPanel();
		tabbedPane.addTab("Request", null, panel_1, null);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		plainRequest = new JTextArea();
		panel_1.add(plainRequest, BorderLayout.NORTH);
		plainRequest.setEditable(false);
		
		panel_2 = new JPanel();
		tabbedPane.addTab("Response (Plain)", null, panel_2, null);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		HttpResponse resp=new HttpResponse();
		resp.addHeader("Server", "Custom HTTP Server 1.0");
		resp.addHeader("Content-Type", "text/plain");
		resp.addHeader("Connection", "close");
		resp.setBody("Ahora es: "+new Date());
		plainResponse = new JTextArea(resp.toString());
		panel_2.add(plainResponse, BorderLayout.CENTER);
		
		panel_3 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_3.getLayout();
		flowLayout_1.setVgap(10);
		flowLayout_1.setHgap(10);
		flowLayout_1.setAlignment(FlowLayout.RIGHT);
		panel_2.add(panel_3, BorderLayout.SOUTH);
		
		btnSendResponse = new JButton("Enviar Respuesta");
		btnSendResponse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					response.send(getPlainResponse().getText());
				} catch (IOException e1) {
					
				}
				server.continueListening();
				panelContenido.setVisible(false);
			}
		});
		panel_3.add(btnSendResponse);
		
		panel_4 = new JPanel();
		tabbedPane.addTab("Response (Wizzard)", null, panel_4, null);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		lblEnDesarrollo = new JLabel("En desarrollo");
		lblEnDesarrollo.setForeground(Color.GREEN);
		lblEnDesarrollo.setFont(new Font("Dialog", Font.BOLD, 22));
		lblEnDesarrollo.setHorizontalAlignment(SwingConstants.CENTER);
		panel_4.add(lblEnDesarrollo, BorderLayout.CENTER);
	}

	private HttpResponse response;
	@Override
	public void request(HttpRequest request, HttpResponse response, ServerCommands serverCommands) {
		panelContenido.setVisible(true);
		getPlainRequest().setText(request.getPlainMessage());
		this.response=response;
	}

	protected JFormattedTextField getTxtPort() {
		return txtPort;
	}
	protected JPanel getPanelContenido() {
		return panelContenido;
	}
	public JTextArea getPlainRequest() {
		return plainRequest;
	}
	protected JTextArea getPlainResponse() {
		return plainResponse;
	}
}
