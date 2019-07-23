package com.alura.br.financask.ui.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.alura.br.financask.R
import com.alura.br.financask.extension.formataParaBrasileiro
import com.alura.br.financask.extension.limitaEmAte
import com.alura.br.financask.model.Tipo
import com.alura.br.financask.model.Transacao
import kotlinx.android.synthetic.main.transacao_item.view.*


class ListaTransacoesAdapter(private val transacoes: List<Transacao>,
                             private val context: Context): BaseAdapter() {

    private val limiteDaCategoria = 14

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val viewCriada = LayoutInflater.from(context).inflate(R.layout.transacao_item, parent, false)

        val transacao = transacoes[position]

        adicionaValor(transacao, viewCriada)
        adicionaIcone(transacao, viewCriada)
        adicionaCategoria(viewCriada, transacao)
        adicionaData(viewCriada, transacao)

        return viewCriada

    }

    private fun adicionaData(viewCriada: View, transacao: Transacao) {
        viewCriada.transacao_data.text = transacao.data.formataParaBrasileiro()
    }

    private fun adicionaCategoria(viewCriada: View, transacao: Transacao) {
        viewCriada.transacao_categoria.text = transacao.categoria.limitaEmAte(limiteDaCategoria)
    }

    private fun adicionaIcone(transacao: Transacao, viewCriada: View) {

        return viewCriada.transacao_icone.setBackgroundResource(iconePor(transacao.tipo))
    }

    private fun iconePor(tipo: Tipo): Int {
        if (tipo == Tipo.RECEITA)
            return R.drawable.icone_transacao_item_receita

        return R.drawable.icone_transacao_item_despesa
    }

    private fun adicionaValor(transacao: Transacao, viewCriada: View) {

        viewCriada.transacao_valor.setTextColor(ContextCompat.getColor(context, corPor(transacao.tipo)))
        viewCriada.transacao_valor.text = transacao.valor.formataParaBrasileiro()
    }

    private fun corPor(tipo: Tipo): Int {
        if (tipo == Tipo.RECEITA)
            return R.color.colorReceita

        return R.color.colorDespesa
    }

    override fun getItem(position: Int): Transacao {
        return transacoes[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return transacoes.size

    }



}