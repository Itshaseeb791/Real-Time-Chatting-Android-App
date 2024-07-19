package com.example.notiii.dataclasses

class Message
{

    var id:String=""
    var message: String=""
    var  SenderId : String=""
    var  id1 : String=""
    var type : String=""
    constructor()

    constructor(id: String, message: String, SenderId: String, type: String,type2 : String) {
        this.id = id
        this.message = message
        this.SenderId = SenderId
        this.id1 = type
        this.type=type2
    }

}
