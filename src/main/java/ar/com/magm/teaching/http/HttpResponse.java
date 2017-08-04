package ar.com.magm.teaching.http;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Hashtable;
/**
 * Representa la respuesta.
 * Permite ejecutarla y ayuda a procesarla 
 * 
 * @author magm
 * @version 1.0
 */
public class HttpResponse {
	private Socket socket;

	public HttpResponse(Socket socket) throws IOException {
		this.socket = socket;
	}
	public HttpResponse() {
	}
	public void dummyOut(String txt) throws IOException {
		BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
		out.write(("HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\nConnection: close\r\n\r\n" + txt).getBytes());
		out.flush();
		socket.close();
	}
	public void send() throws IOException {
		BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
		out.write(toString().getBytes());
		out.flush();
		socket.close();
		
	}
	public void send(String message) throws IOException {
		BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
		out.write(message.getBytes());
		out.flush();
		socket.close();
		
	}
	
	private String httpVersion="HTTP/1.1";
	private int status=200;
	private String statusText="OK";
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
		StringBuilder sb=new StringBuilder();
		for (String k : getHeaders().keySet()) {
			sb.append(String.format("%s:%s%n", k, getHeaders().get(k)));
		}
		String r="";
		if(sb.toString().length()>0)
			r=sb.toString();
		return r;
	}
	public Hashtable<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Hashtable<String, String> headers) {
		this.headers = headers;
	}

	private String body="";
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return String.format("%s %s %s%n%s%n%s",getHttpVersion(),getStatus(),getStatusText(),headersString(),getBody());
	}
	
	public void addHeader(String header, String value) {
		headers.put(header, value);
	}
}
