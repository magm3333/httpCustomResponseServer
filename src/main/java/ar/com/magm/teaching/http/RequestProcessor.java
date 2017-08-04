package ar.com.magm.teaching.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Procesador de requerimientos principal
 * @author magm
 * @version 1.0
 */
public class RequestProcessor {
	private Socket socket;

	public RequestProcessor(Socket socket) throws IOException {
		this.socket = socket;
	}

	/**
	 * 
	 * @return Request procesado
	 * @throws IOException
	 */
	public HttpRequest process() throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		HttpRequest r = new HttpRequest();
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		boolean noBody = false;
		String line;
		int bodyLength = 0;
		while ((line = in.readLine()) != null) {
			if (first) {
				String method = line.split(" ")[0];
				noBody = "GET,HEAD,PURGE,COPY".indexOf(method) != -1;
				first = false;
				r.setRequestLine(line);
			} else {
				if (line.length() != 0) {
					if (!noBody && line.toLowerCase().startsWith("content-length")) {
						bodyLength = Integer.parseInt(line.split(":")[1].trim());
					}
					String[] header = line.split(":");
					r.addHeader(header[0], line.substring(header[0].length() + 1, line.length()));
				}
			}
			sb.append(line + "\n");
			if (line.length() == 0) {
				if (noBody) {
					break;
				} else {
					char[] body = new char[bodyLength];
					in.read(body);
					r.setBodyPayload(new String(body));
					break;
				}
			}
		}
		r.setPlainMessage(sb.toString());
		return r;

	}

}
