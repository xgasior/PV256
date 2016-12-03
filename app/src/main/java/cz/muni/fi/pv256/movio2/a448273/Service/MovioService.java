package cz.muni.fi.pv256.movio2.a448273.Service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.bumptech.glide.Glide;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import cz.muni.fi.pv256.movio2.a448273.Activities.MainActivity;
import cz.muni.fi.pv256.movio2.a448273.Api.MovieApiModel;
import cz.muni.fi.pv256.movio2.a448273.Api.ResponseRoot;
import cz.muni.fi.pv256.movio2.a448273.Constants.ConstantContainer;
import cz.muni.fi.pv256.movio2.a448273.Containers.MovieContainer;
import cz.muni.fi.pv256.movio2.a448273.Entity.Movie;
import cz.muni.fi.pv256.movio2.a448273.Entity.Type;
import cz.muni.fi.pv256.movio2.a448273.Fragments.MainFragment;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gasior on 29.11.2016.
 */

public class MovioService extends IntentService {

    private static final int ERROR = 0;
    private static final int DOWNLOADING = 1;
    private static final int DOWNLOADED = 2;

    private NotificationHelper mNotificationHelper;
    private MovioClient mMovioClient = getClient();
    private static boolean sIsItFirstTime = true;
    private MovioClient getClient() {
        return new Retrofit.Builder()
                .baseUrl(ConstantContainer.BASE_ADDRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovioClient.class);
    }

    public MovioService() {
        super(MovioService.class.getSimpleName());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationHelper = new NotificationHelper(getApplicationContext(),
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE));
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (!isNetworkAvailable()) {
            if (sIsItFirstTime) {
                mNotificationHelper.showNotification(ERROR, "Turn on data!", true);
                sIsItFirstTime = false;
            }
            return;
        }
        Type result = null;
        Type type = intent.getParcelableExtra(ConstantContainer.TYPE_INTENT_KEY);
        retrofit2.Call<ResponseRoot> requestCall = mMovioClient.GetMovies("?api_key=" + ConstantContainer.API_KEY_MOVIE_DB +type.getUrlParameters());
        if (requestCall == null) {
            throw new UnsupportedOperationException("Invalid FilmListType: couldn't make a request.");
        }

        try {
            showDownloadingNotification(type);
            retrofit2.Response<ResponseRoot> response = requestCall.execute();
            ResponseRoot responseRoot = response.body();
            mNotificationHelper.showNotification(DOWNLOADED, "Downloaded", true);
            result = parseResponse(responseRoot,type);
        } catch (Exception ex) {
            String e = ex.getMessage() == null ? ex.toString() : ex.getMessage();
            mNotificationHelper.showNotification(ERROR, "Error downloading: " + type.getName() + " ex: " + ex, true);
        }

        if (result != null) {
            //MovieContainer.sMovies.add(result);
            Intent finishIntent = new Intent(ConstantContainer.DOWNLOADED_INTENT_KEY);
            finishIntent.putExtra(ConstantContainer.TYPE_INTENT_KEY, result);
            LocalBroadcastManager.getInstance(this).sendBroadcast(finishIntent);
        }
    }
    private void showDownloadingNotification(Type type) {
        String message = "Downloading: " + type.getName();

        NotificationCompat.Builder builder = mNotificationHelper.getMainActivityNotificationBuilder(message)
                .setAutoCancel(false)
                .setOngoing(true);
        builder.setTicker(message);
        mNotificationHelper.showNotification(DOWNLOADING, builder);
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    private Type parseResponse(ResponseRoot responseRoot, Type type) throws IOException {
        int i = 0;
        Type output = new Type();
        output.setName(type.getName());
        output.setUrlParameters(type.getUrlParameters());
        for (MovieApiModel result : responseRoot.getResults()) {
            Movie movie = new Movie();
            movie.setBackdrop(result.getBackdropPath());
            movie.setCoverPath(result.getPosterPath());
            movie.setPopularity(Float.parseFloat(result.getPopularity().toString()));
            movie.setReleaseDate(DateTime.parse(result.getReleaseDate()).getMillis());
            movie.setTitle(result.getTitle());
            try {
                MovieContainer.sStringHastMap.put(movie.getCoverPath(), Glide.
                        with(MainFragment.sContext).
                        load(ConstantContainer.IMAGE_BASE_ADDRESS + movie.getCoverPath()).
                        asBitmap().
                        into(200, 200).
                        get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }


            try {
                MovieContainer.sStringHastMap.put(movie.getBackdrop(), Glide.
                        with(MainFragment.sContext).
                        load(ConstantContainer.IMAGE_BASE_ADDRESS + movie.getBackdrop()).
                        asBitmap().
                        into(200, 200).
                        get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            output.getMovies().add(movie);
            i++;
            if(i == 5) {
                break;
            }
        }

        return output;
    }
}
