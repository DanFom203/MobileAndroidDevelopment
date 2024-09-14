package com.itis.android_homework.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.itis.android_homework.base.BaseActivity
import com.itis.android_homework.utils.ActionType

class FirstComposePageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner.lifecycle))
            setContent {
                MaterialTheme {
                    FirstPageScreen()
                }
            }
        }
    }

    private fun navigationFunction(message: String) {
        (requireActivity() as? BaseActivity)?.goToScreen(
            actionType = ActionType.REPLACE,
            destination = SecondPageFragment.newInstance(message),
            tag = SecondPageFragment.SECOND_PAGE_FRAGMENT_TAG,
            isAddToBackStack = true
        )
        (requireActivity() as? BaseActivity)?.goToScreen(
            actionType = ActionType.REPLACE,
            destination = ThirdPageFragment.newInstance(message),
            tag = ThirdPageFragment.THIRD_PAGE_FRAGMENT_TAG,
            isAddToBackStack = true,
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun FirstPageScreen() {
        var message by remember { mutableStateOf("") }
        var showError by remember { mutableStateOf(false) }

        Scaffold(
            topBar = {
                TopAppBar(
                    colors = topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = { Text("First Page") }
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = message,
                    onValueChange = {
                        message = it
                        if (it.isNotEmpty()) showError = false
                    },
                    label = { Text("Enter your message") },
                    isError = showError,
                    modifier = Modifier.fillMaxWidth()
                )

                if (showError) {
                    Text(
                        text = "Invalid message",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (message.isEmpty()) {
                            showError = true
                        } else {
                            navigationFunction(message)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Next")
                }
            }
        }
    }

    companion object {
        const val FIRST_COMPOSE_PAGE_FRAGMENT_TAG = "FIRST_COMPOSE_PAGE_FRAGMENT_TAG"

        fun newInstance() = FirstComposePageFragment()
    }
}