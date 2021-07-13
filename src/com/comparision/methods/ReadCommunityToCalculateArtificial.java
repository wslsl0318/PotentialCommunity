package com.comparision.methods;

import DataReaderArtificialNetwork.DataReaderArtificialNetwork;
import DataReaderDetectedNetwork.DataReaderDetectedNetwork;
import com.pulicoperations.methods.NMI;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

public class ReadCommunityToCalculateArtificial {
    public DataReaderArtificialNetwork data_reader;
    public DataReaderDetectedNetwork detected_reader;
    public int n;// node number
    public int m;// link number
    public int nc;// community number;
    public ArrayList<ArrayList<Integer>> adjacencyTable;
    public ArrayList<ArrayList<Integer>> real_communities;
    public ArrayList<ArrayList<Integer>> detected_communities;
    public String file_path_result;
    public ArrayList<NodeDegree> nodes_degree;
    public int[] nodes_influence_list;
    public ReadCommunityToCalculateArtificial(String file_path_network,
                                              String file_path_community, String file_path_result)
    {
        this.data_reader = new DataReaderArtificialNetwork(file_path_network,
                file_path_community);
        this.detected_reader = new DataReaderDetectedNetwork(file_path_result);
        this.adjacencyTable = new ArrayList<ArrayList<Integer>>();
        this.real_communities = new ArrayList<ArrayList<Integer>>();
        this.detected_communities = new ArrayList<ArrayList<Integer>>();
        this.file_path_result = file_path_result;
        this.nodes_degree = new ArrayList<NodeDegree>();
    }

    /* (Tested) initial n, m, nc, adjacency table, real communities */
    public void initial_paramaters() throws IOException
    {
        this.data_reader.read_artificial_network();
        this.detected_reader.read_detected_communities();
        this.n = this.data_reader.n;
        this.m = this.data_reader.m;
        this.nc = this.data_reader.nc;
        this.adjacencyTable.addAll(this.data_reader.adjacencyTable);
        this.data_reader.adjacencyTable.clear();
        this.real_communities.addAll(this.data_reader.real_communities);
        this.data_reader.real_communities.clear();
        this.detected_communities.addAll(this.detected_reader.detected_communities);
        this.detected_reader.detected_communities.clear();
        this.nodes_degree = getNodeDegree();
        getNodeInfluenceList();
    }

    public ArrayList<NodeDegree> getNodeDegree() {
        ArrayList<NodeDegree> nodeDegrees = new ArrayList<>();
        TreeSet<Integer> degree_number = new TreeSet<>();
        for(int i=0;i<this.n;i++) {
            NodeDegree nd = new NodeDegree();
            nd.setId(i);
            int degree = this.adjacencyTable.get(i).size() - 1;
            nd.setDegree(degree);
            if(!degree_number.contains(degree)) {
                degree_number.add(degree);
            }
            nodeDegrees.add(nd);
        }
        NodeDegree ndlast = new NodeDegree();
        ndlast = nodeDegrees.get(this.n-1);
        ndlast.setNeighbors(degree_number);
        return nodeDegrees;
    }

    public void getNodeInfluenceList() {
        TreeSet<Integer> nodeInfluenceLists = new TreeSet<>();
        NodeDegree ndlast = new NodeDegree();
        ndlast = nodes_degree.get(this.n-1);
        TreeSet<Integer> degree_list = ndlast.getNeighbors();
        int degree_max = degree_list.last();
        this.nodes_influence_list = new int[degree_max+1];
        int size = degree_list.size();
        double range = Math.ceil((double)size/10);
        int range_left = (int) size % 10;

        int tag1 = 10;
        int tag2 = 1;
        int tag3 = 1;
        for(int degree : degree_list) {
            nodes_influence_list[degree] = tag1;
            if(tag1 > range_left && range_left != 0) {
                if(tag2 % (range-1) == 0) {
                    tag2 = 0;
                    tag1--;
                }
                tag2++;
            } else {
                if(tag3 % range == 0) {
                    tag3 = 0;
                    tag1--;
                }
                tag3++;
            }
        }

        /*if(range_left > 0 && range_left < 5) {
            int tag1 = 10;
            int tag2 = 1;
            int tag3 = 1;
            for(int degree : degree_list) {
                nodes_influence_list[degree] = tag1;
                if(tag2 <= 10 - range_left) {
                    if(tag2 % (range-1) == 0) {
                        tag1--;
                    }
                    tag2++;
                } else {
                    if(tag3 % range == 0) {
                        tag1--;
                    }
                    tag3++;
                }
            }
        } else {
            int front = 0;
            int back = 0;
            for (front = 0; front < size; front++) {
                back = size - front;
                if (back % range == 0 && (back / range + front == 10)) {
                    break;
                }
            }

            int tag1 = 10;
            int tag2 = 1;
            int tag3 = 1;
            for(int degree : degree_list) {
                nodes_influence_list[degree] = tag1;
                if(tag2 <= front) {
                    if(tag2 % (range-1) == 0) {
                        tag1--;
                    }
                    tag2++;
                } else {
                    if(tag3 % range == 0) {
                        tag1--;
                    }
                    tag3++;
                }
            }

        }*/
    }

