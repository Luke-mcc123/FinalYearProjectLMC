package com.example.finalyearprojectlmc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.finalyearprojectlmc.databinding.ActivityMainBinding

private lateinit var binding : ActivityMainBinding
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(R.layout.activity_main)
        setContentView(binding.root)

        val context = this
        var db = DataBaseHandler(context)

        // Insert Button
        // Takes User Input and Inserts Into the Database
        binding.btnInsert.setOnClickListener(){
            if (binding.etvBarcode.text.toString().isNotEmpty() && binding.etvBrand.text.toString().isNotEmpty()
                && binding.etvType.text.toString().isNotEmpty() && binding.etvDescription.text.toString().isNotEmpty()
                && binding.etvQuantity.text.toString().isNotEmpty()
            ){
                var product = Product(binding.etvBarcode.text.toString().toInt(), binding.etvBrand.text.toString(),
                    binding.etvType.text.toString(),binding.etvDescription.text.toString(),
                    binding.etvQuantity.text.toString().toInt())
                db.insertData(product)
            } else {
                Toast.makeText(context,"Please Fill In All Fields",Toast.LENGTH_SHORT).show()
            }
        }

        // Read Button
        // Reads and Displays Database Records
        binding.btnRead.setOnClickListener {
            var data = db.readData()
            binding.tvResult.text = ""
            for (i in 0..(data.size -1)){
                binding.tvResult.append(data.get(i).barcode.toString()  + " | " + data.get(i).brand + " | " + data.get(i).type +
                        " | " + data.get(i).description+" | " + data.get(i).quantity + "\n")
            }

            binding.etvBarcode.setText("")
            binding.etvBrand.setText("")
            binding.etvType.setText("")
            binding.etvDescription.setText("")
            binding.etvQuantity.setText("")
        }

        // Update Records
        binding.btnUpdate.setOnClickListener(){
            db.updateData()
            binding.btnRead.performClick()
        }

        // Deletes Records
        binding.btnDelete.setOnClickListener(){
            db.deleteData()
            binding.btnRead.performClick()
        }
    }
}