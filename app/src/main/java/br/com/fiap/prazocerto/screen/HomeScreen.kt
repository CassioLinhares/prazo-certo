package br.com.fiap.prazocerto.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.prazocerto.ui.theme.PrazoCertoTheme
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.prazocerto.R
import br.com.fiap.prazocerto.model.Activity
import br.com.fiap.prazocerto.navigation.Destination
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.database

import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    var showDialog by remember { mutableStateOf(false) }

    var activityEdit by remember { mutableStateOf(Activity()) }
    var showDialogEdit by remember { mutableStateOf(false) }

    var showDialogDelete by remember { mutableStateOf(false) }
    var idActivityDelete by remember { mutableStateOf("") }

    val activities = remember { mutableStateListOf<Activity>() }

    val database = Firebase.database("https://prazo-certo-54a56-default-rtdb.firebaseio.com/")
    val ref = database.getReference("activity")

    //logOut do google
    val authentication = FirebaseAuth.getInstance() //conects to firebaseAuth
    val context = LocalContext.current
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.default_web_client_id)) //google poder confiar no nosso app
        .requestEmail()
        .build()
    val googleSignInClient = GoogleSignIn.getClient(context, gso)


    //add ouvinte p/ c/ alteração
    // interface ValueEventListener que obriga a criação de onDataChange() e onCancelled()
    // onDataChange() é chamado quando os dados são alterados
    // onCancelled() é chamado quando ocorre um erro na leitura dos dados
    ref.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            activities.clear()

            for (child in snapshot.children) { //snapshot.children = c/ item da activity do db
                val activity = child.getValue(Activity::class.java) //desserilização
                if (activity != null) {
                    activities.add(activity)
                }
            }
        }

        override fun onCancelled(error: DatabaseError) {
            println("Erro ao buscar atividades: ${error.message}")
        }
    })

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column() {
                            Text(
                                text = "Prazo Certo",
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                            Text(
                                text = "Olá, ${authentication.currentUser?.displayName}",
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontSize = 15.sp,
                                modifier = modifier
                                    .padding(top = 2.dp)
                            )
                        }
                        IconButton(
                            onClick = {
                                authentication.signOut() //clear session data
                                //logOut do google
                                googleSignInClient.signOut()
                                    .addOnCompleteListener {
                                        navController.navigate(Destination.LoginScreen.route)
                                    }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "close",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults
                    .topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showDialog = true
                },
                shape = RoundedCornerShape(50.dp),
                modifier = modifier
                    .width(100.dp),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Adicionar",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Text(
                text = "Atividades",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Start
            )
            // Exibindo a lista de atividades
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(bottom = 48.dp)
            ) {
                items(activities) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(8.dp)
                                .weight(1f)
                        ) {
                            Text(
                                text = it.title,
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = it.subject,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = it.deliveryDate,
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        //edit
                        IconButton(
                            onClick = {
                                activityEdit = it //vai receber o obj clicado
                                showDialogEdit = true
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Editar",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                        //Delete
                        IconButton(
                            onClick = {
                                idActivityDelete = it.id
                                showDialogDelete = true
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "delete",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    HorizontalDivider() // cria uma linha
                }
            }

        }
        if (showDialogDelete) {
            AlertDialog(
                onDismissRequest = { showDialogDelete = false },
                title = {
                    Text(text = "Excluir atividade")
                },
                text = {
                    Text(text = "Deseja excluir esta atividade?")
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showDialogDelete = false
                            database.getReference("activity").child(idActivityDelete).removeValue()
                        }
                    ) { Text(text = "Excluir")  }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showDialogDelete = false
                        }
                    ) { Text(text = "Cancelar") }
                }



            )
        }
        // Exibe o AlertDialog AtividadeDialog
        if (showDialog) {
            AtividadeDialog(
                onDismiss = { showDialog = false },
                onConfirm = { showDialog = false },
                operation = "Adicionar"
            )
        }

        // Exibe o AlertDialog ActivityEditDialog
        if (showDialogEdit) {
            AtividadeDialog(
                onDismiss = { showDialogEdit = false },
                onConfirm = { showDialogEdit = false },
                operation = "Editar",
                activity = activityEdit
            )
        }
    }
}

@Preview( )
@Composable
fun HomeScreenPreview() {
    PrazoCertoTheme() {
        HomeScreen(navController = rememberNavController())
    }
}

//fluxo do app

//HomeScreen inicia
//↓
//Cria referência do banco
//↓
//Adiciona listener
//↓
//Firebase envia dados atuais
//↓
//onDataChange é chamado
//↓
//lista limpa
//↓
//loop nas atividades
//↓
//converte JSON → Activity
//↓
//adiciona na lista
//↓
//Compose atualiza a UI

//Se você criar ou deletar uma atividade:

//Firebase detecta mudança
//↓
//onDataChange roda novamente
//↓
//lista atualizada
//↓
//LazyColumn atualiza automaticamente