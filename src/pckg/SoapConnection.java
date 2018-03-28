package pckg;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;


public class SoapConnection {
	private HttpURLConnection http;
	public SoapConnection(String endpoint) throws IOException {
		URL url = new URL(endpoint);
		URLConnection con = url.openConnection();
		http = (HttpURLConnection) con;
		http.setRequestMethod("POST");
		
	}
	public String getResponse(String request) throws IOException {
		byte[] out = request.getBytes(StandardCharsets.UTF_8);
		int length = out.length;
		http.setFixedLengthStreamingMode(length);
		http.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
		http.setDoOutput(true);
		http.connect();
		try (OutputStream os = http.getOutputStream()) {
			os.write(out);
		}
		String result = IOUtils.toString(http.getInputStream());
		return result;
	}

}
