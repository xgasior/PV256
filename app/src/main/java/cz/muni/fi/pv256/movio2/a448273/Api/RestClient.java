package cz.muni.fi.pv256.movio2.a448273.Api;
import android.os.AsyncTask;
import android.util.Log;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.joda.time.DateTime;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import cz.muni.fi.pv256.movio2.a448273.Constants.ConstantContainer;
import cz.muni.fi.pv256.movio2.a448273.Containers.MovieContainer;
import cz.muni.fi.pv256.movio2.a448273.Entity.Movie;
import cz.muni.fi.pv256.movio2.a448273.Entity.Type;
import cz.muni.fi.pv256.movio2.a448273.Fragments.MainFragment;

/**
 * Created by gasior on 28.10.2016.
 */

public class RestClient  extends AsyncTask<Void, Void, List<Type>> {

    private static WeakReference<MainFragment> sMainFragment;
    private static RestClient sRestClient;
    private static String mBaseAddress = "https://api.themoviedb.org/3/discover/movie?api_key="+ ConstantContainer.API_KEY_MOVIE_DB+"&";
    private static ArrayList<Type> sTypes;
    private static String mImageBaseAddress = "https://image.tmdb.org/t/p/original";

    public static void SetMainFragment(MainFragment mainFragment) {
        sMainFragment = new WeakReference<>(mainFragment);
    }


    static {
        sRestClient = new RestClient();
    }
    public static RestClient getInstance() {
        return sRestClient;
    }


    private RestClient() {

    }

    public void getMovies(ArrayList<Type> types) {
        sTypes = types;
        this.execute();
    }

    private void parseResponse(Response response, Type type) throws IOException {
        ResponseRoot responseRoot = new Gson().fromJson(response.body().string(), ResponseRoot.class);
        int i = 0;
        for (MovieApiModel result : responseRoot.getResults()) {
            Movie movie = new Movie();
            movie.setBackdrop(result.getBackdropPath());
            movie.setCoverPath(result.getPosterPath());
            movie.setPopularity(Float.parseFloat(result.getPopularity().toString()));
            movie.setReleaseDate(DateTime.parse(result.getReleaseDate()).getMillis());
            movie.setTitle(result.getTitle());
            type.getMovies().add(movie);
            i++;
            if(i == 5) {
                break;
            }
        }
    }


    @Override
    protected List<Type> doInBackground(Void... voids) {


        for (Type t : sTypes) {

            try {

                Request request = new Request.Builder()
                        .url(mBaseAddress+t.getUrlParameters())
                        .build();

                parseResponse(new OkHttpClient().newCall(request).execute(), t);
                int i = 0;
                for (Movie m: t.getMovies()) {



                    MovieContainer.sStringHastMap.put(m.getCoverPath(), Glide.
                            with(sMainFragment.get().getContext()).
                            load(mImageBaseAddress + m.getCoverPath()).
                            asBitmap().
                            into(200, 200).
                            get());


                    MovieContainer.sStringHastMap.put(m.getBackdrop(), Glide.
                            with(sMainFragment.get().getContext()).
                            load(mImageBaseAddress + m.getBackdrop()).
                            asBitmap().
                            into(200, 200).
                            get());


                }

            } catch (IOException e){
                Log.e("Download", "Error on url: " + mBaseAddress );
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return sTypes;

    }
    @Override
    protected void onPostExecute(List<Type> result) {

        MovieContainer.sMovies = result;
        sMainFragment.get().setContent();
    }

}
