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

public class SaveMovie extends AsyncTaskLoader<List<Type>> {

    private MovioManager mMovioManager;
    private Movie mMovie;

    public SaveMovie(Context context, MovioManager movioManager, Movie movie) {
        super(context);
        mMovioManager = movioManager;
        mMovie = movie;
    }

    @Override
    public List<Type> loadInBackground() {
        mMovioManager.createMovie(mMovie);
        return mMovioManager.getAllTypes();
    }
}