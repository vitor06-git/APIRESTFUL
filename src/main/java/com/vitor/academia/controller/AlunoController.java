package com.vitor.academia.controller;

import com.vitor.academia.entity.Aluno;
import com.vitor.academia.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aluno")
public class AlunoController {

    private final AlunoRepository alunoRepository;

    @Autowired
    public AlunoController(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    @GetMapping
    public ResponseEntity<List<Aluno>> getAllAlunos() {
        List<Aluno> alunos = alunoRepository.findAll();
        return ResponseEntity.ok(alunos);
    }

    @PostMapping
    public ResponseEntity<List<Aluno>> createAluno(@RequestBody Aluno aluno) {
        alunoRepository.save(aluno);
        List<Aluno> alunos = alunoRepository.findAll();
        return ResponseEntity.ok(alunos);
    }

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

