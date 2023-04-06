package com.anymindgroup.btctransactioncommandservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@SpringBootApplication
@EnableEncryptableProperties
public class BtctransactioncommandServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BtctransactioncommandServiceApplication.class, args);
	}
}
