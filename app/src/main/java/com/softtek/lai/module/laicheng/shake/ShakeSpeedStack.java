package com.softtek.lai.module.laicheng.shake;

import java.util.ArrayList;
import java.util.List;

/** 
 * @author  作者 E-mail: 
 * @date 创建时间：2015-7-22 下午7:03:09 
 * 做一个简单的堆栈，摇一摇的速度必定是先快，然后在慢，在快，具体速度到多少需要测试
 **/
public class ShakeSpeedStack {
	
	private List<Double> stack = new ArrayList<Double>();
	private int length = 3;
	
	public ShakeSpeedStack(int length){
		this.length = length;
	}
	
	public void addSpeed(double speed){
		if(stack.size()==0){
			stack.add(speed);
			return;
		}else if(stack.size()==1){
			stack.add(1, stack.get(stack.size()-1));
			stack.set(0, speed);
			return;
		}
		if(stack.size() < length){
			stack.add(stack.size(), stack.get(stack.size()-1));
			for(int i=stack.size()-2;i>=0;i--){
				stack.set(i+1, stack.get(i));
			}
			stack.set(0, speed);
		}else{
			for(int i=stack.size()-1;i>=0;i--){
				stack.set(i+1, stack.get(i));
			}
			stack.set(0, speed);
		}
	}
	
	public void removeSpeed(int index){
		if(index<length)
			stack.remove(index);
	}
	
	public int getSize(){
		return stack.size();
	}
	
	public void clear(){
		stack.clear();
	}
	
	//查看是否采集够个数了
	public boolean isEnough(){
		return stack.size()==length?true:false;
	}
	
	public double getSpeed(int index){
		return stack.get(index);
	}
}
