package co.geisyanne.instagram.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ProgressBar
import co.geisyanne.instagram.R

class LoadingButton : FrameLayout {

    private lateinit var button: Button
    private lateinit var progressBar: ProgressBar
    private var text : String? = null  // PODE SER Q O USER NÃO DEFINA NO XML O ATRIBUTO

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setup(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAtte: Int) : super(context, attrs, defStyleAtte) {
        setup(context, attrs)
    }

    private fun setup (context: Context,attrs: AttributeSet?) {
        val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.button_loading, this)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoadingButton, 0 ,0)
        text = typedArray.getString(R.styleable.LoadingButton_text)

        button = getChildAt(0) as Button
        progressBar = getChildAt(1) as ProgressBar

        button.text = text
        button.isEnabled = false

        typedArray.recycle()
    }

    override fun setEnabled(enabled: Boolean) { //  FRAME LAYOUT = BTN
        super.setEnabled(enabled)
        button.isEnabled = enabled
    }

    override fun setOnClickListener(l: OnClickListener?) {
        button.setOnClickListener(l)
    }

    public fun showProgress(enabled: Boolean) {
        if (enabled) {
            button.text = ""
            button.isEnabled = false
            progressBar.visibility = View.VISIBLE
        } else {
            button.text = text
            button.isEnabled = true
            progressBar.visibility = View.GONE
        }
    }

}