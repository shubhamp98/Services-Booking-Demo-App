package com.shubhampandey.myapplication.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.shubhampandey.myapplication.R
import com.shubhampandey.myapplication.utils.SharedPrefUtil
import kotlinx.android.synthetic.main.fragment_username.*

class UsernameFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_username, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkForLogin()
    }

    private fun checkForLogin() {
        if (isLoggedIn()) {
            // User is already logged in
            navigateToHomeFragmentDestination()
        }
        else {
            // User is not logged in
            setListener()
        }
    }

    private fun navigateToHomeFragmentDestination() {
        // Navigate to a destination
        val action =
            UsernameFragmentDirections.actionUsernameFragmentToHomeFragment()
        findNavController().navigate(action)
    }

    private fun isLoggedIn() = SharedPrefUtil.getMobileNumberFromPref(requireContext()) != null

    private fun setListener() {
        next_Btn.setOnClickListener {
            if (mobileNumber_TIL.editText?.text.isNullOrEmpty()) {
                // show error
                mobileNumber_TIL.error = "${getString(R.string.ten_digit_mobile)} required"
            }
            else {
                // clear the error
                mobileNumber_TIL.error = null
                navigateToUserPasswordDestination()
            }
        }
    }

    private fun navigateToUserPasswordDestination() {
        val action =
            UsernameFragmentDirections.actionUsernameFragmentToUserPasswordFragment(mobileNumber_TIL.editText?.text.toString())
        findNavController().navigate(action)
    }
}