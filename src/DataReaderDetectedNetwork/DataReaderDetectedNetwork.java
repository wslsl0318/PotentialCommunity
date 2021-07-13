package DataReaderDetectedNetwork;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DataReaderDetectedNetwork
{
    public String file_path_result;
    public ArrayList<ArrayList<Integer>> detected_communities;

    public DataReaderDetectedNetwork()
    {
        this.detected_communities = new ArrayList<ArrayList<Integer>>();
    }

    public DataReaderDetectedNetwork(String file_path_result)
    {
        this.file_path_result = file_path_result;
        this.detected_communities = new ArrayList<ArrayList<Integer>>();
    }

    /* (Tested) read_detected_communities */
    public void read_detected_communities()
    {
        File file = new File(file_path_result);
        BufferedReader reader = null;
        try
        {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            while ((tempString = reader.readLine()) != null)
            {
                line++;
                String[] strs;
                ArrayList<Integer> community = new ArrayList<Integer>();
                if(!"seed".equals(tempString.split(":")[0])) {
                    continue;
                }
                String seed = tempString.split("\t")[0];
                seed = seed.split(" ")[1];
                community.add(Integer.parseInt(seed));
                tempString = tempString.split(":")[2];
                strs = tempString.split("\t");

                for (int i = 0; i < strs.length; i++)
                {
                    if(!" ".equals(strs[i])) {
                        int node = Integer.parseInt(strs[i].trim());
                        community.add(node);
                    }
                }
                this.detected_communities.add(community);
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
    }

    public static void main(String[] args) throws IOException
    {
        String file_path = "F:/1papers/20190411 first/data set/";
        String file_name = "karate";
        String method_name = "chen";
        // karate;dolphin;football;krebsbook;dblp;amazon;youtube
        String network = "_network.txt";
        // karate_network;dolphin_network;football_network;krebsbook_network
        // dblp_network;amazon_network;youtube_network
        String community = "_community.txt";
        // karate_community;dolphin_community;football_community;krebsbook_community
        // dblp_community;amazon_community;youtube_community
        String table = "_table.txt";
        // karate_table;dolphin_table;football_table;krebsbook_table
        // dblp_table;amazon_table;youtube_table

        String file_result = file_path + file_name + "/" + file_name + "_result(" + method_name + ").txt";

        DataReaderDetectedNetwork reader = new DataReaderDetectedNetwork(file_result);
        reader.read_detected_communities();
    }
}
