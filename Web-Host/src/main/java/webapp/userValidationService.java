package webapp;

public class userValidationService {

	public boolean isUserValid(String user, String password) {
		System.out.println("Testing part 1");
		if(sqlmenuFinal.getUser(user,password) == true) {
			return true;
		}
		return false;
	}
	public static String EmpID;
	
	public static String MemID;
	
	public static void setEmpID(){
		
	}
	public static int setEmpID(){
		
	}
}
