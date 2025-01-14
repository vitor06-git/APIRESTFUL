package com.vitor.academia.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vitor.academia.entity.Aluno;
import com.vitor.academia.repository.AlunoRepository;

@Service
public class AlunoService {
	
	private AlunoRepository alunoRepository;
	
	public List<Aluno> listarTodos(){
		return alunoRepository.findAll();
	}
	

	public Aluno buscarPorId(Long id) {
		return alunoRepository.findById(id).orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
	}
	
	public Aluno salvar(Aluno aluno) {
		return alunoRepository.save(aluno);
	}
	
	public Aluno atualizar(Long id, Aluno alunoAtualizado) {
		Aluno alunoExistente = buscarPorId(id);
		alunoExistente.setNome(alunoAtualizado.getNome());
		alunoExistente.setIdade(alunoAtualizado.getIdade());
		alunoExistente.setFaixa(alunoAtualizado.getFaixa());
		alunoExistente.setGraus(alunoAtualizado.getGraus());
		return alunoRepository.save(alunoExistente);
	}
	
	public void deletar(Long id) {
		Aluno aluno = buscarPorId(id);
		alunoRepository.delete(aluno);
	}
}
