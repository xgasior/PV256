package cz.muni.fi.pv256.movio2.a448273.Peristance;

/**
 * Created by gasior on 04.12.2016.
 */

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cz.muni.fi.pv256.movio2.a448273.Entity.Movie;
import cz.muni.fi.pv256.movio2.a448273.Entity.Type;

import static cz.muni.fi.pv256.movio2.a448273.Peristance.MovioContract.MovieEntry;
import static cz.muni.fi.pv256.movio2.a448273.Peristance.MovioContract.TypeEntry;

public class MovioManager {

    public static final int COLUMN_TYPE_ID = 0;
    public static final int COLUMN_NAME = 1;
    public static final int COLUMN_URL_PARAMETERS = 2;

    private static final String[] TYPE_COLUMNS = {
            TypeEntry.COLUMN_ID,
            TypeEntry.COLUMN_NAME,
            TypeEntry.COLUMN_URL_PARAMETERS
    };

    public static final int COLUMN_MOVIE_ID = 0;
    public static final int COLUMN_RELEASE_DATA = 1;
    public static final int COLUMN_COVER_PATH = 2;
    public static final int COLUMN_TITLE = 3;
    public static final int COLUMN_BACK_DROP = 4;
    public static final int COLUMN_POPULARITY = 5;
    public static final int COLUMN_TYPE_FOREIGN_ID = 5;

    private static final String[] MOVIE_COLUMNS = {
            MovieEntry.COLUMN_ID,
            MovieEntry.COLUMN_RELEASE_DATA,
            MovieEntry.COLUMN_COVER_PATH,
            MovieEntry.COLUMN_TITLE,
            MovieEntry.COLUMN_BACK_DROP,
            MovieEntry.COLUMN_POPULARITY,
            MovieEntry.COLUMN_TYPE_ID
    };

    private static final String LOCAL_DATE_FORMAT = "yyyyMMdd";

    private static final String WHERE_TYPE_ID = TypeEntry.COLUMN_ID + " = ?";
    private static final String WHERE_MOVIE_ID = MovieEntry.COLUMN_ID + " = ?";
    private static final String WHERE_MOVIE_TYPE_ID = MovieEntry.COLUMN_TYPE_ID + " = ?";


    private Context mContext;

    public MovioManager(Context context) {
        mContext = context.getApplicationContext();
    }

    public void createType(Type type) {

        if (type == null) {
            throw new NullPointerException("type == null");
        }
        if (type.getId() == null) {
            throw new IllegalStateException("type id cannot be null");
        }
        if (type.getName() == null) {
            throw new IllegalStateException("type name cannot be null");
        }
        if (type.getUrlParameters() == null) {
            throw new IllegalStateException("type url parameters cannot be null");
        }

        type.setId(ContentUris.parseId(mContext.getContentResolver().insert(TypeEntry.CONTENT_URI, prepareTypeValues(type))));
    }

    public void createMovie(Movie movie) {

        if (movie == null) {
            throw new NullPointerException("movie == null");
        }
        if (movie.getId() == null) {
            throw new IllegalStateException("movie id cannot be null");
        }
        if (movie.getBackdrop() == null) {
            throw new IllegalStateException("movie backdrop cannot be null");
        }
        if (movie.getCoverPath() == null) {
            throw new IllegalStateException("movie cover path cannot be null");
        }
        if (movie.getTitle() == null) {
            throw new IllegalStateException("movie title cannot be null");
        }
        if (! (0 < movie.getReleaseDate()) ) {
            throw new IllegalStateException("movie release date not set");
        }
        if (! (0 < movie.getTypeId()) ) {
            throw new IllegalStateException("type id  not set");
        }


        movie.setId(ContentUris.parseId(mContext.getContentResolver().insert(MovieEntry.CONTENT_URI, prepareMovieValues(movie))));
    }

    public ArrayList<Type> getAllTypes() {
        Cursor cursor = mContext.getContentResolver().query(TypeEntry.CONTENT_URI, TYPE_COLUMNS, "", new String[]{}, null);

        if (cursor != null && cursor.moveToFirst()) {
            ArrayList<Type> movies = new ArrayList<>(cursor.getCount());
            try {
                while (!cursor.isAfterLast()) {
                    movies.add(getType(cursor));
                    cursor.moveToNext();
                }
            } finally {
                cursor.close();
            }
            return movies;
        }

        return new ArrayList<>();
    }

    public ArrayList<Type> getTypeById(Long typeId) {
        if (typeId == null) {
            throw new NullPointerException("typeId == null");
        }

        Cursor cursor = mContext.getContentResolver().query(TypeEntry.CONTENT_URI, TYPE_COLUMNS, WHERE_TYPE_ID, new String[]{typeId.toString()}, null);

        if (cursor != null && cursor.moveToFirst()) {
            ArrayList<Type> movies = new ArrayList<>(cursor.getCount());
            try {
                while (!cursor.isAfterLast()) {
                    movies.add(getType(cursor));
                    cursor.moveToNext();
                }
            } finally {
                cursor.close();
            }
            return movies;
        }

        return new ArrayList<>();
    }

    public ArrayList<Movie> getAllMovies() {
        Cursor cursor = mContext.getContentResolver().query(MovieEntry.CONTENT_URI, MOVIE_COLUMNS, "", new String[]{}, null);

        if (cursor != null && cursor.moveToFirst()) {
            ArrayList<Movie> movies = new ArrayList<>(cursor.getCount());
            try {
                while (!cursor.isAfterLast()) {
                    movies.add(getMovie(cursor));
                    cursor.moveToNext();
                }
            } finally {
                cursor.close();
            }
            return movies;
        }

        return new ArrayList<>();
    }
    public ArrayList<Movie> getMovieById(Long movieId) {
        Cursor cursor = mContext.getContentResolver().query(MovieEntry.CONTENT_URI, MOVIE_COLUMNS, WHERE_MOVIE_ID, new String[]{ movieId.toString()}, null);

        if (cursor != null && cursor.moveToFirst()) {
            ArrayList<Movie> movies = new ArrayList<>(cursor.getCount());
            try {
                while (!cursor.isAfterLast()) {
                    movies.add(getMovie(cursor));
                    cursor.moveToNext();
                }
            } finally {
                cursor.close();
            }
            return movies;
        }

        return new ArrayList<>();
    }

