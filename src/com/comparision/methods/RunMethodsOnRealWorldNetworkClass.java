package com.comparision.methods;

import DataReaderRealWorldNetwork.DataReaderRealWorldNetwork;
import com.comparision.chen.ChenAlgorithm;
import com.comparision.clauset.ClausetAlgorithm;
import com.comparision.fifth.FifthAlgorithm;
import com.comparision.fifth.NodeImportance;
import com.comparision.is.IsAlgorithm;
import com.comparision.is.IsAlgorithmDing;
import com.comparision.is.IsAlgorithmFirst;
import com.comparision.lcd.LCDAlgorithm;
import com.comparision.ls.LSAlgorithm;
import com.comparision.lwp.LWPAlgorithm;
import com.comparision.pc.PCAlgorithm;
import com.comparision.vi.VIAlgorithm;
import com.pulicoperations.methods.NMI;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.TreeSet;

public class RunMethodsOnRealWorldNetworkClass {
    public DataReaderRealWorldNetwork data_reader;
    public int n;// node number
    public int m;// link number
    public int nc;// community number;
    public ArrayList<ArrayList<Integer>> adjacencyTable;
    public ArrayList<ArrayList<Integer>> real_communities;
    public String file_path_result;
    public TreeSet<NodeDegree> nodes_degree;

    public RunMethodsOnRealWorldNetworkClass(String file_path_community,
                                             String file_path_table, String file_path_result)
    {
        this.data_reader = new DataReaderRealWorldNetwork(file_path_community,
                file_path_table);
        this.adjacencyTable = new ArrayList<ArrayList<Integer>>();
        this.real_communities = new ArrayList<ArrayList<Integer>>();
        this.file_path_result = file_path_result;
        this.nodes_degree = new TreeSet<NodeDegree>(new NodeDegree());
    }

