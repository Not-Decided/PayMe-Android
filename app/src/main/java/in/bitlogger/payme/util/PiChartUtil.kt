package `in`.bitlogger.payme.util

import android.R
import android.graphics.Color
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF

class PiChartUtil(piChart: PieChart) {

    companion object {
        fun applySettings(piChart: PieChart): PieChart {
            piChart.apply {
                setUsePercentValues(false)
                description.isEnabled = false
                setExtraOffsets(5f, 10f, 5f, 5f)
                dragDecelerationFrictionCoef = 0.95f

                centerText = ""
                setDrawCenterText(false)

                isDrawHoleEnabled = true
                setHoleColor(Color.WHITE)
                setTransparentCircleColor(Color.WHITE)
                setTransparentCircleAlpha(110)
                holeRadius = 60f
                transparentCircleRadius = 61f

                rotationAngle = 270f
                isRotationEnabled = true
                isHighlightPerTapEnabled = true

                animateY(1400, Easing.EaseInOutQuad)

                setEntryLabelColor(Color.WHITE)
                setEntryLabelTextSize(12f)
            }
            return piChart
        }

        fun setData(piChart: PieChart, entries: ArrayList<PieEntry>) {
            val dataSet = PieDataSet(entries, "Total Expenses")
            dataSet.apply {
                setDrawIcons(false)
                sliceSpace = 3f
                iconsOffset = MPPointF(0f, 40f)
                selectionShift = 10f
            }

            // add a lot of colors
            val colors: ArrayList<Int> = ArrayList()
            for (c in ColorTemplate.JOYFUL_COLORS) colors.add(c)
            dataSet.colors = colors


            val data = PieData(dataSet)
            data.setValueFormatter(PercentFormatter())
            data.setValueTextSize(16f)
            data.setValueTextColor(Color.WHITE)

            piChart.data = data

            piChart.invalidate()
        }
    }

    private fun chartLegend(piChart: PieChart) = piChart.legend.apply {
        verticalAlignment = Legend.LegendVerticalAlignment.TOP
        horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        orientation = Legend.LegendOrientation.VERTICAL
        setDrawInside(false)
        xEntrySpace = 7f
        yEntrySpace = 0f
        yOffset = 0f
    }
}