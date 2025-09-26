package com.fontineleantunes.apirestful.service;

import com.fontineleantunes.apirestful.exception.EntidadeNaoEncontradaException;
import com.fontineleantunes.apirestful.model.Produto;
import com.fontineleantunes.apirestful.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

//    public ProdutoService(ProdutoRepository produtoRepository) {
//        this.produtoRepository = produtoRepository;
//    }

    public List<Produto> recuperarProdutos() {
        return produtoRepository.recuperarProdutos();
    }

    public Produto cadastrarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    @Transactional
    public Produto alterarProduto(Produto produto) {
        // Este método precisa ser transacional uma vez que o método abaixo
        // efetua um lock na linha da tabela referente ao produto recuperado.
        // Observe que o método recuperarProdutoPorIdComLock retorna um
        // Optional<Produto>
        produtoRepository.recuperarProdutoPorIdComLock(produto.getId())
            .orElseThrow(() -> new EntidadeNaoEncontradaException(
                "Produto com id = " + produto.getId() + " não encontrado."));
        return produtoRepository.save(produto);
    }

    public Produto recuperarProdutoPorIdComCategoria(Long id) {
        // findById() retorna um Optional<T>
        return produtoRepository.recuperarProdutoPorIdComCategoria(id)
            .orElseThrow(() -> new EntidadeNaoEncontradaException(
                "Produto com id = " + id + " não encontrado."));
    }


    public Produto recuperarProdutoPorIdSemCategoria(Long id) {
        return produtoRepository.recuperarProdutoPorIdSemCategoria(id)
            .orElseThrow(() -> new EntidadeNaoEncontradaException(
                "Produto com id = " + id + " não encontrado."));
    }

    public void removerProdutoPorId(Long id) {
        // recuperarProdutoPorId(id);
        produtoRepository.deleteById(id);
    }
}
