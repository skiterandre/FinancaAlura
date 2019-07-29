package com.alura.br.financask.ui.activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.alura.br.financask.R
import com.alura.br.financask.extension.formataParaBrasileiro
import com.alura.br.financask.model.Tipo
import com.alura.br.financask.model.Transacao
import com.alura.br.financask.ui.ResumoView
import com.alura.br.financask.ui.adapter.ListaTransacoesAdapter
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import kotlinx.android.synthetic.main.form_transacao.*
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.math.BigDecimal
import java.util.*

class ListaTransacoesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        val transacoes: List<Transacao> = transacoesDeExemplo()

        configuraResumo(transacoes)
        configuraLista(transacoes)

        lista_transacoes_adiciona_receita.setOnClickListener {

            val view: View = window.decorView
            val viewCriada = LayoutInflater.from(this)
                    .inflate(R.layout.form_transacao, view as ViewGroup,
                            false)


            val dia = 29
            val mes = 6
            val ano = 2019

            val hoje = Calendar.getInstance()

            viewCriada.form_transacao_data.setText(hoje.formataParaBrasileiro())

            viewCriada.form_transacao_data.setOnClickListener {
                DatePickerDialog(this,
                        DatePickerDialog.OnDateSetListener { datePicker, ano, mes, dia ->
                            val dataSelecionada = Calendar.getInstance()
                            dataSelecionada.set(ano, mes, dia)
                            viewCriada.form_transacao_data.setText(dataSelecionada.formataParaBrasileiro())

                        }
                , ano , mes, dia).show()

            }


            AlertDialog.Builder(this)
                    .setTitle(R.string.adiciona_receita)
                    .setView(viewCriada)
                    .show()
        }


        lista_transacoes_adiciona_despesa.setOnClickListener {
            Toast.makeText(this, "Adiciona despesa", Toast.LENGTH_LONG).show()
        }

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