package ar.com.magm.teaching.http;

import java.util.Hashtable;
/**
 * Representa el requerimiento.
 * 
 * @author magm
 * @version 1.0
 */
public class HttpRequest {

	private String bodyPayload;
	private String fullPath;

	private String httpVersion;

	private String method;

	private String path;

	private String plainMessage;

	private String queryString = "";

	private Hashtable<String, String> requestHeaders = new Hashtable<String, String>();

	private String requestLine;

	public void addHeader(String header, String value) {
		requestHeaders.put(header, value);
	}

	public void debugInfo() {
		System.out.println("Método   : " + getMethod());
		System.out.println("Full Path: " + getFullPath());
		System.out.println("Path     : " + getPath());
		System.out.println("Query    : " + getQueryString());
		System.out.println("HTTP Ver : " + getHttpVersion());
		System.out.println("******* REQUEST HEADERS *******");
		for (String k : getRequestHeaders().keySet())
			System.out.printf("%s = %s%n", k, getRequestHeaders().get(k));
		System.out.println("***** END REQUEST HEADERS *****");
		System.out.println("************ BODY *************");
		if (getBodyPayload() != null)
			System.out.println(getBodyPayload());
		System.out.println("********** END BODY ***********");

	}

	public String getBodyPayload() {
		return bodyPayload;
	}

	public String getFullPath() {
		return fullPath;
	}

	public String getHttpVersion() {
		return httpVersion;
	}

	public String getMethod() {
		return method;
	}

	public String getPath() {
		return path;
	}

	public String getPlainMessage() {
		return plainMessage + ( bodyPayload.length()>0 ? bodyPayload : "");
	}

	public String getQueryString() {
		return queryString;
	}

	public Hashtable<String, String> getRequestHeaders() {
		return requestHeaders;
	}
	public String getRequestLine() {
		return requestLine;
	}

	public boolean isValid() {
		return getMethod()!=null;
	}

	public void setBodyPayload(String bodyPayload) {
		this.bodyPayload = bodyPayload;
	}

	public void setPlainMessage(String plainMessage) {
		this.plainMessage = plainMessage;
	}
	public void setRequestHeaders(Hashtable<String, String> requestHeaders) {
		this.requestHeaders = requestHeaders;
	}
	
	public void setRequestLine(String requestLine) {

		this.requestLine = requestLine;
		method = requestLine.split(" ")[0];
		fullPath = requestLine.split(" ")[1];
		path = fullPath;
		httpVersion = requestLine.split(" ")[2];
		if (fullPath.indexOf("?") != -1) {
			String[] parts = fullPath.split("\\?");
			path = parts[0];
			queryString = parts[1];
		}
	}

}
