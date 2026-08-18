package com.bel.c22.class9.e_practice_problem.solution;

import java.util.Set;

/**
 * SOLUTION — Cloud Storage SDK
 * =================================================
 *
 * Demonstrates all 4 creational patterns from Class 9 working together:
 *
 *   | Pattern          | Class/Interface        | Purpose                                          |
 *   |------------------|-------------------------|--------------------------------------------------|
 *   | Singleton        | ConfigManager           | One global config: provider, region, debug flag  |
 *   | Builder          | StorageRequest          | Immutable request object, many optional fields   |
 *   | Factory Method   | StorageClientFactory    | Create the right StorageClient from a string     |
 *   | Abstract Factory | CloudFactory            | Create matched StorageClient + Logger per cloud  |
 *
 * Read top-to-bottom — the patterns appear in the same order as the problem statement,
 * each as a static nested class so the whole solution stays in one file.
 *
 * Run main() to see the full output.
 */
public class Solution {

    // ════════════════════════════════════════════════════════════════════════
    // 1) SINGLETON — ConfigManager
    // ════════════════════════════════════════════════════════════════════════
    // Exactly one config should exist for the whole SDK — every gateway, client,
    // and logger reads the SAME provider/region/debug values.
    //
    // Variant chosen: Bill Pugh (lazy + thread-safe, no synchronization cost on read).
    // The Holder class is loaded only the first time getInstance() is called.
    //
    // NOTE: Bill Pugh only guarantees safe, one-time CREATION of the instance.
    // Because ConfigManager is also mutable (setProvider/setRegion/setDebug can be
    // called any time after creation), those setters/getters are separately
    // synchronized so concurrent reads/writes of the fields stay consistent.
    // ════════════════════════════════════════════════════════════════════════
    static class ConfigManager {
        private String provider = "unset";
        private String region = "unset";
        private boolean debug = false;

        private ConfigManager() {}

        private static class Holder {
            private static final ConfigManager INSTANCE = new ConfigManager();
        }

        public static ConfigManager getInstance() {
            return Holder.INSTANCE;
        }

        public synchronized void setProvider(String provider) { this.provider = provider; }
        public synchronized void setRegion(String region)     { this.region = region; }
        public synchronized void setDebug(boolean debug)      { this.debug = debug; }

        public synchronized String getRegion() { return region; }

        public synchronized String summary() {
            return "Config: provider=" + provider + ", region=" + region + ", debug=" + debug;
        }
    }

    // ════════════════════════════════════════════════════════════════════════
    // 2) BUILDER — StorageRequest
    // ════════════════════════════════════════════════════════════════════════
    // 3 required fields + 4 optional fields with defaults — a telescoping
    // constructor would need 7 parameters. Builder keeps call sites readable
    // and gives us one place (build()) to validate before the object exists.
    // ════════════════════════════════════════════════════════════════════════
    static class StorageRequest {
        private static final Set<String> VALID_OPERATIONS = Set.of("upload", "download");

        // Required
        private final String bucket;
        private final String key;
        private final String operation;
        // Optional — defaults applied in Builder
        private final String contentType;
        private final long sizeBytes;
        private final boolean encrypted;
        private final int retries;

        // Private — only Builder can create a StorageRequest.
        private StorageRequest(Builder b) {
            this.bucket = b.bucket;
            this.key = b.key;
            this.operation = b.operation;
            this.contentType = b.contentType;
            this.sizeBytes = b.sizeBytes;
            this.encrypted = b.encrypted;
            this.retries = b.retries;
        }

        public String getBucket()      { return bucket; }
        public String getKey()         { return key; }
        public String getOperation()   { return operation; }
        public String getContentType() { return contentType; }
        public long getSizeBytes()     { return sizeBytes; }
        public boolean isEncrypted()   { return encrypted; }
        public int getRetries()        { return retries; }

        @Override
        public String toString() {
            return operation + " " + bucket + "/" + key
                    + " (" + contentType + ", " + sizeBytes + " bytes"
                    + ", encrypted=" + encrypted + ", retries=" + retries + ")";
        }

        public static class Builder {
            // Required fields
            private final String bucket;
            private final String key;
            private final String operation;
            // Optional fields — defaults defined here
            private String contentType = "application/octet-stream";
            private long sizeBytes     = 0;
            private boolean encrypted  = false;
            private int retries        = 3;

            public Builder(String bucket, String key, String operation) {
                this.bucket = bucket;
                this.key = key;
                this.operation = operation;
            }

