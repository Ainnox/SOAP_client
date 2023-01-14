package com.example.train_client

import com.example.consumingwebservice.wsdl.GetTrainResponse
import com.example.consumingwebservice.wsdl.GetUserResponse
import com.example.consumingwebservice.wsdl.SubscribeResponse

class TrainClientApplication {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val quoteClient = TrainClient()
            val menu = Menu()
            val user = menu.connection(quoteClient)
        }
    }
}


