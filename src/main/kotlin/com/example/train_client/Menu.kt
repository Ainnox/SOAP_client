package com.example.train_client

import com.example.consumingwebservice.wsdl.GetUserResponse
import com.example.consumingwebservice.wsdl.Reservations
import com.example.consumingwebservice.wsdl.SubscribeResponse
import com.example.consumingwebservice.wsdl.User
import java.time.LocalTime


class Menu(private val quoteClient: TrainClient) {
    companion object {
        private lateinit var instance: Menu
        private lateinit var user: User
        fun getInstance(quoteClient: TrainClient): Menu {
            if (!::instance.isInitialized) {
                instance = Menu(quoteClient)
            }
            return instance
        }
    }

    fun connection() {
        println("1-Connection")
        println("2-Inscription")
        var choix: Int
        do {
            choix = readln().toInt()
        } while (choix != 1 && choix != 2)

        when (choix) {
            1 -> {
                println("Entrez votre username :")
                val username = readln()
                println("Entrez votre mot de passe :")
                val pwd = readln()
                val response: GetUserResponse = quoteClient.getUser(username, pwd)
                if (response.user != null) {
                    user = response.user
                    System.err.println("Bonjour ${response.user.name} ${response.user.lastName}")
                } else {
                    System.err.println("Erreur de connexion")
                }
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
                    connection()
                }
            }
        }
    }

    fun menu(): Boolean {
        println("1-Voir mes réservations")
        println("2-Chercher un train")
        println("3-Quitter")
        when (readln().toInt()) {
            1 -> {
                val resList = reservations()
                println("1-Annuler une réservation")
                println("2-Retour")
                when (readln().toInt()) {
                    1 -> {
                        println("Entrez l'id de la réservation à annuler :")
                        val id = readln().toInt()
                        annuler(resList[id - 1])
                    }
                    2 -> {
                        menu()
                    }
                }
            }

            2 -> {
                val trainList = chercherTrain()
                println("1-Reserver")
                println("2-Nouvelle recherche")
                when (readln().toInt()) {
                    1 -> {
                        println("Sélectionner un train :")
                        val indexTrain = readln().toInt()
                        println("Sélectionner une quantité :")
                        val quantity = readln().toInt()
                        println("Sélectioner une catégorie :")
                        println("1-business classe")
                        println("2-standard classe")
                        println("3-première classe")
                        val classe = when (readln().toInt()) {
                            1 -> "business"
                            2 -> "standard"
                            3 -> "first"
                            else -> "standard"
                        }
                        reserver(idTrain = trainList[indexTrain-1],quantite = quantity,classe = classe)
                        return true
                    }
                }
            }

            3 -> {
                return false
            }

            else -> {
                println("Choix invalide")
            }
        }
        return true
    }

    private fun chercherTrain():List<Int> {
        println("2-Chercher un train")
        println("Entrez votre ville de départ :")
        val start = readln()
        println("Entrez votre ville d'arrivée :")
        val dest = readln()
        println("Entrez la date de départ (yyyy-MM-dd):")
        val dateStart = readln() + " " + LocalTime.now().toString()

        val response = quoteClient.getTrainStartDate(start, dest, dateStart)
        val trainList = MutableList(0){0}
        if (response.trains.size == 0)
            System.err.println("Aucun train trouvé")
        else {
            for ((index, train) in response.trains.withIndex()) {
                println(
                    "${index + 1} - Départ depuis ${train.start} à ${train.dateStart} - " +
                            "Arrivée à ${train.dest} à ${train.dateDest} "
                )
                trainList.add(train.idTrain)
            }
        }
        return trainList
    }

    private fun reservations():List<Int> {
        val response = quoteClient.getReservations(user.id)
        val resList = MutableList(0){0}
        if (response.reservations.size == 0)
            System.err.println("Aucune réservation trouvée")
        else {
            for ((index, reservation) in response.reservations.withIndex()) {
                println(
                    "${index + 1} - Départ depuis ${reservation.lieuDepart} à ${reservation.heureDepart} - " +
                            "Arrivée à ${reservation.lieuArrivee} à ${reservation.heureArrivee} - " +
                            "Réservation en ${reservation.classe}, ${reservation.prix} "
                )
                resList.add(reservation.idReservation)
            }
        }
        return resList
    }

    private fun reserver(idTrain:Int,classe:String,quantite:Int){
        val response = quoteClient.getReservation(user.id, idTrain,classe,quantite)
        if (response.message != null)
            System.err.println(response.message)
        else
            System.err.println("Erreur lors de la réservation")
    }

    private fun annuler(ideReservations: Int){
        val response = quoteClient.getAnnulation(user.id,ideReservations)
        if (response.status != null)
            System.err.println(response.status)
        else
            System.err.println("Erreur lors de l'annulation")
    }
}