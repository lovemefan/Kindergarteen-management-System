package construction;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

import javafx.scene.image.ImageView;

public class Roll implements Serializable{

	
		private String Name;
		private String Sex;
		private Date inSchoolTime;
		private Date outSchoolTime;
		private  medicialCondition condition;
		private String eMail;
		private String Address;
		private String phoneNumber;
		private final static String defaultPhotoPath="±³¾°Í¼Æ¬/head.png";
		private String photoPath=defaultPhotoPath;
		
		public String getPhotoPath() {
			return photoPath;
		}
		public void setPhotoPath(String photoPath) {
			this.photoPath = photoPath;
		}
		public static int NUMBER=0;
		
		private ArrayList<dayInformation> dairyRecord = new ArrayList();
		
		
		
		public Roll(){
			this.Name=null;
			this.inSchoolTime=null;
			this.outSchoolTime=null;
			this.condition=null;
			this.eMail=null;
			this.phoneNumber=null;
			NUMBER++;
			
		}
		public static String getDefaultPhotoPath() {
			return defaultPhotoPath;
		}
		public Roll(String Name){
			this.Name=Name;
			NUMBER++;
			
		}
		public Roll(String Name,String Sex,Date inSchoolTime,Date outSchoolTime,medicialCondition condition, String eMail,String Address,String  phoneNumber){
			this.Name=Name;
			this.Sex=Sex;
			this.inSchoolTime=inSchoolTime;
			this.outSchoolTime=outSchoolTime;
			this.condition=condition;
			this.eMail=eMail;
			this.Address=Address;
			this.phoneNumber=phoneNumber;
			NUMBER++;
		}
		public String getSex() {
			return Sex;
		}
		public void setSex(String sex) {
			Sex = sex;
		}
		public String getPhoneNumber() {
			return phoneNumber;
		}
		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}
		public ArrayList<dayInformation> getDairyRecord() {
			return dairyRecord;
		}
		public void setDairyRecord(ArrayList<dayInformation> dairyRecord) {
			this.dairyRecord = dairyRecord;
		}
		public String geteMail() {
			return eMail;
		}
		public void seteMail(String eMail) {
			this.eMail = eMail;
		}
		public String getName() {
			return Name;
		}
		public void setName(String name) {
			Name = name;
		}
		public Date getInSchoolTime() {
			return inSchoolTime;
		}
		public void setInSchoolTime(Date inSchoolTime) {
			this.inSchoolTime = inSchoolTime;
		}
		public Date getOutSchoolTime() {
			return outSchoolTime;
		}
		public void setOutSchoolTime(Date outSchoolTime) {
			this.outSchoolTime = outSchoolTime;
		}
		public medicialCondition getCondition() {
			return condition;
		}
		public void setCondition(medicialCondition Condition) {
			this.condition = Condition;
		}
		public void addDairyInformation(dayInformation dayinf){	
			dairyRecord.add(dayinf);
		}
		
		public String getAddress() {
			return Address;
		}
		public void setAddress(String address) {
			Address = address;
		}
		public static int getNUMBER() {
			return NUMBER;
		}
		public static void setNUMBER(int nUMBER) {
			NUMBER = nUMBER;
		}
		
		
	

}
