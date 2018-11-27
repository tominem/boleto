package com.avinfo.boleto.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.avinfo.boleto.client.BoletoClientImpl;
import com.avinfo.boleto.client.dto.GeraRemessaRetDTO;
import com.avinfo.boleto.config.TecnospedRestConfig;
import com.avinfo.boleto.domain.Boleto;
import com.avinfo.boleto.domain.Remessa;
import com.avinfo.boleto.repository.BoletoRepository;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes={TecnospedRestConfig.class, BoletoRepository.class, BoletoClientImpl.class})
@EnableAutoConfiguration
@EnableJpaRepositories(basePackageClasses=BoletoRepository.class)
@DataJpaTest
@EntityScan(basePackageClasses={Boleto.class})
@ActiveProfiles("homo")
public class RemessaServiceIntegrationTest {
	
	@Autowired
	private BoletoRepository boletoRepository;
	
	private BoletoServiceTestHelper helper;

	private RemessaService remessaService;
	
	@Before
	public void setup(){
		helper = new BoletoServiceTestHelper();
	}
	
	@Test
	public void socitia_remessa_uma_agencia(){
		
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
		
		List<Boleto> boletos = Arrays.asList(boleto1, boleto2, boleto3);
		
		GeraRemessaRetDTO dto = remessaService.geraRemessa(boletos);
		List<Remessa> remessas = dto.getRemessas();
		List<Boleto> boletosComFalha = dto.getFalhas();
		
		assertThat(remessas.stream().map(Remessa::getBoletos).flatMap(Stream::of).allMatch(b -> boletos.contains(b)));
		assertThat(boletosComFalha).isEqualTo(null);
		
	}

}
