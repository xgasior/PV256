package cz.muni.fi.pv256.movio2.a448273.Fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cz.muni.fi.pv256.movio2.a448273.Adapters.MoviesRecyclerViewAdapter;
import cz.muni.fi.pv256.movio2.a448273.Adapters.NavAdapter;
import cz.muni.fi.pv256.movio2.a448273.Api.RestClient;
import cz.muni.fi.pv256.movio2.a448273.Containers.MovieContainer;
import cz.muni.fi.pv256.movio2.a448273.Entity.Movie;
import cz.muni.fi.pv256.movio2.a448273.Entity.Type;
import cz.muni.fi.pv256.movio2.a448273.R;

/**
 * Created by gasior on 12.10.2016.
 */

public class MainFragment  extends Fragment {

    private static final String TAG = MainFragment.class.getSimpleName();
    private static final String SELECTED_KEY = "selected_position";

    private int mPosition = ListView.INVALID_POSITION;
    private MoviesRecyclerViewAdapter.ViewHolder.OnMovieSelectListener mListener;
    private Context mContext;
    private RecyclerView mRecyclerView;

    public MainFragment() {
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
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

        mContext = getActivity().getApplicationContext();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;

        final ConnectivityManager connMgr = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        final android.net.NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final android.net.NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if( mobile == null) {
            if( wifi != null && !wifi.isConnected()) {

                view = inflater.inflate(R.layout.empty_fragment, container, false);
                TextView textView = (TextView)view.findViewById(R.id.empty_fragment_text);
                textView.setText("No Internet");
                return view;
            }
        }  else if( mobile!= null && !wifi.isConnected() && !mobile.isConnected()){

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


            RestClient.SetMainFragment(this);
            MovieContainer.getInstance().initTypedMovies(this);



        return view;
    }

    private void fillRecycleView(View view, List<Type> types) {

        if (types != null && !types.isEmpty()){

            mRecyclerView.setHasFixedSize(true);

            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());

            setAdapter(mRecyclerView, types);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // When tablets rotate, the currently selected list item needs to be saved.
        // When no item is selected, mPosition will be set to Listview.INVALID_POSITION,
        // so check for that before storing.
        if (mPosition != ListView.INVALID_POSITION) {
            outState.putInt(SELECTED_KEY, mPosition);
        }
        super.onSaveInstanceState(outState);
    }



    private void setAdapter(RecyclerView recyclerView, final List<Type> types) {

        MoviesRecyclerViewAdapter adapter = new MoviesRecyclerViewAdapter(mListener, mContext);
        recyclerView.setAdapter(adapter);
    }

    public interface OnMovieSelectListener {
        void onMovieSelect(Movie movie);
    }
    public void setContent() {
        fillRecycleView(mRecyclerView, MovieContainer.mMovies);
    }
}
