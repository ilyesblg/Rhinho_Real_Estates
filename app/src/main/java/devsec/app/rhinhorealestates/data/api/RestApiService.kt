
package devsec.app.rhinhorealestates.api

import devsec.app.rhinhorealestates.data.api.CodeRequest
import devsec.app.rhinhorealestates.data.api.EmailRequest
import devsec.app.rhinhorealestates.data.models.*
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import devsec.app.rhinhorealestates.data.api.UserRequest
interface RestApiService {


    //*********************** Sign up/in ***********************//
    @Headers("Content-Type:application/json")
    @POST("login")
    fun loginUser(@Body info: UserRequest): Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @POST("users")
    fun registerUser(
        @Body info: User
    ): Call<ResponseBody>

    //*********************** User ***********************//
    @GET("users")
    fun getUsers(): Call<List<User>>

    @POST("users/resetpassword")
    fun verifcode(@Body code: CodeRequest): Call<Unit>

    @POST("users/resetpwd")
    fun generatecode(@Body email: EmailRequest): Call<Unit>

    @GET("users/{id}")
    fun getUser(@Path("id") id: String): Call<User>

    @Headers("Content-Type:application/json")
    @PATCH("users/{id}")
    fun updateUser(@Path("id") id: String, @Body user: User): Call<User>

    @DELETE("users/{id}")
    fun deleteUser(@Path("id") id: String): Call<ResponseBody>





    @Multipart
    @POST("uploadfile")
    fun postImage(
        @Part image: MultipartBody.Part,
    ): Call<ResponseBody>




    @Multipart
    @POST("uploadfile")
    fun uploadImage(
        @Part myFile: MultipartBody.Part
    ): Call<ResponseBody>




}

class RetrofitInstance {
    companion object {

        const val BASE_URL: String = "http://192.168.1.183:9090/api/"
     // const val BASE_URL: String = "http://192.168.0.11:9090/api/"


        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        val client: OkHttpClient = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
        }.build()
        fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}

