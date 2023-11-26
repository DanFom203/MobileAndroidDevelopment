package com.itis.android_homework.ui.fragments.navigation

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.SeekBar
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.activityViewModels
import com.itis.android_homework.MainActivity
import com.itis.android_homework.R
import com.itis.android_homework.base.BaseFragment
import com.itis.android_homework.ui.fragments.viewmodel.NotificationSettingsViewModel
import com.itis.android_homework.utils.NotificationsHandler
import com.itis.android_homework.utils.ParamsKey
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CoroutineSettingsFragment : BaseFragment(R.layout.fragment_navigation_coroutine_settings) {
    private var job: Job? = null
    private var numberOfCoroutines: Int = 0
    private var isAsync: Boolean = false
    private var stopOnBackground: Boolean = false
    private val notificationHandler = NotificationsHandler()
    private val sharedViewModel: NotificationSettingsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_navigation_coroutine_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setButtonAvailability(airplaneMode = (requireActivity() as MainActivity).getAirplaneMode())
        val executeBtn = view.findViewById<AppCompatButton>(R.id.execute_btn)
        val seekBar = view.findViewById<SeekBar>(R.id.seekBar)
        val asyncCb = view.findViewById<CheckBox>(R.id.async_cb)
        val stopCb = view.findViewById<CheckBox>(R.id.stop_on_background_cb)

        sharedViewModel.airplaneMode.observe(viewLifecycleOwner) { isAirplaneModeOn ->
            setButtonAvailability(isAirplaneModeOn)
        }

        executeBtn.setOnClickListener {
            startCoroutines(seekBar, asyncCb, stopCb)
        }
    }

    private fun startCoroutines(seekBar : SeekBar, asyncCb : CheckBox, stopCb : CheckBox) {
        numberOfCoroutines = seekBar.progress
        isAsync = asyncCb.isChecked
        stopOnBackground = stopCb.isChecked

        job?.cancel()

        job = CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                repeat(numberOfCoroutines) {
                    if (isAsync) {
                        launch {
                            Log.d("CoroutineExample", "Coroutine $it started")
                            // Simulate some work
                            delay(1000)
                            Log.d("CoroutineExample", "Coroutine $it completed")
                            numberOfCoroutines--
                        }
                    } else {
                        Log.d("CoroutineExample", "Coroutine $it started")
                        // Simulate some work
                        delay(1000)
                        Log.d("CoroutineExample", "Coroutine $it completed")
                        numberOfCoroutines--
                    }
                }
            }
        }.also {
            it.invokeOnCompletion { cause ->
                if (cause == null) {
                    createNotification()
                } else if (cause is CancellationException) {
                    Log.d("CoroutineExample", "Coroutine $numberOfCoroutines cancelled")
                }
                job = null
            }
        }
    }

    private fun createNotification() {
        var selectedPriorityId = 3
        var selectedPrivacyId = 1
        var isDetailedMessageChecked = false
        var isButtonsVisibilityChecked = false

        var notificationSettingsBundle : Bundle
        sharedViewModel.bundleData.observe(viewLifecycleOwner) { bundle ->
            notificationSettingsBundle = bundle
            notificationSettingsBundle.let {

                selectedPriorityId = it.getInt(ParamsKey.PRIORITY_SELECTED_KEY)
                selectedPrivacyId = it.getInt(ParamsKey.PRIVACY_SELECTED_KEY)
                isDetailedMessageChecked = it.getBoolean(ParamsKey.DETAILED_MESSAGE_CHECKED_KEY)
                isButtonsVisibilityChecked = it.getBoolean(ParamsKey.BUTTONS_VISIBILITY_CHECKED_KEY)

            }
        }
        notificationHandler.createNotification(requireContext(), "Coroutines are finished", "My job here is done",
            selectedPriorityId, selectedPrivacyId, isDetailedMessageChecked, isButtonsVisibilityChecked)
    }

    fun setButtonAvailability(airplaneMode : Boolean) {
        if (airplaneMode) {
            // Режим "Полёт" включен
            view?.findViewById<AppCompatButton>(R.id.execute_btn)?.isEnabled = false
            // Отображение вьюшки с предупреждением
            val message = "Нет доступа к сети"
            AlertDialog.Builder(context)
                .setMessage(message)
                .show()
        } else {
            // Режим "Полёт" выключен
            view?.findViewById<AppCompatButton>(R.id.execute_btn)?.isEnabled = true
        }
    }


    override fun onPause() {
        super.onPause()
        if (stopOnBackground) {
            job?.cancel()
            Log.d("CoroutineExample", "Coroutines cancelled on background")
        }
    }
}