package webapp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class DatabaseConnector {

		static int id, idcheck = 0, BookIdCheck;
		static String fName = null, lName = null, BookName, Bookname, id2;
		static boolean idTry = true;
		static int MemberChose, MorE, EmemberChose;
		static String Bookid;
		static boolean Eid = true;
		static Scanner input = new Scanner(System.in);
		
	public static void main(String[] args) {
	try{  
		
		//Class.forName("com.mysql.jdbc.Driver");  
		Class.forName("com.mysql.cj.jdbc.Driver");  
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","toor"); 
		Statement stmt=con.createStatement(); 
		System.out.println("Connection Sucessful");
		//this is to find the MemeberId as a result set
		System.out.println("Are you a Member or Employee?");
		System.out.println("(1) member");
		System.out.println("(2) Employee");
		MorE = input.nextInt();
		//this is to see who is a member or not
		if(MorE == 1) {
		System.out.println("Library Id:");
		id = input.nextInt();
		ResultSet MId = stmt.executeQuery("SELECT Member_Id FROM LibMember WHERE Member_ID = '"+ id+"'");
		while(MId.next()) {
			idcheck = Integer.valueOf(MId.getInt(1));
		}
		if(id == idcheck) {
		ResultSet Name = stmt.executeQuery("select Member_Fname, Member_Lname from LibMember where Member_ID = '"+ id+"'");
		while(Name.next()) {
		fName = Name.getString("Member_Fname");
		lName = Name.getString("Member_Lname");
		}
			System.out.println("Welcome "+fName+" "+lName);
		}else {System.out.println("Wrong ID");
		System.exit(0);}
		Menu();
		while(MemberChose != 4) {
		if(MemberChose == 1) {
		System.out.println("what the name of the book: ");
		//this will find the book by the name and find the dewDecimalnum of the book
		BookName = input.nextLine();
		ResultSet Book = stmt.executeQuery("select DewDecimalNum, NumberAvailable from Book where BookName like '"+ BookName+"'");
		while(Book.next()) {
			BookIdCheck = Integer.valueOf(Book.getString(1));
			System.out.println("Book Available:"+Book.getString(2));
		}
		//this will insert into the membertoBook
		ResultSet insert = stmt.executeQuery("insert into MemberToBook(MemberId, BookId) values("+ id+", "+BookIdCheck +")");
		System.out.println("Book Added to "+fName+" "+lName);
		Menu();
		}//this one for returning a book by deleting it from the membertobook
		else if(MemberChose == 2) {
			int countbook = 0;
			ResultSet findbookid = stmt.executeQuery("select BookId from MemberToBook where MemberId == "+id);
			while(findbookid.next()) {
						System.out.println(findbookid.getString(countbook));
						countbook++;
			}
			System.out.println("What is the bookId?");
			Bookid = input.nextLine();
				ResultSet deleteId = stmt.executeQuery("select BookId from MemberToBook where BookId == "+Bookid);
			if(Bookid.equalsIgnoreCase(deleteId.getString(1))) {
				ResultSet delete = stmt.executeQuery("Delete FROM MemberToBook WHERE BookId == "+Bookid);
			}else {
				System.out.println("no book found");
			}
			Menu();
		}
		else if(MemberChose == 3) {
			ResultSet MemName = stmt.executeQuery("select Member_Fname, Member_Lname, Address, MState, MCity from LibMember where Member_ID == "+ id);
			while(MemName.next()) {
				System.out.println("Name: "+MemName.getString(1)+" "+MemName.getString(2)+"\n");
				System.out.println("ID: "+ id);
				System.out.println("Address: " + MemName.getString(3)+", "+MemName.getString(4)+", "+MemName.getString(5)+"\n");
			}
			Menu();
		}
		}
		//this is the member secition
		}else if(MorE == 2) {
			System.out.println("EmployeeLibrary Id:");
			id = input.nextInt();
			ResultSet EId = stmt.executeQuery("select e.Employee_SSN from Employee e where e.Employee_SSN =="+id);
			if(EId.getString(1).equalsIgnoreCase(id2)) {
				System.out.println("welcome Employee");
			}else {
				System.out.println("wrong ssn byee"); 
				Eid = false;
				System.exit(0);
			}
		}
		while(EmemberChose !=3) {
			if(EmemberChose == 1) {
				System.out.println("what is the book Name to order?");
				Bookname = input.nextLine();
				ResultSet Order = stmt.executeQuery("insert into OrderList(BookName) values("+BookName+");");
				Menu2();
			}else if(EmemberChose==2) {
				ResultSet name = stmt.executeQuery("select Employee_Fname, Employee_Lname, Job, LibraryId from Employee where Employee_SSN == "+id);
				ResultSet location = stmt.executeQuery("select LibraryName, City, State from LibraryLocation where LibraryIdLocation == "+name.getString(4));
				while(name.next()) {
					System.out.println("Name: "+name.getString(1)+" "+ name.getString(2)+"\n");
					System.out.println("Job: "+ name.getString(3)+"\n");
					System.out.println("location: "+ location.getString(1)+" ,"+location.getString(2)+ ", " + location.getString(3));
				}
				Menu2();
			}
		}
	}//this will catch the error if it doesnt connect
	catch(Exception e){System.out.println(e);} 
	
}
	public static void Menu() {
		System.out.println("1 Book Search");
		System.out.println("2 Return Book");
		System.out.println("3 Member Information");
		System.out.println("4 exit");
		MemberChose = input.nextInt();
	}
	public static void Menu2() {
		System.out.println("1 Orderlist");
		System.out.println("2 Employee information");
		System.out.println("3 exit");
		EmemberChose = input.nextInt();
	}
	
	// Member methods
	public static boolean validateMember(int password){
		boolean check = false;
		int ID = password;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","toor"); 
			Statement stmt=con.createStatement(); 
			System.out.println("Connection Sucessful checking member id");
			//Search database for match
			ResultSet MId = stmt.executeQuery("select Member_ID from LibMember  where Member_ID = '"+ID+"';");
			while(MId.next()) {
				idcheck = Integer.valueOf(MId.getInt(1));
			}
			if(password == idcheck) {
				ResultSet Name = stmt.executeQuery("select Member_Fname, Member_Lname from LibMember where Member_ID = '"+password+"';");
				while(Name.next()) {
					fName = Name.getString("Member_Fname");
					lName = Name.getString("Member_Lname");
					check = true;
				}
				System.out.println("Welcome "+fName+" "+lName);
			}else{
				System.out.println("Wrong ID");
				check = false;
			}
			//Return true if match
			con.close();
		}//this will catch the error if it doesnt connect
		catch(Exception e){System.out.println(e);} 
		
		return check;
	}
	//Displays the Specifically searched books
	public static String getSpecificBooks(String bName) {
		String result ="";
		result +=  "BookName"+ "|";
		result += "NumOfBookOrdered"+ "|";
		result += "AuthorFirstName"+ "|";
		result += "AuthorLastName"+ "|";
		result += "DateOfPublication"+ "|";
		result += "DewDecimalNum"+ "|";
		result += "NumberAvailable"+ "|";
		result += "Number_in_Total"+ "|";
		result += "LibraryId"+ "|";
		result +="\n";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","toor"); 
			Statement avail = con.createStatement();
			ResultSet res = avail.executeQuery("SELECT * FROM Book WHERE BookName LIKE '%"+bName+"%';");
			while (res.next()) {
				result +=  res.getString("BookName")+ "|";
				result += res.getInt("NumOfBookOrdered")+ "|";
				result += res.getString("AuthorFirstName")+ "|";
				result += res.getString("AuthorLastName")+ "|";
				result += res.getString("DateOfPublication")+ "|";
				result += res.getInt("DewDecimalNum")+ "|";
				result += res.getInt("NumberAvailable")+ "|";
				result += res.getInt("Number_in_Total")+ "|";
				result += res.getInt("LibraryId")+ "|";
				result +="\n";

			}
			con.close();
			}catch(Exception e){System.out.println(e);}
		return result;
	}
	public static String getOwnedBooks(int i) {
		String result = "";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","toor"); 
			Statement stmt=con.createStatement();
			ResultSet res = stmt.executeQuery("SELECT BookId FROM MemberToBook WHERE MemberId ='"+i+"';");
			while(res.next()) {
				result+= "\r\n";
				result+=res.getInt("BookId");
			}
		}catch(Exception e){System.out.println(e);} 
		return result;
	}
	public static void returnBook(int bookId, int memId) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","toor"); 
			Statement stmt=con.createStatement();
			ResultSet delete = stmt.executeQuery("Delete FROM MemberToBook WHERE BookId = '"+bookId+"' AND MemberId ='"+memId+"';");

		}catch(Exception e){System.out.println(e);} 

	}
	public static String getMemberInfo(int id) {
		String result = "\r\n";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","toor"); 
			Statement stmt=con.createStatement(); 
			ResultSet MemName = stmt.executeQuery("select Member_Fname, Member_Lname, Address, MState, MCity from LibMember where Member_ID = '"+ id+"';");
			while(MemName.next()) {
				result+= "\r\n";
				result+=("Name: "+MemName.getString(1)+" "+MemName.getString(2));
				result+= "\r\n";
				result+=("ID: "+ id);
				result+= "\r\n";
				result+=("Address: " + MemName.getString(3)+", "+MemName.getString(4)+", "+MemName.getString(5));
			}
			con.close();
		}//this will catch the error if it doesnt connect
		catch(Exception e){System.out.println(e);} 
		return result;
	}
	
	// Employee methods------------------------------------------------------------------------------
	public static boolean validateEmployee(String ssn){
		boolean check = false;
		String SSN = ssn;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","toor"); 
			Statement stmt=con.createStatement(); 
			System.out.println("Connection Sucessful");
		//Search database for match

			ResultSet EId = stmt.executeQuery("select Employee_SSN from Employee where Employee_SSN LIKE'"+SSN+"';");
			String res = "";
			if(EId.next()) {
			res = EId.getString("Employee_SSN");
			System.out.println("SSN Found:"+res);
			}
			if(SSN.equals(res)) {
				System.out.println("welcome Employee");
				check = true;
			}else {
				System.out.println("wrong ssn byee"); 
				check = false;
			}
			//Return true if match
			con.close();
		}catch(Exception e){System.out.println(e);} 
		return check;
	}
	//=============================================================================================
	public static String getOrderList() {
		String result = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","toor"); 
			Statement stmt=con.createStatement(); 
			
			System.out.println("Connection Sucessful, Getting orders");
			ResultSet Order = stmt.executeQuery("SELECT BookName FROM OrderList;");
			while(Order.next()) {
				result+=(Order.getString("BookName"));
				result+="\n";
			}
			con.close();
		}catch(Exception e){System.out.println(e);} 
		return result;
	}
	public static void setOrder(String name) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","toor"); 
			Statement stmt=con.createStatement(); 
			
			System.out.println("Connection Sucessful, getting orders");
			String query = ("insert into OrderList values('"+name+"');");
			stmt.executeUpdate(query);
			con.close();
		}catch(Exception e){System.out.println(e);} 

	}
	public static String getEmployeeInfo(String ssn) {

		String result = "\n";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","toor"); 
			Statement stmt=con.createStatement(); 
			System.out.println("Connection Sucessful");
			ResultSet name = stmt.executeQuery("select Employee_Fname, Employee_Lname, Job, LibraryId from Employee where Employee_SSN = '"+ssn+"';");
			ResultSet location = stmt.executeQuery("select LibraryName, City, State from LibraryLocation where LibraryIdLocation = '"+name.getString(4));
			while(name.next()) {
				result+=("Name: "+name.getString(1)+" "+ name.getString(2)+"\n");
				result+=("Job: "+ name.getString(3)+"\n");
				result+=("location: "+ location.getString(1)+" ,"+location.getString(2)+ ", " + location.getString(3));
			}
			con.close();
		}catch(Exception e){System.out.println(e);} 
		return result;
	}
	public static String getAllBooksInfo() {
		String result ="";
		result +=  "BookName"+ "|";
		result += "NumOfBookOrdered"+ "|";
		result += "AuthorFirstName"+ "|";
		result += "AuthorLastName"+ "|";
		result += "DateOfPublication"+ "|";
		result += "DewDecimalNum"+ "|";
		result += "NumberAvailable"+ "|";
		result += "Number_in_Total"+ "|";
		result += "LibraryId"+ "|";
		result +="\n";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","toor"); 
			Statement avail = con.createStatement();
			ResultSet res = avail.executeQuery("SELECT * FROM Book;");
			while (res.next()) {
				result +=  res.getString("BookName")+ "|";
				result += res.getInt("NumOfBookOrdered")+ "|";
				result += res.getString("AuthorFirstName")+ "|";
				result += res.getString("AuthorLastName")+ "|";
				result += res.getString("DateOfPublication")+ "|";
				result += res.getInt("DewDecimalNum")+ "|";
				result += res.getInt("NumberAvailable")+ "|";
				result += res.getInt("Number_in_Total")+ "|";
				result += res.getInt("LibraryId")+ "|";
				result +="\n";

			}
			con.close();
			}catch(Exception e){System.out.println(e);} 

		return result;
	}
	
}