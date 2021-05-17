package com.shubhampandey.myapplication.ui.fragment

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.shubhampandey.myapplication.R
import kotlinx.android.synthetic.main.fragment_form.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class FormFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
    }

    private fun setupUI() {
        setListener()
        updateDateInForm()
    }

    private fun updateDateInForm() {
        val formattedDate: String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val currentDateTime = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")
            formatter.format(currentDateTime)
        } else {
            val currentDateTime = Date()
            val formatter = SimpleDateFormat("MMM dd, yyyy")
            formatter.format(currentDateTime)
        }
        currentDate_TIL.editText?.setText(formattedDate)
    }

    private fun setListener() {
        cancel_Btn.setOnClickListener {
            // Go back to previous screen
            findNavController().popBackStack()
        }
    }
}