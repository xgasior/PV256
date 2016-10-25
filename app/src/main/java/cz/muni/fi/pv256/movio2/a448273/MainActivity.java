package cz.muni.fi.pv256.movio2.a448273;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    Button mThemeSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean isPunk = prefs.getBoolean("punk", false);
        if(isPunk) {
            setTheme(R.style.MaterialStyle);
        } else {
            setTheme(R.style.MaterialStyle_Punk);
        }
        prefs.edit().putBoolean("punk", !isPunk).apply(); // Zmeneno

        setContentView(R.layout.activity_main);


        setTheme(R.style.MaterialStyle_Punk);
        mThemeSwitch = (Button)findViewById(R.id.theme_switch);
        mThemeSwitch.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = getIntent();

                finish();
                startActivity(intent);

                /* Kdyz to udelam takto, tak se aplikace minimalizuje <-- Zmeneno
                startActivity(intent);
                finish();*/

                // Ale pokud si to opravdu prejete, muzu to jeste zmenit :)
            }
        });
    }
}
