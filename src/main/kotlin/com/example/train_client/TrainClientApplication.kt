package com.example.train_client

class TrainClientApplication {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val quoteClient = TrainClient()
            val menu = Menu.getInstance(quoteClient)
            menu.connection()
            while (menu.menu()) {}
        }
    }
}


