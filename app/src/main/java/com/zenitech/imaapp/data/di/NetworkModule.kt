package com.zenitech.imaapp.data.di

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GetTokenResult
import com.zenitech.imaapp.data.network.api.DeviceApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideBaseUrl(): String = "https://ima-dev.zenitech.co.uk/api/"

    private var okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(FirebaseUserIdTokenInterceptor())
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideDeviceApiService(retrofit: Retrofit): DeviceApi = retrofit.create(DeviceApi::class.java)

}

class FirebaseUserIdTokenInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()

        return try {
            val user = FirebaseAuth.getInstance().currentUser ?: throw IOException("User is not logged in.")

            val task: Task<GetTokenResult> = user.getIdToken(false)
            val tokenResult = Tasks.await(task)
            val idToken = tokenResult.token

            if (idToken.isNullOrEmpty()) {
                throw IOException("idToken is null or empty")
            }

            val modifiedRequest: Request = request.newBuilder()
                .addHeader("Authorization", "Bearer $idToken")
                .build()

            Log.d("Request", "URL: ${modifiedRequest.url()}")
            Log.d("Request", "Headers: ${modifiedRequest.headers()}")
            Log.d("Request", "Method: ${modifiedRequest.method()}")

            chain.proceed(modifiedRequest)
        } catch (e: Exception) {
            Log.e("FirebaseAuthInterceptor", "Exception while intercepting request: ${e.message}", e)
            throw IOException(e.message)
        }
    }
}
