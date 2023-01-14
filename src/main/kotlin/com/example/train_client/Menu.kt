package com.example.train_client

import com.example.consumingwebservice.wsdl.GetUserResponse
import com.example.consumingwebservice.wsdl.SubscribeResponse
import com.example.consumingwebservice.wsdl.User

class Menu {
    private lateinit var user:User
    fun connection(quoteClient: TrainClient): User{
        println("1-Connection")
        println("2-Inscription")
        var choix:Int
        do {
            choix = readln().toInt()
        }while (choix !=1 && choix !=2)

        when (choix) {
            1 -> {
                println("Entrez votre username :")
                val username = readln()
                println("Entrez votre mot de passe :")
                val pwd = readln()
                val response: GetUserResponse = quoteClient.getUser(username, pwd)
                System.err.println("Bonjour ${response.user.name} ${response.user.lastName}")
                user = response.user
            }

            2 -> {
                println("entrez un username :")
                val username = readln()
                println("entrez un mot de passe :")
                val pwd = readln()
                println("entrez votre nom :")
                val lastName = readln()
                println("entrez votre prenom :")
                val name = readln()
                val response: SubscribeResponse = quoteClient.subscribe(username, pwd, lastName, name)
                if (response.status == "OK") {
                    System.err.println("Bonjour ${response.user.name} ${response.user.lastName} vous êtes bien inscrit")
                    user = response.user
                } else {
                    System.err.println(response.status)
                    user = connection(quoteClient)
                }
            }
        }
        return user
    }
}