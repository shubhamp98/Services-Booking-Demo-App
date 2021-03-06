package com.shubhampandey.myapplication.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.shubhampandey.myapplication.R
import com.shubhampandey.myapplication.utils.SharedPrefUtil
import kotlinx.android.synthetic.main.fragment_user_password.*

class UserPasswordFragment : Fragment() {

    // To retrieve data sent using Safe args
    private val args: UserPasswordFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListener()
    }

    override fun onStop() {
        super.onStop()
        hideSoftKeyboard()
    }

    private fun hideSoftKeyboard() {
        otpPassword_TIL.editText?.clearFocus()
    }

    private fun setListener() {
        login_Btn.setOnClickListener {
            if (otpPassword_TIL.editText?.text.isNullOrEmpty()) {
                // show error
                otpPassword_TIL.error = getString(R.string.otp_required)
            } else {
                // clear the error
                otpPassword_TIL.error = null
                if (isCorrectPassword()) {
                    saveUsername()
                    navigateToHomeDestination()
                }
                else
                    // Incorrect password, show error
                    otpPassword_TIL.error = getString(R.string.incorrect_password_error)
            }
        }
    }

    private fun saveUsername() {
        SharedPrefUtil.saveMobileNumberToPref(requireContext(), args.userMobileNumber)
    }

    private fun navigateToHomeDestination() {
        val action =
            UserPasswordFragmentDirections.actionUserPasswordFragmentToHomeFragment()
        findNavController().navigate(action)
    }

    private fun isCorrectPassword() = otpPassword_TIL.editText?.text.toString() == DEFAULT_OTP

    companion object {
        private const val DEFAULT_OTP = "0000"
    }
}