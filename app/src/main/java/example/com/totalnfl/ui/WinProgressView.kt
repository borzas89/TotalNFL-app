package example.com.totalnfl.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import example.com.totalnfl.R
import example.com.totalnfl.util.dp
import example.com.totalnfl.util.f


class WinProgressView : View {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet,
                defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    val paint = Paint()
    private var sum = 100.0f

    var winHomeFloat = 0.0f
        set(value) {
            field = value

            postInvalidate()
        }

    var winAwayFloat = 0.0f
        set(value) {
            field = value

            postInvalidate()
        }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        var currentLeft = (winAwayFloat / sum) * width
        var rightToAdd = (winHomeFloat / sum) * width


        if(winAwayFloat > 50.0f){
            paint.color = ContextCompat.getColor(context, R.color.team_blue_light)
            this.setBackgroundColor(ContextCompat.getColor(context, R.color.green))
        } else {
            paint.color = ContextCompat.getColor(context, R.color.green)
            this.setBackgroundColor(ContextCompat.getColor(context, R.color.team_blue_light))
        }

        canvas.drawRoundRect(currentLeft, 0f, (currentLeft + rightToAdd).coerceAtMost(width.f),
                dp(10), 100f, 100f, paint)
        currentLeft += rightToAdd


    }

}