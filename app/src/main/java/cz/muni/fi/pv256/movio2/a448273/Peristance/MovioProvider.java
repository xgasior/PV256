package cz.muni.fi.pv256.movio2.a448273.Peristance;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import java.util.Arrays;

/**
 * Created by gasior on 04.12.2016.
 */

public class MovioProvider extends ContentProvider {

    private static final String TAG = MovioProvider.class.getSimpleName();

    private static final int TYPE = 100;
    private static final int TYPE_ID = 101;
    private static final int MOVIE = 200;
    private static final int MOVIE_ID = 201;
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MovioDbHelper mOpenHelper;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovioContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, MovioContract.PATH_TYPE, TYPE);
        matcher.addURI(authority, MovioContract.PATH_TYPE + "/#", TYPE_ID);

        matcher.addURI(authority, MovioContract.PATH_MOVIE, MOVIE);
        matcher.addURI(authority, MovioContract.PATH_MOVIE + "/#", MOVIE_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new MovioDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.d(TAG, Arrays.toString(selectionArgs));
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case TYPE_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovioContract.TypeEntry.TABLE_NAME,
                        projection,
                        MovioContract.TypeEntry.COLUMN_ID + " = '" + ContentUris.parseId(uri) + "'",
                        null,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case TYPE: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovioContract.TypeEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;

            }
            case MOVIE_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovioContract.MovieEntry.TABLE_NAME,
                        projection,
                        MovioContract.MovieEntry.COLUMN_ID + " = '" + ContentUris.parseId(uri) + "'",
                        null,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case MOVIE: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovioContract.MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;

            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case TYPE:
                return MovioContract.TypeEntry.CONTENT_TYPE;
            case TYPE_ID:
                return MovioContract.TypeEntry.CONTENT_ITEM_TYPE;
            case MOVIE:
                return MovioContract.MovieEntry.CONTENT_TYPE;
            case MOVIE_ID:
                return MovioContract.MovieEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        Log.d(TAG, values.toString());

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case TYPE: {
                long _id = db.insert(MovioContract.TypeEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = MovioContract.TypeEntry.buildWorkTimeUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case MOVIE: {
                long _id = db.insert(MovioContract.MovieEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = MovioContract.MovieEntry.buildWorkTimeUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        switch (match) {
            case TYPE:
                rowsDeleted = db.delete(MovioContract.TypeEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case MOVIE:
                rowsDeleted = db.delete(MovioContract.MovieEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (selection == null || rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case TYPE:
                rowsUpdated = db.update(MovioContract.TypeEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case MOVIE:
                rowsUpdated = db.update(MovioContract.MovieEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
}