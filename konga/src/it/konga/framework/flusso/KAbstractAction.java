package it.konga.framework.flusso;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Konga Abstract Action
 *  @author Giampaolo Saporito
 * @Date 05/09/2014
 */
public abstract class KAbstractAction extends ActionSupport implements ServletResponseAware, RequestAware  , SessionAware
{
	private static final long serialVersionUID = -1669304896799086714L;

	// ------------------------------- implementazioni per accedere a request e response ------------------------------- \\
	protected HttpServletResponse servletResponse;
	private Map<String,Object> request;
	private Map<String,Object> session;

	@Override
	public void setServletResponse(HttpServletResponse servletResponse) {
		this.servletResponse = servletResponse;
	}

	@Override
	public void setRequest(Map<String,Object> request){ 
		this.request = request;
	}
	@Override
	public void setSession(Map<String,Object> session){ 
		this.session = session;
	}

	protected HttpServletResponse getResponse()			{ return servletResponse;}
	protected Map<String,Object> getRequest()			{ return request;}
	protected Map<String, Object> getSession()			{ return session; }
	
}//EO KAbstractAction
