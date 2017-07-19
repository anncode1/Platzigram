package com.platzi.platzigram.api.adapter;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.platzi.platzigram.api.services.PlatzigramFirebaseService;

import java.io.IOException;
import java.util.Arrays;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by anahisalgado on 13/07/17.
 */

public class PlatzigramFirebaseAdapter {

    private static final String FIREBASE_DATABASE_URL = "https://platzigram-427f2.firebaseio.com/";

    public PlatzigramFirebaseService getFirebaseService(final Context context) {

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request request = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + getToken(context))
                        .build()
                        ;
                return chain.proceed(request);
            }
        }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(FIREBASE_DATABASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                ;

        return retrofit.create(PlatzigramFirebaseService.class);
    }

    private String getToken(Context context) throws IOException {

        GoogleCredential googleCredential
                = GoogleCredential.fromStream(context.getAssets().open("platzigram-427f2-firebase-adminsdk-pcfyv-076afb26e3.json"));
        GoogleCredential scoped = googleCredential.createScoped(
                Arrays.asList(
                        "https://www.googleapis.com/auth/firebase.database",
                        "https://www.googleapis.com/auth/userinfo.email"
                )
        );

        scoped.refreshToken();
        Log.w("RETROFIT", "TOKEN: " + scoped.getAccessToken());
        return scoped.getAccessToken();
    }


}
