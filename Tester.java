import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import edu.duke.*;

public class Tester {

	public static void main(String[] args) {
		
		//TEST CAESAR CRACKER
		//FileResource fr = new FileResource();
		//CaesarCracker crack = new CaesarCracker();
		//System.out.println(crack.getKey(fr.asString()));
		
		//TEST VIGENERE CIPHER ENCRYPT / DECRYPT
		//int [] key = {17, 14, 12, 4};
		//VigenereCipher vc = new VigenereCipher(key);
		//String encrypted = vc.encrypt(fr.asString());
		//System.out.println(encrypted);
		//System.out.println("\n"+ vc.decrypt(encrypted));
		
		//TEST VIGENERE BREAKER
		VigenereBreaker vCracker = new VigenereBreaker();
		//System.out.println(vCracker.sliceString("abcdefghijklm", 3, 4));
		
		FileResource fr = new FileResource();
		//System.out.println(Arrays.toString(vCracker.tryKeyLength(fr.asString(),4,'e')));
		
		//vCracker.breakVigenere();
		
		//FileResource dict = new FileResource("/home/jimmie/links/dictionaries/English");
		//HashSet<String> dictionary = vCracker.readDictionary(dict);
		//System.out.print(vCracker.countWords(fr.asString(), dictionary));
		//vCracker.breakForLanguage(fr.asString(), dictionary);
		//System.out.println(vCracker.breakForLanguage(fr.asString(), dictionary));
		
		//System.out.println(vCracker.mostCommonCharIn(dictionary));
		
		//Unknown Lang & Key
		HashMap<String, HashSet<String>> languages = vCracker.languageDictionaries();
		System.out.println("\nTranslastion:\n" + vCracker.breakForAllLangs(fr.asString(), languages));
	}

}
