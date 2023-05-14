package com.leafbodhi.nostr;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;

import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.DeploymentException;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.WebSocketContainer;

@ClientEndpoint
public class WebSocketClient {

    private Session session;
    private CountDownLatch latch;

    public WebSocketClient() {
        this.latch = new CountDownLatch(1);
    }

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        System.out.println("Connected to server");
        this.session = session;
        latch.countDown();
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        System.out.println("Received message: " + message);
    }

    public void sendMessage(String message) throws IOException, InterruptedException {
        latch.await(); // Wait until the connection is established
        this.session.getBasicRemote().sendText(message);
    }

    public void close() throws IOException {
        this.session.close();
    }

    public static void main(String[] args) throws DeploymentException {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();

        try {
            URI uri = new URI("ws://127.0.0.1:8080/");
        	
//        	URI uri = new URI("wss://relay.nostr.express/");
            WebSocketClient client = new WebSocketClient();
            container.connectToServer(client, uri);
            // Send messages
            String eventStr = """
["REQ","50ADF68A-3B0D-4C9C-BF91-D51CB4E8BBF5",{"kinds":[4],"limit":500,"#p":["45bd25c8648da487c573144f481db102ca23fd502e0b503ec90eb7ba451e327b"]},{"kinds":[4],"authors":["45bd25c8648da487c573144f481db102ca23fd502e0b503ec90eb7ba451e327b"]}]
""";
            client.sendMessage(eventStr);
            Thread.sleep(1000);
            // Close connection
            client.close();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
