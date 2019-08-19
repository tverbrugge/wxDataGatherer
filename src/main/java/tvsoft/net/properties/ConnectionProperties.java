package tvsoft.net.properties;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class ConnectionProperties {

	private Properties properties;

	public String getUrlString() {
		return String.format("%sdata?stationid=%s", getConnectionProperties().getProperty("url"),
				getStationId());
	}

	private Properties getConnectionProperties() {
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

	private String getStationId() {
//		return getConnectionProperties().getProperty("stationId");
		return "GHCND:USW00003017";
	}
}
