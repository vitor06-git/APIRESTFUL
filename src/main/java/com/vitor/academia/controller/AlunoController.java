package com.vitor.academia.controller;

import java.util.List;

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

import com.vitor.academia.entity.Aluno;
import com.vitor.academia.repository.AlunoRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/aluno")
@Tag(name = "Aluno", description = "Endpoints para gerenciar alunos")
public class AlunoController {

    private final AlunoRepository alunoRepository;

    @Autowired
    public AlunoController(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    @Operation(summary = "Lista todos os alunos")
    @GetMapping
    public ResponseEntity<List<Aluno>> getAllAlunos() {
        List<Aluno> alunos = alunoRepository.findAll();
        return ResponseEntity.ok(alunos);
    }

    @Operation(summary = "Cria um novo aluno")
    @PostMapping
    public ResponseEntity<Aluno> createAluno(@RequestBody Aluno aluno) {
        aluno.setId(null);
        
        Aluno novoAluno = alunoRepository.save(aluno);
        
        return ResponseEntity.ok(novoAluno);
    }


    @Operation(summary = "Atualiza um aluno existente")
    @PutMapping("/{id}")
    public ResponseEntity<List<Aluno>> updateAluno(@PathVariable Long id, @RequestBody Aluno updatedAluno) {
        return alunoRepository.findById(id)
                .map(aluno -> {
                    aluno.setNome(updatedAluno.getNome());
                    aluno.setIdade(updatedAluno.getIdade());
                    aluno.setFaixa(updatedAluno.getFaixa());
                    aluno.setGraus(updatedAluno.getGraus());
                    alunoRepository.save(aluno);
                    List<Aluno> alunos = alunoRepository.findAll();
                    return ResponseEntity.ok(alunos);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Deleta um aluno pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<List<Aluno>> deleteAluno(@PathVariable Long id) {
        if (alunoRepository.existsById(id)) {
            alunoRepository.deleteById(id);
            List<Aluno> alunos = alunoRepository.findAll();
            return ResponseEntity.ok(alunos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


