package com.wilker.weatherapp.ui.component

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import androidx.core.view.doOnLayout
import androidx.core.view.updateLayoutParams
import com.wilker.weatherapp.R
import com.wilker.weatherapp.databinding.ViewProgressButtonBinding
import kotlin.math.roundToInt

@RequiresApi(Build.VERSION_CODES.N)
class ProgressButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : FrameLayout(context, attrs) {

    val binding by lazy {
        ViewProgressButtonBinding.inflate(LayoutInflater.from(context))
    }

    private var textButton = EMPTY_TEXT
    private var enabledButton = false
    private var initialButtonWith = DEFAULT_INITIAL
    private var targetButtonWith = DEFAULT_INITIAL
    private var initialTextSize = DEFAULT_INITIAL_FLOAT
    private var oldWidthInitial = DEFAULT_INITIAL
    private var oldCornersRadius = DEFAULT_INITIAL
    private var oldInitialTextSize = DEFAULT_INITIAL_FLOAT
    private lateinit var drawable: GradientDrawable

    init {
        addView(binding.root)
        binding.btnSearch.doOnLayout {
            initialButtonWith = it.measuredWidth
            oldWidthInitial = it.measuredWidth
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                oldCornersRadius = drawable.cornerRadius.roundToInt()
            }
            targetButtonWith = it.measuredHeight
            initialTextSize = binding.btnSearch.textSize
            oldInitialTextSize = binding.btnSearch.textSize
        }
        drawable = binding.btnSearch.background as GradientDrawable
        getInfoButton(context, attrs)
    }

    private fun getInfoButton(context: Context, attrs: AttributeSet?) {
        context.withStyledAttributes(attrs, R.styleable.ProgressButton) {
            textButton = getString(R.styleable.ProgressButton_textButton).toString()
            setText(textButton)

            enabledButton = getBoolean(R.styleable.ProgressButton_enabledButton, true)
            setEnabledButton(enabledButton)
        }
    }

    fun setText(text: String) {
        binding.btnSearch.text = text
    }

    fun setEnabledButton(enabled: Boolean) {
        binding.btnSearch.isEnabled = enabled
    }

    fun setTextSize(size: Float) {
        binding.btnSearch.textSize = size
    }

    fun progress() {
        val targetWith = targetButtonWith
        val targetCornerRadius = targetButtonWith
        val cornerRadiusAnimator = cornerRadiusAnimator(drawable, targetCornerRadius)
        val textSizeAnimator =
            textSizeAnimator(initialTextSize, DEFAULT_INITIAL_FLOAT, binding.btnSearch)
        val widthAnimator = widthAnimator(binding.btnSearch.measuredWidth, targetWith)

        progressBarAlphaAnimator(
            view = binding.pbsearch,
            propertyName = "alpha",
            initialValue = ALPHA_INITIAL,
            finalValue = ALPHA_FINAL
        )

        cornerRadiusAnimator?.start()
        textSizeAnimator?.start()
        widthAnimator?.start()
    }

    private fun textSizeAnimator(
        initialValue: Float,
        finalValue: Float,
        textView: TextView
    ): ValueAnimator? {
        return ValueAnimator.ofFloat(initialValue, finalValue).apply {
            this.duration = TIME_ANIMATION
            this.addUpdateListener {
                val textSizeSp = (it.animatedValue as Float) / resources.displayMetrics.density
                textView.textSize = textSizeSp
                initialTextSize = textSizeSp
            }
        }
    }

    private fun progressBarAlphaAnimator(
        view: View,
        propertyName: String,
        initialValue: Float,
        finalValue: Float
    ) {
        ObjectAnimator.ofFloat(view, propertyName, initialValue, finalValue).apply {
            this.duration = TIME_ANIMATION
            binding.pbsearch.alpha = ALPHA_INITIAL
            binding.pbsearch.visibility = VISIBLE
        }.start()
    }

    private fun widthAnimator(initialValue: Int, targetWith: Int): ValueAnimator? {
        return ValueAnimator.ofInt(initialValue, targetWith).apply {
            this.duration = TIME_ANIMATION
            this.addUpdateListener {
                binding.btnSearch.updateLayoutParams {
                    this.width = it.animatedValue as Int
                }
            }
        }
    }

    private fun cornerRadiusAnimator(
        drawable: GradientDrawable,
        targetCornerRadius: Int
    ): ValueAnimator? {
        return ValueAnimator.ofFloat(drawable.cornerRadius, targetCornerRadius.toFloat()).apply {
            this.duration = TIME_ANIMATION
            this.addUpdateListener {
                drawable.cornerRadius = it.animatedValue as Float
                binding.btnSearch.background = drawable
            }
        }

    }

    fun reverseAnimation(onReverse: () -> Unit) {
        binding.pbsearch.visibility = GONE
        widthAnimator(targetButtonWith, oldWidthInitial)?.start()
        cornerRadiusAnimator(drawable, oldCornersRadius)?.start()
        textSizeAnimator(DEFAULT_INITIAL_FLOAT, oldInitialTextSize, binding.btnSearch)?.start()
        onReverse()
    }

    inline fun onClick(crossinline onClick: () -> Unit) {
        binding.btnSearch.setOnClickListener {
            onClick()
        }
        invalidate()
    }

    private companion object {
        const val EMPTY_TEXT = ""
        const val TIME_ANIMATION = 1000L
        const val DEFAULT_INITIAL = 0
        const val DEFAULT_INITIAL_FLOAT = 0F
        const val ALPHA_INITIAL = 0F
        const val ALPHA_FINAL = 1F
    }
}