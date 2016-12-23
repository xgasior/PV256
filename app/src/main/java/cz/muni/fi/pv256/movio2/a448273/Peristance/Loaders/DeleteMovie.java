package cz.muni.fi.pv256.movio2.a448273.Peristance.Loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

import cz.muni.fi.pv256.movio2.a448273.Entity.Movie;
import cz.muni.fi.pv256.movio2.a448273.Entity.Type;
import cz.muni.fi.pv256.movio2.a448273.Peristance.MovioManager;

/**
 * Created by gasior on 23.12.2016.
 */

public class DeleteMovie extends AsyncTaskLoader<List<Type>> {

    private Movie mMovie;
    private MovioManager mMovioManager;

    public DeleteMovie(Context context, Movie movie, MovioManager movioManager) {
        super(context);
        mMovie = movie;
        mMovioManager = movioManager;
    }

    @Override
    public List<Type> loadInBackground() {
        mMovioManager.deleteMovie(mMovie);
        return mMovioManager.getAllTypes();
    }
}
