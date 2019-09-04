package com.alura.br.financask.ui.dialog

import android.content.Context
import android.view.ViewGroup
import com.alura.br.financask.R
import com.alura.br.financask.model.Tipo

class AdicionaTransacaoDialog(
    viewGroup: ViewGroup,
    context: Context
) : FormularioTransacaoDialog(context,viewGroup) {

    override val tituloBotaoPositivo: String = "Adicionar"

    override fun tituloPor(tipo: Tipo): Int {
        if (tipo == Tipo.RECEITA) {
            return R.string.adiciona_receita
        }
        return R.string.adiciona_despesa
    }
}