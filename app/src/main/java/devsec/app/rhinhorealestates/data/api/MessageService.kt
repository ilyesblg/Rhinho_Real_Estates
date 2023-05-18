import devsec.app.rhinhorealestates.data.api.MessageRequest
import devsec.app.rhinhorealestates.data.api.MessageResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface MessageService {

    @POST("/api/message/")
    suspend fun getMessages(
       @Body message: MessageRequest
    ): List<MessageResponse>
}
