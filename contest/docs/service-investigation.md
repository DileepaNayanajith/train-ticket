# Normative source investigation

The offline `source/train-ticket-investigation.zip` contains original selected service folders and the TrainTicket LICENSE, exported directly from Git. Extract it to inspect the paths below. Its hash and the selected file hashes are recorded in `contract-lock.json`. The repository is [DileepaNayanajith/train-ticket](https://github.com/DileepaNayanajith/train-ticket); the offline archive is authoritative if a remote branch differs.

Use the immutable commit `ea6d70dd2cc78bc80aacac128c6d3bdcff7a7517`, not a moving upstream branch. Resolve these exact selectors before implementing:

| Question | File relative to repository | Selector |
|---|---|---|
| Which two classes and numeric codes? | ts-common/src/main/java/edu/fudan/common/entity/SeatClass.java | FIRSTCLASS and SECONDCLASS enum entries |
| Which status codes? | ts-common/src/main/java/edu/fudan/common/entity/OrderStatus.java | NOTPAID, PAID, CHANGE, CANCEL entries |
| Initial status? | ts-preserve-service/src/main/java/preserve/service/PreserveServiceImpl.java | assignment `order.setStatus` in preserve flow |
| Rebook eligibility? | ts-rebook-service/src/main/java/rebook/service/RebookServiceImpl.java | `rebook`, initial order status guard and NOTPAID/PAID/CHANGE branches |
| Rebook success status? | same file | assignment `order.setStatus` in successful rebook flow |
| Cancellable statuses? | ts-cancel-service/src/main/java/cancel/service/CancelServiceImpl.java | `cancelOrder`, first status disjunction after successful order lookup |

These selected constants and guards are part of the specification; all other upstream behavior is excluded unless explicitly stated. Write down the resolved transition table and use Oracle to test your interpretation. This makes original code investigation necessary to recover the complete contract, without relying on undocumented bugs, hidden endpoints, or ambiguous production failures. Someone may share the recovered rules; source investigation cannot be technically enforced by an offline judge.

Branching history and segment capacity are contest additions. Do not import upstream money, time, UUID, seat-selection heuristics, or G/D routing rules. In particular, upstream `ts-order-service` seat aggregation and this contest's CHANGE occupancy may differ.
