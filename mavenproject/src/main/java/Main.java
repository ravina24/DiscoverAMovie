import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;

public class Main {
    // class that gets omdb info
    // parses cineplex with BeautifulSoup
    // uses Twilio to send text to me

    public static final String API_KEY = "265f48b1";
    public static final String OMDB_URL = "http://www.omdbapi.com/?apikey="+ API_KEY + "&";

    public static void main(String[] args) {
        try {
            linkOMDBWithUnirest("tt1285016"); // for the Social Network
        } catch (UnirestException e) {
            System.out.println("RAVINA: UNIREST EXCEPTION THROWN :(");
        }
    }

    public static void linkOMDBWithUnirest(String movieId) throws UnirestException {
        // test request for The Social Network

        HttpResponse<JsonNode> body = Unirest.get(OMDB_URL + "i=" + movieId)
                .header("accept", "application/json")
                .asJson();

        int statusCode = body.getStatus();
        JsonNode responseBody = body.getBody();

        System.out.println("ravina: statusCode = " + statusCode);
        System.out.println("ravina: responseBody = " + responseBody);

    }
}
