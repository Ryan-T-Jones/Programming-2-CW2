package cw2;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;


@SuppressWarnings("serial")
public class StudentRecords extends JFrame{
	private DefaultListModel<String> ListModel;
	@SuppressWarnings("unused")
	private JList<String> TaskList;
	private JTextField TaskField;
	private JTable Table;
	private DefaultTableModel model;
	private JTextField DateField;
	private JComboBox<String> GradeBox;
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
	private List<Student> Students = new ArrayList<>();
	
	public StudentRecords() {
		setTitle("Student Records");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1200,400);
		setLayout(new BorderLayout());
		
		ListModel = new DefaultListModel<>();
		TaskList = new JList<>(ListModel);
		
		
		
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		TaskField = new JTextField(20);
		
		
		panel.add(new JLabel("Student Name:"));
		panel.add(TaskField);
		
		panel.add(new JLabel(" Enrolment Date (DD/MM/YYYY):"));
		DateField = new JTextField(20);
		panel.add(DateField);
		
		panel.add(new JLabel("Grade"));
		String[] grades = {"A", "B", "C"};
		GradeBox = new JComboBox<>(grades);
		panel.add(GradeBox);
		
		
		JButton AddButton = new JButton("Add Student");
		AddButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				@SuppressWarnings("unused")
				String Task = TaskField.getText().trim();
				AddStudent();
			
			}
		});
		JButton RemoveButton = new JButton("Remove Student");
		RemoveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DeleteStudent();
				
			}
		});
		
	
		panel.add(AddButton);
		panel.add(RemoveButton);
	
		
		add(panel,BorderLayout.SOUTH);
		
		//Setup for the table below the panel
		model = new DefaultTableModel(new Object[] {"Student Name", "Enrolment Date", "Grade"},0) {
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return (columnIndex == 2) ? String.class : String.class;
			}
		};
		Table = new JTable(model);
				JScrollPane scrollPane1 = new JScrollPane(Table);
				add(scrollPane1,BorderLayout.CENTER);
	}
	
	//adding a task
	private void AddStudent() {
		String name =TaskField.getText().trim();
		String EnrolmentDate = DateField.getText().trim();
		String Grade = (String) GradeBox.getSelectedItem();
			
		if(!name.isEmpty() && IsValidDate(EnrolmentDate)) {
			Student newTask = new Student(name,EnrolmentDate, Grade);
			Students.add(newTask);
			ListModel.addElement(name);
			UpdateTable();
			TaskField.setText("");
			DateField.setText("");
				
		}else {
			JOptionPane.showMessageDialog(this, "Please Try Again!");
				
		}
			
	}
		
		
		//validating the date format
		private boolean IsValidDate(String date) {
			try {
				DATE_FORMAT.setLenient(false);
				DATE_FORMAT.parse(date);
				return true;
			} catch (ParseException e) {
				return false;
			}
		}
		
		//deleting a task
		private void DeleteStudent() {
			int selectedIndex = Table.getSelectedRow();
			if (selectedIndex >=0) {
				Students.remove(selectedIndex);
				ListModel.remove(selectedIndex);
				UpdateTable();
			} else {
				JOptionPane.showMessageDialog(this, "PLease Select a Record that can be Removed!");
			}
		}
		//updating the table
	private void UpdateTable() {
		model.setRowCount(0);
		for(Student student : Students) {
		model.addRow(new Object[] {student.getName(), student.getEnrolmentDate(),student.getGrade()});;
		}
	}
			
	// Task Class
	static class Student{
		private String Name;
		private String EnrolmentDate;
		private String Grade;
		
			
		public Student(String Name, String EnrolmentDate, String Grade) {
			this.Name = Name;
			this.EnrolmentDate = EnrolmentDate;
			this.Grade = Grade;
		}
			

	
		public String getName() { return Name; }
		public String getEnrolmentDate() {return EnrolmentDate;}
		public String getGrade() {return Grade;}
		}
			
	public static void launch() {
	    SwingUtilities.invokeLater(() -> new StudentRecords().setVisible(true));
	}
	}
	
	
