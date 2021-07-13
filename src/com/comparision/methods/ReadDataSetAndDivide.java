package com.comparision.methods;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class ReadDataSetAndDivide {
    public ArrayList<ArrayList<String>> dataset = new ArrayList<>();
    public ArrayList<ArrayList<ArrayList<Integer>>> datasetInt = new ArrayList<>();


    public void readCommunity(String path, String name) {
        ArrayList<String> dataset1 = new ArrayList<>();
        ArrayList<String> dataset2 = new ArrayList<>();
        ArrayList<String> dataset3 = new ArrayList<>();
        ArrayList<String> dataset4 = new ArrayList<>();
        ArrayList<String> dataset5 = new ArrayList<>();
        ArrayList<String> dataset6 = new ArrayList<>();
        ArrayList<String> dataset7 = new ArrayList<>();
        ArrayList<String> dataset8 = new ArrayList<>();
        ArrayList<String> dataset9 = new ArrayList<>();
        ArrayList<String> dataset10 = new ArrayList<>();
        ArrayList<String> dataset11 = new ArrayList<>();

        path = path + name + "/" + name + "_community.txt";
        File file = new File(path);
        BufferedReader reader = null;
        try
        {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null)
            {
                String[] strs;
                strs = tempString.split("\t");
                int length = strs.length;
                if(length <= 10) {
                    dataset1.add(tempString);
                }
                else if(length > 10 && length <= 20) {
                    dataset2.add(tempString);
                }
                else if(length > 20 && length <= 30) {
                    dataset3.add(tempString);
                }
                else if(length > 30 && length <= 40) {
                    dataset4.add(tempString);
                }
                else if(length > 40 && length <= 50) {
                    dataset5.add(tempString);
                }
                else if(length > 50 && length <= 100) {
                    dataset6.add(tempString);
                }
                else if(length > 100 && length <= 200) {
                    dataset7.add(tempString);
                }
                else if(length > 200 && length <= 300) {
                    dataset8.add(tempString);
                }
                else if(length > 300 && length <= 400) {
                    dataset9.add(tempString);
                }
                else if(length > 400 && length <= 500) {
                    dataset10.add(tempString);
                }
                else if(length > 500 && length <= 1000) {
                    dataset11.add(tempString);
                }
            }
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
        dataset.add(dataset1);
        dataset.add(dataset2);
        dataset.add(dataset3);
        dataset.add(dataset4);
        dataset.add(dataset5);
        dataset.add(dataset6);
        dataset.add(dataset7);
        dataset.add(dataset8);
        dataset.add(dataset9);
        dataset.add(dataset10);
        dataset.add(dataset11);
    }

    public void writeCommunity(String path, String name) throws IOException {
        String write_path = path + name + "/";
        int num = 1;
        for(ArrayList<String> tempdataset : dataset) {
            FileOutputStream fos = new FileOutputStream(write_path + name + num + "_community.txt");
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(osw);
            for(String tempdatasetIndex : tempdataset) {
                bw.write(tempdatasetIndex +"\r\n");
            }
            num ++;
            bw.close();
        }
    }

    public void readTable(String path, String name) {
    }

    public void writeTable(String path, String name) throws IOException {
    }

    public void random(String path, String name) {
        for(int i=1; i<12; i++) {
            ArrayList<Integer> list = new ArrayList<>();
            String readPath = path + name + "/" + name + i + "_community.txt";
            File file = new File(readPath);
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(file));
                String tempString = null;
                int line = 1;
                Random r = new Random();

                while ((tempString = reader.readLine()) != null) {
                    String strs[];
                    strs = tempString.split("\t");
                    int length = strs.length;

                    int randomNode = r.nextInt(length);
                    list.add(Integer.parseInt(strs[randomNode]));

                    line ++;
            }
                System.out.println("//" + name + i);
                System.out.println("seeds.clear();");
                boolean[]  bool = new boolean[line-1];
                int randomLine = 0;
                for(int j=1; j<line; j++) {
                    do{
                        randomLine = r.nextInt(line-1);

                    }while(bool[randomLine]);
                    bool[randomLine] = true;
                    int node = list.get(randomLine);
                    System.out.println("seeds.add(" + node + ");");
                }

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
    }

    }

    public static void main(String[] args) throws IOException {

        String file_path = "F:/1papers/20190411 first/data set/";
        String algiorhtm_name = "youtube";
        ReadDataSetAndDivide read = new ReadDataSetAndDivide();

        read.readCommunity(file_path, algiorhtm_name);
        read.writeCommunity(file_path, algiorhtm_name);
//        read.readTable(file_path, algiorhtm_name);
//        read.writeTable(file_path, algiorhtm_name);

        //test
//        read.random(file_path, algiorhtm_name);
    }

}
