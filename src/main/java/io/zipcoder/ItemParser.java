package io.zipcoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemParser {

    private ArrayList<String> errorLog = new ArrayList<String>();

    public ArrayList<String> parseRawDataIntoStringArray(String rawData) {
        String stringPattern = "##";
        ArrayList<String> response = splitStringWithRegexPattern(stringPattern, rawData);
        return response;
    }

    public ArrayList<String> findKeyValuePairsInRawItemData(String rawItem) {
        String stringPattern = "[;|@|^|!|*|%]";
        ArrayList<String> response = splitStringWithRegexPattern(stringPattern, rawItem);
        return response;
    }

    public Item parseStringIntoItem(String rawItem) throws ItemParseException {

        ArrayList<String> rawPairs = findKeyValuePairsInRawItemData(rawItem.toLowerCase());

        final Pattern
                namePattern = Pattern.compile("(?i)([n][a][m][e]):(\\w*\\d*)"),
                pricePattern = Pattern.compile("(?i)([p][r][i][c][e]):(\\d+\\.\\d{2})"),
                typePattern = Pattern.compile("(?i)([t][y][p][e]):(\\w+)"),
                expiresPattern = Pattern.compile("(?i)([e][x][p]\\w*):(\\d/\\d{2}/\\d{4})(##)");

        final Matcher
                nameMatcher = namePattern.matcher(rawPairs.get(0)),
                priceMatcher = pricePattern.matcher(rawPairs.get(1)),
                typeMatcher = typePattern.matcher(rawPairs.get(2)),
                expiresMatcher = expiresPattern.matcher(rawPairs.get(3));

        // Variables for creating item object
        String name;
        double price;
        String type;
        String expiration;


        if(nameMatcher.matches() && nameMatcher.group(2).length() > 0) {
            name = nameMatcher.group(2);
        }
        else {
            errorLog.add("Invalid data: \'" + rawItem + "\'\n" +
                        "Invalid field: \'Name\'");
            throw new ItemParseException("Invalid name field");
        }

        if(priceMatcher.matches() && priceMatcher.group(2).length() > 0) {
            price = Double.parseDouble(priceMatcher.group(2));
        }
        else {
            errorLog.add("Invalid data: \'" + rawItem + "\'\n" +
                    "Invalid field: \'Price\'");
            throw new ItemParseException("Invalid price field");
        }

        if(typeMatcher.matches() && typeMatcher.group(2).length() > 0) {
            type = typeMatcher.group(2);
        }
        else {
            errorLog.add("Invalid data: \'" + rawItem + "\'\n" +
                    "Invalid field: \'Type\'");
            throw new ItemParseException("Invalid type field");
        }

        if(expiresMatcher.matches() && expiresMatcher.group(2).length() > 0) {
            expiration = expiresMatcher.group(2);
        }
        else {
            errorLog.add("Invalid data: \'" + rawItem + "\'\n" +
                    "Invalid field: \'Expiration\'");
            throw new ItemParseException("Invalid expiration field");
        }

        return new Item(name, price, type, expiration);
//        /* I can't figure out why this doesn't work :''''( </3
//        try {
//            for (int i = 0; i < patterns.size(); i++) {
//
//                Matcher matcher = patterns.get(i).matcher(rawPairs.get(i));
//
//                if (matcher.matches() && matcher.group(2).length() > 0) {
//
//                    switch (i) {
//                        case 0:
//                            name = matcher.group(2);
//                            break;
//                        case 1:
//                            price = Double.parseDouble(matcher.group(2));
//                            break;
//                        case 2:
//                            type = matcher.group(2);
//                            break;
//                        case 3:
//                            expiration = matcher.group(2);
//                            break;
//                        default:
//                            break;
//                    }
//                }
//                else {
//                    catchError = rawPairs.get(i);
//                    throw new ItemParseException("Invalid data: \'" + rawItem + "\'\n" +
//                            "\tError in field: \'" + catchError + "\'");
//                }
//            }
//
//        } catch (ItemParseException e) {
//            errorLog.add("Invalid data: \'" + rawItem + "\'\n" +
//                    "\tError in field: \'" + catchError + "\'");
//        }
//        if() {
//            returnValue = new Item(name, price, type, expiration);
//        }
//        return returnValue;
//        */
    }

    private ArrayList<String> splitStringWithRegexPattern(String stringPattern, String inputString) {
        return new ArrayList<String>(Arrays.asList(inputString.split(stringPattern)));
    }

    public ArrayList<String> getErrorLog() {
        return errorLog;
    }

//    private String validateData(Pattern pattern, String rawData) throws ItemParseException {
//        Matcher matcher = pattern.matcher(rawData);
//        String retString;
//
//        if(matcher.matches() && matcher.group(2).length() > 0) {
//            retString = matcher.group(2);
//        }
//        else {
//            String field = matcher.group(1);
//            errorLog.add("Invalid " + field + " data");
//            throw new ItemParseException("Invalid " + field + " field");
//        }
//        return retString;
//    }

}
