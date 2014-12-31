package it.konga.framework.interfaces;

import it.konga.framework.interfaces.states.IState;

/** Un soggetto che gestisce uno stato di tipo S al quale possono registrarsi vari osservatori */
public interface ISubject<S extends IState>
{
	/** registra un nuovo osservatore dello stato S */
	public void registerObserver(IObserver<S> o);
	/** rimuovi l'osservatore, se esiste */
	public boolean removeObserver(IObserver<S> o);
	/** notifica il nuovo stato S a tutti gli osservatori */
	public void notifyObservers();
}
