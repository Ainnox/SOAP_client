package com.example.train_client

import com.example.consumingwebservice.wsdl.GetTrainResponse
import com.example.consumingwebservice.wsdl.GetUserResponse

class TrainClientApplication {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val quoteClient = TrainClient()
            //demande un id train à l'utilisateur
            println("Entrez votre username :")
            val user: String? = readLine()
            println("Entrez votre mot de passe :")
            val pwd: String? = readLine()
            val response: GetUserResponse = quoteClient.getUser(user!!.toString(),pwd!!.toString())
            System.err.println("Bonjour ${response.user.name} ${response.user.lastName}")
        }
    }
}


