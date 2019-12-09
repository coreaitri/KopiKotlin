package com.tugas.kopikuhkopi.Admin.OrderAdmin

class Order(var userId:String, var orderId: String, var name:String, var numberPhone:String, var address: String, var total:String)
{
    //empty constrcutor
    constructor() : this("","","","","",""){

    }

}