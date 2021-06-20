# Minio with AWS S3 Java SDK

this repo shows how to use minio with the v1 **and** v2 sdk.

start by running this docker command to start minio:

```bash
docker run -it --rm --name minio -p 9000:9000 \
  -e MINIO_ROOT_USER=minio \
  -e MINIO_ROOT_PASSWORD=miniopassword \
  minio/minio server /data
```

then verify that you see your hosts file is printed twice,
meaning that the demo has run successfully twice, for v1 & v2.

```bash
./gradlew clean build run
```