            // Fluent setters — each returns `this` so calls can be chained
            public Builder contentType(String contentType) { this.contentType = contentType; return this; }
            public Builder sizeBytes(long sizeBytes)        { this.sizeBytes = sizeBytes;     return this; }
            public Builder encrypted(boolean encrypted)     { this.encrypted = encrypted;     return this; }
            public Builder retries(int retries)             { this.retries = retries;         return this; }

            // Validates then creates the immutable product
            public StorageRequest build() {
                if (bucket == null || bucket.isBlank()) {
                    throw new IllegalStateException("bucket is required");
                }
                if (key == null || key.isBlank()) {
                    throw new IllegalStateException("key is required");
                }
                if (operation == null || !VALID_OPERATIONS.contains(operation.toLowerCase())) {
                    throw new IllegalStateException("operation must be 'upload' or 'download'");
                }
                return new StorageRequest(this);
            }
        }
    }

    // ════════════════════════════════════════════════════════════════════════
    // 3) FACTORY METHOD — StorageClient / StorageClientFactory
    // ════════════════════════════════════════════════════════════════════════
    // Callers ask StorageClientFactory for a client by provider name — they
    // never write `new AWSS3Client()`. Adding a 4th provider is one new class
    // plus one new switch case; every existing caller stays untouched.
    // ════════════════════════════════════════════════════════════════════════

    // ── PRODUCT interface ────────────────────────────────────────────────────
    interface StorageClient {
        void upload(StorageRequest request);
        void download(StorageRequest request);
    }

    // Shared formatting so the 3 concrete clients don't each re-implement it —
    // only the provider label differs between them.
    static abstract class AbstractStorageClient implements StorageClient {
        protected abstract String label();

        @Override
        public void upload(StorageRequest r) {
            System.out.println("[" + label() + "] Uploading " + describe(r));
        }

        @Override
        public void download(StorageRequest r) {
            System.out.println("[" + label() + "] Downloading " + describe(r));
        }

        private String describe(StorageRequest r) {
            return r.getBucket() + "/" + r.getKey()
                    + " (" + r.getContentType() + ", " + r.getSizeBytes() + " bytes"
                    + ", encrypted=" + r.isEncrypted() + ")";
        }
    }

    // ── CONCRETE PRODUCTS ────────────────────────────────────────────────────
    static class AWSS3Client extends AbstractStorageClient {
        @Override protected String label() { return "AWS S3"; }
    }

    static class GCSClient extends AbstractStorageClient {
        @Override protected String label() { return "GCS"; }
    }

    static class AzureBlobClient extends AbstractStorageClient {
        @Override protected String label() { return "Azure Blob"; }
    }

    // ── STATIC FACTORY ───────────────────────────────────────────────────────
    static class StorageClientFactory {
        public static StorageClient create(String provider) {
            return switch (provider.toLowerCase()) {
                case "aws"   -> new AWSS3Client();
                case "gcs"   -> new GCSClient();
                case "azure" -> new AzureBlobClient();
                default      -> throw new IllegalArgumentException("Unknown provider: " + provider);
            };
        }
    }

    // ════════════════════════════════════════════════════════════════════════
    // 4) ABSTRACT FACTORY — CloudFactory
    // ════════════════════════════════════════════════════════════════════════
    // A StorageClient and a Logger must always come from the SAME cloud — you
    // never want an AWS client paired with a GCS logger. CloudFactory bundles
    // them into one matched family; CloudApp only ever sees the interfaces.
    // ════════════════════════════════════════════════════════════════════════

    // ── ABSTRACT PRODUCT: Logger ─────────────────────────────────────────────
    interface Logger {
        void info(String msg);
        void error(String msg);
    }

    static abstract class AbstractLogger implements Logger {
        protected abstract String prefix();

        @Override public void info(String msg)  { System.out.println("[" + prefix() + "] INFO: " + msg); }
        @Override public void error(String msg) { System.out.println("[" + prefix() + "] ERROR: " + msg); }
    }

    // ── CONCRETE PRODUCTS: Logger family ─────────────────────────────────────
    static class AWSLogger extends AbstractLogger {
        @Override protected String prefix() { return "AWSLogger"; }
    }

    static class GCSLogger extends AbstractLogger {
        @Override protected String prefix() { return "GCSLogger"; }
    }

    static class AzureLogger extends AbstractLogger {
        @Override protected String prefix() { return "AzureLogger"; }
    }

    // ── ABSTRACT FACTORY ─────────────────────────────────────────────────────
    interface CloudFactory {
        StorageClient createStorageClient();
        Logger createLogger();
        String name();
    }

