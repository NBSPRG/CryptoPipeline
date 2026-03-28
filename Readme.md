## What is CryptoPipeline?

CryptoPipeline is a Kotlin/Spring Boot service that secures data through a configurable crypto workflow.
It takes an input payload and processes it in ordered stages:

1. **Canonicalize** - normalize input into a deterministic string format
2. **Hash** - generate a digest (for example SHA-256)
3. **Sign** - apply RSA digital signature
4. **Encrypt** - encrypt the signed data (AES/RSA/DES supported)
5. **Encode** - return output in Base64 or Hex form

The project also includes the reverse validation path (`decode -> decrypt -> verify`) and supports
feature flags so canonicalization, signing, and encryption can be turned on/off at runtime.

In short, CryptoPipeline is designed to provide a reusable, pluggable, and testable end-to-end
data protection pipeline for billing/payment style payloads.

## Backend Only

This project is currently backend-only and exposes Spring Boot APIs for the crypto pipeline.

### Run

```bash
./gradlew bootRun
```

On Windows:

```powershell
.\gradlew.bat bootRun
```

The service starts on `http://localhost:8080`.

### Endpoints

- `GET /api/health`
- `POST /api/v1/pipeline/execute`
- `POST /api/v1/pipeline/execute/bulk`

### Notes

- H2 persists data under `data/cryptodb`.
- The H2 console is enabled.
- DevCycle configuration is controlled through `DVC_SERVER_KEY`.
