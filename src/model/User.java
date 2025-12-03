package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import DataBase.DBConnection;

public class User {

    private int id;
    private String username;
    private String email;
    private String password;
    private String gender;
    private Date dob;
    private String role;
    private Date createdAt;

    private DBConnection db;

    public User() {
        db = DBConnection.getInstance();
    }
    
    // login
    public User login(String email, String password) {

        String query = "SELECT * FROM users WHERE email = ? AND password = ?";

        try {
            PreparedStatement st = db.prepareStatement(query);
            st.setString(1, email);
            st.setString(2, password);

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return mapToUser(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // login gagal
    }

    // customer registration
    public boolean register(String username, String email, String password, String gender, Date dob) {

        String query = "INSERT INTO users(username, email, password, gender, dob, role) "
                     + "VALUES (?, ?, ?, ?, ?, 'Customer')";

        try {
            PreparedStatement st = db.prepareStatement(query);
            st.setString(1, username);
            st.setString(2, email);
            st.setString(3, password);
            st.setString(4, gender);
            st.setDate(5, new java.sql.Date(dob.getTime()));

            int rows = st.executeUpdate();
            return rows == 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // get data by ID
    public User getById(int id) {

        String query = "SELECT * FROM users WHERE id = ?";

        try {
            PreparedStatement st = db.prepareStatement(query);
            st.setInt(1, id);

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return mapToUser(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // insert employee ( khusus admin)
    public boolean insertEmployee(String username, String email, String password, String gender, Date dob, String role) {

        String query = "INSERT INTO users(username, email, password, gender, dob, role) "
                     + "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement st = db.prepareStatement(query);
            st.setString(1, username);
            st.setString(2, email);
            st.setString(3, password);
            st.setString(4, gender);
            st.setDate(5, new java.sql.Date(dob.getTime()));
            st.setString(6, role);

            int rows = st.executeUpdate();
            return rows == 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Update User
    public boolean updateUser(User u) {

        String query = "UPDATE users SET username = ?, email = ?, gender = ?, dob = ? WHERE id = ?";

        try {
            PreparedStatement st = db.prepareStatement(query);
            st.setString(1, u.getUsername());
            st.setString(2, u.getEmail());
            st.setString(3, u.getGender());
            st.setDate(4, new java.sql.Date(u.getDob().getTime()));
            st.setInt(5, u.getId());

            int rows = st.executeUpdate();
            return rows == 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    
    // kalau user mau changePassword
    public boolean changePassword(int id, String newPassword) {

        String query = "UPDATE users SET password = ? WHERE id = ?";

        try {
            PreparedStatement st = db.prepareStatement(query);
            st.setString(1, newPassword);
            st.setInt(2, id);

            int rows = st.executeUpdate();
            return rows == 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }



    // Buat Delete User
    public boolean deleteUser(int id) {

        String query = "DELETE FROM users WHERE id = ?";

        try {
            PreparedStatement st = db.prepareStatement(query);
            st.setInt(1, id);

            int rows = st.executeUpdate();
            return rows == 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // passing data ke user abis log in
    private User mapToUser(ResultSet rs) throws SQLException {

        User u = new User();

        u.id = rs.getInt("id");
        u.username = rs.getString("username");
        u.email = rs.getString("email");
        u.password = "";
        u.gender = rs.getString("gender");
        u.dob = rs.getDate("dob");
        u.role = rs.getString("role");
        u.createdAt = rs.getTimestamp("created_at");

        return u;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
 
}
