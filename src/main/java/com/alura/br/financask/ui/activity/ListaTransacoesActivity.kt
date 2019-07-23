package com.alura.br.financask.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.alura.br.financask.R
import com.alura.br.financask.model.Tipo
import com.alura.br.financask.model.Transacao
import com.alura.br.financask.ui.ResumoView
import com.alura.br.financask.ui.adapter.ListaTransacoesAdapter
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import java.math.BigDecimal
import java.util.*

class ListaTransacoesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        val transacoes: List<Transacao> = transacoesDeExemplo()

        configuraResumo(transacoes)
        configuraLista(transacoes)
    }

    private fun configuraResumo(transacoes: List<Transacao>){
        val decorView = window.decorView
        ResumoView(this,decorView, transacoes).configuraResumo()
    }

    private fun configuraLista(transacoes: List<Transacao>) {
        lista_transacoes_listview.adapter = ListaTransacoesAdapter(transacoes, this)
    }

    private fun transacoesDeExemplo(): List<Transacao> {
        return listOf(Transacao(valor = BigDecimal(20.5), tipo = Tipo.DESPESA, data = Calendar.getInstance()),
                Transacao(valor = BigDecimal(1000), categoria = "Economia", tipo = Tipo.RECEITA),
                Transacao(valor = BigDecimal(2000.50), categoria = "Carro", tipo = Tipo.DESPESA),
                Transacao(valor = BigDecimal(200.50), categoria = "Almoço de final de semana", tipo = Tipo.DESPESA))
    }
}