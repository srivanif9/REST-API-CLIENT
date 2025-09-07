import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class WeatherAPIClient {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter city name: ");
        String city = scanner.nextLine();
        
        try {
            String apiKey = "98c22114cf927cbc4a849c559e82395c";  
            String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlString))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();

            if (jsonObject.has("main")) {
                String cityName = jsonObject.get("name").getAsString();
                JsonObject main = jsonObject.getAsJsonObject("main");
                double temp = main.get("temp").getAsDouble();
                double humidity = main.get("humidity").getAsDouble();
                String weatherDescription = jsonObject.getAsJsonArray("weather")
                        .get(0).getAsJsonObject()
                        .get("description").getAsString();

                System.out.println("\nWeather Info for " + cityName + ":");
                System.out.println("Temperature: " + temp + "°C");
                System.out.println("Humidity: " + humidity + "%");
                System.out.println("Description: " + weatherDescription);
            } else {
                System.out.println("City not found or invalid API key.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        scanner.close();
    }
}
