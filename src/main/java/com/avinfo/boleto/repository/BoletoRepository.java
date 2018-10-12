package com.avinfo.boleto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avinfo.boleto.domain.Boleto;

public interface BoletoRepository extends JpaRepository<Boleto, Long>{

}
