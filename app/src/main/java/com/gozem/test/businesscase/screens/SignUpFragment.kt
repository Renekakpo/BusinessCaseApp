package com.gozem.test.businesscase.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.gozem.test.businesscase.R
import com.gozem.test.businesscase.application.appContext
import com.gozem.test.businesscase.models.User
import com.gozem.test.businesscase.utils.Utils
import com.gozem.test.businesscase.utils.Utils.displayToastMessage
import com.gozem.test.businesscase.utils.Utils.validateUserSignUpCredentials
import com.gozem.test.businesscase.viewModels.MainViewModel
import kotlinx.android.synthetic.main.fragment_sign_up.*

class SignUpFragment : Fragment() {

    private lateinit var root: View
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_sign_up,
            container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initListener()
    }

    private fun initView() {
        val text = getString(R.string.sign_up_screen_sign_in_redirect_string)
        signInText.text = Utils.changeColorOfPartOfString(
            requireContext(),
            text,
            R.color.light_green,
            0,
            text.length,
            false
        )
    }

    private fun initListener() {
        imgBackButton.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_splashScreenFragment)
        }

        signUpButton.setOnClickListener {
            val error = validateUserSignUpCredentials(
                appContext,
                editFullName.text.toString().trim(),
                editEmail.text.toString().trim(),
                editPassword.text.toString().trim(),
                editConfirmPassword.text.toString().trim(),
            )

            if (error.isNotEmpty()) {
                displayToastMessage(error)
            } else {
                val user = User(
                    0,
                    editFullName.text.toString().trim(),
                    editEmail.text.toString().trim(),
                    Utils.md5Hash(editPassword.text.toString().trim())
                )
                mainViewModel.requestSignUp(requireActivity(), user)
            }
        }

        signInText.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }

    }
}