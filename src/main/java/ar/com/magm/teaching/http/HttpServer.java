package ar.com.magm.teaching.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Http Server serial
 * 
 * @author magm
 * @version 1.0
 */
public class HttpServer extends Thread implements ServerCommands {

	private int port;
	private boolean next = false;
	private boolean normalAccept = true;
	boolean cont = true;
	private ServerSocket server;

	public HttpServer(int port, HttpServerListener listener) throws IOException {
		this.port = port;
		this.listener = listener;
		server = new ServerSocket(port);
	}

	private HttpServerListener listener;

	@Override
	public void run() {
		try {
			System.out.println("Server escuchando en " + port);
			while (cont) {
				next = false;
				if (normalAccept)
					System.out.println("Esperando socket...");
				Socket socket = server.accept();

				RequestProcessor rp = new RequestProcessor(socket);
				HttpRequest r = rp.process();
				if (r.isValid()) {
					if (listener != null) {
						listener.request(r, new HttpResponse(socket), this);
						while (!next) {
							sleep(1000);
						}
					} else {
						normalAccept = false;
						socket.close();
					}
				} else {
					normalAccept = false;
					socket.close();
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
		} finally {
			try {
				System.out.println("Shutdown!");
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void continueListening() {
		next = true;
		normalAccept = true;
	}

	@Override
	public void shutdown() {
		cont = false;
	}

	/**
	 * Fuerza la finalización del server
	 */
	public void forceShutdown() {
		try {
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
