package cz.muni.fi.pv256.movio2.a448273.Entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import cz.muni.fi.pv256.movio2.a448273.R;

/**
 * Created by gasior on 12.10.2016.
 */

public class MovieContainer {
    private static MovieContainer sInstance;
    private ArrayList<Movie> mMovieList = new ArrayList<>();

    private MovieContainer() {
        initFilmList();
    }

    public static MovieContainer getInstance() {
        if (sInstance == null) {
            sInstance = new MovieContainer();
        }
        return sInstance;
    }

    private void initFilmList() {
            mMovieList.add(new Movie(getTime(2008), String.valueOf(R.drawable.american_pie_profile), "American Pie: Beta House", String.valueOf(R.drawable.american_pie_back), 2.78f));
            mMovieList.add(new Movie(getTime(1998), String.valueOf(R.drawable.good_will_hunting_profile), "Good Will Hunting", String.valueOf(R.drawable.good_will_hunting_back), 4.92f));
            mMovieList.add(new Movie(getTime(1970), String.valueOf(R.drawable.mash_profile), "M*A*S*H", String.valueOf(R.drawable.mash_back), 4.83f));
    }

    private long getTime(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        return calendar.getTime().getTime();
    }

    public ArrayList<Movie> getMovieList() {
        return mMovieList;
    }

    public void setMovieList(ArrayList<Movie> movieList) {
        mMovieList = movieList;
    }
}