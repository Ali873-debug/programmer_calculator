package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton

class MainActivity : AppCompatActivity() {

    private lateinit var spinnerFrom: Spinner
    private lateinit var spinnerTo: Spinner
    private lateinit var editTextInput: EditText
    private lateinit var textViewResult: TextView
    private lateinit var buttonConvert: AppCompatButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        adapterSetUp()
        onClickButton()
    }

    private fun adapterSetUp() {
        val options = arrayOf("binary", "hexadecimal", "decimal", "octal")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFrom.adapter = adapter
        spinnerTo.adapter = adapter
    }

    private fun onClickButton() {
        buttonConvert.setOnClickListener {
            val from = spinnerFrom.selectedItem.toString()
            val to = spinnerTo.selectedItem.toString()
            val input = editTextInput.text.toString()

            val isValidInput = validateInput(from, input)

            if (isValidInput) {
                val result = convertInput(from, to, input)
                textViewResult.text = result
            } else {
                textViewResult.text = getString(R.string.invalidText)
            }
        }
    }

    private fun validateInput(from: String, input: String): Boolean {
        return when (from) {
            "binary" -> input.matches("[01]+".toRegex())
            "hexadecimal" -> input.matches("[\\dA-Fa-f]+".toRegex())
            "decimal" -> input.matches("\\d+".toRegex())
            "octal" -> input.matches("[0-7]+".toRegex())
            else -> false
        }
    }

    private fun convertInput(from: String, to: String, input: String): String {
        return when {
            from == "binary" && to == "hexadecimal" -> Integer.parseInt(input, 2).toString(16)
            from == "binary" && to == "decimal" -> Integer.parseInt(input, 2).toString()
            from == "binary" && to == "octal" -> Integer.parseInt(input, 2).toString(8)
            from == "hexadecimal" && to == "binary" -> Integer.parseInt(input, 16).toString(2)
            from == "hexadecimal" && to == "decimal" -> Integer.parseInt(input, 16).toString()
            from == "hexadecimal" && to == "octal" -> Integer.parseInt(input, 16).toString(8)
            from == "decimal" && to == "binary" -> Integer.parseInt(input).toString(2)
            from == "decimal" && to == "hexadecimal" -> Integer.parseInt(input).toString(16)
            from == "decimal" && to == "octal" -> Integer.parseInt(input).toString(8)
            from == "octal" && to == "binary" -> Integer.parseInt(input, 8).toString(2)
            from == "octal" && to == "hexadecimal" -> Integer.parseInt(input, 8).toString(16)
            from == "octal" && to == "decimal" -> Integer.parseInt(input, 8).toString()
            else -> getString(R.string.invalidText)
        }
    }

    private fun initView() {
        spinnerFrom = findViewById(R.id.spinnerFrom)
        spinnerTo = findViewById(R.id.spinnerTo)
        editTextInput = findViewById(R.id.input_edittext)
        textViewResult = findViewById(R.id.result_edittext)
        buttonConvert = findViewById<AppCompatButton>(R.id.calculate_button)
    }
}