    // ── CONCRETE FACTORIES — one per cloud, always matched ───────────────────
    static class AWSCloudFactory implements CloudFactory {
        @Override public StorageClient createStorageClient() { return new AWSS3Client(); }
        @Override public Logger createLogger()                { return new AWSLogger(); }
        @Override public String name()                        { return "AWSCloudFactory"; }
    }

    static class GCSCloudFactory implements CloudFactory {
        @Override public StorageClient createStorageClient() { return new GCSClient(); }
        @Override public Logger createLogger()                { return new GCSLogger(); }
        @Override public String name()                        { return "GCSCloudFactory"; }
    }

    static class AzureCloudFactory implements CloudFactory {
        @Override public StorageClient createStorageClient() { return new AzureBlobClient(); }
        @Override public Logger createLogger()                { return new AzureLogger(); }
        @Override public String name()                        { return "AzureCloudFactory"; }
    }

    // ── CLIENT ───────────────────────────────────────────────────────────────
    // Depends only on CloudFactory + the abstract StorageClient/Logger
    // interfaces, plus ConfigManager for the active region. It never imports
    // AWSS3Client, GCSLogger, or any other concrete class directly.
    static class CloudApp {
        private final CloudFactory factory;

        CloudApp(CloudFactory factory) {
            this.factory = factory;
        }

        void run(StorageRequest request) {
            StorageClient client = factory.createStorageClient();
            Logger logger = factory.createLogger();
            String region = ConfigManager.getInstance().getRegion();
            boolean isUpload = "upload".equalsIgnoreCase(request.getOperation());

            System.out.println("\n[CloudApp using " + factory.name() + "]");
            logger.info((isUpload ? "Uploading " : "Downloading ")
                    + request.getBucket() + "/" + request.getKey()
                    + " in region " + region);

            if (isUpload) {
                client.upload(request);
            } else {
                client.download(request);
            }
            logger.info(isUpload ? "Upload complete" : "Download complete");
        }
    }

    // ════════════════════════════════════════════════════════════════════════
    // CLIENT CODE — putting all 4 patterns together
    // ════════════════════════════════════════════════════════════════════════
    public static void main(String[] args) {
        System.out.println("=== CLOUD STORAGE SDK ===\n");

        // ---- Singleton ----
        ConfigManager config = ConfigManager.getInstance();
        config.setProvider("AWS");
        config.setRegion("us-east-1");
        config.setDebug(true);
        System.out.println(config.summary());
        System.out.println("Same instance everywhere? " + (config == ConfigManager.getInstance()));

        // ---- Builder ----
        System.out.println("\n== Builder: StorageRequest ==");
        StorageRequest uploadRequest = new StorageRequest.Builder("my-bucket", "photo.png", "upload")
                .contentType("image/png")
                .sizeBytes(2048)
                .encrypted(true)
                .build();
        System.out.println("Built: " + uploadRequest);

        try {
            new StorageRequest.Builder("", "photo.png", "upload").build();
        } catch (IllegalStateException e) {
            System.out.println("Validation rejected bad request: " + e.getMessage());
        }

        // ---- Factory Method ----
        System.out.println("\n== Factory Method: StorageClientFactory ==");
        for (String provider : new String[]{"aws", "gcs", "azure"}) {
            StorageClient client = StorageClientFactory.create(provider);   // caller never writes `new`
            client.upload(uploadRequest);
        }

        // ---- Abstract Factory + Client ----
        System.out.println("\n== Abstract Factory: CloudFactory + CloudApp ==");

        new CloudApp(new AWSCloudFactory()).run(uploadRequest);

        // Switching the active cloud is just re-pointing the Singleton config
        // and swapping the factory — CloudApp itself doesn't change.
        config.setProvider("GCS");
        config.setRegion("us-central1");
        new CloudApp(new GCSCloudFactory()).run(uploadRequest);

        config.setProvider("Azure");
        config.setRegion("westeurope");
        new CloudApp(new AzureCloudFactory()).run(uploadRequest);

        System.out.println("\n── Key Takeaways ──");
        System.out.println("  • ConfigManager: one mutable instance, shared everywhere (Singleton)");
        System.out.println("  • StorageRequest: immutable once built, validated before construction (Builder)");
        System.out.println("  • StorageClientFactory: caller never writes `new AWSS3Client()` (Factory Method)");
        System.out.println("  • CloudFactory: StorageClient + Logger are always created as a MATCHED PAIR (Abstract Factory)");
        System.out.println("  • CloudApp depends only on interfaces — zero concrete cloud imports");
    }
}
