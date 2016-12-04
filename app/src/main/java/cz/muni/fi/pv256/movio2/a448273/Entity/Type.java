package cz.muni.fi.pv256.movio2.a448273.Entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by gasior on 24.10.2016.
 */

public class Type implements Parcelable {


    private Long mId;
    private String mName;
    private String mUrlParameters;

    private ArrayList<Movie> mMovies;

    public Type() {
        mMovies = new ArrayList<>();
    }

    public Type(String name, ArrayList<Movie> movies, String urlParameters){
        mName = name;
        mMovies = movies;
        mUrlParameters = urlParameters;
    }

    public Type(Long id, ArrayList<Movie> movies, String urlParameters, String name) {
        mId = id;
        mMovies = movies;
        mUrlParameters = urlParameters;
        mName = name;
    }

    public Type(Long id, String name, String urlParameters) {
        mId = id;
        mName = name;
        mUrlParameters = urlParameters;
        mMovies = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Type type = (Type) o;

        return mName.equals(type.mName);

    }

    @Override
    public int hashCode() {
        return mName.hashCode();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeString(mName);
        dest.writeString(mUrlParameters);
        dest.writeList(mMovies);
    }

    public Type(Parcel in) {
        mId = in.readLong();
        mName = in.readString();
        mUrlParameters = in.readString();
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

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

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

    public String getUrlParameters() {
        return mUrlParameters;
    }

    public void setUrlParameters(String urlParameters) {
        mUrlParameters = urlParameters;
    }
}
