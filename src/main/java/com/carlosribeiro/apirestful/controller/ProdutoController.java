package com.carlosribeiro.apirestful.controller;

import com.carlosribeiro.apirestful.model.Produto;
import com.carlosribeiro.apirestful.model.ProdutoDTO;
import com.carlosribeiro.apirestful.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor
@RestController
@RequestMapping("produtos")   // htttp://localhost:8080/produtos
public class ProdutoController {

    private final ProdutoService produtoService;

//    public ProdutoController(ProdutoService produtoService) {
//        this.produtoService = produtoService;
//    }

    @GetMapping
    public List<Produto> recuperarProdutos() {
        return produtoService.recuperarProdutos();
    }

    @PostMapping
    public Produto cadastrarProduto(@RequestBody Produto produto) {
        return produtoService.cadastrarProduto(produto);
    }

    @PutMapping
    public Produto alterarProduto(@RequestBody Produto produto) {
        return produtoService.alterarProduto(produto);
    }

//    Implementação retornando um objeto Produto
//    // http://localhost:8080/produtos/1
//    @GetMapping("{idProduto}")
//    public Produto recuperarProdutoPorId(@PathVariable("idProduto") Long id) {
//        return produtoService.recuperarProdutoPorId(id);
//    }

//    Implementação retornando um ResponseEntity<Produto>
//    // http://localhost:8080/produtos/1
//    @GetMapping("{idProduto}")
//    public ResponseEntity<Produto> recuperarProdutoPorId(@PathVariable("idProduto") Long id) {
//        Produto produto = produtoService.recuperarProdutoPorId(id);
//        return new ResponseEntity<>(produto, HttpStatus.OK);
//    }

    // Implementação utilizando o método estático ok() que retorna um ResponseEntity
    // http://localhost:8080/produtos/1
    @GetMapping("{idProduto}/categoria")
    public ResponseEntity<Produto> recuperarProdutoPorIdComCategoria(@PathVariable("idProduto") Long id) {
        Produto produto = produtoService.recuperarProdutoPorIdComCategoria(id);
        return ResponseEntity.ok(produto);
    }

    @GetMapping("{idProduto}")
    public ResponseEntity<ProdutoDTO> recuperarProdutoPorIdSemCategoria(@PathVariable("idProduto") Long id) {
        Produto produto = produtoService.recuperarProdutoPorIdSemCategoria(id);
        ProdutoDTO produtoDTO = new ProdutoDTO(produto.getId(), produto.getNome());
        System.out.println(produtoDTO.id());
        return ResponseEntity.ok(produtoDTO);
    }

//    Implementação que retorna um Produto ou um String (mensagem de errro) sem utilizar
//    um GlobalExceptionHandler
//    // Get para http://localhost:8080/produtos/1
//    @GetMapping("{idProduto}")
//    public ResponseEntity<?> recuperarProdutoPorId(@PathVariable("idProduto") Long id) {
//        try {
//            Produto produto = produtoService.recuperarProdutoPorId(id);
//            return ResponseEntity
//                .ok()
//                .header("Content-Type", "application/json")
//                .body(produto);
//        }
//        catch(EntidadeNaoEncontradaException e) {
//            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
//        }
//    }

//    // Delete para http://localhost:8080/produtos/1
//    @DeleteMapping("{idProduto}")
//    public void removerProdutoPorId(@PathVariable("idProduto") Long id) {
//        produtoService.removerProdutoPorId(id);
//    }

    // Implementação utilizando um builder
    // Delete para http://localhost:8080/produtos/1
    @DeleteMapping("{idProduto}")
    public ResponseEntity<Void> removerProdutoPorId(@PathVariable("idProduto") Long id) {
        produtoService.removerProdutoPorId(id);
        return ResponseEntity.ok().build();
    }
}











