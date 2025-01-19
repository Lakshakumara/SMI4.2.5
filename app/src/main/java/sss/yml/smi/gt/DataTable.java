package sss.yml.smi.gt;

import com.github.mikephil.charting.data.Entry;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by YML Kumara
 */
public class DataTable {
    private static final int DEEP = 1;
    InputStream is = null;
    private HashMap<Integer, HashMap<String, Float>> dataTable;
    private HashMap<Float, HashMap<String, Float>> dataTableFloat;

    public DataTable(InputStream is) {
        this.is = is;
        loadTable(false);
    }

    public DataTable(InputStream is, boolean precision) {
        this.is = is;
        loadTable(precision);
    }

    public void setIs(InputStream is) {
        this.is = is;
        loadTable(false);
    }
   /* private void loadTable(boolean precision){
        dataTable = new HashMap<>();
        dataTableFloat = new HashMap<>();
        try {

             new LoadTableAsyncTask(precision).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*/

    private void loadTable(boolean precision) {
        dataTable = new HashMap<>();
        dataTableFloat = new HashMap<>();
        try {
            BufferedReader buf = new BufferedReader(new InputStreamReader(is));
            String lineJustFetched;
            String[] wordsArray;
            String[] title = buf.readLine().split("\t");
            while (true) {
                lineJustFetched = buf.readLine();
                if (lineJustFetched == null) {
                    break;
                } else {
                    wordsArray = lineJustFetched.split("\t");
                    HashMap<String, Float> inner = new HashMap<>();
                    for (int i = 1; i < title.length; i++) {
                        inner.put(title[i], Float.valueOf(wordsArray[i]));
                    }

                    if (precision) {
                        float val = Float.parseFloat(wordsArray[0]);
                        //if (val == 1 || val % DEEP == 0)
                            dataTableFloat.put(val, inner);
                    } else {
                        int age = Integer.parseInt(wordsArray[0]);
                        //if(age == 60 || age == 228 || age % DEEP == 0)
                        dataTable.put(age, inner);
                    }
                }
            }
            buf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* private class LoadTableAsyncTask extends AsyncTask<Void, Void, Void> {
         boolean precision;
         LoadTableAsyncTask(boolean precision){
             this.precision = precision;
         }
         @Override
         protected Void doInBackground(Void... Void) {
             Log.e("xyz", "doInBackground "+precision);
             try {
                 BufferedReader buf = new BufferedReader(new InputStreamReader(is));
                 String lineJustFetched;
                 String[] wordsArray;
                 String[] title = buf.readLine().split("\t");
                 while (true) {
                     lineJustFetched = buf.readLine();
                     if (lineJustFetched == null) {
                         Log.e("xyz", "lineJustFetched is null "+is.toString());
                         break;
                     } else {
                         wordsArray = lineJustFetched.split("\t");
                         inner = new HashMap<>();
                         for (int i = 1; i < title.length; i++) {
                             inner.put(title[i], Float.valueOf(wordsArray[i]));
                         }
                         if(precision) dataTableFloat.put(Float.parseFloat(wordsArray[0]), inner);
                         else dataTable.put(Integer.parseInt(wordsArray[0]), inner);
                     }
                 }
                 Log.e("xyz", "loadTable "+dataTable);
                 buf.close();
             }catch(Exception e){
                 e.printStackTrace();
             }

             return null;
         }
     }*/
    public ArrayList<Entry> getPercentile(String percentile) {
        ArrayList<Entry> L1 = new ArrayList<>();
        SortedSet<Integer> keys = new TreeSet<>(dataTable.keySet());
        if (dataTable != null)
            for (Integer months : keys) {
                L1.add(new Entry(months, (dataTable.get(months)).get(percentile)));
            }
        return L1;
    }

    public float getZScore(int age, String line) {
        return dataTable.get(age).get(line);
    }

    public ArrayList<Entry> getPercentileFloat(String percentile) {
        ArrayList<Entry> L1 = new ArrayList<>();
        SortedSet<Float> keys = new TreeSet<>(dataTableFloat.keySet());
        if (dataTableFloat != null)
            for (Float months : keys) {
                //Log.e("xyz", ""+dataTableFloat+ "  "+months);
                // Log.e("xyz", String.valueOf(dataTableFloat.get(months)));
                L1.add(new Entry(months, (dataTableFloat.get(months)).get(percentile)));
            }
        /*
          else restart app here
         */
        return L1;
    }

    public HashMap<String, Float> getIdealData(Float height) {
        return dataTableFloat.get(height);
    }

    public HashMap<String, Float> getIdealData(int age) {
        return dataTable.get(age);
    }

    @Override
    public String toString() {
        String x = "";
        SortedSet<Integer> keys = new TreeSet<>(dataTable.keySet());
        if (dataTable != null) {
            for (Integer months : keys) {
                HashMap<String, Float> dd = dataTable.get(months);
                x = x.concat(months + "::" + (dataTable.get(months)).get("SD3"));
            }
        }
        return x;
    }
}