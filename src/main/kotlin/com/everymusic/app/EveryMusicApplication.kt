package com.everymusic.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EveryMusicApplication

fun main(args: Array<String>) {
    runApplication<EveryMusicApplication>(*args)
}