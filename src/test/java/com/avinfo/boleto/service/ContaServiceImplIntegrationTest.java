package com.avinfo.boleto.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.avinfo.boleto.client.ContaClientImpl;
import com.avinfo.boleto.client.exception.TecnospedRestClientError;
import com.avinfo.boleto.config.TecnospedRestConfig;
import com.avinfo.boleto.domain.Cedente;
import com.avinfo.boleto.domain.Conta;
import com.avinfo.boleto.repository.ContaRepository;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes={TecnospedRestConfig.class, ContaRepository.class, ContaClientImpl.class, ContaServiceImpl.class})
@EnableJpaRepositories(basePackageClasses=ContaRepository.class)
@DataJpaTest
@EntityScan(basePackageClasses={Cedente.class, Conta.class})
@ActiveProfiles("homo")
public class ContaServiceImplIntegrationTest {

	@Autowired
	private ContaService contaService;

	private int random(int min, int max) {
		return new Random().nextInt((max - min) + 1) + min;
	}

	private Conta cadastrarConta(){

		final Conta conta = Conta.builder()
			     .codigoBanco("341")
			     .agencia(random(11111, 99999) + "") //Numero de agência randomica
			     .agenciaDV("1")
                 .conta(random(11111, 99999) + "") //Número de conta randomica
                 .contaDV("3")
                 .tipoConta("CORRENTE")
                 .codBeneficiario("60473")
                 .cedente(Cedente.builder()
                		 	.cpfCnpj("01001001000113")
                		 	.build())
                 .build();
		
		Conta contaCadastrado = contaService.save(conta);
		Conta contaSaved = contaService.findById(contaCadastrado.getId()).get();
		
		assertThat(contaSaved.getId()).isNotNull().isGreaterThan(0L);
		assertThat(contaCadastrado.getId()).isEqualTo(contaSaved.getId());

		// check idIntegracao
		assertThat(contaSaved.getIdIntegracao()).isGreaterThan(0L);
		
		return contaSaved; 
	}
	
	@Test
	public void cadastrarEhEditarContaSuccessTest(){

		final Conta contaPersisted = cadastrarConta();
		final Long idPersisted = contaPersisted.getId();
		
		final Conta contaDTO = contaPersisted.toBuilder()
			     .agencia("55555")
                 .build();
		
		final Conta contaTransmited = contaService.save(contaDTO);
		final Conta contaSaved = contaService.findById(contaTransmited.getId()).get();
		
		assertThat(contaSaved.getAgencia()).isEqualTo("55555");
		assertThat(contaSaved.getCodBeneficiario()).isEqualTo(contaPersisted.getCodBeneficiario());
		
		assertThat(contaSaved.getId()).isNotNull().isGreaterThan(0L);
		assertThat(contaTransmited.getId()).isEqualTo(idPersisted);

	}

	
	@Test(expected=TecnospedRestClientError.class)
	public void cadastrarCedenteFailTest(){
		
		final Conta conta = Conta.builder()
			     .codigoBanco("888888")
			     .agencia("1234")
			     .agenciaDV("1")
                 .conta("59698")
                 .contaDV("3")
                 .tipoConta("CORRENTE")
                 .codBeneficiario("60473")
                 .contaDV("3")
                 .tipoConta("CORRENTE")
                 .codBeneficiario("60473")
                 .cedente(Cedente.builder()
                		 	.id(1L)
                		 	.cpfCnpj("01001001000113")
                		 	.build())
                 .build();
		
		contaService.save(conta);
		
	}
	
	
	
}
