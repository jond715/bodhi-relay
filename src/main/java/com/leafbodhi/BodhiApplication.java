package com.leafbodhi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.leafbodhi.nostr.controller.WebSocketServer;


@EnableAutoConfiguration
@SpringBootApplication
@MapperScan({"com.leafbodhi.nostr.mapper"})
public class BodhiApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(BodhiApplication.class, args);
		WebSocketServer.setApplicationContext(run);
	}
}