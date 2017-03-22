package construction;
import windows.Window;

import java.io.Serializable;
import java.util.*;
public class registration implements Serializable{
//	登记每日的出校离校信息
	public static void addRecord(Roll r,Date date,String inClassTime,String outClassTime,String expression,String pickUpPerson){
		r.getDairyRecord().add(new dayInformation(date,inClassTime, outClassTime,expression,pickUpPerson));
		
	}
//	修改每日出校离校信息
	
	
}

