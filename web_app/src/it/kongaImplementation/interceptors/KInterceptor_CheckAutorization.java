package it.kongaImplementation.interceptors;

import it.konga.framework.flusso.KAbstractAction;
import it.konga.framework.kObjects.KAbstract_User;
import it.kongaImplementation.flusso.IStandardResults;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/** Interceptor che si preoccupa di controllare se esiste l'utente in sessione */ 
public class KInterceptor_CheckAutorization  implements Interceptor
{
	private static final long serialVersionUID = -4209884179941225475L;

	@Override
	public void destroy() {
	}

	@Override
	public void init() {
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception
	{
		KAbstract_User user = (KAbstract_User) ((KAbstractAction)invocation).getSession().get(KAbstract_User.getSessionID());
		String ret = IStandardResults.RESULT_FORBIDDEN;
		if( user != null )
		{
			ret = invocation.invoke();
		}
		return ret;
	}

}//EO KInterceptor_CheckAutorization
