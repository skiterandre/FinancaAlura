package com.alura.br.financask.extension

import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.Locale

fun BigDecimal.formataParaBrasileiro():String {
    val currencyInstance = DecimalFormat.getCurrencyInstance(Locale("pt", "BR"))
    return currencyInstance.format(this).replace("-R\$ ", "R$ -")

}
