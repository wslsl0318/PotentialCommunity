package com.comparision.pc;

import com.comparision.fifth.NodeImportance;
import com.comparision.is.NodeSimilarity;
import com.comparision.methods.KCoreValue;
import com.comparision.methods.NodeDegree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;

public class NodeCentrality implements Comparator<NodeCentrality> {
    public int id;
    public int influence;

    public NodeCentrality() {
    }

    @Override
    public String toString()
    {
        return "NodeInfluence [id=" + id + ", influence=" + influence + "]";
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + influence;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        NodeCentrality other = (NodeCentrality) obj;
        if (influence != other.influence)
            return false;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public int compare(NodeCentrality o1, NodeCentrality o2)
    {
        // TODO Auto-generated method stub
        NodeCentrality node_1 = o1;
        NodeCentrality node_2 = o2;
        if (node_1.influence < node_2.influence)
        {
            return 1;
        } else if (node_1.influence == node_2.influence)
        {
            if (node_1.id > node_2.id)
            {
                return 1;
            } else if (node_1.id == node_2.id)
            {
                return 0;
            } else if (node_1.id < node_2.id)
            {
                return -1;
            }
        } else if (node_1.influence > node_2.influence)
        {
            return -1;
        }
        return 0;
    }

    public TreeSet<NodeCentrality> getNodeInfluence(String type, ArrayList<ArrayList<Integer>> adjacencyTable) {
        ArrayList<ArrayList<Integer>> temp_adjacencyTable = new ArrayList<>();
        temp_adjacencyTable.addAll(adjacencyTable);
        TreeSet<NodeCentrality> node_influences = new TreeSet<NodeCentrality>(new NodeCentrality());
        NodeDegree nd = new NodeDegree();
        KCoreValue k = new KCoreValue();
        NodeImportance i = new NodeImportance();
        NodeSimilarity s  = new NodeSimilarity();
        if("degree".equals(type) || "important".equals(type)) {
            TreeSet<NodeDegree> nodeDegrees = nd.getNodeDegrees(temp_adjacencyTable);
            for(NodeDegree temp_nd : nodeDegrees) {
                NodeCentrality node_influence = new NodeCentrality();
                node_influence.id = temp_nd.id;
                node_influence.influence = temp_nd.degree;
                node_influences.add(node_influence);
            }
        } else if("kcore".equals(type)) {
            TreeSet<KCoreValue> kCoreValues = k.getKCoreValue(temp_adjacencyTable);
            for(KCoreValue temp_k : kCoreValues) {
                NodeCentrality node_influence = new NodeCentrality();
                node_influence.id = temp_k.id;
                node_influence.influence = temp_k.value;
                node_influences.add(node_influence);
            }
        } else if("similarity".equals(type)) {
            TreeSet<NodeSimilarity> nodeSimilarity = s.getNodeSimilarityWeight(temp_adjacencyTable);
            for(NodeSimilarity temp_s : nodeSimilarity) {
                NodeCentrality node_influence = new NodeCentrality();
                node_influence.id = temp_s.id;
                int similarity = (new Double(temp_s.similarity * 10000)).intValue();
                node_influence.influence = similarity;
                node_influences.add(node_influence);
            }
        }
        return node_influences;
    }
}
