package construction;

import java.io.Serializable;
import java.util.Date;

public class dayInformation implements Serializable{
	private Date date;
	private String inClassTime;
	private String outClassTime;
	private String evaluation;
	private String pickUpPerson;
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
public dayInformation( Date date,String inClassTime,String outClassTime,String evaluation,String pickUpPerson){
		this.date=date;
		this.evaluation=evaluation;
		this.inClassTime=inClassTime;
		this.outClassTime=outClassTime;
		this.pickUpPerson=pickUpPerson;
	}
	public String getInClassTime() {
		return inClassTime;
	}
	public void setInClassTime(String inClassTime) {
		this.inClassTime = inClassTime;
	}
	public String getOutClassTime() {
		return outClassTime;
	}
	public void setOutClassTime(String outClassTime) {
		this.outClassTime = outClassTime;
	}
	public String getExpression() {
		return evaluation;
	}
	public void setExpression(String expression) {
		this.evaluation = expression;
	}
	public String getPickUpPerson() {
		return pickUpPerson;
	}
	public void setPickUpPerson(String pickUpPerson) {
		this.pickUpPerson = pickUpPerson;
	}
	
}