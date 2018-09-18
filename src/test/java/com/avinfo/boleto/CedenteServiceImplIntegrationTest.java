package com.avinfo.boleto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.avinfo.boleto.client.CedenteClientImpl;
import com.avinfo.boleto.client.handler.TecnospedRestClientError;
import com.avinfo.boleto.config.TecnospedRestConfig;
import com.avinfo.boleto.domain.Cedente;
import com.avinfo.boleto.repository.CedenteRepository;
import com.avinfo.boleto.service.CedenteService;
import com.avinfo.boleto.service.CedenteServiceImpl;

import generator.CNPJGenerator;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes={TecnospedRestConfig.class, CedenteRepository.class, CedenteClientImpl.class, CedenteServiceImpl.class})
@EnableAutoConfiguration
@DataJpaTest
@EntityScan(basePackageClasses=Cedente.class)
@ActiveProfiles("homo")
public class CedenteServiceImplIntegrationTest {

	@Autowired
	private CedenteService cedenteService;

	@Test
	public void cadastrarCedenteSuccessTest(){

		final Cedente cedente = Cedente.builder()
			     .razaosocial("Empresa Ltda")
			     .nomefantasia("Empresa")
			     .cpfCnpj(new CNPJGenerator(false).generate())
                 .logradouro("Av. Analista Jucá de Souza")
                 .numero("123")
                 .complemento("sala 987")
                 .bairro("Centro")
                 .cep("87012345")
                 .cidadeibge(4115200)
                 .telefone("(44) 3033-1234")
                 .email("cobranca@boleto.com.br")
                 .build();
		
		Cedente cedenteCadastrado = cedenteService.cadastrarCedente(cedente);
		Cedente cedenteSaved = cedenteService.findById(cedenteCadastrado.getId()).get();
		
		assertThat(cedenteSaved.getId()).isNotNull().isGreaterThan(0L);
		assertThat(cedenteCadastrado.getId()).isEqualTo(cedenteSaved.getId());

		// check idIntegracao
		assertThat(cedenteSaved.getIdIntegracao()).isGreaterThan(0L);
		
	}

	@Test(expected=TecnospedRestClientError.class)
	public void cadastrarCedenteFailTest(){
		
		final Cedente cedente = Cedente.builder()
			    .razaosocial("Empresa Ltda")
			    .nomefantasia("Empresa")
			    .cpfCnpj("99999999999") //CPFCNPJ errado
                .logradouro("Av. Analista Jucá de Souza")
                .numero("123")
                .complemento("sala 987")
                .bairro("Centro")
                .cep("87012345")
                .cidadeibge(4115200)
                .telefone("(44) 3033-1234")
                .email("cobranca@boleto.com.br")
                .build();
		
		cedenteService.cadastrarCedente(cedente);
		
	}
	
}
