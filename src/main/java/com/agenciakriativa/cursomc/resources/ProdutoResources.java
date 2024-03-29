package com.agenciakriativa.cursomc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agenciakriativa.cursomc.domain.Produto;
import com.agenciakriativa.cursomc.dto.ProdutoDTO;
import com.agenciakriativa.cursomc.resources.utils.Urls;
import com.agenciakriativa.cursomc.services.ProdutoService;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResources {
		
	@Autowired
	private ProdutoService service;
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Produto> find(@PathVariable Integer id) {
		
			Produto obj = service.find(id);
			return ResponseEntity.ok().body(obj);	

	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO> > findPage( 
			@RequestParam(value="nome", defaultValue = "") String nome,
			@RequestParam(value="categorias", defaultValue = "") String caregorias,
			@RequestParam(value="page", defaultValue = "0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue = "nome") String orderBy, 
			@RequestParam(value="direction", defaultValue = "ASC") String direction) {
		
			String nomeDecoded = Urls.decodeParam(nome);
			List<Integer> ids = Urls.decodeIntList(caregorias);
		
			Page<Produto> list = service.search(nomeDecoded,ids, page, linesPerPage, orderBy, direction);
			
			Page<ProdutoDTO> listDto = list.map(obj -> new ProdutoDTO(obj));
			
						
			return ResponseEntity.ok().body(listDto);	

	}

}
