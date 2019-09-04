package com.alura.br.financask.extension

import java.text.SimpleDateFormat
import java.util.*

fun String.limitaEmAte(caracteres: Int): String{

    if(this.length > caracteres){
        val primeiroCaracter = 0
        return "${this.substring(primeiroCaracter,caracteres)}..."
    }
    return this
}

fun String.converteParaCalendar(): Calendar {
    val dateformat = SimpleDateFormat("dd/MM/yyyy")
    val dataFormadada = dateformat.parse(this)
    val data = Calendar.getInstance()
    data.time = dataFormadada
    return data
}
