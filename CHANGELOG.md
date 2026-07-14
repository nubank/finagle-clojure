# Changelog

## 1.0.1 (2026-07-14)
Dependency-security patch release — CVE-clearing pins over Finagle 24.2.0's frozen transitive set. No API or behavior changes.
- **netty** pinned to `4.1.135.Final` across the aligned set (clears 56 alerts).
- **jackson** core/databind/annotations/module-scala_2.13 aligned at `2.18.9` (GHSA-5jmj-h7xm-6q6v). All four move together because `jackson-module-scala` enforces a matching `jackson-databind` version at runtime.
- **scala-library** `2.13.16` (critical alerts).
- **snakeyaml** `2.4` (CVE-2022-1471 RCE).
- **aws-java-sdk-s3/core** `1.12.797` (drops vulnerable transitive `ion-java`).
- **plexus-utils** `3.6.1`, **guava** `32.0.1-jre`, **httpclient** `4.5.14`, **gson** `2.10.1`.
- `libthrift` remains `0.12.0`: scrooge `24.2.0` codegen requires the pre-`0.13` `TProcessor.process` signature, so the patched `0.23.0` is not adoptable (vulnerable TLS transport is unused — finagle-clojure does TLS via Finagle's Netty transport).

## 1.0.0 (2026-07-03)
- Bump Finagle and scrooge to `24.2.0` (the final Finagle release) and move the built modules (`core`, `http`, `thrift`, `lein-finagle-clojure`) to Scala 2.13 artifacts. `finagle-clojure-template` is not part of the build and remains stale (it predates the Nubank fork).
- **Breaking**: remove `finagle-clojure.builder.server` and `finagle-clojure.builder.client`. Finagle removed the underlying `ServerBuilder` API in `21.8.0`. Use the stack pattern instead (`finagle-clojure.http.server`/`finagle-clojure.http.client`); `common-finagle >= 11.38.0` already does.
- Port `finagle-clojure.scala` to `scala.jdk.javaapi.CollectionConverters` (`scala.collection.JavaConversions` no longer exists in Scala 2.13).
- Pin `libthrift` to `0.12.0`: scrooge `24.2.0` still generates `boolean TProcessor.process`, which is incompatible with the `void` signature introduced by THRIFT-4725 in `0.13.0` (the same issue behind the `0.10.1` rollback below).

## 0.10.1
- Rollbacking thrift bump until we generate a new release with a compatible code with thrift `0.13.0`

## 0.10.0
We are bumping `org.apache.thrift/libthrift` on `finagle-cloure/thrift` due to security issues with the current version.
The nearest version without known vulnerabilities is `0.13.0`.

## Breaking changes from 0.10.0 to 0.13.0

### 0.11.0 -> 0.12.0

- THRIFT-4529 - Rust enum variants are now camel-cased instead of uppercased to conform to Rust naming conventions
- THRIFT-4448 - Support for golang 1.6 and earlier has been dropped.
- THRIFT-4474 - PHP now uses the PSR-4 loader by default instead of class maps.
- THRIFT-4532 - method signatures changed in the compiler's t_oop_generator.
- THRIFT-4648 - The C (GLib) compiler's handling of namespaces has been improved.

### 0.12.0 -> 0.13.0

- THRIFT-4743 - compiler: removed the plug-in mechanism
- THRIFT-4720 - cpp: C++03/C++98 support has been removed; also removed boost as a runtime dependency
- THRIFT-4730 - cpp: BoostThreadFactory, PosixThreadFactory, StdThreadFactory removed
- THRIFT-4732 - cpp: CMake build changed to use BUILD_SHARED_LIBS
- THRIFT-4735 - cpp: Removed Qt4 support
- THRIFT-4740 - cpp: Use std::chrono::duration for timeouts
- THRIFT-4762 - cpp: TTransport::getOrigin() is now const
- THRIFT-4702 - java: class org.apache.thrift.AutoExpandingBuffer is no longer public
- THRIFT-4709 - java: changes to UTF-8 handling require JDK 1.7 at a minimum
- THRIFT-4712 - java: class org.apache.thrift.ShortStack is no longer public
- THRIFT-4725 - java: change return type signature of 'process' methods
- THRIFT-4805 - java: replaced TSaslTransportException with TTransportException
- THRIFT-2530 - java: TIOStreamTransport's "isOpen" now returns false after "close" is called
- THRIFT-4675 - js: now uses node-int64 for 64 bit integer constants
- THRIFT-4841 - delphi: old THTTPTransport is now TMsxmlHTTPTransport
- THRIFT-4536 - rust: convert from try-from crate to rust stable (1.34+), re-export ordered-float

## [0.9.0-NUBANK]
-Bump Finagle version to 20.8.1

## [0.8.0-NUBANK]
-Bump Finagle version to 19.12.0
-Remove MySql and ThriftMux modules

