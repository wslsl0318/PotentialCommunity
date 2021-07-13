package com.comparision.is;

import java.util.*;

/***    20190610 detect the seed node with its neighbors    ***/
public class IsAlgorithmForth {
    public int node_size;
    public int link_size;
    public int numberOfCommunities;
    public ArrayList<ArrayList<Integer>> adjacencyTable;
    public NodeInfluence ni = new NodeInfluence();
    public TreeSet<NodeInfluence> node_influences;
    public Queue<Integer> c_must_queue;

    public IsAlgorithmForth(int n, int m, String type, ArrayList<ArrayList<Integer>> adjacencyTable) {
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

        detected_community = initial_community(seed_id, node_influences_id);
        TreeSet<Integer> temp1_detected_community = new TreeSet<>();
        temp1_detected_community.addAll(detected_community);
        TreeSet<Integer> temp2_detected_community = new TreeSet<>();
        temp2_detected_community.addAll(detected_community);
        TreeSet<Integer> shell = new TreeSet<>();
        shell.addAll(getShell(detected_community));
        int tag_shell = 0;
        while(tag_shell == 0 || !isSame(temp1_detected_community, detected_community)) {
            detected_community.clear();
            detected_community.addAll(temp1_detected_community);
            int tag_neighbor = 0;
            TreeSet<Integer> shell_neighbors = new TreeSet<>();
            for(int shell_id : shell) {
                TreeSet<Integer> neighbors = getNeighbors(shell_id, adjacencyTable);
                shell_neighbors.addAll(neighbors);
            }
            shell_neighbors.removeAll(detected_community);
            while(tag_neighbor == 0 || !isSame(temp2_detected_community, temp1_detected_community)) {
                temp1_detected_community.clear();
                temp1_detected_community.addAll(temp2_detected_community);
                for(int neighbor_id : shell_neighbors) {
                    boolean contain = isContained(neighbor_id, temp2_detected_community, node_influences_id);
                    if(contain) {
                        temp2_detected_community.add(neighbor_id);
                    }
                }
                tag_neighbor++;
            }
            shell.clear();
            shell.addAll(getShell(temp1_detected_community));
            tag_shell++;
        }


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

    public TreeSet<Integer> initial_community(int seed_id, ArrayList<NodeInfluence> node_influences_id) {
        TreeSet<Integer> detected_community = new TreeSet<>();
        TreeSet<Integer> temp_detected_community = new TreeSet<>();
        TreeSet<Integer> neighbors = getNeighbors(seed_id, adjacencyTable);
        detected_community.addAll(neighbors);
        temp_detected_community.addAll(neighbors);
        detected_community.add(seed_id);
        temp_detected_community.add(seed_id);
        int tag = 0;
        while(tag == 0 || !isSame(temp_detected_community, detected_community)) {
            detected_community.clear();
            detected_community.addAll(temp_detected_community);
            for(int node_id : neighbors) {
                temp_detected_community.remove((Integer) node_id);
                boolean contain = isContained(node_id, temp_detected_community, node_influences_id);
                if(contain) {
                    temp_detected_community.add(node_id);
                }
            }
            tag++;
        }
        return temp_detected_community;
    }

    public boolean isSame(TreeSet<Integer> tree1, TreeSet<Integer> tree2) {
        boolean isSame = true;
        if(tree1.size() != tree2.size()) {
            return isSame = false;
        }
        for(int node : tree1) {
            if(!tree2.contains(node)) {
                isSame = false;
                break;
            }
        }
        return isSame;
    }

    public boolean isContained(int node_id, TreeSet<Integer> detected_community, ArrayList<NodeInfluence> node_influences_id) {
        boolean isContained = true;
        TreeSet<Integer> neighbors = getNeighbors(node_id, adjacencyTable);
        TreeSet<Integer> neighbors_in = new TreeSet<>();
        TreeSet<Integer> neighbors_out = new TreeSet<>();
        int influences_in = 0;
        int influences_out = 0;
        for(int neighbor : neighbors) {
            if(detected_community.contains(neighbor)) {
                neighbors_in.add(neighbor);
                influences_in += node_influences_id.get(neighbor).influence;
            } else {
                neighbors_out.add(neighbor);
                influences_out += node_influences_id.get(neighbor).influence;
            }
        }
        if(influences_in < influences_out) {
            isContained = false;
        }
        return isContained;
    }

    public TreeSet<Integer> getShell(TreeSet<Integer> detected_community) {
        TreeSet<Integer> shell = new TreeSet<>();
        for(int node : detected_community) {
            TreeSet<Integer> neighbors = getNeighbors(node, adjacencyTable);
            for(int neighbor : neighbors) {
                if(!detected_community.contains(neighbor)) {
                    shell.add(node);
                    break;
                }
            }
        }
        return shell;
    }
}
