package cz.muni.fi.pv256.movio2.a448273.Peristance.Loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

import cz.muni.fi.pv256.movio2.a448273.Entity.Type;
import cz.muni.fi.pv256.movio2.a448273.Peristance.MovioManager;

/**
 * Created by gasior on 23.12.2016.
 */

public class GetTypes extends AsyncTaskLoader<List<Type>> {

    private MovioManager mMovioManager;

    public GetTypes(Context context, MovioManager movioManager) {
        super(context);
        mMovioManager = movioManager;
    }

    @Override
    public List<Type> loadInBackground() {
        return mMovioManager.getAllTypes();
    }
}
