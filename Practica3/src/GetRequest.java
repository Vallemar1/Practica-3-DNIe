import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetRequest {
	private final String USER_AGENT = "Mozilla/5.0";

	
	public boolean sendGet(String nameuser, String dniuser, String pass) throws Exception {

		String url = "http://localhost/dnie/autentica.php?nameuser="+nameuser+"&dniuser="+dniuser+"&pass="+pass;
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("GET");

		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		System.out.println(response.toString());
		
		return true;

	}
	
	
}
