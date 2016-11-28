package cz.muni.fi.pv256.movio2.a448273.Fragments;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;

import cz.muni.fi.pv256.movio2.a448273.Entity.Movie;
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

    private Movie mMovie;
    private Context mContext;

    public static DetailFragment newInstance(Movie movie) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGS_MOVIE, movie);
        fragment.setArguments(args);
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

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(mMovie.getReleaseDate());
            title.setText(mMovie.getTitle() + "\n" + cal.get(Calendar.YEAR));
           // year.setText(cal.get(Calendar.YEAR));

            back.setBackground(mContext.getResources().getDrawable(Integer.parseInt(mMovie.getBackdrop())));
            prof.setBackground(mContext.getResources().getDrawable(Integer.parseInt(mMovie.getCoverPath())));
        }


        return view;
    }


}
