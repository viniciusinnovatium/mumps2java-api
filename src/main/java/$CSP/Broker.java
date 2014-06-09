package $CSP;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import br.com.innovatium.mumps2java.todo.REVIEW;
import br.com.innovatium.mumps2java.todo.TODO;
import mLibrary.*;

public class Broker extends mPage {

	private String _class;
	private String _method;
	
	private HttpServletRequest request ;
	private HttpServletResponse response ;
	
	@Override
	public void setContext(mContext context) {
		super.setContext(context);
		request = context.getRequest().getOriginalRequest();
		response = context.getResponse();
	}

	@Override
	public boolean OnPreHTTP() {
		// Make sure pageclass query parameter cannot be set in URL
		request.setAttribute("pageclass",null);
		String event = "";
		if ((event = request.getParameter("WJSEVENT"))!=null) {
			response.setContentType("text/html");
		} else if ((event = request.getParameter("WEVENT"))!=null) {
			response.setContentType("application/x-csp-hyperevent");
		}
		if (event.isEmpty()){
			throw new IllegalArgumentException("Bad broker request.");
		} else {
			getEvent(event);
			String pgcls=request.getParameter("pageclass");
			if (!pgcls.isEmpty()) {
				return mFncUtil.booleanConverter(m$.fnc$(pgcls.concat(".").concat("OnPreHyperEvent"),_class,_method));
			}
		}
		return true;
	}
	
	@REVIEW(description = "Descriptografia do parametro event")
	private void getEvent(String event) {
		 
		if (m$.getSession().isNewSession()){
			throw new IllegalArgumentException("You are logged out, and can no longer perform that action");
		}
		//Revisar a descriptografia aqui.
		//Set event=$listget(..Decrypt(event))
		if (event.isEmpty())
		{
			throw new IllegalArgumentException("Illegal Request");
		}
		String pageclass = "";
		int find = event.indexOf(":");
		if (find!=-1){
			pageclass=event.substring(find);
			event=event.substring(1, find-1);
		}
		
		_class = event.substring(0,event.lastIndexOf("."));
		if (_class.isEmpty()){
			throw new IllegalArgumentException("Class name required");
		}
		if (pageclass.isEmpty()){
			request.setAttribute("pageclass",_class);
		} else {
			request.setAttribute("pageclass",pageclass);
		}
		
		_method = event.substring(event.lastIndexOf(".")+1);
		if (_method.isEmpty()){
			throw new IllegalArgumentException("Method name required");
		}
		
	}

	@Override
	public void OnPostHTTP() {
		// TODO Auto-generated method stub
		super.OnPostHTTP();
	}

	@Override
	public Object OnPage() {
		
		if (request.getParameter("WEVENT")!=null){
			return event();
		} else if (request.getParameter("WJSEVENT")!=null){
			return jsEvent();
		} else {
			throw new IllegalArgumentException("Bad broker request.");
		}
		 
	}

	private Object jsEvent() {
		// TODO Auto-generated method stub
		return null;
	}

	@REVIEW(description = "CSPSessionCookie - correlato no java / tratamento de OnPreHyperEvent ")
	@TODO(description = "Tratamento WLIST")
	private Object event() {
		//Write %session.CSPSessionCookie,!
		//m$.Cmd.Write(m$.getSession().getOriginalSession().,"/n");
		m$.Cmd.Write("#R","\r\n");
		
		Object rs;
		//request.getParameter("WEVENT");
		int count = (request.getParameter("WARGC")==null) 
				? 0 
				: Integer.valueOf(request.getParameter("WARGC"));
		//Set ok=..XecuteStr($Get(%request.Data("WEVENT",1)),.code,cls,method,1)
		//		
				
		// get arguments for method; 'null' values become undefined
		String [] parameters = new String[count];
		for (int i=1; i<=count; i++) {
			String arg = request.getParameter("WARG_".concat(String.valueOf(i)));
			if (arg!=null) {
				parameters[i-1] = arg;
			} else if (request.getParameter("WLIST".concat(String.valueOf(i)))!=null){
				throw new IllegalArgumentException("parameter WLIST found, but not implemented.");
			}
		}
		//#; Output anything written in the OnPreHyperEvent code
		//For i=1:1:+$get(%output) Write %output(i)
		// invoke method
		rs = m$.fnc$(_class.concat(".").concat(_method),parameters);
		m$.Cmd.Write("\r\n","#OK","\r\n"); 
		m$.Cmd.Write( rs,"\r\n");
		return null;
	}
	
}
