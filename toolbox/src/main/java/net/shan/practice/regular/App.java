package net.shan.practice.regular;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by sam.js on 14-3-21.
 */
public class App {
    public static void main(String[] args){
        Pattern pattern1 = null;
        Pattern pattern2 = null;
        Pattern pattern3 = null;
        Matcher matcher = null;

//        www.aliexpress.com/store/product/[^\s]*/[\d]*_[\d]*.html
//        www.aliexpress.com/store/product/wholesale-512gb-Iron-Man-USB-flash-drive-memory-stick-Ironman-pen-drive-external-storage-Gift-CARD/1043651_1553276021.html?spm=5261.7132366.1996734146.25

//        http://www.aliexpress.com/item/2014-New-Arrival-6-colors-8GB-mp3-players-FM-radio-Digital-Screen-MP3-Music-Player-8GB/1468775328.html
//        http://www.aliexpress.com/snapshot/304193277.html


        String regex1 = "www.aliexpress.com/store/product/[^\\s]*/[\\d]*_([\\d]*).html";
        pattern1 = Pattern.compile(regex1);

        String regex2 = "www.aliexpress.com/item/[^\\s]*/([\\d]*).html";
        pattern2 = Pattern.compile(regex2);

        String regex3 = "www.aliexpress.com/snapshot/([\\d]*).html";
        pattern3 = Pattern.compile(regex3);

        String target1 = "http;//www.aliexpress.com/store/product/wholesale-512gb-Iron-Man-USB-flash-drive-memory-stick-Ironman-pen-drive-external-storage-Gift-CARD/1043651_1553276021.html?spm=5261.7132366.1996734146.25";
        matcher = pattern1.matcher(target1);

        String target2 = "http://www.aliexpress.com/item/2014-New-Arrival-6-colors-8GB-mp3-players-FM-radio-Digital-Screen-MP3-Music-Player-8GB/1468775328.html";
        matcher = pattern2.matcher(target2);

        String target3 = "http://www.aliexpress.com/snapshot/304193277.html";
        matcher = pattern3.matcher(target3);

        if(matcher.find()){
            System.out.println("matcher.end(): " +  matcher.end());
            System.out.println(matcher.group(1)==null);
            System.out.println(matcher.group(1));

        }

//        boolean found = false;
//        while (matcher.find()) {
//            System.out.printf("I found the text \"%s\" starting at index %d and ending at index %d.%n",
//                    matcher.group(), matcher.start(), matcher.end());
//            found = true;
//        }
//        if (!found) {
//            System.out.printf("No match found.%n");
//        }

    }
}
