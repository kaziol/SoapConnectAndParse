package pckg;

public class XMLRequest {
	private final static String request= "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n" + 
			"<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n" + 
			"  <soap:Body>\r\n" + 
			"    <GetWeather xmlns=\"http://www.webserviceX.NET\">\r\n" + 
			"      <CityName>${city}</CityName>\r\n" + 
			"      <CountryName>${country}</CountryName>\r\n" + 
			"    </GetWeather>\r\n" + 
			"  </soap:Body>\r\n" + 
			"</soap:Envelope>";
	public static String getRequest(String city, String country) {
		return request.replace("${city}", city).replace("${city}", country);
	}
	
}
