package com.example.demo_app


import com.example.demo_app.BusinessesModule.businessesData
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkModule {



        @GET("businesses/search")
        suspend fun getLocation(@Query("location")location:String,@Query("terms")terms:String,@Query("sort_by")sort_by:String,@Query("radius")radius:Int): retrofit2.Response<businessesData>
        companion object{
            var retrofitApiI: NetworkModule?=null;
            fun getInstance(): NetworkModule {
                if(retrofitApiI==null){
                    var headerInterceptor=HeaderInterceptor();
                    var httpClient=OkHttpClient.Builder().addInterceptor(headerInterceptor).build();
                    val retrofit= Retrofit.Builder().baseUrl("https://api.yelp.com/v3/").client(httpClient). addConverterFactory(
                        GsonConverterFactory.create())
                        .build();
                    retrofitApiI=retrofit.create(NetworkModule::class.java);

                }
                return retrofitApiI!!;

            }
        }



}


