package DataIngestion;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

/*
  Class that abstracts the communication 
  with the OPAP API and the data preparation 
  process for game Statistics related data.
*/
public class WinningColumnStatsIntegrator extends JsonReader implements IApiIntegrator {

    private final String TOTAL_STATS_URL = "https://api.opap.gr/games/v1.0/{gameID}/statistics";
    private WinningColumnStats totalNumberStats;
    private HashMap<String, String> queryParams = new HashMap<String, String>();

    //Fetch data from api to API Object
    @Override
    public void loadDataFromAPI() throws IOException {
        //Prepare endpoint URL
        String finalURL = TOTAL_STATS_URL;
        for (String key : queryParams.keySet()) {
            String formatedKey = "{" + key + "}";
            finalURL = finalURL.replace(formatedKey, queryParams.get(key));
        }
        URL TotalNumberStats = new URL(finalURL);
        //Get Results by calling the REST API
        String ApiResponse = getFromAPI(TotalNumberStats);
        //Convert to SingleDrawResults Object
        //and return to caller
        Gson gson = new Gson();
        totalNumberStats = gson.fromJson(ApiResponse, WinningColumnStats.class);
    }

    public WinningColumnStats getTotalNumberStats() {
        return totalNumberStats;
    }

    @Override
    public void addAPIargument(String paramName, String paramValue) {
        queryParams.put(paramName, paramValue);
    }

    @Override
    public void removeAPIargument(String paramName) {
        queryParams.remove(paramName);
    }

    @Override
    public Object getDataObject(Class objectName) {
        if (objectName == WinningColumnStats.class) {
            return totalNumberStats;
        }
        return null;
    }
}
