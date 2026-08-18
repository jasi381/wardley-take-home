# Data Types in Java

## Primitive Data Types (8 total)
These store simple values directly in memory.

| Type    | Size    | Example Value     | Use Case          |
|---------|---------|-------------------|-------------------|
| byte    | 1 byte  | 127               | Small numbers     |
| short   | 2 bytes | 32000             | Medium numbers    |
| int     | 4 bytes | 1000000           | Most integers     |
| long    | 8 bytes | 9999999999L       | Very large numbers|
| float   | 4 bytes | 3.14f             | Decimal (less precise) |
| double  | 8 bytes | 3.14159           | Decimal (more precise) |
| char    | 2 bytes | 'A'               | Single character  |
| boolean | 1 bit   | true / false      | Conditions        |

> **Key rule:** Primitives CANNOT be null. They always have a default value.

## Non-Primitive (Reference) Data Types
These store a **reference (address)** to an object in memory.

- `String` — sequence of characters
- `int[]` — array of integers
- Any class you define (e.g., `Student`, `Car`)

> **Key rule:** Non-primitives CAN be null. Default value is `null`.

## Memory Analogy
- **Primitive** → like a box that holds the value directly
- **Reference** → like a label/address pointing to where the object lives
