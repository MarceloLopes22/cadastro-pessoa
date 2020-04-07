package com.cadastro.pessoa.seguranca;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

//@Configuration
@EnableWebSecurity
public class SegurancaConfig extends WebSecurityConfigurerAdapter/* implements WebMvcConfigurer */{
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.cors().configurationSource(corsConfigurationSource())
			.and()
			.authorizeRequests()
			.anyRequest().authenticated()
			.and()
			.httpBasic();
	}
	
	@Autowired
	public void configureGlobar(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("admin").password("{noop}admin").roles("USER", "ADMIN")
			.and()
			.withUser("marcelo").password("{noop}123").roles("USER");
	}
	
	@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));
        configuration.setAllowCredentials(true);
        //the below three lines will add the relevant CORS response headers
        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
	
//	@Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**").allowedOrigins("http://localhost:4200", "http://localhost:8080")
//        	.allowedMethods("GET", "POST","PUT", "DELETE");
//    }
	
}
