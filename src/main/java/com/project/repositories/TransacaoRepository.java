package com.project.repositories;

import com.project.models.Categoria;
import com.project.models.TipoTransacao;
import com.project.models.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long>{
    @Query("SELECT SUM(t.valor) FROM Transacao t WHERE t.tipo = :tipo AND t.conta.id = :contaId")
    BigDecimal somarPorTipoEConta(TipoTransacao tipo, Long contaId);

    List<Transacao> findByContaId(Long contaId);

    List<Transacao> findByCategoriaId(Long CategoriaId);

    List<Transacao> findByContaIdAndOrderByDataDesc(Long contaId);

    List<Transacao> findByContaIdAndCategoriaIdOrderByValorDesc(Long contaId, Long categoriaId);

    List<Transacao> findByContaIdAndValorGreaterThanEqual(Long contaId, BigDecimal valor);
}

