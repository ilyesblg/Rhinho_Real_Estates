package devsec.app.rhinhorealestates.api

import com.google.gson.Gson
import devsec.app.rhinhorealestates.data.api.*
import devsec.app.rhinhorealestates.data.models.*
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface RestApiService {

    data class CheckLikeResponse(val liked: Boolean)
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

    @POST("{id}")
    fun getUser1(@Path("id") id: String): Call<User>

    @Headers("Content-Type:application/json")
    @PATCH("users/{id}")
    fun updateUser(@Path("id") id: String, @Body user: User): Call<User>

    @DELETE("users/{id}")
    fun deleteUser(@Path("id") id: String): Call<ResponseBody>

    @GET("estates")
    fun getFoodsList(): Call<List<Estate>>

    @POST("estates/getuserLiked/{id}")
    fun getLikedList(@Path("id") id: String): Call<List<Estate>>

    @POST("estates/getuserestates/{username}")
    fun getUserList(@Path("username") username: String): Call<List<Estate>>

    @POST("estates/{estateid}/checklike/{userid}")
    fun checklike(@Path("estateid") idEstate: String,@Path("userid") idUser: String): Call<Boolean>

    @GET("estates/{id}")
    fun getFoodById(@Path("id") id: String): Call<Estate>

    @GET("room")
    fun getRoomsList(): Call<List<Room>>

    @Multipart
    @POST("uploadfile")
    fun postImage(
        @Part image: MultipartBody.Part,
    ): Call<ResponseBody>

    @PATCH("/api/estates/{objectId}/like/{userId}")
    fun like(@Path("objectId") objectId: String, @Path("userId") userId: String): Call<ResponseBody>
    @PATCH("/api/estates/{objectId}/dislike/{userId}")
    fun dislike(@Path("objectId") objectId: String, @Path("userId") userId: String): Call<ResponseBody>

    @Multipart
    @POST("/api/users/upload/{id}")
    fun uploadImage(
        @Path("id") id: String,
        @Part image: MultipartBody.Part
    ): Call<ResponseBody>

    @Multipart
    @POST("/api/estates/uploadimages/{id}")
    fun uploadImageEstate(
        @Path("id") id: String,
        @Part image: MultipartBody.Part
    ): Call<ResponseBody>
    @Multipart
    @POST("/api/estates/uploadimages1/{id}")
    fun uploadImageEstate1(
        @Path("id") id: String,
        @Part image: MultipartBody.Part
    ): Call<ResponseBody>
    @Multipart
    @POST("/api/estates/uploadimages2/{id}")
    fun uploadImageEstate2(
        @Path("id") id: String,
        @Part image: MultipartBody.Part
    ): Call<ResponseBody>
    @Multipart
    @POST("/api/estates/uploadimages3/{id}")
    fun uploadImageEstate3(
        @Path("id") id: String,
        @Part image: MultipartBody.Part
    ): Call<ResponseBody>
    @Multipart
    @POST("/api/estates/uploadimages4/{id}")
    fun uploadImageEstate4(
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

    @GET("/api/message/")
    suspend fun getMessages(
        @Query("from") from: String,
        @Query("to") to: String
    ): List<MessageResponse>

    @POST("users/image/{id}")
    fun getimage(@Path("id") id: String): Call<User>

}

class RetrofitInstance {
    companion object {

        const val BASE_URL: String = "http://192.168.1.111:9090/api/"
     // const val BASE_URL: String = "http://192.168.0.11:9090/api/"


        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()

        fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}

