package com.mitocode.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mitocode.dto.ConsultaListaExamenDTO;
import com.mitocode.dto.ConsultaResumenDTO;
import com.mitocode.dto.FiltroConsultaDTO;
import com.mitocode.exception.ModeloNotFoundException;
import com.mitocode.model.Consulta;
import com.mitocode.service.IConsultaService;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {
	
	@Autowired
	private IConsultaService service;
	
	@GetMapping
	public ResponseEntity<List<Consulta>> listar() throws Exception{
		List<Consulta> lista = service.listar();
		return new ResponseEntity<List<Consulta>>(lista, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Consulta> listarPorId(@PathVariable("id") Integer id) throws Exception{
		Consulta obj = service.listarPorId(id);
		if(obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO: " + id);
		}
		return new ResponseEntity<Consulta>(obj, HttpStatus.OK);
	}
	
//	@PostMapping
//	public ResponseEntity<Consulta> registrar(@Valid @RequestBody Consulta Consulta) {
//		Consulta obj = service.registrar(Consulta);
//		return new ResponseEntity<Consulta>(obj, HttpStatus.CREATED);
//	}
	
	@PostMapping
	public ResponseEntity<Void> registrar(@Valid @RequestBody ConsultaListaExamenDTO dto) throws Exception{
		Consulta obj = service.registrarTransaccional(dto);
		
		//localhost:8080/Consultas/7
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdConsulta()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping
	public ResponseEntity<Consulta> modificar(@Valid @RequestBody Consulta Consulta) throws Exception{
		Consulta obj = service.modificar(Consulta);
		return new ResponseEntity<Consulta>(obj, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception{
		Consulta obj = service.listarPorId(id);
		if(obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO: " + id);
		}
		service.eliminar(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping("/buscar")
	public ResponseEntity<List<Consulta>> buscar(@RequestBody FiltroConsultaDTO filtro) {
		
		List<Consulta> consultas = new ArrayList<>();
		
		if (filtro != null) {
			if (filtro.getFechaConsulta() != null) {
				consultas = service.buscarFecha(filtro);
			} else {
				consultas = service.buscar(filtro);
			}
		}
		
		return new ResponseEntity<List<Consulta>>(consultas, HttpStatus.OK);
	}
	
	@GetMapping(value = "/listarResumen")
	public ResponseEntity<List<ConsultaResumenDTO>> listarResumen() {
		List<ConsultaResumenDTO> consultas = new ArrayList<>();
		consultas = service.listarResumen();
		return new ResponseEntity<List<ConsultaResumenDTO>>(consultas, HttpStatus.OK);
	}
}
