package cz.muni.fi.pv256.movio2.a448273.Containers;

import org.joda.time.DateTime;
import org.joda.time.JodaTimePermission;

import java.util.ArrayList;
import java.util.Calendar;

import cz.muni.fi.pv256.movio2.a448273.Entity.Movie;
import cz.muni.fi.pv256.movio2.a448273.Entity.Type;
import cz.muni.fi.pv256.movio2.a448273.R;

/**
 * Created by gasior on 12.10.2016.
 */

public class MovieContainer {
    private static MovieContainer sInstance;

    private MovieContainer() {

    }

    public static MovieContainer getInstance() {
        if (sInstance == null) {
            sInstance = new MovieContainer();
        }
        return sInstance;
    }

    public static ArrayList<Type> initTypedMovies() {

        ArrayList<Type> types = new ArrayList<>();

       types.add(new Type("In theaters", MovieContainer.getMovies()));
        types.add(new Type("Popular movies", MovieContainer.getMovies()));

        return types;


    }
    public static ArrayList<Movie> getMovies() {

        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie(getTime(2008), String.valueOf(R.drawable.american_pie_profile), "American Pie: Beta House", String.valueOf(R.drawable.american_pie_back), 2.78f));
        movies.add(new Movie(getTime(1998), String.valueOf(R.drawable.good_will_hunting_profile), "Good Will Hunting", String.valueOf(R.drawable.good_will_hunting_back), 4.92f));
        movies.add(new Movie(getTime(1970), String.valueOf(R.drawable.mash_profile), "M*A*S*H", String.valueOf(R.drawable.mash_back), 4.83f));
        movies.add(new Movie(getTime(2008), String.valueOf(R.drawable.american_pie_profile), "American Pie: Beta House", String.valueOf(R.drawable.american_pie_back), 2.78f));
        movies.add(new Movie(getTime(1998), String.valueOf(R.drawable.good_will_hunting_profile), "Good Will Hunting", String.valueOf(R.drawable.good_will_hunting_back), 4.92f));
        movies.add(new Movie(getTime(1970), String.valueOf(R.drawable.mash_profile), "M*A*S*H", String.valueOf(R.drawable.mash_back), 4.83f));
        return movies;
    }


    private static long getTime(int year) {

        DateTime dateTime = new DateTime(year);
        return dateTime.getMillis();

    }

}