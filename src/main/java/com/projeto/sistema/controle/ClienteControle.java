package com.projeto.sistema.controle;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.projeto.sistema.modelos.Cliente;
import com.projeto.sistema.repositorios.CidadeRepositorio;
import com.projeto.sistema.repositorios.ClienteRepositorio;

@Controller
public class ClienteControle {
	
	@Autowired
	private ClienteRepositorio clienteRepositorio;
	@Autowired
	private	CidadeRepositorio cidadeRepositorio;
	
	@GetMapping("/cadastro-cliente")
	public ModelAndView cadastrar(Cliente cliente) {
		ModelAndView mv = new ModelAndView("administrativo/cliente/cadastro");
		mv.addObject("cliente", cliente);
		mv.addObject("listaCidades", cidadeRepositorio.findAll());
		return mv;
	}
	
	@GetMapping("/listar-cliente")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("administrativo/cliente/lista");
		return mv.addObject("listarClientes", clienteRepositorio.findAll());
	}
	
	@PostMapping("/salvar-cliente")
	public ModelAndView salvar(Cliente cliente, BindingResult result) {
		if (result.hasErrors()) {
			return cadastrar(cliente);
		}
		clienteRepositorio.saveAndFlush(cliente);
		return cadastrar(new Cliente());
	}
	
	@GetMapping("/editar-cliente/{id}")
	public ModelAndView editar(@PathVariable("id") Long id) {
		Optional<Cliente> cliente = clienteRepositorio.findById(id);
		return cadastrar(cliente.get());
	}
	
	@GetMapping("/remover-cliente/{id}")
	public ModelAndView remover(@PathVariable("id") Long id) {
		Optional<Cliente> cliente = clienteRepositorio.findById(id);
		clienteRepositorio.delete(cliente.get());
		return listar();
	}
}
