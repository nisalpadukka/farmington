package com.georgian.farmington



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast

class MarketplacesellActivity : AppCompatActivity() {
    var radioGroup: RadioGroup? = null
    lateinit var radioButton: RadioButton
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)



        // Assigning id of RadioGroup
        radioGroup = findViewById(R.id.radioGroup1)

        // Assigning id of Submit button
        button = findViewById(R.id.Sell)

        // Actions to be performed
        // when Submit button is clicked
        button.setOnClickListener {

            // Getting the checked radio button id
            // from the radio group
            val selectedOption: Int = radioGroup!!.checkedRadioButtonId

            // Assigning id of the checked radio button
            radioButton = findViewById(selectedOption)

            // Displaying text of the checked radio button in the form of toast
            Toast.makeText(baseContext, radioButton.text, Toast.LENGTH_SHORT).show()
        }
    }
}

