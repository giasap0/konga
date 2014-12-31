package it.konga.framework.interfaces;

import java.io.OutputStream;

/**
 * Interfaccia implementata da qualsiasi classe in grado di scrivere un file su uno stream
 * @author Giampaolo Saporito
 * @date 24.09.2014
 *
 */
public interface KFileWriter
{
	/**
	 * scrive il file sullo stream
	 * @param out output stream
	 */
	public void writeFile(OutputStream out) throws Exception;
}
