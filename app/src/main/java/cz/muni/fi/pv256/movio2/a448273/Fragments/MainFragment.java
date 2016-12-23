package cz.muni.fi.pv256.movio2.a448273.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cz.muni.fi.pv256.movio2.a448273.Activities.MainActivity;
import cz.muni.fi.pv256.movio2.a448273.Adapters.MoviesRecyclerViewAdapter;
import cz.muni.fi.pv256.movio2.a448273.Adapters.NavAdapter;
import cz.muni.fi.pv256.movio2.a448273.Api.RestClient;
import cz.muni.fi.pv256.movio2.a448273.Constants.ConstantContainer;
import cz.muni.fi.pv256.movio2.a448273.Containers.MovieContainer;
import cz.muni.fi.pv256.movio2.a448273.Entity.Movie;
import cz.muni.fi.pv256.movio2.a448273.Entity.Type;
import cz.muni.fi.pv256.movio2.a448273.Peristance.Loaders.GetTypes;
import cz.muni.fi.pv256.movio2.a448273.Peristance.Loaders.SaveTypes;
import cz.muni.fi.pv256.movio2.a448273.Peristance.MovioContract;
import cz.muni.fi.pv256.movio2.a448273.Peristance.MovioManager;
import cz.muni.fi.pv256.movio2.a448273.R;
import cz.muni.fi.pv256.movio2.a448273.Service.MovioService;

/**
 * Created by gasior on 12.10.2016.
 */

public class MainFragment  extends Fragment {

    private static final String TAG = MainFragment.class.getSimpleName();
    private static final String SELECTED_KEY = "selected_position";
    public static MainFragment sInstance;
    private int mPosition = ListView.INVALID_POSITION;
    private MoviesRecyclerViewAdapter.ViewHolder.OnMovieSelectListener mListener;
    public static Context sContext;
    public static boolean sTypesInited;
    private RecyclerView mRecyclerView;
    private static boolean sIsOnline = true;
    public MainFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
        Log.d(TAG, "onStart: ");
        MainActivity activity = (MainActivity)getActivity();
        Switch onlineOfflineSwitch = activity.getSwitch();

