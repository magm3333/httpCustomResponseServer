package ar.com.magm.teaching.expression;

import java.text.SimpleDateFormat;
import java.util.Date;

import ar.com.magm.teaching.http.HttpResponse;

public class NowExpression implements Expression {

	private String params;
	private String fullExpression;

	public NowExpression(String params, String fullExpression) {
		super();
		this.params = params;
		this.fullExpression = fullExpression;
	}

	@Override
	public byte[] getContent() {
		return null;
	}

	@Override
	public String getString() {
		String format = "dd/MM/yyy H:s:m";
		if (params != null) {
			format = params;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);

		return sdf.format(new Date());
	}

	@Override
	public String getFullExpression() {
		return fullExpression;
	}

	@Override
	public boolean isFullBody() {

		return false;
	}

	@Override
	public void reconfigure(HttpResponse response) {

	}

}
