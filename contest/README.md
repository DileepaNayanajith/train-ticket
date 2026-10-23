# Forked Reservation Ledger

Setup and service access for contestants. The problem statement, samples and submission rules are provided on HackerRank.

## Setup

- Connect to the contestant university Wi-Fi/LAN. Internet access is also needed for HackerRank.
- Install a Java 17 JDK and `grpcurl` for your OS:

  | OS | grpcurl setup |
  |---|---|
  | Windows | Download the matching Windows ZIP from [official releases](https://github.com/fullstorydev/grpcurl/releases), extract it, and add the folder containing `grpcurl.exe` to `PATH`. Open a new PowerShell window. |
  | Linux | Download the matching Linux archive/package from [official releases](https://github.com/fullstorydev/grpcurl/releases). Extract/install it and put `grpcurl` on `PATH`. |
  | macOS | With Homebrew: `brew install grpcurl`, or download the matching macOS archive from [official releases](https://github.com/fullstorydev/grpcurl/releases). |
- The public CA certificate `train-ledger-ca.crt` is included in this folder. No team token, account or SSH access is needed for the services.
- You do not need to deploy TrainTicket, install Docker, or access the VM through SSH.

```text
git clone --depth 1 --branch contest/forked-reservation-ledger https://github.com/DileepaNayanajith/train-ticket.git
cd train-ticket/contest
java -version
grpcurl -version
```

Git is optional: you can download the contest branch as a ZIP instead.

## Check access

Run from the `contest` folder. The same command works in Windows PowerShell, Linux and macOS terminals:

```text
grpcurl -cacert train-ledger-ca.crt 10.50.227.204:52051 grpc.health.v1.Health/Check
```

Expected response: `{"status":"SERVING"}`. Use the supplied CA certificate; do not disable TLS verification.

## Services

Problem ID: `train_ledger_01`. Contract: [proto/challenge.proto](proto/challenge.proto).

| Service | Endpoint | RPC |
|---|---|---|
| Topology | `10.50.227.204:52051` | `TopologyService/DescribeStructure` |
| Constraint | `10.50.227.204:52052` | `ConstraintService/GetConstraints` |
| Oracle | `10.50.227.204:52053` | `OracleService/QuerySample` |
| Validation | `10.50.227.204:52054` | `ValidationService/ValidateApproach` |

RPC names use the prefix `contest.ledger.v1.`. All four services are accessible without tokens. There are no per-team or per-IP call quotas; `calls_remaining: -1` means unlimited calls. Request-size and input limits still apply. See [request formats and service rules](docs/services.md).

## Files

- [gRPC contract](proto/challenge.proto)
- [Source investigation guide](docs/service-investigation.md) and [offline source](source/train-ticket-investigation.zip)
- [Java starter](starter/Solution.java)

TrainTicket source is pinned to `ea6d70dd2cc78bc80aacac128c6d3bdcff7a7517`. See [LICENSE](LICENSE) and [NOTICE](NOTICE) for attribution.
