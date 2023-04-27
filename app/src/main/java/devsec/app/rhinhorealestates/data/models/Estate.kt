package devsec.app.rhinhorealestates.data.models

import com.google.gson.annotations.SerializedName
import java.util.HashMap

data class Estate(@SerializedName("_id")
                    val id: String,
                  @SerializedName("name")
                  val name: String,
                  @SerializedName("area")
                    val area: String,
                  @SerializedName("category")
                    val category: String,
                  @SerializedName("description")
                  val description: String,
                  @SerializedName("image")
                  val image: String,
                  @SerializedName("isCoastal")
                  val isCoastal: Boolean,
                  @SerializedName("surface")
                  val surface: String,
                  @SerializedName("username")
                  val  username :  String,
                  @SerializedName("userId")
                  val  userId :  String,
                  @SerializedName("comments")
                  val  comments :  ArrayList<String>,
                  @SerializedName("likes")
                  val  likes :  Number,
                  @SerializedName("dislikes")
                  val  dislikes :  Number,
                  @SerializedName("usersLiked")
                  val  usersLiked :  ArrayList<String>,
                  @SerializedName("usersDisliked")
                  val  usersDisliked :  ArrayList<String>,
                  @SerializedName("strRoom1")
                    val strRoom1: String,
                  @SerializedName("strRoom2")
                  val strRoom2: String,
                  @SerializedName("strRoom3")
                  val strRoom3: String,
                  @SerializedName("strRoom4")
                  val strRoom4: String,
                  @SerializedName("strRoom5")
                  val strRoom5: String,
                  @SerializedName("strRoom6")
                  val strRoom6: String,
                  @SerializedName("strRoom7")
                  val strRoom7: String,
                  @SerializedName("strRoom8")
                  val strRoom8: String,
                  @SerializedName("strRoom9")
                  val strRoom9: String,
                  @SerializedName("strRoom10")
                    val strRoom10: String,
                  @SerializedName("strRoom11")
                    val strRoom11: String,
                  @SerializedName("strRoom12")
                    val strRoom12: String,
                  @SerializedName("strRoom13")
                    val strRoom13: String,
                  @SerializedName("strRoom14")
                    val strRoom14: String,
                  @SerializedName("strRoom15")
                    val strRoom15: String,
                  @SerializedName("strRoom16")
                    val strRoom16: String,
                  @SerializedName("strRoom17")
                    val strRoom17: String,
                  @SerializedName("strRoom18")
                    val strRoom18: String,
                  @SerializedName("strRoom19")
                    val strRoom19: String,
                  @SerializedName("strRoom20")
                    val strRoom20: String,
                  @SerializedName("sizeFuse1")
                    val sizeFuse1: String,
                  @SerializedName("sizeFuse2")
                    val sizeFuse2: String,
                  @SerializedName("sizeFuse3")
                    val sizeFuse3: String,
                  @SerializedName("sizeFuse4")
                    val sizeFuse4: String,
                  @SerializedName("sizeFuse5")
                    val sizeFuse5: String,
                  @SerializedName("sizeFuse6")
                    val sizeFuse6: String,
                  @SerializedName("sizeFuse7")
                    val sizeFuse7: String,
                  @SerializedName("sizeFuse8")
                    val sizeFuse8: String,
                  @SerializedName("sizeFuse9")
                    val sizeFuse9: String,
                  @SerializedName("sizeFuse10")
                    val sizeFuse10: String,
                  @SerializedName("sizeFuse11")
                    val sizeFuse11: String,
                  @SerializedName("sizeFuse12")
                    val sizeFuse12: String,
                  @SerializedName("sizeFuse13")
                    val sizeFuse13: String,
                  @SerializedName("sizeFuse14")
                    val sizeFuse14: String,
                  @SerializedName("sizeFuse15")
                    val sizeFuse15: String,
                  @SerializedName("sizeFuse16")
                    val sizeFuse16: String,
                  @SerializedName("sizeFuse17")
                    val sizeFuse17: String,
                  @SerializedName("sizeFuse18")
                    val sizeFuse18: String,
                  @SerializedName("sizeFuse19")
                    val sizeFuse19: String,
                  @SerializedName("sizeFuse20")
                    val sizeFuse20: String,


                  )

    {
        constructor(
            name: String,
            area: String,
            category: String,
            description:String,
            image: String,
            isCoastal:Boolean,
            surface:String,
            username:String,
            userId:String,
            strRoom1: String,
            strRoom2: String,
            strRoom3: String,
            strRoom4: String,
            strRoom5: String,
            strRoom6: String,
            strRoom7: String,
            strRoom8: String,
            strRoom9: String,
            strRoom10: String,
            strRoom11: String,
            strRoom12: String,
            strRoom13: String,
            strRoom14: String,
            strRoom15: String,
            strRoom16: String,
            strRoom17: String,
            strRoom18: String,
            strRoom19: String,
            strRoom20: String,
            sizeFuse1: String,
            sizeFuse2: String,
            sizeFuse3: String,
            sizeFuse4: String,
            sizeFuse5: String,
            sizeFuse6: String,
            sizeFuse7: String,
            sizeFuse8: String,
            sizeFuse9: String,
            sizeFuse10: String,
            sizeFuse11: String,
            sizeFuse12: String,
            sizeFuse13: String,
            sizeFuse14: String,
            sizeFuse15: String,
            sizeFuse16: String,
            sizeFuse17: String,
            sizeFuse18: String,
            sizeFuse19: String,
            sizeFuse20: String) :
        this("",name, area, category, description ,image, isCoastal,surface,username , userId, ArrayList<String>(),0,0,ArrayList<String>(),ArrayList<String>(),strRoom1,strRoom2,strRoom3,strRoom4,strRoom5,strRoom6,strRoom7,strRoom8,strRoom9,strRoom10,strRoom11,strRoom12,strRoom13,strRoom14,strRoom15,strRoom16,strRoom17,strRoom18,strRoom19,strRoom20,sizeFuse1,sizeFuse2,sizeFuse3,sizeFuse4,sizeFuse5,sizeFuse6,sizeFuse7,sizeFuse8,sizeFuse9,sizeFuse10,sizeFuse11,sizeFuse12,sizeFuse13,sizeFuse14,sizeFuse15,sizeFuse16,sizeFuse17,sizeFuse18,sizeFuse19,sizeFuse20)

        constructor(name: String, area: String,category:String, description: String,image: String,isCoastal:Boolean,surface:String,strRoom1: String,
                    strRoom2: String,strRoom3: String,strRoom4: String,strRoom5: String,strRoom6: String,strRoom7: String,
                    strRoom8: String,strRoom9: String,strRoom10: String,strRoom11: String,strRoom12: String,strRoom13: String,
                    strRoom14: String,strRoom15: String,strRoom16: String,strRoom17: String,strRoom18: String,strRoom19: String,
                    strRoom20: String,sizeFuse1: String,sizeFuse2: String,sizeFuse3: String,sizeFuse4: String,sizeFuse5: String,
                    sizeFuse6: String,sizeFuse7: String,sizeFuse8: String,sizeFuse9: String,sizeFuse10: String,sizeFuse11: String,
                    sizeFuse12: String,sizeFuse13: String,sizeFuse14: String,sizeFuse15: String,sizeFuse16: String,sizeFuse17: String,
                    sizeFuse18: String,sizeFuse19: String,sizeFuse20: String) :
        this("",name,area,category, description, image, isCoastal,surface,"","",ArrayList<String>(),0,0,ArrayList<String>(),ArrayList<String>(),
            strRoom1,strRoom2,strRoom3,strRoom4,strRoom5,strRoom6,strRoom7,strRoom8,strRoom9,strRoom10,strRoom11,strRoom12,
            strRoom13,strRoom14,strRoom15,strRoom16,strRoom17,strRoom18,strRoom19,strRoom20,sizeFuse1,sizeFuse2,sizeFuse3,sizeFuse4,
            sizeFuse5,sizeFuse6,sizeFuse7,sizeFuse8,sizeFuse9,sizeFuse10,sizeFuse11,sizeFuse12,sizeFuse13,sizeFuse14,sizeFuse15,sizeFuse16,
            sizeFuse17,sizeFuse18,sizeFuse19,sizeFuse20)



    }

