package DataIngestion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public abstract class JsonReader {

    public final static String getFromAPI(URL url) throws IOException {

        StringBuilder result = new StringBuilder();
        //Reads text from a character-input stream, and removes not applicable words,
        //that is punctuation characters and whitespaces.

        //buffering characters so as to provide for the efficient reading of characters, arrays, and lines.            
        //An InputStreamReader is a bridge from byte streams to character streams: 
        //It reads bytes and decodes them into characters using a specified charset. 
        //The charset that it uses may be specified by name or may be given explicitly, 
        //or the platform's default charset may be accepted.                 \
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                result.append(inputLine);
                result.append(" ");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw e;
        }
        return result.toString();
    }

}
