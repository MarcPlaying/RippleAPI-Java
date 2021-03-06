package Main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RippleAPI {
	
	public static void setServer(String server) {
		Static.server = server;
	}
	
	public static Boolean isReachable() throws IOException, ParseException {
		String getter = null;
        URL u = null;
		try {
			u = new URL(Static.server + "/api/v1/ping");
		} catch (MalformedURLException ex) {
			ex.printStackTrace();
		}
            URLConnection urlConnection = u.openConnection();
            urlConnection.addRequestProperty("User-Agent", "Mozilla/5.0");
            try (InputStream stream = urlConnection.getInputStream();
              InputStreamReader inputStreamReader = new InputStreamReader(stream);
              BufferedReader reader = new BufferedReader(inputStreamReader)) {
              StringBuilder result = new StringBuilder();
              String tmp;
              while ((tmp = reader.readLine()) != null) {
                result.append(tmp).append("\n");
              }
              getter = result.toString();
            }
            JSONParser parser = new JSONParser();
            	Object obj = parser.parse(new FileReader(getter));
                JSONObject jsonObject = (JSONObject) obj;
                Long code = (Long) jsonObject.get("code");
                if(code == 404) {
                	return false;
                }else if(code == 200) {
                	return true;
                }
			return null;
	}
	
	public static String getServer() {
		return Static.server;
	}

}
