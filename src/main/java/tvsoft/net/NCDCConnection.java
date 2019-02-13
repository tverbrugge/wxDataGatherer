package tvsoft.net;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class NCDCConnection {
	public static void main(String[] args) {
		Properties props = new Properties();
		props.setProperty("token", "NOPE");
		try {
			props.storeToXML(Files.newOutputStream(Paths.get("./config/ncdcSecurity.xml")), "some comment");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
