package DataIngestion;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import model.GameDrawResults;
import model.WinningColumn;
import model.WinningColumnPK;


/*
   Utility class that is designed to
   foster easy comunication with external APIs
   regarding the multiple draw results data
 */
public class MultiDrawIntegrator extends JsonReader implements IApiIntegrator{

    private final String MULTI_DRAWS_URL = "https://api.opap.gr/draws/v3.0/{gameID}/draw-date/{fromDate}/{toDate}";    
    private MultiDrawResults multiDrawResults;
    private HashMap<String, String> queryParams = new HashMap<String, String>();
    
    //Fetch data from api to API Object
    @Override
    public void loadDataFromAPI() throws IOException{
        //Prepare endpoint URL
        String finalURL = MULTI_DRAWS_URL;
        for (String key : queryParams.keySet()) {
            String formatedKey = "{" + key + "}";
            finalURL = finalURL.replace( formatedKey, queryParams.get(key));
        }        
        URL DrawResultsWithDateURL = new URL(finalURL);        
        //Get Results by calling the REST API
        String ApiResponse = getFromAPI(DrawResultsWithDateURL);
        //Convert to SingleDrawResults Object
        //and return to caller
        Gson gson = new Gson();
        multiDrawResults = gson.fromJson(ApiResponse, MultiDrawResults.class);    
    }
    
    
    @Override
    public Object getDataObject(Class ObjectName) {            
        if (ObjectName == GameDrawResults.class){
            return getGameDrawResults();
        } else if (ObjectName == WinningColumn.class) {
            return getWinningColumnList();
        }
        return null;
    }        
    
    private List<GameDrawResults> getGameDrawResults() {            
        //TODO : Check for valid multiDrawResults object
        
        List<GameDrawResults> gameDrawResultsList = new ArrayList<>();        
        //iterate through draws
        for (MultiDrawResults.Content content:multiDrawResults.content){       
            //iterate through prizecategories in draw
            for (MultiDrawResults.PrizeCategory prizeCategory : content.prizeCategories) {
                //Create GameDrawResults entry
                GameDrawResults gameDrawResults = new GameDrawResults(content.gameId,
                         content.drawId,
                         String.valueOf(prizeCategory.id));
                //And continuing with the rest of the fields
                gameDrawResults.setDividentAmn(prizeCategory.divident);
                gameDrawResults.setSuccessesCnt(prizeCategory.winners);
                gameDrawResults.setTotalPayout(prizeCategory.distributed);            
                gameDrawResultsList.add(gameDrawResults);
            }
        }
        return gameDrawResultsList;
    }
    
    
    private List<WinningColumn> getWinningColumnList() {            
        //TODO : Check for valid multiDrawResults object
        
        List<WinningColumn> winningColumnList = new ArrayList<>();
        
        //iterate through draws
        for (MultiDrawResults.Content content:multiDrawResults.content){ 
            WinningColumnPK winningColumnPK = new WinningColumnPK(content.gameId,content.drawId);  
            WinningColumn WinningColumn = new WinningColumn(winningColumnPK);
            WinningColumn.setN1(content.winningNumbers.list.get(0));    
            WinningColumn.setN1(content.winningNumbers.list.get(0));
            WinningColumn.setN2(content.winningNumbers.list.get(1));
            WinningColumn.setN3(content.winningNumbers.list.get(2));
            WinningColumn.setN4(content.winningNumbers.list.get(3));
            WinningColumn.setN5(content.winningNumbers.list.get(4));
            WinningColumn.setJ1(content.winningNumbers.bonus.get(0));   
            String winningColumnDescr = WinningColumn.getN1() + "," 
                + WinningColumn.getN2() + ","+ WinningColumn.getN3() + ","
                + WinningColumn.getN4() + ","+ WinningColumn.getN5() + "/"
                + WinningColumn.getJ1();
            WinningColumn.setWinningColumnDescr(winningColumnDescr);
            //Get Draw Datetime
            long drawTime = (long) content.drawTime;
            //and convert from unix timestamp
            Date drawDateTime = new java.util.Date((long)drawTime);        
            WinningColumn.setDrawTime(drawDateTime);        
            //add winning column objectg to list
            winningColumnList.add(WinningColumn);
        }
        return winningColumnList;
    }    

    @Override
    public void addAPIargument(String paramName, String paramValue) {
        queryParams.put(paramName, paramValue);
    }

    @Override
    public void removeAPIargument(String paramName) {
        queryParams.remove(paramName);
    }                        
}
