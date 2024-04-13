package com.itis.android_homework.utils

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.itis.android_homework.InceptionApp
import com.itis.android_homework.di.components.AppComponent
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

inline fun <T> Flow<T>.observe(fragment: Fragment, crossinline block: (T) -> Unit): Job {
    val lifecycleOwner = fragment.viewLifecycleOwner
    return lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            collect { data -> block(data) }
        }
    }
}

@Suppress("RecursivePropertyAccessor")
val Context.appComponent: AppComponent
    get() = when (this) {
        is InceptionApp -> appComponent
        else -> this.applicationContext.appComponent
    }

inline fun <reified T : ViewModel> Fragment.lazyViewModel(
    noinline create: (stateHandle: SavedStateHandle) -> T
): Lazy<T> {
    return viewModels<T> {
        AssistedViewModelFactory(this, create)
    }
}