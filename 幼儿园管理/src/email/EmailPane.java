package email;

import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import email.Sender;

public class EmailPane extends GridPane {
	public ComboBox tf=new ComboBox();
	public ComboBox getTf() {
		return tf;
	}
	public void setTf(ComboBox tf) {
		this.tf = tf;
	}
	public EmailPane(){
		this.setVgap(20);
		this.setPadding(new Insets(11.5,12.5,13.5,14.5));
		this.add(new Label("收件人地址："),0,0);
		
		tf.setEditable(true);
		tf.setPromptText("someone@example.com    不同地址用;间隔");
		tf.getItems().addAll(
	            "450489712@qq.com",
	            "1044276659@qq.com",
	            "2677281339@qq.com",
	            "1440403686@qq.com" 
	        );
		this.add(tf,1,0);
		this.add(new Label("邮件正文："),0,1);
		TextArea ta=new TextArea(); 
		this.add(ta,1,1);
		Button bt=new Button("发送");
		bt.getStyleClass().add("button1");
		bt.setPrefSize(200, 80);
		this.add(bt, 1, 2);
		GridPane.setHalignment(bt, HPos.RIGHT);
			
		bt.setOnAction((ActionEvent e)->{
			if("".equals(ta.getText())){
				windows.Window.messageWindow("", "邮件内容不能为空");
			}else{
				String []addresses=(tf.getSelectionModel().getSelectedItem().toString().split(";"));
				String text=ta.getText()+"  爱心幼儿园";
				//调用发送函数，且返回状态(未实现)
				for(int i=0;i<addresses.length;i++)
					Sender.sendmail(addresses[i], text);
				windows.Window.messageWindow("", "邮件发送成功");
			}
				
				
		});
	}
	
}
