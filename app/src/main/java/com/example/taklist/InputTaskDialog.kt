package com.example.taklist

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import com.example.taklist.databinding.InputtaskdialogBinding

class InputTaskDialog(
    context: Context,
    private val onTaskAdded: (String) -> Unit
) : Dialog(context) {

    private lateinit var binding: InputtaskdialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        binding = InputtaskdialogBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)

        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        window?.setGravity(android.view.Gravity.CENTER)

        binding.progressBar.visibility = android.view.View.GONE

        val editText = binding.inputLayout.editText
        editText?.hint = "Enter task name"

        binding.ok.setOnClickListener {
            val taskName = editText?.text.toString().trim()
            if (taskName.isNotEmpty()) {
                onTaskAdded(taskName)
                dismiss()
            } else {
                binding.inputLayout.error = "Task name cannot be empty"
            }
        }
    }
}