package bores;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class EmailValidatorTest {

	@Test
	void test() {
		String PADRAO = "^[0-9?A-z0-9?]+(\\.)?[0-9?A-z0-9?]+@[A-z]+\\.[A-z]{3}.?[A-z]{0,3}$";
		var email = "tiago@gmail.com.br";
		Assert.assertEquals(true, email.matches(PADRAO));
	}

}
