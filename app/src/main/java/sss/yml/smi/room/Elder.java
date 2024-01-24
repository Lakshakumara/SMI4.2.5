package sss.yml.smi.room;

public class Elder {
    private String sex;
    private float bmi;
    private boolean isDOB;
    private int ageYY;
    private int ageMM;
    private int ageDD;

    public Elder(float bmi, boolean isDOB) {
        this.bmi = bmi;
        this.isDOB = isDOB;
    }
    public Elder(float bmi, int ageYY, int ageMM, int ageDD) {
        this.bmi = bmi;
        this.isDOB = true;
        this.ageYY = ageYY;
        this.ageMM = ageMM;
        this.ageDD = ageDD;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public float getBmi() {
        return bmi;
    }

    public void setBmi(float bmi) {
        this.bmi = bmi;
    }

    public boolean isDOB() {
        return isDOB;
    }

    public int getAgeYY() {
        return ageYY;
    }

    public void setAgeYY(int ageYY) {
        this.ageYY = ageYY;
    }

    public int getAgeMM() {
        return ageMM;
    }

    public void setAgeMM(int ageMM) {
        this.ageMM = ageMM;
    }

    public int getAgeDD() {
        return ageDD;
    }

    public void setAgeDD(int ageDD) {
        this.ageDD = ageDD;
    }

    @Override
    public String toString() {
        return "Elder{" +
                "sex='" + sex + '\'' +
                ", bmi=" + bmi +
                ", isDOB=" + isDOB +
                ", ageYY=" + ageYY +
                ", ageMM=" + ageMM +
                ", ageDD=" + ageDD +
                '}';
    }
}
