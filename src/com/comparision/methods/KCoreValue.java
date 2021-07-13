package com.comparision.methods;

import java.util.*;

public class KCoreValue implements Comparator<KCoreValue> {
    public int id;
    public int value;

    public KCoreValue() {

    }

    public KCoreValue(int id, int value) {
        this.id = id;
        this.value = value;
    }

    public KCoreValue(int value) {
        this.value = value;
    }

    @Override
    public String toString()
    {
        return "KCoreValue [id=" + id + ", value=" + value + "]";
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + value;
        result = prime * result + id;
        return result;
    }


    @Override
    public int compare(KCoreValue o1, KCoreValue o2) {
        KCoreValue node1 = o1;
        KCoreValue node2 = o2;
        if(node1.value > node2.value) {
            return -1;
        } else if(node1.value == node2.value) {
            if(node1.id < node2.id) {
                return -1;
            } else if(node1.id == node2.id) {
                return 0;
            } else if(node1.id > node2.id) {
                return 1;
            }
        } else if(node1.value < node2.value) {
            return 1;
        }
        return 0;
    }

    public TreeSet<KCoreValue> getKCoreValue(ArrayList<ArrayList<Integer>> adjacencyTable) {
        TreeSet<KCoreValue> nodes_KCore_values = new TreeSet<KCoreValue>(new KCoreValue());
        TreeSet<NodeDegree> nodes_core_values = getNodeCoreValue(adjacencyTable);
        ArrayList<NodeDegree> nodes_core_values_list = new ArrayList<>();
        nodes_core_values_list.addAll(nodes_core_values);
        Collections.sort(nodes_core_values_list, new Comparator<NodeDegree>() {
            @Override
            public int compare(NodeDegree o1, NodeDegree o2) {
                return o1.id - o2.id;
            }
        });
        for(NodeDegree node_core_value : nodes_core_values_list) {
            int KCore_value = 0;
            TreeSet<Integer> neighbors = node_core_value.neighbors;
            /***    node_kcore_value  ***/
            for(int neighbor_id : neighbors) {
                KCore_value += nodes_core_values_list.get(neighbor_id).degree;
            }
            KCore_value += node_core_value.degree;
            KCoreValue kCore_value = new KCoreValue(node_core_value.id, KCore_value);
            nodes_KCore_values.add(kCore_value);
        }
        return nodes_KCore_values;
    }

    public TreeSet<NodeDegree> getNodeCoreValue(ArrayList<ArrayList<Integer>> adjacencyTable) {
        TreeSet<NodeDegree> nodes_core_values = new TreeSet<NodeDegree>(new NodeDegree());
        TreeSet<Integer> nodes = new TreeSet<>();
        for (int i = 0; i < adjacencyTable.size(); i++) {
            nodes.add(i);
        }
        /* Get the local degree of the nodes of the component (Increase order) */
        NodeDegree nodeDegree = new NodeDegree();
        TreeSet<NodeDegree> nodes_degrees = nodeDegree.getNodeDegrees(adjacencyTable);
        int current_core_value = nodes_degrees.first().degree;
        while(!nodes_degrees.isEmpty()) {
            /* add KCoreValue*/
            NodeDegree nd = nodes_degrees.pollFirst();
            if(nd.degree > current_core_value) {
                current_core_value = nd.degree;
            }
            NodeDegree node_core_value = new NodeDegree(nd.id, current_core_value, nd.neighbors);
            nodes_core_values.add(node_core_value);
            nodes.remove((Integer)nd.id);
            /* update degree of node */
            TreeSet<Integer> node_neighbors = nd.neighbors;
            TreeSet<Integer> node_neighbor_rest = get_insection(node_neighbors, nodes);
            for(int node_neighbor_id : node_neighbor_rest) {
                NodeDegree node_neighbor_degree = new NodeDegree();
                NodeDegree node_neighbor_degree_new = new NodeDegree();
                node_neighbor_degree = node_neighbor_degree.getNodeDegreeById(node_neighbor_id, nodes_degrees);
                node_neighbor_degree_new.id = node_neighbor_degree.id;
                node_neighbor_degree_new.degree = node_neighbor_degree.degree - 1;
                node_neighbor_degree_new.neighbors.addAll(node_neighbor_degree.neighbors);
                nodes_degrees.remove(node_neighbor_degree);
                nodes_degrees.add(node_neighbor_degree_new);
                /* update done_adjacency_table */
            }
        }
        return nodes_core_values;
    }

//    public TreeSet<KCoreValue> get_component_nodes_local_core_value(TreeSet<Integer> component, ArrayList<ArrayList<Integer>> adjacencyTable)
//    {
//        TreeSet<Integer> temp_component = new TreeSet<Integer>();
//        temp_component.addAll(component);
//        TreeSet<KCoreValue> nodes_local_core_value = new TreeSet<KCoreValue>();
//        NodeDegree nd = new NodeDegree();
//         /* Get the local degree of the nodes of the component (Increase order) */
//        ArrayList<NodeDegree> temp_degree = nd.getNodeDegrees(adjacencyTable);
//        TreeSet<NodeDegree> nodes_local_degree = new TreeSet<>();
//        nodes_local_degree.addAll(temp_degree);
//        int current_core_value = nodes_local_degree.first().degree;
//        while (!nodes_local_degree.isEmpty())
//        {
//            NodeDegree node_local_degree = nodes_local_degree.pollFirst();
//            if (node_local_degree.degree <= current_core_value)
//            {
//                KCoreValue node_core_value = new KCoreValue(
//                        node_local_degree.id, current_core_value);
//                nodes_local_core_value.add(node_core_value);
//                temp_component.remove(node_local_degree.id);
//            } else
//            {
//                KCoreValue node_core_value = new KCoreValue(
//                        node_local_degree.id, node_local_degree.degree);
//                nodes_local_core_value.add(node_core_value);
//                current_core_value = node_local_degree.degree;
//                temp_component.remove(node_local_degree.id);
//            }
//             /* Update the local degree of the neighbors of the removed node */
////            TreeSet<Integer> node_neighbors = node_local_degree.neighbors;
////            TreeSet<Integer> node_neighbors_rest = get_insection(node_neighbors, temp_component);
//            TreeSet<Integer> node_neighbors_rest = node_local_degree.neighbors;
//            if (!node_neighbors_rest.isEmpty())
//            {
//                for (Iterator<Integer> iter = node_neighbors_rest.iterator(); iter
//                        .hasNext();)
//                {
//                    Integer node_neighbor_rest = iter.next();
//                    NodeDegree node_neighbor_local_degree = nd.getNodeDegreeById(
//                            node_neighbor_rest, nodes_local_degree);
//                    NodeDegree node_neighbor_local_degree_new = new NodeDegree();
//                    node_neighbor_local_degree_new.id = node_neighbor_local_degree.id;
//                    node_neighbor_local_degree_new.degree = node_neighbor_local_degree.degree - 1;
//                    node_neighbor_local_degree_new.neighbors
//                            .addAll(node_neighbor_local_degree.neighbors);
//                    node_neighbor_local_degree_new.neighbors
//                            .remove((Integer)node_local_degree.id);
//                    nodes_local_degree.remove(node_neighbor_local_degree);
//                    nodes_local_degree.add(node_neighbor_local_degree_new);
//                }
//            }
//        }
//        return nodes_local_core_value;
//    }

