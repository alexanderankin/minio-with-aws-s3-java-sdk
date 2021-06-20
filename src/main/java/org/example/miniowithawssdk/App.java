package org.example.miniowithawssdk;

public class App {
    public static String bucketName = "bucket";
    public static String keyName = "hosts";
    public static String uploadFileName = "/etc/hosts";
    public static String accessKeyId = "minio";
    public static String secretAccessKey = "minio";
    public static String endpoint = "http://localhost:9000";

    // public static void main(String[] args) { System.out.println(new App().getGreeting()); }
    public static void main(String[] args) throws Throwable {
        new S3DemoSdkV1().runDemo();
        new S3DemoSdkV2().runDemo();
    }

    public String getGreeting() {
        return "Hello world.";
    }
}
