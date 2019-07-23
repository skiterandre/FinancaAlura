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

class ResumoView(private val context: Context
                 ,private val view: View
                 , transacoes: List<Transacao>){

    val resumo:Resumo = Resumo(transacoes)

    fun configuraResumo(){
        adicionaReceita()
        adicionaDespesa()
        adicionaTotal()
    }

    fun adicionaReceita(){
        val receita = resumo.receita
        view.resumo_card_receita.setTextColor(ContextCompat.getColor(context, R.color.colorReceita))
        view.resumo_card_receita.text = receita.formataParaBrasileiro()
    }

    fun adicionaDespesa(){
        val despesa = resumo.despesa
        view.resumo_card_despesa.setTextColor(ContextCompat.getColor(context, R.color.colorDespesa))
        view.resumo_card_despesa.text = despesa.formataParaBrasileiro()
    }

    fun adicionaTotal(){
        val total = resumo.total
        var color:Int
        if(total >= BigDecimal.ZERO)
            color = ContextCompat.getColor(context, R.color.colorReceita)
        else
            color = ContextCompat.getColor(context, R.color.colorDespesa)

        view.resumo_card_total.setTextColor(color)
        view.resumo_card_total.text = total.formataParaBrasileiro()
    }

}