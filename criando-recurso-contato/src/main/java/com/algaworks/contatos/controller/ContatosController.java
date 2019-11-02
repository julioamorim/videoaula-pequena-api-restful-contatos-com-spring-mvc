package com.algaworks.contatos.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.contatos.model.Contato;
import com.algaworks.contatos.repository.Contatos;

@RestController
//path  inicial será contatos
@RequestMapping("/contatos")
public class ContatosController {

	// Spring irá fazer a injeção de dependencias automaticamente
	@Autowired
	private Contatos contatos;

	// @PostMapping qualdo alguem fizer uma requisiçaõ so tipo post para contatos,
	// estet método
	// será chamado
	// @RequestBody O que estiver vindo da requisição, deverá ser jogado neste
	// objeto
	// @Valid O método só será executado se passar nas bvalidações do Bean
	@PostMapping
	public Contato adicionar(@Valid @RequestBody Contato contato) {
		return contatos.save(contato);
	}

	@GetMapping
	public List<Contato> listar() {
		return contatos.findAll();
	}

	@GetMapping("/{id}")
	// ResponseEntity -
	// PathVariable - espera uma variável identica a do path URI
	public ResponseEntity<Contato> buscar(@PathVariable Long id) {
		Contato contato = contatos.findOne(id);

		if (contato == null) {
			// ResponseEntity.notFound retorna erro 404
			return ResponseEntity.notFound().build();
		}

		// retorna response OK, cód 200
		return ResponseEntity.ok(contato);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Contato> atualizar(@PathVariable Long id, @Valid @RequestBody Contato contato) {
		Contato existente = contatos.findOne(id);

		if (existente == null) {
			return ResponseEntity.notFound().build();
		}

		BeanUtils.copyProperties(contato, existente, "id");

		existente = contatos.save(existente);

		return ResponseEntity.ok(existente);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		Contato contato = contatos.findOne(id);

		if (contato == null) {
			return ResponseEntity.notFound().build();
		}

		contatos.delete(contato);
		// noContent - retorna 200 porém sem conteúdo
		return ResponseEntity.noContent().build();
	}
}
