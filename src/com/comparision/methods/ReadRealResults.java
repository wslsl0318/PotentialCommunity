package com.comparision.methods;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ReadRealResults {

    public MethodResult ReadRealResults(String file_path_result, String method_name, String dataset_name)
    {
        File file = new File(file_path_result);
        BufferedReader reader = null;
        MethodResult RealResult = new MethodResult();
        RealResult.setMethod_name(method_name);
        RealResult.setDataset_name(dataset_name);
        try
        {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            while ((tempString = reader.readLine()) != null && line <=5)
            {
                if(line == 1) {
                    int costTime = Integer.parseInt(tempString.split(" ")[1]);
                    RealResult.setCostTime(costTime);
                }
                else if(line == 2) {
                    double NMI = Double.parseDouble(tempString.split(" ")[1]);
                    RealResult.setNMI(NMI);
                }
                else if(line == 3) {
                    double node_correct = Double.parseDouble(tempString.split(" ")[1]);
                    RealResult.setNode_correct(node_correct);
                }
                else if(line == 5){
                    double Rvalue = Double.parseDouble(tempString.split("\t")[0]);
                    double Pvalue = Double.parseDouble(tempString.split("\t")[1]);
                    double Fvalue = Double.parseDouble(tempString.split("\t")[2]);
                    RealResult.setRvalue(Rvalue);
                    RealResult.setPvalue(Pvalue);
                    RealResult.setFvalue(Fvalue);
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
        return RealResult;
    }

    public void showResults(ArrayList<MethodResult> list, String dataset_name) {
        DecimalFormat df = new DecimalFormat("0.0000");
        String method_name = "";
        String costTime = "";
        String nmi = "";
        String p = "";
        String r = "";
        String f = "";
        String nodecor = "";
        System.out.print("\\textit{" + dataset_name + "}" );
        for(MethodResult temp : list) {
            method_name = method_name + "  &" + temp.getMethod_name();
            costTime = costTime + " &" + temp.getCostTime();
            nmi = nmi + " &" + df.format(temp.getNMI());
            p = p + " &" + df.format(temp.getPvalue());
            r = r + " &" + df.format(temp.getRvalue());
            f = f + " &" + df.format(temp.getFvalue());
            nodecor = nodecor + " &" + df.format(temp.getNode_correct());
        }

            costTime = "&\\textit{{Time}(ms)}" + costTime + "\\\\";
            nmi = "&\\textit{NMI}" + nmi + "\\\\";
            r = "&\\textit{Recall}" + r + "\\\\";
            p = "&\\textit{Precision}" + p + "\\\\";
            f = "&\\textit{F-Score}" + f + "\\\\";
            nodecor = "&\\textit{NODECOR}" + nodecor + "\\\\";

            System.out.println(method_name);
            System.out.println(nmi);
            System.out.println(r);
            System.out.println(p);
            System.out.println(f);
            System.out.println(costTime);
            System.out.println(nodecor);
            System.out.println();
    }

    public static void main(String[] args) throws IOException {

        ArrayList<String> dataSets = new ArrayList<>();
        dataSets.add("karate");
        dataSets.add("dolphin");
        dataSets.add("football");
        dataSets.add("krebsbook");

//        dataSets.add("amazon");
//        dataSets.add("lastfm_asia");


        /************      add method      ************/
        ArrayList<String> algorithm_names = new ArrayList<>();
//        algorithm_names.add("pc");
//        algorithm_names.add("pcWithoutPc");
//        algorithm_names.add("isdegree");
//        algorithm_names.add("iswithoutpc");

//        algorithm_names.add("fifth");

//        algorithm_names.add("clauset");
//        algorithm_names.add("lwp");
//        algorithm_names.add("chen");
//        algorithm_names.add("ls");
//        algorithm_names.add("vi");

//        algorithm_names.add("lcd");

//        algorithm_names.add("iskcore");
//        algorithm_names.add("isdegreeCn");
//        algorithm_names.add("iswithoutpcCn");

//        algorithm_names.add("isComponent");
//        algorithm_names.add("componentWithoutpc");

        algorithm_names.add("seedOrientDegree");
        algorithm_names.add("seedOrientKcore");

        String file_result = "";
        ReadRealResults reader = new ReadRealResults();
        for(String dataset_name : dataSets) {
            String file_path = "F:/1papers/20190411 first/data set/";
            file_path = file_path + dataset_name + "/";
            ArrayList<MethodResult> resultArrayList = new ArrayList<>();
            for(String method_name : algorithm_names) {
                file_result = file_path + dataset_name + "_result(" + method_name + ").txt";
                MethodResult a = reader.ReadRealResults(file_result, method_name, dataset_name);
                resultArrayList.add(a);
            }
            reader.showResults(resultArrayList, dataset_name);
        }
    }

}
