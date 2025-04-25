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

import coursework.CourseWork_2.Expense;

import java.awt.*;

public class CourseWork_2 {

public static class Room
{
	private int Room_Number;
	private String Room_Location;
	private ArrayList<Room_Ocupied> Room_Ocupied_ID;
	
	public Room(int Room_Number,String Room_Location) 
	{
		this.Room_Number=Room_Number;
		this.Room_Location=Room_Location;
	}
    public int Get_Room_Number() {
    	return Room_Number;}
    public String Get_Room_Location() {
    	return Room_Location;}
}
public static class Room_Ocupied
{
	private int Room_Ocupied_ID;
	private int Ocupied_Time;
	private String Ocupied_User;
	
	public Room_Ocupied(int Room_Ocupied_ID,int Ocupied_Time, String Ocupied_User) 
	{
		this.Room_Ocupied_ID=Room_Ocupied_ID;
		this.Ocupied_Time=Ocupied_Time;
		this.Ocupied_User=Ocupied_User;	
	}
}

public static void main(String args[]) 
{ 
	System.out.println("hello");
	ArrayList<Room> All_Rooms = new ArrayList<Room>();
    To_Do_List_Upload(All_Rooms);
    System.out.println("testing: " + All_Rooms.toString());
	Interface(All_Rooms);
}

public void Room_Details() 
{
	Scanner Inputs= new Scanner(System.in);
	System.out.println("What is the Room ID");
	int Room_Number=Inputs.nextInt();
	System.out.println("What is the Room location");
	String Room_Location=Inputs.nextLine();
	new Room(Room_Number, Room_Location);
	Inputs.close();
}

public static void Interface(ArrayList<Room>All_Rooms)
{
	JFrame Intro_Frame = new JFrame();
	Intro_Frame.setVisible(true);
	Intro_Frame.setTitle("Look for room");
	Intro_Frame.setSize(500,500);
	Intro_Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	JPanel Input_Panel=new JPanel();
	//input panel
	Input_Panel.setLayout(new BoxLayout(Input_Panel,BoxLayout.Y_AXIS));
	
	JComboBox Room_Location_Choice = new JComboBox();
	for(Room Room_Detail:All_Rooms) 
	{
		Room_Location_Choice.addItem(Room_Detail.Get_Room_Location());}
	Input_Panel.add(Room_Location_Choice);
	
	JComboBox Room_ID_Choice = new JComboBox();
	Input_Panel.add(Room_ID_Choice);
	Room_Location_Choice.addActionListener(new ActionListener(){
    	@Override
        public void actionPerformed(ActionEvent e) {
    		for(Room Room_Detail:All_Rooms) 
    		{
    			if(Room_Detail.Get_Room_Location()==Room_Location_Choice.getSelectedItem())
    			{
    				Room_ID_Choice.addItem(Room_Detail.Get_Room_Number());
    			}
    		}
    	}
    });
	
	
	JTextField Room_ID=new JTextField("Input room ID");
	JTextField Room_Location=new JTextField("Input room Location");//change to drop down option
	JButton Submit=new JButton("Submit");
	Input_Panel.add(Room_ID);
	Input_Panel.add(Room_Location);
	Input_Panel.add(Submit);
	Intro_Frame.add(Input_Panel);
	Intro_Frame.revalidate();
	
	//time select panel
	
	JPanel Time_Panel=new JPanel(new GridLayout(0,7,5,5));
	Calendar c = Calendar.getInstance(); 
	ArrayList<String> Date_Slot_Array = new ArrayList<String>();
	
	for (int Date_Loop = 0; Date_Loop < 7; Date_Loop++) 
	{
		String Date_Format = " %d:%d:%d";
		String Date_Formatted = String.format(Date_Format, c.get(Calendar.DATE), c.get(Calendar.MONTH) + 1, c.get(Calendar.YEAR));
		Date_Slot_Array.add(Date_Formatted);
		JLabel Date=new JLabel(Date_Formatted);
		c.add(Calendar.DATE,1);
		Time_Panel.add(Date);
	}

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
			int Date_Number=Date_Loop;
			Time_Slot_Button.addActionListener(new ActionListener(){
		    	@Override
		        public void actionPerformed(ActionEvent e) {
		    		for (JButton Buttons : Time_Slot_Array) {
		    			Buttons.setBackground(null);
                    }	    		
		    		String Date=Date_Slot_Array.get(Date_Number);
		    		Selected_Time_Slot.set(0,Selected_Time+Date);
                    System.out.println("Selected Time Slot: " + Selected_Time_Slot);
                    Time_Slot_Button.setBackground(Color.GREEN);
		        }
		    });
		}
	}  
	
	JButton Submission=new JButton("Submit");
	Time_Panel.add(Submission);
	//actions
	Submission.addActionListener(new ActionListener(){
    	@Override
        public void actionPerformed(ActionEvent e) {
    		//check then save
    		Intro_Frame.remove(Time_Panel);
    		Intro_Frame.revalidate();
    		System.out.println("Final Selected Time Slot: " + Selected_Time_Slot);
        }
    });
	
	Submit.addActionListener(new ActionListener(){
    	@Override
        public void actionPerformed(ActionEvent e) {
    		//check then save
    		Intro_Frame.remove(Input_Panel);
    		Intro_Frame.revalidate();
    		Intro_Frame.add(Time_Panel);
        }
    });
}

/*
public void To_Do_List_Save(String Selected_Time_Slot) 
{
    try {
    	FileWriter Task_Files = new FileWriter("CourseWork_2_Extra\\Rooms.txt",true);
        BufferedWriter File_Writer = new BufferedWriter(Task_Files);
        File_Writer.write(Selected_Time_Slot);//say where to write
        File_Writer.close();
    } catch (IOException e) {
    	e.printStackTrace();
    }
}
*/

public static void To_Do_List_Upload(ArrayList<Room>All_Rooms) 
{
	try {
		File File = new File("CourseWork_2_Extra\\Rooms");
		Scanner File_Reader = new Scanner(File);
        if (!File.exists() || File.length() == 0) {
        	File_Reader.close();
            return;
        }
        else {
        	while (File_Reader.hasNextLine()) {
        		String Rooms = File_Reader.nextLine();
        		String[] Room_Features = Rooms.split("/");
        		int Room_ID = Integer.parseInt(Room_Features[0]);
        		String Room_Location = Room_Features[1];
        		//int Room_Ocupied_ID = Integer.parseInt(Room_Features[2]);
        		All_Rooms.add(new Room(Room_ID,Room_Location));
        	}
        }
		File_Reader.close();
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
}

/*
public void To_Do_List_Delete(String Remove)
{
	try {
		File Old_File = new File("CourseWork_2_Extra\\Rooms.txt");
		File Temp_File = new File("CourseWork_2_Extra\\Temp_Rooms.txt");
		
		FileWriter File_Writer = new FileWriter(Temp_File);
        BufferedWriter Buffered_Writer = new BufferedWriter(File_Writer);

		Scanner File_Reader = new Scanner(Old_File);
		while (File_Reader.hasNextLine()) {
			String Task = File_Reader.nextLine();
			if (!Task.equals(Remove)) {
				Buffered_Writer.write(Task +"\n");
		}
		}
		File_Reader.close();
		Buffered_Writer.close();
		Old_File.delete();
		Temp_File.renameTo(Old_File);
	}catch (Exception e) {
		e.printStackTrace();}
}
*/


}
