package sss.yml.smi.util;

import sss.yml.smi.gt.DataTable;
import sss.yml.smi.room.ChildWpData;

public class Util {
    public static double genZScore(DataTable dataTableMonth, ChildWpData childWpData) {
        int ageMM = childWpData.getAgeYY() * 12 + childWpData.getAgeMM();
        float L = dataTableMonth.getZScore(ageMM, "L");
        float M = dataTableMonth.getZScore(ageMM, "M");
        float S = dataTableMonth.getZScore(ageMM, "S");
        double Zind = (Math.pow(childWpData.getWt() / M, L) - 1) / (S * L);
        if (Zind < -3) {
            double SD2neg = M * Math.pow(1 + L * S * -2, 1 / L);
            double SD3neg = M * Math.pow(1 + L * S * -3, 1 / L);
            double SD23neg = SD2neg - SD3neg;
            Zind = -3 + ((childWpData.getWt() - SD3neg) / SD23neg);
        } else if (Zind > 3) {
            double SD3pos = M * Math.pow(1 + L * S * 3, 1 / L);
            double SD2pos = M * Math.pow(1 + L * S * 2, 1 / L);
            double SD23pos = SD3pos - SD2pos;
            Zind = 3 + ((childWpData.getWt() - SD3pos) / SD23pos);
        }
        return Zind;
    }

   /* public static ArrayAdapter<String> getss(){
        return new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                Collections.singletonList(getResources().getStringArray(R.array.logging_group))) {
            public View getView(int position,
                                View convertView,
                                ViewGroup parent) {
                return super.getView(position, convertView, parent);
            }
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                return super.getDropDownView(position, convertView, parent);
            }
        };
    }*/
}
