package com.comparision.is;

import java.util.*;

public class IsAlgorithmThird {
    public int node_size;
    public int link_size;
    public int numberOfCommunities;
    public ArrayList<ArrayList<Integer>> adjacencyTable;
    public NodeInfluence ni = new NodeInfluence();
    public TreeSet<NodeInfluence> node_influences;
    public Queue<Integer> c_must_queue;

    public IsAlgorithmThird(int n, int m, String type, ArrayList<ArrayList<Integer>> adjacencyTable) {
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
        /************    get more influencial node of seed    ************/
        int temp_node_id = -2;
        while(temp_node_id != -1) {
            temp_node_id = get_more_influence_node(seed_id,adjacencyTable, node_influences_id);
            if(temp_node_id != -1) {
                seed_id = temp_node_id;
            }
        }
        c_must_queue.offer(seed_id);
        while(!c_must_queue.isEmpty()) {
//            int first = c_must_queue.peek();  //返回队列头部的元素             如果队列为空，则返回null
            int first = c_must_queue.poll();    //移除并返问队列头部的元素    如果队列为空，则返回null
            TreeSet<Integer> neighbours = getNeighbors(first, adjacencyTable);
            int first_influence = node_influences_id.get(first).influence;
            for(int neighbor_id : neighbours) {
                int neighbor_influence = node_influences_id.get(neighbor_id).influence;
                if(neighbor_influence < first_influence) {
                    if(cMust.add(neighbor_id)) {
                        c_must_queue.offer(neighbor_id);
                        detected_community.add(neighbor_id);
                    }
                }
            }
        }
        detected_community.add(seed_id);
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

    /***  20190609 get more influencial node of seed  ***/
    public int get_more_influence_node(int seed_id, ArrayList<ArrayList<Integer>> adjacencyTables,
                                       ArrayList<NodeInfluence> node_influences) {
        int more_influence_node = -1;
        TreeSet<Integer> neighbors = getNeighbors(seed_id, adjacencyTables);
        TreeSet<NodeInfluence> neighborsInfluence = new TreeSet<>(new NodeInfluence());
        for(int node : neighbors) {
            neighborsInfluence.add(node_influences.get(node));
        }
        if(node_influences.get(seed_id).influence < node_influences.get(neighborsInfluence.first().id).influence) {
            more_influence_node = neighborsInfluence.first().id;
        }
        return more_influence_node;
    }
}
