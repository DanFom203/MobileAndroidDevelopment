package com.itis.android_homework.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.itis.android_homework.R
import com.itis.android_homework.base.BaseActivity
import com.itis.android_homework.base.BaseFragment
import com.itis.android_homework.databinding.FragmentMainPageBinding
import com.itis.android_homework.utils.ActionType
import com.itis.android_homework.utils.ParamsKey

class MainPageFragment : BaseFragment(R.layout.fragment_main_page) {
    private var _viewBinding: FragmentMainPageBinding? = null
    private val viewBinding: FragmentMainPageBinding
        get() = _viewBinding!!
    private var phoneNumberEt : EditText? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _viewBinding = FragmentMainPageBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        phoneNumberEt = requireActivity().findViewById(R.id.phone_number_et)
        phoneNumberEt?.addTextChangedListener(CurrencyTextWatcher())
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

    private fun initViews() {
        with(viewBinding) {
            mainPageActionBtn.setOnClickListener {
                if (phoneNumberEt.text.toString().isEmpty() ||
                    phoneNumberEt.text.toString()[0] == '+' && phoneNumberEt.text.toString().length < 18 ||
                    phoneNumberEt.text.toString()[0] == '8' && phoneNumberEt.text.toString().length < 17) {
                    phoneNumberEt.error = getString(R.string.invalid_number)
                    return@setOnClickListener
                }
                if (countOfQuestionsEt.text.toString().isEmpty()) {
                    countOfQuestionsEt.error = getString(R.string.invalid_count)
                    return@setOnClickListener
                } else { ParamsKey.COUNT_OF_QUESTIONS_KEY = countOfQuestionsEt.text.toString().toInt() }

                (requireActivity() as? BaseActivity)?.goToScreen(
                    actionType = ActionType.REPLACE,
                    destination = ViewPagerFragment.newInstance(ParamsKey.COUNT_OF_QUESTIONS_KEY),
                    tag = ViewPagerFragment.QUESTIONNAIRE_PAGE_FRAGMENT_TAG,
                    isAddToBackStack = true,
                )
            }
        }
    }

    companion object {
        const val MAIN_PAGE_FRAGMENT_TAG = "MAIN_PAGE_FRAGMENT_TAG"

        fun newInstance() = MainPageFragment()
    }
}