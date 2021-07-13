package com.comparision.methods;

import DataReaderDetectedNetwork.DataReaderDetectedNetwork;
import DataReaderRealWorldNetwork.DataReaderRealWorldNetwork;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;

/*读取社区信息，并输出标准社区结果，以供画图等功能使用。*/
public class ReadCommunityToTable {

    public DataReaderRealWorldNetwork data_reader;
    public DataReaderDetectedNetwork detected_reader;
    public int n;// node number
    public int m;// link number
    public int nc;// community number;
    public ArrayList<ArrayList<Integer>> adjacencyTable;
    public ArrayList<ArrayList<Integer>> real_communities;
    public ArrayList<ArrayList<Integer>> detected_communities;
    public String file_path_result;
    public TreeSet<NodeDegree> nodes_degree;
    public ReadCommunityToTable(String file_path_community, String file_path_table)
    {
        this.data_reader = new DataReaderRealWorldNetwork(file_path_community,
                file_path_table);
        this.adjacencyTable = new ArrayList<ArrayList<Integer>>();
        this.real_communities = new ArrayList<ArrayList<Integer>>();
        this.nodes_degree = new TreeSet<NodeDegree>(new NodeDegree());
    }

    public void initial_paramaters() throws IOException
    {
        this.data_reader.read_realworld_network();
        this.n = this.data_reader.n;
        this.m = this.data_reader.m;
        this.nc = this.data_reader.nc;
        this.adjacencyTable.addAll(this.data_reader.adjacencyTable);
        this.data_reader.adjacencyTable.clear();
        this.real_communities.addAll(this.data_reader.real_communities);
        this.data_reader.real_communities.clear();
    }

    public void to_net() throws IOException {
        initial_paramaters();
        System.out.println("net: ");
        int i = 1;
        for (ArrayList<Integer> temp : adjacencyTable) {
            int j = 1;
            int now = 0;
            for(int node : temp) {
                if(j == 1) {
                    now = node + 1;
                } else {
                    node = node + 1;
                    System.out.println(now + " " + node);
                }
                j++;
            }
        }
    }

    public void to_clu() {
        int i = 1;
        int nodes[] = new int[n];
        for(ArrayList<Integer> list : real_communities) {
            for(int node : list) {
                nodes[node] = i;
            }
            i++;
        }
        System.out.println("clu: ");
        for(int j : nodes) {
            System.out.println(j);
        }
    }


    public static void main(String[] args) throws IOException {
        String dateset_name = "karate";
        // karate;dolphin;football;krebsbook;dblp;amazon;youtube;lj
        String file_path = "F:/1papers/20190411 first/data set/" + dateset_name + "/";
        String file_name = dateset_name;
        String table = "_table.txt";
        String network = "_network.txt";
        String community = "_community.txt";
        String file_path_table = file_path + file_name + table;
        String file_path_network = file_path + file_name + network;
        String file_path_community = file_path + file_name + community;
        ReadCommunityToTable run = new ReadCommunityToTable(
                file_path_community, file_path_table);
        run.to_net();
        run.to_clu();
        System.out.println("dateset_name :  " + dateset_name);
    }
}
