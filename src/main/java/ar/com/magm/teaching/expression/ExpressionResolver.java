package ar.com.magm.teaching.expression;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ar.com.magm.teaching.http.BadHttpFormatException;
import ar.com.magm.teaching.http.HttpResponse;

public class ExpressionResolver {
	private static final String REG_EXP_FOR_EXPRESSION = "\\$\\{\\w+(;[\\w+|,|\\/|.|\\s|\\-]*)*\\}";
	private HttpResponse response;

	private List<Expression> listExp = new ArrayList<Expression>();

	public ExpressionResolver(HttpResponse response) {
		this.response = response;
	}

	public HttpResponse resolve() throws ExpressionParseException, IOException, BadHttpFormatException {
		if (response.getBody().length == 0)
			return response;
		HttpResponse r = new HttpResponse(response.getSocket(), response.getContent());
		String newBody = new String(r.getBody());
		processListExpression(newBody);
		int fullBodyCount = 0;
		for (Expression e : listExp) {
			if (e.isFullBody())
				fullBodyCount++;
		}
		if (fullBodyCount > 1)
			throw new ExpressionParseException(
					"No puede haber más de una expressión que represente el contenido completo del body");
		if (fullBodyCount == 1 && listExp.size() != 1)
			throw new ExpressionParseException(
					"No puede mezclar una expressión que represente el contenido completo del body con expresiones simples");
		if (fullBodyCount == 1) {
			Expression e = listExp.get(0);
			e.reconfigure(r);
			r.setBody(e.getContent());
		} else {
			for (Expression e : listExp) {
				if (e != null) {
					newBody = newBody.replace(e.getFullExpression(), e.getString());
					
				}
			}
			r.setBody(newBody.getBytes());
		}
		if (r.getBody().length > 0) {
			r.setHeader("content-length", r.getBody().length + "");
		}
		return r;
	}

	private void processListExpression(String body) {
		Pattern p = Pattern.compile(REG_EXP_FOR_EXPRESSION);
		Matcher m = p.matcher(body);
		while (m.find()) {
			String expFull = body.substring(m.start(), m.end());
			listExp.add( loadClass(getExpName(expFull), getExpParams(expFull),expFull) );
		}
	}

	private Expression loadClass(String expName, String expParams, String expFull) {
		if (expName.equals("now")) {
			return new NowExpression(expParams,expFull);
		}
		if (expName.equals("selectFile")) {
			return new SelectFileExpression(expParams);
		}
		if (expName.equals("loadFile")) {
			return new LoadFileExpression(expParams);
		}
		return null;
	}

	private String getExpName(String exp) {
		String r = "";
		r = exp.substring(2, exp.length() - 1);
		if (exp.indexOf(";") != -1) {
			r = r.substring(0, r.indexOf(";"));
		}
		return r;
	}

	private String getExpParams(String exp) {
		String r = null;
		if (exp.indexOf(";") != -1) {
			r = exp.substring(exp.indexOf(";") + 1, exp.length() - 1);
		}
		if (r == null || r.trim().length() == 0)
			r = null;
		return r;
	}
}
