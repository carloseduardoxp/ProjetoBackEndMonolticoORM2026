package br.edu.iftm.tspi.pbackorm.e_commerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.edu.iftm.tspi.pbackorm.e_commerce.domain.Produto;
import br.edu.iftm.tspi.pbackorm.e_commerce.dto.ProdutoDTO;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto,Integer> {

    public List<Produto> 
        findByNomeContainingIgnoreCaseAndEstoqueGreaterThanAndPrecoLessThan(
            String nome,Short estoqueMin, Double precoMax);

    @Query("""
              SELECT new br.edu.iftm.tspi.pbackorm.e_commerce.dto.ProdutoDTO(
                    p.id,p.nome,p.preco,p.estoque,p.caminhoImagem,p.categoria.id)
              FROM Produto p
              WHERE p.preco = (
                    SELECT MAX(p2.preco)
                    FROM Produto p2
                    WHERE p2.categoria.id = p.categoria.id
                    )      
            """)
    public List<ProdutoDTO> buscarProdutosComMaiorPrecoPorCategoria();


    @Query(nativeQuery = true, value = """
            Select p.produtoID,p.produtoNome,p.preco,
                   p.unidadesemestoque,p.imagem,p.categoriaID
            FROM Produtos p
            where p.preco = (
                    Select MAX(p2.preco)
                    From Produtos p2
                    Where p2.categoriaID = p.categoriaID
                    )
            """)
    public List<ProdutoDTO> buscarProdutosComMaiorPrecoPorCategoriaNativo();

}
