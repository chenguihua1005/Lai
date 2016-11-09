package com.softtek.lai.widgets.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
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

public class BrokenLine extends View{

    private static int offset;

    private DataLine dataLine;

    private int maxYAxis=8000;//y轴的最大值
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

    private void init(){
        offset= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                10,getContext().getResources().getDisplayMetrics());
        textPaint=new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                12,getContext().getResources().getDisplayMetrics()));
        aTextPaint=new Paint();
        aTextPaint.setAntiAlias(true);
        aTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        aTextPaint.setColor(Color.WHITE);
        aTextPaint.setTextAlign(Paint.Align.CENTER);
        aTextPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                9,getContext().getResources().getDisplayMetrics()));

        linePaint=new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(Color.parseColor("#4CFFFFFF"));

        bgPaint=new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setStyle(Paint.Style.FILL_AND_STROKE);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width=getWidth()-offset*2;
        int height=getHeight();
        int chartHeight= (int) (height-TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                        22,getContext().getResources().getDisplayMetrics()));

        //画x轴的线
        linePaint.setStrokeWidth(1);
        canvas.drawLine(0+offset,chartHeight,width,chartHeight,linePaint);

        //画x轴数据
        if(dataLine!=null){
            List<String> xAxis=dataLine.getxAxis();
            if(xAxis!=null&&!xAxis.isEmpty()){
                xPoint=new int[xAxis.size()];
                int x=0+offset;
                for (int i=0,j=xAxis.size();i<j;i++){
                    String str=xAxis.get(i);
                    int textWitdh= width/(xAxis.size()-1);
                    if(i==j-1){
                        xPoint[i]=x-offset;
                        canvas.drawText(str,x-offset-textPaint.measureText(str,0,str.length()),height,textPaint);
                    }else if(i==0){
                        xPoint[i]=x;
                        canvas.drawText(str,x,height,textPaint);
                    }else {
                        xPoint[i]=x;
                        canvas.drawText(str,x-textPaint.measureText(str,0,str.length())/2,height,textPaint);
                    }
                    x+=textWitdh;
                }
            }
            List<Entry> yAxis=dataLine.getEntries();
            if(yAxis!=null&&!yAxis.isEmpty()){
                double per=chartHeight*1d/maxYAxis;

                Point[] broken=new Point[yAxis.size()];
                //有数据，就画一个路径框
                Path path=new Path();
                //从右下角开始画一个路径框
                path.moveTo(width,chartHeight);
                path.lineTo(0+offset,chartHeight);
                bgPaint.setColor(0x99FFFFFF);
                for (int i=0,j=yAxis.size();i<j;i++){
                    Entry entry=yAxis.get(i);
                    int x=xPoint[entry.getIndex()];
                    float y= (float) ((chartHeight-per*entry.getVal())+getPaddingTop());
                    path.lineTo(x, y);
                    broken[i]=new Point(x, (int) y);
                    //画圆点
                    canvas.drawCircle(x, y,5,bgPaint);
                    //标数字
                    canvas.drawText((int)entry.getVal()+"",x,y-10,aTextPaint);
                }
                //path.lineTo(width,chartHeight);
                bgPaint.setShader(new LinearGradient(width/2,0,width/2,chartHeight,0x8CFFFFFF,
                        0X19FFFFFF, Shader.TileMode.REPEAT));
                path.close();
                canvas.drawPath(path,bgPaint);
                //画折线
                linePaint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        1.5f,getContext().getResources().getDisplayMetrics()));
                for (int i=1,j=broken.length;i<j;i++){
                    Point spoint=broken[i-1];
                    Point epoint=broken[i];
                    canvas.drawLine(spoint.x,spoint.y,epoint.x,epoint.y,linePaint);
                }
                //画平均线(虚线)
                linePaint.reset();
                linePaint.setColor(0x4CFFFFFF);
                linePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
                linePaint.setAntiAlias(true);
                linePaint.setStyle(Paint.Style.STROKE);
                linePaint.setStrokeWidth(1);
                linePaint.setPathEffect(new DashPathEffect(new float[]{5,5,5,5},1));
                float y=(float) (chartHeight-maxYAxis/2*per);
                Path dash=new Path();
                dash.moveTo(0+offset,y);
                dash.lineTo(width,y);
                canvas.drawPath(dash,linePaint);

                String avg=String.valueOf(maxYAxis/2).substring(0,1)+getUnit(maxYAxis/2);
                canvas.drawText(avg,width+aTextPaint.measureText(avg)/2,y,aTextPaint);
            }

        }

    }
    public String getUnit(int value){
        if(value<10){
            return "";
        }else if(value<100){
            return "十";
        }else if(value<1000){
            return "百";
        } else if(value<10000) {
            return "千";
        }
        else if(value<100000){
            return "万";
        }
        return "";
    }

    public void setData(DataLine dataLine){
        this.dataLine=dataLine;
        postInvalidate();
    }


}
