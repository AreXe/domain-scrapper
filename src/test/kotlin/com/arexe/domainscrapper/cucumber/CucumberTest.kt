package com.arexe.domainscrapper.cucumber

import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import io.cucumber.spring.CucumberContextConfiguration
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest

@RunWith(Cucumber::class)
@CucumberOptions(features = ["src/test/resources/scenarios"])
@CucumberContextConfiguration
@SpringBootTest
class CucumberTest