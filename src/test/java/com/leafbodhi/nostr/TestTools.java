package com.leafbodhi.nostr;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.io.JsonStringEncoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leafbodhi.nostr.crypto.Bech32;
import com.leafbodhi.nostr.crypto.Schnorr;
import com.leafbodhi.nostr.entity.PublishEventIn;
import com.leafbodhi.nostr.entity.Tag;
import com.leafbodhi.nostr.utils.Bech32Prefix;
import com.leafbodhi.nostr.utils.NostrUtil;

public class TestTools {
	
	public static void main(String[] args) {
//		GenerateKey.genPriKeyAndPubkey();

//		testContentEscape();
//		testTagTojson();

		System.out.println(UUID.randomUUID());
		
	}
	
	public static void genPriKeyAndPubkey() {

		try {
			byte[] priKeyByte = Schnorr.generatePrivateKey();
			String priKeyHex = NostrUtil.bytesToHex(priKeyByte);
			String priKeyBech32 = Bech32.toBech32(Bech32Prefix.NSEC.getCode(), priKeyHex);

			System.out.println("priKeyHex:" + priKeyHex);
			System.out.println("priKeyBech32:" + priKeyBech32);

			byte[] pubKeyByte = Schnorr.genPubKey(priKeyByte);
			String pubKeyHex = NostrUtil.bytesToHex(pubKeyByte);
			String pubKeyBech32 = Bech32.toBech32(Bech32Prefix.NPUB.getCode(), pubKeyHex);
			System.out.println("pubKeyHex:" + pubKeyHex);
			System.out.println("pubKeyBech32:" + pubKeyBech32);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public static void testContentEscape() {
		String jsonStr = """
				{"name":"JNostr Channel","about":"This is a channel to test NIP28 in nostr-java","picture":"https://cdn.pixabay.com/photo/2020/05/19/13/48/cartoon-5190942_960_720.jpg"}
				""";
		StringBuilder builder = new StringBuilder();
		builder.append("[\"");
		builder.append(JsonStringEncoder.getInstance().quoteAsString(jsonStr));
		builder.append("\"]");
		
		System.out.println(builder.toString());
		ObjectMapper mapper = new ObjectMapper();
		try {
			ArrayList node = mapper.readValue(builder.toString(), ArrayList.class);
			System.out.println(node.toString());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
	}


	public static void testTagTojson() {
		String jsonStr = """
				["EVENT",{"created_at":1682351083,"sig":"c54a62e0f07530b96bffbb190d80f2fcb5df6478887b61bb828ed669d80d5ac8453ea3fdc76b819b5a52d1d9886cabddfe1d039f0db282727b2244bccbdcc02f","kind":20000,"id":"da717511c10d51ea807dec45c8bcca83a060958258bb81858f8842631dd2cc4d","content":"...","pubkey":"99cf4426cb4507688ff151a760ec098ff78af3cfcdcb6e74fa9c9ed76cba43fa","tags":[["p","99cf4426cb4507688ff151a760ec098ff78af3cfcdcb6e74fa9c9ed76cba43fa","nostr-java"]]}]
						""";

		String tagStr2 = """
				[["p","99cf4426cb4507688ff151a760ec098ff78af3cfcdcb6e74fa9c9ed76cba43fa","nostr-java"]]
										""";
		try {
			ObjectMapper mapper = new ObjectMapper();
			List<Tag> t = mapper.readValue(tagStr2, ArrayList.class);
			PublishEventIn in = PublishEventIn.fromJson(jsonStr);
			System.out.println(t.toString());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

}
