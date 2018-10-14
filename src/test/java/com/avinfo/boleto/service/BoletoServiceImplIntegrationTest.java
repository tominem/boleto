package com.avinfo.boleto.service;

import static com.avinfo.boleto.domain.BoletoSituacao.FALHA;
import static com.avinfo.boleto.domain.BoletoSituacao.SALVO;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.avinfo.boleto.client.BoletoClient;
import com.avinfo.boleto.client.dto.BoletoListDTO;
import com.avinfo.boleto.config.TecnospedRestConfig;
import com.avinfo.boleto.domain.Boleto;
import com.avinfo.boleto.domain.BoletoLog;
import com.avinfo.boleto.domain.BoletoSituacao;
import com.avinfo.boleto.repository.BoletoRepository;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes={TecnospedRestConfig.class, BoletoRepository.class})
@EnableAutoConfiguration
@EnableJpaRepositories(basePackageClasses=BoletoRepository.class)
@DataJpaTest
@EntityScan(basePackageClasses={Boleto.class})
@ActiveProfiles("homo")
public class BoletoServiceImplIntegrationTest {

	private static final String CPF_CNPJ_CEDENTE = "77747208000101";
	
	private BoletoService boletoService;
	
	@Autowired
	private BoletoRepository boletoRepository;

	@Mock
	private BoletoClient boletoClient;
	
	@Autowired
	private EntityManager em;
	
	@Before
	public void setup(){
		boletoService = new BoletoServiceImpl(boletoClient, boletoRepository);
	}
	
	@Test
	public void incluir_Boletos_Sucesso(){
		
		// given
		Boleto boleto1 = buildBoleto();
		Boleto boleto2 = buildBoleto();
		
		List<Boleto> boletos = Arrays.asList(boleto1, boleto2);
		
		boletoService.save(boletos);
		
		when(boletoClient.incluir(boletos, CPF_CNPJ_CEDENTE))
			.thenReturn(createBoletoSalvosListDTO(boletos));
		
		List<Boleto> boletosIncluidos = boletoService.incluir(boletos, CPF_CNPJ_CEDENTE);
		
		List<Long> ids = boletos.stream().map(Boleto::getId).collect(toList());
		List<Boleto> boletosFromBD = boletoService.findByIds(ids);
		
		List<BoletoLog> boletoLogs = boletosFromBD.stream()
			.map(Boleto::getBoletoLog)
			.flatMap(b -> b.stream())
			.collect(toList());
		
		assertThat(boletosIncluidos).allMatch(b -> checkBoleto(b, SALVO));
		assertThat(boletosFromBD).allMatch(b -> checkBoleto(b, SALVO));
		assertThat(boletoLogs).size().isEqualTo(2);
		assertThat(boletoLogs).allMatch(bl -> checkBoletoLog(bl, SALVO));
		
	}
	
	@Test
	public void incluir_Boletos_Falha(){
		
		// given
		Boleto boleto1 = buildBoleto();
		Boleto boleto2 = buildBoleto();
		
		List<Boleto> boletos = Arrays.asList(boleto1, boleto2);
		
		boletoService.save(boletos);
		
		when(boletoClient.incluir(boletos, CPF_CNPJ_CEDENTE))
			.thenReturn(createBoletoFalhaListDTO(boletos));
		
		List<Boleto> boletosIncluidos = boletoService.incluir(boletos, CPF_CNPJ_CEDENTE);
		
		List<Long> ids = boletos.stream().map(Boleto::getId).collect(toList());
		List<Boleto> boletosFromBD = boletoService.findByIds(ids);
		
		List<BoletoLog> boletoLogs = boletosFromBD.stream()
				.map(Boleto::getBoletoLog)
				.flatMap(b -> b.stream())
				.collect(toList());
		
		assertThat(boletosIncluidos).allMatch(b -> checkBoleto(b, FALHA));
		assertThat(boletosFromBD).allMatch(b -> checkBoleto(b, FALHA));
		assertThat(boletoLogs).size().isEqualTo(2);
		assertThat(boletoLogs).allMatch(bl -> checkBoletoLog(bl, FALHA));
		
	}

