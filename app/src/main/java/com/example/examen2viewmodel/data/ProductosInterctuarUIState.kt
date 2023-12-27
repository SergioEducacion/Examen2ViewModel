package com.example.examen2viewmodel.data

data class ProductosInterctuarUIState(
    val productos: List<Producto>,
    val textoAbajo: String = "Todavía no han añadido ningun valor",
)