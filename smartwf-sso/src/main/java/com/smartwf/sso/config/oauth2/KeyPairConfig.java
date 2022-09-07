package com.smartwf.sso.config.oauth2;

import java.security.KeyPair;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;



@Configuration
public class KeyPairConfig {

	/**
	@Bean
	public KeyPair keyPair() throws Exception {

	ClassPathResource ksFile = new ClassPathResource("smartwf.jks");//文件名
	KeyStoreKeyFactory ksFactory = new KeyStoreKeyFactory(ksFile, "wind2@".toCharArray()); //第二个参数就是生成时候的密码
	return ksFactory.getKeyPair("smartwf.jks");
	}
	*/
}
