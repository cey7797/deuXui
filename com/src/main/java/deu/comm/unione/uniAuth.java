package deu.comm.unione;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.nexacro.xapi.data.DataSet;

public class uniAuth {
	
	public uniAuth(){
		super();
	}
	
	public String checkAuth(HttpServletRequest request, HttpServletResponse response) throws Exception  {

		HttpSession session = request.getSession();
		//DataSet ds = new DataSet("resultList");
		DataSet ds = (DataSet) session.getAttribute("loginUser");
		try{
			//ds.loadXml((String) session.getAttribute("loginUser"));
			
			if(ds.hasData()){
				return "SUCESS";
			} else {
				return "FAIL";						
			}
		} catch (Exception e) {
			return "FAIL";
		}
	}
}
