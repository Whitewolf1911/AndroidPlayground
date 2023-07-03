package com.alibasoglu.androidplayground.ui

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.inputmethod.EditorInfo
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.alibasoglu.androidplayground.R
import com.alibasoglu.androidplayground.databinding.CustomEditTextFieldBinding
import com.alibasoglu.androidplayground.util.gone
import com.alibasoglu.androidplayground.util.visible
import com.alibasoglu.androidplayground.viewBinding
import com.google.android.material.textfield.TextInputLayout.END_ICON_PASSWORD_TOGGLE

class CustomTextInputLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = viewBinding(CustomEditTextFieldBinding::inflate)

    private var hasFocus = false

    private var isPasswordField = false

    private var labelText: String? = null

    var text = ""


    init {
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.CustomTextInputLayout,
            defStyleAttr,
            0
        )

        try {
            labelText = typedArray.getString(
                R.styleable.CustomTextInputLayout_labelText
            )
            isPasswordField =
                typedArray.getBoolean(R.styleable.CustomTextInputLayout_isPasswordField, false)


        } finally {
            typedArray.recycle()
        }


        binding.editText.maxLines = 1
        binding.editText.imeOptions = EditorInfo.IME_ACTION_DONE

        labelText?.let { labelText ->
            with(binding) {
                editText.hint = labelText
                hintTextView.text = labelText
            }
        }

        if (isPasswordField) {
            binding.inputLayout.endIconMode = END_ICON_PASSWORD_TOGGLE
        }

        binding.editText.run {
            addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if (hasFocus.not()) {
                        if (s.toString().isNotEmpty()) {
                            binding.hintTextView.visible()
                        } else {
                            binding.hintTextView.gone()
                        }
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    this@CustomTextInputLayout.text = s.toString()
                }
            })

            setOnFocusChangeListener { _, hasFocus ->
                this@CustomTextInputLayout.hasFocus = hasFocus
                if (hasFocus || binding.editText.text.toString().isNotEmpty()) {
                    binding.hintTextView.visible()
                } else {
                    binding.hintTextView.gone()
                }
            }
        }
    }

    fun setErrorMessage(errorMessage: String) {
        with(binding) {
            inputLayout.error = errorMessage
            editText.background =
                ContextCompat.getDrawable(context, R.drawable.bg_text_input_layout_error)
        }
    }

    fun clearErrorMessage() {
        with(binding) {
            inputLayout.error = null
            editText.background = null
        }
    }
}
