package com.calmero.config


//@Component
//class S3Client {
//    @Value("\${s3.accesskey}")
//    private val awsId: String? = null
//
//    @Value("\${s3.secretkey}")
//    private val awsKey: String? = null
//
//    @Value("\${s3.region}")
//    private val region: String? = null
//
//    @Bean
//    fun s3client(): AmazonS3 {
//        val awsCreds = BasicAWSCredentials(awsId, awsKey)
//        return AmazonS3ClientBuilder.standard()
//                .withRegion(region)
//                .withCredentials(AWSStaticCredentialsProvider(awsCreds))
//                .build()
//    }
//}
