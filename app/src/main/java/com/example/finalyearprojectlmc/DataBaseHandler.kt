package com.example.finalyearprojectlmc

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.sqlite.SQLiteOpenHelper
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.widget.Toast

val DATABASE_NAME = "FinalYearProject.SQLite"
val TABLE_NAME = "Products"
val COL_BARCODE = "Barcode"
val COL_BRAND = "Brand"
val COL_TYPE = "Type"
val COL_DESCRIPTION = "Description"
val COL_QUANTITY = "Quantity"
val COL_ID = "Id"

class DataBaseHandler (var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1){
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + TABLE_NAME +" (" +
                COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_BARCODE + " INTEGER," +
                COL_BRAND + " VARCHAR(256)," +
                COL_TYPE + " VARCHAR(256)," +
                COL_DESCRIPTION + " VARCHAR(256)," +
                COL_QUANTITY + " INTEGER)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun insertData (product : Product){
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(COL_BARCODE, product.barcode)
        cv.put(COL_BRAND, product.brand)
        cv.put(COL_TYPE, product.type)
        cv.put(COL_DESCRIPTION, product.description)
        cv.put(COL_QUANTITY, product.quantity)

        var result = db.insert(TABLE_NAME, null, cv)
        if(result == -1.toLong())
            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("Range")
    fun readData() : MutableList<Product>{
        var list: MutableList<Product> = ArrayList()

        val db = this.readableDatabase
        val query = "Select * from " + TABLE_NAME
        val result = db.rawQuery(query, null)
        if(result.moveToFirst()){
            do{
                var product = Product()
                product.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
                product.barcode = result.getString(result.getColumnIndex(COL_BARCODE)).toInt()
                product.brand = result.getString(result.getColumnIndex(COL_BRAND))
                product.type = result.getString(result.getColumnIndex(COL_TYPE))
                product.description = result.getString(result.getColumnIndex(COL_DESCRIPTION))
                product.quantity = result.getString(result.getColumnIndex(COL_QUANTITY)).toInt()
                list.add(product)
            } while (result.moveToNext())
        }
        result.close()
        db.close()
        return list
    }

    fun deleteData(){
        val db = this.writableDatabase
        db.delete(TABLE_NAME, null, null)
        db.close()
    }

    @SuppressLint("Range")
    fun updateData(){
        val db = this.writableDatabase
        val query = "Select * from " + TABLE_NAME
        val result = db.rawQuery(query,null)
        if(result.moveToFirst()){
            do {
                var cv = ContentValues()
                cv.put(COL_QUANTITY,(result.getInt(result.getColumnIndex(COL_QUANTITY))+1))
                db.update(TABLE_NAME,cv,COL_ID + "=? AND " + COL_BRAND + "=?",
                    arrayOf(result.getString(result.getColumnIndex(COL_ID)),
                        result.getString(result.getColumnIndex(COL_BRAND))))
            }while (result.moveToNext())
        }
        result.close()
        db.close()
    }
}