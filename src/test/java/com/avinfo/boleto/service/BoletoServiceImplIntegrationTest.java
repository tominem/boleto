package com.avinfo.boleto.service;

import static com.avinfo.boleto.client.dto.TipoImpressao.IMPRESSAO_NORMAL;
import static com.avinfo.boleto.domain.BoletoSituacao.FALHA;
import static com.avinfo.boleto.domain.BoletoSituacao.SALVO;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.avinfo.boleto.client.BoletoClientImpl;
import com.avinfo.boleto.client.dto.BoletoListDTO;
import com.avinfo.boleto.config.TecnospedRestConfig;
import com.avinfo.boleto.domain.Boleto;
import com.avinfo.boleto.domain.BoletoLog;
import com.avinfo.boleto.domain.BoletoSituacao;
import com.avinfo.boleto.repository.BoletoRepository;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes={TecnospedRestConfig.class, BoletoRepository.class, BoletoClientImpl.class})
@EnableAutoConfiguration
@EnableJpaRepositories(basePackageClasses=BoletoRepository.class)
@DataJpaTest
@EntityScan(basePackageClasses={Boleto.class})
@ActiveProfiles("homo")
public class BoletoServiceImplIntegrationTest {

	private static final String CPF_CNPJ_CEDENTE = "01001001000113";
	
	private BoletoService boletoService;
	
	@Autowired
	private BoletoRepository boletoRepository;

	@Mock
	private BoletoClient boletoClientMock;

	@Mock
	private BoletoRepository boletoRepositoryMock;
	
	@Autowired
	private BoletoClient boletoClient;
	
	@Autowired
	private EntityManager em;

	private BoletoServiceTestHelper helper;
	
	@Before
	public void setup(){
		boletoService = new BoletoServiceImpl(boletoClientMock, boletoRepository);
		helper = new BoletoServiceTestHelper(em);
	}
	
	@Test
	public void incluir_Boletos_Sucesso(){
		
		// given
		Boleto boleto1 = helper.buildBoleto();
		Boleto boleto2 = helper.buildBoleto();
		
		List<Boleto> boletos = Arrays.asList(boleto1, boleto2);
		
		boletoService.save(boletos);
		
		when(boletoClientMock.incluir(boletos, CPF_CNPJ_CEDENTE))
			.thenReturn(helper.createBoletoSalvosListDTO(boletos));
		
		List<Boleto> boletosIncluidos = boletoService.incluir(boletos, CPF_CNPJ_CEDENTE);
		
		List<Long> ids = boletos.stream().map(Boleto::getId).collect(toList());
		List<Boleto> boletosFromBD = boletoService.findByIds(ids);
		
		List<BoletoLog> boletoLogs = boletosFromBD.stream()
			.map(Boleto::getBoletoLog)
			.flatMap(b -> b.stream())
			.collect(toList());
		
		assertThat(boletosIncluidos).allMatch(b -> helper.checkBoleto(b, SALVO));
		assertThat(boletosFromBD).allMatch(b -> helper.checkBoleto(b, SALVO));
		assertThat(boletoLogs).size().isEqualTo(2);
		assertThat(boletoLogs).allMatch(bl -> helper.checkBoletoLog(bl, SALVO));
		
	}
	
	@Test
	public void incluir_Boletos_Falha(){
		
		// given
		Boleto boleto1 = helper.buildBoleto();
		Boleto boleto2 = helper.buildBoleto();
		
		List<Boleto> boletos = Arrays.asList(boleto1, boleto2);
		
		boletoService.save(boletos);
		
		when(boletoClientMock.incluir(boletos, CPF_CNPJ_CEDENTE))
			.thenReturn(helper.createBoletoFalhaListDTO(boletos));
		
		List<Boleto> boletosIncluidos = boletoService.incluir(boletos, CPF_CNPJ_CEDENTE);
		
		List<Long> ids = boletos.stream().map(Boleto::getId).collect(toList());
		List<Boleto> boletosFromBD = boletoService.findByIds(ids);
		
		List<BoletoLog> boletoLogs = boletosFromBD.stream()
				.map(Boleto::getBoletoLog)
				.flatMap(b -> b.stream())
				.collect(toList());
		
		assertThat(boletosIncluidos).allMatch(b -> helper.checkBoleto(b, FALHA));
		assertThat(boletosFromBD).allMatch(b -> helper.checkBoleto(b, FALHA));
		assertThat(boletoLogs).size().isEqualTo(2);
		assertThat(boletoLogs).allMatch(bl -> helper.checkBoletoLog(bl, FALHA));
		
	}

