public class Utility {

  public JSONObject addToObject(JSONObject object, String label, String attribute) throws JSONException {
    try {
      object.put(label, attribute);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return object;
  }

}
