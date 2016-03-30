package com.iveely.framework.net.websocket;

import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

/**
 *
 * @author liufanping@iveely.com
 */
@WebSocket
public class WSHandler extends WebSocketHandler {

	/**
	 * Current connector.
	 */
	private RemoteEndpoint connector;

	/**
	 * Event processor.
	 */
	private static SocketServer.IHandler eventProcessor;

	public static void setProcessor(SocketServer.IHandler processor) {
		eventProcessor = processor;
	}

	@OnWebSocketClose
	public void onClose(int statusCode, String reason) {
		eventProcessor.close(connector.hashCode());
	}

	@OnWebSocketError
	public void onError(Throwable t) {
		eventProcessor.close(connector.hashCode());
	}

	@OnWebSocketConnect
	public void onConnect(Session session) {
		connector = session.getRemote();
	}

	@OnWebSocketMessage
	public void onMessage(String message) {
		if (eventProcessor != null) {
			// Using asynchronous
			eventProcessor.invoke(connector.hashCode(), this, message);
		}
	}

	/**
	 * Asynchronous callback.
	 * 
	 * @param msg
	 *            the information send to the client.
	 */
	public void send(String msg) {
		try {
			if (connector != null) {
				connector.sendString(msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void configure(WebSocketServletFactory factory) {
		// TODO Auto-generated method stub
		factory.register(WSHandler.class);
	}
}
