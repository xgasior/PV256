package cz.muni.fi.pv256.movio2.a448273.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import cz.muni.fi.pv256.movio2.a448273.Containers.MovieContainer;
import cz.muni.fi.pv256.movio2.a448273.Fragments.MainFragment;
import cz.muni.fi.pv256.movio2.a448273.R;

/**
 * Created by gasior on 25.10.2016.
 */

public class NavAdapter extends BaseAdapter {

    public static HashMap<CheckBox, String> sCheckBoxes = new HashMap<>();
    public static HashMap<String, Boolean> sIsChecked = new HashMap<>();
    private Context mContext;
    public NavAdapter(Context context) {
        mContext = context;
    }
    @Override
    public int getCount() {
        return MovieContainer.getTypes().size();
    }

    @Override
    public Object getItem(int i) {
        return MovieContainer.getTypes().get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.naw_drawer_item, parent, false);

        }
        View v = convertView;

        TextView tt1 = (TextView) v.findViewById(R.id.nav_title);
        CheckBox chb = (CheckBox) v.findViewById(R.id.checkBox);

        tt1.setText(MovieContainer.getTypes().get(position).getName());
        sCheckBoxes.put(chb, MovieContainer.getTypes().get(position).getName());
        if(!sIsChecked.containsKey(MovieContainer.getTypes().get(position).getName())) {
            sIsChecked.put(MovieContainer.getTypes().get(position).getName(), true);
        }

        chb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MainFragment.sInstance.setContent();
                sIsChecked.put(sCheckBoxes.get(buttonView), isChecked);
            }
        });


        chb.setChecked(sIsChecked.get(MovieContainer.getTypes().get(position).getName()));
        return v;
    }

}
