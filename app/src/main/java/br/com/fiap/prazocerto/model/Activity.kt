package br.com.fiap.prazocerto.model

data class Activity(
    var id: String = "",
    val title: String = "",
    val subject: String = "", // assunto
    val deliveryDate: String = "", // data de entrega
    val wasDelivered: Boolean = false, // foi entregue
) {
    fun toJson(): Map<String, Any> =
        mapOf(
            "id" to id,
            "title" to title,
            "subject" to subject,
            "deliveryDate" to deliveryDate,
            "wasDelivered" to wasDelivered,
        )

}
// Map<String, Any> = cria um mapa em que a chave é string e o valor é do tipo Any
// Any é um tipo genérico para qualquer tipo de dado

// converte o obj criado p/ json para poder ser enviado para o realTime do Firebase
// quando for fun de 1a linha só (fun que somente return) precisa somente do =