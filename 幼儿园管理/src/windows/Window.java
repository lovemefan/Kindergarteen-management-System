package windows;
import construction.dayInformation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sms.SmsPane;
import javafx.util.Duration;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import construction.Roll;
import construction.medicialCondition;
import email.EmailPane;
import javafx.stage.FileChooser;


public class Window extends Application {

	private final Desktop desktop = Desktop.getDesktop();
	
	private ArrayList<Roll> students = new ArrayList<Roll>();
	
	
	private static ArrayList<String> studentsName = new ArrayList<String>();
	
	private static String FILEPATH;
	
	VBox rootPane = new VBox();
	
	VBox menuPane = new VBox();
	
	BorderPane homePage = new BorderPane();
	
	boolean  isDelete = false;//用于deleteStage()函数的返回
	
	@Override
	public void start(Stage stage) throws Exception {
			homePage.setPadding(new Insets(0,0,0,0));
			rootPane.setPadding(new Insets(0,0,0,0));
			rootPane.setSpacing(0);
			
			//菜单栏
			MenuBar menuBar = new MenuBar();
			Menu menuFile = new Menu("登记");
			//菜单目录
			MenuItem open = new MenuItem("导入本地文件");
			open.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
			
			
			open.setOnAction((t) -> {
				//等待添加函数
				try{
						FileChooser fileChooser = new FileChooser();
						fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("DAT","*.dat"),
								new FileChooser.ExtensionFilter("所有文件", "*.*"));	
						fileChooser.setTitle("选择本地文件");
						fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));//初始化路径，为当前项目路径
						File file = fileChooser.showOpenDialog(stage);
						if (file != null) {
							students=(ArrayList<Roll>)readObjectFromFile(file.getAbsolutePath());
							
//							openFile(file);
						
					}
					}catch(Exception e){
						
						Window.messageWindow("错误", "打开文件失败，请重试");
					}
			    //vbox.setVisible(false);
			});
			
			MenuItem save = new MenuItem("保存");
			save.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
			
			
			save.setOnAction((t) -> {
				try{
					File file = new File(FILEPATH);
					if (file != null) {
				        try {
				        	writeObjectToFile(students,FILEPATH);
	
				        } catch (Exception ex) {
				             System.out.println(ex.getMessage());
				        }
				    }
				}catch(NullPointerException e){
					Window.messageWindow("错误", "请打开一个文件");
				}
				
			});
			
			MenuItem saveAs = new MenuItem("另保存");
			saveAs.setAccelerator(KeyCombination.keyCombination("Alt+S"));
			saveAs.setOnAction((t) -> {
				//等待添加函数
				FileChooser fileChooser1 = new FileChooser();
			    fileChooser1.setTitle("保存文件");
			    fileChooser1.setInitialDirectory(new File(System.getProperty("user.dir")));//初始化路径，为当前项目路径
			    fileChooser1.getExtensionFilters().addAll(
			    		new FileChooser.ExtensionFilter("DAT", "*.dat"),
			    		new FileChooser.ExtensionFilter("所有文件", "*.*")
			    		);
			    File file = fileChooser1.showSaveDialog(stage);
			    if (file != null) {
			        try {
			        	writeObjectToFile(students,file.getAbsolutePath());

			        } catch (Exception ex) {
			             System.out.println(ex.getMessage());
			        }
			    }
			    //vbox.setVisible(false);
			});
			menuFile.getItems().addAll(open,save,saveAs);
			
			Menu menuMessage = new Menu("安全小贴士");
			CheckMenuItem tips = new CheckMenuItem("定时发送");
			tips.setOnAction((e)->{
				sendTips();
			});
			menuMessage.getItems().add(tips);
			Menu menuHelp = new Menu("帮助");
			MenuItem help = new MenuItem("帮助文档");
			menuHelp.getItems().add(help);
			help.setOnAction((e)->{
				
				try {
					desktop.open(new File("help.txt"));

				} catch (Exception e1) {
					// TODO 自动生成的 catch 块
					Window.messageWindow("错误", "help.txt文件丢失");
				}//打开帮助文档
			});
			menuBar.getMenus().addAll(menuFile, menuMessage, menuHelp);
			menuBar.setMinHeight(30);
			
//			主页
//			数据测试
//			students.add(new Roll("小红","女",new Date(),new Date(),
//					new medicialCondition(170,50,0.0,0.0,"o",false, false),
//					"some@xx","南昌", "156xxxxxxxx"));
//			students.add(new Roll("小明2","男", new Date(),new Date(),
//					new medicialCondition(170,50,0.0,0.0,"a",false, false),
//					"some@xx","北京", "186xxxxxxxx"));
////////	
//			
			
			
			
			homePage();
			
			stage.setTitle("幼儿园管理系统");
	        stage.getIcons().add(new Image("icon.png"));
	        Scene scene = new Scene(rootPane, 1000, 750);
	        
	        ((VBox) scene.getRoot()).getChildren().addAll(menuBar,homePage);
	        stage.setScene(scene);
