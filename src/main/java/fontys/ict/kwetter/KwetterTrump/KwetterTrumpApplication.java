package fontys.ict.kwetter.KwetterTrump;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@SpringBootApplication
public class KwetterTrumpApplication {

	public static void main(String[] args) {
		SpringApplication.run(KwetterTrumpApplication.class, args);
	}

	@Bean
	public Supplier<String> trumpQuote() throws IOException {
		URL url = new URL("https://api.whatdoestrumpthink.com/api/v1/quotes/random");
		HttpURLConnection http = (HttpURLConnection)url.openConnection();
		BufferedReader br;
		if (100 <= http.getResponseCode() && http.getResponseCode() <= 399) {
			br = new BufferedReader(new InputStreamReader(http.getInputStream()));
		} else {
			br = new BufferedReader(new InputStreamReader(http.getErrorStream()));
		}
		String responseBody = br.lines().collect(Collectors.joining());
		http.disconnect();
		System.out.println(responseBody);
		return () -> responseBody;
	}
}
