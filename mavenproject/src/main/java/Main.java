import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.ArrayList; //"tt1285016"
import java.util.List;

// next step: try to get response body by giving params for movies:
// director, actor(s), IMDB rating, genre, year, title
public class Main {
    // class that gets omdb info
    // parses cineplex with BeautifulSoup
    // uses Twilio to send text to me

    public static final String API_KEY = "484f4e63198cc39f9e9c72d002f9269d";
    public static final String TMDB_URL = "https://api.themoviedb.org/3/discover/movie?api_key="+ API_KEY + "&";

    public static void main(String[] args) {
        try {
            // these params will actually come from the params to main
            ArrayList<String> directorList = new ArrayList<String>();
            ArrayList<String> actorList = new ArrayList<String>();
            ArrayList<String> genreList = new ArrayList<String>();

            String imdbRating;
            String year;
            String title;

            directorList.add("David Fincher");

            actorList.add("Jesse Eisenberg");
            actorList.add("Rooney Mara");
            actorList.add("Bryan Barter");
            actorList.add("Dustin Fitzsimons");

            genreList.add("Biography");
            genreList.add("Drama");

            imdbRating = "7.7";
            year = "2010";
            title = "The Social Network";

            linkOMDBWithUnirest(directorList, actorList, genreList, imdbRating, year, title);
        } catch (UnirestException e) {
            e.printStackTrace();
            System.out.println("RAVINA: UNIREST EXCEPTION THROWN :(");
        }
    }

    public static void linkOMDBWithUnirest(List<String> directorList, List<String> actorList, List<String> genreList, String imdbRating, String year, String title) throws UnirestException {
        // test request for The Social Network

        HttpResponse<JsonNode> body = Unirest.get(TMDB_URL + "primary_release_date.gte=" + "2014-09-15")
                .header("accept", "application/json")
                .asJson();

        int statusCode = body.getStatus();
        JsonNode responseBody = body.getBody();

        System.out.println("ravina: statusCode = " + statusCode);
        System.out.println("ravina: responseBody = " + responseBody);

    }
}