	@Test
	public void incluir_Boletos_ComFalha_Eh_SemFalha(){
		
		// given
		Boleto boleto1 = helper.buildBoleto();
		Boleto boleto2 = helper.buildBoleto();
		Boleto boleto3 = helper.buildBoleto();
		Boleto boleto4 = helper.buildBoleto();
		
		List<Boleto> boletosSucesso = Arrays.asList(boleto1, boleto2);
		List<Boleto> boletosFalha = Arrays.asList(boleto3, boleto4);
		
		boletoService.save(boletosSucesso);
		boletoService.save(boletosFalha);
		
		ArrayList<Boleto> boletosToSend = new ArrayList<>(boletosSucesso);
		boletosToSend.addAll(boletosFalha);
		
		BoletoListDTO boletoListSucesso = helper.createBoletoSalvosListDTO(boletosSucesso);
		BoletoListDTO boletoListFalha = helper.createBoletoFalhaListDTO(boletosFalha);
		
		BoletoListDTO boletoListDTO = new BoletoListDTO();
		boletoListDTO.setSuccesseds(boletoListSucesso.getSuccesseds());
		boletoListDTO.setFaileds(boletoListFalha.getFaileds());
		
		when(boletoClientMock.incluir(boletosToSend, CPF_CNPJ_CEDENTE))
			.thenReturn(boletoListDTO);
		
		List<Boleto> boletosIncluidos = boletoService.incluir(boletosToSend, CPF_CNPJ_CEDENTE);
		
		List<Long> ids = boletosToSend.stream().map(Boleto::getId).collect(toList());
		List<Boleto> boletosFromBD = boletoService.findByIds(ids);
		
		List<BoletoLog> boletoLogs = boletosFromBD.stream()
				.map(Boleto::getBoletoLog)
				.flatMap(b -> b.stream())
				.collect(toList());
		
		assertThat(boletosIncluidos).filteredOn(b -> helper.checkBoleto(b, FALHA)).size().isEqualTo(2);
		assertThat(boletosIncluidos).filteredOn(b -> helper.checkBoleto(b, SALVO)).size().isEqualTo(2);
		assertThat(boletosFromBD).filteredOn(b -> helper.checkBoleto(b, FALHA)).size().isEqualTo(2);
		assertThat(boletosFromBD).filteredOn(b -> helper.checkBoleto(b, SALVO)).size().isEqualTo(2);
		assertThat(boletoLogs).size().isEqualTo(4);
		assertThat(boletoLogs).filteredOn(bl -> helper.checkBoletoLog(bl, FALHA)).size().isEqualTo(2);
		assertThat(boletoLogs).filteredOn(bl -> helper.checkBoletoLog(bl, SALVO)).size().isEqualTo(2);
		
	}
	
	@Test
	public void consulta_um_boleto(){
		
		boletoService = new BoletoServiceImpl(boletoClient, boletoRepository);
		
		Boleto boleto = Boleto.builder()
				.idIntegracao("rylcsPqudg")
				.build();
		
		boletoRepository.save(boleto);
		
		Map<String, String> filters = new HashMap<>();
		filters.put("IdIntegracao", "rylcsPqudg");
		
		List<Boleto> boletos = boletoService.getFromWS(filters, CPF_CNPJ_CEDENTE);
		
		assertThat(boletos).size().isGreaterThan(0);
	}
	
	@Test
	public void solicita_impressao_Eh_salvarPDF() throws IOException{
		
		boletoService = new BoletoServiceImpl(boletoClient, boletoRepository);
		
		Boleto boleto1 = helper.buildBoleto();
		Boleto boleto2 = helper.buildBoleto();
		Boleto boleto3 = helper.buildBoleto();
		boleto1.setId(200012L);
		boleto1.setTituloNossoNumero("200012");
		boleto2.setId(200013L);
		boleto2.setTituloNossoNumero("200013");
		boleto3.setId(200014L);
		boleto3.setTituloNossoNumero("200014");
		
		boleto1.setIdIntegracao("SJqpHNgnX");
		boleto2.setIdIntegracao("HJg56BVehX");
		boleto3.setIdIntegracao("Bk-cpBVg3Q");
		
		boletoRepository.save(boleto1);
		boletoRepository.save(boleto2);
		boletoRepository.save(boleto3);
		
		List<Boleto> boletosIncluidos = Arrays.asList(boleto1, boleto2, boleto3);
		
		List<Boleto> boletosComProtocolo = boletoService.solicitaImpressao(boletosIncluidos, IMPRESSAO_NORMAL, CPF_CNPJ_CEDENTE);
		
		byte[] pdf = boletoService.salvarPDF(boletosComProtocolo.get(0).getProtocoloImpressao(), CPF_CNPJ_CEDENTE);
		
		Path pathFile = Paths.get("boleto.pdf");
		if (pdf.length > 0) {
			Files.write(pathFile, pdf, StandardOpenOption.WRITE);
			Files.delete(pathFile);
	    }
		
		assertThat(boletosComProtocolo).allMatch(b -> b.getProtocoloImpressao() != null && b.getSituacao() == BoletoSituacao.PROCESSANDO);
		assertThat(pdf.length).isGreaterThan(0);
		assertThat(pathFile.toFile().exists()).isFalse();
		
	}
	
}