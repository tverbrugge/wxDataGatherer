package tvsoft.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import javax.net.ssl.HttpsURLConnection;

public class NCDCConnection {

	// See
	// https://stackoverflow.com/questions/24454164/what-will-be-the-equivalent-to-following-curl-command-in-java
	public static void main(String[] args) {

		String data = "key={authKey}";
		byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
		int length = bytes.length;
		// BufferedReader reader;

		HttpsURLConnection connection = getConnection();
		try (OutputStream os = connection.getOutputStream()) {
			os.write(bytes, 0, length);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	private static HttpsURLConnection getConnection() {
		HttpsURLConnection connection;
		try {
			String urlString = getUrlString();
			System.out.println(urlString);
			connection = (HttpsURLConnection) new URL(urlString).openConnection();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		setHeaderProperties(connection);
		try {
			connection.connect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return connection;
	}

	private static void setHeaderProperties(HttpsURLConnection connection) {
		try {
			connection.setRequestMethod("GET");
		} catch (ProtocolException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		// connection.setRequestProperty("Content-Type",
		// "application/x-www-form-urlencoded");
		// connection.setRequestProperty("charset", "UTF-8");
		// connection.setRequestProperty("Content-Length",
		// String.valueOf(length));
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setReadTimeout(15 * 1000);

	}

	private static Properties properties;

	private static Properties getConnectionProperties() {
		if (properties == null) {
			properties = new Properties();
			try {
				properties.loadFromXML(Files.newInputStream(Paths.get("./config/ncdcSecurity.xml")));
				properties = new Properties(properties);
				properties.loadFromXML(Files.newInputStream(Paths.get("./config/ncdcConfig.xml")));
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

		return properties;
	}

	private static String getUrlString() {
		return String.format("%sdata?stationid=%s", getConnectionProperties().getProperty("url"), "GHCND:USW00003017"
		// getConnectionProperties().getProperty("stationId")
		);
	}

}
