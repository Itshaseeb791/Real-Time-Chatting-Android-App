//package com.example.notiii
//
//class UserData{
//
//
//    var Userid : String = "",
//    var userName : String = "",
//    var email : String="",
//    var password : String="",
//    var phone : String="",
//    var image : String=""
//    constructor()
//
//  constructor(
//      Userid : String,
//      userName : String,
//      email : String,
//      password : String,
//      phone : String,
//      image : String
//  ){
//      this.Userid=Userid
//      this.image=image
//      this.email=email
//      this.userName=userName
//      this.password=password
//      this.phone=phone
//  }
//
//}
package com.example.notiii.dataclasses

class UserData {
    var Userid: String = ""
    var userName: String = ""
    var email: String = ""
    var password: String = ""
    var phone: String = ""
    var image: String = ""

    constructor()

    constructor(
        Userid: String,
        userName: String,
        email: String,
        password: String,
        phone: String,
        image: String
    ) {
        this.Userid = Userid
        this.image = image
        this.email = email
        this.userName = userName
        this.password = password
        this.phone = phone
    }
}
