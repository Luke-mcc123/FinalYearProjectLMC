package com.example.finalyearprojectlmc

class Product {
    var id : Int = 0
    var barcode : Int = 0
    var brand : String = ""
    var type : String = ""
    var description : String = ""
    var quantity : Int = 0

    constructor(
        barcode: Int,
        brand: String,
        type: String,
        description: String,
        quantity: Int
    ) {

        this.barcode = barcode
        this.brand = brand
        this.type = type
        this.description = description
        this.quantity = quantity
    }

    constructor(){

    }
}