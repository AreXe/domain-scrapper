package com.arexe.domainscrapper.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory

interface LoggerTrait {
    val logger: Logger
    get() = LoggerFactory.getLogger(javaClass)
}