    /* (Tested) initial n, m, nc, adjacency table, real communities */
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
        System.out.println("node number: " + n);
        System.out.println("link number: " + m);
        System.out.println("community number: " + nc);
    }

    //20200120 fifth_algorithm needs
    /* (Tested) Get node neighbors */
    public TreeSet<Integer> get_node_neighbors(int node_id)
    {
        TreeSet<Integer> neighbors = new TreeSet<Integer>();
        neighbors.addAll(adjacencyTable.get(node_id));
        neighbors.remove((Integer) node_id);
        return neighbors;
    }

    //20200120 fifth_algorithm needs
    /* (Tested) Calculate node importance */
    public int calculate_node_importance(int node_id)
    {
        TreeSet<Integer> neighbors_tree = get_node_neighbors(node_id);
        ArrayList<Integer> neighbors = new ArrayList<Integer>();
        neighbors.addAll(neighbors_tree);
        int links_between_neighbors = 0;
        int all_links = 0;
        for (int i = 0; i < neighbors.size(); i++)
        {
            int neighbor_1 = neighbors.get(i);
            for (int j = i + 1; j < neighbors.size(); j++)
            {
                int neighbor_2 = neighbors.get(j);
                if (adjacencyTable.get(neighbor_1).contains(neighbor_2))
                {
                    links_between_neighbors++;
                }
            }
        }
        all_links = links_between_neighbors + neighbors.size();
        // System.out.println(all_links);
        return all_links;
    }

    //20200120 fifth_algorithm needs
    /* (Tested) calculate_node_importance_for_all_nodes */
    public ArrayList<NodeImportance> calculate_node_importance_for_all_nodes()
    {
        ArrayList<NodeImportance> nodes_importance = new ArrayList<NodeImportance>();
        for (int i = 0; i < this.n; i++)
        {
            NodeImportance node_importance = new NodeImportance(i,
                    calculate_node_importance(i));
            nodes_importance.add(node_importance);
        }
        return nodes_importance;
    }

    /* (Tested) Get insection size */
    public int get_insection_size(ArrayList<Integer> detected_community,
                                  ArrayList<Integer> real_community)
    {
        int insection_size = 0;
        for (Iterator<Integer> iter = detected_community.iterator(); iter
                .hasNext();)
        {
            Integer member = iter.next();
            if (real_community.contains(member))
            {
                insection_size++;
            }
        }
        return insection_size;
    }

    /* (Tested) Get insection size */
    public int get_insection_size(TreeSet<Integer> community,
                                  TreeSet<Integer> detected_community)
    {
        int insection_size = 0;
        for (Iterator<Integer> iter = community.iterator(); iter.hasNext();)
        {
            Integer member = iter.next();
            if (detected_community.contains(member))
            {
                insection_size++;
            }
        }
        return insection_size;
    }

    /* (Tested) Get real partitions containing the seed node */
    public ArrayList<ArrayList<ArrayList<Integer>>> get_real_partitions_containing_seed(
            int seed_id)
    {
        ArrayList<ArrayList<Integer>> real_communities_containing_seed = new ArrayList<ArrayList<Integer>>();
        for (Iterator<ArrayList<Integer>> iter = real_communities.iterator(); iter
                .hasNext();)
        {
            ArrayList<Integer> real_community = iter.next();
            if (real_community.contains((Integer) seed_id))
            {
                real_communities_containing_seed.add(real_community);
            }
        }
        ArrayList<ArrayList<ArrayList<Integer>>> partitionRS = new ArrayList<ArrayList<ArrayList<Integer>>>();
        for (Iterator<ArrayList<Integer>> iter = real_communities_containing_seed
                .iterator(); iter.hasNext();)
        {
            ArrayList<Integer> real_community = iter.next();
            ArrayList<Integer> rest_nodes = new ArrayList<Integer>();
            for (int i = 0; i < this.n; i++)
            {
                if (!real_community.contains(i))
                {
                    rest_nodes.add(i);
                }
            }
            ArrayList<ArrayList<Integer>> partitionR = new ArrayList<ArrayList<Integer>>();
            partitionR.add(real_community);
            partitionR.add(rest_nodes);
            partitionRS.add(partitionR);
        }
        return partitionRS;
    }

    /*
     * (Tested) Get the nodes with max degree from real communities as seeds for
     * algorithms
     */
    public TreeSet<Integer> get_max_degree_nodes_from_real_communities()
    {
        TreeSet<Integer> seeds = new TreeSet<Integer>();
        for (Iterator<ArrayList<Integer>> iter_real_community = real_communities
                .iterator(); iter_real_community.hasNext();)
        {
            ArrayList<Integer> real_community = iter_real_community.next();
            int node_with_max_degree = -1;
            int max_degree = 0;
            for (Iterator<Integer> iter_member = real_community.iterator(); iter_member
                    .hasNext();)
            {
                Integer member = iter_member.next();
                if (adjacencyTable.get(member).size() > max_degree)
                {
                    max_degree = adjacencyTable.get(member).size();
                    node_with_max_degree = member;
                }
            }
            seeds.add(node_with_max_degree);
        }
        return seeds;
    }

    /* (Tested) write results into files */
    public void write_results_into_files(String algorithm_name,
                                         long costTime_average, double NMI_average,
                                         double nodes_correctly_classified_percentage,
                                         ArrayList<Double> RPF_average,
                                         ArrayList<SeedAndCommunity> detected_communities)
            throws IOException
    {
        String file = this.file_path_result + algorithm_name;
        FileOutputStream fos = new FileOutputStream(file);
        OutputStreamWriter osw = new OutputStreamWriter(fos);
        BufferedWriter bw = new BufferedWriter(osw);
        bw.write("costTime_average: " + costTime_average + "\r\n");
        bw.write("NMI_average: " + NMI_average + "\r\n");
        bw.write("nodes_correctly_classified_percentage: "
                + nodes_correctly_classified_percentage + "\r\n");
        bw.write("RPF_average: " + "\r\n");
        for (Iterator<Double> iter_rpf = RPF_average.iterator(); iter_rpf
                .hasNext();)
        {
            Double rpf = iter_rpf.next();
            bw.write(rpf + "\t");
        }
        bw.write("\r\n");
        bw.write("detected_communities:" + "\r\n");
        for (Iterator<SeedAndCommunity> iter_community = detected_communities
                .iterator(); iter_community.hasNext();)
        {
            SeedAndCommunity community = iter_community.next();
            bw.write("seed: " + community.seed + "\t" + "community: ");
            for (Iterator<Integer> iter_member = community.community.iterator(); iter_member
                    .hasNext();)
            {
                Integer member = iter_member.next();
                bw.write(member + "\t");
            }
            bw.write("\r\n");
        }

        bw.write("\r\n");
        DecimalFormat df = new DecimalFormat("0.00");
        bw.write("NMI " + df.format(NMI_average) + "\r\n");
        bw.write("R " + df.format(RPF_average.get(0)) + "\r\n");
        bw.write("P " + df.format(RPF_average.get(1)) + "\r\n");
        bw.write("F-score " + df.format(RPF_average.get(2)) + "\r\n");
        bw.write("time cost(ms) " + costTime_average + "\r\n");
        bw.write("NCR " + df.format(nodes_correctly_classified_percentage) + "\r\n");
        bw.write(df.format(NMI_average) + "\r\n");
        bw.write(df.format(RPF_average.get(0)) + "\r\n");
        bw.write(df.format(RPF_average.get(1)) + "\r\n");
        bw.write(df.format(RPF_average.get(2)) + "\r\n");
        bw.write(costTime_average + "\r\n");
        bw.write(df.format(nodes_correctly_classified_percentage) + "\r\n");

        bw.close();
    }

    public void run_methords_on_realworld_network(ArrayList<String> algorithm_names, String run_type) throws IOException {
        /*****************************************************************************************/
        initial_paramaters();
        ArrayList<NodeImportance> nodes_importance = calculate_node_importance_for_all_nodes();
        /************      add method      ************/
        LWPAlgorithm lwp = new LWPAlgorithm(n, m, adjacencyTable);
        ChenAlgorithm chen = new ChenAlgorithm(n, m, adjacencyTable);
        LSAlgorithm ls = new LSAlgorithm(n, m, adjacencyTable);
        LCDAlgorithm lcd = new LCDAlgorithm(n, m, adjacencyTable);
        VIAlgorithm vi =new VIAlgorithm(n, m, adjacencyTable);
        IsAlgorithm isComponent = new IsAlgorithm(n, m, adjacencyTable,"yes","yes");
        IsAlgorithm iskcore = new IsAlgorithm(n, m, adjacencyTable,"yes","yes");
        IsAlgorithm componentWithoutpc = new IsAlgorithm(n, m, adjacencyTable,"no","yes");
        IsAlgorithm isdegreeCn = new IsAlgorithm(n, m, adjacencyTable,"yes","no");
        IsAlgorithm iswithoutpcCn = new IsAlgorithm(n, m, adjacencyTable,"no","no");
        IsAlgorithmDing isdegreeDing = new IsAlgorithmDing(n, m, adjacencyTable,"yes","yes");
        PCAlgorithm pc = new PCAlgorithm(n, m, adjacencyTable,"yes");
        PCAlgorithm pcWithoutPc = new PCAlgorithm(n, m, adjacencyTable,"no");

        FifthAlgorithm fifthPC = new FifthAlgorithm(n, m, adjacencyTable, nodes_importance,"yes");
        FifthAlgorithm fifth = new FifthAlgorithm(n, m, adjacencyTable, nodes_importance,"no");
        ClausetAlgorithm clausetPC = new ClausetAlgorithm(n, m, adjacencyTable, "yes");
        ClausetAlgorithm clauset = new ClausetAlgorithm(n, m, adjacencyTable, "no");

        //run on core_node or all nodes
        TreeSet<Integer> seeds = new TreeSet<>();
        int seed_size = 0;
        int count = 0;
        if ("all_nodes".equals(run_type)) {
            seed_size = this.n;
            for (int i = 0; i < seed_size; i++) {
                seeds.add(i);
            }
        } else if ("core_node".equals(run_type)) {
            seeds = get_max_degree_nodes_from_real_communities();
            seed_size = seeds.size();
        } else if ("random_node".equals(run_type)) {
            Random r = new Random();
            for(int i=0 ; i<20 ;  i++) {
                int random = r.nextInt(this.n);
                System.out.println(random);
                seeds.add(random);
            }
            seed_size = seeds.size();
        }


        for(String method_name : algorithm_names) {
            String algorithm_name = "(" + method_name + ").txt";
            double NMI_average = 0;
            /* The percentage of nodes that are correctly classified */
            double nodes_correctly_classified_percentage = 0;
            long costTime_average = 0;
            /* r, p, f */
            ArrayList<Double> rpf_average = new ArrayList<Double>();
            double r_average = 0;
            double p_average = 0;
            double f_average = 0;
            /* detected distinct communities with out sub set */
            ArrayList<SeedAndCommunity> detected_communities = new ArrayList<SeedAndCommunity>();

            if ("iskcore".equals(method_name)) {
                iskcore.initial("kcore");
            } else if ("isComponent".equals(method_name)) {
                isComponent.initial("degree");
            } else if ("componentWithoutpc".equals(method_name)) {
                componentWithoutpc.initial("degree");
            } else if ("isdegreeCn".equals(method_name)) {
                isdegreeCn.initial("degree");
            } else if ("iswithoutpcCn".equals(method_name)) {
                iswithoutpcCn.initial("degree");
            } else if ("isdegreeDing".equals(method_name)) {
                isdegreeDing.initial("degree");
            }


            count = seed_size;
            for (int seed_id : seeds) {
                count--;
                System.out.println(method_name + " left node sizes: " + count);
                System.out.println(method_name + " seed_id: " + seed_id);
                long startTime_once = 0;
                long endTime_once = 0;
                ArrayList<ArrayList<Integer>> partitionF = new ArrayList<ArrayList<Integer>>();
                ArrayList<ArrayList<ArrayList<Integer>>> partitionFS = new ArrayList<ArrayList<ArrayList<Integer>>>();
                ArrayList<ArrayList<ArrayList<Integer>>> partitionRS = get_real_partitions_containing_seed(seed_id);

                startTime_once = System.currentTimeMillis();
                /************      add method      ************/
                switch (method_name) {
                    case "chen":
                        partitionF = chen.run_chen(seed_id);
                        break;
                    case "lwp":
                        partitionF = lwp.run_lwp(seed_id);
                        break;
                    case "ls":
                        partitionF = ls.run_ls(seed_id);
                        break;
                    case "vi":
                        partitionF = vi.run_vi(seed_id);
                        break;
                    case "iskcore":
                        partitionF = iskcore.run_is(seed_id);
                        break;
                    case "isComponent":
                        partitionF = isComponent.run_is(seed_id);
                        break;
                    case "componentWithoutpc":
                        partitionF = componentWithoutpc.run_is(seed_id);
                        break;
                    case "isdegreeCn":
                        partitionF = isdegreeCn.run_is(seed_id);
                        break;
                    case "iswithoutpcCn":
                        partitionF = iswithoutpcCn.run_is(seed_id);
                        break;
                    case "isdegreeDing":
                        partitionF = isdegreeDing.run_is(seed_id);
                        break;

                    case "pc":
                        partitionF = pc.run_pc(seed_id);
                        break;
                    case "pcWithoutPc":
                        partitionF = pcWithoutPc.run_pc(seed_id);
                        break;
                    case "fifth":
                        partitionF = fifth.run_fifth(seed_id);
                        break;
                    case "fifthPC":
                        partitionF = fifthPC.run_fifth(seed_id);
                        break;
                    case "clauset":
                        partitionF = clauset.run_clauset(seed_id);
                        break;
                    case "clausetPC":
                        partitionF = clausetPC.run_clauset(seed_id);
                        break;
                }
                if ("lcd".equals(method_name)) {
                    /* lcd */
                    partitionFS = lcd.run_lcd(seed_id);
                    endTime_once = System.currentTimeMillis();
                    long costTime_once = endTime_once - startTime_once;
                    costTime_average += costTime_once;
                    double NMI_once_average = 0;
                    boolean is_partitionFS_contain_seed = false;
                    double r_once_average = 0;
                    double p_once_average = 0;
                    double f_once_average = 0;
                    for (Iterator<ArrayList<ArrayList<Integer>>> iter_partitionR = partitionRS
                            .iterator(); iter_partitionR.hasNext(); ) {
                        ArrayList<ArrayList<Integer>> partitionR = iter_partitionR
                                .next();
                        double NMI_once_average_one = 0;
                        double r_once_average_one = 0;
                        double p_once_average_one = 0;
                        double f_once_average_one = 0;
                        for (Iterator<ArrayList<ArrayList<Integer>>> iter_partitionF = partitionFS
                                .iterator(); iter_partitionF.hasNext(); ) {
                            partitionF = iter_partitionF.next();
                            SeedAndCommunity seed_community = new SeedAndCommunity(
                                    seed_id, partitionF.get(0));
                            detected_communities.add(seed_community);
                            if (partitionF.get(0).contains(seed_id)) {
                                is_partitionFS_contain_seed = true;
                            }
                            double NMI_value = NMI.NMIPartitionUnoverLap(partitionF,
                                    partitionR, this.n);
                            NMI_once_average_one += NMI_value;
                            int partitionF_size = partitionF.get(0).size();
                            int partitionR_size = partitionR.get(0).size();
                            int insection_size = get_insection_size(partitionF.get(0),
                                    partitionR.get(0));
                            double r;
                            double p;
                            double f;
                            if (insection_size == 0) {
                                r = 0;
                                p = 0;
                                f = 0;
                            } else {
                                r = (double) insection_size / (double) partitionR_size;
                                p = (double) insection_size / (double) partitionF_size;
                                f = 2 * r * p / (r + p);
                            }
                            r_once_average_one += r;
                            p_once_average_one += p;
                            f_once_average_one += f;
                        }
                        NMI_once_average_one /= partitionFS.size();
                        NMI_once_average += NMI_once_average_one;
                        r_once_average_one /= partitionFS.size();
                        p_once_average_one /= partitionFS.size();
                        f_once_average_one /= partitionFS.size();
                        r_once_average += r_once_average_one;
                        p_once_average += p_once_average_one;
                        f_once_average += f_once_average_one;
                    }
                    NMI_once_average /= partitionRS.size();
                    NMI_average += NMI_once_average;
                    if (is_partitionFS_contain_seed) {
                        nodes_correctly_classified_percentage++;
                    }
                    r_once_average /= partitionRS.size();
                    p_once_average /= partitionRS.size();
                    f_once_average /= partitionRS.size();
                    r_average += r_once_average;
                    p_average += p_once_average;
                    f_average += f_once_average;
                } else {
                    SeedAndCommunity seed_community = new SeedAndCommunity(
                            seed_id, partitionF.get(0));
                    detected_communities.add(seed_community);
                    endTime_once = System.currentTimeMillis();
                    long costTime_once = endTime_once - startTime_once;
                    costTime_average += costTime_once;
                    double NMI_once_average = 0;
                    if (partitionF.get(0).contains(seed_id)) {
                        nodes_correctly_classified_percentage++;
                    }
                    double r_once_average = 0;
                    double p_once_average = 0;
                    double f_once_average = 0;
                    for (Iterator<ArrayList<ArrayList<Integer>>> iter_partitionR = partitionRS
                            .iterator(); iter_partitionR.hasNext(); ) {
                        ArrayList<ArrayList<Integer>> partitionR = iter_partitionR
                                .next();
                        double NMI_value = NMI.NMIPartitionUnoverLap(partitionF,
                                partitionR, this.n);
                        NMI_once_average += NMI_value;
                        int partitionF_size = partitionF.get(0).size();
                        int partitionR_size = partitionR.get(0).size();
                        int insection_size = get_insection_size(partitionF.get(0),
                                partitionR.get(0));
                        double r;
                        double p;
                        double f;
                        if (insection_size == 0) {
                            r = 0;
                            p = 0;
                            f = 0;
                        } else {
                            r = (double) insection_size / (double) partitionR_size;
                            p = (double) insection_size / (double) partitionF_size;
                            f = 2 * r * p / (r + p);
                        }
                        r_once_average += r;
                        p_once_average += p;
                        f_once_average += f;
                    }
                    NMI_once_average /= partitionRS.size();
                    NMI_average += NMI_once_average;
                    r_once_average /= partitionRS.size();
                    p_once_average /= partitionRS.size();
                    f_once_average /= partitionRS.size();
                    r_average += r_once_average;
                    p_average += p_once_average;
                    f_average += f_once_average;
                }
            }
            costTime_average /= seed_size;
            NMI_average /= seed_size;
            nodes_correctly_classified_percentage /= (double) seed_size;
            r_average /= seed_size;
            p_average /= seed_size;
            f_average /= seed_size;
            rpf_average.add(r_average);
            rpf_average.add(p_average);
            rpf_average.add(f_average);
            write_results_into_files(algorithm_name,
                    costTime_average, NMI_average,
                    nodes_correctly_classified_percentage,
                    rpf_average, detected_communities);
            System.out.println(method_name + ":");
            System.out.println("costTime_average:" + costTime_average);
            System.out.println("NMI_average:" + NMI_average);
            System.out.println("r_average:" + r_average);
            System.out.println("p_average:" + p_average);
            System.out.println("f_average:" + f_average);
            System.out.println("nodes_correctly_classified_percentage:" + nodes_correctly_classified_percentage);
        }
    }

    public static void main(String[] args) throws IOException {
        String dateset_name = "krebsbook";
        //karate;dolphin;football;krebsbook;
        //musae_DE;musae_EN;musae_ES;musae_facebook;musae_FR;musae_github;musae_PT;musae_RU;
        //Combined-APMS;Ito-core;LC-multiple;Uetz-screen;Y2H-union;CCSB-YI1;
        //dblp;amazon;youtube;lj;
        //lastfm_asia;

        String name = dateset_name;
        String file_path = "F:/1papers/20190411 first/data set/";
        int dataSetNumbers = 1;
        if("dblp".equals(name) || "youtube".equals(name)) {
            dataSetNumbers = 11;
            file_path = file_path + name + "/";
        }
        for (int i = 1; i <= dataSetNumbers; i++) {
            if("dblp".equals(name) || "youtube".equals(name)) {
                dateset_name = name + i;
                System.out.println(dateset_name);
            }

            //PC Â·¾¶
            String finial_path = file_path + dateset_name + "/";
            //MAC Â·¾¶
//            String file_path = "/Users/long/Desktop/data set/" + dateset_name + "/";
            String file_name = dateset_name;
            String network = "_network.txt";
            String community = "_community.txt";
            String table = "_table.txt";
            String result = "_result";
            String file_path_network = finial_path + file_name + network;
            String file_path_community = finial_path + file_name + community;
            String file_path_table = finial_path + file_name + table;
            String file_path_result = finial_path + file_name + result;
            RunMethodsOnRealWorldNetworkClass run_methords = new RunMethodsOnRealWorldNetworkClass(
                    file_path_community, file_path_table, file_path_result);
            ArrayList<String> algorithm_names = new ArrayList<>();
            /************      add method      ************/
//            algorithm_names.add("pc");
//            algorithm_names.add("pcWithoutPc");

//            algorithm_names.add("lwp");
//            algorithm_names.add("ls");
//            algorithm_names.add("chen");


//            algorithm_names.add("vi");
//            algorithm_names.add("lcd");

//            algorithm_names.add("isComponent");
//            algorithm_names.add("componentWithoutpc");

//          algorithm_names.add("isdegreeCn");
//          algorithm_names.add("iswithoutpcCn");
//          algorithm_names.add("iskcore");

//            algorithm_names.add("fifthPC");
            algorithm_names.add("fifth");
//            algorithm_names.add("clausetPC");
            algorithm_names.add("clauset");


            String run_type = "core_node";   //core_node; all_nodes; random_node
            run_methords.run_methords_on_realworld_network(algorithm_names, run_type);

            //Remind
            System.out.println("Warning: THE PROGRAM HAS FINISHED RUNNING!!!");
//        Remind r = new Remind();
//        r.remind();
        }
    }

}