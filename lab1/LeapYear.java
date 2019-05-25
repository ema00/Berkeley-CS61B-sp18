/** Class that determines whether or not a year is a leap year.
 *  @author EMANUEL AGUIRRE
 */
public class LeapYear {

    /** Calls isLeapYear to print correct statement.
     *  @param  year to be analyzed
     */
    private static void checkLeapYear(int year) {
        if (isLeapYear(year)) {
            System.out.printf("%d is a leap year.\n", year);
        } else {
            System.out.printf("%d is not a leap year.\n", year);
        }
    }

    /** Returns true or false whether the year is a leap year or not.
     *  A leap year is one that is EITHER divisible by 400, OR divisible by 4 but not by 100.
     *  @param  year to be analyzed
     */
    public static boolean isLeapYear(int year) {
        return (year % 400 == 0) ^ (year % 4 == 0 && year % 100 != 0);
    }

    /** Must be provided an integer as a command line argument ARGS. */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter command line arguments.");
            System.out.println("e.g. java Year 2000");
        }
        for (int i = 0; i < args.length; i++) {
            try {
                int year = Integer.parseInt(args[i]);
                checkLeapYear(year);
            } catch (NumberFormatException e) {
                System.out.printf("%s is not a valid number.\n", args[i]);
            }
        }
    }
}

