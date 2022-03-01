import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Quotes {

    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://motivational-quotes1.p.rapidapi.com/motivation"))
            .header("content-type", "application/json")
            .header("x-rapidapi-host", "motivational-quotes1.p.rapidapi.com")
            .header("x-rapidapi-key", "a2f56d4216msh516ff8b6cb54e05p195dc8jsn612f558247f2")
            .method("POST", HttpRequest.BodyPublishers.ofString("{\n    \"key1\": \"value\",\n    \"key2\": \"value\"\n}"))
            .build();
    HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

    public Quotes() throws IOException, InterruptedException {
        System.out.println(response.body());
    }


}
