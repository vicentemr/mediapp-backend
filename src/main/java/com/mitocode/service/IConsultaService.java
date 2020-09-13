package com.mitocode.service;

import com.mitocode.dto.ConsultaListaExamenDTO;
import com.mitocode.model.Consulta;

public interface IConsultaService extends ICRUD<Consulta, Integer>{
	
	Consulta registrarTransaccional(ConsultaListaExamenDTO dto) throws Exception;
}