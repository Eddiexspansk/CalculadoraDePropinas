package com.developerCompany.calculadoraDePropinas.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.res.stringResource
import com.developerCompany.calculadoraDePropinas.R
import com.developerCompany.calculadoraDePropinas.domain.model.Camarero
import com.developerCompany.calculadoraDePropinas.ui.util.NumberUtils

/**
 * Pantalla principal de la calculadora desarrollada íntegramente con Jetpack Compose.
 * Utiliza Material 3 para un diseño moderno y adaptativo.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalcScreen(viewModel: CamareroViewModel = viewModel()) {
    // Observamos el estado del ViewModel de forma reactiva
    val uiState by viewModel.uiState.collectAsState()
    
    // Estados locales para los campos de texto
    var nombre by remember { mutableStateOf("") }
    var horas by remember { mutableStateOf("") }
    var propinaInput by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.app_name)) })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Campo para el total de la jornada
            OutlinedTextField(
                value = propinaInput,
                onValueChange = { 
                    propinaInput = it
                    viewModel.actualizarPropinaTotal(it)
                },
                label = { Text(stringResource(R.string.propinas_totales_en_jornada)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text(stringResource(R.string.nombre_del_camarero)) },
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            OutlinedTextField(
                value = horas,
                onValueChange = { horas = it },
                label = { Text(stringResource(R.string.horas_trabajadas)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = {
                    val horasVal = NumberUtils.parse(horas)
                    if (nombre.isNotBlank() && (horasVal > 0)) {
                        viewModel.agregarCamarero(nombre, horasVal)
                        nombre = ""
                        horas = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(stringResource(R.string.agregar))
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(stringResource(R.string.valor_por_hora), fontWeight = FontWeight.Bold)
                Text(NumberUtils.format(uiState.valorPorHora), fontSize = 18.sp)
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(uiState.camareros, key = { it.id }) { camarero ->
                    CamareroItem(
                        camarero = camarero,
                        onDelete = { viewModel.eliminarCamarero(camarero) }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Button(
                onClick = { 
                    viewModel.limpiarTodo()
                    propinaInput = ""
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.borrar))
            }
        }
    }
}

@Composable
fun CamareroItem(camarero: Camarero, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(camarero.nombre, fontWeight = FontWeight.Bold)
                Text("Horas: ${camarero.horasTrabajadas}", fontSize = 12.sp)
                Text("Propina: $${NumberUtils.format(camarero.propina)}", fontSize = 14.sp)
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}
