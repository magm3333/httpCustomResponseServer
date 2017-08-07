package ar.com.magm.teaching.http;

public enum MimeType {

	HTML("text/html"), TEXT_PLAIN("text/plain"), JSON("application/json"), XML("application/xml"), PDF(
			"application/pdf");

	private String mimeType;

	MimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String mimeType() {
		return mimeType;
	}
}
