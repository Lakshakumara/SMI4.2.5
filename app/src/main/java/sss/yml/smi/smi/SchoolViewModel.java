package sss.yml.smi.smi;

import android.os.AsyncTask;
import android.preference.PreferenceManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import sss.yml.smi.Home.HomePage;
import sss.yml.smi.room.DbRepository;
import sss.yml.smi.room.School;

public class SchoolViewModel extends ViewModel {
    private final DbRepository repository = new DbRepository();
    private static final String txtUrl = PreferenceManager.getDefaultSharedPreferences(
            HomePage.application.getApplicationContext()).getString("urlschool", "");
    public void insert (School school) {
        repository.insert(school);
    }
    public void delete (School school) {
        repository.delete(school);
    }

    public LiveData<List<String>> getList (String query, List<String>fieldList) {
        List<String> textList = new ArrayList<>();

        new GetArray(query, fieldList).execute(textList);
        return new MutableLiveData<>(textList);
    }

    public static class GetArray extends AsyncTask<List<String>, Void, LiveData<List<String>>> {
        private final String query;
        private final List<String> fieldList;
        GetArray(String query, List<String> fieldList) {
            this.query = query;
            this.fieldList = fieldList;
        }
        @SafeVarargs
        @Override
        protected final LiveData<List<String>> doInBackground(List<String>... textList) {
            List<String> list = textList[0];
            try {
                URL url = new URL(txtUrl);
                Map<String,Object> params = new LinkedHashMap<>();
                params.put("auth", "Manjula");
                params.put("query", query);

                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String,Object> param : params.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                byte[] postDataBytes = postData.toString().getBytes(StandardCharsets.UTF_8);

                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
                conn.setDoOutput(true);
                conn.getOutputStream().write(postDataBytes);

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                JSONObject jsonResponse = new JSONObject(response.toString());
                int success = (int) jsonResponse.get("success");
                if (success == 1) {
                    JSONArray array = jsonResponse.getJSONArray("data");
                    for(int i = 0 ; i < array.length() ; i++){
                        list.add(array.getJSONObject(i).getString(fieldList.get(0)));
                    }
                }
            } catch (IOException | JSONException ex) {
                ex.printStackTrace();
            }
            return new MutableLiveData<>(list);
        }
        @Override
        protected void onPreExecute() {
            HomePage.progress.setMessage("Data fetching");
            HomePage.progress.show();
        }
        @Override
        protected void onPostExecute(final LiveData<List<String>> list) {
            if(HomePage.progress.isShowing()) HomePage.progress.dismiss();
        }

        @Override
        protected void onCancelled() {
        }
    }

    public List<School> downloadSchool (String query) {
        List<School> textList = null;
        try {
            textList = new GetSchool(query).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return textList;
    }
    private static class GetSchool extends AsyncTask<Void, Void, List<School>> {
        private final String query;

        GetSchool(String query) {
            this.query = query;
        }
        @Override
        protected List<School> doInBackground(Void... params1) {
            List<School> list = new ArrayList<>();
            try {

                URL url = new URL(txtUrl);
                Map<String,Object> params = new LinkedHashMap<>();
                params.put("auth", "Manjula");
                params.put("query", query);

                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String,Object> param : params.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                byte[] postDataBytes = postData.toString().getBytes(StandardCharsets.UTF_8);

                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
                conn.setDoOutput(true);
                conn.getOutputStream().write(postDataBytes);

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                JSONObject jsonResponse = new JSONObject(response.toString());
                int success = (int) jsonResponse.get("success");
                if (success == 1) {
                    JSONArray array = jsonResponse.getJSONArray("data");
                    for(int i = 0 ; i < array.length() ; i++){
                        JSONObject oo = array.getJSONObject(i);
                        School sch = new School(oo.getInt("censusNo"),
                                oo.getString("name"),
                                oo.getString("address"),
                                oo.getString("province"),
                                oo.getString("district"),
                                oo.getString("zone"),
                                oo.getString("division"),
                                oo.getString("level"),
                                oo.getString("span"),
                                oo.getInt("male"),
                                oo.getInt("female"),
                                false);
                        list.add(sch);
                    }
                }
            } catch (IOException | JSONException ex) {
                ex.printStackTrace();
            }
            return list;
        }

        @Override
        protected void onPostExecute(final List<School> list) {
        }

        @Override
        protected void onCancelled() {
        }
    }

    public List<School> getSchoolList (boolean selected) {
        return repository.getSchoolList(selected);
    }

    /*public static class TaskRunner {
        private final Executor executor = Executors.newSingleThreadExecutor(); // change according to your requirements
        private final Handler handler = new Handler(Looper.getMainLooper());

        public interface Callback<R> {
            void onComplete(R result);
        }

        public <R> void executeAsync(Callable<R> callable, Callback<R> callback) {
            executor.execute(() -> {
                //Background work here
                final R result = callable.call();

                handler.post(() -> {
                    //UI Thread work here
                    callback.onComplete(result);
                });
            });
        }
    }*/
}
