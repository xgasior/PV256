package cz.muni.fi.pv256.movio2.a448273.Peristance.Loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import cz.muni.fi.pv256.movio2.a448273.Entity.Movie;
import cz.muni.fi.pv256.movio2.a448273.Peristance.MovioManager;

/**
 * Created by gasior on 23.12.2016.
 */

public class GetMovie extends AsyncTaskLoader<Movie> {

    private Long mMovieId;
    private MovioManager mMovioManager;


    public GetMovie(Context context, Long movieId, MovioManager movioManager) {
        super(context);
        mMovieId = movieId;
        mMovioManager = movioManager;
    }

    @Override
    public Movie loadInBackground() {
        return mMovioManager.getMovieById(mMovieId).get(0);
    }
}
