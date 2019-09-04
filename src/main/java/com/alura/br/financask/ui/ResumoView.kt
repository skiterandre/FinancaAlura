package com.alura.br.financask.ui

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import com.alura.br.financask.R
import com.alura.br.financask.extension.formataParaBrasileiro
import com.alura.br.financask.model.Resumo
import com.alura.br.financask.model.Transacao
import kotlinx.android.synthetic.main.resumo_card.view.*
import java.math.BigDecimal

class ResumoView(
    private val context: Context
    , private val view: View
    , transacoes: List<Transacao>
) {

    val resumo: Resumo = Resumo(transacoes)

    fun configuraResumo() {
        adicionaReceita()
        adicionaDespesa()
        adicionaTotal()
    }

    fun adicionaReceita() {
        val receita = resumo.receita

        view?.let{
            with(it.resumo_card_receita) {
                setTextColor(ContextCompat.getColor(context, R.color.colorReceita))
                text = receita.formataParaBrasileiro()
            }
        }
    }

    fun adicionaDespesa() {
        val despesa = resumo.despesa

        view?.let {
            with(it.resumo_card_despesa) {
                setTextColor(ContextCompat.getColor(context, R.color.colorDespesa))
                text = despesa.formataParaBrasileiro()
            }
        }
    }

    fun adicionaTotal() {

        if (view != null) {
            val total = resumo.total
            var color: Int = corPor(total)

            view?.let{
                with(it.resumo_card_total) {
                    setTextColor(color)
                    text = total.formataParaBrasileiro()
                }
            }
        }
    }

    private fun corPor(total: BigDecimal): Int {

        if (total >= BigDecimal.ZERO)
            return ContextCompat.getColor(context, R.color.colorReceita)

        return ContextCompat.getColor(context, R.color.colorDespesa)

    }

}