    public ArrayList<Movie> getMoviesOfType(Long typeId) {
        if (typeId == null) {
            throw new NullPointerException("typeId == null");
        }

        Cursor cursor = mContext.getContentResolver().query(MovieEntry.CONTENT_URI, MOVIE_COLUMNS, WHERE_MOVIE_TYPE_ID, new String[]{typeId.toString()}, null);

        if (cursor != null && cursor.moveToFirst()) {
            ArrayList<Movie> movies = new ArrayList<>(cursor.getCount());
            try {
                while (!cursor.isAfterLast()) {
                    movies.add(getMovie(cursor));
                    cursor.moveToNext();
                }
            } finally {
                cursor.close();
            }
            return movies;
        }

        return new ArrayList<>();
    }

    public void updateType(Type type) {

        if (type == null) {
            throw new NullPointerException("type == null");
        }
        if (type.getId() == null) {
            throw new IllegalStateException("type id cannot be null");
        }
        if (type.getName() == null) {
            throw new IllegalStateException("type name cannot be null");
        }
        if (type.getUrlParameters() == null) {
            throw new IllegalStateException("type url parameters cannot be null");
        }

        mContext.getContentResolver().update(TypeEntry.CONTENT_URI, prepareTypeValues(type), WHERE_TYPE_ID, new String[]{String.valueOf(type.getId())});
    }

    public void updateMovie(Movie movie) {
        if (movie == null) {
            throw new NullPointerException("movie == null");
        }
        if (movie.getId() == null) {
            throw new IllegalStateException("movie id cannot be null");
        }
        if (movie.getBackdrop() == null) {
            throw new IllegalStateException("movie backdrop cannot be null");
        }
        if (movie.getCoverPath() == null) {
            throw new IllegalStateException("movie cover path cannot be null");
        }
        if (movie.getTitle() == null) {
            throw new IllegalStateException("movie title cannot be null");
        }
        if (! (0 < movie.getReleaseDate()) ) {
            throw new IllegalStateException("movie release date not set");
        }
        if (! (0 < movie.getTypeId()) ) {
            throw new IllegalStateException("type id  not set");
        }

        mContext.getContentResolver().update(MovieEntry.CONTENT_URI, prepareMovieValues(movie), WHERE_MOVIE_ID, new String[]{String.valueOf(movie.getId())});
    }

    public void deleteType(Type type) {
        if (type == null) {
            throw new NullPointerException("type == null");
        }
        if (type.getId() == null) {
            throw new IllegalStateException("type id cannot be null");
        }

        mContext.getContentResolver().delete(TypeEntry.CONTENT_URI, WHERE_TYPE_ID, new String[]{String.valueOf(type.getId())});
    }

    public void deleteMovie(Movie movie) {

        if (movie == null) {
            throw new NullPointerException("movie == null");
        }
        if (movie.getId() == null) {
            throw new IllegalStateException("movie id cannot be null");
        }

        mContext.getContentResolver().delete(MovieEntry.CONTENT_URI, WHERE_MOVIE_ID, new String[]{String.valueOf(movie.getId())});
    }

    private ContentValues prepareTypeValues(Type type) {
        ContentValues values = new ContentValues();
        values.put(TypeEntry.COLUMN_ID, type.getId());
        values.put(TypeEntry.COLUMN_NAME, type.getName());
        values.put(TypeEntry.COLUMN_URL_PARAMETERS, type.getUrlParameters());
        return values;
    }

    private Type getType(Cursor cursor) {
        Type type = new Type();
        type.setId(cursor.getLong(COLUMN_TYPE_ID));
        type.setName(cursor.getColumnName(COLUMN_NAME));
        type.setUrlParameters(cursor.getColumnName(COLUMN_URL_PARAMETERS));
        return type;
    }
    private ContentValues prepareMovieValues(Movie movie) {
        ContentValues values = new ContentValues();
        values.put(MovieEntry.COLUMN_ID, movie.getId());
        values.put(MovieEntry.COLUMN_BACK_DROP, movie.getBackdrop());
        values.put(MovieEntry.COLUMN_COVER_PATH, movie.getCoverPath());
        values.put(MovieEntry.COLUMN_POPULARITY, movie.getPopularity());
        values.put(MovieEntry.COLUMN_TITLE, movie.getTitle());
        values.put(MovieEntry.COLUMN_RELEASE_DATA, movie.getReleaseDate());
        values.put(MovieEntry.COLUMN_TYPE_ID, movie.getTypeId());
        return values;
    }
    private Movie getMovie(Cursor cursor) {
        Movie movie = new Movie();
        movie.setId(cursor.getLong(COLUMN_MOVIE_ID));
        movie.setCoverPath(cursor.getColumnName(COLUMN_COVER_PATH));
        movie.setTitle(cursor.getColumnName(COLUMN_TITLE));
        movie.setReleaseDate(cursor.getLong(COLUMN_RELEASE_DATA));
        movie.setBackdrop(cursor.getColumnName(COLUMN_BACK_DROP));
        movie.setPopularity(cursor.getFloat(COLUMN_POPULARITY));
        movie.setTypeId(cursor.getLong(COLUMN_TYPE_FOREIGN_ID));
        return movie;
    }
}