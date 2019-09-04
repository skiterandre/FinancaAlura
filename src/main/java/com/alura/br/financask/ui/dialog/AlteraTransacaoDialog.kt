package com.alura.br.financask.ui.dialog

import android.content.Context
import android.view.ViewGroup
import com.alura.br.financask.R
import com.alura.br.financask.extension.formataParaBrasileiro
import com.alura.br.financask.model.Tipo
import com.alura.br.financask.model.Transacao

class AlteraTransacaoDialog(
    viewGroup: ViewGroup,
    private val context: Context
):FormularioTransacaoDialog(context,viewGroup) {

    override val tituloBotaoPositivo: String = "Alterar"

     fun chama(transacao:Transacao, delegate:(Transacao) -> Unit) {

         val tipo = transacao.tipo
        super.chama(tipo,delegate)

         inicializaCampos(transacao)
     }

    private fun inicializaCampos(
        transacao: Transacao
    ) {
        val tipo = transacao.tipo
        inicializaCampoValor(transacao)
        inicializaCampoData(transacao)
        inicializaCampoCategoria(tipo, transacao)
    }

    private fun inicializaCampoCategoria(
        tipo: Tipo,
        transacao: Transacao
    ) {
        //retorno array das categorias por tipo
        val categoriasRetornadas = context.resources.getStringArray(categoriaPor(tipo))

        val posicaoCategoria = categoriasRetornadas.indexOf(transacao.categoria)
        campoCategoria.setSelection(posicaoCategoria, true)
    }

    private fun inicializaCampoData(transacao: Transacao) {
        campoData.setText(transacao.data.formataParaBrasileiro())
    }

    private fun inicializaCampoValor(transacao: Transacao) {
        campoValor.setText(transacao.valor.toString())
    }

    override fun tituloPor(tipo: Tipo): Int {
        if (tipo == Tipo.RECEITA) {
            return R.string.altera_receita
        }
        return R.string.altera_despesa

    }


}