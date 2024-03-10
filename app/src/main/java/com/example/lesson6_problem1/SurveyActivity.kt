package com.example.lesson6_problem1

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.marginTop
import androidx.core.view.setPadding
import com.example.lesson6_problem1.databinding.ActivitySurveyBinding


class SurveyActivity : AppCompatActivity() {



    private val foodPrefQuestions = listOf(
        Question("What is your favorite cuisine?", listOf("Chinese", "French", "Italian", "Indian", "Japanese", "Thai", "Turkish")),
        Question("How often do you eat out?", listOf("Never", "Rarely", "Sometimes", "Frequently")),
        Question("Do you prefer spicy food?", listOf("Yes", "No")),
        Question("Do you prefer vegetarian food?", listOf("Yes", "No")),
        Question("Do you like seafood?", listOf("Yes", "No"))
    )

    private val dietaryQuestions = listOf(
        Question("Are you vegetarian?", listOf("Yes", "No")),
        Question("Do you prefer organic food?", listOf("Yes", "No")),
        Question("Do you consume dairy products?", listOf("Yes", "No")),
        Question("Do you eat fast food?", listOf("Yes", "No")),
        Question("Do you have any food allergies?", listOf("Yes", "No"))
    )



    private lateinit var answer : MutableList<Int>;


    private lateinit var binding: ActivitySurveyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        @Suppress("DEPRECATION")


        val surveyType = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            intent.getSerializableExtra("enumKey", SurveyType::class.java)
        else
            intent.getSerializableExtra("enumKey") as SurveyType


        binding = ActivitySurveyBinding.inflate(layoutInflater)

        when (surveyType) {
            SurveyType.FoodPreference -> {
                binding.title.text = "Food Preference"
                answer = MutableList(foodPrefQuestions.size,){_->-1}

                populateLinearLayout(foodPrefQuestions, binding.linearLayout)

            }

            SurveyType.DietaryHabit -> {
                binding.title.text = "Dietary Habit"
                answer = MutableList(dietaryQuestions.size,){_->-1}
                populateLinearLayout(dietaryQuestions, binding.linearLayout)
            }

            else -> {}
        }

        binding.submitButton.setOnClickListener {
            if(answer.contains(-1)){
                Toast.makeText(this, "Answer all questions", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val questions = if (surveyType == SurveyType.DietaryHabit) dietaryQuestions else foodPrefQuestions


            questions.forEachIndexed{i, q ->


                val textView = TextView(this)

                val title = " ${i + 1}. ${q.question} ${q.choices[answer[i]]}"
                textView.text = title

                textView.setPadding(8)
                binding.linearLayout2.addView(textView)

            }

        }


        setContentView(binding.root)
    }


    fun  populateLinearLayout(questions : List<Question> , linearLayout: LinearLayout ){


        questions.forEachIndexed{i, q ->


            val textView = TextView(this)

            val title = " ${i + 1}. ${q.question}"
            textView.text = title
            val radioGroup = RadioGroup(this)

            q.choices.forEach { choice ->
                val radioButton = RadioButton(this)
                radioButton.text = choice
                radioGroup.addView(radioButton)
            }

            radioGroup.setOnCheckedChangeListener{ group, checkedId ->
                val radioButton = findViewById<RadioButton>(checkedId)
                val selectedIndex = group.indexOfChild(radioButton)
                answer[i] = selectedIndex;

            }
            textView.setPadding(16)
            linearLayout.addView(textView)
            linearLayout.addView(radioGroup)

        }
    }
}