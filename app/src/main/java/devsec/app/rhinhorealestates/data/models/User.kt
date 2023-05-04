package devsec.app.rhinhorealestates.data.models

data class User (
    val id: String,
    val username: String,
    val email: String,
    val address: String,
    val password: String,
    val phone: String
) {
    constructor(username: String, email: String, address: String, password: String, phone: String) : this("", username, email, address, password, phone)
}
//    val  photo :  String