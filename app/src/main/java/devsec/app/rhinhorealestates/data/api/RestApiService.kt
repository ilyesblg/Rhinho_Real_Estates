package devsec.app.rhinhorealestates.api

import devsec.app.rhinhorealestates.data.api.*
import devsec.app.rhinhorealestates.data.models.*
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

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
    fun verifcode(@Body code: CodeRequest): Call<Unit>   //verification code

    @POST("users/resetpwd")
    fun generatecode(@Body email: EmailRequest): Call<Unit>       // envoyer code

    @POST("users/changepwd")
    fun changepass(@Body password: changepass): Call<Unit>       // changer pass

    @GET("users/{id}")
    fun getUser(@Path("id") id: String): Call<User>

    @Headers("Content-Type:application/json")
    @PATCH("users/{id}")
    fun updateUser(@Path("id") id: String, @Body user: User): Call<User>

    @DELETE("users/{id}")
    fun deleteUser(@Path("id") id: String): Call<ResponseBody>

    @GET("estates")
    fun getFoodsList(): Call<List<Estate>>

    @GET("estates/{id}")
    fun getFoodById(@Path("id") id: String): Call<Estate>

    @GET("room")
    fun getRoomsList(): Call<List<Room>>

    @Multipart
    @POST("uploadfile")
    fun postImage(
        @Part image: MultipartBody.Part,
    ): Call<ResponseBody>


    @Multipart
    @POST("users/upload/{id}")
    fun uploadImage(
        @Path("id") id: String,
        @Part image: MultipartBody.Part
    ): Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @POST("estates")
    fun addEstate(
        @Body Estateinfo: Estate
    ): Call<ResponseBody>

    @POST("message")
    fun message(@Body message: MessageRequest): Call<Unit>

}

class RetrofitInstance {
    companion object {

        const val BASE_URL: String = "http://172.16.7.176:9090/api/"
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

