package dev.wericson.wrn_burguers;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import dev.wericson.wrn_burguers.domain.model.Product;
import dev.wericson.wrn_burguers.service.ProductService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(servers = {@Server(url = "/", description = "Default Server URL")})
@SpringBootApplication
public class WrnBurguersApplication {

	public static void main(String[] args) {
		SpringApplication.run(WrnBurguersApplication.class, args);
	}
	
	@Bean
    CommandLineRunner initDatabase(@Autowired ProductService productService) {
        return args -> {
            if (productService.findAll().isEmpty()) {
                productService.create(new Product("Hambúrguer Clássico", new BigDecimal("15.00")));
                productService.create(new Product("Cheeseburger", new BigDecimal("18.00")));
                productService.create(new Product("Refrigerante", new BigDecimal("5.00")));
                productService.create(new Product("Suco Natural", new BigDecimal("7.00")));
            }
        };
    }

}
