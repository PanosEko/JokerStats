package DataIngestion;

import java.io.IOException;


/*
   Interface that denotes the basic functionality that all
   Classes that provide access to the OPAP API must provide.

   It is used so that the client classes (the gui) don't
   have to take care of the specifics of each API call. Thus 
   we can add more Integrator objects (i.e API calls for other games)
   without too much effort (loose coupling with the GUI).
        For example MultiDrawIntegrator and SingleDrawIntegrator, both
        implement this Interface and provide a way of:
                - calling the corresponding endpoint, 
                - transform the JSON response to Java Object and 
                - return it to the caller.  
*/
public interface IApiIntegrator {    
    public void addAPIargument(String paramName, String paramValue);
    public void removeAPIargument(String paramName);
    public void loadDataFromAPI() throws IOException; 
    public Object getDataObject(Class objectName);
}
