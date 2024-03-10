package com.example.lesson6_problem1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import com.example.lesson6_problem1.databinding.ActivityMainBinding


const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.startButton.setOnClickListener {
            when(findViewById<RadioButton?>(binding.radioGroup.checkedRadioButtonId)){
                binding.foodButton-> {
                    val intent = Intent(this, SurveyActivity::class.java)
                    intent.putExtra("enumKey", SurveyType.FoodPreference)
                    startActivity(intent)
                }
                binding.dietButton -> {
                    val intent = Intent(this, SurveyActivity::class.java)
                    intent.putExtra("enumKey", SurveyType.DietaryHabit)
                    startActivity(intent)
                }
                else -> Toast.makeText(this, "Select Survey type", Toast.LENGTH_LONG).show()
            }
        }



        setContentView(binding.root)
    }
}