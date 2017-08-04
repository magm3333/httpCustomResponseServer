package ar.com.magm.teaching.http;

/**
 * Comandos disponibles en el server
 * 
 * @author magm
 * @version 1.0
 */
public interface ServerCommands {
	/**
	 * Le comunica al server que puede serguir aceptando requerimientos
	 */
	public void continueListening();

	/**
	 * Le comunica al server qu eluego de procesar el requerimiento actual debe
	 * finalizar la ejecución
	 */
	public void shutdown();

}
