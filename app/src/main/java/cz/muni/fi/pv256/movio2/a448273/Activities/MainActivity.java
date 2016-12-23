package cz.muni.fi.pv256.movio2.a448273.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Switch;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import java.util.List;

import cz.muni.fi.pv256.movio2.a448273.Adapters.MoviesRecyclerViewAdapter;
import cz.muni.fi.pv256.movio2.a448273.Adapters.NavAdapter;
import cz.muni.fi.pv256.movio2.a448273.Containers.MovieContainer;
import cz.muni.fi.pv256.movio2.a448273.Entity.Movie;
import cz.muni.fi.pv256.movio2.a448273.Entity.Type;
import cz.muni.fi.pv256.movio2.a448273.Fragments.DetailFragment;
import cz.muni.fi.pv256.movio2.a448273.Fragments.MainFragment;
import cz.muni.fi.pv256.movio2.a448273.Peristance.Loaders.SaveTypes;
import cz.muni.fi.pv256.movio2.a448273.Peristance.MovioManager;
import cz.muni.fi.pv256.movio2.a448273.R;

import static cz.muni.fi.pv256.movio2.a448273.Fragments.MainFragment.*;


/**
 * Created by gasior on 12.10.2016.
 */

public class MainActivity extends AppCompatActivity implements MoviesRecyclerViewAdapter.ViewHolder.OnMovieSelectListener  {
   // Button mThemeSwitch;
    public static boolean sTypesInited;
    public static boolean mIsTwoPanes;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
    savedInstanceState = null;
    super.onCreate(savedInstanceState);
    setTheme(R.style.MaterialStyle_Punk);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.fragment_detail) != null) {

            mIsTwoPanes = true;
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_detail, new DetailFragment(), DetailFragment.TAG)
                        .commit();
        } else {
            mIsTwoPanes = false;
        }
        initNav();
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
            DetailFragment.sIsEmpty = false;
            startActivity(intent);
        }
    }

    private void initNav() {
        ListView listView = (ListView) findViewById(R.id.lst_menu_items);
        if(listView != null) {
            NavAdapter customAdapter = new NavAdapter(this);
            listView.setAdapter(customAdapter);
        }
    }

    public Switch getSwitch() {
        return (Switch)findViewById(R.id.online_offline_switch);
    }




}
