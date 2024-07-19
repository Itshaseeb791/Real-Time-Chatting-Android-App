package com.example.notiii.dataclasses

class Notification {
    var Notiid : String =" "
    var message:String=" "
    var senderName : String=" "
    var image : String=""
     constructor()
    constructor(
         Notiid : String ,
         message:String ,
         senderName : String,
         image : String
    ){
        this.image=image
        this.Notiid=Notiid
        this.senderName=senderName
        this.message=message
    }

}