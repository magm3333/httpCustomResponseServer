package ar.com.magm.teaching.http.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.text.NumberFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.ListModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ar.com.magm.teaching.expression.ExpressionParseException;
import ar.com.magm.teaching.expression.ExpressionResolver;
import ar.com.magm.teaching.http.BadHttpFormatException;
import ar.com.magm.teaching.http.HttpRequest;
import ar.com.magm.teaching.http.HttpResponse;
import ar.com.magm.teaching.http.HttpResponseJListAdapter;
import ar.com.magm.teaching.http.HttpServer;
import ar.com.magm.teaching.http.HttpServerListener;
import ar.com.magm.teaching.http.ServerCommands;
import ar.com.magm.teaching.util.Util;

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
	private String namePreRequestName;

	public static void main(String[] args) {

		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		new CustomHttpResponser().setVisible(true);
	}

	private ListModel<HttpResponseJListAdapter> responsesLM = null;

	private void loadAndSetPreResponsesModel() {
		try {
			responsesLM = Util.getPreResponsesListModel(null);
			getPreResponseFiles().setModel(responsesLM);
		} catch (IOException | BadHttpFormatException e2) {
			e2.printStackTrace();
		}
	}

	public CustomHttpResponser() {

		setSize(800, 600);

		setTitle("Custom HTTP Responser (by Magm 2017) - Email: magm3333@gmail.com - Twitter: @magm3333");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
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

		final CustomHttpResponser instance = this;
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

		HttpResponse resp = new HttpResponse();
		resp.addHeader("Server", "Custom HTTP Server 1.0");
		resp.addHeader("Content-Type", "text/plain");
		resp.addHeader("Connection", "close");
		resp.setBody(("Ahora es: " + new Date()).getBytes());

		panel_3 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_3.getLayout();
		flowLayout_1.setVgap(10);
		flowLayout_1.setHgap(10);
		flowLayout_1.setAlignment(FlowLayout.RIGHT);
		panel_2.add(panel_3, BorderLayout.SOUTH);

		btnSendResponse = new JButton("Enviar Respuesta");
		btnSendResponse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				send();
			}
		});
		panel_3.add(btnSendResponse);

		btnSendResponseAndSave = new JButton("Enviar Respuesta y Guardar");
		btnSendResponseAndSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendAndSave();
			}
		});
		panel_3.add(btnSendResponseAndSave);
		plainResponse = new JTextArea(resp.toString());
		// panel_2.add(plainResponse, BorderLayout.CENTER);

		splitPane = new JSplitPane();
		panel_2.add(splitPane, BorderLayout.CENTER);

		scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);

		preResponseFiles = new JList<HttpResponseJListAdapter>();
		loadAndSetPreResponsesModel();
		preResponseFiles.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				if (!event.getValueIsAdjusting()) {
					@SuppressWarnings("unchecked")
					JList<HttpResponseJListAdapter> source = (JList<HttpResponseJListAdapter>) event.getSource();
					String selected = null;
					if (source != null && source.getSelectedValue()!=null)
						selected = ((HttpResponseJListAdapter) source.getSelectedValue()).toString();
					try {
						if (selected != null) {
							plainResponse.setText(Util.getPreResponse(selected));
							namePreRequestName = selected;
						}
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		});
		scrollPane.setViewportView(preResponseFiles);

		scrollPane_1 = new JScrollPane();
		splitPane.setRightComponent(scrollPane_1);

		// textArea = new JTextArea();
		scrollPane_1.setViewportView(plainResponse);

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
	private JSplitPane splitPane;
	private JScrollPane scrollPane;
	private JList<HttpResponseJListAdapter> preResponseFiles;
	private JScrollPane scrollPane_1;
	private JButton btnSendResponseAndSave;

	@Override
	public void request(HttpRequest request, HttpResponse response, ServerCommands serverCommands) {
		panelContenido.setVisible(true);
		getPlainRequest().setText(request.getPlainMessage());
		this.response = response;
	}

	private void send() {
		try {
			response.setContent(plainResponse.getText());
			response.process();
			HttpResponse newResponse=new ExpressionResolver(response).resolve();
			newResponse.send();
			server.continueListening();
			panelContenido.setVisible(false);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (BadHttpFormatException e1) {
			e1.printStackTrace();
		} catch (ExpressionParseException e) {
			e.printStackTrace();
		}

	}

	private void sendAndSave() {
		send();
		boolean seguir = true;
		String nameFile = "";
		do {
			nameFile = JOptionPane.showInputDialog(this, "Nombre:", namePreRequestName);
			nameFile = nameFile.trim();
			seguir = false;
			if (nameFile.length() == 0) {
				seguir = JOptionPane.showConfirmDialog(this, "Nombre incorrecto. Ingresa nuevamente?", "Incorrecto",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION;
			}
		} while (seguir);
		try {
			if (nameFile.length() > 0) {
				Util.savePreResponse(nameFile, getPlainResponse().getText(), this);
				loadAndSetPreResponsesModel();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	public JList<HttpResponseJListAdapter> getPreResponseFiles() {
		return preResponseFiles;
	}
}
