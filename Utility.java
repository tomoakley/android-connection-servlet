package ubiserv.simple.tom;

import javax.json.*;
import org.json.*;

public class Utility {

  public static JSONObject addToObject(JSONObject object, String label, String attribute) throws JSONException {
    try {
      object.put(label, attribute);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return object;
  }

  public static boolean isNotNull(String txt){
    return txt!=null && txt.trim().length()>0 ? true: false;
  }

  public static boolean validateEmail(String email) {
    pattern = Pattern.compile(EMAIL_PATTERN);
    matcher = pattern.matcher(email);
    return matcher.matches();
  }

}
