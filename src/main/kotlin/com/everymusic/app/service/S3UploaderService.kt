package com.everymusic.app.service

import io.github.cdimascio.dotenv.dotenv
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest
import java.net.URI
import java.nio.file.Files
import java.time.Duration
import java.util.*

@Service
class S3UploaderService {

    private val dotenv = dotenv()
    private val region = Region.of(dotenv["AWS_REGION"])
    private val bucket = dotenv["S3_BUCKET_NAME"]

    // 任意指定された場合のみ MinIO とみなす
    private val endpoint = dotenv["S3_ENDPOINT"]
    private val useMinio = !endpoint.isNullOrBlank()

    private val s3: S3Client = S3Client.builder()
        .apply {
            if (useMinio) {
                endpointOverride(URI.create(endpoint))
                forcePathStyle(true)
            }
        }
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

        return key
    }

    fun getPublicUrl(s3Key: String): String {
        return if (useMinio) {
            "$endpoint/$bucket/$s3Key"  // ローカル（MinIO）の場合は直接アクセス
        } else {
            val presigner = S3Presigner.builder()
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

            val getObjectRequest = GetObjectRequest.builder()
                .bucket(bucket)
                .key(s3Key)
                .build()

            val presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(15))
                .getObjectRequest(getObjectRequest)
                .build()

            val presignedUrl = presigner.presignGetObject(presignRequest).url().toString()

            presigner.close()
            presignedUrl
        }
    }
}