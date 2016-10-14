package cz.muni.fi.pv256.movio2.a448273.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cz.muni.fi.pv256.movio2.a448273.Entity.Movie;
import cz.muni.fi.pv256.movio2.a448273.Fragments.DetailFragment;
import cz.muni.fi.pv256.movio2.a448273.Fragments.MainFragment;
import cz.muni.fi.pv256.movio2.a448273.R;



/**
 * Created by gasior on 12.10.2016.
 */

public class MainActivity extends AppCompatActivity implements MainFragment.OnMovieSelectListener  {


   // Button mThemeSwitch;



    private boolean mIsTwoPanes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     //   SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
      /*  Boolean isPunk = prefs.getBoolean("punk", false);

        if(isPunk) {
            setTheme(R.style.MaterialStyle);
        } else {*/
       /* }
        prefs.edit().putBoolean("punk", !isPunk).apply(); // Zmeneno
*/

     setTheme(R.style.MaterialStyle_Punk);
     setContentView(R.layout.activity_main);

      /*  mThemeSwitch = (Button)findViewById(R.id.theme_switch);
        mThemeSwitch.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = getIntent();

                finish();
                startActivity(intent);

                /* Kdyz to udelam takto, tak se aplikace minimalizuje <-- Zmeneno
                startActivity(intent);
                finish();

                // Ale pokud si to opravdu prejete, muzu to jeste zmenit :)
            }
        });*/
        if (findViewById(R.id.fragment_detail) != null) {

            mIsTwoPanes = true;

            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_detail, new DetailFragment(), DetailFragment.TAG)
                        .commit();
            }
        } else {
            mIsTwoPanes = false;

            getSupportActionBar().setElevation(0f);
        }


    }

    @Override
    public void onMovieSelect(Movie movie) {
        if (mIsTwoPanes) {
            FragmentManager fragmentManager = getSupportFragmentManager();

            DetailFragment fragment = DetailFragment.newInstance(movie);
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_detail, fragment, DetailFragment.TAG)
                    .commit();

        } else {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(DetailActivity.SELECTED_MOVIE, movie);
            startActivity(intent);
        }
    }


}
