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