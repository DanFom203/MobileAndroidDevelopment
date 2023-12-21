package com.itis.android_homework.ui.fragments.auth

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.android_homework.R
import com.itis.android_homework.base.BaseFragment
import com.itis.android_homework.databinding.FragmentSignInBinding
import com.itis.android_homework.db.entity.UserEntity
import com.itis.android_homework.di.ServiceLocator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignInFragment: BaseFragment(R.layout.fragment_sign_in) {

    private val viewBinding by viewBinding(FragmentSignInBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = requireContext().getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val isUserAuthenticated = sharedPreferences.getBoolean("is_authenticated", false)
        if (isUserAuthenticated) {
            navigateToMainScreen()
        }

        viewBinding.redirectActionBtn.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        viewBinding.signInActionBtn.setOnClickListener {
            val email: String = viewBinding.emailEt.text.toString()
            val password: String = viewBinding.passwordEt.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {

                lifecycleScope.launch(Dispatchers.IO) {
                    val userEntity: UserEntity? = ServiceLocator.getDbInstance().userDao.getUserInfoByEmail(email)

                    withContext(Dispatchers.Main) {
                        if (userEntity != null) {
                            if (checkAccountRestoration(userEntity)) {
                                showRestorationDialog(userEntity)
                            } else {
                                if (password == userEntity.password) {
                                    navigateToMainScreen()
                                    showToast("Successful Login")
                                } else {
                                    viewBinding.passwordEt.error = "Invalid Password"
                                }

                                val editor = sharedPreferences.edit()
                                editor.putString("user_id", userEntity.userId)
                                editor.putBoolean("is_authenticated", true)
                                editor.apply()
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

    private fun checkAccountRestoration(userEntity: UserEntity): Boolean {
        if (userEntity.deletionTime != null) {
            val deletionTime = userEntity.deletionTime
            val currentTime = System.currentTimeMillis()
            val sevenDaysInMillis = 7 * 24 * 60 * 60 * 1000

            if (currentTime - deletionTime < sevenDaysInMillis) {
                return true
            }
        }
        return false
    }



    private fun showRestorationDialog(userEntity: UserEntity) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Account Deleted")
        builder.setMessage("Your account was deleted. Do you want to restore it or permanently delete?")

        builder.setPositiveButton("Restore") { _, _ ->
            restoreAccount(userEntity)
        }

        builder.setNegativeButton("Permanently Delete") { _, _ ->
            permanentlyDeleteAccount(userEntity)
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun restoreAccount(userEntity: UserEntity) {
        lifecycleScope.launch(Dispatchers.IO) {
            ServiceLocator.getDbInstance().userDao.updateUserDeletionTime(userEntity.userId, null)
        }
        navigateToMainScreen()
    }

    private fun permanentlyDeleteAccount(userEntity: UserEntity) {
        with(viewBinding) {
            emailEt.text?.clear()
            passwordEt.text?.clear()
        }
        lifecycleScope.launch(Dispatchers.IO) {
            ServiceLocator.getDbInstance().userDao.deleteUserById(userEntity.userId)
        }
    }

    private fun navigateToMainScreen() {
        with(viewBinding) {
            emailEt.text?.clear()
            passwordEt.text?.clear()
        }
        findNavController().navigate(R.id.action_signInFragment_to_mainScreenFragment)
    }

}