        if (onlineOfflineSwitch != null) {

            onlineOfflineSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (!isChecked) {
                        MovieContainer.sMovies.clear();
                        sIsOnline = false;
                        load();

                    } else {
                        MovieContainer.sMovies.clear();
                        sIsOnline = true;
                        download();
                    }
                }
            });
        }
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        sInstance = this;
        mListener =  (MoviesRecyclerViewAdapter.ViewHolder.OnMovieSelectListener) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mListener = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sInstance = this;
        sContext = getActivity().getApplicationContext();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;

        if(sIsOnline) {
            final ConnectivityManager connMgr = (ConnectivityManager) sContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            final android.net.NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            final android.net.NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if (mobile == null) {
                if (wifi != null && !wifi.isConnected()) {

                    view = inflater.inflate(R.layout.empty_fragment, container, false);
                    TextView textView = (TextView) view.findViewById(R.id.empty_fragment_text);
                    textView.setText("No Internet");
                    return view;
                }
            } else if (mobile != null && !wifi.isConnected() && !mobile.isConnected()) {

                view = inflater.inflate(R.layout.empty_fragment, container, false);
                TextView textView = (TextView) view.findViewById(R.id.empty_fragment_text);
                textView.setText("No Internet");
                return view;
            }

            view = inflater.inflate(R.layout.fragment_main, container, false);
            mRecyclerView = (RecyclerView) view.findViewById(R.id.movies_by_genres_rec_view);
            Log.i("onCreateView:", "full");

            if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
                mPosition = savedInstanceState.getInt(SELECTED_KEY);

                if (mPosition != ListView.INVALID_POSITION) {
                    mRecyclerView.smoothScrollToPosition(mPosition);
                }
            }


            //RestClient.SetMainFragment(this);
            //MovieContainer.getInstance().initTypedMovies(this);

           download();
        }
        else  {
           load();
            int count = 0;
            for(Type t : MovieContainer.sMovies) {
                count += t.getMovies().size();
            }
            if(count == 0) {

            view = inflater.inflate(R.layout.empty_fragment, container, false);
            TextView textView = (TextView) view.findViewById(R.id.empty_fragment_text);
            textView.setText("No data");
                return view;
            } else {
                view = inflater.inflate(R.layout.fragment_main, container, false);
                mRecyclerView = (RecyclerView) view.findViewById(R.id.movies_by_genres_rec_view);
            }


            if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
            mPosition = savedInstanceState.getInt(SELECTED_KEY);

            if (mPosition != ListView.INVALID_POSITION) {
                mRecyclerView.smoothScrollToPosition(mPosition);
            }
            }

        }
        return view;
    }


    private void load() {
        getLoaderManager().initLoader(0, null, new TypesCallback(getActivity().getApplicationContext())).forceLoad();
    }

    private class TypesCallback implements LoaderManager.LoaderCallbacks<List<Type>> {
        Context mContext;

        public TypesCallback(Context context) {
            mContext = context;
        }

        @Override
        public Loader<List<Type>> onCreateLoader(int id, Bundle args) {
            return new GetTypes(mContext, new MovioManager(mContext));
        }

        @Override
        public void onLoadFinished(Loader<List<Type>> loader, List<Type> data) {
            MovieContainer.sMovies.addAll(data);
            setAdapter(mRecyclerView, MovieContainer.sMovies);
            setContent();
        }

        @Override
        public void onLoaderReset(Loader<List<Type>> loader) {

        }
    }

    public class SaveTypesCallback implements LoaderManager.LoaderCallbacks<List<Type>> {
        Context mContext;

        public SaveTypesCallback(Context context) {
            mContext = context;
        }

        @Override
        public Loader<List<Type>> onCreateLoader(int id, Bundle args) {
            return new SaveTypes(mContext, new MovioManager(mContext), MovieContainer.getTypes());
        }




        @Override
        public void onLoadFinished(Loader<List<Type>> loader, List<Type> data) {
            MovieContainer.sMovies.addAll(data);
        }

        @Override
        public void onLoaderReset(Loader<List<Type>> loader) {

        }
    }


    public class GetTypesCallback implements LoaderManager.LoaderCallbacks<List<Type>> {
        Context mContext;

        public GetTypesCallback(Context context) {
            mContext = context;
        }

        @Override
        public Loader<List<Type>> onCreateLoader(int id, Bundle args) {
            return new GetTypes(mContext, new MovioManager(mContext));
        }

        @Override
        public void onLoadFinished(Loader<List<Type>> loader, List<Type> data) {
            MovieContainer.sMovies.addAll(data);
            sTypesInited = true;
        }

        @Override
        public void onLoaderReset(Loader<List<Type>> loader) {

        }
    }
    private void download() {
        startListening();
        for (Type t : MovieContainer.getTypes()) {
            if (!MovieContainer.sMovies.contains(t)) {
                downloadMovies(t);
            }
        }
    }

    private void fillRecycleView(RecyclerView view, List<Type> types) {
        if (types != null && view != null) {
            view.setHasFixedSize(true);

            view.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            view.setItemAnimator(new DefaultItemAnimator());

            setAdapter(view, types);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mPosition != ListView.INVALID_POSITION) {
            outState.putInt(SELECTED_KEY, mPosition);
        }
        super.onSaveInstanceState(outState);
    }


    private void downloadMovies(Type type) {
        Intent intent = new Intent(sContext, MovioService.class);
        intent.putExtra(ConstantContainer.TYPE_INTENT_KEY, type);
        sContext.startService(intent);
    }
    private void startListening() {
        LocalBroadcastManager.getInstance(sContext).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Type type = (Type) intent.getParcelableExtra(ConstantContainer.TYPE_INTENT_KEY);
                MovieContainer.sMovies.add(type);
                setAdapter(mRecyclerView, MovieContainer.sMovies);
                setContent();
            }
        }, new IntentFilter(ConstantContainer.DOWNLOADED_INTENT_KEY));
    }

    private void setAdapter(RecyclerView recyclerView, final List<Type> types) {
        if (recyclerView != null){
            MoviesRecyclerViewAdapter adapter = new MoviesRecyclerViewAdapter(mListener, sContext, types);
        recyclerView.setAdapter(adapter);
    }
    }



    public void setContent() {
            ArrayList<Type> typeArrayList = new ArrayList<>();
            ArrayList<String> checkboxesChecked = new ArrayList<>();
            for (CheckBox c : NavAdapter.sCheckBoxes.keySet()) {
                if (c.isChecked()) {
                    checkboxesChecked.add(NavAdapter.sCheckBoxes.get(c));
                }
            }
            for (Type t : MovieContainer.sMovies) {
                if (checkboxesChecked.contains(t.getName())) {
                    if (!typeArrayList.contains(t)) {
                        typeArrayList.add(t);
                    }
                }
            }
            fillRecycleView(mRecyclerView, typeArrayList);

    }
    public void init() {
        getLoaderManager().initLoader(0, null, new GetTypesCallback(getActivity().getApplicationContext())).forceLoad();
        if(!sTypesInited) {
            sTypesInited = true;
            getLoaderManager().initLoader(0, null, new SaveTypesCallback(getActivity().getApplicationContext())).forceLoad();

    }}
}
