package spark.template.freemaker;

import com.google.gson.Gson;
import spark.ResponseTransformer;

/**
 * @author Semih Okan Pehlivan
 */
public class JsonTransformer implements ResponseTransformer {

    private Gson gson = new Gson();

    @Override
    public String render(Object model) throws Exception {
        return gson.toJson(model);
    }
}
