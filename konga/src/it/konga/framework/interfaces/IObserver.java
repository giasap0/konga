package it.konga.framework.interfaces;

import it.konga.framework.interfaces.states.IState;

/** Un osservatore  al quale viene notificato il cambiamento di uno stato di tipo S */
public interface IObserver<S extends IState>
{
	public void update(S newState);
}
