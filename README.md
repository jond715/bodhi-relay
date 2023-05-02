# [bodhi-relay](https://github.com/jond715/bodhi-relay)
This is a [nostr](https://github.com/fiatjaf/nostr) relay, written in Java.

## Features

NIPs with a relay-specific implementation are listed here.

- [x] NIP-01: Basic protocol flow description
- [x] NIP-02: Contact list and petnames
- [x] NIP-04: Encrypted Direct Message
- [x] NIP-09: Event deletion
- [x] NIP-11: Relay information document
- [x] NIP-12: Generic tag queries
- [x] NIP-15: End of Stored Events Notice
- [x] NIP-16: Event Treatment
- [x] NIP-20: Command Results
- [x] NIP-22: Event `created_at` Limits
- [x] NIP-26: Delegated Event Signing
- [x] NIP-28: Public Chat
- [x] NIP-33: Parameterized Replaceable Events
- [x] NIP-40: Expiration Timestamp

## Requirements
- JDK 17
- SQLite
- Spring boot 3.0

## Build instructions

To build, run `mvn package`

