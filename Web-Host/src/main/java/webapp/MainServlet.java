package webapp;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/main.do")

public class MainServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		request.setAttribute("name",request.getParameter("name"));
		request.setAttribute("password",request.getParameter("password"));

		request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);

	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		request.setAttribute("name",request.getParameter("name"));
		request.setAttribute("password",request.getParameter("password"));
		
		int i = LoginServlet.empId;
		
		String book = request.getParameter("Book Name");
		String ret = request.getParameter("Return Book");
		
		
		
		if(book != null) {
			String memInfo = DatabaseConnector.getMemberInfo(i);
			request.setAttribute("info",memInfo);
			String own = DatabaseConnector.getOwnedBooks(i);
			request.setAttribute("owend",own);
			String result = DatabaseConnector.getSpecificBooks(book);
			request.setAttribute("set", result);
			request.getRequestDispatcher("/WEB-INF/views/MemberPage.jsp").forward(request, response);
		}else if(ret != null) {
			int a = Integer.parseInt(ret);
			String memInfo = DatabaseConnector.getMemberInfo(i);
			request.setAttribute("info",memInfo);
			DatabaseConnector.returnBook(a, i);
			String own = DatabaseConnector.getOwnedBooks(i);
			request.setAttribute("owend",own);
			String result = DatabaseConnector.getAllBooksInfo();
			request.setAttribute("set", result);
			request.getRequestDispatcher("/WEB-INF/views/MemberPage.jsp").forward(request, response);
		}else {

			String memInfo = DatabaseConnector.getMemberInfo(i);
			request.setAttribute("info",memInfo);
			String own = DatabaseConnector.getOwnedBooks(i);
			request.setAttribute("owend",own);
			String result = DatabaseConnector.getAllBooksInfo();
			request.setAttribute("set", result);
			request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
		}

	}
	protected void doLogOff(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);

	}
}