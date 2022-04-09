package com.example.rxandroid.square;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface GitHubServiceApi {
    @GET("repos/{owner}/{repo}/contributors")
    Call<List<Contributor>> getCallContributors(@Path("owner") String owner,
                                                @Path("repo") String repo);

    @GET("repos/{owner}/{repo}/contributors")
    Observable<List<Contributor>> getObContributors(@Path("owner") String owner,
                                                      @Path("repo") String repo);

    @Headers({"Accpet: application/vnd.github.v3.full+json"})
    Call<List<Contributor>> getCallContributorsWithHeader(@Path("owner") String owner,
                                                @Path("repo") String repo);

}
