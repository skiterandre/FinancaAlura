package com.alura.br.financask.ui.dialog

import android.app.DatePickerDialog
import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.alura.br.financask.R
import com.alura.br.financask.delegate.TransacaoDelegate
import com.alura.br.financask.extension.converteParaCalendar
import com.alura.br.financask.extension.formataParaBrasileiro
import com.alura.br.financask.model.Tipo
import com.alura.br.financask.model.Transacao
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.math.BigDecimal
import java.util.*

class AlteraTransacaoDialog(
    private val viewGroup: ViewGroup,
    private val context: Context
) {

    private val viewCriada = criaLayout()
    private val campoData = viewCriada.form_transacao_data
    private val campoValor = viewCriada.form_transacao_valor
    private val campoCategoria = viewCriada.form_transacao_categoria


    fun chama(transacao:Transacao, transacaoDelegate: TransacaoDelegate) {

        val tipo = transacao.tipo

        configuraCampoData()
        configuraCampoCategoria(tipo)
        configuraFormulario(transacaoDelegate, tipo)


        campoValor.setText(transacao.valor.toString())
        campoData.setText(transacao.data.formataParaBrasileiro())
        //retorno array das categorias por tipo
        val categoriasRetornadas = context.resources.getStringArray(categoriaPor(tipo))

        val posicaoCategoria = categoriasRetornadas.indexOf(transacao.categoria)
        campoCategoria.setSelection(posicaoCategoria,true)
    }

    private fun configuraFormulario(transacaoDelegate: TransacaoDelegate, tipo: Tipo) {

        val titulo = tituloPor(tipo)

        AlertDialog.Builder(context)
            .setTitle(titulo)
            .setView(viewCriada)
            .setPositiveButton("Alterar") { _, _ ->

                val valorEmTexto = campoValor.text.toString()
                val dataEmTexto = campoData.text.toString()
                val categoriaEmTexto = campoCategoria.selectedItem.toString()


                val valor = convertCampoValor(valorEmTexto)
                val data = dataEmTexto.converteParaCalendar()
                val transacaoCriada = Transacao(
                    tipo = tipo,
                    valor = valor,
                    categoria = categoriaEmTexto,
                    data = data
                )

                transacaoDelegate.delegate(transacaoCriada)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun tituloPor(tipo: Tipo): Int {
        if (tipo == Tipo.RECEITA) {
            return R.string.altera_receita
        }

        return R.string.altera_despesa

    }


    private fun convertCampoValor(valorEmTexto: String): BigDecimal {
        return try {
            BigDecimal(valorEmTexto)
        } catch (ex: NumberFormatException) {
            Toast.makeText(
                context, "Valor informado inválido",
                Toast.LENGTH_LONG
            )
                .show()
            BigDecimal.ZERO
        }

    }

    private fun configuraCampoCategoria(tipo: Tipo) {

        val categorias = categoriaPor(tipo)

        val adapter = ArrayAdapter.createFromResource(
            context,
            categorias,
            android.R.layout.simple_spinner_dropdown_item
        )

        campoCategoria.adapter = adapter
    }

    private fun categoriaPor(tipo: Tipo): Int {

        if (tipo == Tipo.RECEITA) {
            return R.array.categorias_de_receita
        }
        return R.array.categorias_de_despesa

    }

    private fun configuraCampoData() {

        val hoje = Calendar.getInstance()

        val dia = hoje.get(Calendar.DAY_OF_MONTH)
        val mes = hoje.get(Calendar.MONTH)
        val ano = hoje.get(Calendar.YEAR)

        campoData.setText(hoje.formataParaBrasileiro())

        campoData.setOnClickListener {
            DatePickerDialog(context,
                DatePickerDialog.OnDateSetListener { _, ano, mes, dia ->
                    val dataSelecionada = Calendar.getInstance()
                    dataSelecionada.set(ano, mes, dia)
                    campoData.setText(dataSelecionada.formataParaBrasileiro())

                }
                , ano, mes, dia).show()

        }
    }

    private fun criaLayout() = LayoutInflater.from(context)
        .inflate(
            R.layout.form_transacao,
            viewGroup,
            false
        )

}