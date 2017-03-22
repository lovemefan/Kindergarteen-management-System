package construction;

import java.io.Serializable;

public class medicialCondition implements Serializable{
	private int height;
	private int weight;
	private double eyesight;
	private double hearing;
	private String bloodType;
	private boolean isFever;
	private boolean heartCondition;
	public medicialCondition(int height,int weight,double eyesight,double hearing,String bloodType,boolean isFeve, boolean heartCondition){
		this.height = height;
		this.weight = weight;
		this.eyesight = eyesight;
		this.hearing = hearing;
		this.bloodType = bloodType;
		this.isFever=isFever;
		this.heartCondition=heartCondition;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public double getEyesight() {
		return eyesight;
	}
	public void setEyesight(double eyesight) {
		this.eyesight = eyesight;
	}
	public double getHearring() {
		return hearing;
	}
	public void setHearring(double hearring) {
		this.hearing = hearring;
	}
	public String getBloodtype() {
		return bloodType;
	}
	public void setBloodtype(String bloodtype) {
		this.bloodType = bloodtype;
	}
	public boolean isFever() {
		return isFever;
	}
	public void setFever(boolean isFever) {
		this.isFever = isFever;
	}
	public boolean isHeartCondition() {
		return heartCondition;
	}
	public void setHeartCondition(boolean heartCondition) {
		this.heartCondition = heartCondition;
	}
	

}
