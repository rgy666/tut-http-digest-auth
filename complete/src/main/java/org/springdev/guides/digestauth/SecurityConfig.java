package org.springdev.guides.digestauth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;

@Configuration  // <1>
@EnableWebSecurity  // <2>
public class SecurityConfig extends WebSecurityConfigurerAdapter {  // <3>
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("user").password("password").roles("USER");  // <4>
	}
	
	@Bean("authenticationManager")  // <5>
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	public DigestAuthenticationEntryPoint authenticationEntryPoint() {  // <6>
		DigestAuthenticationEntryPoint authenticationEntryPoint = new DigestAuthenticationEntryPoint();
		authenticationEntryPoint.setRealmName("Spring Books");
		authenticationEntryPoint.setKey("spring");
		
		return authenticationEntryPoint;
	}
	
	public DigestAuthenticationFilter digestAuthenticationFilter() throws Exception {  // <7>
		DigestAuthenticationFilter digestAuthenticationFilter = new DigestAuthenticationFilter();
    	digestAuthenticationFilter.setAuthenticationEntryPoint(authenticationEntryPoint());
    	digestAuthenticationFilter.setUserDetailsService(this.userDetailsServiceBean());
    	
    	return digestAuthenticationFilter;
	}
	
    @Override
    public void configure(HttpSecurity http) throws Exception {
    	http.addFilterBefore(digestAuthenticationFilter(), BasicAuthenticationFilter.class);  // <8>
    }
}
