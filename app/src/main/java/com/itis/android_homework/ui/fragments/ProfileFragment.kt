package com.itis.android_homework.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.android_homework.R
import com.itis.android_homework.base.BaseFragment
import com.itis.android_homework.databinding.FragmentProfileBinding
import com.itis.android_homework.db.entity.UserEntity
import com.itis.android_homework.di.ServiceLocator
import com.itis.android_homework.model.UserModel
import com.itis.android_homework.ui.mappers.UserMapper
import com.itis.android_homework.utils.CurrencyTextWatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileFragment : BaseFragment(R.layout.fragment_profile){
    private val viewBinding by viewBinding(FragmentProfileBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = requireContext().getSharedPreferences(
            "user_session",
            Context.MODE_PRIVATE
        )
        val userId = sharedPreferences.getString("user_id", "")
        var userModel: UserModel?
        if (userId != null) {

            lifecycleScope.launch(Dispatchers.IO) {

                val userEntity: UserEntity? = userId.let {
                    ServiceLocator.getDbInstance().userDao.getUserInfoById(
                        it
                    )
                }
                userModel = userEntity?.let { UserMapper.toModel(it) }

                withContext(Dispatchers.Main) {
                    with(viewBinding) {
                        userNameTv.text = userModel?.name
                        userSecondNameTv.text = userModel?.secondName
                        userEmailTv.text = userModel?.emailAddress
                        userIdTv.text = userModel?.userId
                        userPhoneNumberTv.text = userModel?.phoneNumber
                    }
                }
            }

            viewBinding.userNewPhoneNumberEt.addTextChangedListener(CurrencyTextWatcher())

            viewBinding.submitActionBtn.setOnClickListener {
                val newPhoneNumber: String = viewBinding.userNewPhoneNumberEt.text.toString()
                val newPassword: String = viewBinding.userNewPasswordEt.text.toString()

                lifecycleScope.launch {
                    val userWithSamePhoneNumber = withContext(Dispatchers.IO) {
                        ServiceLocator.getDbInstance().userDao.getUserInfoByPhoneNumber(newPhoneNumber)
                    }
                    if (newPhoneNumber != "" && userWithSamePhoneNumber == null) {
                        lifecycleScope.launch(Dispatchers.IO) {
                            ServiceLocator.getDbInstance().userDao.updateUserPhoneNumber(
                                userId,
                                newPhoneNumber
                            )
                        }
                        viewBinding.userPhoneNumberTv.text = newPhoneNumber
                        showToast("Phone Number successfully updated")
                        viewBinding.userNewPhoneNumberEt.text?.clear()
                    } else {
                        showToast("Try another phone number")
                    }
                }

                if (newPassword != "") {
                    lifecycleScope.launch(Dispatchers.IO) {
                        ServiceLocator.getDbInstance().userDao.updateUserPassword(
                            userId,
                            newPassword
                        )
                    }
                    showToast("Password successfully updated")
                    viewBinding.userNewPasswordEt.text?.clear()
                }
            }

            viewBinding.signOutActionBtn.setOnClickListener {
                findNavController().navigateUp()

                val editor = sharedPreferences.edit()
                editor.putBoolean("is_authenticated", false)
                editor.apply()
            }

            viewBinding.deleteUserActionBtn.setOnClickListener {
                deleteAccount(userId)
                findNavController().navigateUp()

                val editor = sharedPreferences.edit()
                editor.putBoolean("is_authenticated", false)
                editor.apply()

                showToast("Current User was deleted")
            }
        }
    }

    private fun deleteAccount(userId: String) {
        val currentTime = System.currentTimeMillis()
        lifecycleScope.launch(Dispatchers.IO) {
            ServiceLocator.getDbInstance().userDao.updateUserDeletionTime(userId, currentTime)
        }
    }

}