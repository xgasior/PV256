package cz.muni.fi.pv256.movio2.a448273.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import cz.muni.fi.pv256.movio2.a448273.Entity.Movie;
import cz.muni.fi.pv256.movio2.a448273.Fragments.DetailFragment;
import cz.muni.fi.pv256.movio2.a448273.R;

/**
 * Created by gasior on 12.10.2016.
 */

public class DetailActivity extends AppCompatActivity {

    public static final String SELECTED_MOVIE = "selected_movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.MaterialStyle_Punk);
        setContentView(R.layout.activity_detail);

        if(savedInstanceState == null){
            Movie movie = getIntent().getParcelableExtra(SELECTED_MOVIE);
            FragmentManager fragmentManager = getSupportFragmentManager();
            DetailFragment detailFragment = (DetailFragment) fragmentManager.findFragmentById(R.id.fragment_detail);

            if (detailFragment == null) {
                detailFragment = DetailFragment.newInstance(movie);
                fragmentManager.beginTransaction()
                        .add(R.id.fragment_detail, detailFragment)
                        .commit();
            }
        }
    }
}