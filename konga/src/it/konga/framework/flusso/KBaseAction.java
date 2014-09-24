package it.konga.framework.flusso;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Konga Base Action
 *  @author Giampaolo Saporito
 * @Date 05/09/2014
 */
public abstract class KBaseAction extends ActionSupport implements ServletResponseAware, ServletRequestAware
{
	private static final long serialVersionUID = -1669304896799086714L;

	// ------------------------------- implementazioni per accedere a request e response ------------------------------- \\
	protected HttpServletResponse servletResponse;
	  @Override
	  public void setServletResponse(HttpServletResponse servletResponse) {
	    this.servletResponse = servletResponse;
	  }

	  protected HttpServletRequest servletRequest;
	  @Override
	  public void setServletRequest(HttpServletRequest servletRequest) {
	    this.servletRequest = servletRequest;
	  }
}
