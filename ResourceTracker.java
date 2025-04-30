package cw2;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class ResourceTracker extends JFrame {
    private ArrayList<Book> books = new ArrayList<>();
    private ArrayList<Equipment> equipmentList = new ArrayList<>();

    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);

    private JLabel titleLabel = new JLabel("Library Books", SwingConstants.CENTER);

    public ResourceTracker() {
        setTitle("Library & Lab Equipment Tracker");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel navPanel = new JPanel(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        JButton booksButton = new JButton("Library Books");
        JButton equipmentButton = new JButton("Lab Equipment");

        booksButton.addActionListener(_ -> {
            cardLayout.show(mainPanel, "BOOKS");
            titleLabel.setText("Library Books");
        });

        equipmentButton.addActionListener(_ -> {
            cardLayout.show(mainPanel, "EQUIPMENT");
            titleLabel.setText("Lab Equipment");
        });

        buttonPanel.add(booksButton);
        buttonPanel.add(equipmentButton);
        navPanel.add(buttonPanel, BorderLayout.NORTH);
        navPanel.add(titleLabel, BorderLayout.SOUTH);

        mainPanel.add(createBooksPanel(), "BOOKS");
        mainPanel.add(createEquipmentPanel(), "EQUIPMENT");

        add(navPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private JPanel createBooksPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> bookList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(bookList);

        JPanel buttonPanel = new JPanel();
        JButton add = new JButton("Add");
        JButton remove = new JButton("Remove");
        JButton increase = new JButton("Increase Copies");
        JButton decrease = new JButton("Decrease Copies");

        add.addActionListener(_ -> {
            String title = JOptionPane.showInputDialog("Enter Book Title:");
            String author = JOptionPane.showInputDialog("Enter Author:");
            String quantityStr = JOptionPane.showInputDialog("Enter Number of Copies:");
            try {
                int quantity = Integer.parseInt(quantityStr);
                Book book = new Book(title, author, quantity);
                books.add(book);
                listModel.addElement(book.toString());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Invalid input.");
            }
        });

        remove.addActionListener(_ -> {
            int index = bookList.getSelectedIndex();
            if (index != -1) {
                books.remove(index);
                listModel.remove(index);
            }
        });

        increase.addActionListener(_ -> {
            int index = bookList.getSelectedIndex();
            if (index != -1) {
                books.get(index).increaseQuantity();
                listModel.set(index, books.get(index).toString());
            }
        });

        decrease.addActionListener(_ -> {
            int index = bookList.getSelectedIndex();
            if (index != -1) {
                books.get(index).decreaseQuantity();
                listModel.set(index, books.get(index).toString());
            }
        });

        buttonPanel.add(add);
        buttonPanel.add(remove);
        buttonPanel.add(increase);
        buttonPanel.add(decrease);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createEquipmentPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> equipmentJList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(equipmentJList);

        JPanel buttonPanel = new JPanel();
        JButton add = new JButton("Add");
        JButton remove = new JButton("Remove");
        JButton increase = new JButton("Increase Quantity");
        JButton decrease = new JButton("Decrease Quantity");

        add.addActionListener(_ -> {
            String name = JOptionPane.showInputDialog("Enter Equipment Name:");
            String quantityStr = JOptionPane.showInputDialog("Enter Quantity:");
            try {
                int quantity = Integer.parseInt(quantityStr);
                Equipment equipment = new Equipment(name, quantity);
                equipmentList.add(equipment);
                listModel.addElement(equipment.toString());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Invalid input.");
            }
        });

        remove.addActionListener(_ -> {
            int index = equipmentJList.getSelectedIndex();
            if (index != -1) {
                equipmentList.remove(index);
                listModel.remove(index);
            }
        });

        increase.addActionListener(_ -> {
            int index = equipmentJList.getSelectedIndex();
            if (index != -1) {
                equipmentList.get(index).increaseQuantity();
                listModel.set(index, equipmentList.get(index).toString());
            }
        });

        decrease.addActionListener(_ -> {
            int index = equipmentJList.getSelectedIndex();
            if (index != -1) {
                equipmentList.get(index).decreaseQuantity();
                listModel.set(index, equipmentList.get(index).toString());
            }
        });

        buttonPanel.add(add);
        buttonPanel.add(remove);
        buttonPanel.add(increase);
        buttonPanel.add(decrease);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    public static void launch() {
        SwingUtilities.invokeLater(ResourceTracker::new);
    }

    class Book {
        private String title;
        private String author;
        private int quantity;

        public Book(String title, String author, int quantity) {
            this.title = title;
            this.author = author;
            this.quantity = quantity;
        }

        public void increaseQuantity() {
        	quantity++;
        }

        public void decreaseQuantity() {
        	if (quantity > 0) quantity--;
        }

        public String toString() {
            return "\"" + title + "\" by " + author + " (Copies: " + quantity + ")";
        }
    }

    class Equipment {
        private String name;
        private int quantity;

        public Equipment(String name, int quantity) {
            this.name = name;
            this.quantity = quantity;
        }

        public void increaseQuantity() {
        	quantity++;
        }

        public void decreaseQuantity() {
        	if (quantity > 0) quantity--;
        }

        public String toString() {
            return name + " (Quantity: " + quantity + ")";
        }
    }
}