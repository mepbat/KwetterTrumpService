package fontys.ict.kwetter.KwetterTrump;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Configuration
public class QuoteSender {

    List<String> quotes = new ArrayList<>();

    @Bean
    public Supplier<String> trumpQuote() throws IOException {
        for(int i = 0; i < 10; i++) {
            URL url = new URL("https://api.whatdoestrumpthink.com/api/v1/quotes/random");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            BufferedReader br;
            if (100 <= http.getResponseCode() && http.getResponseCode() <= 399) {
                br = new BufferedReader(new InputStreamReader(http.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(http.getErrorStream()));
            }
            String responseBody = br.lines().collect(Collectors.joining());
            quotes.add(responseBody);
            http.disconnect();
            System.out.println(responseBody);
        }

        return () -> quotes.get(new Random().nextInt(10));
    }
}
