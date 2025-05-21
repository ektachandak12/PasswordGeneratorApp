# 🔐 Strong Password Generator - Java Swing + MySQL

A desktop application built using Java and Swing to generate secure, customizable passwords. It allows users to save, view, and delete generated passwords using a MySQL database. Built with simplicity and functionality in mind.

---

## 🧰 Features

- ✅ Generate strong passwords with user-defined criteria:
  - Uppercase Letters
  - Lowercase Letters
  - Numbers
  - Special Characters
- ✅ Adjustable password length
- ✅ View password generation history (stored in MySQL)
- ✅ Delete all saved passwords with confirmation
- ✅ GUI-based interface using Java Swing

---

## 📸 UI Overview

### 🧾 Main Window:
- Input field for password length
- Checkboxes to choose character types
- Button to generate password
- Output field displaying the result
- Buttons to:
  - View password history
  - Delete password history

### 📜 History View:
- Displays a table with:
  - ID
  - Generated Password
  - Timestamp

---

## 💻 Technologies Used

- **Java (JDK 17 or later)**
- **Java Swing** for GUI
- **JDBC** for database connectivity
- **MySQL** for data storage
- **IntelliJ IDEA** (Recommended IDE)

---

## ⚙️ Setup Instructions

```bash
### 1. Clone This Repository


git clone https://github.com/your-username/password-generator-app.git
cd password-generator-app

2. Set Up MySQL Database
Use the following SQL commands to create the database and table:
CREATE DATABASE password_manager;
USE password_manager;

CREATE TABLE passwords (
    id INT AUTO_INCREMENT PRIMARY KEY,
    password VARCHAR(255),
    created_at VARCHAR(50)
);

3. Configure Database Credentials
In the file PasswordGeneratorApp.java, update the database credentials to match your MySQL setup:

private static final String DB_URL = "jdbc:mysql://localhost:3306/password_manager";
private static final String DB_USER = "root";           // Your MySQL username
private static final String DB_PASSWORD = "Ekta@1204";  // Your MySQL password

4. Add MySQL JDBC Driver
Download MySQL Connector/J:

🔗 https://dev.mysql.com/downloads/connector/j/

In IntelliJ IDEA:

Go to: File → Project Structure → Libraries

Click ➕ to add the .jar file (mysql-connector-java-8.0.xx.jar)

5. Compile & Run the Program
✅ From IntelliJ IDEA
Open the project.

Ensure mysql-connector-java.jar is in the classpath.

Run the main() method in PasswordGeneratorApp.

✅ From Command Line
bash
Copy code
javac -cp ".;mysql-connector-java-8.0.xx.jar" PasswordGeneratorApp.java
java -cp ".;mysql-connector-java-8.0.xx.jar" PasswordGeneratorApp
Replace xx with the actual version number of your connector.

```

🧪 How to Use
Open the application.

- Enter the desired password length.

- Select the character types to include.

- Click Generate Password.

- The generated password will appear in the text area.

- Use View Password History to see all saved entries.

- Use Delete Password History to clear all saved data.

📁 Project Structure
csharp
Copy code
password-generator-app/
│
├── PasswordGeneratorApp.java         # Main source code
├── mysql-connector-java-8.0.xx.jar   # JDBC driver
├── README.md                         # Project documentation
├── .idea/ / out/                     # IntelliJ-related files (optional)

🧑‍💻 Author
Ekta Naresh Chandak
🎓 BTech, Artificial Intelligence and Data Science
🔗 GitHub: https://github.com/ektachandak12
📧 Email: ektachandak.edu@gmail.com

🪪 License
This project is open-source and available under the MIT License.

🙌 Contributions
Pull requests are welcome!
If you find bugs or have suggestions, feel free to open an issue or contribute directly.
