package com.comparision.is;

import com.pulicoperations.methods.AdjacencyTables;

import javax.xml.soap.Node;
import java.util.*;

public class NodeSimilarity implements Comparator<NodeSimilarity> {
    public int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getSimilarity() {
        return similarity;
    }

    public void setSimilarity(double similarity) {
        this.similarity = similarity;
    }

    public double similarity;

    public NodeSimilarity() {

    }

    public NodeSimilarity(int id, int similarity) {
        this.id = id;
        this.similarity = similarity;
    }

    public NodeSimilarity(int similarity) {
        this.similarity = similarity;
    }

    @Override
    public String toString()
    {
        return "NodeSimilarity [id=" + id + ", similarity=" + similarity + "]";
    }


    @Override
    public int compare(NodeSimilarity o1, NodeSimilarity o2) {
        NodeSimilarity node1 = o1;
        NodeSimilarity node2 = o2;
        if(node1.similarity > node2.similarity) {
            return -1;
        } else if(node1.similarity == node2.similarity) {
            if(node1.id < node2.id) {
                return -1;
            } else if(node1.id == node2.id) {
                return 0;
            } else if(node1.id > node2.id) {
                return 1;
            }
        } else if(node1.similarity < node2.similarity) {
            return 1;
        }
        return 0;
    }
    

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if(obj instanceof NodeSimilarity) {
            NodeSimilarity anotherNodeSimilarity = (NodeSimilarity)obj;
            if(this.similarity == anotherNodeSimilarity.similarity) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public TreeSet<NodeSimilarity> getNodeSimilarityWeight(ArrayList<ArrayList<Integer>> adjacencyTable) {
        TreeSet<NodeSimilarity> NodeSimilarity = new TreeSet<NodeSimilarity>(new NodeSimilarity());
        for(int i=0; i<adjacencyTable.size(); i++) {
            double similarityWeightOfNode = 0;
            NodeSimilarity ns = new NodeSimilarity();
            ns.id = i;
            ns.similarity = getNodeCentrality(ns.id, adjacencyTable);
//            ns.similarity = getWeightOfNode(ns.id, adjacencyTable);
            NodeSimilarity.add(ns);
        }
        return NodeSimilarity;
    }

    public NodeSimilarity getNodeSimilarityById(int id, TreeSet<NodeSimilarity> NodeSimilaritys) {
        TreeSet<NodeSimilarity> nds = new TreeSet<>(new NodeSimilarity());
        nds.addAll(NodeSimilaritys);
        NodeSimilarity NodeSimilarity = new NodeSimilarity();
        for(NodeSimilarity ns : nds) {
            if(id == ns.getId()) {
                NodeSimilarity = ns;
                break;
            }
        }
        return NodeSimilarity;
    }

    public double calculateSimilarityBetweenNodes(int nodeA, int nodeB,
                                                  ArrayList<ArrayList<Integer>> adjacencyTable) {
        double similarity = 0;
        TreeSet<Integer> neighborsOfA = new TreeSet<>();
        TreeSet<Integer> neighborsOfB = new TreeSet<>();
        neighborsOfA = getNeighbors(nodeA, adjacencyTable);
        neighborsOfB = getNeighbors(nodeB, adjacencyTable);
        similarity = (double)getInsection(neighborsOfA, neighborsOfB).size() /
                ((double)neighborsOfA.size() + (double)neighborsOfB.size());
        return similarity;
    }

    public double getNodeCentrality(int node, ArrayList<ArrayList<Integer>> adjacencyTable) {
        double nodeCentrality = 0;
        TreeSet<Integer> nodeMass = new TreeSet<>();
        nodeMass = getNeighbors(node, adjacencyTable);
        nodeMass.add(node);
        for(int nodeId : nodeMass) {
            TreeSet<Integer> neighborMass = new TreeSet<>();
            TreeSet<Integer> commonArea = new TreeSet<>();
            neighborMass = getNeighbors(nodeId, adjacencyTable);
            neighborMass.add(nodeId);
            commonArea = getInsection(nodeMass, neighborMass);
            nodeCentrality += commonArea.size();
        }
        nodeCentrality = nodeCentrality / 2;
        return nodeCentrality;
    }
    public double getWeightOfNode(int node, ArrayList<ArrayList<Integer>> adjacencyTable) {
        double weightOfNode = 0;
        TreeSet<Integer> neighbors = new TreeSet<>();
        neighbors = getNeighbors(node, adjacencyTable);
        for(int neighborNode : neighbors) {
            weightOfNode += calculateSimilarityBetweenNodes(node, neighborNode, adjacencyTable);
        }
        return weightOfNode;
    }

    public TreeSet<Integer> getNeighbors(int id, ArrayList<ArrayList<Integer>> adjacencyTables) {
        TreeSet<Integer> neighbours = new TreeSet<>();
        neighbours.addAll(adjacencyTables.get(id));
        neighbours.remove((Integer)id);
        return neighbours;
    }

    public TreeSet<Integer> getInsection(TreeSet<Integer> setA,
                                          TreeSet<Integer> setB)
    {
        TreeSet<Integer> insection = new TreeSet<>();
        for (int node : setA) {
            if (setB.contains(node))
                insection.add(node);
        }
        return insection;
    }




    public static void main(String args[]) {
        //override equals method for deleting obj that similarity meet requements from List directly -20190523
        NodeSimilarity k1 = new NodeSimilarity();
        NodeSimilarity k2 = new NodeSimilarity();
        NodeSimilarity k3 = new NodeSimilarity();
        NodeSimilarity k4 = new NodeSimilarity();
        k1.id = 1;
        k1.similarity = 123;
        k2.id = 2;
        k2.similarity = 123;
        k3.id = 3;
        k3.similarity = 123;
        k4.id = 4;
        k4.similarity = 1234;
        ArrayList<NodeSimilarity> k = new ArrayList<NodeSimilarity>();
        k.add(k1);
        k.add(k2);
        k.add(k3);
        k.add(k4);

        System.out.println(k1.equals(k1));
        System.out.println(k1.equals(k2));
        System.out.println(k2.equals(k1));
        k.remove(new NodeSimilarity(123));
        for(Iterator<NodeSimilarity> iter = k.iterator();iter.hasNext();) {
            System.out.println(iter.next());
        }
        k.remove(new NodeSimilarity(123));
        k.remove(new NodeSimilarity(123));
        for(Iterator<NodeSimilarity> iter = k.iterator();iter.hasNext();) {
            System.out.println(iter.next());
        }
        k.remove(new NodeSimilarity(123));
        for(Iterator<NodeSimilarity> iter = k.iterator();iter.hasNext();) {
            System.out.println(iter.next());
        }
    }
}
