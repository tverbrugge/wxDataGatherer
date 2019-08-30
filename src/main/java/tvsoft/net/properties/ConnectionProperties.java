package tvsoft.net.properties;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class ConnectionProperties {

	private Properties properties;
	private static SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

	public String getUrlStringForData(Date startDate, Date endDate) {
		// https://www.ncdc.noaa.gov/cdo-web/api/v2/data?stationid=GHCND:USW00003017&datasetid=GHCND&startdate=2017-02-01&enddate=2017-02-28&units=standard&limit=200
		return String.format("%sdata?stationid=%s&datasetid=%s&startdate=%s&enddate=%s&units=standard&limit=200", 
				getConnectionProperties().getProperty("url"),
				getStationId(),
				getDatasetId(),
				DATE_FORMATTER.format(startDate),
				DATE_FORMATTER.format(endDate));
	}
	
	private Object getDatasetId() {
		return "GHCND";
	}

	public String getUrlStringForDatasets() {
		return String.format("%sdatasets?stationid=%s", getConnectionProperties().getProperty("url"),
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
