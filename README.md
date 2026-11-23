# Cinema Seat Booking – Java HashMap Project

A Java console application that manages cinema seat bookings using a `HashMap` with a custom `Seat` class as its key. It also uses a `HashSet` to store broken seats.

## Project overview

The cinema has **5 rows (A–E)** and **6 seats per row (1–6)**.

- **HashMap<Seat, String> bookings** stores each booked seat and the customer's name.
- **HashSet<Seat> broken** stores seats that cannot be booked.
- `Seat` represents a seat using its row and seat number.
- `equals()` checks whether two `Seat` objects represent the same row and number.
- `hashCode()` generates a hash using the same two fields, allowing `HashMap` and `HashSet` to find equivalent newly created `Seat` objects correctly.
- `toString()` displays a seat in a short form such as `B5`.
- `printChart()` displays the hall: `X` means booked, `#` means broken, and `.` means free.

## Hardcoded example data

The program uses these sample bookings in `main()`:

| Seat | Customer |
|---|---|
| A1 | Ali |
| A2 | Sara |
| B5 | Omar |
| C3 | Mona |

The program also marks **E1** and **E2** as broken seats.

These are demonstration values written directly in the code. To change the sample bookings, edit the `bookings.put(...)` statements in `main()`. To change broken seats, edit the `broken.add(...)` statements.

## How the program works

1. Adds the four sample bookings to the `HashMap`.
2. Uses `get()` to find who booked C3.
3. Uses `containsKey()` to check whether A2 and E6 are booked.
4. Uses `putIfAbsent()` to try to book B5 for Youssef without replacing Omar's booking.
5. Uses `remove()` and `put()` to move Ali from A1 to D2.
6. Uses `HashSet.contains()` to reject Hana's request for broken seat E1.
7. Uses `size()` to print the number of booked seats.
8. Calls `printChart()` to display the final seating chart.

## Expected result

The exact order of entries inside the printed `{ ... }` map can vary because a `HashMap` does not guarantee iteration order.

```text
Who booked C3:      Mona
Is A2 taken:        true
Is E6 taken:        false
Youssef -> B5:      refused, taken by Omar
Hana -> E1:         refused, seat is broken
Seats booked:       4
```

In the final seating chart:
- A2, B5, C3, and D2 are booked.
- E1 and E2 are broken.
- All other seats are free.


## Output Screenshots

### 1. Program output and seating chart

<img src="./screenshots/program-output.jpeg" alt="Program output" width="600">

### 2. Questions and answers

<img src="./screenshots/questions-answers.jpeg" alt="Questions and answers" width="600">

### 3. Bonus: Java record answer

<img src="./screenshots/bonus-record-answer.jpeg" alt="Bonus record answer" width="600">


## Questions covered in the task

- **Q1 – Why override `equals()` and `hashCode()`?**  
  So two different `Seat` objects with the same row and number are treated as the same key or set element.

- **Q2 – What happens if `hashCode()` is removed?**  
  Equal seats may have different identity-based hash codes. Hash-based collections may search the wrong bucket, so lookups and other operations can fail.

- **Q3 – Why are `row` and `number` final?**  
  A key's hash-related fields should not change after it is added to a hash-based collection. Making them `final` keeps a `Seat` immutable.

- **Q4 – What happens with `equals(Seat)` instead of `equals(Object)`?**  
  It overloads rather than overrides `equals(Object)`. Hash-based collections call the `Object` signature, so the intended equality check is not used.

- **Bonus – What if `Seat` is a record?**  
  A record automatically provides `equals()`, `hashCode()`, and `toString()` based on its components. Its default printed format differs from the custom `A1` format.

## How to run

1. Open the project in IntelliJ IDEA.
2. Make sure a compatible JDK is configured.
3. Open `Task_CinemaBooking.java`.
4. Run the `main()` method.

The source file has no external library dependencies; it uses Java's built-in `java.util` classes.

## Repository structure

```text
CinemaSeatBooking/
├── Task_CinemaBooking.java
├── README.md
└── screenshots/
    ├── program-output.jpeg
    ├── questions-answers.jpeg
    └── bonus-record-answer.jpeg
```
