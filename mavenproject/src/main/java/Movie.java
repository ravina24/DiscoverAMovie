import java.util.List;

public class Movie {
    public static String title;
    public static List<String> genreList;
    public static List<String> actorList;
    public static Number rating;
    public static String overview;
    public static String releaseDate;

    public Movie(String title, List<String> genreList, List<String> actorList, Number rating, String overview, String releaseDate) {
        this.title = title;
        this.genreList = genreList;
        this.actorList = actorList;
        this.rating = rating;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }


}
