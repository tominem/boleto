package com.avinfo.boleto.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

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

import com.avinfo.boleto.config.TecnospedRestConfig;
import com.avinfo.boleto.domain.Boleto;
import com.avinfo.boleto.domain.BoletoSituacao;
import com.avinfo.boleto.repository.BoletoRepository;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes={TecnospedRestConfig.class, BoletoServiceImpl.class, BoletoRepository.class})
@EnableAutoConfiguration
@EnableJpaRepositories(basePackageClasses=BoletoRepository.class)
@DataJpaTest
@EntityScan(basePackageClasses={Boleto.class})
@ActiveProfiles("homo")
public class BoletoServiceImplIntegrationTest {

	private static final String CPF_CNPJ_CEDENTE = "77747208000101";
	
	@Autowired
	private BoletoService boletoService;
	
	@Test
	public void incluirSomenteUmBoletoTest(){
		
		BoletoTestFactory factory = new BoletoTestFactory();
		Boleto boleto = factory.buildBoleto();
		
		List<Boleto> boletosAhEnviar = new ArrayList<>();
		boletosAhEnviar.add(boleto);
		
		boletoService.save(boleto);
		
		assertThat(boleto.getIdIntegracao()).isEqualTo(null);
		
		List<Boleto> boletosEnviados = boletoService.incluir(boletosAhEnviar, CPF_CNPJ_CEDENTE);
		
		Boleto boleto1 = boletosEnviados.get(0);
		
		assertThat(boleto1.getIdIntegracao()).isNotNull();
		assertThat(boleto1.getSituacao()).isEqualTo(BoletoSituacao.SALVO);
		assertThat(boleto1.getId()).isEqualTo(boleto.getId());
		
	}
	
}
