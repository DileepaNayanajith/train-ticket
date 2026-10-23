# Reconnaissance services

Problem ID: `train_ledger_01`. The complete method/message contract is `proto/challenge.proto`. The services are four independent processes and do not call one another.

| Service | Method | University LAN endpoint |
|---|---|---|
| TopologyService | DescribeStructure | `10.50.227.204:52051` |
| ConstraintService | GetConstraints | `10.50.227.204:52052` |
| OracleService | QuerySample | `10.50.227.204:52053` |
| ValidationService | ValidateApproach | `10.50.227.204:52054` |

Connect through the contestant university Wi-Fi/LAN. HackerRank submissions require Internet access. Use the included `train-ledger-ca.crt`. No authentication token is required. Use verified TLS for every service; do not use `-plaintext` or disable certificate verification.

Run commands from the `contest` folder with the included `train-ledger-ca.crt`. For a Topology request, save this as `request.json`:

```json
{"problem_id":"train_ledger_01"}
```

Linux / macOS:

```bash
grpcurl -cacert train-ledger-ca.crt -d @ 10.50.227.204:52051 contest.ledger.v1.TopologyService/DescribeStructure < request.json
```

Windows PowerShell:

```powershell
Get-Content -Raw request.json | grpcurl -cacert train-ledger-ca.crt -d '@' 10.50.227.204:52051 contest.ledger.v1.TopologyService/DescribeStructure
```

All four services are token-free. There are no per-team or per-IP call quotas. Oracle and Validation return `calls_remaining: -1` to indicate unlimited calls. Standard `grpc.health.v1.Health/Check` is read-only. Generated clients can use the supplied proto and CA certificate.

## Oracle

Sample input must be <=16384 UTF-8 bytes, with 1..10 trains, 2..20 stations per train and 1..20 operations. It follows the same lexical grammar and operation semantics as the offline judge. Definitions and parent links must be valid. An invalid journey reference remains a well-formed semantic test.

Malformed syntax, unsupported operation names, invalid/duplicate definitions, invalid parents, negative numbers, non-ASCII numeric characters, signed numbers, grouped digits and trailing tokens return INVALID_ARGUMENT. A successful result includes `expected_output` and `calls_remaining: -1`. Concurrent responses can arrive out of order.

## Validation

`approach` is a nonempty ASCII label after trimming, at most 200 characters. `complexity` is nonempty after normalization, at most 80 normalized characters and 512 raw characters. Printable ASCII and ASCII whitespace are accepted; whitespace is removed from complexity and letters are converted to uppercase using fixed locale-independent rules. No source code is executed.

| Claimed signature after normalization | Coarse response |
|---|---|
| O(Q*S), O(Q^2), O((S+Q)^2) | LIKELY_TOO_SLOW |
| O((S+Q)LOGS), O((S+Q)LOG(S)), O(S+QLOGS), O(S+QLOG(S)), O(S+Q) | COMPLEXITY_PLAUSIBLE |
| Other well-formed nonempty signatures | INSUFFICIENT_EVIDENCE |

S means total elementary route segments; Q means operations. Feedback assesses only a self-reported bound. It does not verify correctness, implementation performance or a hidden verdict.

## Errors and availability

| gRPC status | Meaning |
|---|---|
| NOT_FOUND | Unknown problem ID. |
| INVALID_ARGUMENT | Request content is malformed. |
| RESOURCE_EXHAUSTED | An inbound transport/resource limit was exceeded; this is not a call quota. |
| UNAVAILABLE | Service is unavailable. |

The Oracle transport envelope is capped at 32768 bytes; other service messages are capped at 4096 bytes. Request metadata is capped at 8192 bytes. A transport rejection never runs the method. A normal well-formed Oracle request within its documented 16384-byte sample limit fits its envelope.

Offline submissions do not call the services and remain independent of reconnaissance outages.
