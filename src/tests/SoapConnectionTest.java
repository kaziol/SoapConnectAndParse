package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import pckg.SoapConnection;
import pckg.XMLRequest;

class SoapConnectionTest {
	
	 

	@Test
	void testGetResponse() throws IOException {
		SoapConnection sc = new SoapConnection("http://www.webservicex.net/globalweather.asmx?op=GetWeather");
		String request = XMLRequest.getRequest("Kraków", "Polska");
		String resp=sc.getResponse(request);
		assertNotNull(resp);
		assertTrue(resp.startsWith("<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope"));
	}

}
