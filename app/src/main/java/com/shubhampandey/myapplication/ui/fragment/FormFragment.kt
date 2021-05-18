package com.shubhampandey.myapplication.ui.fragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.shubhampandey.myapplication.R
import com.shubhampandey.myapplication.utils.SharedPrefUtil
import kotlinx.android.synthetic.main.fragment_form.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import kotlin.collections.ArrayList

class FormFragment : Fragment() {

    lateinit var db: FirebaseFirestore
    private val TAG = FormFragment::class.java.simpleName
    // To retrieve data sent using Safe args
    private val args: FormFragmentArgs by navArgs()
    private lateinit var servicesOpted: Array<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Access Firestore instance
        db = Firebase.firestore
        setupUI()
    }

    private fun setupUI() {
        setListener()
        updateMobileNumberInForm()
        updateSelectedServiceInForm()
        updateDateInForm()
    }

    private fun updateSelectedServiceInForm() {
        servicesOpted = args.servicesOpted
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, servicesOpted)
        (servicesOpted_TIL.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        // Show number of services opted
        servicesOpted_ACTV.setText(servicesOpted.size.toString(), false)
    }

    private fun updateMobileNumberInForm() {
        mobile_TIL.editText?.setText(SharedPrefUtil.getMobileNumberFromPref(requireContext()))
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
        save_Btn.setOnClickListener {
            when {
                name_TIL.editText?.text.isNullOrEmpty() -> name_TIL.error =
                    getString(R.string.full_name_required_error)
                email_TIL.editText?.text.isNullOrEmpty() -> {
                    name_TIL.error = null
                    email_TIL.error = getString(R.string.email_required_error)
                }
                address_TIL.editText?.text.isNullOrEmpty() -> {
                    name_TIL.error = null
                    email_TIL.error = null
                    address_TIL.error = getString(R.string.address_required_error)
                }
                postalCode_TIL.editText?.text.isNullOrEmpty() -> {
                    name_TIL.error = null
                    email_TIL.error = null
                    address_TIL.error = null
                    postalCode_TIL.error = getString(R.string.postal_required_error)
                }
                else -> {
                    // Clear all errors, if any
                    name_TIL.error = null
                    email_TIL.error = null
                    address_TIL.error = null
                    postalCode_TIL.error = null
                    saveData()
                }
            }
        }

        cancel_Btn.setOnClickListener {
            // Go back to previous screen
            findNavController().popBackStack()
        }
    }

    private fun saveData() {
        // Create a new user with a first and last name
        val bookedServiceDetails =
            hashMapOf<String, Any>(
                "fullName" to name_TIL.editText?.text.toString(),
                "mobileNumber" to mobile_TIL.editText?.text.toString(),
                "emailAddress" to email_TIL.editText?.text.toString(),
                "address" to address_TIL.editText?.text.toString(),
                "postalCode" to postalCode_TIL.editText?.text.toString(),
                "servicesOpted" to servicesOpted.toList(),
                "date" to currentDate_TIL.editText?.text.toString()
            )
        // Add a new document with a generated ID
        db.collection(getString(R.string.booked_services_collection_key))
            .add(bookedServiceDetails)
            .addOnSuccessListener {
                //Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                navigateToBookedDialogFragmentDestination()
            }
            .addOnFailureListener { e ->
                //Log.w(TAG, "Error adding document", e)
                showError()
            }
    }

    private fun showError() {
        Snackbar.make(requireView(), getString(R.string.error_message), Snackbar.LENGTH_LONG).show()
    }

    private fun navigateToBookedDialogFragmentDestination() {
        // Navigate to a destination
        val action =
            FormFragmentDirections.actionFormFragmentToBookedDialogFragment()
        findNavController().navigate(action)
    }
}