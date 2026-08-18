# Practice Problem — Class 9: Creational Design Patterns

## Scenario
**Cloud Storage SDK** — a unified client for AWS S3, Google Cloud Storage, and Azure Blob.

## What You'll Build

| Pattern | Class/Interface | Purpose |
|---------|----------------|---------|
| Singleton | `ConfigManager` | One global config: provider, region, debug flag |
| Builder | `StorageRequest` | Immutable request object with many optional fields |
| Factory Method | `StorageClientFactory` | Create the right `StorageClient` from a string |
| Abstract Factory | `CloudFactory` | Create matched `StorageClient` + `Logger` per provider |

## Suggested Order
1. **`ConfigManager`** (Singleton) — no dependencies, build first
2. **`StorageRequest`** (Builder) — standalone data object, needed by clients
3. **`StorageClientFactory`** (Factory) — creates `StorageClient` from provider string
4. **`CloudFactory`** (Abstract Factory) — bundles `StorageClient` + `Logger` as a family
5. **`CloudApp`** (Client) — ties it all together

## Hints
- `ConfigManager` should use Bill Pugh or Double-Checked Locking.
- `StorageRequest.Builder` must accept `bucket`, `key`, `operation` in its constructor.
- `StorageClientFactory.create("aws")` → `AWSS3Client`.
- `AWSCloudFactory` creates `AWSS3Client` + `AWSLogger` together (always matched).
- `CloudApp` takes a `CloudFactory` and uses `ConfigManager` to read the region for logging.

## Expected Output (approximate)
```
Config: provider=AWS, region=us-east-1, debug=true

[CloudApp using AWSCloudFactory]
[AWSLogger] INFO: Uploading my-bucket/photo.png in region us-east-1
[AWS S3] Uploading my-bucket/photo.png (image/png, 2048 bytes, encrypted=true)
[AWSLogger] INFO: Upload complete

[CloudApp using GCSCloudFactory]
[GCSLogger] INFO: Uploading my-bucket/photo.png in region us-central1
[GCS] Uploading my-bucket/photo.png (image/png, 2048 bytes, encrypted=true)
[GCSLogger] INFO: Upload complete
```

## Solution
See `solution/` package.
