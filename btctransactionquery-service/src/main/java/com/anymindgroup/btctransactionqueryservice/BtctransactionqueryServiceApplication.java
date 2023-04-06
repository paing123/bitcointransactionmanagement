package com.anymindgroup.btctransactionqueryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@SpringBootApplication
@EnableEncryptableProperties
public class BtctransactionqueryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BtctransactionqueryServiceApplication.class, args);
	}

}
