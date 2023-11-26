package com.itis.android_homework.ui.fragments.navigation

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.itis.android_homework.MainActivity
import com.itis.android_homework.R
import com.itis.android_homework.base.BaseFragment
import com.itis.android_homework.databinding.FragmentNavigationMainBinding
import com.itis.android_homework.ui.fragments.viewmodel.NotificationSettingsViewModel
import com.itis.android_homework.utils.NotificationsHandler
import com.itis.android_homework.utils.ParamsKey.BUTTONS_VISIBILITY_CHECKED_KEY
import com.itis.android_homework.utils.ParamsKey.DETAILED_MESSAGE_CHECKED_KEY
import com.itis.android_homework.utils.ParamsKey.PRIORITY_SELECTED_KEY
import com.itis.android_homework.utils.ParamsKey.PRIVACY_SELECTED_KEY


class MainScreenFragment : BaseFragment(R.layout.fragment_navigation_main) {

    private var viewBinding: FragmentNavigationMainBinding? = null
    private val notificationHandler = NotificationsHandler()

    private val sharedViewModel: NotificationSettingsViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding = FragmentNavigationMainBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        setButtonAvailability(airplaneMode = (requireActivity() as MainActivity).getAirplaneMode())

        sharedViewModel.airplaneMode.observe(viewLifecycleOwner) { isAirplaneModeOn ->
            setButtonAvailability(isAirplaneModeOn)
        }

        viewBinding!!.mainScreenActionBtn.setOnClickListener {

            val notificationHeader = viewBinding!!.notificationHeaderEt.text.toString()
            val notificationMessage = viewBinding!!.notificationMessageEt.text.toString()
            var selectedPriorityId = 3
            var selectedPrivacyId = 1
            var isDetailedMessageChecked = false
            var isButtonsVisibilityChecked = false

            var notificationSettingsBundle : Bundle
            sharedViewModel.bundleData.observe(viewLifecycleOwner) { bundle ->
                notificationSettingsBundle = bundle
                notificationSettingsBundle.let {

                    selectedPriorityId = it.getInt(PRIORITY_SELECTED_KEY)
                    selectedPrivacyId = it.getInt(PRIVACY_SELECTED_KEY)
                    isDetailedMessageChecked = it.getBoolean(DETAILED_MESSAGE_CHECKED_KEY)
                    isButtonsVisibilityChecked = it.getBoolean(BUTTONS_VISIBILITY_CHECKED_KEY)

                }
            }

            notificationHandler.createNotification(requireContext(), notificationHeader, notificationMessage,
                selectedPriorityId, selectedPrivacyId, isDetailedMessageChecked, isButtonsVisibilityChecked )
        }
    }

    fun setButtonAvailability(airplaneMode : Boolean) {
        if (airplaneMode) {

            viewBinding!!.mainScreenActionBtn.isEnabled = false

            val message = "Нет доступа к сети"
            AlertDialog.Builder(context)
                .setMessage(message)
                .show()
        } else {

            viewBinding!!.mainScreenActionBtn.isEnabled = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}