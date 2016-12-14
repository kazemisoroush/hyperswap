package main;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class Helpers {

    /**
     * a run-time constant randomly chosen within [0 .. A] that can be varied without altering performance.
     */
    private static final int c_255 = getRandomInteger(0, 255);
    private static final int c_1023 = getRandomInteger(0, 1023);
    private static final int c_8191 = getRandomInteger(0, 8191);

    /**
     * Glue an input list with input delimiter.
     *
     * @param list      to be imploded.
     * @param delimiter or the glue string.
     *
     * @return imploded string.
     */
    public static String implode(Collection list, String delimiter) {
        // initialize variables...
        int index = 0;
        String imploded = "";

        for (Object item : list) {
            // add the object to the result...
            imploded += item.toString();

            // add the delimiter with condition...
            if (index != list.size() - 1) {
                imploded += delimiter;
            }

            // increment the index...
            index++;
        }

        return imploded;
    }

    /**
     * Explode string into array with input regex delimiter.
     *
     * @param string         to explode.
     * @param regexDelimiter string value.
     *
     * @return exploded array.
     */
    public static String[] explode(String string, String regexDelimiter) {
        return string.split(regexDelimiter);
    }

    /**
     * Generate random number between min and max.
     *
     * @param min lower bound of random number.
     * @param max upper bound of random number.
     *
     * @return generated random number.
     */
    public static int getRandomInteger(int min, int max) {
        Random random = new Random();

        return random.nextInt(max - min + 1) + min;
    }

    /**
     * Get `count` number of random integers in [min ... max] range.
     *
     * @param min   value of range.
     * @param max   value of range.
     * @param count of random numbers.
     *
     * @return list of random integers.
     */
    public static ArrayList<Integer> getRandomIntegers(int min, int max, int count) {
        ArrayList<Integer> numbers = new ArrayList<>();

        // in this condition it would be not possible to get random numbers...
        if (max - min < count) {
            count = max - min - 1;
        }

        Integer number;

        for (int i = 0; i < count; i++) {
            // do not add duplicate numbers...
            do {
                number = getRandomInteger(min, max);
            } while (numbers.contains(number));

            // we are now sure that this number is not redundant...
            numbers.add(number);
        }

        return numbers;
    }

    /**
     * Generate random double between min and max.
     *
     * @param min lower bound of random number.
     * @param max upper bound of random number.
     *
     * @return generated random number.
     */
    public static double getRandomNumber(double min, double max) {
        Random random = new Random();

        return random.nextDouble() * (max - min) + min;
    }

    /**
     * The non-uniform random, means an independently selected and non -uniformly distributed random number over the
     * specified range of values [min .. max].
     *
     * @param a   factor of non-uniform number.
     * @param min value of range for non-uniform random number.
     * @param max value of range for non-uniform random number.
     *
     * @return non-uniform integer from the range.
     */
    public static int getNonUniformRandomInteger(int a, int min, int max) {
        // a run-time constant randomly chosen within [0 .. a] that can be varied without altering performance...
        int c;

        // select the `c` variable...
        switch (a) {
            case 255:
                c = c_255;
                break;
            case 1023:
                c = c_1023;
                break;
            case 8191:
                c = c_8191;
                break;
            default:
                throw new RuntimeException("Invalid A variable `%d`" + a);
        }

        return ((((getRandomInteger(0, a) | getRandomInteger(min, max)) + c) % (max - min + 1)) + min);
    }

    /**
     * make a `random a-string`: a string of random alphanumeric characters of a random length of minimum x, maximum y
     * and mean (y + x) / 2 characters.
     *
     * @param x minimum number of characters.
     * @param y minimum number of characters.
     *
     * @return alphabetic random string.
     */
    public static String getRandomString(int x, int y, String allCharacters) {
        if (x < 0) {
            x = 0;
        }

        // final string...
        String string = "";

        // make array of all characters...
        char[] alphabetArray = allCharacters.toCharArray();

        // random string with random length between x and y...
        for (int i = 1; i < getRandomInteger(x, y); i++) {
            string += alphabetArray[getRandomInteger(0, alphabetArray.length - 1)];
        }

        return string;
    }

    /**
     * make a `random a-string`: a string of random alphanumeric characters of a random length of minimum x, maximum y
     * and mean (y + x) / 2 characters. all possible characters are allowed.
     *
     * @param x minimum number of characters.
     * @param y minimum number of characters.
     *
     * @return alphabetic random string.
     */
    public static String getRandomStringAlphabetic(int x, int y) {
        String characters = "0123456789" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvwxyz";

        return getRandomString(x, y, characters);
    }

    /**
     * make a `random a-string`: a string of random alphanumeric characters of a random length of minimum x, maximum y
     * and mean (y + x) / 2 characters. numeric characters only.
     *
     * @param x minimum number of characters.
     * @param y minimum number of characters.
     *
     * @return alphabetic random string.
     */
    public static String getRandomStringNumeric(int x, int y) {
        String characters = "0123456789";

        return getRandomString(x, y, characters);
    }

    /**
     * make a `random a-string`: a string of random alphanumeric characters of a random length of minimum x, maximum y
     * and mean (y + x) / 2 characters. This method also adds a `original` randomly inside the string for `probability`
     * percentage of strings.
     *
     * @param x minimum number of characters.
     * @param y minimum number of characters.
     *
     * @return alphabetic random string.
     */
    public static String getRandomStringWithOriginal(int x, int y, double probability) {
        String string = getRandomStringAlphabetic(x, y);

        // make the probability in range...
        if (probability > 1 || probability < 0) {
            probability = 1 / 10;
        }

        // must add original...
        if (getRandomNumber(0, 1) <= probability) {
            int position = getRandomInteger(0, string.length() - 8);

            // put the `original` string inside it...
            string = string.substring(0, position) + "original" + string.substring(position + 8);
        }

        return string;
    }

    /**
     * The customer last name `C_LAST` must be generated by the concatenation of three variable length syllables
     * selected from the following list.
     *
     * @param number between 0 and 999.
     *
     * @return string of last name.
     */
    public static String getCustomerLastName(int number) {
        String[] n = {"BAR", "OUGHT", "ABLE", "PRI", "PRES", "ESE", "ANTI", "CALLY", "ATION", "EING"};

        return n[number / 100] + n[(number / 10) % 10] + n[number % 10];
    }

    /**
     * Get random credit type by input distribution.
     *
     * @param distribution of random credit type.
     *
     * @return string of random credit.
     */
    public static String getRandomCredit(double distribution) {
        String credit;

        // use the distribution probability...
        if (getRandomNumber(0, 1) >= distribution) {
            credit = "G";
        } else {
            credit = "B";
        }

        return credit + "C";
    }

    /**
     * Get true with given probability.
     *
     * @param probability percentage.
     *
     * @return random boolean with probability.
     */
    public static boolean getRandomBooleanWithProbability(double probability) {
        // return true with input probability...
        return getRandomNumber(0, 1) <= probability;
    }

    /**
     * Add padding to the number and return the number in string format.
     *
     * @param number             which needs to be pad.
     * @param numberOfCharacters number of characters in resulting string.
     *
     * @return pad string.
     */
    public static String parWithZero(int number, int numberOfCharacters) {
        String result = number + "";

        // handle invalid inputs or no padding required conditions...
        if (result.length() >= numberOfCharacters) {
            return result;
        }

        // pad zeros at the left side of resulting number string...
        for (int i = 0; i < numberOfCharacters - result.length(); i++) {
            result = "0" + result;
        }

        return result;
    }

    /**
     * Append string to logFile.
     *
     * @param filePath to be appended.
     * @param string   to append.
     */
    public static void appendStringToFile(String filePath, String string) {
        BufferedWriter bufferedWriter = null;
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(filePath, true);
            bufferedWriter = new BufferedWriter(fileWriter);

            // write the string to the logFile...
            bufferedWriter.write(string);

            System.out.println("New line...");

            // also add new line at the end of the logFile...
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // close the writers...
            try {
                fileWriter.close();
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Sum array of numbers.
     *
     * @param numbers in array.
     *
     * @return total sum.
     */
    public static int sum(int[] numbers) {
        int sum = 0;

        for (int number : numbers) {
            sum += number;
        }

        return sum;
    }

    /**
     * Weighted random number selection between some numbers.
     *
     * @param weights of probability.
     *
     * @return index of selected number.
     */
    public static int getRandomNumberWithWeightedProbability(int... weights) {
        // calculate the weighted sum of the numbers...
        int sum = sum(weights);

        // get a number between 1 and weighted sum...
        int randomNumber = getRandomInteger(1, sum);

        // index of which the weight is belong to...
        int index = 0;

        // return selected weight...
        for (int weight : weights) {
            if (randomNumber - weight <= 0) {
                return index;
            }

            index++;
            randomNumber -= weight;
        }

        return index;
    }

    /**
     * Fill an array of string lines from input logFile path.
     *
     * @param path to the logFile.
     *
     * @return array of lines.
     * @throws IOException of logFile not found.
     */
    public static ArrayList<String> fileToArray(String path) throws IOException {
        // make a variable for lines...
        String line;

        // initialize array of lines...
        ArrayList<String> arrayOfLines = new ArrayList<>();

        // get the absolute path...
        InputStream absolutePath = Helpers.class.getResourceAsStream(path);

        // make the stream reader...
        InputStreamReader streamReader = new InputStreamReader(absolutePath);

        // make a buffered reader to read the stream line by line...
        BufferedReader reader = new BufferedReader(streamReader);

        // the first non-comment line of logFile is number of nodes and number of edges of the graph...
        // so we need to extract the first non-comment line...
        while ((line = reader.readLine()) != null) {
            // define comment for first of lines...
            if (isComment(line)) {
                continue;
            }

            // remove comment part of the line...
            line = cutCommentPart(line);

            // add the line string to the array of lines...
            arrayOfLines.add(line);
        }

        return arrayOfLines;
    }

    /**
     * Check if the line is comment.
     *
     * @param line to check.
     *
     * @return boolean which specifies the line is comment or not.
     */
    public static boolean isComment(String line) {
        // check each comment starting...
        for (String comment : main.Main.COMMENTS) {
            if (line.startsWith(comment))
                return true;
        }

        // otherwise...
        return false;
    }

    /**
     * Remove the comment part from string line.
     *
     * @param line which we need to be comment free.
     *
     * @return comment free line.
     */
    public static String cutCommentPart(String line) {
        for (String comment : main.Main.COMMENTS) {
            // if line does not have this comment then just skip it...
            if (! line.contains(comment))
                continue;

            // cut the line from beginning to the comment character...
            line = line.substring(0, line.lastIndexOf(comment) - 1);
        }

        return line;
    }

}
