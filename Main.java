package cw2;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class Main extends JFrame {

    public Main() {
        setTitle("School Resource Management System");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1, 10, 10));

        JButton bookerBtn = new JButton("Room Booker");
        JButton studentBtn = new JButton("Student Records");
        JButton resourceBtn = new JButton("Resource Tracker");
        JButton exitBtn = new JButton("Exit");

        bookerBtn.addActionListener(e -> RoomBooker.launch());
        studentBtn.addActionListener(e -> StudentRecords.launch());
        resourceBtn.addActionListener(e -> ResourceTracker.launch());
        exitBtn.addActionListener(e -> System.exit(0));

        add(bookerBtn);
        add(studentBtn);
        add(resourceBtn);
        add(exitBtn);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}
