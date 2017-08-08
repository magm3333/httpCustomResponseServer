package ar.com.magm.teaching.http;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Hashtable;
import java.util.Scanner;

/**
 * Representa la respuesta. Permite ejecutarla y ayuda a procesarla
 * 
 * @author magm
 * @version 1.0
 */
public class HttpResponse {
	private Socket socket;
	private String content;

	public HttpResponse(Socket socket) throws IOException {
		this.socket = socket;
	}

	public HttpResponse() {
	}

	public HttpResponse(Socket socket, String content) throws IOException, BadHttpFormatException {
		this.socket = socket;
		this.content = content;
		process();
	}

	public void process() throws IOException, BadHttpFormatException {
		Scanner s = new Scanner(new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));
		boolean first = true;
		boolean headers = true;
		StringBuilder body = new StringBuilder();
		while (s.hasNextLine()) {
			String line = s.nextLine();
			if (first) {
				String[] l = line.split(" ");
				if (l.length != 3)
					throw new BadHttpFormatException("Primera línea [" + line + "] incorrecta");
				httpVersion = l[0];
				status = Integer.parseInt(l[1]);
				statusText = l[2];
				first = false;
			} else {
				if (line.trim().length() == 0) {
					headers = false;
					continue;
				}
				if (headers) {
					String[] header = line.split(":");
					addHeader(header[0], line.substring(header[0].length() + 1, line.length()));
				} else {
					body.append(line);
				}
			}

		}
		setHeader("content-length", body.length() + "");
		s.close();
		setBody(body.toString().getBytes());

	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void dummyOut(String txt) throws IOException {
		BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
		out.write(("HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\nConnection: close\r\n\r\n" + txt).getBytes());
		out.flush();
		socket.close();
	}

	public void send() throws IOException {
		BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
		out.write(
				String.format("%s %s %s\r\n%s\r\n", getHttpVersion(), getStatus(), getStatusText(), headersStringOut())
						.getBytes());
		if (getBody().length > 0) {
			out.write(getBody());
		}
		out.flush();
		socket.close();

	}

	public void send(String message) throws IOException {
		BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
		out.write(
				String.format("%s %s %s\r\n%s\r\n", getHttpVersion(), getStatus(), getStatusText(), headersStringOut())
						.getBytes());
		if (getBody().length > 0) {
			out.write(getBody());
		}
		out.flush();
		socket.close();

	}

	private String httpVersion = "HTTP/1.1";
	private int status = 200;
	private String statusText = "OK";
	private Hashtable<String, String> headers = new Hashtable<String, String>();

	public String getHttpVersion() {
		return httpVersion;
	}

	public void setHttpVersion(String httpVersion) {
		this.httpVersion = httpVersion;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}

	private String headersString() {
		StringBuilder sb = new StringBuilder();
		for (String k : getHeaders().keySet()) {
			sb.append(String.format("%s:%s%n", k, getHeaders().get(k)));
		}
		String r = "";
		if (sb.toString().length() > 0)
			r = sb.toString();
		return r;
	}

	private String headersStringOut() {
		StringBuilder sb = new StringBuilder();
		for (String k : getHeaders().keySet()) {
			sb.append(String.format("%s:%s\r\n", k, getHeaders().get(k)));
		}
		String r = "";
		if (sb.toString().length() > 0)
			r = sb.toString();
		return r;
	}

	public Hashtable<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Hashtable<String, String> headers) {
		this.headers = headers;
	}

	private byte[] body = new byte[0];

	public byte[] getBody() {
		return body;
	}

	public void setBody(byte[] body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return String.format("%s %s %s%n%s%n%s", getHttpVersion(), getStatus(), getStatusText(), headersString(),
				new String(getBody()));
	}

	public void addHeader(String header, String value) {
		headers.put(header.toLowerCase(), value);
	}

	public void setHeader(String h, String v) {
		headers.remove(h.toLowerCase());
		addHeader(h, v);
	}
}
