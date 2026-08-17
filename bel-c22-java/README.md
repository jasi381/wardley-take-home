# BEL C22 Java Course Repository

This is a Maven-ized copy of the **BEL C22 code repository** — a Java
training/course repo — imported from the Google Drive folder
`BEL C22 code repository`. The original Drive tree was organized as
`classN/<topic>/...`; that layout has been mirrored into a standard
Maven `src/main/java` package structure under `com.bel.c22`.

## Building

```
mvn -q -f pom.xml compile
```

Java 17, no external dependencies (nothing in the source tree required
any).

## Topics imported

| Drive path | Package | Contents |
|---|---|---|
| `class2/s1_jdk_jre_jvm` | `com.bel.c22.class2.s1_jdk_jre_jvm` | `NOTES.md` — JDK/JRE/JVM notes |
| `class5/a_abstraction` | `com.bel.c22.class5.a_abstraction` | `notes.md` — Abstraction notes (references an `AbstractionDemo.java` that was never actually uploaded to Drive; not fabricated here) |
| `class6/a_intro` | `com.bel.c22.class6.a_intro` | `notes.md` — What are design principles |
| `class6/b_dry_kiss_yagni` | `com.bel.c22.class6.b_dry_kiss_yagni` | `notes.md` — DRY / KISS / YAGNI |
| `class6/c_srp` | `com.bel.c22.class6.c_srp` | `notes.md` — Single Responsibility Principle |
| `class6/d_ocp` | `com.bel.c22.class6.d_ocp` | `notes.md` — Open/Closed Principle |
| `class6/e_lsp` | `com.bel.c22.class6.e_lsp` | `notes.md` — Liskov Substitution Principle |
| `class6/f_isp` | `com.bel.c22.class6.f_isp` | `IspDemo.java` (compilable demo) + `notes.md` — Interface Segregation Principle |
| `class6/g_dip` | `com.bel.c22.class6.g_dip` | `notes.md` — Dependency Inversion Principle |

`.md` notes files live alongside their package's `.java` files (Maven
ignores non-`.java` sources under `src/main/java`), keeping each
topic's documentation next to its code.

## Topic folders found empty on Drive (skipped, nothing fabricated)

- `class1`
- `class3/.claude` (tooling config only, no course content)
- `class4`
- `class5/b_interfaces`
- `class8`
- `class9/warmup_session`
- `c_access_modifiers` (top-level sibling folder)

These folders exist in the Drive tree but had no files in them at the
time of import.
