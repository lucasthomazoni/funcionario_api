package br.com.ciss.entrevista.controller;

import br.com.ciss.entrevista.exception.ResourceNotFoundException;
import br.com.ciss.entrevista.model.Funcionario;
import br.com.ciss.entrevista.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @GetMapping("/users")
    public List<Funcionario> getAllUsers() {
        return funcionarioRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Funcionario> getFuncionariosById(@PathVariable(value = "id") Long funcionarioId)
            throws ResourceNotFoundException {
        Funcionario funcionario =
                funcionarioRepository
                        .findById(funcionarioId)
                        .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado o funcionário com id: " + funcionarioId));
        return ResponseEntity.ok().body(funcionario);
    }

    @PostMapping("/funcionarios")
    public Funcionario createFuncionario(@Valid @RequestBody Funcionario funcionario) {
        return funcionarioRepository.save(funcionario);
    }

    @PutMapping("/funcionarios/{id}")
    public ResponseEntity<Funcionario> updateFuncionario(
            @PathVariable(value = "id") Long funcionarioId, @Valid @RequestBody Funcionario funcionarioDetails)
            throws ResourceNotFoundException {

        Funcionario funcionario =
                funcionarioRepository
                        .findById(funcionarioId)
                        .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado o funcionário com id: " + funcionarioId));

        funcionario.setEmail(funcionario.getEmail());
        funcionario.setSobrenome(funcionarioDetails.getSobrenome());
        funcionario.setNome(funcionarioDetails.getNome());
        funcionario.setUpdatedAt(new Date());
        final Funcionario updatedFuncionario = funcionarioRepository.save(funcionario);
        return ResponseEntity.ok(updatedFuncionario);
    }

    @DeleteMapping("/funcionario/{id}")
    public Map<String, Boolean> deleteFuncionario(@PathVariable(value = "id") Long funcionarioId) throws Exception {
        Funcionario funcionario =
                funcionarioRepository
                        .findById(funcionarioId)
                        .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado o funcionário com id: " + funcionarioId));

        funcionarioRepository.delete(funcionario);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Excluído", Boolean.TRUE);
        return response;
    }
}