package application;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import db.Database;

public class DatabaseManager {
	private Database dat;
	private Connection conn;
	private static String editName;

	// constructor/initializer for WriteIt Database
	public DatabaseManager() {
		dat = new Database("jdbc:sqlite:src\\db\\WriteIt.db");
		dat.createTables();
		conn = dat.connect();
	}

	/**
	 * getter for single string var from table with id
	 * 
	 * @param table name of target table
	 * @param col   name of target column
	 * @param id    int of row id
	 * @return String retrieved from table, col
	 */
	public String getSingleStringVarFromID(String table, String col, String idName, int id) {
		String rString; // string to return
		try {
			PreparedStatement pstmt = conn
					.prepareStatement("SELECT " + col + " FROM " + table + " WHERE " + idName + " = ? ");
			pstmt.setDouble(1, id);
			ResultSet rs = pstmt.executeQuery();
			rString = rs.getString(col);
			rs.close();
			return rString;
		}

		catch (SQLException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	/**
	 * getter for single int var from table with named id
	 * 
	 * @param table name of target table
	 * @param col   name of target column
	 * @param id    int of row id
	 * @return String retrieved from table, col
	 */
	public int getSingleIntVarFromID(String table, String col, String idName, int id) {
		int i; // string to return
		try {
			PreparedStatement pstmt = conn
					.prepareStatement("SELECT " + col + " FROM " + table + " WHERE " + idName + " = ? ");
			pstmt.setDouble(1, id);
			ResultSet rs = pstmt.executeQuery();
			i = rs.getInt(col);
			rs.close();
			return i;
		}

		catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * method to return id of target string
	 * 
	 * @param table target table
	 * @param col   target column in table
	 * @param p     seapassworrched string
	 * @return row id of string
	 */
	public int getIdfromStringVar(String table, String col, String p) {
		try {
			PreparedStatement pstmt = conn.prepareStatement("SELECT id FROM " + table + " WHERE " + col + " = ? ");
			pstmt.setString(1, p);
			ResultSet rs = pstmt.executeQuery();
			int r = rs.getInt("id");
			rs.close();
			return r;
		}

		catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * method to get list of string vars form column in table
	 * 
	 * @param table target table
	 * @param col   target column
	 * @return ArrayList of strings from table
	 */
	public List<String> getAllSingleStringVars(String table, String col) {
		List<String> rStringList = new ArrayList<String>();
		; // string to return
		try {
			PreparedStatement pstmt = conn.prepareStatement("SELECT " + col + " FROM " + table);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				rStringList.add(rs.getString(col));
			}
			rs.close();
			return rStringList;
		}

		catch (SQLException e) {
			e.printStackTrace();
			return rStringList;
		}
	}

	/**
	 * method to get list of string vars form column in table
	 * 
	 * @param table target table
	 * @param col   target column
	 * @return ArrayList of strings from table
	 */
	public List<String> getAllSingleStringVars(String table, String col, String type, int id) {
		List<String> rStringList = new ArrayList<>();
		; // string to return
		try {
			PreparedStatement pstmt = conn
					.prepareStatement("SELECT " + col + " FROM " + table + " WHERE " + type + "  = ? ");
			pstmt.setDouble(1, id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				rStringList.add(rs.getString(col));
			}
			rs.close();
			return rStringList;
		}

		catch (SQLException e) {
			e.printStackTrace();
			return rStringList;
		}
	}

	/**
	 * method to get list of integer vars form column in table
	 * 
	 * @param table target table
	 * @param col   target column
	 * @return ArrayList of strings from table
	 */
	public List<Integer> getAllSingleIntVars(String table, String col, String type, int id) {
		List<Integer> rList = new ArrayList<>();
		; // string to return
		try {
			PreparedStatement pstmt = conn
					.prepareStatement("SELECT " + col + " FROM " + table + " WHERE " + type + "  = ? ");
			pstmt.setDouble(1, id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				rList.add(rs.getInt(col));
			}
			rs.close();
			return rList;
		}

		catch (SQLException e) {
			e.printStackTrace();
			return rList;
		}
	}

	/**
	 * method to set password
	 * 
	 * @param p new password
	 */
	public void setPassword(String p) {
		try {
			String sql = "UPDATE password SET key = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, p);
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * method to add values to table with single data column
	 * 
	 * @param table target table
	 * @param col   target column
	 * @param p     Object to insert
	 */
	public void addSingleVar(String table, String col, Object p) {
		List<Object> rList = new ArrayList<>(); // ArrayList to hold current db entries in column col
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT " + col + " FROM " + table);
			// collect results of query into ArrayList
			while (rs.next()) {
				rList.add(rs.getObject(col));
			}
			// add if not duplicate
			if (!rList.contains(p)) {
				String sql = "INSERT INTO " + table + "(" + col + ") VALUES ('" + p + "')";
				stmt.executeUpdate(sql);
			}
		}

		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * method to add values to table with one data column, one type id
	 * 
	 * @param table target table
	 * @param col   target column
	 * @param p     String to insert
	 */
	public void addSingleStringVarWithId(String table, String col, String p, String idName, int id) {
		List<String> rList = new ArrayList<>(); // ArrayList to hold current db entries in column col
		try {
			// collect all matching vars
			rList = this.getAllSingleStringVars(table, col, idName, id);
			// add if not duplicate
			if (!rList.contains(p)) {
				String sql = "INSERT INTO " + table + "('" + col + "', type) VALUES (?, ?)";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, p);
				pstmt.setInt(2, id);
				pstmt.execute();

			}
		}

		catch (SQLException e) {
			System.out.println(e.getMessage());
			// e.printStackTrace();
		}
	}

	/**
	 * method to set value of target table column
	 * 
	 * @param table target table
	 * @param col   target column
	 * @param p     String to update
	 */
	public void setSingleStringVar(String table, String col, String p) {
		try {
			String sql = "UPDATE " + table + " SET " + col + " = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, p);
			pstmt.execute();
		}

		catch (SQLException e) {
			System.out.println(e.getMessage());
			// e.printStackTrace();
		}
	}

	// method to calculate hashcode from first + last name
	public int getNameHash(String first, String last) {
		return (first + last).hashCode();
	}

	/**
	 * method to add new recommendation
	 * 
	 * @param firstName
	 * @param lastName
	 * @param gender
	 * @param schoolName
	 * @param selectedDate
	 * @param program
	 * @param semester
	 * @param year
	 * @param courses
	 * @param grades
	 * @param personalChars
	 * @param academicChars
	 */
	public void addRecommendation(String firstName, String lastName, String gender, String schoolName,
			LocalDate selectedDate, String program, String semester, String year, List<String> courses,
			Map<String, String> grades, List<String> personalChars, List<String> academicChars) {
		// insert recommendation data to table
		String rec = "INSERT INTO recommendations(hash, firstName, lastName, gender, schoolName, selectedDate,"
				+ " program, semester, year) VALUES(?,?,?,?,?,?,?,?,?)\n"; // string for student data insertion
		String grade = "INSERT INTO grades (studentID, courseID, grade) VALUES (?,?,?);"; // string for grade data
																							// insertion
		String chars = "INSERT INTO studentChars(studentID, characteristicID) VALUES (?,?);";
		try {
			// insert student data
			PreparedStatement pstmt = conn.prepareStatement(rec);
			pstmt.setInt(1, this.getNameHash(firstName, lastName));
			pstmt.setString(2, firstName);
			pstmt.setString(3, lastName);
			pstmt.setString(4, gender);
			pstmt.setString(5, schoolName);
			pstmt.setDate(6, Date.valueOf(selectedDate));
			pstmt.setString(7, program);
			pstmt.setString(8, semester);
			pstmt.setString(9, year);
			pstmt.execute();

			// insert grade data
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT courseName from courses"); // collect course names from db
			List<String> courseList = new ArrayList<>();
			while (rs.next()) {
				courseList.add(rs.getString("courseName")); // add course names to List
			}
			pstmt = conn.prepareStatement(grade);
			for (String course : courses) {
				// if course is not included in table for student OR course included but with
				// different grade
				if (!this.isDuplicateForStudent("grades", "courseID", this.getNameHash(firstName, lastName),
						courseList.indexOf(course) + 1)
						|| (this.isDuplicateForStudent("grades", "courseID", this.getNameHash(firstName, lastName),
								courseList.indexOf(course) + 1)
								&& this.getSingleStringVarFromID("grades", "grade", "courseID",
										courseList.indexOf(course)) != grades.get(course))) {
					if (this.isDuplicateForStudent("grades", "grade", this.getNameHash(firstName, lastName),
							grades.get(course))) { // if grade needs to be updated

					}
					pstmt.setInt(1, this.getNameHash(firstName, lastName)); // student id (hash)
					pstmt.setInt(2, courseList.indexOf(course) + 1); // course id (arrayList location + 1, since SQLite
																		// numbers beginning at 1)
					pstmt.setString(3, grades.get(course)); // course grade from HashMap
					pstmt.execute(); // upload to db
				}
			}

			// insert characteristics data
			rs = stmt.executeQuery("SELECT description from characteristics"); // collect characteristics from db
			List<String> charList = new ArrayList<>();
			while (rs.next()) {
				charList.add(rs.getString("description")); // add characteristics to List
			}

			pstmt = conn.prepareStatement(chars);
			for (String personal : personalChars) {
				if (!this.isDuplicateForStudent("studentChars", "characteristicID",
						this.getNameHash(firstName, lastName), charList.indexOf(personal) + 1)) {
					pstmt.setInt(1, this.getNameHash(firstName, lastName)); // student ID
					pstmt.setInt(2, charList.indexOf(personal) + 1); // characteristic id
					pstmt.execute();
				}
			}

			for (String academic : academicChars) {
				if (!this.isDuplicateForStudent("studentChars", "characteristicID",
						this.getNameHash(firstName, lastName), charList.indexOf(academic) + 1)) {
					pstmt.setInt(1, this.getNameHash(firstName, lastName)); // student ID
					pstmt.setInt(2, charList.indexOf(academic) + 1); // characteristic id
					pstmt.execute();
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// method to return last inserted id from target table
	public int getLastInsertID(String table) {
		try {
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(id) AS max_id FROM " + table);
			ResultSet rs = pstmt.executeQuery();
			return rs.getInt("max_id");
		} catch (SQLException e) {
			e.getMessage();
			return -1;
		}

	}

	/**
	 * method to return target data from recommendation id
	 * 
	 * @param id
	 * @param dataID
	 * @param dataTable
	 * @param text
	 * @param sourceTable
	 * @return
	 */
	public List<String> getDataFromStudent(int id, String dataID, String dataTable, String text, String sourceTable) {
		List<String> rList = new ArrayList<>();
		// retrieve student id hash from recommendations table
		int h = this.getSingleIntVarFromID("recommendations", "hash", "id", id);

//			// retrieve student's data id's from  table
		List<Integer> ids = this.getAllSingleIntVars(dataTable, dataID, "studentID", h);
//			
		// retrieve names, add to list for return
		for (int i : ids) {
			rList.addAll(this.getAllSingleStringVars(sourceTable, text, "id", i));
		}
		return rList;
	}

	/**
	 * method to return target data (with type id) from recommendation id
	 * 
	 * @param id
	 * @param dataID
	 * @param dataTable
	 * @param text
	 * @param sourceTable
	 * @param int         type
	 * @return
	 */
	public List<String> getDataFromStudent(int id, String dataID, String dataTable, String text, String sourceTable,
			int type) {
		List<String> rList = new ArrayList<>();
		try {
			// retrieve student id hash from recommendations table
			int h = this.getSingleIntVarFromID("recommendations", "hash", "id", id);

			// retrieve student's data id's from table
			List<Integer> ids = this.getAllSingleIntVars(dataTable, dataID, "studentID", h);
			// retrieve names, add to list for return
			for (int i : ids) {
				PreparedStatement pstmt = conn.prepareStatement(
						"SELECT " + text + " FROM " + sourceTable + " WHERE type IS " + type + " AND id IS " + i);
				ResultSet rs = pstmt.executeQuery();
				String s = rs.getString(text);
				if (!(s == null)) {
					rList.add(s);
				}
			}

			return rList;
		} catch (SQLException e) {
			e.getMessage();
			return rList;
		}
	}

	/**
	 * method to delete recommendation from database (from last name)
	 * 
	 * @param lastName
	 */
	void deleteRecommendation(String lastName) {
		// get student hashcode from lastName
		int hash = this.getHashFromLast(lastName);
		// delete student data
		try {
			Statement stmt = conn.createStatement();

			String deleteRec = "DELETE FROM recommendations WHERE hash = " + hash; // delete from recommendations based
																					// on hash
			String deleteGrades = "DELETE FROM grades WHERE studentID = " + hash; // delete from grades based on hash
			String deleteChars = "DELETE FROM studentChars WHERE studentID = " + hash; // delete from studentChars based
																						// on hash
			stmt.execute(deleteRec);
			stmt.execute(deleteGrades);
			stmt.execute(deleteChars);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// method to delete string from table
	void deleteData(String table, String column, String data) {
		try {
			Statement stmt = conn.createStatement();
			String delete = "DELETE FROM " + table + " WHERE " + column + " = '" + data + "'";
			stmt.execute(delete);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// method to delete all data from table
	void deleteAll(String table) {
		try {
			Statement stmt = conn.createStatement();
			String delete = "DELETE FROM " + table;
			stmt.execute(delete);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// setter for private static String editName
	void setNameToEdit(String l) {
		editName = l;
	}

	// getter for private static String editName
	String getNameToEdit() {
		return editName;
	}

	// method to return student id hash from last name
	int getHashFromLast(String lastName) {
		return this.getSingleIntVarFromID("recommendations", "hash", "id",
				this.getIdfromStringVar("recommendations", "lastName", lastName));
	}

	// method to get date from student id hash
	LocalDate getDate(int id) {
		LocalDate rDate = LocalDate.now();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(("SELECT selectedDate FROM recommendations WHERE hash = " + id));
			rDate = rs.getDate("selectedDate").toLocalDate();
			rs.close();
			return rDate;
		}

		catch (SQLException e) {
			e.printStackTrace();
			return rDate;
		}
	}

	// method to check if data id is already included in db for student
	boolean isDuplicateForStudent(String table, String column, int hash, Object id) {
		String sql = "SELECT " + column + " FROM " + table + " WHERE studentID is " + hash;
		List<Object> dataList = new ArrayList<>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql); // get data matching student hash
			while (rs.next()) {
				dataList.add(rs.getObject(column));
			}
			if (dataList.contains(id)) {
				return true;
			} else
				return false;
		} catch (SQLException e) {
			e.getMessage();
			return false;
		}
	}

	// method to close database connection
	public void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				System.err.println("Error closing database connection: " + e.getMessage());
			}
		}
	}
}
