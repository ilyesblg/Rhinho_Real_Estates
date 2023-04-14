package devsec.app.rhinhorealestates.data.models

data  class User (
    val  id :  String ,
    val  username :  String ,
    val  email :  String ,
    val adress : String,
    val  password :  String ,
    val  phone :  String ) {

    constructor(id: String) : this(id,"","","","","")
    constructor(username: String, password: String, email: String,adress : String, phone: String) : this("", username, email, password,adress, phone)
   // constructor(id: String) : this(id,"","","","","")

}
//    val  photo :  String