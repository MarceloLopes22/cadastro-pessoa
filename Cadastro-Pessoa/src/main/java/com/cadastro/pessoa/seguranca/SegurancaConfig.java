package com.cadastro.pessoa.seguranca;

//@SuppressWarnings("deprecation")
//@Configuration
//public class SegurancaConfig extends WebSecurityConfigurerAdapter{
//
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication()
//		.withUser("marcelo").password("123").roles("ADMIN")
//		.and()
//		.withUser("admin").password("admin").roles("ADMIN");
//	}
//	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http
//			.authorizeRequests()
//			.anyRequest()
//			.authenticated()
//			.and()
//			.httpBasic()
//			.and()
//			.sessionManagement()
//			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//	}
//	
//	@Bean
//	@SuppressWarnings("deprecation")
//	public PasswordEncoder passwordEncoder() {
//		return NoOpPasswordEncoder.getInstance();
//	}
//}
