package cz.muni.fi.pv256.movio2.a448273.Entity;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import cz.muni.fi.pv256.movio2.a448273.R;

/**
 * Created by gasior on 12.10.2016.
 */

public class MovieAdapter extends BaseAdapter {

    private Context mAppContext;
    private ArrayList<Movie> mMovieList;

    public MovieAdapter(ArrayList<Movie> movieList, Context context) {
        mMovieList = movieList;
        mAppContext = context.getApplicationContext();
    }

    @Override
    public int getCount() {
        return mMovieList.size();
    }

    @Override
    public Object getItem(int position) {
        return mMovieList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mAppContext).inflate(R.layout.movie_item, parent, false);
            ViewHolder holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.bindView(mAppContext, mMovieList.get(position));

        return convertView;
    }

    private static class ViewHolder {
        private ImageView coverIv;
        private TextView year;
        private TextView title;

        public ViewHolder(View view) {
            coverIv = (ImageView) view.findViewById(R.id.list_item_icon);
            year = (TextView) view.findViewById(R.id.list_item_date);
            title = (TextView) view.findViewById(R.id.list_item_title);
        }

        public void bindView(Context context, Movie movie) {
            if (movie == null)  return;
           // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
           //     coverIv.setImageDrawable(context.getDrawable(Integer.parseInt(movie.getCoverPath())));
          /*  } else {*/
                coverIv.setImageDrawable(context.getResources().getDrawable(Integer.parseInt(movie.getCoverPath())));
            /*}*/
            coverIv.setScaleType(ImageView.ScaleType.CENTER);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(movie.getReleaseDate());
            year.setText(String.valueOf(calendar.get(Calendar.YEAR)));
            title.setText(movie.getTitle());
        }
    }
}
