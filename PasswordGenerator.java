import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class PasswordGenerator {
    
    private static int count = 0;
    public static String generate() {
        String password = "";
        for(int i = 0; i < 16; i++) {
            password += (char) ThreadLocalRandom.current().nextInt(33, 126 + 1);
        }
        return password;
    }

    public static double timeToCrack(String password) {
        count = 0;
        String str = "abcdefghi";
        long start = System.currentTimeMillis(); 
        allLexicographic(str);
        long stop = System.currentTimeMillis();
        double timeTaken = (stop-start) / 1000.0;
        double secper100k = timeTaken * 100000.0 / count;

        //need to know permutations
        double permutations = checkPermutations(password);
        permutations /= 100000;
        
        return permutations * secper100k;
    }
    
    private static double checkPermutations(String password) {
        boolean hasLowercase = false, hasUppercase = false, hasNumbers = false, hasSymbols = false;
        
        //check if contains lowercase chars
        String lowercase = "abcdefghijklmnopqrstuvwxyz";
        for(int i = 0; i < lowercase.length(); i++) {
            for(int j = 0; j < password.length(); j++) {
                if(password.charAt(j) == lowercase.charAt(i)) {
                    hasLowercase = true;
                    break;
                }
            }
            if(hasLowercase)
                break;
        }

        //check if contains uppercases chars
        String uppercase = lowercase.toUpperCase();
        for(int i = 0; i < uppercase.length(); i++) {
            for(int j = 0; j < password.length(); j++) {
                if(password.charAt(j) == uppercase.charAt(i)) {
                    hasUppercase = true;
                    break;
                }
            }
            if(hasUppercase)
                break;
        }

        //check if contains numbers
        String numbers = "0123456789";
        for(int i = 0; i < numbers.length(); i++) {
            for(int j = 0; j < password.length(); j++) {
                if(password.charAt(j) == numbers.charAt(i)) {
                    hasNumbers = true;
                    break;
                }
            }
            if(hasNumbers)
                break;
        }

        //check if contains numbers
        String symbols = "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}";
        for(int i = 0; i < symbols.length(); i++) {
            for(int j = 0; j < password.length(); j++) {
                if(password.charAt(j) == symbols.charAt(i)) {
                    hasSymbols = true;
                    break;
                }
            }
            if(hasSymbols)
                break;
        }
    
        String finalCombination = "";
        if(hasLowercase)
            finalCombination += lowercase;

        if(hasUppercase)
            finalCombination += uppercase;
        
        if(hasNumbers)
            finalCombination += numbers;
        
        if(hasSymbols)
            finalCombination += symbols;
        
        double permutationCount = 0;
        for (int i = password.length(); i > 0; i--) {
            permutationCount += Math.pow(finalCombination.length(), i);
        }

        return permutationCount;
        
    }

    // The main function that recursively prints  
    // all repeated permutations of the given string.  
    // It uses data[] to store all permutations one by one 
    static void allLexicographicRecur(String str, char[] data, 
                                      int last, int index)  
    { 
        int length = str.length(); 
  
        // One by one fix all characters at the given index  
        // and recur for the subsequent indexes 
        for (int i = 0; i < length; i++)  
        { 
  
            // Fix the ith character at index and if  
            // this is not the last index then  
            // recursively call for higher indexes 
            data[index] = str.charAt(i); 
  
            // If this is the last index then print  
            // the string stored in data[] 
            if (index == last) {
                // System.out.println(new String(data));
                if(!(new String(data).equals("Cant be this"))) {
                    count++;
                }
            }
            else
                allLexicographicRecur(str, data, last,  
                                           index + 1); 
        } 
    } 
  
    // This function sorts input string, allocate memory  
    // for data(needed for allLexicographicRecur()) and calls 
    // allLexicographicRecur() for printing all permutations 
    static void allLexicographic(String str)  
    { 
        int length = str.length(); 
  
        // Create a temp array that will be used by 
        // allLexicographicRecur() 
        char[] data = new char[length + 1]; 
        char[] temp = str.toCharArray(); 
  
        // Sort the input string so that we get all  
        // output strings in lexicographically sorted order 
        Arrays.sort(temp); 
        str = new String(temp); 
  
        // Now print all permutaions 
        allLexicographicRecur(str, data, length - 1, 0); 
    }

}
