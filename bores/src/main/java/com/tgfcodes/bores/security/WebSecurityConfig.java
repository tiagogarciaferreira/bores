package com.tgfcodes.bores.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.authorizeRequests()
			.antMatchers("/publico/**").permitAll()
			.antMatchers("/error/**").permitAll()
			//.antMatchers("/usuario/novo").hasAnyRole("CADASTRAR_CIDADE")
			.anyRequest().authenticated()
			.and()
		.formLogin()
			.passwordParameter("password")
			.usernameParameter("username")
			.loginProcessingUrl("/publico/Login.xhtml")
			.loginPage("/publico/Login.xhtml?acesso=restrito")
			.failureUrl("/publico/Login.xhtml?login=erro")
			.defaultSuccessUrl("/home/Dashboard.xhtml", true)
			.permitAll()
			.and()
			.exceptionHandling().accessDeniedPage("/error/403.xhtml")
			.and()
		.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.deleteCookies("JSESSIONID")
			.invalidateHttpSession(true)
			.clearAuthentication(true)
			.logoutSuccessUrl("/publico/Login.xhtml?logout=sucesso")
			.and()
		.sessionManagement()
			.enableSessionUrlRewriting(false)
			.maximumSessions(1)
			.expiredUrl("/publico/Login.xhtml?session=expirada")
			.and()
		.sessionFixation()
		.migrateSession();
	}
	
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/javax.faces.resource/**");
	}
}
