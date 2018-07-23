package org.springdev.guides.digestauth;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.digest;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)  // <1>
@SpringBootTest  // <2>
public class DigestAuthApplicationTests {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	@Before
	public void setup() {  // <3>
		mvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())  // <4>
				.build();
	}

	@Test
	public void digestAuth() throws Exception {
		mvc
			.perform(
				get("/").with(
						digest("user")   // <5>
						.password("password")
						.realm("Spring Books")))
			.andExpect(status().isOk());  // <6>
	}

}