//	        scene.getStylesheets().add(Login.class.getResource("Logint.css").toExternalForm());
	        scene.getStylesheets().add("stylesheet.css");
	        stage.setResizable(false);//不可改变大小
	        stage.initStyle(StageStyle.TRANSPARENT);//去掉窗口边框
	        stage.show();
		
	}
private void sendTips() {
	EventHandler<ActionEvent> eventHandler=(e)->{
		String allAddress=null;
		for(int i=0;i<students.size();i++){
			if(i==0)
				allAddress=students.get(i).geteMail();
			else
				allAddress=allAddress+";"+students.get(i).geteMail();
			
		}
		if(allAddress!=null)
			email.Sender.sendmail(allAddress, readTxtFile("安全小贴士.txt"));
		
	};
	//建立时间线，每一段时间给所有人发送邮件
	Timeline animation=new Timeline(new KeyFrame(Duration.millis(600000),eventHandler));
	animation.setCycleCount(Timeline.INDEFINITE);
	//每分钟触发evenHandle事件
	animation.play();
}
	
//	信息弹窗，可以弹出提示，错误等
	public static void messageWindow(String title,String message) {
		Stage messageWindow=new Stage();
		Label messageLabel = new Label(message);
		messageLabel.setAlignment(Pos.CENTER);
		Scene messageScene=new Scene(messageLabel,300,100);
		messageWindow.setTitle(title);
		messageWindow.setScene(messageScene);
		messageWindow.show();
		
	}
	public void homePage(){
		VBox menuPage = new VBox();
		menuPage.setPadding(new Insets(200,10,0,10));
		menuPage.setSpacing(50);
		menuPage.setAlignment(Pos.CENTER);
		menuPage.setMinWidth(400);
		homePage.getChildren().add(new ImageView("背景图片/background.jpg"));
		Button checkButton = new Button("学生信息");
		checkButton.setMinSize(200, 70);
		checkButton.getStyleClass().add("button1");
		checkButton.setOnAction((e)->{
			checkStage();
		});
		
		Button registerButton = new Button("登记信息");
		registerButton.setMinSize(200, 70);
		registerButton.getStyleClass().add("button1");
		registerButton.setOnAction((e)->{
			registerStage();
		});
		
		Button tipsButton = new Button("消息通知发布");
		tipsButton.setMinSize(200, 70);
		tipsButton.getStyleClass().add("button1");
		tipsButton.setOnAction((e)->{
			tipsStage();
		});
		
		Button exitButton = new Button("退出");
		exitButton.setMinSize(200, 70);
		exitButton.getStyleClass().add("button1");
		exitButton.setOnAction((e)->{
			System.exit(0);
		});
		
		menuPage.getChildren().addAll(checkButton,registerButton,tipsButton,exitButton);
		homePage.setRight(menuPage);
//		homePage.setPadding(new Insets(0,0,0,0));
		
		
	}
	
