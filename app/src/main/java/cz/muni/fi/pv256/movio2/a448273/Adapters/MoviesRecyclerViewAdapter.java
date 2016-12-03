package cz.muni.fi.pv256.movio2.a448273.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cz.muni.fi.pv256.movio2.a448273.Activities.MainActivity;
import cz.muni.fi.pv256.movio2.a448273.Containers.MovieContainer;
import cz.muni.fi.pv256.movio2.a448273.Entity.Type;
import cz.muni.fi.pv256.movio2.a448273.Entity.Movie;
import cz.muni.fi.pv256.movio2.a448273.Fragments.DetailFragment;
import cz.muni.fi.pv256.movio2.a448273.R;

/**
 * Created by gasior on 24.10.2016.
 */

public class MoviesRecyclerViewAdapter extends RecyclerView.Adapter<MoviesRecyclerViewAdapter.ViewHolder>  {

    public static Context sContext;


    ViewHolder.OnMovieSelectListener mOnMovieSelectListener;

    public MoviesRecyclerViewAdapter(ViewHolder.OnMovieSelectListener listener, Context context) {
        sContext = context;
        mOnMovieSelectListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) sContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.movie_item, parent, false);
        Log.i("onCreateViewHolder:", view.toString());
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        int i = 0;
        int j = 0;
        Type x = null;
        for (Type t : MovieContainer.sMovies) {
            j = i;
            i = i+t.getMovies().size();
            if(position<i) {
                x = t;
                break;
            }
        }
        String name = "";
        if(position-j == 0) {
            name = x.getName();
        }
        holder.bindView(sContext, name, x.getMovies().get(position-j), mOnMovieSelectListener);
        if(position == 0) {
            if(!DetailFragment.sIsEmpty && MainActivity.mIsTwoPanes) {
                ViewHolder.sOnMovieSelectListener.onMovieSelect(MovieContainer.sMovies.get(0).getMovies().get(0)); }

        }
    }

    @Override
    public int getItemCount() {

        int i = 0;
        for (Type t : MovieContainer.sMovies) {
           i+= t.getMovies().size();
        }
        return i;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener  {
        private RelativeLayout mRelativeLayout;
        private Movie mMovie;
        private Context mContext;
        private ImageView mCover;
        private TextView mTitle;
        private TextView mRating;
        private TextView mTypeText;
        public static OnMovieSelectListener sOnMovieSelectListener;

        public ViewHolder(View view) {
            super(view);
            mRelativeLayout = (RelativeLayout) view.findViewById(R.id.rel_lay_rel);
            mTitle = (TextView) view.findViewById(R.id.list_item_title);
            mRating = (TextView) view.findViewById(R.id.list_item_rating);
            mCover = (ImageView)view.findViewById(R.id.movie_item_icon);
            mTypeText = (TextView) view.findViewById(R.id.type_text);
        }
//linearLayout
        public void bindView(Context context, String name, Movie movie, OnMovieSelectListener listener) {
            Log.i("OnBindView", movie.getTitle());
            if (movie == null)  return;
            if(!name.equals("")) {

                mTypeText.setText(name);
                mTypeText.setVisibility(View.VISIBLE);
            } else {
                mTypeText.setVisibility(View.GONE);
            }
            mMovie = movie;
            mContext = context;
            Bitmap myBitmap = MovieContainer.sStringHastMap.get(movie.getBackdrop());
            if (myBitmap != null && !myBitmap.isRecycled()) {
                Palette palette = Palette.from(myBitmap).generate();
                int defaultCol = 0x000000;
                int palColor = palette.getLightVibrantColor(defaultCol);

                int transparent = Color.argb(0x99, Color.red(palColor), Color.green(palColor), Color.blue(palColor));
                mRating.setBackground(new ColorDrawable(transparent));
                mTitle.setBackground(new ColorDrawable(transparent));

            }

            mCover.setImageDrawable( new BitmapDrawable(MovieContainer.sStringHastMap.get(movie.getBackdrop())));
            mRelativeLayout.setOnClickListener(this);
            mRelativeLayout.setOnLongClickListener(this);
            mRating.setCompoundDrawablesWithIntrinsicBounds(R.drawable.star, 0, 0, 0);
            mRating.setText(String.valueOf(movie.getPopularity()));
            mTitle.setText(movie.getTitle());
            mRating.bringToFront();
            mTitle.bringToFront();
            ViewHolder.sOnMovieSelectListener = listener;
        }

        @Override
        public void onClick(View v) {
            ViewHolder.sOnMovieSelectListener.onMovieSelect(mMovie);
        }

        @Override
        public boolean onLongClick(View v) {
            if(mContext != null && mMovie != null) {
                Toast.makeText(mContext, mMovie.getTitle(), Toast.LENGTH_SHORT)
                        .show();
            }
            return true;
        }

        public interface OnMovieSelectListener {
            void onMovieSelect(Movie movie);
        }
    }

}
