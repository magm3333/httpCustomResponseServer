package ar.com.magm.teaching.http;

public class HttpResponseJListAdapter {
	private HttpResponse response;
	private String name;
	public HttpResponseJListAdapter(HttpResponse response, String name) {
		super();
		this.response = response;
		this.name = name;
	}
	public HttpResponse getResponse() {
		return response;
	}
	public void setResponse(HttpResponse response) {
		this.response = response;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return getName();
	}

}
