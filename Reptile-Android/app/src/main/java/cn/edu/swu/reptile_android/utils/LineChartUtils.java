package cn.edu.swu.reptile_android.utils;


import android.graphics.Color;
import android.graphics.Matrix;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 折线图表
 */
public class LineChartUtils {

    /**
     * 初始化图表
     *
     * @param chart 原始图表
     * @return 初始化后的图表
     */
    public static LineChart initChart(LineChart chart, boolean isXEnbale, boolean isLeftYEnable, boolean isRightYEnable) {
        // 不显示表格颜色
        chart.setDrawGridBackground(false);
        chart.setDrawBorders(false);
        chart.setDragEnabled(true);//是否可以用手指移动图表
        chart.setScaleEnabled(false);//设置为false以禁止通过在其上双击缩放图表。是否可以缩放图表，当屏幕一屏显示不下，希望能通过缩放或滑动图表，仅设置这个和上面那个是不行的，还需要配合Matrix来实现
        chart.setTouchEnabled(true);
        chart.animateX(1500);
        chart.setDoubleTapToZoomEnabled(false);
        //下方3个设置ScaleEnabled,DragEnabled用到，表示x轴放大4倍，y不变
//        Matrix matrix=new Matrix();
//        matrix.postScale(4.0f,0.0f);
//        chart.getViewPortHandler().refresh(matrix,chart,false);
        //禁止x轴y轴同时进行缩放
        chart.setPinchZoom(false);
        // 不显示数据描述
        Description description = chart.getDescription();
        description.setEnabled(false);
        chart.setDescription(description);

        chart.setDragDecelerationEnabled(false);//拖拽滚动时，手放开是否会持续滚动，默认是true（false是拖到哪是哪，true拖拽之后还会有缓冲）
        chart.setDragDecelerationFrictionCoef(0.88f);//与上面那个属性配合，持续滚动时的速度快慢，[0,1) 0代表立即停止。
        // 不显示图例
        Legend legend = chart.getLegend();
        legend.setEnabled(false);
        chart.setNoDataTextColor(Color.parseColor("#999999"));
        chart.setNoDataText("你还没有记录数据");
        initLineChart(chart, isXEnbale, isLeftYEnable, isRightYEnable);
        chart.invalidate();
        return chart;
    }

    /**
     * 初始化xy轴
     *
     * @param chart
     */
    public static void initLineChart(LineChart chart, boolean isXEnbale, boolean isLeftYEnable, boolean isRightYEnable) {
        setXAxisBasic(chart, isXEnbale);
        setLeftYAxisBasic(chart, isLeftYEnable);
        setRightYAxisBasic(chart, isRightYEnable);
    }

    /**
     * x轴基础设置
     *
     * @param chart
     * @param isEnable 设置x轴启用或禁用
     */
    public static void setXAxisBasic(LineChart chart, boolean isEnable) {
        //得到x轴
        XAxis xAxis = chart.getXAxis();
        //是否调用x轴
        xAxis.setEnabled(isEnable);
        xAxis.setDrawAxisLine(true);//是否绘制x轴的直线
        xAxis.setDrawGridLines(true);//是否画网格线
        xAxis.setGridColor(Color.GRAY);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);// 设置x轴数据的位置
        xAxis.setTextSize(3);//设置轴标签字体大小
        xAxis.setTextColor(Color.parseColor("#999999"));//设置轴标签字体的颜色

