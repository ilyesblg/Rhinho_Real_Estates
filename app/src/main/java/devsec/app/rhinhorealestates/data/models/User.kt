package devsec.app.rhinhorealestates.data.models

data class User (
    val id: String,
    val username: String,
    val email: String,
    val password: String,
    val address: String,
    val phone: String,
    val image : String

) {

    constructor(username: String, email: String, password: String, address: String, phone: String , image: String ) : this("" , username, email , password,address,phone, image)

}
//    val  photo :  String