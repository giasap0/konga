package it.konga.framework.flusso;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class CustomInterceptor implements Interceptor
{
	private static final long serialVersionUID = 7970731294731136737L;

	@Override
	public void destroy() {
		System.out.println("MyCustomInterceptor: Inside destroy");
	}

	@Override
	public void init() {
		System.out.println("MyCustomInterceptor: Inside init");
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception
	{
		System.out.println("MyCustomInterceptor: Inside intercept - pre process");
		String ret = invocation.invoke();
		System.out.println("MyCustomInterceptor: Inside intercept - post process");
		return ret;
	}

}//EO CustomInterceptor
