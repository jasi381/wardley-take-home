package com.bel.c22.class9.e_practice_problem;

/**
 * PRACTICE PROBLEM — Class 9: Creational Design Patterns
 * ========================================================
 *
 * SCENARIO: Cloud Storage SDK
 * ----------------------------
 * You are building a cloud storage SDK that supports multiple providers
 * (AWS S3, Google Cloud Storage, Azure Blob). The SDK must:
 *
 *   1. SINGLETON — ConfigManager
 *      - A single global configuration object that holds the active provider name,
 *        region, and debug mode flag.
 *      - Must be thread-safe. Use Bill Pugh or Double-Checked Locking.
 *      - Methods: setProvider(String), setRegion(String), setDebug(boolean), summary()
 *
 *   2. BUILDER — StorageRequest
 *      - Represents an upload/download request with many optional fields:
 *          Required: bucket (String), key (String), operation ("upload" | "download")
 *          Optional: contentType (String, default "application/octet-stream")
 *                    sizeBytes (long, default 0)
 *                    encrypted (boolean, default false)
 *                    retries (int, default 3)
 *      - Must be immutable after build(). Validate required fields in build().
 *      - Support fluent chaining: new StorageRequest.Builder(...)
 *                                     .contentType("image/png")
 *                                     .encrypted(true)
 *                                     .build();
 *
 *   3. FACTORY METHOD — StorageClient
 *      - Interface: void upload(StorageRequest r), void download(StorageRequest r)
 *      - Concrete: AWSS3Client, GCSClient, AzureBlobClient (each prints its provider name)
 *      - Static factory: StorageClientFactory.create(String provider)
 *        → returns the correct implementation based on provider string
 *
 *   4. ABSTRACT FACTORY — CloudEnvironment
 *      - Some providers bundle their storage client with a specialized logger.
 *      - Logger interface: void info(String msg), void error(String msg)
 *      - Concrete loggers: AWSLogger, GCSLogger, AzureLogger (each prefixes output)
 *      - Abstract factory interface: CloudFactory
 *          StorageClient createStorageClient();
 *          Logger        createLogger();
 *      - Concrete factories: AWSCloudFactory, GCSCloudFactory, AzureCloudFactory
 *      - Client class: CloudApp(CloudFactory factory) — renders upload + logs result
 *
 * HINTS:
 *   - Keep each pattern in its own inner static class or group within one file.
 *   - Reuse StorageRequest (Builder) as the input to StorageClient (Factory).
 *   - ConfigManager (Singleton) should be read by CloudApp to include region in logs.
 *   - You do NOT need to implement actual HTTP calls — just print what would happen.
 *
 * EXPECTED OUTPUT (approximate):
 *   Config: provider=AWS, region=us-east-1, debug=true
 *   [AWS S3] Uploading my-bucket/photo.png (image/png, 2048 bytes, encrypted)
 *   [AWSLogger] INFO: Upload complete
 *   [GCS] Uploading my-bucket/photo.png (image/png, 2048 bytes, encrypted)
 *   [GCSLogger] INFO: Upload complete
 */
public class ProblemStatement {
    public static void main(String[] args) {
        System.out.println("Read the Javadoc above and implement the solution in the solution/ package.");
        System.out.println("Start with ConfigManager (Singleton), then StorageRequest (Builder),");
        System.out.println("then StorageClientFactory (Factory), then CloudFactory (Abstract Factory).");
    }
}