    public void caculate_nmi_f() {
        int seed_size = 0;
        double NMI_average = 0;
        /* The percentage of nodes that are correctly classified */
        double nodes_correctly_classified_percentage = 0;
        long costTime_average = 0;
        /* r, p, f */
        ArrayList<Double> rpf_average = new ArrayList<Double>();
        double r_average = 0;
        double p_average = 0;
        double f_average = 0;
        for(Iterator<ArrayList<Integer>> detectedIter = detected_communities.iterator();detectedIter.hasNext();) {
            seed_size++;
            int community_size = detected_communities.size();
//            System.out.println(seed_size + " / " + community_size);
            ArrayList<Integer> community = new ArrayList<>();
            community.addAll(detectedIter.next());
            int seed_id = community.get(0);
            ArrayList<Integer> restnodes = new ArrayList<>();
            ArrayList<ArrayList<Integer>> partitionF = new ArrayList<>();
            ArrayList<ArrayList<ArrayList<Integer>>> partitionRS = get_real_partitions_containing_seed(seed_id);
            community.remove(0);
            for(int i = 0; i < n; i++) {
                if(!community.contains(i)) {
                    restnodes.add(i);
                }
            }
            partitionF.add(community);
            partitionF.add(restnodes);

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
        costTime_average /= seed_size;
        NMI_average /= seed_size;
        nodes_correctly_classified_percentage /= (double) seed_size;
        r_average /= seed_size;
        p_average /= seed_size;
        f_average /= seed_size;
        rpf_average.add(r_average);
        rpf_average.add(p_average);
        rpf_average.add(f_average);
        System.out.println("costTime_average:" + costTime_average);
        System.out.println("NMI_average:" + NMI_average);
        System.out.println("r_average:" + r_average);
        System.out.println("p_average:" + p_average);
        System.out.println("f_average:" + f_average);
        System.out.println("nodes_correctly_classified_percentage:" + nodes_correctly_classified_percentage);
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

    public ArrayList<Float> caculate_LCE() {
        ArrayList<Float> LCE_Valid = new ArrayList();
        int seed_size = 0;
        int[] LCE = new int[this.n];
        for(Iterator<ArrayList<Integer>> detectedIter = detected_communities.iterator();detectedIter.hasNext();) {
            seed_size++;
            int community_size = detected_communities.size();
//            System.out.println(seed_size + " / " + community_size);
            ArrayList<Integer> community = new ArrayList<>();
            community.addAll(detectedIter.next());
            int seed_id = community.get(0);
            community.remove(0);
            int size = community.size();
            int seed_position = 0;
            for(int i=0;i<size;i++) {
                if(seed_id == community.get(i)) {
                    seed_position = i;
                }
            }
            int[][] map = new int[size][size];
            for(int i=0;i<size;i++) {
                int nodeA = community.get(i);
                ArrayList<Integer> neighbors = this.adjacencyTable.get(nodeA);
                for(int j=0;j<size;j++) {
                    int nodeB = community.get(j);
                    if(nodeA == nodeB) {
                        map[i][j] = 0x3fffffff;
                    } else if(neighbors.contains(nodeB)) {
                        map[i][j] = 1;
                    } else {
                        map[i][j] = 0x3fffffff;
                    }
                }
            }
            for(int k=0;k<size;k++) {
                for(int i=0;i<size;i++) {
                    for(int j=0;j<size;j++) {
                        if (map[i][j]>map[i][k]+map[k][j]) {
                            int temp = map[i][j];
                            map[i][j]=map[i][k]+map[k][j];
                            if(map[i][j] > 0x3fffffff) {
                                map[i][j] = temp;
                            }
                        }
                    }
                }
            }

            int[] path_length = new int[size];
            for(int i=0;i<size;i++) {
                for(int j=0;j<size;j++) {
                    if(i != j) {
                        if(map[i][j] != 0x3fffffff) {
                            path_length[i] += map[i][j];
                        }
                    }
                }
            }
            for(int i=0;i<size;i++) {
                if(path_length[seed_position] > path_length[i]) {
                    LCE[seed_id] = 0;
                    /*int nodeA = community.get(seed_position);
                    int nodeB = community.get(i);
                    int degreeA = this.adjacencyTable.get(nodeA).size()-1;
                    int degreeB = this.adjacencyTable.get(nodeB).size()-1;
                    System.out.println("seed: "+nodeA+"degree= "+degreeA+" node: " + nodeB + "degree= "+degreeB);*/
                    break;
                }
                LCE[seed_id] = 1;
            }
        }
        double LCE_average = 0;
        for(int i:LCE) {
            LCE_average += i;
        }
        LCE_average /= seed_size;

        LCE_Valid.add((float)LCE_average);

//        System.out.println("LCE:" + LCE_average);

        String type = "Influence_sort"; //100 (100 per group); Influence_sort;
        if("100".equals(type)) {
            int valid_node = 0;
            int tag = 0;
            int tag2 = 1;
            for (int i : LCE) {
                valid_node += i;
                if (tag % 100 == 99) {
                    float ratio = 0;
                    ratio = (float) valid_node / 100;
                    LCE_Valid.add(ratio);
                    System.out.println(tag2 + ":  " + ratio);
                    valid_node = 0;
                    tag2++;
                }
                tag++;
            }
        } else if ("Influence_sort".equals(type)) {
            int[] invalidNodes = new int[10];
            int[] validNodes = new int[10];

            int j = 0;
            for (int i : LCE) {
                int degree = this.nodes_degree.get(j).getDegree();
                int influence_sort = nodes_influence_list[degree];
                if (i == 0) {
                    invalidNodes[influence_sort - 1] += 1;
                } else if (i == 1) {
                    validNodes[influence_sort - 1] += 1;
                }
                j++;
            }
            for (int i = 0; i < 10; i++) {
                float ratio = 0;
                float sum = validNodes[i] + invalidNodes[i];
                if (sum == 0) {
                    ratio = 0;
                } else {
                    ratio = (float) (validNodes[i] / sum);
                }
                LCE_Valid.add(ratio);
                System.out.println(i + ":   " + ratio);
            }
        }
        return LCE_Valid;
    }

    public double caculate_LCU() {
        int seed_size = 0;
        double LCU = this.n;
        ArrayList<ArrayList<Integer>> communities = new ArrayList<>();
        for(Iterator<ArrayList<Integer>> detectedIter = detected_communities.iterator();detectedIter.hasNext();) {
            seed_size++;
            int community_size = detected_communities.size();
//            System.out.println(seed_size + " / " + community_size);
            ArrayList<Integer> community = new ArrayList<>();
            community.addAll(detectedIter.next());
            int seed_id = community.get(0);
            community.remove(0);
            int size = community.size();
            communities.add(community);
        }
        ArrayList<Integer> invalid = new ArrayList();
        for(int i=0; i<this.n; i++) {
            for(int j=i+1; j<this.n; j++) {
                if(!invalid.contains(i) && !invalid.contains(j)) {
                    ArrayList<Integer> communityA = new ArrayList();
                    ArrayList<Integer> communityB = new ArrayList();
                    communityA.addAll(communities.get(i));
                    communityB.addAll(communities.get(j));
                    int sizeA = communityA.size();
                    int sizeB = communityB.size();


                    String type = "noncontain"; //1.|contain| A contains B is not allowed 2. |noncontain| else
                    String tag = "";

                    if ("contain".equals(type)) {
                        if (sizeA > sizeB) {
                            tag = "A";
                            for (int node : communityB) {
                                if (!communityA.contains(node)) {
                                    tag = "false";
                                    break;
                                }
                            }
                        } else {
                            tag = "B";
                            for (int node : communityA) {
                                if (!communityB.contains(node)) {
                                    tag = "false";
                                    break;
                                }
                            }
                        }
                        if ("A".equals(tag)) {
                            LCU -= 1;
                            invalid.add(j);
                        } else if ("B".equals(tag)) {
                            invalid.add(i);
                            LCU -= 1;
                            break;
                        }
                    } else if ("noncontain".equals(type)) {
                        if (sizeA == sizeB) {
                            tag = "true";
                            for (int node : communityB) {
                                if (!communityA.contains(node)) {
                                    tag = "false";
                                    break;
                                }
                            }
                        }
                        if ("true".equals(tag)) {
                            invalid.add(i);
                            LCU -= 1;
                            break;
                        }
                    }
                }
            }
        }
        LCU /= seed_size;

//        System.out.println("LCU:" + LCU);
        return LCU;
    }

    public static ArrayList<MethodResult> caculateAverageValue(ArrayList<MethodResult> list, int number_of_file,
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
                    tempAverage.setLCE(tempAverage.getLCE() + tempResult.getLCE());
                    tempAverage.setLCU(tempAverage.getLCU() + tempResult.getLCU());
                    float[] valid = new float[10];
                    for(int i=0; i<10; i++) {
                        valid[i] = tempAverage.getValid()[i] + tempResult.getValid()[i];
                    }
                    tempAverage.setValid(valid);
                }
            }
        }

