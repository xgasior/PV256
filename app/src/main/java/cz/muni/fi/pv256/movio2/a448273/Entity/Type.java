package cz.muni.fi.pv256.movio2.a448273.Entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by gasior on 24.10.2016.
 */

public class Type implements Parcelable {

    private ArrayList<Movie> mMovies;
    private String mName;

    public Type(String name, ArrayList<Movie> movies){
        mName = name;
        mMovies = movies;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeList(mMovies);
    }

    public Type(Parcel in) {
        mName = in.readString();
        mMovies = in.readArrayList(null);
    }

    public static final Parcelable.Creator<Type> CREATOR = new Parcelable.Creator<Type>() {
        @Override
        public Type createFromParcel(Parcel source) {
            return new Type(source);
        }

        @Override
        public Type[] newArray(int size) {
            return new Type[size];
        }
    };

    public ArrayList<Movie> getMovies() {
        return mMovies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        mMovies = movies;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
