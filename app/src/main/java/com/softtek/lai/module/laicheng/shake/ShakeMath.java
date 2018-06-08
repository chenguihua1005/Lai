package com.softtek.lai.module.laicheng.shake;

import android.hardware.SensorEvent;

/** 
 * @author  作者 E-mail: 
 * @date 创建时间：2015-7-22 下午7:02:09 
 **/
public class ShakeMath {
	// 速度阈值，当摇晃速度达到这值后产生作用
	private static final int SPEED_SHRESHOLD_ONE = 800;//快
	private static final int SPEED_SHRESHOLD_TWO = 150;//无
	private static final int SPEED_SHRESHOLD_THREE = 300;//有
	// 两次检测的时间间隔
	private static final int UPTATE_INTERVAL_TIME = 70;
	
	private static final int SPEED_SHRESHOLD_SLOW = 80;
	private static final int SPEED_SLOW_MAX = 4;
	
	ShakeSpeedStack mShakeSpeedStack;
	
	// 手机上一个位置时重力感应坐标
	private float lastX;
	private float lastY;
	private float lastZ;
	
	//如果多次速度过慢，清空计算器，重新计算，避免2次摇一摇数据叠加
	private int slowNum;
	// 上次检测时间
	private long lastUpdateTime;
	public ShakeMath(){
		mShakeSpeedStack = new ShakeSpeedStack(3);
	}
	
	public void Reset(){
		mShakeSpeedStack.clear();
		slowNum = 0;
	}
	
	public boolean isShake(SensorEvent event){
		// 现在检测时间
		long currentUpdateTime = System.currentTimeMillis();
		// 两次检测的时间间隔
		long timeInterval = currentUpdateTime - lastUpdateTime;
		// 判断是否达到了检测时间间隔
//		if (timeInterval < UPTATE_INTERVAL_TIME)
//			return false;
		if(lastUpdateTime==0){
			lastUpdateTime = currentUpdateTime;
			return false;
		}
		// 现在的时间变成last时间
		lastUpdateTime = currentUpdateTime;

		// 获得x,y,z坐标
		float x = event.values[0];
		float y = event.values[1];
		float z = event.values[2];

		// 获得x,y,z的变化值
		float deltaX = x - lastX;
		float deltaY = y - lastY;
		float deltaZ = z - lastZ;

		// 将现在的坐标变成last坐标
		lastX = x;
		lastY = y;
		lastZ = z;
		double speed = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ
				* deltaZ)
				/ timeInterval * 1000;
//		if(speed>40)
//			LogManager.getLogger().d("MainBaseActivity", "speed:"+speed);
		// 达到速度阀值，发出提示
		if( speed < SPEED_SHRESHOLD_SLOW ){
			slowNum++;
			if(slowNum>=SPEED_SLOW_MAX){
//				LogManager.getLogger().d("MainBaseActivity", "clear");
				mShakeSpeedStack.clear();
				slowNum = 0;
				return false;
			}
		}
		//需要快速
		if(mShakeSpeedStack.getSize()==0){
			if(speed>SPEED_SHRESHOLD_ONE&&speed< Double.POSITIVE_INFINITY){//有时候会出现无限的情况，感觉和时间为0有关系
				mShakeSpeedStack.addSpeed(speed);
				slowNum = 0;
//				LogManager.getLogger().d("MainBaseActivity", "speed_one:"+speed);
			}
		}else if(mShakeSpeedStack.getSize()==1){
			if(speed<SPEED_SHRESHOLD_TWO){
				mShakeSpeedStack.addSpeed(speed);
				slowNum = 0;
//				LogManager.getLogger().d("MainBaseActivity", "speed_two:"+speed);
			}
		}else if(mShakeSpeedStack.getSize()==2&&speed< Double.POSITIVE_INFINITY){
			if(speed>SPEED_SHRESHOLD_THREE){
				mShakeSpeedStack.addSpeed(speed);
				slowNum = 0;
//				LogManager.getLogger().d("MainBaseActivity", "speed_three:"+speed);
			}
		}
		if(!mShakeSpeedStack.isEnough()){
			return false;
		}
//		LogManager.getLogger().d("MainBaseActivity", "speed_yes");
		mShakeSpeedStack.clear();
		return true;
	}
}
