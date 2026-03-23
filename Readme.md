## What is CryptoPipeline?

CryptoPipeline is a Kotlin/Spring Boot service that secures data through a configurable crypto workflow.
It takes an input payload and processes it in ordered stages:

1. **Canonicalize** – normalize input into a deterministic string format
2. **Hash** – generate a digest (for example SHA-256)
3. **Sign** – apply RSA digital signature
4. **Encrypt** – encrypt the signed data (AES/RSA/DES supported)
5. **Encode** – return output in Base64 or Hex form

The project also includes the reverse validation path (**decode → decrypt → verify**) and supports
feature flags so canonicalization, signing, and encryption can be turned on/off at runtime.

In short, CryptoPipeline is designed to provide a reusable, pluggable, and testable end-to-end
data protection pipeline for billing/payment style payloads.

## Changelog

### Phase 1 — Forward Pipeline
Built the core signing pipeline: canonicalization (deterministic string from `BillingEvent`/`Payment`),
hashing (SHA-256/512/MD5), RSA signing, encryption (AES/RSA/DES), and Base64/Hex encoding.
Introduced factory registry pattern for pluggable algorithm selection.

### Phase 2 — Reverse Pipeline + Feature Flags
Built the inverse verification flow: `decode → decrypt → RSA verify`.
Integrated DevCycle feature flags to toggle canonicalization, signing, and encryption stages
at runtime without redeployment. Added static fallback so pipeline runs locally without any
external dependency.
