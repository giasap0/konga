package it.kongaImplementation.interceptors;

import it.kongaImplementation.flusso.IStandardResults;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class KErrorInterceptor implements Interceptor
{
	private static final long serialVersionUID = -1190891967178052729L;

	@Override
	public void destroy() {
	}

	@Override
	public void init() {
		System.out.println("init error interceptor");
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception
	{
		String ret = null;
		try {
			ret = invocation.invoke();
		} catch (Exception e) {
			System.out.println("l'interceptor ha beccato un'eccezione");
			ret = IStandardResults.RESULT_ERROR;
		}

		return ret;
	}

}//EO KErrorInterceptor
