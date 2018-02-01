package com.softtek.lai.widgets.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.List;

/**
 * Created by jerry.guan on 11/8/2016.
 */

public class BrokenLine extends View {

    public static final int NORMAL = 0;
    public static final int INTEGER = 1;

    private static int offset;
    private DataLine dataLine;
    private int format;

    private float maxYAxis;//y轴的最大值
    int[] xPoint;//x轴点的坐标数组

    private Paint textPaint;//折线图字体画笔
    private Paint aTextPaint;//折线图标注画笔
    private Paint linePaint;//折线图线条画笔
    private Paint bgPaint;//折线图背景颜色


    public BrokenLine(Context context) {
        super(context);
        init();
    }

    public BrokenLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BrokenLine(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        offset = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                10, getContext().getResources().getDisplayMetrics());
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                12, getContext().getResources().getDisplayMetrics()));
        aTextPaint = new Paint();
        aTextPaint.setAntiAlias(true);
        aTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        aTextPaint.setColor(Color.WHITE);
        aTextPaint.setTextAlign(Paint.Align.CENTER);
        aTextPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                9, getContext().getResources().getDisplayMetrics()));

        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(0x4CFFFFFF);

        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setStyle(Paint.Style.FILL_AND_STROKE);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth() - offset * 2;
        int height = getHeight();
        int chartHeight = (int) (height - TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                22, getContext().getResources().getDisplayMetrics()));

        //画x轴的线
        linePaint.setStrokeWidth(1);
        canvas.drawLine(offset, chartHeight, width, chartHeight, linePaint);

        //画x轴数据同时确定x轴的点
        if (dataLine != null) {
            List<String> xAxis = dataLine.getxAxis();
            if (xAxis != null && !xAxis.isEmpty()) {
                xPoint = new int[xAxis.size()];
                int x = offset;
                Paint.FontMetrics fm = textPaint.getFontMetrics();
                int textHeight = (int) Math.ceil(fm.descent - fm.ascent) / 2;
                //用控件的宽度/x轴的数量
                int textWidth;
                if (xAxis.size() == 1) {
                    String str = xAxis.get(0);
                    textWidth = (int) textPaint.measureText(str, 0, str.length());
                } else {
                    textWidth = width / (xAxis.size() - 1);
                }
                for (int i = 0, j = xAxis.size(); i < j; i++) {
                    String str = xAxis.get(i);
                    if (i == 0) {
                        xPoint[i] = x;
                        canvas.drawText(str, x, height - textHeight, textPaint);
                    } else if (i == j - 1) {
                        xPoint[i] = x - offset;
                        canvas.drawText(str, x - offset - textPaint.measureText(str, 0, str.length()) / 2, height - textHeight, textPaint);
                    } else {
                        xPoint[i] = x;
                        canvas.drawText(str, x - textPaint.measureText(str, 0, str.length()) / 2, height - textHeight, textPaint);
                    }
                    x += textWidth;
                }
            }
            List<Entry> yAxis = dataLine.getEntries();
            if (yAxis != null && !yAxis.isEmpty()) {
                maxYAxis = dataLine.getMaxYAxis();
                double per = 0;
                if (maxYAxis != 0) {
                    per = (chartHeight - getPaddingTop() + 7) * 1d / maxYAxis;
                }
                Point[] broken = new Point[yAxis.size()];
                //有数据，就画一个路径框
                Path path = new Path();
                //从右下角开始画一个路径框
                Entry firstEntry = yAxis.get(0);
                //移动到第一个点
                path.moveTo(xPoint[firstEntry.getIndex()], chartHeight);
                bgPaint.setColor(0x99FFFFFF);
                for (int i = 0, j = yAxis.size(); i < j; i++) {
                    Entry entry = yAxis.get(i);
                    if (entry.getIndex() < xPoint.length) {
                        int x = xPoint[entry.getIndex()];
                        int y = (int) (chartHeight - per * (entry.getVal() < 0 ? 0 : entry.getVal()));
                        path.lineTo(x, y);
                        broken[i] = new Point(x, y);
                        //画圆点
                        canvas.drawCircle(x, y, 5, bgPaint);
                        //标数字
                        String value;
                        if (format == 0) {
                            value = String.valueOf(entry.getVal());
                        } else {
                            int entryVal = (int) entry.getVal();
                            value = String.valueOf(entryVal);
                        }
                        canvas.drawText(value, x, y - 10, aTextPaint);
                    }
                }
                Entry endEntry = yAxis.get(yAxis.size() - 1);
                if (endEntry.getIndex() < xPoint.length) {
                    path.lineTo(xPoint[endEntry.getIndex()], chartHeight);
                }
                bgPaint.setShader(new LinearGradient(width / 2, 0, width / 2, chartHeight, 0x8CFFFFFF,
                        0X19FFFFFF, Shader.TileMode.REPEAT));
                path.close();
                canvas.drawPath(path, bgPaint);
                //画折线
                linePaint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        1.5f, getContext().getResources().getDisplayMetrics()));
                for (int i = 1, j = broken.length; i < j; i++) {
                    Point spoint = broken[i - 1];
                    Point epoint = broken[i];
                    canvas.drawLine(spoint.x, spoint.y, epoint.x, epoint.y, linePaint);
                }
            }

        }

    }

    public String getUnit(int value) {
        if (value < 10) {
            return "";
        } else if (value < 100) {
            return "十";
        } else if (value < 1000) {
            return "百";
        } else if (value < 10000) {
            return "千";
        } else if (value < 100000) {
            return "万";
        }
        return "";
    }

    public void setData(DataLine dataLine) {
        this.format = NORMAL;
        this.dataLine = dataLine;
        postInvalidate();
    }

    public void setData(DataLine dataLine, int format) {
        this.format = format;
        this.dataLine = dataLine;
        postInvalidate();
    }


    public void setxTextSize(float xTextSize) {
        textPaint.setTextSize(xTextSize);
    }
}
