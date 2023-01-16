package com.example.train_client

import com.example.consumingwebservice.wsdl.*
import org.springframework.ws.client.core.support.WebServiceGatewaySupport
import org.springframework.ws.soap.client.core.SoapActionCallback
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TrainClient : WebServiceGatewaySupport() {
//    private val log: Logger = LoggerFactory.getLogger(TrainClient::class.java)

    fun getTrainStartDate(start: String, dest: String): GetTrainStartDateResponse {
        val request = GetTrainStartDateRequest()
        request.start = start
        request.dest = dest
        //Créer localtime de 2022-05-01 12h45
        val datFormate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        request.dateStart = LocalDateTime.of(2022, 5, 1, 12, 45).format(datFormate).toString()
        webServiceTemplate.marshaller = TrainConfiguration().marshaller()
        webServiceTemplate.unmarshaller = TrainConfiguration().marshaller()
        val response = webServiceTemplate.marshalSendAndReceive(
            "http://localhost:8080/ws",
            request,
            SoapActionCallback("http://localhost:8080/ws/")
        )

        return response as GetTrainStartDateResponse
    }

    fun getUser(username: String, pwd: String): GetUserResponse {
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

    fun subscribe(username: String, pwd: String, lastName: String, name: String): SubscribeResponse {
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

    fun getReservations(userId: Int): GetReservationsResponse {
        val request = GetReservationsRequest()
        request.usrId = userId
        webServiceTemplate.marshaller = TrainConfiguration().marshaller()
        webServiceTemplate.unmarshaller = TrainConfiguration().marshaller()
        val response = webServiceTemplate.marshalSendAndReceive(
            "http://localhost:8080/ws",
            request,
            SoapActionCallback("http://localhost:8080/ws/")
        )
        return response as GetReservationsResponse
    }
}