package database;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Auto;

import java.util.ArrayList;
import java.util.List;

public class AutoCouchDBDAO {

    private CouchDBaccess db;
    private Gson gson;

    public AutoCouchDBDAO(CouchDBaccess db) {
        super();
        this.db = db;
        gson = new Gson();
    }

    public String saveSingleAuto(Auto auto) {
        String jsonstring = gson.toJson(auto);
        System.out.println(jsonstring);
        JsonParser parser = new JsonParser();
        JsonObject jsonobject = parser.parse(jsonstring).getAsJsonObject();
        String doc_Id = db.saveDocument(jsonobject);
        return doc_Id;
    }

    public ArrayList<Auto> getAllAutos() {
        List<JsonObject> allDocs = db.getClient().view("_all_docs").includeDocs(true).query(JsonObject.class);
        ArrayList<Auto> autos = new ArrayList<>();
        for (JsonObject j : allDocs) {
            autos.add(gson.fromJson(j, Auto.class));
        }
        return autos;
    }

    public Auto getAutoByKenteken (String kenteken) {
        Auto auto = null;
        List<JsonObject> alleAutos = db.getClient().view("_all_docs").includeDocs(true).query(JsonObject.class);
        for (JsonObject json : alleAutos) {
            auto = gson.fromJson(json, Auto.class);
            if (auto.getKenteken().equals(kenteken)) {
                return auto;
            }
        }
        return auto;
    }

    public void printOverzichtKostenPerKenteken() {
        List<JsonObject> reducer = db.getClient().view("opdrachten/kosten-per-kenteken").reduce(true).groupLevel(1).query(JsonObject.class);
        for (JsonObject json : reducer) {
            System.out.println(json);
        }
    }


}