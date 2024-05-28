package com.itis.android_homework

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Shader
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import kotlin.math.sqrt

class CustomGraphView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }

    private val pointPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private val gradientPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private val gridPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = Color.GRAY
        strokeWidth = 1f
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 20f
        color = Color.BLACK
    }

    private var dataPoints: List<Pair<Float, Float>> = emptyList()
    private var showGradient = false
    private var pointColor = Color.RED
    private var lineColor = Color.BLUE
    private var lineWidth = 4f
    private var gradientColor = Color.LTGRAY
    private var gridColor = Color.GRAY

    private var minX = 0f
    private var maxX = 0f
    private var minY = 0f
    private var maxY = 0f

    private var selectedPointIndex = -1

    private var popupWindow: PopupWindow? = null

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomGraphView)
        try {
            showGradient = typedArray.getBoolean(R.styleable.CustomGraphView_showGradient, false)
            pointColor = typedArray.getColor(R.styleable.CustomGraphView_pointColor, Color.RED)
            lineColor = typedArray.getColor(R.styleable.CustomGraphView_lineColor, Color.BLUE)
            lineWidth = typedArray.getDimension(R.styleable.CustomGraphView_lineWidth, 4f)
            gradientColor = typedArray.getColor(R.styleable.CustomGraphView_gradientColor, Color.LTGRAY)
            gridColor = typedArray.getColor(R.styleable.CustomGraphView_gridColor, Color.GRAY)
        } finally {
            typedArray.recycle()
        }
        pointPaint.color = pointColor
        pointPaint.strokeWidth = 15f // Увеличенный размер точек
        paint.color = lineColor
        paint.strokeWidth = lineWidth
        gridPaint.color = gridColor
        updateGradientShader()
    }

    private fun updateGradientShader() {
        gradientPaint.shader = LinearGradient(0f, 0f, 0f, height.toFloat(), gradientColor, Color.WHITE, Shader.TileMode.CLAMP)
    }

    fun setData(points: List<Pair<Float, Float>>) {
        dataPoints = points
        minX = dataPoints.minOf { it.first }
        maxX = dataPoints.maxOf { it.first }
        minY = dataPoints.minOf { it.second }
        maxY = dataPoints.maxOf { it.second }
        updateGradientShader()
        invalidate()
    }

    fun setShowGradient(show: Boolean) {
        showGradient = show
        invalidate()
    }

    fun setPointColor(color: Int) {
        pointColor = color
        pointPaint.color = color
        invalidate()
    }

    fun setLineColor(color: Int) {
        lineColor = color
        paint.color = color
        invalidate()
    }

    fun setLineWidth(width: Float) {
        lineWidth = width
        paint.strokeWidth = width
        invalidate()
    }

    fun setGradientColor(color: Int) {
        gradientColor = color
        updateGradientShader()
        invalidate()
    }

    fun setGridColor(color: Int) {
        gridColor = color
        gridPaint.color = color
        invalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        updateGradientShader()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.WHITE)

        drawGrid(canvas)

        if (showGradient && dataPoints.isNotEmpty()) {
            drawGradient(canvas)
        }

        drawGraphLine(canvas)
        drawPoints(canvas)
    }

    private fun drawGrid(canvas: Canvas) {
        val gridSpacingX = width / 10f
        val gridSpacingY = height / 10f

        for (i in 0..10) {
            val x = i * gridSpacingX
            val y = i * gridSpacingY
            canvas.drawLine(x, 0f, x, height.toFloat(), gridPaint)
            canvas.drawLine(0f, y, width.toFloat(), y, gridPaint)
        }
    }

    private fun drawGradient(canvas: Canvas) {
        val path = Path().apply {
            moveTo(mapX(dataPoints[0].first), mapY(dataPoints[0].second))
            for (point in dataPoints) {
                lineTo(mapX(point.first), mapY(point.second))
            }
            lineTo(mapX(dataPoints.last().first), height.toFloat())
            lineTo(mapX(dataPoints.first().first), height.toFloat())
            close()
        }
        canvas.drawPath(path, gradientPaint)
    }

    private fun drawGraphLine(canvas: Canvas) {
        if (dataPoints.size > 1) {
            var previousX = mapX(dataPoints[0].first)
            var previousY = mapY(dataPoints[0].second)
            for (i in 1 until dataPoints.size) {
                val x = mapX(dataPoints[i].first)
                val y = mapY(dataPoints[i].second)
                canvas.drawLine(previousX, previousY, x, y, paint)
                previousX = x
                previousY = y
            }
        }
    }

    private fun drawPoints(canvas: Canvas) {
        for ((index, point) in dataPoints.withIndex()) {
            val x = mapX(point.first)
            val y = mapY(point.second)
            canvas.drawCircle(x, y, pointPaint.strokeWidth / 2, pointPaint)

        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                selectedPointIndex = findClosestPoint(event.x, event.y)
                if (selectedPointIndex != -1) {
                    val point = dataPoints[selectedPointIndex]
                    showPopup(point)
                    invalidate()
                }
            }
            MotionEvent.ACTION_UP -> {
                selectedPointIndex = -1
                dismissPopup()
                invalidate()
            }
        }
        return true
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun showPopup(point: Pair<Float, Float>) {
        val popupView = LayoutInflater.from(context).inflate(R.layout.popup_point_info, null)
        val textView = popupView.findViewById<TextView>(R.id.point_info)
        textView.text = "(${point.first}, ${point.second})"

        popupWindow = PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
            isFocusable = true
            showAtLocation(this@CustomGraphView, android.view.Gravity.CENTER, 0, 0)
        }

        popupView.setOnTouchListener { _, _ ->
            dismissPopup()
            true
        }
    }

    private fun dismissPopup() {
        popupWindow?.dismiss()
        popupWindow = null
    }

    private fun mapX(x: Float): Float {
        return mapValue(x, minX, maxX, 0f, width.toFloat())
    }

    private fun mapY(y: Float): Float {
        return mapValue(y, minY, maxY, height.toFloat(), 0f)
    }

    private fun mapValue(value: Float, fromMin: Float, fromMax: Float, toMin: Float, toMax: Float): Float {
        return ((value - fromMin) / (fromMax - fromMin)) * (toMax - toMin) + toMin
    }

    private fun findClosestPoint(x: Float, y: Float): Int {
        var closestDistance = Float.MAX_VALUE
        var closestIndex = -1
        for ((index, point) in dataPoints.withIndex()) {
            val dx = mapX(point.first) - x
            val dy = mapY(point.second) - y
            val distance = sqrt((dx * dx + dy * dy).toDouble()).toFloat()
            if (distance < closestDistance) {
                closestDistance = distance
                closestIndex = index
            }
        }
        return closestIndex
    }
}
