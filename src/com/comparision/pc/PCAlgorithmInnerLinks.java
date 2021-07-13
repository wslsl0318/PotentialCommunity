package com.comparision.pc;

import java.util.*;

/******    20201017 ֱ�������㷨��������Ǳ������Ϊ����    ******/
public class PCAlgorithmInnerLinks {
    public int node_size;
    public int link_size;
    public int numberOfCommunities;
    public ArrayList<ArrayList<Integer>> adjacencyTable;

    public String potential_community;
    public String core_nodes;
    public String similarity_type;

    public PCAlgorithmInnerLinks(int n, int m, ArrayList<ArrayList<Integer>> adjacencyTable, String potential_community) {
        this.node_size = n;
        this.link_size = m;
        this.numberOfCommunities = 0;
        this.adjacencyTable = adjacencyTable;
        this.potential_community = potential_community;
        this.similarity_type = ""; //
    }

    public ArrayList<ArrayList<Integer>> run_pc(int seed_id) {
        ArrayList<ArrayList<Integer>> partitionF = new ArrayList<>();
        TreeSet<Integer> detected_community = new TreeSet<>();
        TreeSet<Integer> rest_nodes = new TreeSet<>();

        seed_id = seeding(seed_id);

        detected_community = initial_community(seed_id);
        detected_community = extend_community(detected_community);

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

    public int seeding(int seed_id) {
        int candidate_seed = seed_id;
        do{
            seed_id = candidate_seed;
            TreeSet<Integer> node_neighbors = new TreeSet<>();
            node_neighbors = getNeighbors(candidate_seed,adjacencyTable);
            double similarity = 0;
            for(int neighbor_id : node_neighbors) {
                double degree_can = getDegree(candidate_seed);
                double degree_nei = getDegree(neighbor_id);
                if(degree_nei > degree_can) {
                    double similarity_temp = getNodeToNodeSimilarity(candidate_seed, neighbor_id);
                    if(similarity_temp > similarity) {
                        similarity = similarity_temp;
                        candidate_seed = neighbor_id;
                    }
                }
            }
        }
        while(candidate_seed != seed_id);
        return candidate_seed;
    }

    public TreeSet<Integer> initial_community(int seed_id) {
        TreeSet<Integer> initial_community = new TreeSet<>();
        TreeSet<Integer> node_neighbors = new TreeSet<>();
        ArrayList<TreeSet<Integer>> connetected_components = new ArrayList<>();
        node_neighbors = getNeighbors(seed_id,adjacencyTable);
        connetected_components = getConnectedComponents(node_neighbors);

        double communnity_similarity = 0;
        for(TreeSet<Integer> component : connetected_components) {
            double temp_similarity = getNodeCommunitySimilarity(seed_id, component);
            if(temp_similarity > communnity_similarity) {
                communnity_similarity = temp_similarity;
                initial_community.clear();
                initial_community.addAll(component);
            }
        }
        initial_community.add(seed_id);
        initial_community = communityCleanup(initial_community, seed_id);
        return initial_community;
    }

    public TreeSet<Integer> extend_community(TreeSet<Integer> detected_community) {
        TreeSet<Integer> temp_community = new TreeSet<>();
        TreeSet<Integer> community_neighbors = new TreeSet<>();
        Queue<Integer> suspicious_nodes = new LinkedList<>();
        temp_community.addAll(detected_community);
        community_neighbors = get_community_neighbors(temp_community);

        TreeSet<Integer> temp_list = new TreeSet<>();
        temp_list.addAll(community_neighbors);
        TreeSet<Integer> new_members = new TreeSet<>();
        do {
            suspicious_nodes.addAll(temp_list);
            temp_list.clear();
            new_members.clear();
            while(!suspicious_nodes.isEmpty()) {
                int node_id = suspicious_nodes.poll();
                boolean contain = isContained(node_id, temp_community, "extend");
                if (contain) {
                    temp_community.add(node_id);
                    new_members.add(node_id);
                    TreeSet<Integer> neighbor_suspicious_nodes = new TreeSet<>();
                    neighbor_suspicious_nodes = getNeighbors(node_id, adjacencyTable);
                    neighbor_suspicious_nodes.removeAll(get_insection(neighbor_suspicious_nodes, temp_community));
                    temp_list.addAll(neighbor_suspicious_nodes);
                } else {
                    temp_list.add(node_id);
                }
            }
        } while (!new_members.isEmpty());

        return temp_community;
    }

    ArrayList<TreeSet<Integer>> getConnectedComponents(TreeSet<Integer> nodes) {
        ArrayList<TreeSet<Integer>> connected_components = new ArrayList<>();
        TreeSet<Integer> nodes_list = new TreeSet<>();
        nodes_list.addAll(nodes);
        while(!nodes_list.isEmpty()) {
            TreeSet<Integer> component = new TreeSet<>();
            TreeSet<Integer> queue = new TreeSet<Integer>();
            queue.add(nodes_list.pollFirst());
            while (!queue.isEmpty())
            {
                int node_neighbor_id = queue.pollFirst();
                component.add(node_neighbor_id);
                TreeSet<Integer> node_neighbor_neighbors = new TreeSet<>();
                node_neighbor_neighbors.addAll(getNeighbors(node_neighbor_id, adjacencyTable));
                TreeSet<Integer> insection = get_insection(node_neighbor_neighbors, nodes_list);
                queue.addAll(insection);
                nodes_list.removeAll(insection);
            }

            TreeSet<Integer> component_temp = new TreeSet<>();
            component_temp.addAll(component_cleanup(component));

            connected_components.add(component_temp);
        }
        return connected_components;
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

    public double getNodeCommunitySimilarity(int node, TreeSet<Integer> community) {
        double similarity = 0;

        //�ýڵ���ڽڵ��������ڵ㼯�ϵĽ�������
        /*TreeSet<Integer> node_neighbors = new TreeSet<>();
        TreeSet<Integer> nodeCommunityCommon = new TreeSet<>();
        node_neighbors = getNeighbors(node, adjacencyTable);
        nodeCommunityCommon = get_insection(community, node_neighbors);
        for(int neighbor_id : nodeCommunityCommon) {
            double degree = getDegree(neighbor_id);
            similarity += degree;
        }*/


        //�ڵ������������ڵ�Ľ����ڲ�����
        TreeSet<Integer> node_neighbors = new TreeSet<>();
        TreeSet<Integer> nodeCommunityCommon = new TreeSet<>();
        node_neighbors = getNeighbors(node, adjacencyTable);
        nodeCommunityCommon = get_insection(community, node_neighbors);
        nodeCommunityCommon.add(node);
        while(!nodeCommunityCommon.isEmpty()) {
            int node_id = nodeCommunityCommon.pollFirst();
            TreeSet<Integer> node_id_neighbors = new TreeSet<>();
            node_id_neighbors = getNeighbors(node_id, adjacencyTable);
            for(int neighbor : node_id_neighbors) {
                if(nodeCommunityCommon.contains(neighbor)) {
                    similarity += 1;
                }
            }
        }

        //�ڵ������������ڵ�Ľ����ڲ����ӱ���
        /*TreeSet<Integer> node_neighbors = new TreeSet<>();
        TreeSet<Integer> nodeCommunityCommon = new TreeSet<>();
        node_neighbors = getNeighbors(node, adjacencyTable);
        nodeCommunityCommon = get_insection(community, node_neighbors);
        nodeCommunityCommon.add(node);
        int edge_number = 0;
        int node_number = nodeCommunityCommon.size();
        while(!nodeCommunityCommon.isEmpty()) {
            int node_id = nodeCommunityCommon.pollFirst();
            TreeSet<Integer> node_id_neighbors = new TreeSet<>();
            node_id_neighbors = getNeighbors(node_id, adjacencyTable);
            for(int neighbor : node_id_neighbors) {
                if(nodeCommunityCommon.contains(neighbor)) {
                    edge_number += 1;
                }
            }
        }
        similarity = (double)edge_number / (double)node_number;*/

        //TPR ���Ǳ�������
        /*TreeSet<Integer> node_neighbors = new TreeSet<>();
        TreeSet<Integer> nodeCommunityCommon = new TreeSet<>();
        node_neighbors = getNeighbors(node, adjacencyTable);
        nodeCommunityCommon = get_insection(community, node_neighbors);
        int node_number = nodeCommunityCommon.size() + 1;
        while(!nodeCommunityCommon.isEmpty()) {
            int node_id = nodeCommunityCommon.pollFirst();
            TreeSet<Integer> node_id_neighbors = new TreeSet<>();
            node_id_neighbors = getNeighbors(node_id, adjacencyTable);
            for(int node_neighbor_id : node_id_neighbors) {
                if(nodeCommunityCommon.contains(node_neighbor_id)) {
                    similarity++;
                }
            }
        }
        similarity = similarity / node_number;*/

        //�ڵ������������ڵ�Ľ����ڲ�������������߱���
        /*TreeSet<Integer> node_neighbors = new TreeSet<>();
        TreeSet<Integer> nodeCommunityCommon = new TreeSet<>();
        node_neighbors = getNeighbors(node, adjacencyTable);
        nodeCommunityCommon = get_insection(community, node_neighbors);
        nodeCommunityCommon.add(node);
        int edge_number = 0;
        int node_number = nodeCommunityCommon.size();
        while(!nodeCommunityCommon.isEmpty()) {
            int node_id = nodeCommunityCommon.pollFirst();
            TreeSet<Integer> node_id_neighbors = new TreeSet<>();
            node_id_neighbors = getNeighbors(node_id, adjacencyTable);
            for(int neighbor : node_id_neighbors) {
                if(nodeCommunityCommon.contains(neighbor)) {
                    edge_number += 1;
                }
            }
        }
        similarity = (double)edge_number / (double)( node_number * (node_number - 1) / 2);*/

        //MAXClique




        return similarity;
    }

    public double getNodeToNodeSimilarity(int nodeA, int nodeB) {
        double similarity = 0;
        //1.Jaccard Index
        TreeSet<Integer> node_neighborsA = new TreeSet<>();
        TreeSet<Integer> node_neighborsB = new TreeSet<>();
        TreeSet<Integer> common_neighbors = new TreeSet<>();
        TreeSet<Integer> unionOfAB = new TreeSet<>();
        node_neighborsA = getNeighbors(nodeA, adjacencyTable);
        node_neighborsB = getNeighbors(nodeB, adjacencyTable);
        node_neighborsA.add(nodeA);
        node_neighborsB.add(nodeB);
        common_neighbors = get_insection(node_neighborsA, node_neighborsB);
        unionOfAB.addAll(node_neighborsA);
        unionOfAB.addAll(node_neighborsB);
        similarity = (double)common_neighbors.size() / (double)unionOfAB.size();

        //1.Salton Index(Cosine similarity)
        /*TreeSet<Integer> node_neighborsA = new TreeSet<>();
        TreeSet<Integer> node_neighborsB = new TreeSet<>();
        TreeSet<Integer> common_neighbors = new TreeSet<>();
        node_neighborsA = getNeighbors(nodeA, adjacencyTable);
        node_neighborsB = getNeighbors(nodeB, adjacencyTable);
        common_neighbors = get_insection(node_neighborsA, node_neighborsB);
        similarity = (double)common_neighbors.size() / java.lang.Math.sqrt((double)(node_neighborsA.size() * node_neighborsB.size()));*/

        //�����ö�
//        similarity = getDegree(nodeA);
        return similarity;
    }


    public TreeSet<Integer> component_cleanup(TreeSet<Integer> community) {
        Queue<Integer> community_queue = new LinkedList<>();
        TreeSet<Integer> community_nodes = new TreeSet<>();
        TreeSet<Integer> temp_list = new TreeSet<>();
        community_nodes.addAll(community);

        do {
            temp_list.clear();
            community_queue.addAll(community_nodes);
            while(!community_queue.isEmpty()) {
                int node_id = community_queue.poll();
                community_nodes.remove((Integer)node_id);
                boolean contain = isContained(node_id, community_nodes, "component");
                if (contain) {
                    community_nodes.add(node_id);
                } else {
                    temp_list.add(node_id);
                }
            }
        } while(!temp_list.isEmpty());

        return community_nodes;
    }
    public TreeSet<Integer> communityCleanup(TreeSet<Integer> community, int seed_id) {
        Queue<Integer> community_queue = new LinkedList<>();
        TreeSet<Integer> community_nodes = new TreeSet<>();
        TreeSet<Integer> temp_list = new TreeSet<>();
        community_nodes.addAll(community);

        do {
            temp_list.clear();
            community_queue.addAll(community_nodes);
            community_queue.remove((Integer)seed_id);
            while(!community_queue.isEmpty()) {
                int node_id = community_queue.poll();
                community_nodes.remove((Integer)node_id);
                boolean contain = isContained(node_id, community_nodes, "initial");
                if (contain) {
                    community_nodes.add(node_id);
                } else {
                    temp_list.add(node_id);
                }
            }
        } while(!temp_list.isEmpty());

        return community_nodes;
    }

    public boolean isContained(int node_id, TreeSet<Integer> detected_community, String type) {
        boolean isContained = true;
        TreeSet<Integer> neighbors = getNeighbors(node_id, adjacencyTable);
        TreeSet<Integer> neighbors_in = new TreeSet<>();
        TreeSet<Integer> neighbors_out = new TreeSet<>();
        double neighbors_conectivity_in = 0;
        double neighbors_conectivity_out = 0;
        TreeSet<Integer> connectivity_out = new TreeSet();
        for(int neighbor : neighbors) {
            if(detected_community.contains(neighbor)) {
                neighbors_in.add(neighbor);
            } else {
                neighbors_out.add(neighbor);
            }
        }

        neighbors_conectivity_in = getNodeCommunitySimilarity(node_id, neighbors_in);

        if("yes".equals(this.potential_community) && "extend".equals(type)) {
            /******    20200110 modify    ******/
            ArrayList<TreeSet<Integer>> connected_components = new ArrayList<>();
            connected_components = getConnectedComponents(neighbors_out);
            for(TreeSet<Integer> tempList : connected_components) {
                double temp_out_influences = 0;
                temp_out_influences = getNodeCommunitySimilarity(node_id, tempList);
                if(temp_out_influences > neighbors_conectivity_out) {
                    neighbors_conectivity_out = temp_out_influences;
                }
            }
        } else {
            neighbors_conectivity_out = getNodeCommunitySimilarity(node_id, neighbors_out);
        }


        if(neighbors_conectivity_in < neighbors_conectivity_out) {
            isContained = false;
        }
        return isContained;
    }

    public TreeSet<Integer> getNeighbors(int id, ArrayList<ArrayList<Integer>> adjacencyTables) {
        TreeSet<Integer> neighbours = new TreeSet<>();
        neighbours.addAll(adjacencyTables.get(id));
        neighbours.remove((Integer)id);
        return neighbours;
    }

    public TreeSet<Integer> get_community_neighbors(TreeSet<Integer> detected_community) {
        TreeSet<Integer> community_neighbors = new TreeSet<>();
        for(int node : detected_community) {
            TreeSet<Integer> neighbors = getNeighbors(node, adjacencyTable);
            for(int neighbor : neighbors) {
                if(!detected_community.contains(neighbor)) {
                    community_neighbors.add(neighbor);
                }
            }
        }
        return community_neighbors;
    }

    public double getDegree(int nodeA) {
        double degree = 0;
        TreeSet<Integer> neighbors = getNeighbors(nodeA, adjacencyTable);
        degree = (double)neighbors.size();
        return degree;
    }

}