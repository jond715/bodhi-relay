package com.leafbodhi.nostr;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.Session;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ClientEndpoint
public class TestWebSocketServer {

	@LocalServerPort
	private String port;
 
	WebSocketClient client = new WebSocketClient();

	@Test
	public void contextLoads() throws Exception {
//		assertThat(server, notNullValue());
	}

	@Test
	void testEventRequest() throws Exception {
		String url = "ws://127.0.0.1:" + port + "/";
		System.out.println("port:" + port);
		URI uri = new URI(url); 
		Session session = ContainerProvider.getWebSocketContainer().connectToServer(client, uri);
		String eventStr = """
				["EVENT",{"created_at":1682351083,"sig":"c54a62e0f07530b96bffbb190d80f2fcb5df6478887b61bb828ed669d80d5ac8453ea3fdc76b819b5a52d1d9886cabddfe1d039f0db282727b2244bccbdcc02f","kind":20000,"id":"da717511c10d51ea807dec45c8bcca83a060958258bb81858f8842631dd2cc4d","content":"...","pubkey":"99cf4426cb4507688ff151a760ec098ff78af3cfcdcb6e74fa9c9ed76cba43fa","tags":[["p","99cf4426cb4507688ff151a760ec098ff78af3cfcdcb6e74fa9c9ed76cba43fa","nostr-java"]]}]
				""";
		session.getBasicRemote().sendText(eventStr);
		
	}

	@Test
	void testSubscribeRequest() throws Exception {
		//TODO
		String url = "ws://127.0.0.1:" + port + "/";
		URI uri = new URI(url); 
		Session session = ContainerProvider.getWebSocketContainer().connectToServer(getClass(), uri);
		String req = """
				["REQ","50ADF68A-3B0D-4C9C-BF91-D51CB4E8BBF5",{"kinds":[4],"limit":500,"#p":["45bd25c8648da487c573144f481db102ca23fd502e0b503ec90eb7ba451e327b"]},{"kinds":[4],"authors":["45bd25c8648da487c573144f481db102ca23fd502e0b503ec90eb7ba451e327b"]}]
				""";
		session.getBasicRemote().sendText(req);
	}

	@Test
	void testCloseRequest() {
		//TODO
	}

}
