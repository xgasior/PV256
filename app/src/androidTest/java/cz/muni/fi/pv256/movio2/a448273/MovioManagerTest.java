package cz.muni.fi.pv256.movio2.a448273;

import android.test.AndroidTestCase;

import org.joda.time.DateTime;
import org.junit.Assert;

import java.util.List;

import cz.muni.fi.pv256.movio2.a448273.Entity.Movie;
import cz.muni.fi.pv256.movio2.a448273.Entity.Type;
import cz.muni.fi.pv256.movio2.a448273.Peristance.MovioManager;

import static cz.muni.fi.pv256.movio2.a448273.Peristance.MovioContract.MovieEntry;
import static cz.muni.fi.pv256.movio2.a448273.Peristance.MovioContract.TypeEntry;

/**
 * Created by gasior on 04.12.2016.
 */

public class MovioManagerTest extends AndroidTestCase {

    private static final String TAG = MovioManagerTest.class.getSimpleName();

    private MovioManager mManager;

    @Override
    protected void setUp() throws Exception {
        mManager = new MovioManager(mContext);
    }

    @Override
    public void tearDown() throws Exception {
        mContext.getContentResolver().delete(
                TypeEntry.CONTENT_URI,
                null,
                null
        );
        mContext.getContentResolver().delete(
                MovieEntry.CONTENT_URI,
                null,
                null
        );
    }

    public void testCreateType() throws Exception {
        Type type = new Type(1l, "Typ", "asda");
        mManager.createType(type);

        List<Type> result = mManager.getTypeById(type.getId());

        assertTrue(result.size() == 1);
        assertEquals(type.getName(), result.get(0).getName());
    }

    public void testUpdateType() throws Exception {
        Type type = new Type(1l, "Typ", "asda");
        mManager.createType(type);

        type.setName("asfhgas");
        mManager.updateType(type);

        List<Type> result = mManager.getTypeById(type.getId());

        assertTrue(result.size() == 1);
        assertEquals(type.getName(), result.get(0).getName());
    }


    public void testDeleteType() throws Exception {
        Type type = new Type(1l, "Typ", "asda");
        mManager.createType(type);


        List<Type> result = mManager.getTypeById(type.getId());
        assertTrue(result.size() == 1);

        mManager.deleteType(type);

        List<Type> result2 = mManager.getTypeById(type.getId());
        assertTrue(result2.size() == 0);
    }

    public void testFindAllTypes() {

        Type type = new Type(1l, "Typ", "asda");
        mManager.createType(type);

        Type type2 = new Type(2l, "Typ2", "asdat");
        mManager.createType(type2);

        List<Type> result = mManager.getAllTypes();
        assertTrue(result.size() == 2);

    }
    public void testFindTypeByIdCheckMoviesNotEmpty() {
        Type type = new Type(1l, "Typ", "asda");
        mManager.createType(type);

        Type type2 = new Type(2l, "Typ", "asda");
        mManager.createType(type2);

        Movie movie = new Movie(1l, DateTime.now().getMillis(), "Asfsg", "ASDg", "ASfg", 6.9f, 1l);
        mManager.createMovie(movie);

        Movie movie2 = new Movie(2l, DateTime.now().getMillis(), "Asfsg", "ASDg", "ASfg", 6.9f, 2l);
        mManager.createMovie(movie2);

        List<Type> result1 = mManager.getTypeById(type.getId());
        List<Type> result2 = mManager.getTypeById(type.getId());


        Assert.assertTrue(result1.get(0).getMovies().size() == 1);
        Assert.assertTrue(result2.get(0).getMovies().size() == 1);
    }


    public void testFindMoviesByTypeId() throws Exception {
        Type type = new Type(1l, "Typ", "asda");
        mManager.createType(type);

        Type type2 = new Type(2l, "Typ", "asda");
        mManager.createType(type2);

        Movie movie = new Movie(1l, DateTime.now().getMillis(), "Asfsg", "ASDg", "ASfg", 6.9f, 1l);
        mManager.createMovie(movie);

        Movie movie2 = new Movie(2l, DateTime.now().getMillis(), "Asfsg", "ASDg", "ASfg", 6.9f, 2l);
        mManager.createMovie(movie2);

        List<Movie> result = mManager.getMoviesOfType(type.getId());

        assertTrue(result.size() == 1);
        assertTrue(result.get(0) instanceof Movie);
        assertEquals(movie.getTitle(), result.get(0).getTitle());
    }


    public void testCreateMovie() throws Exception {
        Type type = new Type(1l, "Typ", "asda");
        mManager.createType(type);

        Movie movie = new Movie(1l, DateTime.now().getMillis(), "Asfsg", "ASDg", "ASfg", 6.9f, 1l);
        mManager.createMovie(movie);

        List<Movie> result = mManager.getMovieById(movie.getId());

        assertTrue(result.size() == 1);
        assertEquals(movie.getTitle(), result.get(0).getTitle());
    }

    public void testUpdateMovie() throws Exception {
        Type type = new Type(1l, "Typ", "asda");
        mManager.createType(type);

        Movie movie = new Movie(1l, DateTime.now().getMillis(), "Asfsg", "ASDg", "ASfg", 6.9f, 1l);
        mManager.createMovie(movie);

        movie.setTitle("Za co paneboze za co");
        mManager.updateMovie(movie);

        List<Movie> result = mManager.getMovieById(movie.getId());

        assertTrue(result.size() == 1);
        assertEquals(movie.getTitle(), result.get(0).getTitle());
    }


    public void testDeleteMovie() throws Exception {
        Type type = new Type(1l, "Typ", "asda");
        mManager.createType(type);

        Movie movie = new Movie(1l, DateTime.now().getMillis(), "Asfsg", "ASDg", "ASfg", 6.9f, 1l);
        mManager.createMovie(movie);

        List<Movie> result = mManager.getMovieById(movie.getId());
        assertTrue(result.size() == 1);

        mManager.deleteMovie(movie);

        List<Movie> result2 = mManager.getMovieById(movie.getId());
        assertTrue(result2.size() == 0);
    }
}