package com.itis.android_homework.ui.fragments.auth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.android_homework.R
import com.itis.android_homework.base.BaseFragment
import com.itis.android_homework.databinding.FragmentSignUpBinding
import com.itis.android_homework.db.entity.UserEntity
import com.itis.android_homework.di.ServiceLocator
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
                        ServiceLocator.getDbInstance().userDao.getUserInfoByEmail(email)

                    withContext(Dispatchers.Main) {
                        if (existingUser == null) {

                            val userEntity = UserEntity(
                                userId = UUID.randomUUID().toString(),
                                name = name,
                                secondName = secondName,
                                phoneNumber = phoneNumber,
                                emailAddress = email,
                                password = password
                            )
                            lifecycleScope.launch(Dispatchers.IO) {
                                ServiceLocator.getDbInstance().userDao.addUser(userEntity)
                            }

                            navController.navigate(R.id.action_signUpFragment_to_signInFragment)

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

    private inner class CurrencyTextWatcher : TextWatcher {
        var stringBuilder : StringBuilder = java.lang.StringBuilder()
        var ignore : Boolean = false

        private val numPlace : Char = 'X'

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(editable: Editable) {
            if (!ignore) {
                removeFormat(editable.toString())
                applyFormat(stringBuilder.toString())
                ignore = true
                editable.replace(0, editable.length, stringBuilder.toString())
                ignore = false
            }
        }

        private fun removeFormat(text: String) {
            stringBuilder.setLength(0)
            for (i in 0 until text.length) {
                val c : Char = text[i]
                if (isNumberChar(c)) {
                    stringBuilder.append(c)
                }
            }
        }

        private fun applyFormat(text: String) {
            val template: String = getTemplate(text)
            stringBuilder.setLength(0)
            var i = 0
            var textIndex = 0
            while (i < template.length && textIndex < text.length) {
                if (template[i] == numPlace) {
                    stringBuilder.append(text[textIndex])
                    textIndex++
                } else {
                    stringBuilder.append(template[i])
                }
                i++
            }
        }

        private fun isNumberChar(c: Char): Boolean {
            return c in '0'..'9'
        }

        private fun getTemplate(text: String): String {
            if (text.startsWith("8")) {
                return "X (XXX)-XXX-XX-XX"
            }
            if (text.startsWith("7")) {
                return "+X (XXX)-XXX-XX-XX"
            }
            return if (text.startsWith("49")) {
                "+XX-XX-XXX-XXXXX"
            } else "+XXX (XXX)-XX-XX-XX"
        }

    }


    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val SIGN_UP_FRAGMENT_TAG = "SIGN_UP_FRAGMENT_TAG"
    }
}