package com.github.chiby.ide.frontend;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan({"com.github.chiby.player.*","com.github.chiby.ide.*"})
public class FrontendApplicationTests {

	@Test
	public void contextLoads() {
	}

}
