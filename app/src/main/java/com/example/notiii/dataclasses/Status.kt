package com.example.notiii.dataclasses

class Status {
    var senderpic : String = ""
    var sender : String = ""
    var des : String = ""
    var imagestatus : String = ""
    constructor()
    constructor(
        senderpic : String,
        sender : String,
        des : String,
        imagestatus : String
    ){
        this.des=des
       this.sender=sender
       this.imagestatus=imagestatus
       this.senderpic=senderpic
    }
}