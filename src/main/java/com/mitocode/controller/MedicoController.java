package com.mitocode.controller;

import java.net.URI;
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

import com.mitocode.exception.ModeloNotFoundException;
import com.mitocode.model.Medico;
import com.mitocode.service.IMedicoService;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
	
	@Autowired
	private IMedicoService service;
	
	@GetMapping
	public ResponseEntity<List<Medico>> listar() throws Exception{
		List<Medico> lista = service.listar();
		return new ResponseEntity<List<Medico>>(lista, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Medico> listarPorId(@PathVariable("id") Integer id) throws Exception{
		Medico obj = service.listarPorId(id);
		if(obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO: " + id);
		}
		return new ResponseEntity<Medico>(obj, HttpStatus.OK);
	}
	
//	@PostMapping
//	public ResponseEntity<Medico> registrar(@Valid @RequestBody Medico Medico) {
//		Medico obj = service.registrar(Medico);
//		return new ResponseEntity<Medico>(obj, HttpStatus.CREATED);
//	}
	
	@PostMapping
	public ResponseEntity<Void> registrar(@Valid @RequestBody Medico Medico) throws Exception{
		Medico obj = service.registrar(Medico);
		
		//localhost:8080/Medicos/7
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdMedico()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping
	public ResponseEntity<Medico> modificar(@Valid @RequestBody Medico Medico) throws Exception{
		Medico obj = service.modificar(Medico);
		return new ResponseEntity<Medico>(obj, HttpStatus.OK);
	}
	
	@DeleteMapping("/id")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception{
		Medico obj = service.listarPorId(id);
		if(obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO: " + id);
		}
		service.eliminar(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}