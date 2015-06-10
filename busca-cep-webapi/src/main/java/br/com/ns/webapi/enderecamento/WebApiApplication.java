package br.com.ns.webapi.enderecamento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * Application class do Spring Boot responsável pela inicialização da Web Api.
 * 
 * Esta WebApi será responsável por prover operações de um domínio específico,
 * no caso o domínio de endereçamento postal.
 * 
 * Será utilizado o projeto Spring Cloud Hystrix para Dashboards (+actuator) e
 * circuite breaker.
 * 
 * @author Erico Marineli (emarineli@gmail.com)
 * @version 1.0, 09/06/2015
 * @since 1.0
 *
 */
@SpringBootApplication
@EnableHystrixDashboard
@EnableCircuitBreaker
public class WebApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebApiApplication.class, args);
	}
}