    public TreeSet<Integer> get_insection(TreeSet<Integer> node_neighbors, TreeSet<Integer> temp_component) {
        TreeSet<Integer> node_neighbors_rest = new TreeSet<>();
        for(int node : temp_component) {
            if(node_neighbors.contains(node)) {
                node_neighbors_rest.add(node);
            }
        }
        return node_neighbors_rest;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if(obj instanceof KCoreValue) {
            KCoreValue anotherKCoreValue = (KCoreValue)obj;
            if(this.value == anotherKCoreValue.value) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public ArrayList<Integer> getAdjancentNodes(int id, ArrayList<ArrayList<Integer>> adjacencyTables) {
        ArrayList<Integer> neighbours = new ArrayList<>();
        neighbours.addAll(adjacencyTables.get(id));
        neighbours.remove((Integer)id);
        return neighbours;
    }

    public ArrayList<Integer> updateAdjancentNodes(int id, ArrayList<ArrayList<Integer>> adjacencyTables, ArrayList<ArrayList<Integer>> doneAdjacencyTables) {
        ArrayList<Integer> neighbours = new ArrayList<>();
        neighbours.addAll(adjacencyTables.get(id));
        neighbours.remove((Integer)id);
        for(int done_node : doneAdjacencyTables.get(id)) {
            if(neighbours.contains(done_node)) {
                neighbours.remove((Integer)done_node);
            }
        }
        return neighbours;
    }

    public static void main(String args[]) {
        //override equals method for deleting obj that value meet requements from List directly -20190523
        KCoreValue k1 = new KCoreValue();
        KCoreValue k2 = new KCoreValue();
        KCoreValue k3 = new KCoreValue();
        KCoreValue k4 = new KCoreValue();
        k1.id = 1;
        k1.value = 123;
        k2.id = 2;
        k2.value = 123;
        k3.id = 3;
        k3.value = 123;
        k4.id = 4;
        k4.value = 1234;
        ArrayList<KCoreValue> k = new ArrayList<KCoreValue>();
        k.add(k1);
        k.add(k2);
        k.add(k3);
        k.add(k4);

        System.out.println(k1.equals(k1));
        System.out.println(k1.equals(k2));
        System.out.println(k2.equals(k1));
        k.remove(new KCoreValue(123));
        for(Iterator<KCoreValue> iter = k.iterator();iter.hasNext();) {
            System.out.println(iter.next());
        }
        k.remove(new KCoreValue(123));
        k.remove(new KCoreValue(123));
        for(Iterator<KCoreValue> iter = k.iterator();iter.hasNext();) {
            System.out.println(iter.next());
        }
        k.remove(new KCoreValue(123));
        for(Iterator<KCoreValue> iter = k.iterator();iter.hasNext();) {
            System.out.println(iter.next());
        }
    }
}
