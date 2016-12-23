package cz.muni.fi.pv256.movio2.a448273.Peristance.Loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

import cz.muni.fi.pv256.movio2.a448273.Entity.Type;
import cz.muni.fi.pv256.movio2.a448273.Peristance.MovioManager;

/**
 * Created by gasior on 24.12.2016.
 */

public class SaveTypes  extends AsyncTaskLoader<List<Type>> {

    private MovioManager mMovioManager;
    private List<Type> mTypes;

    public SaveTypes(Context context, MovioManager movioManager,List<Type> types) {
        super(context);
        mMovioManager = movioManager;
        mTypes = types;
    }

    @Override
    public List<Type> loadInBackground() {
        for (Type t : mTypes) {
            mMovioManager.createType(t);
            return mMovioManager.getAllTypes();
        }
        return mTypes;
    }
}