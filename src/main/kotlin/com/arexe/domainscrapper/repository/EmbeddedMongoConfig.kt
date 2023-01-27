package com.arexe.domainscrapper.repository

import de.flapdoodle.embed.mongo.config.Net
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.mongo.transitions.Mongod
import de.flapdoodle.embed.mongo.transitions.RunningMongodProcess
import de.flapdoodle.embed.process.runtime.Network
import de.flapdoodle.reverse.transitions.Start
import jakarta.annotation.PreDestroy
import org.springframework.boot.autoconfigure.mongo.MongoProperties
import org.springframework.context.annotation.Configuration


@Configuration
class EmbeddedMongoConfig(
        mongoConfig: CustomMongoConfig
) {
    init {
        startMongoProcess(mongoConfig.mongoProperties())
    }

    private lateinit var mongodProcess: RunningMongodProcess

    private fun startMongoProcess(mongoProperties: MongoProperties) {
        Mongod.instance()
                .withNet(Start.to(Net::class.java).initializedWith(mongoProperties.let { Net.of(it.host, it.port, Network.localhostIsIPv6()) }))
                .start(Version.V6_0_3)
                .current().also { mongodProcess = it }
    }

    @PreDestroy
    private fun handleExit() = mongodProcess.stop()
}