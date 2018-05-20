package me.pedroguimaraes.xkcd.api

import io.reactivex.Observable
import me.pedroguimaraes.xkcd.model.Comic
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface XkcdApiInterface {

    @GET("info.0.json")
    fun getLatestComic(): Observable<Comic>

    @GET("/{number}/info.0.json")
    fun getComic(@Path("number") number: Int): Observable<Comic>


    companion object {
        fun create(): XkcdApiInterface {

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://xkcd.com/")
                    .build()

            return retrofit.create(XkcdApiInterface::class.java)
        }
    }
}