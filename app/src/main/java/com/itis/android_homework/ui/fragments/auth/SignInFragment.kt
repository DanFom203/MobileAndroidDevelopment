package com.itis.android_homework.ui.fragments.auth

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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInFragment: BaseFragment(R.layout.fragment_sign_in) {

    private val viewBinding by viewBinding(FragmentSignInBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()

        viewBinding.redirectActionBtn.setOnClickListener {
            // Используем NavController для перехода к SignUpFragment
            navController.navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        viewBinding.signInActionBtn.setOnClickListener {
            val email: String = viewBinding.emailEt.text.toString()
            val password: String = viewBinding.passwordEt.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                lifecycleScope.launch(Dispatchers.IO) {
                    val userEntity: UserEntity? = ServiceLocator.getDbInstance().userDao.getUserInfoByEmail(email)

                    lifecycleScope.launch(Dispatchers.Main) {
                        if (userEntity != null) {
                            if (password == userEntity.password) {
                                navController.navigate(R.id.action_signInFragment_to_mainScreenFragment)
                                showToast("Successful Login")
                            } else {
                                viewBinding.passwordEt.error = "Invalid Password"
                            }
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