package DataBase;

import java.sql.SQLException;
import java.sql.Statement;

public class Migration {
	private DBConnection db;

    public Migration() {
        db = DBConnection.getInstance();

        try {
            Statement stmt = db.getStatement();

            stmt.executeUpdate("DROP TABLE IF EXISTS notifications");
            stmt.executeUpdate("DROP TABLE IF EXISTS transactions");
            stmt.executeUpdate("DROP TABLE IF EXISTS services");
            stmt.executeUpdate("DROP TABLE IF EXISTS users");

            // USERS ( untuk employee dan customer Employee + Customer)
            String usersTable = ""
                + "CREATE TABLE users ("
                + "  id INT PRIMARY KEY AUTO_INCREMENT,"
                + "  username VARCHAR(100) NOT NULL UNIQUE,"
                + "  email VARCHAR(255) NOT NULL UNIQUE,"
                + "  password VARCHAR(255) NOT NULL,"
                + "  gender VARCHAR(10) NOT NULL,"
                + "  dob DATE NOT NULL,"
                + "  role VARCHAR(20) NOT NULL,"
                + "  created_at DATETIME DEFAULT CURRENT_TIMESTAMP"
                + ") ENGINE=InnoDB;";

            stmt.executeUpdate(usersTable);
            
            String fixAdmin = "INSERT INTO users (username, email, password, gender, dob, role) " +
                    "VALUES ('FixAdmin', 'admin@govlash.com', 'admin123', 'Male', '1990-01-01', 'Admin')";
 stmt.executeUpdate(fixAdmin);

            // SERVICES
            String servicesTable = ""
                + "CREATE TABLE services ("
                + "  id INT PRIMARY KEY AUTO_INCREMENT,"
                + "  name VARCHAR(50) NOT NULL,"
                + "  description VARCHAR(250) NOT NULL,"
                + "  price DECIMAL(12,2) NOT NULL,"
                + "  duration_days INT NOT NULL,"
                + "  created_at DATETIME DEFAULT CURRENT_TIMESTAMP"
                + ") ENGINE=InnoDB;";

            stmt.executeUpdate(servicesTable);

            // TRANSACTIONS
            String transactionsTable = ""
                + "CREATE TABLE transactions ("
                + "  id INT PRIMARY KEY AUTO_INCREMENT,"
                + "  service_id INT NOT NULL,"
                + "  customer_id INT NOT NULL,"
                + "  receptionist_id INT DEFAULT NULL,"
                + "  laundry_staff_id INT DEFAULT NULL,"
                + "  transaction_date DATETIME DEFAULT CURRENT_TIMESTAMP,"
                + "  transaction_status VARCHAR(20) NOT NULL DEFAULT 'Pending',"
                + "  total_weight DECIMAL(6,2) NOT NULL,"
                + "  notes VARCHAR(250),"
                + "  FOREIGN KEY (service_id) REFERENCES services(id)"
                + "      ON UPDATE CASCADE ON DELETE RESTRICT,"
                + "  FOREIGN KEY (customer_id) REFERENCES users(id)"
                + "      ON UPDATE CASCADE ON DELETE RESTRICT,"
                + "  FOREIGN KEY (receptionist_id) REFERENCES users(id)"
                + "      ON UPDATE CASCADE ON DELETE SET NULL,"
                + "  FOREIGN KEY (laundry_staff_id) REFERENCES users(id)"
                + "      ON UPDATE CASCADE ON DELETE SET NULL"
                + ") ENGINE=InnoDB;";

            stmt.executeUpdate(transactionsTable);

            //NOTIFICATION
            String notificationsTable = ""
                + "CREATE TABLE notifications ("
                + "  id VARCHAR(50) PRIMARY KEY,"
                + "  recipient_id INT NOT NULL,"
                + "  message VARCHAR(500) NOT NULL,"
                + "  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,"
                + "  is_read TINYINT(1) NOT NULL DEFAULT 0,"
                + "  FOREIGN KEY (recipient_id) REFERENCES users(id)"
                + "      ON UPDATE CASCADE ON DELETE CASCADE"
                + ") ENGINE=InnoDB;";

            stmt.executeUpdate(notificationsTable);

            System.out.println("Migration completed successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
