package cz.muni.fi.pv256.movio2.a448273.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import cz.muni.fi.pv256.movio2.a448273.Entity.Movie;
import cz.muni.fi.pv256.movio2.a448273.Entity.MovieAdapter;
import cz.muni.fi.pv256.movio2.a448273.Entity.MovieContainer;
import cz.muni.fi.pv256.movio2.a448273.R;

/**
 * Created by gasior on 12.10.2016.
 */

public class MainFragment  extends Fragment {

    private static final String TAG = MainFragment.class.getSimpleName();
    private static final String SELECTED_KEY = "selected_position";

    private int mPosition = ListView.INVALID_POSITION;
    private OnMovieSelectListener mListener;
    private Context mContext;
    private ListView mListView;

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);

        try {
            mListener = (OnMovieSelectListener) activity;
        } catch (ClassCastException e) {
            Log.e(TAG, "Activity must implement OnMovieSelectListener", e);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mListener = null; //Avoid leaking the Activity
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity().getApplicationContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        fillListView(view);

        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
            mPosition = savedInstanceState.getInt(SELECTED_KEY);

            if (mPosition != ListView.INVALID_POSITION) {
                mListView.smoothScrollToPosition(mPosition);
            }
        }

        return view;
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

    private void fillListView(View rootView) {
        // get data
        ArrayList<Movie> movieList = MovieContainer.getInstance().getMovieList();

        mListView = (ListView) rootView.findViewById(R.id.movie_list);

        if (movieList != null && !movieList.isEmpty()) {
            setAdapter(mListView, movieList);
        }
    }

    private void setAdapter(ListView filmLV, final ArrayList<Movie> movieList) {
        MovieAdapter adapter = new MovieAdapter(movieList, mContext);
        filmLV.setAdapter(adapter);

        // set on click listener
        filmLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPosition = position;
                mListener.onMovieSelect(movieList.get(position));
            }
        });

        // set on long click listener
        filmLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mContext, movieList.get(position).getTitle(), Toast.LENGTH_SHORT)
                        .show();
                return true;
            }
        });
    }

    public interface OnMovieSelectListener {
        void onMovieSelect(Movie movie);
    }
}
