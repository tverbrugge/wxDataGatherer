package tvsoft.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.net.ssl.HttpsURLConnection;

public class Main {
	// See
	// https://stackoverflow.com/questions/24454164/what-will-be-the-equivalent-to-following-curl-command-in-java
	public static void main(String[] args) {
		HttpsURLConnection connection = new NCDCConnection().getConnection();

		try {
			connection.connect();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
}
