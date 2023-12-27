package com.example.examen2viewmodel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.examen2viewmodel.data.DataSource
import com.example.examen2viewmodel.data.Producto
import com.example.examen2viewmodel.data.ProductosInterctuarUIState
import com.example.examen2viewmodel.ui.theme.Examen2ViewModelTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Examen2ViewModelTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Inicio()
                }
            }
        }
    }
}
@Composable
fun Inicio(viewModelProductosInteractuar: ProductosInteracturViewModel = viewModel()){
    val uiState by viewModelProductosInteractuar.uiState.collectAsState()
    Pantallas(viewModelProductosInteractuar,uiState)
}

@Composable
fun Pantallas(
    viewModelProductosInteractuar: ProductosInteracturViewModel,
    uiState: ProductosInterctuarUIState,
    modifier: Modifier = Modifier,
) {
    Column() {
        Text(
            text = "Hola soy alumno ViewModel",
            modifier = modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .weight(0.25f)
                .padding(start = 20.dp, top = 50.dp)
        )
        ProductosYTextEditBoton(modifier = modifier.weight(1f),
            productosTotales = uiState.productos,
            valorPrimerTextEditor=viewModelProductosInteractuar.nombreProductoUsuario,
            valorSegundoTextEditor=viewModelProductosInteractuar.cantidadProductoUsuario,
            primerTextEditor = { viewModelProductosInteractuar.setNombreProducto(it)},
            segundoTextEditor = { viewModelProductosInteractuar.setPrecioProducto(it) },
            clickAction = {
                viewModelProductosInteractuar.intentoModificarListaProductos()
            })
        Text(
            text = uiState.textoAbajo,
            textAlign = TextAlign.Center,
            modifier = modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .weight(0.25f)

        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductosYTextEditBoton(
    modifier: Modifier,
    productosTotales: List<Producto>,
    clickAction: () -> Unit,
    valorPrimerTextEditor: String,
    valorSegundoTextEditor: String,
    primerTextEditor: (String) -> Unit,
    segundoTextEditor: (String) -> Unit,
) {
    Row(modifier = modifier) {
        Column(modifier = modifier.weight(1f)) {
            TextField(
                value = valorPrimerTextEditor,
                singleLine = true,
                modifier = Modifier.padding(16.dp),
                //onValueChange = primerTextEditor,
                onValueChange = primerTextEditor,
                label = { Text("Nombre Model View") },

                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                )
            )

            TextField(
                value = valorSegundoTextEditor,
                singleLine = true,
                modifier = Modifier.padding(16.dp),
                //onValueChange = segundoTextEditor,
                onValueChange = segundoTextEditor,
                label = { Text("Precio") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
                )
            )
            Button(
                onClick = {
                    clickAction.invoke() //Lo hago con invoke en vez de ponerlo directamente
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Add/Update producto")
            }
        }

        LazyColumn(
            modifier = modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(productosTotales) { producto ->
                Card(
                    modifier = modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Nombre: ${producto.nombre}",
                        modifier = Modifier
                            .background(Color.Yellow)
                            .fillMaxWidth()
                            .padding(20.dp)
                    )
                    Text(
                        text = "Precio: ${producto.precio.toString()}",
                        modifier = Modifier
                            .background(Color.Cyan)
                            .fillMaxWidth()
                            .padding(20.dp)
                    )
                }
            }
        }
    }
}