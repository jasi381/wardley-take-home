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
| `class2/s1_jdk_jre_jvm` | `com.bel.c22.class2.s1_jdk_jre_jvm` | `NOTES.md` + `JdkJreJvmDemo.java` — JDK/JRE/JVM |
| `class5/a_abstraction` | `com.bel.c22.class5.a_abstraction` | `notes.md` + `AbstractionDemo.java` — Abstraction |
| `class6/a_intro` | `com.bel.c22.class6.a_intro` | `notes.md` + `DesignPrinciplesDemo.java` — What are design principles |
| `class6/b_dry_kiss_yagni` | `com.bel.c22.class6.b_dry_kiss_yagni` | `notes.md` + `DryKissYagniDemo.java` — DRY / KISS / YAGNI |
| `class6/c_srp` | `com.bel.c22.class6.c_srp` | `notes.md` + `SrpDemo.java` — Single Responsibility Principle |
| `class6/d_ocp` | `com.bel.c22.class6.d_ocp` | `notes.md` + `OcpDemo.java` — Open/Closed Principle |
| `class6/e_lsp` | `com.bel.c22.class6.e_lsp` | `notes.md` + `LspDemo.java` — Liskov Substitution Principle |
| `class6/f_isp` | `com.bel.c22.class6.f_isp` | `notes.md` + `IspDemo.java` — Interface Segregation Principle |
| `class6/g_dip` | `com.bel.c22.class6.g_dip` | `notes.md` + `DipDemo.java` — Dependency Inversion Principle |

`.md` notes files live alongside their package's `.java` files (Maven
ignores non-`.java` sources under `src/main/java`), keeping each
topic's documentation next to its code.

### About the `*Demo.java` files

Only `class6/f_isp/IspDemo.java` was actually present on Google Drive.
Every other `*Demo.java` file listed above (`JdkJreJvmDemo`,
`AbstractionDemo`, `DesignPrinciplesDemo`, `DryKissYagniDemo`,
`SrpDemo`, `OcpDemo`, `LspDemo`, `DipDemo`) **did not exist on Drive**
— confirmed by an account-wide search that found exactly one `.java`
file in the whole Drive account. They were written from scratch to
match each topic's `notes.md` (same before/after teaching style as
`IspDemo.java`), at the user's explicit request, and are new authored
code rather than an import from Drive. All of them compile and were
run to verify their output.

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
