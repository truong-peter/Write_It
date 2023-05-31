package db;

import java.sql.*;

public class Database {
	private String url;

	/**
	 * const for Database
	 * 
	 * @param u the filepath for Database
	 */
	public Database(String u) {
		this.url = u;
	}

	// set up connection to WriteIt.db
	public Connection connect() {
		// SQLite connection string

		Connection conn = null;
		try {
			conn = DriverManager.getConnection(this.url);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}

	// create tables for data storage
	public void createTables() {
		// String to create password table
		String password = "CREATE TABLE IF NOT EXISTS password (\nid integer PRIMARY KEY, \n key text NOT NULL);";

		// String to set default password
		String defPassword = "INSERT INTO password(key) VALUES ('p')";

		// String to create table for user data
		String user = "CREATE TABLE IF NOT EXISTS user (\n" + "id integer PRIMARY KEY, \n" + "firstName text NOT NULL,\n"
				+ "lastName text NOT NULL, \n title text NOT NULL, \n" + "school text NOT NULL, \n department text NOT NULL, "
				 + "\n email text NOT NULL, \n" + "phoneNumber real \n);";

		// String to set default user data
		String defUser = "INSERT INTO user(firstName, lastName, title, school, department, email, phoneNumber) VALUES(?, ?, ?, ?, ?, ?, ?)\n";

		// String to create table for semester list
		String semesters = "CREATE TABLE IF NOT EXISTS semesters (\n" + "id integer PRIMARY KEY AUTOINCREMENT, \n"
				+ "semesterName text NOT NULL \n);";

		// String to set default semesters
		String defSemester = "INSERT INTO semesters(semesterName) VALUES(?)\n";

		// List of default semesters
		String[] semesterList = { "Spring", "Summer", "Fall" };

		// String to create table for course list
		String courses = "CREATE TABLE IF NOT EXISTS courses (\n" + "id integer PRIMARY KEY AUTOINCREMENT, \n"
				+ "courseName text NOT NULL \n);";

		// String to set default courses
		String defCourses = "INSERT INTO courses(courseName) VALUES(?)\n";

		// List of default courses
		String[] courseList = { "CS151: Object-Oriented Design", "CS166: Information Security",
				"CS154: Theory of Computation", "CS160: Software Engineering", "CS256: Cryptography",
				"CS146: Data Structures and Algorithms", "CS152: Programming Languages Paradigm" };

		// String to create table for program list
		String programs = "CREATE TABLE IF NOT EXISTS programs (\n" + "id integer PRIMARY KEY AUTOINCREMENT, \n"
				+ "programName text NOT NULL \n);";

		// String to set default programs
		String defPrograms = "INSERT INTO programs(programName) VALUES(?)\n";

		// List of default programs
		String[] programList = { "Master of science (MS)", "Master of business administration (MBA)",
				"Doctor of philosophy (PhD)" };

		// String to create table for characteristics
		// personal chars -- type = 0; academic chars type = 1
		String characteristics = "CREATE TABLE IF NOT EXISTS characteristics (\n" + "id integer PRIMARY KEY AUTOINCREMENT, \n"
				+ "type integer, \n" + "description text NOT NULL \n);";

		// String to set default characteristics
		String defChars = "INSERT INTO characteristics(type,description) VALUES(?,?)\n";

		// List of default personal characteristics
		String[] personalList = { "very passionate", "very enthusiastic", "punctual", "attentive", "polite" };

		// List of default academic characteristics
		String[] academicList = { "submitted well-written assignments", "participated in all of my class activities",
				"worked hard", "was very well prepared for every exam and assignment", "picked up new skills quickly",
				"was able to excel academically at the top of my class" };

		// String to create table for recommendations
		String recommendations = "CREATE TABLE IF NOT EXISTS recommendations (id integer PRIMARY KEY AUTOINCREMENT, \n"
				+ "hash int NOT NULL, \n" + "firstName text NOT NULL, \n" + "lastName text NOT NULL, \n"
				+ "gender text NOT NULL, \n" + "schoolName text NOT NULL, \n" + "selectedDate date NOT NULL, \n"
				+ "program text NOT NULL, \n" + "semester text NOT NULL, \n" + "year text NOT NULL\n);";
		
		// String to create table to store grade info by student hash and course id
		String grades = "CREATE TABLE IF NOT EXISTS grades (id integer PRIMARY KEY AUTOINCREMENT, \n"
				+ "studentID int NOT NULL, \n" + "courseID int NOT NULL, \n" + "grade text NOT NULL\n);";
		
		// String to create table to store student characteristic data
		String studentChars = "CREATE TABLE IF NOT EXISTS studentChars (\n id integer PRIMARY KEY AUTOINCREMENT, \n"
				+ "studentID int NOT NULL, \n" + "characteristicID int NOT NULL \n);";
		
		// create tables
		try (Connection conn = this.connect(); Statement stmt = conn.createStatement()) {
			// create tables
			stmt.execute(password);
			stmt.execute(user);
			stmt.execute(semesters);
			stmt.execute(courses);
			stmt.execute(programs);
			stmt.execute(characteristics);
			stmt.execute(recommendations);
			stmt.execute(grades);
			stmt.execute(studentChars);

			// checks if tables are empty, if yes, insert default data
			// query for number of rows in password
			ResultSet rs = stmt.executeQuery("SELECT Count(rowid) FROM password");
			// if no password has been set, set to default
			if (rs.getInt(1) == 0) {
				stmt.executeUpdate(defPassword);
				System.out.println("Set default password");
			}

			// query for number of rows in user
			rs = stmt.executeQuery("SELECT Count(rowid) FROM user");
			// if no user data has been entered, set to default
			if (rs.getInt(1) == 0) {
				PreparedStatement pstmt = conn.prepareStatement(defUser);

				// set parameters
				pstmt.setString(1, "Ahmad");
				pstmt.setString(2, "Yazdankhah");
				pstmt.setString(3, "Lecturer");
				pstmt.setString(4, "SJSU");
				pstmt.setString(5, "CS Department");
				pstmt.setString(6, "ayazdankhah@sjsu.edu");
				pstmt.setString(7, "(123) 456-7890");

				// update database
				pstmt.execute();
				System.out.println("Set default user data");
			}

			// query for number of rows in semesters
			rs = stmt.executeQuery("SELECT Count(rowid) FROM semesters");
			// if none, set to default
			if (rs.getInt(1) == 0) {
				for (int i = 0; i < semesterList.length; i++) {
					insert(conn, defSemester, semesterList[i]);
				}
				System.out.println("Inserted default semesters");
			}

			// query for number of rows in courses
			rs = stmt.executeQuery("SELECT Count(rowid) FROM courses");
			// if none, set to default
			if (rs.getInt(1) == 0) {
				for (int i = 0; i < courseList.length; i++) {
					insert(conn, defCourses, courseList[i]);
				}
				System.out.println("Inserted default courses");
			}

			// query for number of rows in programs
			rs = stmt.executeQuery("SELECT Count(rowid) FROM programs");
			// if none, set to default
			if (rs.getInt(1) == 0) {
				for (int i = 0; i < programList.length; i++) {
					insert(conn, defPrograms, programList[i]);
				}
				System.out.println("Inserted default programs");
			}

			// query for number of rows in programs
			rs = stmt.executeQuery("SELECT Count(rowid) FROM characteristics");
			// if none, set to default
			if (rs.getInt(1) == 0) {
				for (int i = 0; i < personalList.length; i++) {
					insertWithID(conn, defChars, 0, personalList[i]);
				}
				for (int i = 0; i < academicList.length; i++) {
					insertWithID(conn, defChars, 1, academicList[i]);
				}
				System.out.println("Inserted default characteristics");
			}

			// close ResultSet object
			rs.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	// method to insert single variable data into table
	/**
	 * @param c    Connection object for database ops
	 * @param ps   String to create PreparedStatement for data insert
	 * @param name String data to insert
	 */
	void insert(Connection c, String ps, String name) {
		try {
			PreparedStatement pstmt = c.prepareStatement(ps);
			pstmt.setString(1, name);
			pstmt.execute();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	// method to insert single string data with type id into table
	/**
	 * @param c           Connection object for database ops
	 * @param ps          String to create PreparedStatement for data insert
	 * @param id          int type id to insert
	 * @param description String data to insert
	 */
	void insertWithID(Connection c, String ps, int id, String description) {
		try {
			PreparedStatement pstmt = c.prepareStatement(ps);
			pstmt.setInt(1, id);
			pstmt.setString(2, description);
			pstmt.execute();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}
}
