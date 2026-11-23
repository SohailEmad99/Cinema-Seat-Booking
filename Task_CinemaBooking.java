import java.util.*;

/*
 =======================================================================
  TASK: Cinema Seat Booking  -  a HashMap with your own class as the key
 =======================================================================

 A cinema hall has rows A to E, with seats 1 to 6 in each row.
 You will store the bookings in a HashMap:

     key   = Seat     (a class you finish below, for example B5)
     value = String   (the name of the customer who booked it)

 STEP 1  Complete Parts A to G in main(). Each part says what to print.
 STEP 2  Run the program BEFORE writing equals() and hashCode().
         Some results will be wrong. That is expected - write down which ones.
 STEP 3  Write equals() and hashCode() in the Seat class.
 STEP 4  Run it again and compare with the EXPECTED OUTPUT below.
 STEP 5  Answer the questions at the bottom of this file.

 RULES
   - Every time you use a seat, create a NEW object: new Seat('B', 5).
     A real program works the same way: when a customer types "B5",
     the program makes a new Seat object for it.
   - Do not change printChart().

 EXPECTED OUTPUT (after STEP 3)
 The order inside { } may be different in your output. HashMap has no order.

   Bookings:           {A1=Ali, C3=Mona, A2=Sara, B5=Omar}
   Who booked C3:      Mona
   Is A2 taken:        true
   Is E6 taken:        false
   Youssef -> B5:      refused, taken by Omar
   After Ali moved:    {C3=Mona, A2=Sara, B5=Omar, D2=Ali}
   Hana -> E1:         refused, seat is broken
   Seats booked:       4

        1  2  3  4  5  6
   A    .  X  .  .  .  .
   B    .  .  .  .  X  .
   C    .  .  X  .  .  .
   D    .  X  .  .  .  .
   E    #  #  .  .  .  .
*/

class Task_CinemaBooking {

    // A seat in the cinema hall, for example B5 = row B, seat number 5.
    // static so that main() can create Seat objects directly.
    static class Seat {
        final char row;     // 'A' to 'E'
        final int number;   // 1 to 6

        Seat(char row, int number) {
            this.row = row;
            this.number = number;
        }

        @Override
        public String toString() {
            return "" + row + number;
        }

        // TODO 1: override equals()
        //   Two Seat objects are the same seat when the row AND the number are the same.
        //   The parameter must be of type Object, not Seat. Write @Override above it.
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;                          // same object in memory
            if (o == null || getClass() != o.getClass()) return false; // null or not a Seat
            Seat other = (Seat) o;                               // safe cast after the check
            return this.row == other.row && this.number == other.number;
        }

        // TODO 2: override hashCode()
        //   Equal seats must return the same number.
        //   Use the same fields you used in equals().
        @Override
        public int hashCode() {
            return Objects.hash(row, number);                    // same fields as equals()
        }
    }

    public static void main(String[] args) {

        // Key = Seat, Value = name of the customer who booked it
        HashMap<Seat, String> bookings = new HashMap<>();
        HashSet<Seat> broken = new HashSet<>();

        // Part A: book these seats using put()
        //   A1 -> Ali     A2 -> Sara     B5 -> Omar     C3 -> Mona
        // Print:  Bookings:           <the map>
        bookings.put(new Seat('A', 1), "Ali");
        bookings.put(new Seat('A', 2), "Sara");
        bookings.put(new Seat('B', 5), "Omar");
        bookings.put(new Seat('C', 3), "Mona");
        System.out.println("Bookings:           " + bookings);

        // Part B: who booked seat C3?  Use get().
        // Print:  Who booked C3:      <name>
        String owner = bookings.get(new Seat('C', 3));
        System.out.println("Who booked C3:      " + owner);

        // Part C: are seats A2 and E6 taken?  Use containsKey().
        // Print:  Is A2 taken:        <true or false>
        //         Is E6 taken:        <true or false>
        System.out.println("Is A2 taken:        " + bookings.containsKey(new Seat('A', 2)));
        System.out.println("Is E6 taken:        " + bookings.containsKey(new Seat('E', 6)));

        // Part D: Youssef wants seat B5. Use putIfAbsent(), so a booked seat is not replaced.
        //   putIfAbsent() returns null if the seat was free,
        //   otherwise it returns the name that is already there.
        // Print:  Youssef -> B5:      booked
        //    or:  Youssef -> B5:      refused, taken by <name>
        String existing = bookings.putIfAbsent(new Seat('B', 5), "Youssef");
        if (existing == null) {
            System.out.println("Youssef -> B5:      booked");
        } else {
            System.out.println("Youssef -> B5:      refused, taken by " + existing);
        }

        // Part E: Ali moves from seat A1 to seat D2.  Use remove() and put().
        // Print:  After Ali moved:    <the map>
        bookings.remove(new Seat('A', 1));
        bookings.put(new Seat('D', 2), "Ali");
        System.out.println("After Ali moved:    " + bookings);

        // Part F: seats E1 and E2 are broken. Add them to the HashSet "broken".
        //   Hana wants seat E1. Book it only if it is not broken.  Use contains().
        // Print:  Hana -> E1:         booked
        //    or:  Hana -> E1:         refused, seat is broken
        broken.add(new Seat('E', 1));
        broken.add(new Seat('E', 2));
        if (broken.contains(new Seat('E', 1))) {
            System.out.println("Hana -> E1:         refused, seat is broken");
        } else {
            bookings.put(new Seat('E', 1), "Hana");
            System.out.println("Hana -> E1:         booked");
        }

        // Part G: how many seats are booked?  Use size().
        // Print:  Seats booked:       <number>
        System.out.println("Seats booked:       " + bookings.size());

        // GIVEN - prints the seating chart
        System.out.println();
        printChart(bookings, broken);
    }

    // GIVEN - do not change.  Prints the hall:  X = booked   # = broken   . = free
    static void printChart(HashMap<Seat, String> bookings, HashSet<Seat> broken) {
        System.out.println("     1  2  3  4  5  6");
        for (char row = 'A'; row <= 'E'; row++) {
            System.out.print(row + "  ");
            for (int number = 1; number <= 6; number++) {
                Seat seat = new Seat(row, number);
                if (bookings.containsKey(seat)) {
                    System.out.print("  X");
                } else if (broken.contains(seat)) {
                    System.out.print("  #");
                } else {
                    System.out.print("  .");
                }
            }
            System.out.println();
        }
    }
}

