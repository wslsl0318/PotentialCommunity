package com.comparision.methods;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ReadArtificialResults {

    public MethodResult read_artificial_results(int file_number, int network_number, String method_name, String file_path_result)
    {
        File file = new File(file_path_result);
        BufferedReader reader = null;
        MethodResult AMResult = new MethodResult();
        AMResult.setMethod_name(method_name);
        AMResult.setFile_number(file_number);
        AMResult.setNetwork_number(network_number);
        try
        {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            while ((tempString = reader.readLine()) != null && line <=5)
            {
                if(line == 1) {
                    int costTime = Integer.parseInt(tempString.split(" ")[1]);
                    AMResult.setCostTime(costTime);
                }
                else if(line == 2) {
                    double NMI = Double.parseDouble(tempString.split(" ")[1]);
                    AMResult.setNMI(NMI);
                }
                else if(line == 3) {
                    double node_correct = Double.parseDouble(tempString.split(" ")[1]);
                    AMResult.setNode_correct(node_correct);
                }
                else if(line == 5){
                    double Rvalue = Double.parseDouble(tempString.split("\t")[0]);
                    double Pvalue = Double.parseDouble(tempString.split("\t")[1]);
                    double Fvalue = Double.parseDouble(tempString.split("\t")[2]);
                    AMResult.setRvalue(Rvalue);
                    AMResult.setPvalue(Pvalue);
                    AMResult.setFvalue(Fvalue);
                }
                line++;
            }
            reader.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            if (reader != null)
            {
                try
                {
                    reader.close();
                } catch (IOException e1)
                {
                }
            }
        }
        return AMResult;
    }

    public ArrayList<MethodResult> caculateAverageValue(ArrayList<MethodResult> list, int number_of_file,
                                                        int number_of_network, String algorithm_name) {
        ArrayList<MethodResult> averageList = new ArrayList<>();
        for (int i = 1; i <= number_of_file; i++) {
            MethodResult average = new MethodResult();
            average.setMethod_name(algorithm_name);
            average.setFile_number(i);
            averageList.add(average);
        }

        for(MethodResult tempResult : list) {
            String name = tempResult.getMethod_name();
            int file_number = tempResult.getFile_number();
            for(MethodResult tempAverage : averageList) {
                if(tempAverage.getFile_number() == tempResult.getFile_number()) {
                    tempAverage.setCostTime(tempAverage.getCostTime() + tempResult.getCostTime());
                    tempAverage.setNMI(tempAverage.getNMI() + tempResult.getNMI());
                    tempAverage.setNode_correct(tempAverage.getNode_correct() + tempResult.getNode_correct());
                    tempAverage.setRvalue(tempAverage.getRvalue() + tempResult.getRvalue());
                    tempAverage.setPvalue(tempAverage.getPvalue() + tempResult.getPvalue());
                    tempAverage.setFvalue(tempAverage.getFvalue() + tempResult.getFvalue());
                }
            }
        }

        for(MethodResult tempAverage : averageList) {
            tempAverage.setCostTime(tempAverage.getCostTime() / number_of_network);
            tempAverage.setNMI(tempAverage.getNMI() / number_of_network);
            tempAverage.setNode_correct(tempAverage.getNode_correct() / number_of_network);
            tempAverage.setRvalue(tempAverage.getRvalue() / number_of_network);
            tempAverage.setPvalue(tempAverage.getPvalue() / number_of_network);
            tempAverage.setFvalue(tempAverage.getFvalue() / number_of_network);
        }

        return averageList;
    }

    public ArrayList<String> caculateResults(ArrayList<MethodResult> list, String algorithm_name) {
        switch (algorithm_name) {
            case "clauset":
                algorithm_name = "Clauset";
                break;
            case "chen":
                algorithm_name = "Chen";
                break;
            case "lwp":
                algorithm_name = "LWP";
                break;
            case "ls":
                algorithm_name = "LS";
                break;
            case "vi":
                algorithm_name = "VI";
                break;
            case "lcd":
                algorithm_name = "LCD";
                break;
            case "iskcore":
                algorithm_name = "LCDPC3";
                break;
            case "pc":
                algorithm_name = "LCDPC1";
                break;
            case "pcWithoutPc":
                algorithm_name = "LCDPC2";
                break;
            case "isdegreeCn":
                algorithm_name = "LCDPC4";
                break;
            case "iswithoutpcCn":
                algorithm_name = "LCDPC5";
                break;
            case "fifth":
                algorithm_name = "RTLCD";
                break;
        }
        ArrayList<String> resultlist = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("0.0000");
        String costTime = "";
        String nmi = "";
        String p = "";
        String r = "";
        String f = "";
        String nodecor = "";
        for(MethodResult temp : list) {
            costTime = costTime + " " + temp.getCostTime();
            nmi = nmi + " " + df.format(temp.getNMI());
            p = p + " " + df.format(temp.getPvalue());
            r = r + " " + df.format(temp.getRvalue());
            f = f + " " + df.format(temp.getFvalue());
            nodecor = nodecor + " " + df.format(temp.getNode_correct());
        }
        costTime = "costTime    " + algorithm_name + " = [" + costTime + " ]";
        nmi = "NMI    " + algorithm_name + " = [" + nmi + " ]";
        r = "R    " + algorithm_name + " = [" + r + " ]";
        p = "P    " + algorithm_name + " = [" + p + " ]";
        f = "F    " + algorithm_name + " = [" + f + " ]";
        nodecor = "NODECOR    " + algorithm_name + " = [" + nodecor + " ]";
        resultlist.add(costTime);
        resultlist.add(nmi);
        resultlist.add(r);
        resultlist.add(p);
        resultlist.add(f);
        resultlist.add(nodecor);
        return resultlist;
    }

    public void showResults(ArrayList<ArrayList<String>> resultLists) {
        String costTime = "";
        String nmi = "";
        String r = "";
        String p = "";
        String f = "";
        String nodecor = "";

        for(ArrayList<String> list : resultLists) {
            costTime = costTime + "\n" + list.get(0);
            nmi = nmi + "\n" + list.get(1);
            r = r + "\n" + list.get(2);
            p = p + "\n" + list.get(3);
            f = f + "\n" + list.get(4);
            nodecor = nodecor + "\n" + list.get(5);
        }
        System.out.println(nmi);
        System.out.println(r);
        System.out.println(p);
        System.out.println(f);
        System.out.println(costTime);
//        System.out.println(nodecor);
    }

    public static void main(String[] args) throws IOException {

        String file_path_static = "F:/tests on artificial networks/1000/c_pc/";
        //mu_pc;8;c_pc;8;k_pc;8;k_Orient;11;mu_Orient;8;c_Orient;5;
        int number_of_file = 5;
        int number_of_network = 1;
        /************      add method      ************/
        ArrayList<String> algorithm_names = new ArrayList<>();
//        algorithm_names.add("clauset");
//        algorithm_names.add("lwp");
//        algorithm_names.add("chen");
//        algorithm_names.add("ls");
//        algorithm_names.add("vi");

//        algorithm_names.add("isdegree");
//        algorithm_names.add("iswithoutpc");


        algorithm_names.add("lcd");
//        algorithm_names.add("isdegreeFirst");
//        algorithm_names.add("isComponent");
//        algorithm_names.add("componentWithoutpc");

//        algorithm_names.add("iskcore");
//        algorithm_names.add("isdegreeCn");
//        algorithm_names.add("iswithoutpcCn");

//        algorithm_names.add("pc");
//        algorithm_names.add("pcWithoutPc");

        algorithm_names.add("fifth");
        algorithm_names.add("fifthPC");

        String file_result = "";
        ReadArtificialResults reader = new ReadArtificialResults();
        ArrayList<ArrayList<String>> resultLists = new ArrayList<>();
        for(String method_name : algorithm_names) {
            ArrayList<MethodResult> resultArrayList = new ArrayList<>();

            for(int file_number=1; file_number<=number_of_file; file_number++) {
                for(int network_number=1; network_number<=number_of_network; network_number++) {
                    file_result = file_path_static + file_number + "/"
                            + network_number + "_results.txt(" + method_name + ").txt";
                    MethodResult a = reader.read_artificial_results(file_number, network_number,
                            method_name, file_result);
                    resultArrayList.add(a);
                }
            }
            ArrayList<MethodResult> averageResultArrayList = new ArrayList<>();
            averageResultArrayList = reader.caculateAverageValue(resultArrayList, number_of_file, number_of_network, method_name);
            resultLists.add(reader.caculateResults(averageResultArrayList, method_name));
        }
        reader.showResults(resultLists);
    }

}
