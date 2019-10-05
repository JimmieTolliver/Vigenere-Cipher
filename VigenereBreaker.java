
import java.io.File;
import java.util.*;
import edu.duke.*;

public class VigenereBreaker {
    public String sliceString(String message, int whichSlice, int totalSlices) {
        String sliced = "";
    	for(int i = whichSlice; i < message.length(); i += totalSlices) {
        	sliced = sliced + message.charAt(i);
        }
        return sliced;
    }

    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] key = new int[klength];
        String[] slices = new String[klength];
        CaesarCracker cCracker = new CaesarCracker();
        for(int i = 0; i<klength; i++) {
        	slices[i] = sliceString(encrypted, i, klength);
        	key[i] = cCracker.getKey(slices[i]);
        }
        return key;
    }

    public void breakVigenere (int keyLength, char mostCommonChar) {
    	//int keyLength = 4;
    	//char mostCommonChar = 'e';
    	int key[] = new int[keyLength];
        FileResource fr = new FileResource();
        String encrypted = fr.asString();
        key = tryKeyLength(encrypted, keyLength, mostCommonChar);
        VigenereCipher vCipher = new VigenereCipher(key);
        System.out.println(vCipher.decrypt(encrypted));
    }
    
    public HashSet<String> readDictionary(FileResource fr){
    	HashSet<String> dictionary = new HashSet<String>();
    	for (String s: fr.words()) {
    		dictionary.add(s.toLowerCase());
    	}
    	return dictionary;
    }
    
    public int countWords(String message, HashSet<String> dictionary) {
    	int numWords = 0;
    	for(String s: message.split("\\W+")) {
    		if(dictionary.contains(s.toLowerCase())) {
    			numWords ++;
    		}
    	}
    	return numWords;
    }
    
    public String breakForLanguage(String encrypted, HashSet<String> dictionary) {
    	int counts = 0;
    	int maxCorrect = 0;
    	int keyLength = 0;
    	//char mostCommonChar = 'e';
    	char mostCommonChar = mostCommonCharIn(dictionary);
    	int[] bestKey = tryKeyLength(encrypted, 0, mostCommonChar);
    	for(int i = 1; i <= 100; i++) {
    		
            int[] key = tryKeyLength(encrypted, i, mostCommonChar);
            VigenereCipher vCipher = new VigenereCipher(key);
            String decrypted = vCipher.decrypt(encrypted);
            counts = countWords(decrypted, dictionary);
            if(counts > maxCorrect) {
            	maxCorrect = counts;
            	bestKey = key;
            	keyLength = i;
            }
    	}
    	System.out.println("Number of valid words: " + maxCorrect);
    	System.out.println("key: " + Arrays.toString(bestKey));
    	System.out.println("key length: " + keyLength + "\n");
    	VigenereCipher vCipher = new VigenereCipher(bestKey);
    	return vCipher.decrypt(encrypted);
    }
    
    public char mostCommonCharIn(HashSet<String> dictionary) {
    	HashMap<Character, Integer> mostCommonChar = new HashMap<Character, Integer>();
    	for(String s: dictionary) {
    		for(int i = 0; i < s.length(); i++) {
    			if(!mostCommonChar.containsKey(s.charAt(i))) {
    				mostCommonChar.put(s.charAt(i),1);
    			}
    			else {
    				mostCommonChar.put(s.charAt(i), mostCommonChar.get(s.charAt(i)) + 1);
    			}
    		}
    	}   	
    	int maxCount = 0;
		char maxChar = '#';
    	for(char c: mostCommonChar.keySet()) {
    		if(mostCommonChar.get(c) > maxCount) {
    			maxCount = mostCommonChar.get(c);
    			maxChar = c;
    		}
    	}
    	return maxChar;
    }
    
    public String breakForAllLangs(String encrypted, HashMap<String, HashSet<String>> languages) {
    	//HashMap<String, Integer> correctWords = new HashMap<String, Integer>();
    	int maxCorrectWords = 0;
    	String correctLanguage = "";
    	String correctTranslation = "";
    	for (String s: languages.keySet()) {
    		HashSet<String> dictSet = languages.get(s);
    		System.out.println(s);
    		String crackLang = breakForLanguage(encrypted, dictSet);
    		//correctWords.put(s, countWords(crackLang,dictSet));
    		if(countWords(crackLang,dictSet) > maxCorrectWords) {
    			maxCorrectWords = countWords(crackLang,dictSet);
    			correctLanguage = s;
    			correctTranslation = crackLang;
    		}
    	}
    	System.out.println(correctLanguage + " detected as language");
    	return correctTranslation;
    }
    
    public HashMap<String, HashSet<String>> languageDictionaries(){
    	DirectoryResource dr = new DirectoryResource();
    	HashMap<String, HashSet<String>> dictMap= new HashMap<String, HashSet<String>>();
    	for(File f: dr.selectedFiles()) {
    		FileResource fr = new FileResource(f);
    		HashSet<String> hs = new HashSet<String>();
    		for(String s: fr.lines()) {
    			hs.add(s);
    		}
    		dictMap.put(f.getName(), hs);
    	}
    	return dictMap;
    }
}
