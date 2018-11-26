package com.avinfo.boleto.client;

import static com.avinfo.boleto.domain.BoletoSituacao.FALHA;
import static com.avinfo.boleto.domain.BoletoSituacao.SALVO;
import static java.lang.ClassLoader.getSystemResource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.avinfo.boleto.client.BoletoClient;
import com.avinfo.boleto.client.BoletoClientImpl;
import com.avinfo.boleto.client.dto.BoletoListDTO;
import com.avinfo.boleto.config.TecnospedRestReqParams;
import com.avinfo.boleto.domain.Boleto;

public class BoletoClientImplTest {
	
	private static final String CNPJ_CEDENTE = "77747208000101";

	private BoletoClient boletoClient;
	
	@Mock
	private TecnospedRestReqParams tecnospedRestReqParams;
	
	private MockRestServiceServer mockServer;
	
	private RestTemplate restTemplate;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		restTemplate = new RestTemplate();
		mockServer = MockRestServiceServer.createServer(restTemplate);
		boletoClient = new BoletoClientImpl(restTemplate, tecnospedRestReqParams);
	}
	
	@Test
	public void incluirBoleto(){
		
		String json = loadJSON("boleto-incluir-resp.json");
		
		mockServer.expect(once(), requestTo(BoletoClientImpl.END_POINT_LOTE))
			.andExpect(method(HttpMethod.POST))
			.andRespond(withSuccess(json, MediaType.APPLICATION_JSON));
		
		List<Boleto> boletos = new ArrayList<>();
		
		BoletoTestFactory factory = new BoletoTestFactory();
		Boleto boleto = factory.buildBoleto();
		
		boletos.add(boleto);
		
		BoletoListDTO boletoList = boletoClient.incluir(boletos, CNPJ_CEDENTE);
		
		mockServer.verify();
		
		Boleto boletoRes1 = boletoList.getSuccesseds().get(1L);
		Boleto boletoRes2 = boletoList.getSuccesseds().get(2L);
		
		assertThat(boletoList.getFaileds()).size().isEqualTo(1);
		assertThat(boletoList.getSuccesseds()).size().isEqualTo(2);
		assertThat(boletoRes1.getTituloNumeroDocumento()).isEqualTo("1");
		assertThat(boletoRes1.getIdIntegracao()).isEqualTo("H1-vGuYDul");
		assertThat(boletoRes2.getTituloNumeroDocumento()).isEqualTo("2");
		assertThat(boletoRes2.getIdIntegracao()).isEqualTo("H1-vGuYDul2");
		assertThat(boletoList.getSuccesseds().values()).allMatch(b -> b.getSituacao().equals(SALVO));
		assertThat(boletoList.getFaileds().values()).allMatch(b -> b.getSituacao().equals(FALHA));
		
	}
	
	@Test
	public void incluirBoletoSomenteBemSucedidos(){
		
		String json = loadJSON("boleto-incluir-resp-bem-sucedidos.json");
		
		mockServer.expect(once(), requestTo(BoletoClientImpl.END_POINT_LOTE))
			.andExpect(method(HttpMethod.POST))
			.andRespond(withSuccess(json, MediaType.APPLICATION_JSON));
		
		List<Boleto> boletos = new ArrayList<>();
		
		BoletoTestFactory factory = new BoletoTestFactory();
		Boleto boleto = factory.buildBoleto();
		
		boletos.add(boleto);
		
		BoletoListDTO boletoList = boletoClient.incluir(boletos, CNPJ_CEDENTE);
		
		mockServer.verify();
		
		Boleto boletoRes1 = boletoList.getSuccesseds().get(1L);
		Boleto boletoRes2 = boletoList.getSuccesseds().get(2L);
		
		assertThat(boletoList.getFaileds()).size().isEqualTo(0);
		assertThat(boletoList.getSuccesseds()).size().isEqualTo(2);
		assertThat(boletoRes1.getTituloNumeroDocumento()).isEqualTo("1");
		assertThat(boletoRes1.getIdIntegracao()).isEqualTo("H1-vGuYDul");
		assertThat(boletoRes2.getTituloNumeroDocumento()).isEqualTo("2");
		assertThat(boletoRes2.getIdIntegracao()).isEqualTo("H1-vGuYDul2");
		assertThat(boletoList.getSuccesseds().values()).allMatch(b -> b.getSituacao().equals(SALVO));
		
	}
	
	@Test
	public void incluirBoletoSomenteMalSucedidos(){
		
		String json = loadJSON("boleto-incluir-resp-mal-sucedidos.json");
		
		mockServer.expect(once(), requestTo(BoletoClientImpl.END_POINT_LOTE))
			.andExpect(method(HttpMethod.POST))
			.andRespond(withSuccess(json, MediaType.APPLICATION_JSON));
		
		List<Boleto> boletos = new ArrayList<>();
		
		BoletoTestFactory factory = new BoletoTestFactory();
		Boleto boleto = factory.buildBoleto();
		
		boletos.add(boleto);
		
		BoletoListDTO boletoList = boletoClient.incluir(boletos, CNPJ_CEDENTE);
		
		mockServer.verify();
		
		Boleto boletoRes1 = boletoList.getFaileds().get(1L);
		Boleto boletoRes2 = boletoList.getFaileds().get(2L);
		
		assertThat(boletoList.getSuccesseds()).size().isEqualTo(0);
		assertThat(boletoList.getFaileds()).size().isEqualTo(2);
		assertThat(boletoRes1.getTituloNumeroDocumento()).isEqualTo("1");
		assertThat(boletoRes2.getTituloNumeroDocumento()).isEqualTo("2");
		assertThat(boletoList.getFaileds().values()).allMatch(b -> b.getSituacao().equals(FALHA));
		
	}
	
	
	private String loadJSON(String fileName) {
		try {
			return new String(Files.readAllBytes(Paths.get(getSystemResource(fileName).toURI())));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
