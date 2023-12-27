package com.example.examen2viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.examen2viewmodel.data.DataSource
import com.example.examen2viewmodel.data.Producto
import com.example.examen2viewmodel.data.ProductosInterctuarUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProductosInteracturViewModel : ViewModel() {
    private val productos = DataSource.productos;
    private val _uiState = MutableStateFlow(ProductosInterctuarUIState(productos))
    val uiState: StateFlow<ProductosInterctuarUIState> = _uiState.asStateFlow()
    var nombreProductoUsuario by mutableStateOf("")
            private set;
    var cantidadProductoUsuario by mutableStateOf("")
            private set;




    fun setNombreProducto(nombreProductoEscrito: String) {
        nombreProductoUsuario = nombreProductoEscrito

    }

    fun setPrecioProducto(cantidadProductoEscrito: String) {
        cantidadProductoUsuario=cantidadProductoEscrito
    }

    fun intentoModificarListaProductos() {
        var textoAbajo: String //No lo cogemos del uiState porque no es necesario para nada
        val nombreProductoEscrito: String = nombreProductoUsuario
        val cantidadProductoEscrito: String = cantidadProductoUsuario


        val precioNuevo: Int
        try {
            precioNuevo = cantidadProductoEscrito.toInt()
        } catch (e: Exception) {
            textoAbajo =
                "El precio introducido no es un número entero"
            cambiarSoloTextoAbajoDeUIState(textoAbajo)
            return
        }


        for (producto in productos) {
            if (producto.nombre.equals(nombreProductoEscrito) && producto.precio == precioNuevo) {

                textoAbajo =
                    "NO se ha modificado nada del producto ${producto.nombre}, el precio es el mismo"
                cambiarSoloTextoAbajoDeUIState(textoAbajo)
                return
            }
            if (producto.nombre.equals(nombreProductoEscrito) && producto.precio != precioNuevo) {

                textoAbajo =
                    "Del producto ${producto.nombre} se ha modificado el precio de: ${producto.precio} euros a ${precioNuevo} euros"
                producto.precio = precioNuevo
                cambiarSoloTextoAbajoDeUIState(textoAbajo)
                return
            }
        }
        //Solo llega aquí si no hay ningun producto con el mismo nombre, si no, entra por uno de los returns anteriores
        val nuevoProducto = Producto(nombreProductoEscrito, precioNuevo)
        productos.add(nuevoProducto)
        textoAbajo =
            "Se ha añadido el producto ${nuevoProducto.nombre} con precio ${nuevoProducto.precio}"
        cambiarSoloTextoAbajoDeUIState(textoAbajo)
    }

    private fun cambiarSoloTextoAbajoDeUIState(textoAbajo: String) {
        _uiState.update { currentState ->
            currentState.copy(
                textoAbajo = textoAbajo
            )
        }
    }
}