package ar.com.magm.teaching.http;

/**
 * Comandos disponibles en el server
 * 
 * @author magm
 * @version 1.0
 */
public interface HttpServerListener {
	/**
	 * Evento que produce el server al recibir un request correcto.
	 * 
	 * @param request
	 *            El server envía el request procesado
	 * @param response
	 *            El server envía una instancia de un objeto respuesta que
	 *            permite al cliente procesarla
	 * @param serverCommands
	 *            Instancia de la implementación de los comandos del server
	 */
	public void request(HttpRequest request, HttpResponse response, ServerCommands serverCommands);
}
