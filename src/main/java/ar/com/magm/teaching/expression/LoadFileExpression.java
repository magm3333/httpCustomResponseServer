package ar.com.magm.teaching.expression;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import ar.com.magm.teaching.http.HttpResponse;

public class LoadFileExpression implements Expression {
	private String params;

	public LoadFileExpression(String params) {
		super();
		this.params = params;
	}

	@Override
	public byte[] getContent() {
		byte[] r = new byte[0];
		File f = new File(params);
System.out.println("cargando: "+f.getAbsolutePath());
		if (f.exists() && f.canRead() && f.isFile()) {
			try {
				// TODO detectar mime type
				r = Files.readAllBytes(f.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return r;
	}

	@Override
	public String getString() {
		return null;
	}

	@Override
	public String getFullExpression() {
		return null;
	}

	@Override
	public boolean isFullBody() {

		return true;
	}

	@Override
	public void reconfigure(HttpResponse response) {
		// TODO set mime type
	}

}