	@Test
	public void incluir_Boletos_ComFalha_Eh_SemFalha(){
		
		// given
		Boleto boleto1 = buildBoleto();
		Boleto boleto2 = buildBoleto();
		Boleto boleto3 = buildBoleto();
		Boleto boleto4 = buildBoleto();
		
		List<Boleto> boletosSucesso = Arrays.asList(boleto1, boleto2);
		List<Boleto> boletosFalha = Arrays.asList(boleto3, boleto4);
		
		boletoService.save(boletosSucesso);
		boletoService.save(boletosFalha);
		
		ArrayList<Boleto> boletosToSend = new ArrayList<>(boletosSucesso);
		boletosToSend.addAll(boletosFalha);
		
		BoletoListDTO boletoListSucesso = createBoletoSalvosListDTO(boletosSucesso);
		BoletoListDTO boletoListFalha = createBoletoFalhaListDTO(boletosFalha);
		
		BoletoListDTO boletoListDTO = new BoletoListDTO();
		boletoListDTO.setSuccesseds(boletoListSucesso.getSuccesseds());
		boletoListDTO.setFaileds(boletoListFalha.getFaileds());
		
		when(boletoClient.incluir(boletosToSend, CPF_CNPJ_CEDENTE))
			.thenReturn(boletoListDTO);
		
		List<Boleto> boletosIncluidos = boletoService.incluir(boletosToSend, CPF_CNPJ_CEDENTE);
		
		List<Long> ids = boletosToSend.stream().map(Boleto::getId).collect(toList());
		List<Boleto> boletosFromBD = boletoService.findByIds(ids);
		
		List<BoletoLog> boletoLogs = boletosFromBD.stream()
				.map(Boleto::getBoletoLog)
				.flatMap(b -> b.stream())
				.collect(toList());
		
		assertThat(boletosIncluidos).filteredOn(b -> checkBoleto(b, FALHA)).size().isEqualTo(2);
		assertThat(boletosIncluidos).filteredOn(b -> checkBoleto(b, SALVO)).size().isEqualTo(2);
		assertThat(boletosFromBD).filteredOn(b -> checkBoleto(b, FALHA)).size().isEqualTo(2);
		assertThat(boletosFromBD).filteredOn(b -> checkBoleto(b, SALVO)).size().isEqualTo(2);
		assertThat(boletoLogs).size().isEqualTo(4);
		assertThat(boletoLogs).filteredOn(bl -> checkBoletoLog(bl, FALHA)).size().isEqualTo(2);
		assertThat(boletoLogs).filteredOn(bl -> checkBoletoLog(bl, SALVO)).size().isEqualTo(2);
		
	}
	
	private boolean checkBoletoLog(BoletoLog boletoLog, BoletoSituacao situacao){
		boolean checkSituacao = boletoLog.getSituacao().equals(situacao);
		boolean checkIdGraterThanZero = boletoLog.getId() != null && boletoLog.getId() > 0L;
		boolean checkMsgErro = situacao == FALHA ? "Teste de erro".equals(boletoLog.getErro()) : true;
		
		return checkSituacao && checkIdGraterThanZero && checkMsgErro;
	}
	
	private boolean checkBoleto(Boleto boleto, BoletoSituacao situacao){
		boolean checkIdIntegracao = situacao == SALVO ? (boleto.getIdIntegracao() != null && boleto.getIdIntegracao().equals("idIntegracao" + boleto.getId())) : true;
		boolean checkBoletoSituacaoSalvo = boleto.getSituacao().equals(situacao);
		
		return checkIdIntegracao && checkBoletoSituacaoSalvo;
	}
	

	private BoletoListDTO createBoletoFalhaListDTO(List<Boleto> boletos) {
		return createBoletoListDTO(boletos, FALHA, "Teste de erro");
	}
	
	private BoletoListDTO createBoletoSalvosListDTO(List<Boleto> boletos) {
		return createBoletoListDTO(boletos, SALVO, null);
	}
	
	private BoletoListDTO createBoletoListDTO(List<Boleto> boletos, BoletoSituacao situacao, String erro) {
		
		BoletoListDTO boletoListDTO = new BoletoListDTO();
		
		for (Boleto boleto : boletos) {

			Boleto boletoTrans = boleto.toBuilder()
					.idIntegracao(situacao.equals(SALVO) ? "idIntegracao" + boleto.getId() : null)
					.tituloNumeroDocumento(boleto.getId() + "")
					.situacao(situacao)
					.build();
			
			// retirar objeto do entitymanager
			em.detach(boletoTrans);
			
			boletoTrans.setBoletoLog(new ArrayList<>());
			boletoTrans.addBoletoLog(new BoletoLog(erro, situacao));
			
			if (situacao == BoletoSituacao.SALVO) {
				boletoListDTO.addSuccessed(boletoTrans);
			} else{
				boletoListDTO.addFailed(boletoTrans);
			}
			
		}
		
		return boletoListDTO;
	}

	private Boleto buildBoleto() {
		return new BoletoTestFactory().buildBoleto();
	}
	
}
