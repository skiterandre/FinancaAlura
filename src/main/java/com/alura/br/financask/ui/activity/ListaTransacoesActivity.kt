package com.alura.br.financask.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import com.alura.br.financask.R
import com.alura.br.financask.delegate.TransacaoDelegate
import com.alura.br.financask.model.Tipo
import com.alura.br.financask.model.Transacao
import com.alura.br.financask.ui.ResumoView
import com.alura.br.financask.ui.adapter.ListaTransacoesAdapter
import com.alura.br.financask.ui.dialog.AdicionaTransacaoDialog
import com.alura.br.financask.ui.dialog.AlteraTransacaoDialog
import kotlinx.android.synthetic.main.activity_lista_transacoes.*

class ListaTransacoesActivity : AppCompatActivity() {

    private var transacoes: MutableList<Transacao> = mutableListOf()
    private val viewDaActivity: View by lazy {
        window.decorView
    }

    private val viewGroupDaActivity by lazy {
        viewDaActivity as ViewGroup
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        configuraResumo()
        configuraLista()
        configuraFloadButtom()

    }

    private fun configuraFloadButtom() {
        lista_transacoes_adiciona_receita
            .setOnClickListener {
                chamaDialogDeAdicao(Tipo.RECEITA)
            }

        lista_transacoes_adiciona_despesa
            .setOnClickListener {
                chamaDialogDeAdicao(Tipo.DESPESA)
            }
    }


    private fun adiciona(transacao: Transacao) {
        transacoes.add(transacao)
        atualizaTransacoes()
    }

    fun atualizaTransacoes() {
        configuraLista()
        configuraResumo()
    }


    private fun configuraResumo() {
        ResumoView(this, viewDaActivity, transacoes).configuraResumo()
    }

    private fun configuraLista() {
        val listaTransacoesAdapter = ListaTransacoesAdapter(transacoes, this)

        with(lista_transacoes_listview) {
            adapter = listaTransacoesAdapter
            //configura alteração da lista
            setOnItemClickListener { _, _, posicao, _ ->
                val transacao = transacoes[posicao]
                chamaDialogDeAlteracao(transacao, posicao)
            }
        }
    }

    private fun chamaDialogDeAdicao(tipo: Tipo) {
        AdicionaTransacaoDialog(viewGroupDaActivity, this)
            .chama(tipo
                , transacaoDelegate = object : TransacaoDelegate {
                    override fun delegate(transacao: Transacao) {
                        adiciona(transacao)
                        lista_transacoes_adiciona_menu.close(true)
                    }
                })
    }

    private fun chamaDialogDeAlteracao(
        transacao: Transacao,
        posicao: Int
    ) {
        AlteraTransacaoDialog(viewGroupDaActivity, this).chama(
            transacao,
            object : TransacaoDelegate {
                override fun delegate(transacao: Transacao) {
                    altera(transacao, posicao)
                }

            })
    }

    private fun altera(transacao: Transacao, posicao: Int) {
        transacoes[posicao] = transacao
        atualizaTransacoes()
    }

}