package com.alura.br.financask.delegate

import com.alura.br.financask.model.Transacao

interface TransacaoDelegate {

    fun delegate(transacao:Transacao)
}