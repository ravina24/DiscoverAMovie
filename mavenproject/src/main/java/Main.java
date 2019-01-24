import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList; //"tt1285016"
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// next step: try to get response body by giving params for movies:
// director, actor(s), IMDB rating, genre, year, title
public class Main {
    // class that gets omdb info
    // parses cineplex with BeautifulSoup
    // uses Twilio to send text to me

    public static final String API_KEY = "484f4e63198cc39f9e9c72d002f9269d";
    public static final String TMDB_DISCOVER_URL = "https://api.themoviedb.org/3/discover/movie?api_key="+ API_KEY + "&";
    public static final String TMDB_SEARCH_ACTOR_URL = "http://api.tmdb.org/3/search/person?api_key=" + API_KEY + "&query=";
    public static final String TMDB_SEARCH_GENRE_URL = "http://api.tmdb.org/3/genre/movie/list?api_key=" + API_KEY;

    public static void main(String[] args) {
        try {
            // these params will actually come from the params to main
            //ArrayList<String> directorList = new ArrayList<String>();
            ArrayList<String> actorList = new ArrayList<String>();
            ArrayList<String> genreList = new ArrayList<String>();

            Number imdbRating;
            Integer year;

            actorList.add("Ryan Reynolds");

            genreList.add("Action");

            imdbRating = 7.5;
            year = null;

            linkOMDBWithUnirest(actorList, genreList, imdbRating, year);
        } catch (UnirestException e) {
            e.printStackTrace();
            System.out.println("RAVINA: UNIREST EXCEPTION THROWN :(");
        }
    }

    public static void linkOMDBWithUnirest(List<String> actorList, List<String> genreList, Number imdbRating, Integer year) throws UnirestException {

        String csActors = getActorIds(actorList);
        String csGenres = getGenreIds(genreList);

        // form url for unirest
        String parametersForUrl = formUrlParams(csActors, csGenres, imdbRating, year);

        HttpResponse<JsonNode> body = makeApiCallout(TMDB_DISCOVER_URL + parametersForUrl);

        int statusCode = body.getStatus();
        JsonNode responseBody = body.getBody();

        System.out.println("ravina: statusCode = " + statusCode);
        System.out.println("ravina: responseBody = " + responseBody);

    }

    private static HttpResponse<JsonNode> makeApiCallout(String url) throws UnirestException {
        HttpResponse<JsonNode> body = Unirest.get(url)
                .header("accept", "application/json")
                .asJson();

        return body;
    }

    // TODO: create final vars for param names
    private static String formUrlParams(String csActors, String csGenres, Number imdbRating, Integer year) {
        String parametersForUrl = "";
        if(csActors != null && csActors.length() > 0) {
            parametersForUrl += "with_cast=" + csActors;
        }

        if(csGenres != null && csGenres.length() > 0) {
            if(parametersForUrl.length() != 0) parametersForUrl+= "&";
            parametersForUrl += "with_genres=" + csGenres;
        }

        if(imdbRating != null) {
            if(parametersForUrl.length() != 0) parametersForUrl+= "&";
            parametersForUrl += "vote_average.gte=" + imdbRating;
        }

        if(year != null) {
            if(parametersForUrl.length() != 0) parametersForUrl+= "&";
            parametersForUrl += "year=" + year;
        }

        System.out.println("RAVINA: parametersForUrl = " + parametersForUrl);
        return parametersForUrl;
    }

    // TODO: make api calls to get genre ids and put them in a comma separated string
    private static String getGenreIds(List<String> genreList) throws UnirestException {
        String csGenres = "";

        HttpResponse<JsonNode> body = makeApiCallout(TMDB_SEARCH_GENRE_URL);
        JsonNode responseBody = body.getBody();
        JSONObject jsonObject = responseBody.getObject();

        HashMap<String, Integer> nameIdMap = new HashMap<String, Integer>();

        JSONArray genreArray = jsonObject.getJSONArray("genres");

        for(int i = 0; i < genreArray.length(); i++)
        {
            JSONObject object = genreArray.getJSONObject(i);
            nameIdMap.put((String) object.get("name"), (Integer) object.get("id"));
        }

        for(String genre : genreList) {
            if(nameIdMap.containsKey(genre)) {
                if(csGenres.length() > 0) csGenres += ",";
                csGenres += nameIdMap.get(genre);
            }
        }

        System.out.println("RAVINA: csGenres = " + csGenres);
        return csGenres;
    }

    // TODO: make api calls to get actor ids and put them in a comma separated string
    private static String getActorIds(List<String> actorList) throws UnirestException {
        String csActors = "";

        for(String actor : actorList) {
            // make api callout, get ids of actors and append to csActors
            HttpResponse<JsonNode> body = null;

            String[] splitName = actor.split("\\s+");
            if(splitName != null && splitName.length == 2) {
                String firstName = splitName[0];
                String lastName = splitName[1];

                body = makeApiCallout(TMDB_SEARCH_ACTOR_URL + firstName + "+" + lastName);
            }

            if(body != null) {
                JsonNode responseBody = body.getBody();
                JSONObject jsonObject = responseBody.getObject();
                JSONArray results = jsonObject.getJSONArray("results");
                if(results != null) {
                    JSONObject actorObject = results.getJSONObject(0);
                    Integer id = (Integer) actorObject.get("id");

                    if(id != null) {
                        if(csActors.length() > 0) csActors += ",";
                        csActors += id;
                    }
                }
            }
        }

        System.out.println("RAVINA: csActors = " + csActors);
        return csActors;
    }
}
