package com.itis.android_homework.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.snackbar.Snackbar
import com.itis.android_homework.R
import com.itis.android_homework.adapter.AnswerAdapter
import com.itis.android_homework.base.BaseActivity
import com.itis.android_homework.base.BaseFragment
import com.itis.android_homework.databinding.FragmentQuestionnaireBinding
import com.itis.android_homework.utils.ActionType
import com.itis.android_homework.utils.QARepository


class QuestionnaireFragment(private val questionCount: Int) : BaseFragment(R.layout.fragment_questionnaire) {
    private var _binding: FragmentQuestionnaireBinding? = null
    private val binding get() = _binding!!
    private var questions = QARepository.questions

    private lateinit var questionRepository: QARepository
    private lateinit var answerAdapter: AnswerAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuestionnaireBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        questionRepository = QARepository
        val position = arguments?.getInt(POSITION_KEY, 0) ?: 0
        val question = questionRepository.getQuestionByPosition(position)

        with(binding) {

            questionNumberTv.text = (position + 1).toString()
            questionTv.text = question.question

            answerAdapter = AnswerAdapter(
                items = question.answers,
                onItemChecked = { position ->
                    updateChecked(position)
                    updateButton(finishBtn, questionCount)
                },
                onRootClicked = { position ->
                    updateChecked(position)
                    updateButton(finishBtn, questionCount)
                }
            )

            questionnaireRv.adapter = answerAdapter
            if (savedInstanceState != null) {
                val selectedAnswerIndex = savedInstanceState.getInt(SELECTED_ANSWER_INDEX_KEY, -1)
                if (selectedAnswerIndex != -1) {
                    updateChecked(selectedAnswerIndex)
                }
            }

            if (isAllChecked(questionCount) && position >= questionCount - 1) {
                finishBtn.visibility = View.VISIBLE
            }
            finishBtn.setOnClickListener {
                finishBtn.visibility = View.GONE
                (requireActivity() as? BaseActivity)?.goToScreen(
                    actionType = ActionType.REPLACE,
                    destination = MainPageFragment(),
                    tag = ViewPagerFragment.QUESTIONNAIRE_PAGE_FRAGMENT_TAG,
                    isAddToBackStack = true,
                    )
                Snackbar.make(view, getString(R.string.ending_snackbar), Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val selectedAnswerIndex = answerAdapter.items.indexOfFirst { it.isSelected }
        if (selectedAnswerIndex != -1) {
            outState.putInt(SELECTED_ANSWER_INDEX_KEY, selectedAnswerIndex)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateChecked(position: Int) {
        answerAdapter.items.let {
            it.forEach { answer ->
                answer.isSelected = false
            }
            it[position].isSelected = true
        }
        answerAdapter.notifyDataSetChanged()
    }

    private fun isAllChecked(totalQuestionCount: Int): Boolean {
        var allQuestionsChecked = true
        for (i in 0 until totalQuestionCount) {
            val currentQuestion = questions[i]
            var isAnyAnswerChecked = false
            currentQuestion.answers.forEach { answer ->
                if (answer.isSelected) {
                    isAnyAnswerChecked = true
                    return@forEach
                }
            }
            if (!isAnyAnswerChecked) {
                allQuestionsChecked = false
            }
        }
        return allQuestionsChecked
    }

    private fun updateButton(button: Button, cnt: Int) {
        if (isAllChecked(cnt)) {
            button.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val POSITION_KEY = "position"
        private const val SELECTED_ANSWER_INDEX_KEY = "selected_answer_index"
        private const val QUESTION_COUNT_KEY = "question_count"

        fun newInstance(position: Int, questionCount: Int): QuestionnaireFragment {
            val fragment = QuestionnaireFragment(questionCount)
            val args = Bundle()
            args.putInt(POSITION_KEY, position)
            args.putInt(QUESTION_COUNT_KEY, questionCount)
            fragment.arguments = args
            return fragment
        }
    }
}