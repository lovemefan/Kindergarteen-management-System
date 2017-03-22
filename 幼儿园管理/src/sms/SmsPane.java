package sms;

import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class SmsPane extends GridPane {
	public ComboBox tf=new ComboBox();
	public ComboBox getTf() {
		return tf;
	}
	public void setTf(ComboBox tf) {
		this.tf = tf;
	}
	public SmsPane(){
		this.setVgap(20);
		this.setPadding(new Insets(11.5,12.5,13.5,14.5));
		this.add(new Label("收件人号码："),0,0);
		
		
		tf.setEditable(true);
		tf.setPromptText("不同号码用;间隔");
		this.add(tf,1,0);
		this.add(new Label("短信内容："),0,1);
		TextArea ta=new TextArea(); 
		this.add(ta,1,1);
		Button bt=new Button("发送");
		bt.getStyleClass().add("button1");
		bt.setPrefSize(200, 80);
		this.add(bt, 1, 2);
		GridPane.setHalignment(bt, HPos.RIGHT);
			
		bt.setOnAction((ActionEvent e)->{
			String []numbers=tf.getSelectionModel().getSelectedItem().toString().split(";");
			String 	text=ta.getText();
			//调用发送函数，且返回状态,未实现
			for(int i=0;i<numbers.length;i++){
				try {
					if("".equals(ta.getText())){
						windows.Window.messageWindow("", "邮件内容不能为空");
					}else{
						Sender.sendSms(numbers[i], text);
						windows.Window.messageWindow("", "邮件发送成功");
					}
					
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}			
		});
	}
	
}
