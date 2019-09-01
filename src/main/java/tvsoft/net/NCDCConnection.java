package tvsoft.net;

import java.io.IOException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Properties;

import javax.net.ssl.HttpsURLConnection;

import tvsoft.net.properties.ConnectionProperties;

public class NCDCConnection {

	private ConnectionProperties connectionProperties;

	public HttpsURLConnection getConnection() {
		HttpsURLConnection connection;
		try {
			String urlString = getConnectionProperties()
					.getUrlStringForData(Date.from(Instant.now().minus(7, ChronoUnit.DAYS)), Date.from(Instant.now()));
			System.out.println(urlString);
			connection = (HttpsURLConnection) new URL(urlString).openConnection();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		setHeaderProperties(connection);

		return connection;
	}

	private ConnectionProperties getConnectionProperties() {
		if (connectionProperties == null) {
			connectionProperties = new ConnectionProperties();
		}
		return connectionProperties;
	}

	private String getRequestToken() {
		Properties properties = new Properties();
		try {
			properties.loadFromXML(Files.newInputStream(Paths.get("./config/ncdcSecurity.xml")));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		System.out.println("Using token " + properties.getProperty("token"));
		return properties.getProperty("token");
	}

	private void setHeaderProperties(HttpsURLConnection connection) {
		try {
			connection.setRequestMethod("GET");
		} catch (ProtocolException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		connection.setRequestProperty("token", getRequestToken());
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setReadTimeout(15 * 1000);
	}

}
