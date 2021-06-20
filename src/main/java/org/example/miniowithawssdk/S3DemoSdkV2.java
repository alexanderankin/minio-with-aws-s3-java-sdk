package org.example.miniowithawssdk;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.auth.signer.AwsS3V4Signer;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.client.config.SdkAdvancedClientOption;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


public class S3DemoSdkV2 extends S3Demo {

    public void runDemo() throws IOException, URISyntaxException {
        AwsBasicCredentials credentials =
                AwsBasicCredentials.create(App.accessKeyId, App.secretAccessKey);

        S3Client s3Client = S3Client.builder()
                .endpointOverride(new URI(App.endpoint))
                .region(Region.US_EAST_1)
                .serviceConfiguration(e -> e.pathStyleAccessEnabled(false))
                .overrideConfiguration(c -> {
                    c.putAdvancedOption(SdkAdvancedClientOption.SIGNER,
                            AwsS3V4Signer.create());
                })
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();

        try {
            System.out.println("Uploading a new object to S3 from a file\n");
            File file = new File(App.uploadFileName);

            // Upload file
            s3Client.putObject(PutObjectRequest.builder()
                            .bucket(App.bucketName)
                            .key(App.keyName)
                            .build(),
                    RequestBody.fromFile(file));

            // Download file
            GetObjectRequest getReq = GetObjectRequest.builder()
                    .bucket(App.bucketName)
                    .key(App.keyName)
                    .build();

            ResponseInputStream<GetObjectResponse> objectPortion =
                    s3Client.getObject(getReq);

            System.out.println("Printing bytes retrieved:");
            displayTextInputStream(objectPortion);
        } catch (AwsServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which " +
                    "means your request made it " +
                    "to Amazon S3, but was rejected with an error response" +
                    " for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.statusCode());
            System.out.println("AWS Error Code:   " + ase.awsErrorDetails().errorCode());
            System.out.println("Error Type:       " + ase.awsErrorDetails().errorMessage());
            System.out.println("Request ID:       " + ase.requestId());

        } catch (SdkClientException ace) {
            System.out.println("Caught an AmazonClientException, which " +
                    "means the client encountered " +
                    "an internal error while trying to " +
                    "communicate with S3, " +
                    "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());

        }
    }
}
