package com.itis.android_homework.ui.fragments.auth

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.android_homework.R
import com.itis.android_homework.base.BaseFragment
import com.itis.android_homework.databinding.FragmentSignUpBinding
import com.itis.android_homework.db.entity.UserEntity
import com.itis.android_homework.di.ServiceLocator
import com.itis.android_homework.utils.CurrencyTextWatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class SignUpFragment : BaseFragment(R.layout.fragment_sign_up) {

    private val viewBinding by viewBinding(FragmentSignUpBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
        viewBinding.phoneNumberEt.addTextChangedListener(CurrencyTextWatcher())

        viewBinding.signUpActionBtn.setOnClickListener {
            with(viewBinding) {
                if (nameEt.text.toString().length < 2) {
                    nameEt.error = getString(R.string.invalid_name)
                    return@setOnClickListener
                }
                if (secondNameEt.text.toString().length < 2) {
                    secondNameEt.error = getString(R.string.invalid_second_name)
                    return@setOnClickListener
                }
                if (phoneNumberEt.text.toString().isEmpty() ||
                    phoneNumberEt.text.toString()[0] == '+' && phoneNumberEt.text.toString().length < 18 ||
                    phoneNumberEt.text.toString()[0] == '8' && phoneNumberEt.text.toString().length < 17
                ) {
                    phoneNumberEt.error = getString(R.string.invalid_number)
                    return@setOnClickListener
                }
                if (passwordEt.text.toString().length < 5) {
                    passwordEt.error = getString(R.string.invalid_password)
                    return@setOnClickListener
                }
                if (!emailEt.text.toString().contains('@')) {
                    emailEt.error = getString(R.string.invalid_email)
                    return@setOnClickListener
                }
            }

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
                        ServiceLocator.getDbInstance().userDao.getUserInfoByEmailAndPhoneNumber(email, phoneNumber)

                    withContext(Dispatchers.Main) {
                        if (existingUser == null) {

                            val userEntity = UserEntity(
                                userId = UUID.randomUUID().toString(),
                                name = name,
                                secondName = secondName,
                                phoneNumber = phoneNumber,
                                emailAddress = email,
                                password = password,
                                deletionTime = null
                            )
                            lifecycleScope.launch(Dispatchers.IO) {
                                ServiceLocator.getDbInstance().userDao.addUser(userEntity)
                            }

                            navController.navigate(R.id.action_signUpFragment_to_signInFragment)

                            showToast("User successfully registered")
                        } else {
                            showToast("User with this email/phone number is already registered")
                        }
                    }
                }
            } else {

                showToast("Please fill in ALL fields")
            }
        }
    }

}