        for(MethodResult tempAverage : averageList) {
            tempAverage.setLCE(tempAverage.getLCE() / number_of_network);
            tempAverage.setLCU(tempAverage.getLCU() / number_of_network);
            float[] valid = new float[10];
            for(int i=0; i<10; i++) {
                valid[i] = tempAverage.getValid()[i] / number_of_network;
            }
            tempAverage.setValid(valid);
        }

        return averageList;
    }

    public static ArrayList<String> caculateResults(ArrayList<MethodResult> list, String algorithm_name) {
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
        String LCE = "";
        String LCU = "";
        String Valid = "";
        int file_number = 0;
        for(MethodResult temp : list) {
            file_number++;
            LCE = LCE + " " + df.format(temp.getLCE());
            LCU = LCU + " " + df.format(temp.getLCU());
            Valid = Valid + file_number + ": ";
            for(int i=0; i<10; i++) {
                Valid = Valid + " " + df.format(temp.getValid()[i]);
            }
        }
        LCE = "LCE    " + algorithm_name + " = [" + LCE + " ]";
        LCU = "LCU    " + algorithm_name + " = [" + LCU + " ]";
        Valid = "Valid    " + algorithm_name + " = [" + Valid + " ]";
        resultlist.add(LCE);
        resultlist.add(LCU);
        resultlist.add(Valid);
        return resultlist;
    }

    public static void showResults(ArrayList<ArrayList<String>> resultLists) {
        String LCE = "";
        String LCU = "";
        String Valid = "";

        for(ArrayList<String> list : resultLists) {
            LCE = LCE + "\n" + list.get(0);
            LCU = LCU + "\n" + list.get(1);
            Valid = Valid + "\n" + list.get(2);
        }
        System.out.println(LCE);
        System.out.println(LCU);
        System.out.println(Valid);
    }

    public static void main(String[] args) throws IOException {
        String file_path_static = "F:/tests on artificial networks/1000/mu_Orient/";
        //mu;8;c;8;k;8;k_Orient;11;mu_Orient;8;c_Orient;5;
        int number_of_file = 8;
        int number_of_network = 10;
        String network = ".txt";//.txt/network.dat
        String community = ".clu";//.clu/community.dat
        ArrayList<String> algorithm_names = new ArrayList<>();
        //chen;clauset;fifth;iskcore;lcd;ls;lwp;vi;isdegree;
        //seedOrientDegree;seedOrientKcore
        algorithm_names.add("seedOrientDegree");
        algorithm_names.add("seedOrientKcore");
        algorithm_names.add("clauset");
        algorithm_names.add("lwp");
        algorithm_names.add("chen");
        algorithm_names.add("ls");
        algorithm_names.add("lcd");
        algorithm_names.add("vi");
        algorithm_names.add("fifth");

        String file_path_result = "";
        ArrayList<ArrayList<String>> resultLists = new ArrayList<>();
        for(String algorithm_name : algorithm_names) {
            ArrayList<MethodResult> RealResults = new ArrayList<>();
            for (int file_number = 1; file_number <= number_of_file; file_number++) {
                for (int network_number = 1; network_number <= number_of_network; network_number++) {
                    file_path_result = file_path_static + file_number + "/"
                            + network_number + "_results.txt(" + algorithm_name + ").txt";
                    String file_path_network = file_path_static + file_number + "/"
                            + network_number + network;
                    String file_path_community = file_path_static + file_number + "/"
                            + network_number + community;
                    ReadCommunityToCalculateArtificial run_methords = new ReadCommunityToCalculateArtificial(
                            file_path_network, file_path_community, file_path_result);
                    run_methords.initial_paramaters();
                    MethodResult RealResult = new MethodResult();
//                run_methords.caculate_nmi_f();

                    ArrayList<Float> LCE_Valid = new ArrayList();
                    LCE_Valid.addAll(run_methords.caculate_LCE());
                    double LCE = (double)LCE_Valid.get(0);
                    float[] valid = new float[10];
                    for(int i=1;i<LCE_Valid.size();i++) {
                        float valid_ratio = LCE_Valid.get(i);
                        valid[i-1] = valid_ratio;
                    }
                    RealResult.setLCE(LCE);
                    RealResult.setValid(valid);
                    RealResult.setLCU(run_methords.caculate_LCU());

                    RealResult.setNetwork_number(network_number);
                    RealResult.setFile_number(file_number);
                    RealResult.setMethod_name(algorithm_name);
                    RealResults.add(RealResult);

                    System.out.println("algorithm_name: " + algorithm_name);
                    System.out.println("number_of_file: " + file_number);
                    System.out.println("network_number: " + network_number);
                }
            }
            ArrayList<MethodResult> averageResultArrayList = new ArrayList<>();
            averageResultArrayList = caculateAverageValue(RealResults, number_of_file, number_of_network, algorithm_name);
            resultLists.add(caculateResults(averageResultArrayList, algorithm_name));
        }
        showResults(resultLists);
    }
}
