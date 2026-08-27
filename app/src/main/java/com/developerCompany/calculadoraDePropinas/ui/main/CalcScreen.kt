package com.developerCompany.calculadoraDePropinas.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
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

import android.content.res.Configuration
import androidx.compose.ui.platform.LocalConfiguration

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction

/**
 * Pantalla principal de la calculadora desarrollada íntegramente con Jetpack Compose.
 * Utiliza Material 3 para un diseño moderno y adaptativo.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalcScreen(viewModel: CamareroViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val configuration = LocalConfiguration.current
    val focusManager = LocalFocusManager.current
    
    var nombre by remember { mutableStateOf("") }
    var horas by remember { mutableStateOf("") }
    var propinaInput by remember { mutableStateOf("") }

    LaunchedEffect(uiState.camareroEnEdicion) {
        uiState.camareroEnEdicion?.let {
            nombre = it.nombre
            horas = it.horasTrabajadas.toString()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.app_name)) })
        }
    ) { padding ->
        // Si estamos en horizontal, usamos el diseño unificado para asegurar que todo sea accesible.
        // Si estamos en vertical, usamos el diseño separado que prefiere el usuario.
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            UnifiedLayout(
                padding = padding,
                propinaInput = propinaInput,
                nombre = nombre,
                horas = horas,
                uiState = uiState,
                onPropinaChange = { 
                    propinaInput = it
                    viewModel.actualizarPropinaTotal(it)
                },
                onNombreChange = { nombre = it },
                onHorasChange = { horas = it },
                onAgregar = {
                    val horasVal = NumberUtils.parse(horas)
                    if (nombre.isNotBlank() && (horasVal > 0)) {
                        if (uiState.camareroEnEdicion != null) {
                            viewModel.actualizarCamarero(nombre, horasVal)
                        } else {
                            viewModel.agregarCamarero(nombre, horasVal)
                        }
                        nombre = ""
                        horas = ""
                        focusManager.clearFocus()
                    }
                },
                onDeleteCamarero = { viewModel.eliminarCamarero(it) },
                onEditCamarero = { viewModel.iniciarEdicion(it) },
                onCancelEdit = {
                    viewModel.cancelarEdicion()
                    nombre = ""
                    horas = ""
                },
                onClearMonto = {
                    viewModel.limpiarMonto()
                    propinaInput = ""
                }
            )
        } else {
            SeparateLayout(
                padding = padding,
                propinaInput = propinaInput,
                nombre = nombre,
                horas = horas,
                uiState = uiState,
                onPropinaChange = { 
                    propinaInput = it
                    viewModel.actualizarPropinaTotal(it)
                },
                onNombreChange = { nombre = it },
                onHorasChange = { horas = it },
                onAgregar = {
                    val horasVal = NumberUtils.parse(horas)
                    if (nombre.isNotBlank() && (horasVal > 0)) {
                        if (uiState.camareroEnEdicion != null) {
                            viewModel.actualizarCamarero(nombre, horasVal)
                        } else {
                            viewModel.agregarCamarero(nombre, horasVal)
                        }
                        nombre = ""
                        horas = ""
                        focusManager.clearFocus()
                    }
                },
                onDeleteCamarero = { viewModel.eliminarCamarero(it) },
                onEditCamarero = { viewModel.iniciarEdicion(it) },
                onCancelEdit = {
                    viewModel.cancelarEdicion()
                    nombre = ""
                    horas = ""
                },
                onClearMonto = {
                    viewModel.limpiarMonto()
                    propinaInput = ""
                }
            )
        }
    }
}

@Composable
fun SeparateLayout(
    padding: PaddingValues,
    propinaInput: String,
    nombre: String,
    horas: String,
    uiState: CalcUiState,
    onPropinaChange: (String) -> Unit,
    onNombreChange: (String) -> Unit,
    onHorasChange: (String) -> Unit,
    onAgregar: () -> Unit,
    onDeleteCamarero: (Camarero) -> Unit,
    onEditCamarero: (Camarero) -> Unit,
    onCancelEdit: () -> Unit,
    onClearMonto: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Formulario Superior (Fijo)
        OutlinedTextField(
            value = propinaInput,
            onValueChange = onPropinaChange,
            label = { Text(stringResource(R.string.propinas_totales_en_jornada)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = nombre,
            onValueChange = onNombreChange,
            label = { Text(stringResource(R.string.nombre_del_camarero)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = horas,
            onValueChange = onHorasChange,
            label = { Text(stringResource(R.string.horas_trabajadas)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            ),
            singleLine = true,
            keyboardActions = KeyboardActions(onDone = { onAgregar() }),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = onAgregar,
                modifier = Modifier.weight(1f)
            ) {
                Icon(if (uiState.camareroEnEdicion != null) Icons.Default.Edit else Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (uiState.camareroEnEdicion != null) stringResource(R.string.actualizar) else stringResource(R.string.agregar))
            }
            if (uiState.camareroEnEdicion != null) {
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedButton(
                    onClick = onCancelEdit,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(stringResource(R.string.cancelar))
                }
            }
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

        // Lista de Camareros (Desplazable de forma independiente)
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(uiState.camareros, key = { it.id }) { camarero ->
                CamareroItem(
                    camarero = camarero,
                    onDelete = { onDeleteCamarero(camarero) },
                    onEdit = { onEditCamarero(camarero) }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onClearMonto,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.borrar))
        }
    }
}

@Composable
fun UnifiedLayout(
    padding: PaddingValues,
    propinaInput: String,
    nombre: String,
    horas: String,
    uiState: CalcUiState,
    onPropinaChange: (String) -> Unit,
    onNombreChange: (String) -> Unit,
    onHorasChange: (String) -> Unit,
    onAgregar: () -> Unit,
    onDeleteCamarero: (Camarero) -> Unit,
    onEditCamarero: (Camarero) -> Unit,
    onCancelEdit: () -> Unit,
    onClearMonto: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = propinaInput,
                onValueChange = onPropinaChange,
                label = { Text(stringResource(R.string.propinas_totales_en_jornada)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = nombre,
                onValueChange = onNombreChange,
                label = { Text(stringResource(R.string.nombre_del_camarero)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = horas,
                onValueChange = onHorasChange,
                label = { Text(stringResource(R.string.horas_trabajadas)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                ),
                singleLine = true,
                keyboardActions = KeyboardActions(onDone = { onAgregar() }),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = onAgregar,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(if (uiState.camareroEnEdicion != null) Icons.Default.Edit else Icons.Default.Add, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (uiState.camareroEnEdicion != null) stringResource(R.string.actualizar) else stringResource(R.string.agregar))
                }
                if (uiState.camareroEnEdicion != null) {
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedButton(
                        onClick = onCancelEdit,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(stringResource(R.string.cancelar))
                    }
                }
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
        }

        items(uiState.camareros, key = { it.id }) { camarero ->
            CamareroItem(
                camarero = camarero,
                onDelete = { onDeleteCamarero(camarero) },
                onEdit = { onEditCamarero(camarero) }
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onClearMonto,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.borrar))
            }
        }
    }
}

@Composable
fun CamareroItem(camarero: Camarero, onDelete: () -> Unit, onEdit: () -> Unit) {
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
            Column(modifier = Modifier.weight(1f)) {
                Text(camarero.nombre, fontWeight = FontWeight.Bold)
                Text(
                    text = stringResource(R.string.waiter_hours_label, camarero.horasTrabajadas),
                    fontSize = 12.sp
                )
                Text(
                    text = stringResource(R.string.individual_tip_label, NumberUtils.format(camarero.propina)),
                    fontSize = 14.sp
                )
            }
            Row {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = stringResource(R.string.editar), tint = MaterialTheme.colorScheme.primary)
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}
