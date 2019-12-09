package com.tugas.kopikuhkopi.Admin.MenuAdmin

data class Menu(
    val menuId: String,
    val name: String,
    val price:String,
    val image: String
){
    constructor() : this("","","",""){
    }
}