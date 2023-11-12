package com.leafbodhi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

import com.leafbodhi.nostr.config.NostrConfig;
import com.leafbodhi.nostr.config.ServerInfo;
import com.leafbodhi.nostr.controller.WebSocketServer;

/**
 * @author Jond
 */
@SpringBootApplication
@MapperScan({"com.leafbodhi.nostr.db.mapper"})
@EnableConfigurationProperties(value = {ServerInfo.class,NostrConfig.class} )
public class BodhiApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(BodhiApplication.class, args);
		WebSocketServer.setApplicationContext(run);
	}
}