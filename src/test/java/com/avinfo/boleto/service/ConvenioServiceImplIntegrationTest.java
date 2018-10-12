package com.avinfo.boleto.service;

import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.avinfo.boleto.client.ConvenioClientImpl;
import com.avinfo.boleto.client.exception.TecnospedRestClientError;
import com.avinfo.boleto.config.TecnospedRestConfig;
import com.avinfo.boleto.domain.Conta;
import com.avinfo.boleto.domain.Convenio;
import com.avinfo.boleto.repository.ConvenioRepository;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes={TecnospedRestConfig.class, ConvenioRepository.class, ConvenioClientImpl.class, ConvenioServiceImpl.class})
@EnableJpaRepositories(basePackageClasses=ConvenioRepository.class)
@DataJpaTest
@EntityScan(basePackageClasses={Convenio.class, Convenio.class})
@ActiveProfiles("homo")
public class ConvenioServiceImplIntegrationTest {

	private static final String NUMERO_CONVENIO_CADASTRO = "7889604730";
	private static final String NUMERO_CONVENIO_EDICAO = "7889604732";
	private static final Long CONTA_ID_INTEGRACAO = 38L;
	private static final String CNPJ_SACADO = "01001001000113";

	@Autowired
	private ConvenioService convenioService;

	@Autowired
	private EntityManager entityManager;
	
	private Convenio cadastrarConvenio(){

		final Convenio convenio = Convenio.builder()
				.numeroConvenio(NUMERO_CONVENIO_CADASTRO)
				.descricaoConvenio("Convenio da tecnospeed")
				.carteira("109")
				.especie("Boleto")
				.padraoCNAB("400")
				.numeroRemessa("1")
				.conta(Conta.builder()
							.idIntegracao(CONTA_ID_INTEGRACAO)
							.build())
				.build();
		
		Convenio convenioCadastrado = convenioService.save(convenio, CNPJ_SACADO);
		Convenio convenioSaved = convenioService.findById(convenioCadastrado.getId()).get();
		
		assertThat(convenioSaved.getId()).isNotNull().isGreaterThan(0L);
		assertThat(convenioCadastrado.getId()).isEqualTo(convenioSaved.getId());

		// check idIntegracao
		assertThat(convenioSaved.getIdIntegracao()).isGreaterThan(0L);
		
		Convenio convenioResult = convenio.toBuilder()
				.id(convenioCadastrado.getId())
				.idIntegracao(convenioCadastrado.getIdIntegracao())
				.build();
		
		return convenioResult; 
	}
	
	@Test
	public void cadastrarEhEditarConvenioSuccessTest(){

		final Convenio convenioPersisted = cadastrarConvenio();
		final Long idPersisted = convenioPersisted.getId();

		// remove o objeto do entitymanager para fazer o teste de comparação
		entityManager.detach(convenioPersisted);
		
		final Convenio convenioDTO = convenioPersisted.toBuilder()
			     .carteira("110")
			     .numeroConvenio(NUMERO_CONVENIO_EDICAO)
                 .build();
		
		final Convenio convenioTransmited = convenioService.save(convenioDTO, CNPJ_SACADO);
		final Convenio convenioSaved = convenioService.findById(convenioTransmited.getId()).get();
		
		assertThat(convenioSaved.getCarteira()).isEqualTo("110");
		assertThat(convenioSaved.getCarteira()).isNotEqualTo(convenioPersisted.getCarteira());
		assertThat(convenioSaved.getNumeroConvenio()).isEqualTo(NUMERO_CONVENIO_EDICAO);
		
		assertThat(convenioSaved.getId()).isNotNull().isGreaterThan(0L);
		assertThat(convenioTransmited.getId()).isEqualTo(idPersisted);

	}

	
	@Test(expected=TecnospedRestClientError.class)
	public void cadastrarConvenioFailTest(){
		
		final Convenio convenio = Convenio.builder()
				.numeroConvenio(NUMERO_CONVENIO_CADASTRO)
				.descricaoConvenio("Convenio da tecnospeed")
				.carteira("109")
				.especie("Boleto")
				.padraoCNAB("400")
				.numeroRemessa("1")
				.conta(Conta.builder()
							.idIntegracao(-10L)  //conta inexistente
							.build())
				.build();
		
		convenioService.save(convenio, CNPJ_SACADO);
		
	}
	
	
	
}