/*	private void checkStage() {
		Stage checkStage=new Stage();
		BorderPane rootPane = new BorderPane();
		VBox navigationBar  = new VBox();
		
		
		rootPane.getChildren().add(new ImageView("背景图片/background2.jpg"));
		navigationBar .setPadding(new Insets(200,10,0,10));
		navigationBar .setSpacing(50);
		navigationBar .setAlignment(Pos.CENTER);
		navigationBar .setMinWidth(400);

		rootPane.setLeft(navigationBar);
		Scene checkScene=new Scene(rootPane,1024,768);
		checkStage.setScene(checkScene);
		checkStage.show();
	}
*/	
	private void checkStage() {
		//学生的姓名信息；
		Stage checkStage=new Stage();
		BorderPane rootPane = new BorderPane();
		VBox navigationBar  = new VBox();
		VBox right_navigationBar=new VBox();
		HBox find_hbox=new HBox();
		GridPane information=new GridPane();
			right_navigationBar.setSpacing(20);
			information.setVgap(11);
			information.setHgap(10);
			ImageView background3=new ImageView("背景图片/background5.jpg");
			//background3.setFitHeight(768);
			//background3.setFitWidth(1200);
		rootPane.getChildren().add(background3);
		
			navigationBar .setPadding(new Insets(20,0,100,0));
			navigationBar .setSpacing(20);
			navigationBar .setAlignment(Pos.CENTER);
			navigationBar .setMinWidth(200);
		
		Button find_Button=new Button("搜索");
		Button add_Button=new Button("添加");		
			find_Button.setMinSize(50, 30);
			add_Button.setMinSize(50, 30);
		TextField find_textfield=new TextField();
			find_textfield.setMinSize(140, 30);



	//将学生姓名放入滚动条；
		String[]stu=new String[students.size()];
		for (int i = 0; i < students.size(); i++) {
			stu[i]=students.get(i).getName();
		}
		
		
		ListView<String> lv=new ListView<>(FXCollections.observableArrayList(stu));
		lv.setMaxWidth(300);
		lv.setMinHeight(700);
	//	lv.setMaxSize(200, 450);
		//	lv.setPrefSize(100, 600);
	//将选项设置为单选;
		lv.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	//把滚动条放入；
	//	ScrollPane scroll=new ScrollPane(lv);
		//	//scroll.setMaxSize(250, 550);
		//	scroll.setMinWidth(150);
		//	scroll.setMinHeight(550);
	//将搜索版面放在左边；(右边版面)


		ImageView stu_Image=new ImageView("背景图片/head.png");
		stu_Image.setFitWidth(150);
		stu_Image.setFitHeight(150);
		//stu_Image.set

	
		Label name_Label=new Label("姓名：");
			name_Label.setFont(Font.font(20));
			name_Label.setTextFill(Color.RED);
		TextField name_Textfield=new TextField();
			name_Textfield.setMinSize(70, 30);
			//name_Textfield.setText(Window.roll(1).getName());
		Label sex_Label=new Label("性别:");
			sex_Label.setFont(Font.font(20));   
			sex_Label.setTextFill(Color.RED);
			ComboBox<String> sex_comboBox= new ComboBox<String>();
			sex_comboBox.getItems().addAll(
					"男",
					"女"
					);
			sex_comboBox.setEditable(false);
			sex_comboBox.setValue("");
			sex_comboBox.setMinSize(70, 30);
		Label address_Label=new Label("家庭住址:");
			address_Label.setFont(Font.font(20));   
			address_Label.setTextFill(Color.RED);
		TextField address_Textfield=new TextField();
			address_Textfield.setMinSize(200, 30);
		Label inSchoolTime_Label=new Label("入学时间:");
			inSchoolTime_Label.setFont(Font.font(20));
			DatePicker inSchoolTimeDatePicker = new DatePicker(LocalDate.of(1998, 10, 8));
			//通过setValue方法来设置指定日期checkInDatePicker.setValue(LocalDate.of(1998, 10, 8));
			//设置日历中最小的可用日期
			inSchoolTimeDatePicker.setValue(LocalDate.MIN);
			//设置日历中最大的可用日期
			inSchoolTimeDatePicker.setValue(LocalDate.MAX);
			//设置当前日期
			inSchoolTimeDatePicker.setValue(LocalDate.now());
			
		
		Label eMail_Label=new Label("邮件:");
			eMail_Label.setFont(Font.font(20));   
		TextField eMail_Textfield=new TextField();
			eMail_Textfield.setMinSize(200, 30);
		Label phoneNumber_Label=new Label("电话:");
			phoneNumber_Label.setFont(Font.font(20));   
			phoneNumber_Label.setTextFill(Color.RED);

		TextField phoneNumber_Textfield=new TextField();
			phoneNumber_Textfield.setMinSize(200, 30);
		//这下面的是身体信息状况； 
			Label label_No1=new Label("身体健康状况");
			label_No1.setFont(Font.font(23));
		Label height_Label=new Label("身高:");
			height_Label.setFont(Font.font(20)); 
		//这里可能要添加一个 控件；
		TextField height_Textfield=new TextField("");
			height_Textfield.setPromptText("单位：cm");
			height_Textfield.setMinSize(200, 30);
		Label weight_Label=new Label("体重:");
			weight_Label.setFont(Font.font(20)); 
		TextField weight_Textfield=new TextField();
			weight_Textfield.setPromptText("单位：kg");
			weight_Textfield.setMinSize(200, 30);
		Label eyesight_Label=new Label("视力:");
			eyesight_Label.setFont(Font.font(20)); 
		TextField eyesight_Textfield=new TextField();	
			eyesight_Textfield.setMinSize(200, 30);
		Label hearing_Label=new Label("听力:");
			hearing_Label.setFont(Font.font(20)); 
		TextField hearing_Textfield=new TextField();
		hearing_Textfield.setPromptText("单位：dB SPL");
			hearing_Textfield.setMinSize(200, 30);
		Label bloodType_Label=new Label("血型:");
			bloodType_Label.setFont(Font.font(20)); 
		ComboBox<String> bloodType_comboBox= new ComboBox<String>();
		bloodType_comboBox.getItems().addAll(
					"A",
					"B",
					"AB",
					"O"
					);
		bloodType_comboBox.setEditable(true);
		bloodType_comboBox.setEditable(false);
		Label isFever_Label=new Label("是否发烧:");
			isFever_Label.setFont(Font.font(20)); 
		TextField isFever_Textfield=new TextField();
			isFever_Textfield.setMinSize(200, 30);
		Label heartCondition_Label=new Label("是否有心脏病:");
			heartCondition_Label.setFont(Font.font(20));   
			heartCondition_Label.setTextFill(Color.RED);
		ComboBox<String> heartCondition = new ComboBox<String>();
		heartCondition.getItems().addAll(
						"是",
						"否"
						);
			heartCondition.setMinSize(200, 30);
		Label label_No2=new Label("(红色字体信息必填)");
			label_No2.setTextFill(Color.RED);
			Button save_Button=new Button("保存");
			save_Button.setTextFill(Color.BLUE);
			save_Button.setFont(Font.font(30));
		Button delate_Button=new Button("删除");
			delate_Button.setTextFill(Color.BLUE);
			delate_Button.setFont(Font.font(30));
			
			
			
			find_hbox.getChildren().addAll(find_Button,find_textfield,add_Button);		
			information.addRow(0, name_Label,name_Textfield,sex_Label,sex_comboBox);
			information.addRow(1, address_Label,address_Textfield, inSchoolTime_Label,inSchoolTimeDatePicker);
			information.addRow(2,eMail_Label,eMail_Textfield,phoneNumber_Label,phoneNumber_Textfield);
			information.addRow(3, label_No1);
			information.addRow(4,height_Label, height_Textfield,weight_Label,weight_Textfield);
			information.addRow(5,eyesight_Label,eyesight_Textfield,hearing_Label,hearing_Textfield);
			information.addRow(6, bloodType_Label,bloodType_comboBox,heartCondition_Label,heartCondition);
			information.addRow(7, label_No2);
			information.add(save_Button, 1, 8);
			information.add(delate_Button, 2, 8);
			
			navigationBar .getChildren().addAll(find_hbox,lv);
			right_navigationBar.setPadding(new Insets(100,100,50,50));
			right_navigationBar.getChildren().add(stu_Image);
			right_navigationBar.getChildren().add(information);
//			right_navigationBar.getStyleClass().add("frosted-glass");
			
			rootPane.setLeft(navigationBar);
			rootPane.setCenter(right_navigationBar);
			

			
//			处理对列表项（ListItem）的选中事件
			lv.getSelectionModel().selectedItemProperty().addListener(
					(ObservableValue<? extends String> ov, String old_val,
					String new_val) -> {
						
						stu_Image.setImage(new Image(students.get(lv.getSelectionModel().getSelectedIndex()).getPhotoPath()));
						name_Textfield.setText(students.get(lv.getSelectionModel().getSelectedIndex()).getName());
						sex_comboBox.setValue(students.get(lv.getSelectionModel().getSelectedIndex()).getSex());
		  				address_Textfield.setText(students.get(lv.getSelectionModel().getSelectedIndex()).getAddress());
//		  				System.out.println(inSchoolTimeDatePicker.getValue().toString());//测试使用
//		  				inSchoolTimeDatePicker.setValue(inSchoolTimeDatePicker.getValue().toString()); //也可显示内容
		  				
		  				
//**********************************java.util.Date --> java.time.LocalDate  类型转换**********************************
		  				java.util.Date date = students.get(lv.getSelectionModel().getSelectedIndex()).getInSchoolTime();
		  				if(date!=null){
		  			    Instant instant = date.toInstant();
		  			    ZoneId zone = ZoneId.systemDefault();
		  			    LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
		  			    LocalDate localDate = localDateTime.toLocalDate();
		  			    inSchoolTimeDatePicker.setValue(localDate);
		  				}
//*********************************************date转localdate***************************************************
//		  			    
//		  			  
		  			    
		  				
		  				eMail_Textfield.setText(students.get(lv.getSelectionModel().getSelectedIndex()).geteMail());
		  				phoneNumber_Textfield.setText(students.get(lv.getSelectionModel().getSelectedIndex()).getPhoneNumber());
		  				height_Textfield.setText(students.get(lv.getSelectionModel().getSelectedIndex()).getCondition().getHeight()+"");
		  				weight_Textfield.setText(students.get(lv.getSelectionModel().getSelectedIndex()).getCondition().getWeight()+"");
		  				eyesight_Textfield.setText(students.get(lv.getSelectionModel().getSelectedIndex()).getCondition().getEyesight()+"");
		  				hearing_Textfield.setText(students.get(lv.getSelectionModel().getSelectedIndex()).getCondition().getHearring()+"");
		  				bloodType_comboBox.setValue(students.get(lv.getSelectionModel().getSelectedIndex()).getCondition().getBloodtype());
		  				if(students.get(lv.getSelectionModel().getSelectedIndex()).getCondition().isHeartCondition()){
		  					heartCondition.setValue("是");
		  				}else{
		  					heartCondition.setValue("否");
		  				}
				
			});
			
			
			
			
		find_Button.setOnAction((e)->{
			int index=0;
			if("".equals(find_textfield.getText())){
				windows.Window.messageWindow("", "输入不能为空");
			}else{
				for(;index<students.size();index++){
					if(index<students.size() &&find_textfield.getText().equals(students.get(index).getName())){
						lv.getSelectionModel().clearSelection();
						lv.getSelectionModel().select(index);
						lv.scrollTo(index);//跳转到
						break;
					}
				}
				if(index==lv.getItems().size()){
					windows.Window.messageWindow("错误", "查无此人");
				}
			}		
			
			});
		add_Button.setOnAction((e)->{
			if("".equals(find_textfield.getText())){
				windows.Window.messageWindow("", "输入不能为空");
			}else{
				
				students.add(new Roll(find_textfield.getText(),"",new Date(),new Date(),
						new medicialCondition(0,0,0.0,0.0,"",false, false),
						"","", ""));
				lv.getItems().add(find_textfield.getText());
//				添加并初始化
//				面板初始化
				stu_Image.setImage(new Image(Roll.getDefaultPhotoPath()));
				name_Textfield.setText("");
  				sex_comboBox.setValue("");
  				address_Textfield.setText("");
  				eMail_Textfield.setText("");
  				phoneNumber_Textfield.setText("");
  				height_Textfield.setText("");
  				weight_Textfield.setText("");
  				eyesight_Textfield.setText("");
  				hearing_Textfield.setText("");
  				bloodType_comboBox.setValue("");
  				heartCondition.setValue("");
//  			
  			
//******************************完成初始化*****************************
			}
		});
		save_Button.setOnAction((e)->{
//			如果用户没有点击listview 则无效
			if(lv.getSelectionModel().getSelectedIndex()>=0 && lv.getSelectionModel().getSelectedIndex()<lv.getItems().size()){
				
				students.get(lv.getSelectionModel().getSelectedIndex()).setName(name_Textfield.getText());
				lv.getItems().set(lv.getSelectionModel().getSelectedIndex(), name_Textfield.getText());
				students.get(lv.getSelectionModel().getSelectedIndex()).setSex(sex_comboBox.getSelectionModel().getSelectedItem().toString());
				students.get(lv.getSelectionModel().getSelectedIndex()).setAddress(address_Textfield.getText());
				
				SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
				Date date = null;
				try {
					String time = inSchoolTimeDatePicker.getValue().toString();
					date = sdf.parse(time);
				} catch (ParseException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
				
				students.get(lv.getSelectionModel().getSelectedIndex()).setInSchoolTime(date);
				students.get(lv.getSelectionModel().getSelectedIndex()).seteMail(eMail_Textfield.getText());
				students.get(lv.getSelectionModel().getSelectedIndex()).setPhoneNumber(phoneNumber_Textfield.getText());
				students.get(lv.getSelectionModel().getSelectedIndex()).getCondition().setHeight(Integer.parseInt(height_Textfield.getText()));
				students.get(lv.getSelectionModel().getSelectedIndex()).getCondition().setEyesight(Double.parseDouble(hearing_Textfield.getText()));
				students.get(lv.getSelectionModel().getSelectedIndex()).getCondition().setBloodtype(bloodType_comboBox.getSelectionModel().getSelectedItem().toString());
				students.get(lv.getSelectionModel().getSelectedIndex()).getCondition().setHeartCondition(Boolean.parseBoolean(heartCondition.getSelectionModel().getSelectedItem().toString()));
				
			}
			
			});
		delate_Button.setOnAction((e)->{
					Stage delateStage=new Stage(); 
					isDelete=false;
					VBox ture_vbox=new VBox();
					ture_vbox.setSpacing(30);
					ture_vbox.setPadding(new Insets(20,20,60,60));
					HBox ture_hbox=new HBox();
					ture_hbox.setSpacing(50);
					Label delate_Label=new Label("是否删除此人所有信息！");
			 		delate_Label.setFont(Font.font(20));
			 		delate_Label.setTextFill(Color.RED);
			 	//如果查找栏里的输入的名字查找不到；
			 		Button yes=new Button("确定");
					yes.setFont(Font.font(20));
					Button cancel_Button=new Button("取消");
					cancel_Button.setFont(Font.font(20));
					yes.setOnAction((e1)->{
						//加入保存信息的函数；
						lv.getItems().remove(lv.getSelectionModel().getSelectedIndex());
						students.remove(lv.getSelectionModel().getSelectedIndex());
						delateStage.close();
						
			});
			cancel_Button.setOnAction((e2)->{
				delateStage.close();
			});
			ture_hbox.getChildren().addAll(yes,cancel_Button);
			ture_vbox.getChildren().addAll(delate_Label,ture_hbox);
			Scene scene=new Scene(ture_vbox,300,150);
			delateStage.setScene(scene);
			delateStage.setResizable(false);//不可改变大小
	     //  tureStage.initStyle(StageStyle.TRANSPARENT);//去掉窗口边框
			delateStage.show();
			
			
			
		});
			
			
			
			
		
		Scene checkScene=new Scene(rootPane,1200,768);
//		checkScene.getStylesheets().add("stylesheet3.css");
		checkStage.setScene(checkScene);
		checkStage.show();
	}
//	****************************************************************************************************************************
	private void registerStage() {
		//	Stage registerStage=new Stage();
		//	BorderPane rootPane = new BorderPane();
		//	VBox navigationBar  = new VBox();
		//	rootPane.setLeft(navigationBar);
			
			//载入学生的姓名信息；
		String[]stu=new String[students.size()];
		for (int i = 0; i < students.size(); i++) {
			stu[i]=students.get(i).getName();
		}
			Stage registerStage=new Stage();
			BorderPane rootPane = new BorderPane();
			VBox navigationBar  = new VBox();
			VBox right_navigationBar=new VBox();
				right_navigationBar.setPadding(new Insets(100,100,50,50));
			HBox find_hbox=new HBox();
			HBox xxx_hbox=new HBox();
			GridPane information=new GridPane();
				right_navigationBar.setSpacing(20);
				information.setVgap(11);
				information.setHgap(10);
			
			rootPane.getChildren().add(new ImageView("背景图片/background5.jpg"));
			
				navigationBar .setPadding(new Insets(20,0,100,0));
				navigationBar .setSpacing(20);
				navigationBar .setAlignment(Pos.CENTER);
				navigationBar .setMinWidth(200);
			
			Button find_Button=new Button("搜索");
					
				find_Button.setMinSize(50, 30);
			TextField find_textfield=new TextField();
				find_textfield.setMinSize(140, 30);
		


		//将学生姓名放入滚动条；
			ListView<String> lv=new ListView<>(FXCollections.observableArrayList(stu));
			lv.setMaxWidth(300);
			lv.setMinHeight(700);
		//	lv.setMaxSize(200, 450);
			//	lv.setPrefSize(100, 600);
		//将选项设置为单选;
			lv.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		//把滚动条放入；
		//	ScrollPane scroll=new ScrollPane(lv);
			//	//scroll.setMaxSize(250, 550);
			//	scroll.setMinWidth(150);
			//	scroll.setMinHeight(550);
		//将搜索版面放在左边；(右边版面)


			ImageView stu_Image=new ImageView("背景图片/head.png");
			stu_Image.setFitWidth(150);
			stu_Image.setFitHeight(150);
			//stu_Image.set

			Label dayInformation_Label=new Label("此页信息记入时间：");
		
				dayInformation_Label.setFont(Font.font(20));
			DatePicker dayInformationDatePicker = new DatePicker(LocalDate.of(1998, 10, 8));
			//通过setValue方法来设置指定日期checkInDatePicker.setValue(LocalDate.of(1998, 10, 8));
			//设置日历中最小的可用日期
				dayInformationDatePicker.setValue(LocalDate.MIN);
			//设置日历中最大的可用日期
				dayInformationDatePicker.setValue(LocalDate.MAX);
			//设置当前日期
				dayInformationDatePicker.setValue(LocalDate.now());
			

			Label guardianName_Label=new Label("家长姓名：");
				guardianName_Label.setFont(Font.font(20));
				guardianName_Label.setTextFill(Color.RED);
			TextField guardianName_Textfield=new TextField();
				guardianName_Textfield.setMinSize(200, 30);
			Label inClassTime_Label=new Label("入园时间：");
				inClassTime_Label.setFont(Font.font(20));
			TextField inClassTime_Textfield=new TextField();
				inClassTime_Textfield.setMinSize(200, 30);
			Label experession_Label=new Label("与送的人关系:");	
				experession_Label.setFont(Font.font(20));
				
			Label outClassTime_Label=new Label("离园时间：");	
			TextField outClassTime_Textfield=new TextField();
				outClassTime_Label.setFont(Font.font(20));
					outClassTime_Textfield.setMinSize(200, 30);
			Label pickUpPeression_Label=new Label("与送的人关系:");	
					pickUpPeression_Label.setFont(Font.font(20));
					
			Button history=new Button("历史纪录");
			history.setTextFill(Color.BLUE);
			history.setFont(Font.font(30));
			history.setMinWidth(50);
					
			Button save_Button=new Button("保存此记录");
				save_Button.setTextFill(Color.BLUE);
				save_Button.setFont(Font.font(30));
				save_Button.setMinWidth(50);
			TextField evaluation_Textfield=new TextField();
			evaluation_Textfield.setMinSize(400, 100);
			
				
				
				history.setOnAction((e)->{
					Stage historyStage = new Stage();
					BorderPane rootPane2 = new BorderPane();
					GridPane information2=new GridPane();
					information2.setPadding(new Insets(20,10,10,10));
					
					Label guardianName_Label2=new Label("家长姓名：");
					guardianName_Label2.setFont(Font.font(20));
					guardianName_Label2.setTextFill(Color.RED);
					TextField guardianName_Textfield2=new TextField();
						guardianName_Textfield2.setMinSize(300, 30);
					Label inClassTime_Label2=new Label("入园时间：");
						inClassTime_Label2.setFont(Font.font(20));
					TextField inClassTime_Textfield2=new TextField();
						inClassTime_Textfield2.setMinSize(300, 30);
				
						
					Label outClassTime_Label2=new Label("离园时间：");	
					TextField outClassTime_Textfield2=new TextField();
						outClassTime_Label2.setFont(Font.font(20));
						outClassTime_Textfield2.setMinSize(200, 30);
					TextField evaluation_Textfield2=new TextField();
						evaluation_Textfield2.setMinSize(300, 400);	
//					未点击姓名listview无效
					if(lv.getSelectionModel().getSelectedIndex()>=0 && lv.getSelectionModel().getSelectedIndex()<lv.getItems().size()){
						String[]record=new String[students.get(lv.getSelectionModel().getSelectedIndex()).getDairyRecord().size()];
						for (int i = 0; i < students.get(lv.getSelectionModel().getSelectedIndex()).getDairyRecord().size(); i++) {
							record[i]=students.get(lv.getSelectionModel().getSelectedIndex()).getDairyRecord().get(i).getDate().toGMTString();
						}
						ListView<String> historyList=new ListView<>(FXCollections.observableArrayList(record));
						historyList.setMaxWidth(300);
						historyList.setMinHeight(400);
					historyList.getSelectionModel().selectedItemProperty().addListener(
								(ObservableValue<? extends String> ov, String old_val,
								String new_val) -> {
							
									guardianName_Textfield2.setText(students.get(lv.getSelectionModel().getSelectedIndex()).getDairyRecord().get(historyList.getSelectionModel().getSelectedIndex()).getPickUpPerson());
									inClassTime_Textfield2.setText(students.get(lv.getSelectionModel().getSelectedIndex()).getDairyRecord().get(historyList.getSelectionModel().getSelectedIndex()).getInClassTime());
									outClassTime_Textfield2.setText(students.get(lv.getSelectionModel().getSelectedIndex()).getDairyRecord().get(historyList.getSelectionModel().getSelectedIndex()).getOutClassTime());
									evaluation_Textfield2.setText(students.get(lv.getSelectionModel().getSelectedIndex()).getDairyRecord().get(historyList.getSelectionModel().getSelectedIndex()).getExpression());
					});
						
					
					information2.addRow(0, guardianName_Label2);	
					information2.addRow(1,guardianName_Textfield2);
					information2.addRow(2,inClassTime_Label2);
					information2.addRow(4, inClassTime_Textfield2);	
					information2.addRow(5, outClassTime_Label2);	
					information2.addRow(6, outClassTime_Textfield2);
					information2.addRow(7, new Label("评价"));
					information2.addRow(8, evaluation_Textfield2);
							
						rootPane2.setLeft(historyList);
						rootPane2.setCenter(information2);
						Scene newScene = new Scene(rootPane2,600,700);
						historyStage.setScene(newScene);
						historyStage.show();
					}
					
				
			});
				
			
			
			find_Button.setOnAction((e)->{
				int index=0;
				if("".equals(find_textfield.getText())){
					windows.Window.messageWindow("", "输入不能为空");
				}else{
					for(;index<students.size();index++){
						if(index<students.size() &&find_textfield.getText().equals(students.get(index).getName())){
							lv.getSelectionModel().clearSelection();
							lv.getSelectionModel().select(index);
							lv.scrollTo(index);//跳转到
							break;
						}
					}
					if(index==lv.getItems().size()){
						windows.Window.messageWindow("错误", "查无此人");
					}
				}		
				
				});
				
		save_Button.setOnAction((e)->{
//			如果用户没有点击listview 则无效
			if(lv.getSelectionModel().getSelectedIndex()>=0 && lv.getSelectionModel().getSelectedIndex()<lv.getItems().size()){
				
				ArrayList<dayInformation> dairyRecord = students.get(lv.getSelectionModel().getSelectedIndex()).getDairyRecord();
			
				Date date = null;
		
				try {
					SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
					String time = dayInformationDatePicker.getValue().toString();
					date = sdf.parse(time);
					
				} catch (ParseException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
				
				dairyRecord.add(new dayInformation(
						date,
						inClassTime_Textfield.getText(),
						outClassTime_Textfield.getText(),
						evaluation_Textfield.getText(),
						guardianName_Textfield.getText()));
				
				
			}
			
			});
				
			find_hbox.getChildren().addAll(find_Button,find_textfield);
			navigationBar .getChildren().addAll(find_hbox,lv);
			information.addRow(0, dayInformation_Label,dayInformationDatePicker);	
			information.addRow(1, guardianName_Label,guardianName_Textfield);	
			information.addRow(2, inClassTime_Label,inClassTime_Textfield,experession_Label);
			information.addRow(3, outClassTime_Label,outClassTime_Textfield,pickUpPeression_Label);
			information.addRow(4, new Label("评价"));
			information.addRow(5, evaluation_Textfield);
			
			right_navigationBar.getChildren().add(stu_Image);
			right_navigationBar.getChildren().add(history);
			right_navigationBar.getChildren().add(xxx_hbox);
			right_navigationBar.getChildren().add(information);
			right_navigationBar.getChildren().add(save_Button);
			rootPane.setLeft(navigationBar);
			rootPane.setCenter(right_navigationBar);
			

			Scene messageScene=new Scene(rootPane,1300,768);
			registerStage.setTitle("学生信息");
			registerStage.setScene(messageScene);
			registerStage.getIcons().add(new Image("icon.png"));
			registerStage.show();
		}

	public void changeInformation(int index){

	
	}
	
	public void trueStage(){
		Stage trueStage=new Stage();
		BorderPane find_BorderPane=new BorderPane();
		Label find_Label=new Label("红色字体为必填信息！");
		 	find_Label.setFont(Font.font(25));
		 	find_Label.setTextFill(Color.RED);
		 //如果这个搜索栏里输入的名字查找不到就
		 	find_BorderPane.setCenter(find_Label);
		Scene scene=new Scene(find_BorderPane,300,150);
		trueStage.setScene(scene);
		trueStage.setResizable(false);//不可改变大小
     //  tureStage.initStyle(StageStyle.TRANSPARENT);//去掉窗口边框
		trueStage.show();
	}



	private void tipsStage() {
		Stage tipStage=new Stage();		
		BorderPane pane=new BorderPane();
		SmsPane sp=new SmsPane();
		EmailPane ep=new EmailPane();
		Button smsbt=new Button("短信发送"),emailbt=new Button("邮件发送");
		smsbt.getStyleClass().add("button1");
		emailbt.getStyleClass().add("button1");
		smsbt.setPrefSize(500, 200);
		emailbt.setPrefSize(500, 200);
		HBox hbox=new HBox();
		hbox.getChildren().addAll(smsbt,emailbt);
		pane.setTop(hbox);
		//载入学生的姓名信息；
				String[]stu=new String[students.size()];
				for (int i = 0; i < students.size(); i++) {
					stu[i]=students.get(i).getName();
				}
			ListView<String> lv=new ListView<>(FXCollections.observableArrayList(stu));
		lv.setMaxWidth(300);
		lv.setMinHeight(500);
		pane.setLeft(lv);
		
		pane.setCenter(sp);
		lv.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		


		smsbt.setOnAction((ActionEvent e)->{
			
			lv.setOnMouseClicked((e1)-> {
				String phoneNumber=null;   
                for (int i = 0; i < students.size(); i++) {
                	if(sp.getTf().getValue()==null){
                		phoneNumber=students.get(lv.getSelectionModel().getSelectedIndex()).getPhoneNumber();
                	}
                	else
                		phoneNumber=sp.getTf().getValue()+";"+students.get(lv.getSelectionModel().getSelectedIndex()).getPhoneNumber();
				}

                lv.getItems().set(lv.getSelectionModel().getSelectedIndex(), lv.getItems().get(lv.getSelectionModel().getSelectedIndex())+"  已选");
                 sp.getTf().setValue(phoneNumber);
                

            

        });
			
			pane.getChildren().removeAll(hbox,ep);
			pane.setTop(hbox);
			pane.setCenter(sp);
		});
		
		emailbt.setOnAction((ActionEvent e)->{
			
			lv.setOnMouseClicked((e2)-> {
				
		        
				String eMailAddress=null;
                for (int i = 0; i < students.size(); i++) {
                	if( ep.getTf().getValue()==null){
                		eMailAddress=students.get(lv.getSelectionModel().getSelectedIndex()).geteMail();
                	}
                	else
                		eMailAddress=ep.getTf().getValue()+";"+students.get(lv.getSelectionModel().getSelectedIndex()).geteMail();
				}

                lv.getItems().set(lv.getSelectionModel().getSelectedIndex(), students.get(lv.getSelectionModel().getSelectedIndex()).getName()+"  已选");
                
                 ep.getTf().setValue(eMailAddress);

            

        });
			pane.getChildren().removeAll(hbox,sp);
			pane.setTop(hbox);
			pane.setCenter(ep);
		});	
		
		tipStage.setTitle("消息通知");
		Scene tipScene=new Scene(pane,1024,768);
		tipScene.getStylesheets().add("stylesheet2.css");
		tipStage.setScene(tipScene);
		
		tipStage.show();
		
	}
	
	public void openFile(File file){
		
			
		
			try {
				String exportFilePath=file.getAbsolutePath();
				desktop.open(file);//打开文件
				} catch (IOException ex) {
					Window.messageWindow("错误", "打开文件失败");
				}
		
		
	}
		
	//从文件读取对象
			public   Object readObjectFromFile(String filePath)
		    {
		        Object temp=null;
		        File file =new File(filePath);
		        FileInputStream in;
		        try {
		            in = new FileInputStream(file);
		            ObjectInputStream objIn=new ObjectInputStream(in);
		            temp=objIn.readObject();
		            objIn.close();
		            FILEPATH=file.getAbsolutePath();
		            windows.Window.messageWindow("", "读取成功");
		        } catch (IOException e) {
		        	windows.Window.messageWindow("", "读取失败");
		            e.printStackTrace();
		        } catch (ClassNotFoundException e) {
		            e.printStackTrace();
		        }
		        return temp;
		    }
//			保存对象到文件
			 public static void writeObjectToFile(Object obj,String filePath)
			    {
			        File file =new File(filePath);
			        FileOutputStream out;
			        try {
			            out = new FileOutputStream(file);
			            ObjectOutputStream objOut=new ObjectOutputStream(out);
			            objOut.writeObject(obj);
			            objOut.flush();
			            objOut.close();
			            windows.Window.messageWindow("", "保存成功");
			        } catch (IOException e) {
			        	windows.Window.messageWindow("", "保存失败");
			            e.printStackTrace();
			        }
			    }
			
			 public static String readTxtFile(String filePath){
				 		String lineTxt = "";
				         try {
				                 String encoding="GBK";
				                 File file=new File(filePath);
				                 if(file.isFile() && file.exists()){ //判断文件是否存在
				                     InputStreamReader read = new InputStreamReader(
				                     new FileInputStream(file),encoding);//考虑到编码格式
				                     BufferedReader bufferedReader = new BufferedReader(read);
				                     
				                     String temp=null;
				                     while((temp = bufferedReader.readLine()) != null){
				                         lineTxt=lineTxt+"\n"+temp;
				                     }
				                     read.close();
				                     
				         }else{
				             System.out.println("找不到指定的文件");
				         
				         }
				         } catch (Exception e) {
				             System.out.println("读取文件内容出错");
				             e.printStackTrace();
				         }
				         return lineTxt;
				      
				     }
//	主函数

	public static void main(String[] args) {
		Application.launch(args);
		try{
			
		}catch(Exception e){
			
		}
		
	}

}
