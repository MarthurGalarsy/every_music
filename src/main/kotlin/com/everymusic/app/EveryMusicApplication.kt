package com.everymusic.app

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@MapperScan("com.everymusic.app.mapper")
class EveryMusicApplication

fun main(args: Array<String>) {
    runApplication<EveryMusicApplication>(*args)
}