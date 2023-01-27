package com.arexe.domainscrapper.management

import org.springframework.boot.info.BuildProperties
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/hello")
class HelloController(
        private var buildProperties: BuildProperties
) {
    @GetMapping
    fun hello(): ResponseEntity<Message> = ResponseEntity.ok()
            .body(Message("Hello from ${buildProperties.name} application!"))
}

data class Message(
        val message: String
)