import java.util.ArrayList;
import java.util.Scanner;
import java.util.Calendar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*; 
import javax.swing.*;
import java.awt.*;

public class CourseWork_2 {

public static class Room
{
	private int Room_Number;
	private String Room_Location;
	private ArrayList<String> Room_Ocupied_Date;
	
	public Room(int Room_Number,String Room_Location, ArrayList<String> Room_Ocupied_Date) 
	{
		this.Room_Number=Room_Number;
		this.Room_Location=Room_Location;
		this.Room_Ocupied_Date=Room_Ocupied_Date;
	}
    public int Get_Room_Number() {
    	return Room_Number;}
    public String Get_Room_Location() {
    	return Room_Location;}
    public ArrayList<String> Get_Room_Ocupied_Date() {
    	return Room_Ocupied_Date;}
}

public static void main(String args[]) 
{ 
	ArrayList<Room> All_Rooms = new ArrayList<Room>();
    Rooms_Upload(All_Rooms);
	Interface(All_Rooms);
}

public static void Interface(ArrayList<Room>All_Rooms)
{
	//input panel
	JFrame Intro_Frame = new JFrame();
	Intro_Frame.setVisible(true);
	Intro_Frame.setTitle("Look for room");
	Intro_Frame.setSize(500,500);
	Intro_Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	JPanel Input_Panel=new JPanel();
	Input_Panel.setLayout(new BoxLayout(Input_Panel,BoxLayout.Y_AXIS));
	Intro_Frame.setMaximumSize(new Dimension(600, 200));
	//room location selection
	JComboBox Room_Location_Choice = new JComboBox();
	Room_Location_Choice.setPreferredSize(new Dimension(250, 60));
	ArrayList<String> Room_Locations_Array = new ArrayList<String>();
	
	for(Room Room_Detail:All_Rooms)
	{
		if(!Room_Locations_Array.contains(Room_Detail.Get_Room_Location()))
		{
			Room_Location_Choice.addItem(Room_Detail.Get_Room_Location());
		}
		Room_Locations_Array.add(Room_Detail.Get_Room_Location());
	}
	Input_Panel.add(Room_Location_Choice);
	//room number selection
	Input_Panel.add(Box.createRigidArea(new Dimension(25,10)));
	JComboBox Room_Number_Choice = new JComboBox();
	Room_Number_Choice.setPreferredSize(new Dimension(250, 60));
	Input_Panel.add(Room_Number_Choice);
	Room_Location_Choice.addActionListener(new ActionListener(){
    	@Override
        public void actionPerformed(ActionEvent e) {
    		Room_Number_Choice.removeAllItems();
    		for(Room Room_Detail:All_Rooms) 
    		{
    			if(Room_Detail.Get_Room_Location().equals(Room_Location_Choice.getSelectedItem()))
    			{
    				Room_Number_Choice.addItem(Room_Detail.Get_Room_Number());
    			}
    		}
    	}
    });
	JButton Submit=new JButton("Submit");
	Input_Panel.add(Submit);
	Intro_Frame.add(Input_Panel);
	Intro_Frame.revalidate();
	
	
	
	ArrayList<String> Room_Ocupied_Dates = new ArrayList<String>();
	
	Submit.addActionListener(new ActionListener(){
    	@Override
        public void actionPerformed(ActionEvent e) {
    		//check then save
    		Intro_Frame.remove(Input_Panel);
    		//String Room_Data=(String)Room_Location_Choice.getSelectedItem()+"/"+Room_Number_Choice.getSelectedItem();
    		String Room_Data=Room_Number_Choice.getSelectedItem()+"/"+(String)Room_Location_Choice.getSelectedItem()+"/";
    		Intro_Frame.revalidate();
    		for(Room Room_Detail:All_Rooms) 
    		{
    			if(Room_Detail.Get_Room_Number()==(int)Room_Number_Choice.getSelectedItem()){
    				Room_Ocupied_Dates.addAll(Room_Detail.Get_Room_Ocupied_Date());}
    		}
    		Interface_Time(Intro_Frame,All_Rooms,Room_Ocupied_Dates,Room_Data);
        }
    });
}
public static void Interface_Time(Frame Intro_Frame,ArrayList<Room>All_Rooms,ArrayList<String>Room_Ocupied_Dates,String Room_Data)
{
	//time select panel
	JPanel Time_Panel=new JPanel(new GridLayout(0,7,5,5));
	Intro_Frame.add(Time_Panel);
	Calendar c = Calendar.getInstance(); 
	ArrayList<String> Date_Slot_Array = new ArrayList<String>();
	//get date
	for (int Date_Loop = 0; Date_Loop < 7; Date_Loop++) 
	{
		String Date_Format = " %d:%d:%d";
		String Date_Formatted = String.format(Date_Format, c.get(Calendar.DATE), c.get(Calendar.MONTH) + 1, c.get(Calendar.YEAR));
		Date_Slot_Array.add(Date_Formatted);
		JLabel Date=new JLabel(Date_Formatted);
		c.add(Calendar.DATE,1);
		Time_Panel.add(Date);
	}
	//create time slot buttons
	ButtonGroup Button_Group = new ButtonGroup();
	ArrayList<JButton> Time_Slot_Array = new ArrayList<JButton>();
	ArrayList<String> Selected_Time_Slot = new ArrayList<String>();
	Selected_Time_Slot.add("");
	
	for (int Hour_Loop = 9; Hour_Loop < 17; Hour_Loop++) 
	{
		for (int Date_Loop = 0; Date_Loop < 7; Date_Loop++) 
		{
			String Selected_Time=Hour_Loop+":00";
			JButton Time_Slot_Button=new JButton(Selected_Time);
			Button_Group.add(Time_Slot_Button);
			Time_Panel.add(Time_Slot_Button);
			Time_Slot_Array.add(Time_Slot_Button);

			String Date=Date_Slot_Array.get(Date_Loop);
			Selected_Time_Slot.set(0,Selected_Time+Date);
			
			if (Room_Ocupied_Dates.contains(Selected_Time_Slot.get(0))) {
			    Time_Slot_Button.setEnabled(false);}

			Time_Slot_Button.addActionListener(new ActionListener(){
		    	@Override
		        public void actionPerformed(ActionEvent e) {
		    		for (JButton Buttons : Time_Slot_Array) {
		    			Buttons.setBackground(null);
                    }	    	
		    		Selected_Time_Slot.set(0,Selected_Time+Date);	
                    Time_Slot_Button.setBackground(Color.GREEN);
		        }
		    });
		}
	}  
	
	
	JButton Submission=new JButton("Submit");
	Time_Panel.add(Submission);
	Submission.addActionListener(new ActionListener(){
    	@Override
        public void actionPerformed(ActionEvent e) {
    		//check then save
    		Intro_Frame.remove(Time_Panel);
    		Intro_Frame.revalidate();
    		Rooms_Save(Selected_Time_Slot,Room_Data);
        }
    });
}


public static void Rooms_Save(ArrayList<String> Selected_Time_Slot,String Room_Data) 
{
    try {
    	String Saving = String.join("/", Selected_Time_Slot);
    	File Old_File = new File("CourseWork_2_Extra\\Rooms");
    	File Temp_File = new File("CourseWork_2_Extra\\Temp_Rooms");
    		
    	FileWriter File_Writer = new FileWriter(Temp_File);
        BufferedWriter Buffered_Writer = new BufferedWriter(File_Writer);

    	Scanner File_Reader = new Scanner(Old_File);
    	while (File_Reader.hasNextLine()) {
    		String Line = File_Reader.nextLine();
    		if (Line.contains(Room_Data)) {
    			Buffered_Writer.write(Line +"/"+Saving+"\n");}
    		else {
    			Buffered_Writer.write(Line +"\n");}
    	}
    	File_Reader.close();
    	Buffered_Writer.close();
    	Old_File.delete();
    	Temp_File.renameTo(Old_File);
    }catch (Exception e) {
    	e.printStackTrace();}
}

public static void Rooms_Upload(ArrayList<Room>All_Rooms) 
{
	try {
		File File = new File("CourseWork_2_Extra\\Rooms");
		Scanner File_Reader = new Scanner(File);
        if (!File.exists() || File.length() == 0) {
        	File_Reader.close();
            return;
        }
        else {
        	while (File_Reader.hasNextLine()) 
        	{
        		String Rooms = File_Reader.nextLine();
        		String[] Room_Features = Rooms.split("/");
        		int Room_ID = Integer.parseInt(Room_Features[0]);
        		String Room_Location = Room_Features[1];
        		ArrayList<String> Room_Ocupied_Date = new ArrayList<String>();
        		for (int i = 2; i < Room_Features.length; i++) {
        			Room_Ocupied_Date.add( Room_Features[i]);}
        		All_Rooms.add(new Room(Room_ID,Room_Location,Room_Ocupied_Date));
        	}
        }
		File_Reader.close();
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
}

}
