package devsec.app.rhinhorealestates.data.models

import com.google.gson.annotations.SerializedName

data class Room(
    @SerializedName("_id")
    val id: String,
    @SerializedName("strRoom")
    val name: String,
)