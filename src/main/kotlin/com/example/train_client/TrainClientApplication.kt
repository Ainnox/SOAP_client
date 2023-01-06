package com.example.train_client

import com.example.consumingwebservice.wsdl.GetTrainResponse

class TrainClientApplication {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val quoteClient = TrainClient()
            //demande un id train à l'utilisateur
            println("Entrez un id de train :")
            val train: String? = readLine()
            val response: GetTrainResponse = quoteClient.getTrain(train!!.toInt())
            System.err.println("Le train $train part de " + response.train.start + " et arrive à " + response.train.dest)
        }
    }
}


