package bores;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.tgfcodes.bores.model.Cliente;
import com.tgfcodes.bores.model.Usuario;

class TipoObjectValidationTest {

	@Test
	void IsUsuario() {
		Assert.assertEquals("usuario", this.tipoObject(new Usuario()));
		
	}

	@Test
	void IsCliente() {
		Assert.assertEquals("cliente", this.tipoObject(new Cliente()));
	}
	
	private String tipoObject(Object object) {
		if(object instanceof Usuario) {
			return "usuario";
		}else if(object instanceof Cliente) {
			return "cliente";
		}
		return null;
	}

}
