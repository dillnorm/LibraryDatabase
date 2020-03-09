package webapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class sqlmenuFinal {

	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);
		int SID = 0;
		String fname;
		String lname;
		boolean condition = true;
		boolean log = false;
		
			try{  
				while(condition == true){	
				Class.forName("com.mysql.cj.jdbc.Driver");  
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/universitydb","root","toor"); 

				while(log == false) {
					System.out.println("Enter your Student ID: (Example is 666666606)");
					SID = input.nextInt();
					Statement login = con.createStatement();
					ResultSet rs = login.executeQuery("SELECT FName, LName FROM student WHERE SID = '"+SID+"';");
					while (rs.next()) {
						fname = rs.getString("FName");
						lname = rs.getString("LName");
						if(fname != null) {
							System.out.println("Welcome "+ fname+" "+lname);
							log = true;
						}
						else {
							System.out.println("Incorrect Login ID");
						}
					
					
					}

				}
				
				System.out.println("Main Menu: \n (1) Add a class \n (2) Drop a class \n (3) See My Schedule \n (4) Exit");
				System.out.println("Enter your choice:");
				int x = input.nextInt();
			
				switch(x)
				{
			   		case 1 :
				   		System.out.println("Available Courses:");
				   		Statement avail = con.createStatement();
				   		ResultSet res = avail.executeQuery("SELECT * FROM section WHERE SYear = '2007';");
				   		while (res.next()) {
							int id = res.getInt("SecID");
							String name = res.getString("Cname");
							int no = res.getInt("SecNo");
							String sem = res.getString("Sem");
							int SYear = res.getInt("SYear");
							int Days = res.getInt("DaysTime");
							int Bldg = res.getInt("Bldg");
							int Room = res.getInt("Room");
							int RoomNo = res.getInt("RoomNo");
							int Courseid = res.getInt("Courseid");
							System.out.println(id +"|"+name+"|"+no+"|"+sem+"|"+SYear+"|"+Days+"|"+Bldg+"|"+Room+"|"+RoomNo+"|"+Courseid);
				   		}
				   		String course;
				   			int section;
				   			System.out.println("Enter the Section Number to add: ");
				   		section = input.nextInt();
				       
				   		String query = "INSERT INTO takes VALUES ('"+SID+"','"+section+"','-');";
		                   
				   		Statement stmt=con.createStatement(); 
				   		stmt.executeUpdate(query); 
				   
				   		String searchquery = "SELECT SecID,CName FROM section WHERE SecNo = '"+section+"';";
				   		Statement stmt2 = con.createStatement(); 
				   		ResultSet rs2 = stmt.executeQuery(searchquery);
				   		while (res.next()) {
							int id = res.getInt("SecID");
							String name = res.getString("Cname");
							System.out.println("Added Class Section: "+id+", "+ name +", "+section+" to your schedule");
				   		}
				   		break;
			   		case 2 :
				   		System.out.println("Current Schedule:");
				   		Statement curr = con.createStatement();
				   		ResultSet currres = curr.executeQuery("SELECT * FROM takes t LEFT JOIN section s ON s.SecId = t.SectionID WHERE StudentId = '"+SID+"';");
				   		while (currres.next()) {
							int id = currres.getInt("SecID");
							String name = currres.getString("Cname");
							int no = currres.getInt("SecNo");
							String sem = currres.getString("Sem");
							int SYear = currres.getInt("SYear");
							int Days = currres.getInt("DaysTime");
							int Bldg = currres.getInt("Bldg");
							int Room = currres.getInt("Room");
							int RoomNo = currres.getInt("RoomNo");
							int Courseid = currres.getInt("Courseid");
							System.out.println(id +"|"+name+"|"+no+"|"+sem+"|"+SYear+"|"+Days+"|"+Bldg+"|"+Room+"|"+RoomNo+"|"+Courseid);
				   		}
				   		System.out.println("Enter the Section Number to drop: ");
				   		section = input.nextInt();
				   		String dq = "DELETE FROM takes WHERE StudentId = '"+SID+"' AND SectionID ='"+section+"';";
				   		Statement dst= con.createStatement(); 
				   		dst.executeUpdate(dq); 
				   		System.out.println("Class Dropped From Schedule");
						break;
			   		case 3 :
				   		System.out.println("Current Schedule:");
				   		Statement d = con.createStatement();
				   		ResultSet dres = d.executeQuery("SELECT * FROM takes t LEFT JOIN section s ON s.SecId = t.SectionID WHERE StudentId = '"+SID+"';");
				   		while (dres.next()) {
							int id = dres.getInt("SecID");
							String name = dres.getString("Cname");
							int no = dres.getInt("SecNo");
							String sem = dres.getString("Sem");
							int SYear = dres.getInt("SYear");
							int Days = dres.getInt("DaysTime");
							int Bldg = dres.getInt("Bldg");
							int Room = dres.getInt("Room");
							int RoomNo = dres.getInt("RoomNo");
							int Courseid = dres.getInt("Courseid");
							System.out.println(id +"|"+name+"|"+no+"|"+sem+"|"+SYear+"|"+Days+"|"+Bldg+"|"+Room+"|"+RoomNo+"|"+Courseid);
				   		}
				   		break;
			   		case 4 :
				   		condition = false;
				   		System.out.println("Logging Out.");
				   		con.close();
				   		break;
				}
			

			}
		}
		catch(Exception e){ 
			System.out.println(e);
		} 
	}
	public static boolean getUser(String user, String password) {
		boolean log = false;
		try{  
			String fname;
			String lname;
			System.out.println("Testing credentials");
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/universitydb","root","toor"); 
			Statement login = con.createStatement();
			ResultSet rs = login.executeQuery("SELECT FName, LName FROM student WHERE SID = '"+password+"';");
			System.out.println("Testing "+ password);
			while (rs.next()) {
				fname = rs.getString("FName");
				lname = rs.getString("LName");
				if(fname != null) {
					System.out.println("Welcome "+ fname+" "+lname);
					log = true;
					System.out.println("Credentials Worked");
				}

			}
			con.close();
		}
		catch(Exception e){ 
			System.out.println(e);
		}
		
		return log; 

	}
}
