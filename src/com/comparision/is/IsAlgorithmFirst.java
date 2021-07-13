package com.comparision.is;

import java.util.*;

/***    20190515 recall is high enough, but prescise is far lower than expection.    ***/
public class IsAlgorithmFirst {
    public int node_size;
    public int link_size;
    public int numberOfCommunities;
    public ArrayList<ArrayList<Integer>> adjacencyTable;
    public NodeInfluence ni = new NodeInfluence();
    public TreeSet<NodeInfluence> node_influences;
    public Queue<Integer> c_must_queue;

    public IsAlgorithmFirst(int n, int m, String type, ArrayList<ArrayList<Integer>> adjacencyTable) {
        this.node_size = n;
        this.link_size = m;
        this.numberOfCommunities = 0;
        this.adjacencyTable = adjacencyTable;
        this.c_must_queue = new LinkedList<>();
        this.node_influences = ni.getNodeInfluence(type, adjacencyTable);
    }

    public ArrayList<ArrayList<Integer>> run_is(int seed_id) {
        ArrayList<ArrayList<Integer>> partitionF = new ArrayList<>();
        TreeSet<Integer> detected_community = new TreeSet<>();
        TreeSet<Integer> rest_nodes = new TreeSet<>();
        TreeSet<Integer> cMay = new TreeSet<>();
        TreeSet<Integer> cMust = new TreeSet<>();
        ArrayList<NodeInfluence> node_influences_id = new ArrayList<>();
        node_influences_id.addAll(node_influences);
        Collections.sort(node_influences_id, new Comparator<NodeInfluence>() {
            public int compare(NodeInfluence o1, NodeInfluence o2) {
                return o1.id - o2.id;
            }
        });
        c_must_queue.offer(seed_id);

        //20210429 修改算法
        TreeSet<Integer> seed_neighbors = new TreeSet<>();
        seed_neighbors = getNeighbors(seed_id,adjacencyTable);
        for(int seed_neighbor : seed_neighbors) {
            int seed_neighbor_influence = node_influences_id.get(seed_neighbor).influence;
            int seed_influence = node_influences_id.get(seed_id).influence;
            if(seed_neighbor_influence >= seed_influence) {
                cMay.add(seed_neighbor);
            }
            /*else {
                cMust.add(seed_neighbor);
                c_must_queue.offer(seed_neighbor);
            }*/
        }

        while(!c_must_queue.isEmpty()) {
            int first_node = c_must_queue.peek();
            TreeSet<Integer> first_node_neighbors = new TreeSet<>();
            first_node_neighbors = getNeighbors(first_node, adjacencyTable);
            int first_influence = node_influences_id.get(first_node).influence;
            for(int first_node_neighbor : first_node_neighbors) {

                if(first_node_neighbor == 9) {
                    int awer = 1;
                }
                if(first_node_neighbor == 25) {
                    int awer = 1;
                }
                if(first_node_neighbor == 27) {
                    int awer = 1;
                }

                String flag = "";
                int first_node_neighbor_influence = node_influences_id.get(first_node_neighbor).influence;
                if(first_node_neighbor_influence < first_influence && !cMust.contains(first_node_neighbor)) {
                    flag = "True";
                    TreeSet<Integer> first_node_neighbor_neighbor = new TreeSet<>();
                    first_node_neighbor_neighbor = getNeighbors(first_node_neighbor, adjacencyTable);
                    TreeSet<Integer> common = new TreeSet<>();
                    common.addAll(cMust);
                    common.addAll(c_must_queue);
                    common = get_insection(first_node_neighbor_neighbor, common);
                    for(int node : common) {
                        int node_influence = node_influences_id.get(node).influence;
                        if(node_influence < first_node_neighbor_influence) {
                            flag = "False";
                        }
                    }
                    if("True".equals(flag)) {
                        c_must_queue.add(first_node_neighbor);
                    }
                }
            }
            cMust.add(first_node);
            c_must_queue.remove(first_node);
        }



/*
        *//*** 20190605 test improve local community extend so far ***//*
//        detected_community.add(seed_id);
        *//*** 20190605 test improve local community extend so far ***//*
        int tag = 0;
        while(!c_must_queue.isEmpty()) {
//            int first = c_must_queue.peek();  //返回队列头部的元素             如果队列为空，则返回null
            int first = c_must_queue.poll();    //移除并返问队列头部的元素    如果队列为空，则返回null
            TreeSet<Integer> neighbours = getNeighbors(first, adjacencyTable);
            int first_influence = node_influences_id.get(first).influence;
            for(int neighbor_id : neighbours) {
                int neighbor_influence = node_influences_id.get(neighbor_id).influence;

                *//*** 20190605 test improve local community extend so far ***//*
                *//*double average_neighbor_influences_in_community = getNeighborInfluences(neighbor_id,"in" ,adjacencyTable,
                        node_influences_id, detected_community);
                double average_neighbor_influences_out_community = getNeighborInfluences(neighbor_id,"out" ,adjacencyTable,
                        node_influences_id, detected_community);*//*
                *//*** 20190605 test improve local community extend so far ***//*

//                if(neighbor_influence < first_influence && average_neighbor_influences_in_community > average_neighbor_influences_out_community) {
                String tag_must = "true";
                if(neighbor_influence < first_influence) {
                    if(cMust.add(neighbor_id)) {
                        TreeSet<Integer> must_neighbours = getNeighbors(neighbor_id, adjacencyTable);
                        TreeSet<Integer> common = get_insection(must_neighbours, cMust);
                        for(int must_neighbor : common) {
                            int must_neighbor_influence = node_influences_id.get(must_neighbor).influence;
                            if(must_neighbor_influence < neighbor_influence) {
                                tag_must = "flase";
                                break;
                            }
                        }
                        if("true".equals(tag_must)) {
                            c_must_queue.offer(neighbor_id);
                            detected_community.add(neighbor_id);
                        }
                    }
                } else if(tag == 0){
                    cMay.add(neighbor_id);
                    *//***    test    (how to improve seed`s community whitch influence is lowest)   ***//*
//                    c_must_queue.offer(neighbor_id);
                    *//***    test    (how to improve seed`s community whitch influence is lowest)   ***//*
                }
            }
            tag++;
        }*/


        detected_community.add(seed_id);
        detected_community.addAll(cMay);
        detected_community.addAll(cMust);


        for (int i = 0; i < node_size; i++) {
            if (!detected_community.contains(i)) {
                rest_nodes.add(i);
            }
        }
        ArrayList<Integer> temp_community = new ArrayList<>();
        ArrayList<Integer> temp_rest_nodes = new ArrayList<>();
        temp_community.addAll(detected_community);
        temp_rest_nodes.addAll(rest_nodes);
        partitionF.add(temp_community);
        partitionF.add(temp_rest_nodes);
        return partitionF;
    }

