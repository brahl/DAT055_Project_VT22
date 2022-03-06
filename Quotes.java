import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collection;
import java.util.HashSet;
//import java.util.Observable;

public class Quotes{


    private String res;


    public Quotes() throws IOException, InterruptedException {
        request();

    }
    public void request(){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://motivational-quotes1.p.rapidapi.com/motivation"))
                .header("content-type", "application/json")
                .header("x-rapidapi-host", "motivational-quotes1.p.rapidapi.com")
                .header("x-rapidapi-key", "a2f56d4216msh516ff8b6cb54e05p195dc8jsn612f558247f2")
                .method("POST", HttpRequest.BodyPublishers.ofString("{\n    \"key1\": \"value\",\n    \"key2\": \"value\"\n}"))
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        this.res = response.body();
    }

    public String getRes() throws InterruptedException {
        int responseTime =(int) ((Math.random() * (2000 - 900)) + 900);
        Thread.sleep(responseTime);
        return res;
    }

}
