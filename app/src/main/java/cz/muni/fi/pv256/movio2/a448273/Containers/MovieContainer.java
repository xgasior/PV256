package cz.muni.fi.pv256.movio2.a448273.Containers;

import android.graphics.Bitmap;

import org.joda.time.DateTime;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.muni.fi.pv256.movio2.a448273.Api.RestClient;
import cz.muni.fi.pv256.movio2.a448273.Entity.Movie;
import cz.muni.fi.pv256.movio2.a448273.Entity.Type;
import cz.muni.fi.pv256.movio2.a448273.Fragments.MainFragment;

/**
 * Created by gasior on 12.10.2016.
 */

public class MovieContainer {
    private WeakReference<MainFragment> sMainFragment;
    private static MovieContainer sInstance;
    public static List<Type> sMovies = new ArrayList<>();
    public static boolean sDownloaded;
    public static HashMap<String, Bitmap> sStringHastMap = new HashMap<>();
    private MovieContainer() {


    }
    public static MovieContainer getInstance() {
        if (sInstance == null) {
            sInstance = new MovieContainer();
        }
        return sInstance;
    }

    public void initTypedMovies(MainFragment mainFragment) {
        sMainFragment = new WeakReference<>(mainFragment);
        if(sDownloaded) {
            sMainFragment.get().setContent();
        } else {
            sDownloaded = true;
            RestClient.getInstance().getMovies(getTypes());
        }

    }
    public static ArrayList<Type> getTypes() {

        ArrayList<Type> types = new ArrayList<>();

        types.add(new Type("In theaters", new ArrayList<Movie>(),"&primary_release_date.gte="+DateTime.now().minusDays(14).toString("YYYY-MM-dd")+"&primary_release_date.lte="+DateTime.now().toString("YYYY-MM-dd") ));
        types.add(new Type("Popular movies", new ArrayList<Movie>(),"&sort_by=popularity.desc"));

        return types;
    }
   /* public static ArrayList<Movie> getMovies() {

        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie(getTime(2008), String.valueOf(R.drawable.american_pie_profile), "American Pie: Beta House", String.valueOf(R.drawable.american_pie_back), 2.78f));
        movies.add(new Movie(getTime(1998), String.valueOf(R.drawable.good_will_hunting_profile), "Good Will Hunting", String.valueOf(R.drawable.good_will_hunting_back), 4.92f));
        movies.add(new Movie(getTime(1970), String.valueOf(R.drawable.mash_profile), "M*A*S*H", String.valueOf(R.drawable.mash_back), 4.83f));
        movies.add(new Movie(getTime(2008), String.valueOf(R.drawable.american_pie_profile), "American Pie: Beta House", String.valueOf(R.drawable.american_pie_back), 2.78f));
        movies.add(new Movie(getTime(1998), String.valueOf(R.drawable.good_will_hunting_profile), "Good Will Hunting", String.valueOf(R.drawable.good_will_hunting_back), 4.92f));
        movies.add(new Movie(getTime(1970), String.valueOf(R.drawable.mash_profile), "M*A*S*H", String.valueOf(R.drawable.mash_back), 4.83f));
        return movies;
    }
*/

}