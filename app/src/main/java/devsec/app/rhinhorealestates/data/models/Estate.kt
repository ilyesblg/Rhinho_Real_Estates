package devsec.app.rhinhorealestates.data.models

import com.google.gson.annotations.SerializedName
import java.util.HashMap

data class Estate(@SerializedName("_id")
                    val id: String,
                  @SerializedName("name")
                  val name: String,
                  @SerializedName("strArea")
                    val area: String,
                  @SerializedName("strCategory")
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
                  @SerializedName("strRoom2")
                    val strRoom2: String,
                  @SerializedName("strRoom20")
                    val strRoom20: String,
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
                  @SerializedName("strSize1")
                    val strSize1: String,
                  @SerializedName("strSize2")
                    val strSize2: String,
                  @SerializedName("strSize3")
                    val strSize3: String,
                  @SerializedName("strSize4")
                    val strSize4: String,
                  @SerializedName("strSize5")
                    val strSize5: String,
                  @SerializedName("strSize6")
                    val strSize6: String,
                  @SerializedName("strSize7")
                    val strSize7: String,
                  @SerializedName("strSize8")
                    val strSize8: String,
                  @SerializedName("strSize9")
                    val strSize9: String,
                  @SerializedName("strSize10")
                    val strSize10: String,
                  @SerializedName("strSize11")
                    val strSize11: String,
                  @SerializedName("strSize12")
                    val strSize12: String,
                  @SerializedName("strSize13")
                    val strSize13: String,
                  @SerializedName("strSize14")
                    val strSize14: String,
                  @SerializedName("strSize15")
                    val strSize15: String,
                  @SerializedName("strSize16")
                    val strSize16: String,
                  @SerializedName("strSize17")
                    val strSize17: String,
                  @SerializedName("strSize18")
                    val strSize18: String,
                  @SerializedName("strSize19")
                    val strSize19: String,
                  @SerializedName("strSize20")
                    val strSize20: String,


                  )

    {
        constructor(
            area: String,
            name: String,
            category: String,
            description:String, image: String,
            isCoastal:Boolean,
            surface:String,
            username:String,
            userId:String, strRoom1: String,
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
            strSize1: String,
            strSize2: String,
            strSize3: String,
            strSize4: String,
            strSize5: String,
            strSize6: String,
            strSize7: String,
            strSize8: String,
            strSize9: String,
            strSize10: String,
            strSize11: String,
            strSize12: String,
            strSize13: String,
            strSize14: String,
            strSize15: String,
            strSize16: String,
            strSize17: String,
            strSize18: String,
            strSize19: String,
            strSize20: String) :
        this("",name, area, category, description ,image, isCoastal,surface,username , userId, ArrayList<String>(),0,0,ArrayList<String>(),ArrayList<String>(),strRoom1,strRoom2,strRoom3,strRoom4,strRoom5,strRoom6,strRoom7,strRoom8,strRoom9,strRoom10,strRoom11,strRoom12,strRoom13,strRoom14,strRoom15,strRoom16,strRoom17,strRoom18,strRoom19,strRoom20,strSize1,strSize2,strSize3,strSize4,strSize5,strSize6,strSize7,strSize8,strSize9,strSize10,strSize11,strSize12,strSize13,strSize14,strSize15,strSize16,strSize17,strSize18,strSize19,strSize20)

        constructor(name: String, area: String,category:String, description: String,image: String,isCoastal:Boolean,surface:String,strRoom1: String,
                    strRoom2: String,strRoom3: String,strRoom4: String,strRoom5: String,strRoom6: String,strRoom7: String,
                    strRoom8: String,strRoom9: String,strRoom10: String,strRoom11: String,strRoom12: String,strRoom13: String,
                    strRoom14: String,strRoom15: String,strRoom16: String,strRoom17: String,strRoom18: String,strRoom19: String,
                    strRoom20: String,strSize1: String,strSize2: String,strSize3: String,strSize4: String,strSize5: String,
                    strSize6: String,strSize7: String,strSize8: String,strSize9: String,strSize10: String,strSize11: String,
                    strSize12: String,strSize13: String,strSize14: String,strSize15: String,strSize16: String,strSize17: String,
                    strSize18: String,strSize19: String,strSize20: String) :
        this("",name,area,category, description, image, isCoastal,surface,"","",ArrayList<String>(),0,0,ArrayList<String>(),ArrayList<String>(),
            strRoom1,strRoom2,strRoom3,strRoom4,strRoom5,strRoom6,strRoom7,strRoom8,strRoom9,strRoom10,strRoom11,strRoom12,
            strRoom13,strRoom14,strRoom15,strRoom16,strRoom17,strRoom18,strRoom19,strRoom20,strSize1,strSize2,strSize3,strSize4,
            strSize5,strSize6,strSize7,strSize8,strSize9,strSize10,strSize11,strSize12,strSize13,strSize14,strSize15,strSize16,
            strSize17,strSize18,strSize19,strSize20)



    }

