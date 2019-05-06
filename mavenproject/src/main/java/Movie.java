import java.util.List;

public class Movie {
    public String title;
    public List<String> genreList;
    public List<String> actorList;
    public Number rating;
    public String overview;
    public String releaseDate;

    public Movie(String title, List<String> genreList, List<String> actorList, Number rating, String overview, String releaseDate) {
        this.title = title;
        this.genreList = genreList;
        this.actorList = actorList;
        this.rating = rating;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }


}
