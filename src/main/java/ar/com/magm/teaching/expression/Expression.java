package ar.com.magm.teaching.expression;

import ar.com.magm.teaching.http.HttpResponse;

public interface Expression {
	public byte[] getContent();

	public String getFullExpression();

	public boolean isFullBody();

	public String getString();

	public void reconfigure(HttpResponse response);

}
