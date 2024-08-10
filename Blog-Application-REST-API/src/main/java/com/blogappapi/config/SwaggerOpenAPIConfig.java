package com.blogappapi.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(

		info = @Info(

				title = "Blog-App-REST-API", description = "This is a back-end for blog application", summary = "In this project we devloped the API for the User, Post, Category, Comments and Login ", termsOfService = "T&C", contact = @Contact(name = "Swapnil Tole", email = "swapnil.info2020@gmail.com"),

				license = @License(name = "Open License !!"),

				version = "1.0V"),

		servers = { @Server(description = "Devlopment Server !!", url = "http://localhost:9090"

		),

				@Server(description = "Production Server !! ", url = "http://blogapp-env.eba-dpqwy42r.eu-north-1.elasticbeanstalk.com/"

				) },

		security = @SecurityRequirement(

				name = "auth")

)

@SecurityScheme(name = "auth", in = SecuritySchemeIn.HEADER, type = SecuritySchemeType.HTTP, bearerFormat = "JWT", description = "Apply Security/Authentication on All API", scheme = "bearer"

)

@Configuration
public class SwaggerOpenAPIConfig {

}
