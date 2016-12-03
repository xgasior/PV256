package cz.muni.fi.pv256.movio2.a448273.Service;

import cz.muni.fi.pv256.movio2.a448273.Api.ResponseRoot;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by gasior on 29.11.2016.
 */

public interface MovioClient {
    @GET
    Call<ResponseRoot> GetMovies(@Url String url);
}
