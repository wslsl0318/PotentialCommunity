package com.comparision.methods;

public class MethodResult {
    public String method_name;
    public String dataset_name;
    public int file_number;
    public int network_number;
    public int costTime;
    public double NMI;
    public double node_correct;
    public double Rvalue;
    public double Pvalue;
    public double Fvalue;
    public double LCE;
    public double LCU;
    public float[] Valid = new float[10];

    public String getMethod_name() {
        return method_name;
    }

    public void setMethod_name(String method_name) {
        this.method_name = method_name;
    }

    public int getFile_number() {
        return file_number;
    }

    public void setFile_number(int file_number) {
        this.file_number = file_number;
    }

    public int getNetwork_number() {
        return network_number;
    }

    public void setNetwork_number(int network_number) {
        this.network_number = network_number;
    }

    public int getCostTime() {
        return costTime;
    }

    public void setCostTime(int costTime) {
        this.costTime = costTime;
    }

    public double getNMI() {
        return NMI;
    }

    public void setNMI(double NMI) {
        this.NMI = NMI;
    }

    public double getNode_correct() {
        return node_correct;
    }

    public void setNode_correct(double node_correct) {
        this.node_correct = node_correct;
    }

    public double getRvalue() {
        return Rvalue;
    }

    public void setRvalue(double rvalue) {
        Rvalue = rvalue;
    }

    public double getPvalue() {
        return Pvalue;
    }

    public void setPvalue(double pvalue) {
        Pvalue = pvalue;
    }

    public double getFvalue() {
        return Fvalue;
    }

    public void setFvalue(double fvalue) {
        Fvalue = fvalue;
    }

    public String getDataset_name() {
        return dataset_name;
    }

    public void setDataset_name(String dataset_name) {
        this.dataset_name = dataset_name;
    }

    public double getLCE() { return LCE; }

    public void setLCE(double LCE) { this.LCE = LCE; }

    public double getLCU() { return LCU; }

    public void setLCU(double LCU) { this.LCU = LCU; }

    public float[] getValid() { return Valid; }

    public void setValid(float[] valid) { Valid = valid; }
}
