package com.leafbodhi.nostr;

import com.leafbodhi.nostr.crypto.Bech32;
import com.leafbodhi.nostr.crypto.Schnorr;
import com.leafbodhi.nostr.utils.Bech32Prefix;
import com.leafbodhi.nostr.utils.NostrUtil;

public class GenerateKey {

	public static void main(String[] args) {
//		GenerateKey.genPriKeyAndPubkey();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
