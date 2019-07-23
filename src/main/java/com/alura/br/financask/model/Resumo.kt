package com.alura.br.financask.model

import java.math.BigDecimal

class Resumo(val transacoes:List<Transacao>) {

    val despesa get() = somarPor(Tipo.DESPESA)

    val receita get() = somarPor(Tipo.RECEITA)

    val total get() = receita.subtract(despesa)

    private fun somarPor(tipo: Tipo):BigDecimal{
        var somaTransacoesPorTipo = transacoes
                                .filter{ it.tipo == tipo}
                                .sumByDouble { it.valor.toDouble() }

        return  BigDecimal(somaTransacoesPorTipo)
    }
}