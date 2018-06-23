package com.github.chiby.ide.frontend;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.chibyhq.store.model.repositories.ApplicationRepository;
import com.github.chibyhq.store.model.repositories.RunSessionRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
public class FrontendApplicationTests {

	@Autowired
	ApplicationRepository applicationRepository;
	
	@Autowired
	RunSessionRepository runSessionRepository;
	
	@Test
	public void contextLoads() {
	}

}
