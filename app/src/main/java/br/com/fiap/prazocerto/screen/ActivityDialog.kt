package br.com.fiap.prazocerto.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.fiap.prazocerto.model.Activity
import com.google.firebase.Firebase
import com.google.firebase.database.database

@Composable
fun AtividadeDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    activity: Activity = Activity(),
    operation: String
) {

    // Definindo o estado dos campos
    var title by remember { mutableStateOf(activity.title) }
    var subject by remember { mutableStateOf(activity.subject) }
    var deliveryDate: String by remember { mutableStateOf(activity.deliveryDate.toString()) }
    var wasDelivered by remember { mutableStateOf(activity.wasDelivered) }

    // Criando o AlertDialog
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = operation)
        },
        text = {
            Column(){
                OutlinedTextField(
                    value = title,
                    onValueChange = {
                        title = it
                    },
                    label = {
                        Text(text = "Título da atividade")
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = subject,
                    onValueChange = {
                        subject = it
                    },
                    label = {
                        Text(text = "Disciplina")
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = deliveryDate.toString(),
                    onValueChange = {
                        deliveryDate = it
                    },
                    label = {
                        Text(text = "Data da entrega")
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = wasDelivered,
                        onCheckedChange = {
                            wasDelivered = it
                        }
                    )
                    Text(text = "Realizada")
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    //pegar uma referencia para o bd
                    val database = Firebase.database("https://prazo-certo-54a56-default-rtdb.firebaseio.com/")

                    if (operation == "Adicionar"){
                        val newActivity = Activity(
                            title = title,
                            subject = subject,
                            deliveryDate = deliveryDate,
                            wasDelivered = wasDelivered
                        )
                        //obter uma referencia para o nó "activity" | similar a pasta C do pc | sistema de hierarquia
                        val activityRef = database.getReference("activity").push()

                        //obeter o id da atividade que é gerado pelo firebase e atribui a minha activity da classe model
                        newActivity.id = activityRef.key.toString()

                        //gravar a activity no firebase | precisa ser em formato Json
                        activityRef.setValue(newActivity.toJson())

                        onConfirm()
                    } else {
                        val newActivity = activity.copy(
                            title = title,
                            subject = subject,
                            deliveryDate = deliveryDate,
                            wasDelivered = wasDelivered
                        )
                        //passando o id o firebase sabe que é pra editar
                        database.getReference("activity").child(activity.id).setValue(newActivity.toJson())
                        onConfirm()
                    }

                }
            ) {
                Text(text = "Confirmar")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text(text = "Cancelar")
            }
        }
    )
}