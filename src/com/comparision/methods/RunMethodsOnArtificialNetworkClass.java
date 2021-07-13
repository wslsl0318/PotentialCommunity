package com.comparision.methods;

import DataReaderArtificialNetwork.DataReaderArtificialNetwork;
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
import java.util.TreeSet;

public class RunMethodsOnArtificialNetworkClass {
    public DataReaderArtificialNetwork data_reader;
    public int n;// node number
    public int m;// link number
    public int nc;// community number;
    public ArrayList<ArrayList<Integer>> adjacencyTable;
    public ArrayList<ArrayList<Integer>> real_communities;
    public String file_path_result;

    public RunMethodsOnArtificialNetworkClass(String file_path_network,
                                          String file_path_community, String file_path_result)
    {
        this.data_reader = new DataReaderArtificialNetwork(file_path_network,
                file_path_community);
        this.adjacencyTable = new ArrayList<ArrayList<Integer>>();
        this.real_communities = new ArrayList<ArrayList<Integer>>();
        this.file_path_result = file_path_result;
    }

    /* (Tested) initial n, m, nc, adjacency table, real communities */
    public void initial_paramaters() throws IOException {
        this.data_reader.read_artificial_network();
        this.n = this.data_reader.n;
        this.m = this.data_reader.m;
        this.nc = this.data_reader.nc;
        this.adjacencyTable.addAll(this.data_reader.adjacencyTable);
        this.data_reader.adjacencyTable.clear();
        this.real_communities.addAll(this.data_reader.real_communities);
        this.data_reader.real_communities.clear();
        // System.out.println("node number: " + n);
        // System.out.println("link number: " + m);
        // System.out.println("community number: " + nc);
        // System.out.println("adjacencyTable");
        // for (Iterator<ArrayList<Integer>> iter = adjacencyTable.iterator();
        // iter
        // .hasNext();)
        // {
        // System.out.println(iter.next());
        // }
        // System.out.println("real_communities");
        // for (Iterator<ArrayList<Integer>> iter = real_communities.iterator();
        // iter
        // .hasNext();)
        // {
        // System.out.println(iter.next());
        // }
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

    /* (Tested) Get the real partition containing the seed node */
    public ArrayList<ArrayList<Integer>> get_real_partition_containing_seed(
            int seed_id)
    {
        ArrayList<Integer> real_community_containing_seed = new ArrayList<Integer>();
        for (Iterator<ArrayList<Integer>> iter = real_communities.iterator(); iter
                .hasNext();)
        {
            ArrayList<Integer> real_community = iter.next();
            if (real_community.contains((Integer) seed_id))
            {
                real_community_containing_seed = real_community;
            }
        }
        ArrayList<Integer> rest_nodes = new ArrayList<Integer>();
        for (int i = 0; i < this.n; i++)
        {
            if (!real_community_containing_seed.contains(i))
            {
                rest_nodes.add(i);
            }
        }
        ArrayList<ArrayList<Integer>> partitionR = new ArrayList<ArrayList<Integer>>();
        partitionR.add(real_community_containing_seed);
        partitionR.add(rest_nodes);
        return partitionR;
    }

    /* (Tested) Get node neighbors */
    public TreeSet<Integer> get_node_neighbors(int node_id)
    {
        TreeSet<Integer> neighbors = new TreeSet<Integer>();
        neighbors.addAll(adjacencyTable.get(node_id));
        neighbors.remove((Integer) node_id);
        return neighbors;
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

    /* (Tested) wrute results into files */
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
        bw.close();
    }

    /* (Tested) wrute results into files */
    public static void write_average_results_into_files(String target_path,
                                                        ArrayList<ArrayList<Measurements>> measurements, int number_of_network, ArrayList<String> algorithm_names) throws IOException
    {
        String file = target_path + "results.txt";
        FileOutputStream fos = new FileOutputStream(file);
        OutputStreamWriter osw = new OutputStreamWriter(fos);
        BufferedWriter bw = new BufferedWriter(osw);

        for(int j = 0; j < algorithm_names.size(); j++) {
            String algorithm_name = algorithm_names.get(j);
            double average_NMI = 0;
            double average_r = 0;
            double average_p = 0;
            double average_f = 0;
            long average_timecost = 0;
            double average_nodes_correctly_classified_percentage = 0;
            ArrayList<String> NMI = new ArrayList<>();
            ArrayList<String> R = new ArrayList<>();
            ArrayList<String> P = new ArrayList<>();
            ArrayList<String> F = new ArrayList<>();
            ArrayList<String> NODECOR = new ArrayList<>();
            DecimalFormat df = new DecimalFormat("0.0000");
            for(int i = 0; i < number_of_network; i++) {
                Measurements measurement = measurements.get(i).get(j);
                average_NMI += measurement.NMI_average;
                average_r += measurement.RPF_average
                        .get(0);
                average_p += measurement.RPF_average
                        .get(1);
                average_f += measurement.RPF_average
                        .get(2);
                average_timecost += measurement.costTime_average;
                average_nodes_correctly_classified_percentage += measurement.nodes_correctly_classified_percentage;

                NMI.add(df.format(measurement.NMI_average));
                R.add(df.format(measurement.RPF_average.get(0)));
                P.add(df.format(measurement.RPF_average.get(1)));
                F.add(df.format(measurement.RPF_average.get(2)));
                NODECOR.add(df.format(measurement.nodes_correctly_classified_percentage));
            }
            average_NMI /= number_of_network;
            average_r /= number_of_network;
            average_p /= number_of_network;
            average_f /= number_of_network;
            average_timecost /= number_of_network;
            average_nodes_correctly_classified_percentage /= number_of_network;

            bw.write(algorithm_name + " algorithm: " + "\r\n");
            bw.write("costTime_average: " + average_timecost + "\r\n");
            bw.write("NMI_average: " + average_NMI + "\r\n");
            bw.write("nodes_correctly_classified_percentage: "
                    + average_nodes_correctly_classified_percentage
                    + "\r\n");
            bw.write("RPF_average: " + "\r\n");
            bw.write("R: " + average_r + "\t");
            bw.write("P: " + average_p + "\t");
            bw.write("F: " + average_f + "\t");
            bw.write("average_timecost: " + average_timecost + "\r\n");
            bw.write("\r\n");
            String nmi = "";
            String p = "";
            String r = "";
            String f = "";
            String nodecor = "";
            for(int num = 0; num < number_of_network; num++) {
                nmi += " " + NMI.get(num);
                p += " " + P.get(num);
                r += " " + R.get(num);
                f += " " + F.get(num);
                nodecor += " " + NODECOR.get(num);
            }
            nmi = "NMI    " + algorithm_name + " = [" + nmi + " ]";
            p = "P    " + algorithm_name + " = [" + p + " ]";
            r = "R    " + algorithm_name + " = [" + r + " ]";
            f = "F    " + algorithm_name + " = [" + f + " ]";
            nodecor = "NODECOR    " + algorithm_name + " = [" + nodecor + " ]";
//            System.out.println(algorithm_name + ": ");
//            System.out.println(nmi);
//            System.out.println(p);
//            System.out.println(r);
//            System.out.println(f);
//            System.out.println(nodecor);
            bw.write(algorithm_name + "\r\n");
            bw.write(nmi + "\r\n");
            bw.write(p + "\r\n");
            bw.write(r + "\r\n");
            bw.write(f + "\r\n");
            bw.write(nodecor + "\r\n");
        }

        bw.close();
        /* fifth,clauset,lwp,chen,ls,lcd,is_degree,is_kcore */
    }

    /***********************************************************************************/
    public Measurements run_method_on_artificial_network_core_only(
            TreeSet<Integer> nodes, String method_name) throws IOException {
        ArrayList<NodeImportance> nodes_importance = calculate_node_importance_for_all_nodes();
        /************      add method      ************/
        LWPAlgorithm lwp = new LWPAlgorithm(n, m, adjacencyTable);
        ChenAlgorithm chen = new ChenAlgorithm(n, m, adjacencyTable);
        LSAlgorithm ls = new LSAlgorithm(n, m, adjacencyTable);
        LCDAlgorithm lcd = new LCDAlgorithm(n, m, adjacencyTable);
        VIAlgorithm vi = new VIAlgorithm(n, m, adjacencyTable);
        IsAlgorithm iskcore = new IsAlgorithm(n, m, adjacencyTable,"yes","yes");
        IsAlgorithm isComponent = new IsAlgorithm(n, m, adjacencyTable,"yes","yes");
        IsAlgorithm componentWithoutpc = new IsAlgorithm(n, m, adjacencyTable,"no","yes");
        IsAlgorithm isdegreeCn = new IsAlgorithm(n, m, adjacencyTable,"yes","no");
        IsAlgorithm iswithoutpcCn = new IsAlgorithm(n, m, adjacencyTable,"no","no");
        IsAlgorithmDing isdegreeDing = new IsAlgorithmDing(n, m, adjacencyTable,"yes","no");
        PCAlgorithm pc = new PCAlgorithm(n, m, adjacencyTable,"yes");
        PCAlgorithm pcWithoutPc = new PCAlgorithm(n, m, adjacencyTable,"no");
        FifthAlgorithm fifthPC = new FifthAlgorithm(n, m, adjacencyTable, nodes_importance,"yes");
        FifthAlgorithm fifth = new FifthAlgorithm(n, m, adjacencyTable, nodes_importance,"no");
        ClausetAlgorithm clauset = new ClausetAlgorithm(n, m, adjacencyTable, "no");
        ClausetAlgorithm clausetPC = new ClausetAlgorithm(n, m, adjacencyTable, "yes");


        if("iskcore".equals(method_name)) {
            iskcore.initial("kcore");
        } else if("isComponent".equals(method_name)) {
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

        int number_of_core = nodes.size();
        int count = nodes.size();
        String algorithm_name = "(" + method_name + ").txt";
        double NMI_average = 0;
        double nodes_correctly_classified_percentage = 0;
        long costTime_average = 0;
        ArrayList<Double> rpf_average = new ArrayList<Double>();
        double r_average = 0;
        double p_average = 0;
        double f_average = 0;
        ArrayList<SeedAndCommunity> detected_communities = new ArrayList<SeedAndCommunity>();
        for (Iterator<Integer> iter_core = nodes.iterator(); iter_core
                .hasNext(); ) {
            Integer seed_id = iter_core.next();
            // for (int seed_id = 0; seed_id < this.n; seed_id++)
            // {
            count--;
            System.out.println(method_name + " left node sizes: " + count);
            System.out.println(method_name + " seed_id: " + seed_id);
            long startTime_once = 0;
            long endTime_once = 0;
            ArrayList<ArrayList<Integer>> partitionF = new ArrayList<ArrayList<Integer>>();
            ArrayList<ArrayList<ArrayList<Integer>>> partitionFS = new ArrayList<ArrayList<ArrayList<Integer>>>();
            ArrayList<ArrayList<Integer>> partitionR = get_real_partition_containing_seed(seed_id);
            int insection_size = 0;
            double NMI_temp = 0;
            double r = 0;
            double p = 0;
            double f = 0;
            startTime_once = System.currentTimeMillis();
            /************      add method      ************/
            if("lcd".equals(method_name)) {
                partitionFS = lcd.run_lcd(seed_id);
                endTime_once = System.currentTimeMillis();
                long costTime_once = endTime_once - startTime_once;
                costTime_average += costTime_once;
                double NMI_once_average = 0;
                double r_once_average = 0;
                double p_once_average = 0;
                double f_once_average = 0;
                for (Iterator<ArrayList<ArrayList<Integer>>> iter_partitionF = partitionFS
                        .iterator(); iter_partitionF.hasNext();)
                {
                    partitionF = iter_partitionF.next();
                    SeedAndCommunity seed_community = new SeedAndCommunity(
                            seed_id, partitionF.get(0));
                    detected_communities.add(seed_community);
                    NMI_temp = NMI.NMIPartitionUnoverLap(partitionF, partitionR,
                            this.n);
                    insection_size = get_insection_size(partitionF.get(0),
                            partitionR.get(0));
                    if (insection_size == 0)
                    {
                        r = 0;
                        p = 0;
                        f = 0;
                    } else
                    {
                        r = (double) insection_size
                                / (double) partitionR.get(0).size();
                        p = (double) insection_size
                                / (double) partitionF.get(0).size();
                        f = 2 * r * p / (r + p);
                    }
                    NMI_once_average += NMI_temp;
                    r_once_average += r;
                    p_once_average += p;
                    f_once_average += f;
                }
                NMI_once_average /= partitionFS.size();
                r_once_average /= partitionFS.size();
                p_once_average /= partitionFS.size();
                f_once_average /= partitionFS.size();
                nodes_correctly_classified_percentage++;
                NMI_average += NMI_once_average;
                r_average += r_once_average;
                p_average += p_once_average;
                f_average += f_once_average;
            } else {
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
                SeedAndCommunity seed_community = new SeedAndCommunity(
                        seed_id, partitionF.get(0));
                detected_communities.add(seed_community);
                endTime_once = System.currentTimeMillis();
                long costTime_once = endTime_once - startTime_once;
                costTime_average += costTime_once;
                if (partitionF.get(0).contains(seed_id)) {
                    nodes_correctly_classified_percentage++;
                }
                NMI_temp = NMI
                        .NMIPartitionUnoverLap(partitionF, partitionR, this.n);
                insection_size = get_insection_size(partitionF.get(0),
                        partitionR.get(0));
                if (insection_size == 0) {
                    r = 0;
                    p = 0;
                    f = 0;
                } else {
                    r = (double) insection_size / (double) partitionR.get(0).size();
                    p = (double) insection_size / (double) partitionF.get(0).size();
                    f = 2 * r * p / (r + p);
                }
                NMI_average += NMI_temp;
                r_average += r;
                p_average += p;
                f_average += f;
            }
        }
        costTime_average /= number_of_core;
        NMI_average /= number_of_core;
        nodes_correctly_classified_percentage /= (double) number_of_core;
        r_average /= number_of_core;
        p_average /= number_of_core;
        f_average /= number_of_core;
        rpf_average.add(r_average);
        rpf_average.add(p_average);
        rpf_average.add(f_average);
        write_results_into_files(algorithm_name, costTime_average,
                NMI_average, nodes_correctly_classified_percentage,
                rpf_average, detected_communities);
        Measurements measurements = new Measurements(algorithm_name,
                costTime_average, NMI_average,
                nodes_correctly_classified_percentage, rpf_average);

        return measurements;
    }

    public ArrayList<Measurements> run_methords_on_artificial_network_core_only(ArrayList<String> algoritym_names, String run_type )
            throws IOException
    {
        initial_paramaters();
        TreeSet<Integer> nodes = new TreeSet<>();
        if ("all_nodes".equals(run_type)) {
            int seed_size = this.n;
            for (int i = 0; i < seed_size; i++) {
                nodes.add(i);
            }
        } else {
            nodes = get_max_degree_nodes_from_real_communities();
        }
        ArrayList<Measurements> all_measurements = new ArrayList<>();
        for(String method_name : algoritym_names) {
            Measurements measurements = run_method_on_artificial_network_core_only(nodes, method_name);
            all_measurements.add(measurements);
        }
        return all_measurements;
    }

    public static void main(String[] args) throws IOException
    {
        String file_path_static = "F:/tests on artificial networks/1000/mu_pc/";
        //mu_pc;8;c_pc;8;k_pc;8;k_Orient;11;mu_Orient;8;c_Orient;5;
        String network = ".txt";//.txt/network.dat
        String community = ".clu";//.clu/community.dat
        int number_of_file = 1;
        int number_of_network = 1;
        String run_type = "all_nodes"; //core_node;all_nodes

        /************      add method      ************/
        ArrayList<String> algorithm_names = new ArrayList<>();
//        algorithm_names.add("isComponent");
//        algorithm_names.add("componentWithoutpc");
//
//        algorithm_names.add("lwp");
//        algorithm_names.add("chen");
//        algorithm_names.add("ls");
//

//
//        algorithm_names.add("vi");
//        algorithm_names.add("lcd");

//        algorithm_names.add("isdegreeCn");
//        algorithm_names.add("iswithoutpcCn");
//        algorithm_names.add("iskcore");

//        algorithm_names.add("pc");
//        algorithm_names.add("pcWithoutPc");

//        algorithm_names.add("fifth");
//        algorithm_names.add("fifthPC");

        algorithm_names.add("clauset");
//        algorithm_names.add("clausetPC");


        for (int i = 1; i <= number_of_file; i++)
        {
            String file_path = file_path_static + String.valueOf(i) + "/";
            ArrayList<ArrayList<Measurements>> measurements_on_all_data_sets = new ArrayList<>();

            for (int j = 1; j <= number_of_network; j++) {
                String file_name = String.valueOf(j);
                String file_path_network = file_path + file_name + network;
                String file_path_community = file_path + file_name + community;
                String file_path_results = file_path + file_name
                        + "_results.txt";
                System.out.println("file_path_network: " + file_path_network);
                System.out.println("file_path_community: "
                        + file_path_community);
                RunMethodsOnArtificialNetworkClass run_methords = new RunMethodsOnArtificialNetworkClass(
                        file_path_network, file_path_community,
                        file_path_results);
                ArrayList<Measurements> measurements_on_one_data_sets = new ArrayList<>();
                measurements_on_one_data_sets = run_methords.run_methords_on_artificial_network_core_only(algorithm_names, run_type);
                measurements_on_all_data_sets.add(measurements_on_one_data_sets);
            }
            write_average_results_into_files(file_path, measurements_on_all_data_sets, number_of_network, algorithm_names);
        }
    }
}
