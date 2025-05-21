import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Random;

public class PasswordGeneratorApp extends JFrame {
    private JTextField lengthField;
    private JCheckBox upperCaseBox, lowerCaseBox, numberBox, specialCharBox;
    private JTextArea outputArea;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/password_manager";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Ekta@1204";

    public PasswordGeneratorApp() {
        setTitle("Strong Password Generator");
        setSize(500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setupDatabase();
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        lengthField = new JTextField();
        upperCaseBox = new JCheckBox("Include Uppercase");
        lowerCaseBox = new JCheckBox("Include Lowercase");
        numberBox = new JCheckBox("Include Numbers");
        specialCharBox = new JCheckBox("Include Special Characters");

        JButton generateButton = new JButton("Generate Password");
        JButton historyButton = new JButton("View Password History");
        JButton deleteHistoryButton = new JButton("Delete Password History");

        outputArea = new JTextArea(3, 30);
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        panel.add(new JLabel("Enter Password Length:"));
        panel.add(lengthField);
        panel.add(upperCaseBox);
        panel.add(lowerCaseBox);
        panel.add(numberBox);
        panel.add(specialCharBox);
        panel.add(generateButton);
        panel.add(historyButton);
        panel.add(deleteHistoryButton);
        panel.add(new JLabel("Generated Password:"));
        panel.add(scrollPane);

        add(panel);

        generateButton.addActionListener(e -> generatePassword());
        historyButton.addActionListener(e -> showPasswordHistory());
        deleteHistoryButton.addActionListener(e -> deletePasswordHistory());
    }

    private void generatePassword() {
        int length;
        try {
            length = Integer.parseInt(lengthField.getText().trim());
            if (length <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid password length.");
            return;
        }

        StringBuilder characterSet = getCharacterSet();

        if (characterSet.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Select at least one character type.");
            return;
        }

        StringBuilder password = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < length; i++) {
            int index = rand.nextInt(characterSet.length());
            password.append(characterSet.charAt(index));
        }

        String generatedPassword = password.toString();
        outputArea.setText(generatedPassword);
        saveToDatabase(generatedPassword);
    }

    private StringBuilder getCharacterSet() {
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String special = "!@#$%^&*()-_=+[{]}|;:'\",<.>/?";

        StringBuilder characterSet = new StringBuilder();
        if (upperCaseBox.isSelected()) characterSet.append(upper);
        if (lowerCaseBox.isSelected()) characterSet.append(lower);
        if (numberBox.isSelected()) characterSet.append(numbers);
        if (specialCharBox.isSelected()) characterSet.append(special);

        return characterSet;
    }

    private void setupDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS passwords (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "password VARCHAR(255), " +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database setup error: " + e.getMessage());
        }
    }

    private void saveToDatabase(String password) {
        String sql = "INSERT INTO passwords(password, created_at) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, password);
            pstmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error saving password: " + e.getMessage());
        }
    }

    private void showPasswordHistory() {
        JFrame historyFrame = new JFrame("Password History");
        historyFrame.setSize(600, 300);
        historyFrame.setLocationRelativeTo(this);

        String[] columnNames = {"ID", "Password", "Generated At"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);

        String sql = "SELECT * FROM passwords ORDER BY id DESC";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String password = rs.getString("password");
                Timestamp timestamp = rs.getTimestamp("created_at");
                tableModel.addRow(new Object[]{id, password, timestamp});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching history: " + e.getMessage());
        }

        JScrollPane scrollPane = new JScrollPane(table);
        historyFrame.add(scrollPane);
        historyFrame.setVisible(true);
    }

    private void deletePasswordHistory() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete all saved passwords?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("DELETE FROM passwords");
                JOptionPane.showMessageDialog(this, "All saved passwords deleted.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error deleting history: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PasswordGeneratorApp().setVisible(true));
    }
}
