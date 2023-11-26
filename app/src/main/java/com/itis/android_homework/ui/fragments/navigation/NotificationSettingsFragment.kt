package com.itis.android_homework.ui.fragments.navigation

import android.app.NotificationManager
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.RadioGroup
import androidx.core.app.NotificationCompat
import androidx.fragment.app.activityViewModels
import com.itis.android_homework.R
import com.itis.android_homework.base.BaseFragment
import com.itis.android_homework.ui.fragments.viewmodel.NotificationSettingsViewModel
import com.itis.android_homework.utils.ParamsKey.BUTTONS_VISIBILITY_CHECKED_KEY
import com.itis.android_homework.utils.ParamsKey.DETAILED_MESSAGE_CHECKED_KEY
import com.itis.android_homework.utils.ParamsKey.PRIORITY_SELECTED_KEY
import com.itis.android_homework.utils.ParamsKey.PRIVACY_SELECTED_KEY

class NotificationSettingsFragment : BaseFragment(R.layout.fragment_navigation_notification_settings) {

    private var selectedPriority: Int = 3
    private var selectedPrivacy: Int = 1
    private var isDetailedMessageChecked: Boolean = false
    private var isButtonsVisibilityChecked: Boolean = false

    private val sharedViewModel: NotificationSettingsViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        savedInstanceState?.let {
            selectedPriority = it.getInt(PRIORITY_SELECTED_KEY)
            selectedPrivacy = it.getInt(PRIVACY_SELECTED_KEY)
            isDetailedMessageChecked = it.getBoolean(DETAILED_MESSAGE_CHECKED_KEY)
            isButtonsVisibilityChecked = it.getBoolean(BUTTONS_VISIBILITY_CHECKED_KEY)
        }

        val radioGroupPriority = view.findViewById<RadioGroup>(R.id.radioGroupPriority)
        val radioGroupPrivacy = view.findViewById<RadioGroup>(R.id.radioGroupPrivacy)
        val detailedMessageCheckbox = view.findViewById<CheckBox>(R.id.detailed_message_cb)
        val buttonsVisibilityCheckbox = view.findViewById<CheckBox>(R.id.buttons_visibility_cb)

        if (selectedPriority != 3) {
            radioGroupPriority.check(selectedPriority)
        }
        if (selectedPrivacy != 1) {
            radioGroupPrivacy.check(selectedPrivacy)
        }
        detailedMessageCheckbox.isChecked = isDetailedMessageChecked
        buttonsVisibilityCheckbox.isChecked = isButtonsVisibilityChecked

        radioGroupPriority.setOnCheckedChangeListener { _, checkedId ->
            selectedPriority = when (checkedId) {
                R.id.radioButtonMedium1 -> NotificationManager.IMPORTANCE_LOW
                R.id.radioButtonHigh1 -> NotificationManager.IMPORTANCE_DEFAULT
                R.id.radioButtonUrgent1 -> NotificationManager.IMPORTANCE_HIGH
                else -> NotificationManager.IMPORTANCE_DEFAULT
            }
        }

        radioGroupPrivacy.setOnCheckedChangeListener { _, checkedId ->
            selectedPrivacy = when (checkedId) {
                R.id.radioButtonMedium2 -> NotificationCompat.VISIBILITY_PUBLIC
                R.id.radioButtonHigh2 -> NotificationCompat.VISIBILITY_SECRET
                R.id.radioButtonUrgent2 -> NotificationCompat.VISIBILITY_PRIVATE
                else -> NotificationCompat.VISIBILITY_PUBLIC
            }
        }
        detailedMessageCheckbox.setOnCheckedChangeListener { _, isChecked ->
            isDetailedMessageChecked = isChecked
        }
        buttonsVisibilityCheckbox.setOnCheckedChangeListener { _, isChecked ->
            isButtonsVisibilityChecked = isChecked
        }

    }


    override fun onSaveInstanceState(outState: Bundle) {

        outState.putInt(PRIORITY_SELECTED_KEY, selectedPriority)
        outState.putInt(PRIVACY_SELECTED_KEY, selectedPrivacy)
        outState.putBoolean(DETAILED_MESSAGE_CHECKED_KEY, isDetailedMessageChecked)
        outState.putBoolean(BUTTONS_VISIBILITY_CHECKED_KEY, isButtonsVisibilityChecked)

        sharedViewModel.bundleData.value = outState

        super.onSaveInstanceState(outState)
    }
}
