package com.softtek.lai.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.softtek.lai.R;
import com.softtek.lai.module.laicheng.util.StringMath;
import com.softtek.lai.utils.DisplayUtil;

import java.util.List;

/**
 * Created by nolan on 2015/12/2.
 * Email：jy05892485@163.com
 * 各项指标的标准线
 */
public class StandardLine extends View {

    private static final int LINE_HEIGHT_DP = 10;
    private static final int TAG_LINE_WIDTH_DP = 1;

    private Context context;

    private float maxValue;
    private float minValue;
    private float curValue;
    private List<Float> valueList;
    private List<String> colorList;
    private String curColor;
    private SpannableString unit;
    private boolean isInt;

    private float rangeValue;//最大值最小值之差
    private float valueWidth;//每一个值的屏幕宽度

    private int lineHeight;//线的高度
    private int lineTop;//线的top的y坐标

    private int tagLineWidth;//各种标线的宽度

    private float lastEndX;//上一段线段的结束X轴左边

    public StandardLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        lineHeight = DisplayUtil.dip2px(context,LINE_HEIGHT_DP);
        tagLineWidth = DisplayUtil.dip2px(context,TAG_LINE_WIDTH_DP);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if( maxValue == 0 || colorList.size() - valueList.size() != 1 ){
            Paint paintLine = new Paint();
            paintLine.setStrokeWidth(lineHeight);
            paintLine.setColor(getResources().getColor(R.color.background_green));
            RectF rect = new RectF(0, lineTop , getWidth(), lineTop + lineHeight);
            canvas.drawRoundRect(rect,
                    lineHeight / 2, //x轴的半径
                    lineHeight / 2, //y轴的半径
                    paintLine);
            return;
        }
        valueWidth = getWidth()/rangeValue;

        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(DisplayUtil.sp2px(context,10f));
        textPaint.setColor(getResources().getColor(R.color.history_chart_text_color));

        Paint paintLine = new Paint();

        if(valueList.size()!=0) {
            for (int i = 0; i < colorList.size(); i++) {
                paintLine.setStrokeWidth(lineHeight);
                paintLine.setColor(Color.parseColor(colorList.get(i)));

                if (i == 0) {//由于开头结尾需要画圆角，需要先画一个园
                    canvas.drawCircle(lineHeight / 2, lineTop + lineHeight / 2, lineHeight / 2, paintLine);
                    float width = (valueList.get(0) - minValue) * valueWidth;
                    canvas.drawLine(lineHeight / 2, lineTop + lineHeight / 2, width, lineTop + lineHeight / 2, paintLine);
                    lastEndX = width;
                } else if (i == colorList.size() - 1) {
                    canvas.drawCircle(getWidth() - lineHeight / 2, lineTop + lineHeight / 2, lineHeight / 2, paintLine);
                    float width = (maxValue - valueList.get(valueList.size() - 1)) * valueWidth;
                    canvas.drawLine(getWidth() - width, lineTop + lineHeight / 2, getWidth() - lineHeight / 2, lineTop + lineHeight / 2, paintLine);
                    paintLine.setStrokeWidth(tagLineWidth);
                    canvas.drawLine(getWidth() - width, lineTop, getWidth() - width, lineTop + 2 * lineHeight, paintLine);
                    String value = ""+ StringMath.fourRemoveFiveAdd1(""+valueList.get(i - 1));
                    if (isInt)
                        value = ""+(valueList.get(i - 1).intValue());
                    canvas.drawText(value, getWidth() - width, lineTop + 3 * lineHeight, textPaint);
                } else {
                    float width = (valueList.get(i) - valueList.get(i - 1)) * valueWidth;
                    canvas.drawLine(lastEndX, lineTop + lineHeight / 2, lastEndX + width, lineTop + lineHeight / 2, paintLine);
                    paintLine.setStrokeWidth(tagLineWidth);
                    canvas.drawLine(lastEndX, lineTop, lastEndX, lineTop + 2 * lineHeight, paintLine);
                    String value = ""+ StringMath.fourRemoveFiveAdd1(""+valueList.get(i - 1));
                    if (isInt)
                        value = ""+(valueList.get(i - 1).intValue());
                    float textWidth = textPaint.measureText(value);
                    if(textWidth>width)//判断文字是否需要靠左对齐
                        canvas.drawText(value, lastEndX-textWidth, lineTop + 3 * lineHeight, textPaint);
                    else
                        canvas.drawText(value, lastEndX, lineTop + 3 * lineHeight, textPaint);
                    lastEndX += width;
                }
            }
        }else{
            paintLine.setStrokeWidth(lineHeight);
            paintLine.setColor(Color.parseColor(colorList.get(0)));
            RectF rect = new RectF(0, lineTop , getWidth(), lineTop + lineHeight);
            canvas.drawRoundRect(rect, lineHeight / 2, //x轴的半径
                    lineHeight / 2, //y轴的半径
                    paintLine);
        }
        if(curValue>0){
            paintLine.setStrokeWidth(tagLineWidth);
            paintLine.setColor(Color.parseColor(curColor));
            float width = (curValue-minValue)*valueWidth;
            canvas.drawLine(width, lineTop - lineHeight, width, lineTop + lineHeight, paintLine);//当前数值的标线

            Path path = new Path(); //定义一条路径
            path.moveTo(width, lineTop-lineHeight/2); //移动到 坐标10,10
            path.lineTo(width - lineHeight / 2, lineTop - lineHeight);
            path.lineTo(width+lineHeight/2, lineTop-lineHeight);
            path.lineTo(width, lineTop-lineHeight/2);
            canvas.drawPath(path, paintLine);
            //写当前数值
            SpannableStringBuilder ssb=new SpannableStringBuilder();
            String value = StringMath.fourRemoveFiveAdd1(""+curValue)+"";
            if(isInt)
                value = ((int) curValue)+"";
            ssb.append(value);
            ssb.append(unit);
            textPaint.setTextSize(DisplayUtil.sp2px(context,25f));
            textPaint.setColor(Color.parseColor(curColor));
            float textWidth = textPaint.measureText(ssb.toString());
            if((width-textWidth/2)<0)
                canvas.drawText(ssb,0,ssb.length(), 0, lineTop - lineHeight*2 , textPaint);
            else if((width-textWidth/2+textWidth)>getWidth())
                canvas.drawText(ssb,0,ssb.length(),getWidth()-textWidth, lineTop - lineHeight*2 , textPaint);
            else
                canvas.drawText(ssb,0,ssb.length(), width-textWidth/2, lineTop - lineHeight*2 , textPaint);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if(changed){
            lineTop = getHeight() - 5*lineHeight;
        }
    }

    /**
     * 设置数据
     * @param maxValue 最大值
     * @param minValue 最小值
     * @param curValue 当前值
     * @param valueList 刻度的list
     * @param colorList 每段颜色的list
     * @param curColor 当前值的颜色
     */
    public void setData(final float minValue,final float maxValue,final float curValue,final SpannableString unit,
                        final List<Float> valueList,final List<String> colorList,final String curColor,final boolean isInt){
        this.maxValue = maxValue;
        this.minValue = minValue;
        this.curValue = curValue;
        this.valueList = valueList;
        this.colorList = colorList;
        this.curColor = curColor;
        this.unit = unit;
        this.isInt = isInt;
        if(this.unit==null)
            this.unit = new SpannableString("");

        rangeValue = this.maxValue-this.minValue;
        invalidate();
    }
}

