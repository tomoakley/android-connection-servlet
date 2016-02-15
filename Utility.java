package ubiserv.simple.tom;

import javax.json.*;
import org.json.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {
  private static Pattern pattern;
  private static Matcher matcher;
  private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\+]+(\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{2,})$";

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
