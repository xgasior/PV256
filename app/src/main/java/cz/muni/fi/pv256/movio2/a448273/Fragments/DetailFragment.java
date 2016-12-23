package cz.muni.fi.pv256.movio2.a448273.Fragments;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cz.muni.fi.pv256.movio2.a448273.Containers.MovieContainer;
import cz.muni.fi.pv256.movio2.a448273.Entity.Movie;
import cz.muni.fi.pv256.movio2.a448273.Entity.Type;
import cz.muni.fi.pv256.movio2.a448273.Peristance.Loaders.DeleteMovie;
import cz.muni.fi.pv256.movio2.a448273.Peristance.Loaders.GetTypes;
import cz.muni.fi.pv256.movio2.a448273.Peristance.Loaders.SaveMovie;
import cz.muni.fi.pv256.movio2.a448273.Peristance.MovioManager;
import cz.muni.fi.pv256.movio2.a448273.R;

/**
 * Created by gasior on 12.10.2016.
 */

public class DetailFragment  extends Fragment {

    public DetailFragment() {
    }

    public static boolean sIsEmpty;
    public static final String TAG = DetailFragment.class.getSimpleName();
    private static final String ARGS_MOVIE = "args_movie";
    public static DetailFragment lastInstance;
    private Movie mMovie;
    private Context mContext;

    public static DetailFragment newInstance(Movie movie) {
        DetailFragment fragment = new DetailFragment();

        if(movie != null) {
            Bundle args = new Bundle();
            args.putParcelable(ARGS_MOVIE, movie);
            fragment.setArguments(args);
        }
        lastInstance = fragment;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity();
        Bundle args = getArguments();
        if (args != null) {
            mMovie = args.getParcelable(ARGS_MOVIE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        TextView title = (TextView) view.findViewById(R.id.movie_title_name);
        //TextView year = (TextView) view.findViewById(R.id.movie_title);
        LinearLayout prof = (LinearLayout) view.findViewById(R.id.profile_pic);
        LinearLayout back = (LinearLayout) view.findViewById(R.id.header_pic);

        if (mMovie != null) {

            DateTime dateTime = new DateTime(mMovie.getReleaseDate());

            title.setText(mMovie.getTitle() + "\n" + dateTime.toString(" dd.MM. yyyy"));

            back.setBackground(new BitmapDrawable(MovieContainer.sStringHastMap.get(mMovie.getBackdrop())));
            prof.setBackground(new BitmapDrawable(MovieContainer.sStringHastMap.get(mMovie.getCoverPath())));
            FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
            fab.setVisibility(View.VISIBLE);
            if(mMovie.getId()  == 0) {
                fab.setImageResource(R.drawable.fab);

            } else {
                fab.setImageResource(R.drawable.delete);
            }
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mMovie.getId() == 0) {
                        getLoaderManager().initLoader(0, null, new SaveMovieCallback(getActivity().getApplicationContext())).forceLoad();
                    } else {
                        getLoaderManager().initLoader(0, null, new DeleteMovieCallback(getActivity().getApplicationContext())).forceLoad();
                    }
                }
            });
        } else {
                title.setText("NO DATA");
                FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
                fab.setVisibility(View.INVISIBLE);
        }

        return view;
    }

    public class SaveMovieCallback implements LoaderManager.LoaderCallbacks<List<Type>> {
    Context mContext;

    public SaveMovieCallback(Context context) {
        mContext = context;
    }

    @Override
    public Loader<List<Type>> onCreateLoader(int id, Bundle args) {
        return new SaveMovie(mContext, new MovioManager(mContext), mMovie);
    }

    @Override
    public void onLoadFinished(Loader<List<Type>> loader, List<Type> data) {
        MovieContainer.sMovies.addAll(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Type>> loader) {

    }
    }
    private class DeleteMovieCallback implements LoaderManager.LoaderCallbacks<List<Type>> {
        Context mContext;

        public DeleteMovieCallback(Context context) {
            mContext = context;
        }

        @Override
        public Loader<List<Type>> onCreateLoader(int id, Bundle args) {
            return new DeleteMovie(mContext, mMovie, new MovioManager(mContext));
        }

        @Override
        public void onLoadFinished(Loader<List<Type>> loader, List<Type> data) {
            MovieContainer.sMovies.addAll(data);
        }

        @Override
        public void onLoaderReset(Loader<List<Type>> loader) {

        }
    }
}
