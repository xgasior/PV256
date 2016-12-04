package cz.muni.fi.pv256.movio2.a448273.Peristance;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;


import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 * Created by gasior on 04.12.2016.
 */

public class MovioContract {

    public static final String CONTENT_AUTHORITY = "cz.muni.fi.pv256.movio2.a448273";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MOVIE = "movie";
    public static final String PATH_TYPE = "type";

    public static final class TypeEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TYPE).build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_TYPE;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_TYPE;

        public static final String TABLE_NAME = "types";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_URL_PARAMETERS = "url_parameters";

        public static Uri buildWorkTimeUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;


        public static final String TABLE_NAME = "movies";

        public static final String COLUMN_ID ="id";
        public static final String COLUMN_RELEASE_DATA ="release_date";
        public static final String COLUMN_COVER_PATH ="cover_path";
        public static final String COLUMN_TITLE ="title";
        public static final String COLUMN_BACK_DROP ="back_drop";
        public static final String COLUMN_POPULARITY = "popularity";
        public static final String COLUMN_TYPE_ID = "type_id";

        private float mPopularity;

        public static Uri buildWorkTimeUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}