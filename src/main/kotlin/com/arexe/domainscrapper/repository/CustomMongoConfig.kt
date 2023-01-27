package com.arexe.domainscrapper.repository

import com.mongodb.ConnectionString
import org.springframework.boot.autoconfigure.mongo.MongoProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory
import org.springframework.data.mongodb.core.convert.MappingMongoConverter
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories
import java.net.URI


@Configuration
@EnableReactiveMongoRepositories
class CustomMongoConfig {

    @Primary
    @Bean("reactiveMongoTemplate")
    fun mongoTemplate(
            databaseFactory: ReactiveMongoDatabaseFactory,
            mappingMongoConverter: MappingMongoConverter
    ) = ReactiveMongoTemplate(databaseFactory, mappingMongoConverter)

    @Primary
    @Bean
    fun mongoDatabaseFactory(
            mongoProperties: MongoProperties
    ) = SimpleReactiveMongoDatabaseFactory(ConnectionString(mongoProperties.uri()))

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "data.mongodb")
    fun mongoProperties() = MongoProperties()

    private fun MongoProperties.uri() = uri ?: URI("mongodb", null, host, port.toInt(), "/$database", null, null).toString()
}