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
import com.itis.android_homework.databinding.FragmentSignUpBinding
import com.itis.android_homework.db.entity.UserEntity
import com.itis.android_homework.di.ServiceLocator
import com.itis.android_homework.ui.fragments.MainScreenFragment
import com.itis.android_homework.utils.ActionType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpFragment : BaseFragment(R.layout.fragment_sign_up) {

    private val viewBinding by viewBinding(FragmentSignUpBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var counter = 0
        val navController = findNavController()
//        lifecycleScope.launch {
//            val usersList = withContext(Dispatchers.IO) {
//                ServiceLocator.getDbInstance().userDao.getAllUsers()
//            }
//        }
        viewBinding.signUpActionBtn.setOnClickListener {
            val name: String = viewBinding.nameEt.text.toString()
            val secondName: String = viewBinding.secondNameEt.text.toString()
            val phoneNumber: String = viewBinding.phoneNumberEt.text.toString()
            val email: String = viewBinding.emailEt.text.toString()
            val password: String = viewBinding.passwordEt.text.toString()

            if (name.isNotEmpty() && secondName.isNotEmpty() && phoneNumber.isNotEmpty()
                && email.isNotEmpty() && password.isNotEmpty()
            ) {
                lifecycleScope.launch(Dispatchers.IO) {

                    val existingUser: UserEntity? =
                        ServiceLocator.getDbInstance().userDao.getUserInfoByEmail(email)

                    withContext(Dispatchers.Main) {
                        if (existingUser == null) {

                            val userEntity = UserEntity(
                                id = "user_id_${++counter}",
                                name = name,
                                secondName = secondName,
                                phoneNumber = phoneNumber,
                                emailAddress = email,
                                password = password
                            )
                            lifecycleScope.launch(Dispatchers.IO) {
                                ServiceLocator.getDbInstance().userDao.addUser(userEntity)
                            }

                            navController.navigate(R.id.action_signUpFragment_to_mainScreenFragment)

                            showToast("User successfully registered")
                        } else {
                            showToast("User with this email is already registered")
                        }
                    }
                }
            } else {

                showToast("Please fill in ALL fields")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val SIGN_UP_FRAGMENT_TAG = "SIGN_UP_FRAGMENT_TAG"
    }
}