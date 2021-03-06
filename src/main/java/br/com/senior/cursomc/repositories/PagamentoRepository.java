package br.com.senior.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.senior.cursomc.domain.Categoria;
import br.com.senior.cursomc.domain.Pagamento;
import br.com.senior.cursomc.domain.Produto;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer>{

}
