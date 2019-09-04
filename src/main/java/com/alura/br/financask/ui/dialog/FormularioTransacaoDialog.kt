package com.alura.br.financask.ui.dialog

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.alura.br.financask.R
import com.alura.br.financask.extension.converteParaCalendar
import com.alura.br.financask.extension.formataParaBrasileiro
import com.alura.br.financask.model.Tipo
import com.alura.br.financask.model.Transacao
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.math.BigDecimal
import java.util.*

abstract class FormularioTransacaoDialog(private val context: Context,
                                     private val viewGroup: ViewGroup) {

    private val viewCriada = criaLayout()
    protected val campoData = viewCriada.form_transacao_data
    protected val campoValor = viewCriada.form_transacao_valor
    protected val campoCategoria = viewCriada.form_transacao_categoria

    protected abstract fun tituloPor(tipo: Tipo): Int
    protected abstract val tituloBotaoPositivo:String

    fun chama(tipo: Tipo, delegate: (transacao :Transacao) -> Unit) {

        configuraCampoData()
        configuraCampoCategoria(tipo)
        configuraFormulario( tipo, delegate)
    }



    private fun configuraFormulario(tipo: Tipo,delegate: (transacao: Transacao) -> Unit) {

        val titulo = tituloPor(tipo)

        AlertDialog.Builder(context)
            .setTitle(titulo)
            .setView(viewCriada)
            .setPositiveButton(tituloBotaoPositivo) { _, _ ->

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

                delegate(transacaoCriada)
            }
            .setNegativeButton("Cancelar", null)
            .show()
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

    fun categoriaPor(tipo: Tipo): Int {

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