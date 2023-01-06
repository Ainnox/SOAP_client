package com.example.train_client

import org.springframework.oxm.jaxb.Jaxb2Marshaller

class TrainConfiguration {


    fun marshaller(): Jaxb2Marshaller {
        val marshaller = Jaxb2Marshaller()
        // this package must match the package in the <generatePackage> specified in
        // pom.xml
        marshaller.contextPath = "com.example.consumingwebservice.wsdl"
        return marshaller
    }
}