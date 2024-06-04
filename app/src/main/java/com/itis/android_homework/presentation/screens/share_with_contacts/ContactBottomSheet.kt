package com.itis.android_homework.presentation.screens.share_with_contacts

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.itis.android_homework.base.Keys
import com.itis.android_homework.presentation.model.Contact
import com.itis.android_homework.utils.appComponent
import kotlinx.coroutines.launch
import javax.inject.Inject


class ContactBottomSheet : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: ContactViewModel by viewModels { viewModelFactory }
    private var contactList: MutableList<Contact> = mutableListOf()

    override fun onAttach(context: Context) {
        requireContext().appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner.lifecycle))
            setContent {
                MaterialTheme {
                    ContactSheetContent()
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ContactSheetContent() {
        var isBottomSheetOpen by remember { mutableStateOf(true) }
        val coroutineScope = rememberCoroutineScope()
        val bottomSheetState = rememberModalBottomSheetState()

        if (isBottomSheetOpen) {
            ModalBottomSheet(
                sheetState = bottomSheetState,
                onDismissRequest = { isBottomSheetOpen = false }
            ) {
                ContactList(
                    onHideBottomClick = {
                        coroutineScope.launch {
                            bottomSheetState.hide()
                            dismiss()
                        }.invokeOnCompletion {
                            if (!bottomSheetState.isVisible) isBottomSheetOpen = false
                        }
                    }
                )
            }
        }
    }

    @Composable
    fun ContactList(
        onHideBottomClick: () -> Unit
    ) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp)
        ) {
            items(contactList) { contact ->
                ListItem(
                    headlineContent  = { Text(text = contact.name) },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier.clickable {
                        sendMessage(contact.phone)
                        onHideBottomClick()
                    }
                )
            }
            item {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onHideBottomClick
                ) {
                    Text(text = "Cancel")
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeContactData()
        viewModel.fetchContactList(requireContext())
    }

    private fun sendMessage(phoneNumber: String?) {
        val toSms = "smsto:$phoneNumber"

        val city = requireArguments().getString(Keys.CITY_NAME_KEY) ?: ""
        val weatherLong = requireArguments().getFloat(Keys.CITY_LONG_KEY)
        val weatherLat = requireArguments().getFloat(Keys.CITY_LAT_KEY)
        val temp = requireArguments().getFloat(Keys.CITY_TEMPERATURE_KEY)
        val message = "City: $city ($weatherLat, $weatherLong), Temp: $temp"

        val smsIntent = Intent(Intent.ACTION_SENDTO, Uri.parse(toSms))
        smsIntent.putExtra(Keys.MESSAGE_KEY, message)

        startActivity(smsIntent)
    }

    private fun observeContactData() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.contactFlow.collect { flow ->
                    if (flow == null) return@collect
                    contactList = flow.toMutableList()
                }
            }
        }
    }

    companion object {
        fun newInstance(city: String, weatherLong: Float, weatherLat: Float, temp: Float) = ContactBottomSheet().apply {
            arguments = bundleOf(
                Keys.CITY_NAME_KEY to city,
                Keys.CITY_LAT_KEY to weatherLat,
                Keys.CITY_LONG_KEY to weatherLong,
                Keys.CITY_TEMPERATURE_KEY to temp
            )
        }
    }
}