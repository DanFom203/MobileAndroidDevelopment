package com.itis.android_homework.ui.fragments.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.android_homework.R
import com.itis.android_homework.base.BaseActivity
import com.itis.android_homework.base.BaseFragment
import com.itis.android_homework.databinding.FragmentSignInBinding
import com.itis.android_homework.db.entity.UserEntity
import com.itis.android_homework.di.ServiceLocator
import com.itis.android_homework.utils.ActionType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignInFragment: BaseFragment(R.layout.fragment_sign_in) {

    private val viewBinding by viewBinding(FragmentSignInBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.signInActionBtn.setOnClickListener {
            val email: String = viewBinding.emailEt.text.toString()
            val password: String = viewBinding.passwordEt.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                lifecycleScope.launch(Dispatchers.IO) {
                    val userEntity: UserEntity? =
                        ServiceLocator.getDbInstance().userDao.getUserInfoByEmail(email)

                    withContext(Dispatchers.Main) {
                        if (userEntity != null) {
                            if (password == userEntity.password) {
                                (requireActivity() as? BaseActivity)?.goToScreen(
                                    actionType = ActionType.REPLACE,
                                    destination = MainScreenFragment(),
                                    tag = MainScreenFragment.MAIN_SCREEN_FRAGMENT_TAG,
                                    isAddToBackStack = true,
                                )
                                showToast("Successful Login")
                            } else {
                                showToast("Invalid Password")
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