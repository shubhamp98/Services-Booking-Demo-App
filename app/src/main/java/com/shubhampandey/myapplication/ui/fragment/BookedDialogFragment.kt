package com.shubhampandey.myapplication.ui.fragment

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.shubhampandey.myapplication.R

class BookedDialogFragment: DialogFragment() {

    private val TAG = BookedDialogFragment::class.java.simpleName

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        val dialogLayout = inflater.inflate(R.layout.dialog_success, null)
        dialogLayout.setOnClickListener {
            navigateToHomeFragmentDestination()
        }
        //Log.d(TAG, "onCreateDialog")

        return builder
            .setView(dialogLayout)
            .create()
    }

    private fun navigateToHomeFragmentDestination() {
        // Navigate to a destination
        val action =
            BookedDialogFragmentDirections.actionBookedDialogFragmentToHomeFragment()
        findNavController().navigate(action)
    }
}