package br.com.fiap.bstest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.bstest.ui.theme.BSTESTTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BSTESTTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold { innerPadding ->Box(modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)) {

        Text(
            text = "Hello $name!",
            modifier = Modifier.align(Alignment.Center)
        )


        var estado by remember { mutableStateOf("") }
        EstadoTextField(value = estado, onClick = {
            showBottomSheet = true
        })


//        Button(
//            onClick = { showBottomSheet = true },
//            modifier = Modifier.align(Alignment.Center) // Centraliza o botão
//        ) {
//            Text("Abrir Bottom Sheet")
//        }

        if(showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState
            ) {
                // Conteúdo do Bottom Sheet com padding
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp) // Adiciona padding de 16.dp em todos os lados
                ) {

//                    Button(onClick = {
//                        scope.launch { sheetState.hide() }.invokeOnCompletion {
//                            if (!sheetState.isVisible) {
//                                showBottomSheet = false
//                            }
//                        }
//                    }) {
//                        Text("Hide bottom sheet")
//                    }

                    EstadosOptions(
                        onEstadoClick = { e ->
                            estado = e
                            scope.launch {
                                sheetState.hide()
                            }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    showBottomSheet = false
                                }
                            }
                        }
                    )

                }
            }
        }
    }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BSTESTTheme {
        Greeting("Android")
    }
}

@Composable
fun EstadosOptions(
    onEstadoClick: (String) -> Unit
) {
    val estados = remember {
        listOf("São Paulo", "Rio de Janeiro", "Minas Gerais", "Bahia", "Pernambuco", "Ceará", "Rio Grande do Sul", "Santa Catarina", "Paraná", "Amazonas", "Espírito Santo", "Goiás", "Mato Grosso do Sul", "Mato Grosso", "Distrito Federal", "Pará", "Tocantins", "Maranhão", "Alagoas", "Sergipe", "Rondônia", "Amapá", "Roraima")
    }
    Column(
        Modifier
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Escolha um estado:",
            modifier = Modifier.padding(bottom = 8.dp)
        )

        estados.forEach { estado ->
            Text(
                text = estado,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { onEstadoClick(estado) }
            )
        }
    }
}

@Composable
fun EstadoTextField (
    value: String,
    onClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = value,
        onValueChange = {},
        Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .onFocusChanged {
                if (it.isFocused) {
                    onClick()
                    focusManager.clearFocus()
                }
            },
        readOnly = true,
        label = { Text("Estado") },
    )
}