/*
 QUESTIONS - write your answers here

 Q1.Why did we have to override hashcode() and equals()?
 A1. HashMap does not compare keys by their fields. It uses hashCode() to choose
     the bucket where the key lives, and then equals() to compare the key with the
     keys inside that bucket. The default versions inherited from Object compare
     MEMORY ADDRESSES, so new Seat('B', 5) and another new Seat('B', 5) are two
     different keys even though they describe the same seat. Overriding both
     methods tells Java: "two seats are the same when row AND number are the same",
     so put/get/containsKey/remove/putIfAbsent work with a freshly created Seat.

 Q2. Delete your hashCode() but keep equals(), and run again.
     What happens? Why?
 A2. The results become wrong again (get() returns null, containsKey() returns
     false, remove() does nothing, putIfAbsent() never refuses, the chart is empty,
     and the size is bigger than expected).
     Reason: without hashCode() we use Object.hashCode(), which is based on the
     object's identity, so two equal seats get different hash codes. HashMap then
     looks in the wrong bucket and never even calls equals(). The contract says:
     if a.equals(b) is true then a.hashCode() == b.hashCode() must be true.
     Overriding equals() alone breaks this contract.

 Q3. Why are row and number final? What could go wrong if the number
     of a booked seat could be changed after it was put in the map?
 A3. The hash code depends on row and number. If the number of a key could change
     after put(), its hash code would change, but the entry would still be stored in
     the OLD bucket. Later get()/containsKey()/remove() would compute the NEW hash,
     search another bucket, and fail to find the entry. The entry becomes "lost"
     (still counted in size() and printed in the map, but impossible to reach),
     and the same seat could be booked twice. final makes Seat immutable, so its
     hash code never changes while it is a key.

 Q4. Change equals(Object o) to equals(Seat o). What happens?
     Then also remove @Override and run again. What happens now?
 A4. With @Override: compile error - "method does not override or implement a
     method from a supertype", because the real method is equals(Object), and
     equals(Seat) has a different signature.
     Without @Override: it compiles, but equals(Seat) is only an OVERLOAD, not an
     override. HashMap/HashSet call equals(Object), which is still Object's
     identity-based version, so the keys are never equal and the results are wrong
     again (like Step 2). This is exactly why we always write @Override.

 BONUS. Replace the whole Seat class with this one line:
            record Seat(char row, int number) {}
        Run it. What happens, and why?
 BONUS ANSWER:
     It compiles and the logic works correctly (get, containsKey, putIfAbsent,
     remove, the HashSet and the chart all behave like the correct version),
     because a record automatically generates equals(), hashCode() and toString()
     from its components, and its fields are automatically private final.
     The only difference is the printed text: the generated toString() gives
     Seat[row=A, number=1] instead of A1, so the maps print as
     {Seat[row=A, number=1]=Ali, ...}. To get "A1" back, override toString()
     inside the record.
*/
