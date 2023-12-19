package com.itis.android_homework.ui.fragments.auth

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.android_homework.R
import com.itis.android_homework.base.BaseActivity
import com.itis.android_homework.base.BaseFragment
import com.itis.android_homework.databinding.FragmentSignInBinding
import com.itis.android_homework.db.entity.UserEntity
import com.itis.android_homework.di.ServiceLocator
import com.itis.android_homework.ui.fragments.MainScreenFragment
import com.itis.android_homework.utils.ActionType
import com.itis.android_homework.utils.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignInFragment: BaseFragment(R.layout.fragment_sign_in) {

    private val viewBinding by viewBinding(FragmentSignInBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()

        viewBinding.redirectActionBtn.setOnClickListener {
            navController.navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        viewBinding.signInActionBtn.setOnClickListener {
            val email: String = viewBinding.emailEt.text.toString()
            val password: String = viewBinding.passwordEt.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                lifecycleScope.launch(Dispatchers.IO) {
                    val userEntity: UserEntity? = ServiceLocator.getDbInstance().userDao.getUserInfoByEmail(email)

                    withContext(Dispatchers.Main) {
                        if (userEntity != null) {
                            if (password == userEntity.password) {
                                with(viewBinding) {
                                    emailEt.text?.clear()
                                    passwordEt.text?.clear()
                                }
                                navController.navigate(R.id.action_signInFragment_to_mainScreenFragment)
                                showToast("Successful Login")
                            } else {
                                viewBinding.passwordEt.error = "Invalid Password"
                            }

                            val sharedPreferences = requireContext().getSharedPreferences(
                                "user_session",
                                Context.MODE_PRIVATE
                            )
                            val editor = sharedPreferences.edit()
                            editor.putString("user_id", userEntity.userId)
                            editor.putBoolean("is_authenticated", true)
                            editor.apply()

                        } else {
                            showToast("User with this email not exists")
                        }
                    }
                }
            } else {
                showToast("Please enter email and password")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val SIGN_IN_FRAGMENT_TAG = "SIGN_IN_FRAGMENT_TAG"
    }
}