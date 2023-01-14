package com.example.train_client


import com.example.consumingwebservice.wsdl.GetTrainRequest
import com.example.consumingwebservice.wsdl.GetTrainResponse
import com.example.consumingwebservice.wsdl.GetUserRequest
import com.example.consumingwebservice.wsdl.GetUserResponse
import com.example.consumingwebservice.wsdl.SubscribeRequest
import com.example.consumingwebservice.wsdl.SubscribeResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.ws.client.core.support.WebServiceGatewaySupport
import org.springframework.ws.soap.client.core.SoapActionCallback

class TrainClient : WebServiceGatewaySupport() {
//    private val log: Logger = LoggerFactory.getLogger(TrainClient::class.java)

    fun getTrain(trainId: Int): GetTrainResponse {
        val request = GetTrainRequest()
        request.id = trainId
        webServiceTemplate.marshaller = TrainConfiguration().marshaller()
        webServiceTemplate.unmarshaller = TrainConfiguration().marshaller()

//        log.info("Requesting train for id: $trainId")
        val response = webServiceTemplate.marshalSendAndReceive(
            "http://localhost:8080/ws",
            request,
            SoapActionCallback("http://localhost:8080/ws/")
        )

        return response as GetTrainResponse
    }

    fun getUser(username: String,pwd:String): GetUserResponse {
        val request = GetUserRequest()
        request.username = username
        request.pwd = pwd
        webServiceTemplate.marshaller = TrainConfiguration().marshaller()
        webServiceTemplate.unmarshaller = TrainConfiguration().marshaller()
        val response = webServiceTemplate.marshalSendAndReceive(
            "http://localhost:8080/ws",
            request,
            SoapActionCallback("http://localhost:8080/ws/")
        )
        return response as GetUserResponse
    }

    fun subscribe(username: String,pwd:String,lastName:String,name:String): SubscribeResponse {
        val request = SubscribeRequest()
        request.username = username
        request.pwd = pwd
        request.lastName = lastName
        request.name = name
        webServiceTemplate.marshaller = TrainConfiguration().marshaller()
        webServiceTemplate.unmarshaller = TrainConfiguration().marshaller()
        val response = webServiceTemplate.marshalSendAndReceive(
            "http://localhost:8080/ws",
            request,
            SoapActionCallback("http://localhost:8080/ws/")
        )
        return response as SubscribeResponse
    }
}