    public TreeSet<Integer> get_insection(TreeSet<Integer> setA,
                                          TreeSet<Integer> setB)
    {
        TreeSet<Integer> insection = new TreeSet<>();
        for (int node : setA) {
            if (setB.contains(node))
                insection.add(node);
        }
        return insection;
    }

    public TreeSet<Integer> getNeighbors(int id, ArrayList<ArrayList<Integer>> adjacencyTables) {
        TreeSet<Integer> neighbours = new TreeSet<>();
        neighbours.addAll(adjacencyTables.get(id));
        neighbours.remove((Integer)id);
        return neighbours;
    }

    /*** 20190605 test improve local community extend so far ***/
    public double getNeighborInfluences(int id, String type, ArrayList<ArrayList<Integer>> adjacencyTable,
                                                  ArrayList<NodeInfluence> node_influences, TreeSet<Integer> detected_community) {
        double average_influence = 0;
        TreeSet<Integer> neighbors = getNeighbors(id, adjacencyTable);
        int node_numbers = 0;
        for (int temp_id : neighbors) {
            if (detected_community.contains(temp_id) && "in".equals(type)) {
                int tempInfluence = node_influences.get(temp_id).influence;
                average_influence += tempInfluence;
                node_numbers++;
            } else if(!detected_community.contains(temp_id) && "out".equals(type)) {
                int tempInfluence = node_influences.get(temp_id).influence;
                average_influence += tempInfluence;
                node_numbers++;
            }
        }
        if(node_numbers != 0) {
            average_influence = average_influence / node_numbers;
        }
        return average_influence;
    }
}
