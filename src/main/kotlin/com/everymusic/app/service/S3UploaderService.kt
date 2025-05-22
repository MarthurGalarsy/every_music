package com.everymusic.app.service

import io.github.cdimascio.dotenv.dotenv
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.nio.file.Files
import java.util.*

@Service
class S3UploaderService {

    private val dotenv = dotenv()
    private val region = Region.of(dotenv["AWS_REGION"])
    private val s3: S3Client = S3Client.builder()
        .region(region)
        .credentialsProvider(
            StaticCredentialsProvider.create(
                AwsBasicCredentials.create(
                    dotenv["AWS_ACCESS_KEY_ID"],
                    dotenv["AWS_SECRET_ACCESS_KEY"]
                )
            )
        )
        .build()

    private val bucket = dotenv["S3_BUCKET_NAME"]

    fun upload(file: MultipartFile): String {
        val tempFile = Files.createTempFile(UUID.randomUUID().toString(), file.originalFilename?.substringAfterLast(".")).toFile()
        file.transferTo(tempFile)

        val key = "uploads/${UUID.randomUUID()}_${file.originalFilename}"

        val putRequest = PutObjectRequest.builder()
            .bucket(bucket)
            .key(key)
            .contentType(file.contentType)
            .build()

        s3.putObject(putRequest, tempFile.toPath())

        tempFile.delete()

        return key // DB登録時などに使用
    }
}