        xAxis.setAvoidFirstLastClipping(true);//图表将避免第一个和最后一个标签条目被减掉在图表或屏幕的边缘
        //设置竖线的显示样式为虚线  lineLength控制虚线段的长度  spaceLength控制线之间的空间
        xAxis.enableGridDashedLine(10f, 10f, 0f);

    }

    /**
     * 左侧y轴基础设置
     *
     * @param chart
     * @param isEnable
     */
    public static void setLeftYAxisBasic(LineChart chart, boolean isEnable) {
        //不显示y轴左边的值
        chart.getAxisLeft().setEnabled(false);

    }

    /**
     * 右侧y轴基础设置
     *
     * @param chart
     * @param isEnable
     */
    public static void setRightYAxisBasic(LineChart chart, boolean isEnable) {
        // 不显示y轴右边的值
        chart.getAxisRight().setEnabled(false);

    }

    /**
     * x轴在点击切换后重新设置的值
     *
     * @param chart
     * @param labelCount
     * @param maximum
     * @param xRangeMaximum
     */
    public static void setXAxis(LineChart chart, int labelCount, float maximum, int xRangeMaximum) {
        chart.setScaleMinima(1.0f, 1.0f);
        chart.getViewPortHandler().refresh(new Matrix(), chart, true);
        //上面两行必须放最上面否则折线间隔失效
        XAxis xAxis = chart.getXAxis();
        xAxis.setLabelCount(labelCount, true);// 设置X轴的刻度数量，第二个参数表示是否平均分配
        xAxis.setAxisMinimum(-1f);
//        xAxis.setAxisMinimum(0f);
//        xAxis.setAxisMaximum(maximum);//设置X轴的值（最小值、最大值、然后会根据设置的刻度数量自动分配刻度显示）
        xAxis.setAxisMaximum(maximum + 1f);//设置X轴的值（最小值、最大值、然后会根据设置的刻度数量自动分配刻度显示）
        xAxis.setGranularity(1f);//设置x轴坐标之间的最小间隔（因为此图有缩放功能，X轴,Y轴可设置可缩放），放在setValueFormatter之前设置
        //设置当前图表中最多在x轴坐标线上显示的刻度线总量为6
        chart.setVisibleXRangeMaximum(xRangeMaximum);//这行必须放到下面，和setLabelCount同时设置，值-1
        chart.setScaleEnabled(false);
        chart.setDoubleTapToZoomEnabled(false);
//        chart.moveViewToX(0f);//切换时间时用来将折线图回归到0点
        chart.moveViewToX(-1f);//切换时间时用来将折线图回归到0点

    }


    /**
     * 显示无数据的提示
     *
     * @param chart
     */
    public static void NotShowNoDataText(Chart chart) {
        chart.clear();
        chart.notifyDataSetChanged();
        chart.setNoDataTextColor(Color.parseColor("#999999"));
        chart.setNoDataText("你还没有记录数据");
        chart.invalidate();
    }


    /**
     * 更新图表
     *
     * @param chart  图表
     * @param values 数据点
     */
    public static void notifyDataSetChanged(LineChart chart, List<Entry> values,
                                            List<String> datelist) {
        chart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if (value<0){
                    return "";
                }
                int index = (int) value;
                if (index < 0 || index >= datelist.size()) {
//                    return "" + (int) value;
                    return "";
                }
                return datelist.get(index);
            }
        });
        chart.invalidate();
        setChartData(chart, values, LineDataSet.Mode.CUBIC_BEZIER);
    }

    /**
     * 设置图表数据
     *
     * @param chart  图表
     * @param values xy数据集合
     */
    public static void setChartData(LineChart chart, List<Entry> values, LineDataSet.Mode mode) {
        LineDataSet lineDataSet;
        //getDataSetCount() 总线条数,getDataSetByIndex(0) 索引0处的xy数据的集合（30天就是30个 0-29）
        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {
            lineDataSet = (LineDataSet) chart.getData().getDataSetByIndex(0);
            lineDataSet.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            //初始化折线
            lineDataSet = new LineDataSet(values, "");
            // 设置曲线颜色
            lineDataSet.setColor(Color.parseColor("#FFCC33"));

            if (mode == null) {
                //设置曲线展示为圆滑曲线（如果不设置则默认折线)
                lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            } else {
                lineDataSet.setMode(mode);
            }
            // 不显示坐标点的小圆点
            lineDataSet.setDrawCircles(true);
            // 是否显示坐标点的数据，折线上是否绘制数据
            lineDataSet.setDrawValues(true);
            lineDataSet.setValueTextColor(Color.RED);//数据的字体颜色
            lineDataSet.setValueTextSize(8f);//设置显示数据的值的文字大小

            lineDataSet.setLineWidth(1f);//折线的宽度
            lineDataSet.setCircleRadius(2f);//数据点的半径

            // 不显示定位线，是否禁用点击高亮线
            lineDataSet.setHighlightEnabled(false);
            lineDataSet.enableDashedHighlightLine(10f, 5f, 0f);//点击后的高亮线的显示样式
            lineDataSet.setHighlightLineWidth(2f);//设置点击交点后显示高亮线宽
            lineDataSet.setHighLightColor(Color.RED);//设置点击交点后显示交高亮线的颜色

            lineDataSet.setCircleColor(Color.RED);
            lineDataSet.setDrawFilled(false);//是否对数据范围背景进行填充
            lineDataSet.setDrawCircleHole(false);// 数据点圆是否为空心圆
            final DecimalFormat format = new DecimalFormat("###,###,##0");
            lineDataSet.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return format.format(value);
                }
            });
            //管理数据集
            LineData data = new LineData(lineDataSet);
            chart.setData(data);
            chart.invalidate();
        }

    }
}
