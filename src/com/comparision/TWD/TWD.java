package com.comparision.TWD;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import DataReaderRealWorldNetwork.DataReaderRealWorldNetwork;

public class TWD
{
    public int n;
    public int m;
    public ArrayList<ArrayList<Integer>> adjacencyTable;
    public double a;
    public double b;
    public double e;

    public ArrayList<NodeLIV> nodes_liv;

    public TWD(int n, ArrayList<ArrayList<Integer>> adjacencyTable,
               double a, double b, double e)
    {
        this.n = n;
        this.adjacencyTable = adjacencyTable;
        // System.out.println("adjacency table");
        // for (Iterator<ArrayList<Integer>> iter =
        // this.adjacencyTable.iterator(); iter
        // .hasNext();)
        // {
        // ArrayList<Integer> line = iter.next();
        // System.out.println(line.size() - 1 + "," + line);
        // }
        // System.out.println();
        this.nodes_liv = new ArrayList<NodeLIV>();
        this.a = a;
        this.b = b;
        this.e = e;
    }

    /* (Tested) Calculate nodes' liv */
    public void calculate_nodes_liv()
    {
        for (int i = 0; i < this.n; i++)
        {
            int count = 0;
            ArrayList<Integer> line = this.adjacencyTable.get(i);
            int weight_of_i = line.size() - 1;
            for (int j = 1; j < line.size(); j++)
            {
                Integer neighbor = line.get(j);
                int weight_of_neighbor = (this.adjacencyTable.get(neighbor)
                        .size() - 1);
                if (weight_of_neighbor <= weight_of_i)
                {
                    count++;
                }
            }
            double liv_of_i = (double) (count) / (double) (weight_of_i);
            NodeLIV node_liv = new NodeLIV(i, liv_of_i);
            // System.out.println(count + "," + weight_of_i + "," + node_liv);
            this.nodes_liv.add(node_liv);
        }
    }

    /* (Tested) Get core nodes */
    public TreeSet<Integer> get_core_nodes()
    {
        TreeSet<Integer> core_nodes = new TreeSet<Integer>();
        for (Iterator<NodeLIV> iter = this.nodes_liv.iterator(); iter.hasNext();)
        {
            NodeLIV node_liv = iter.next();
            if (node_liv.liv == 1)
            {
                core_nodes.add(node_liv.id);
                // System.out.println(node_liv.id);
            }
        }
        return core_nodes;
    }

    /* (Tested) Get max connected components from graph */
    public ArrayList<TreeSet<Integer>> get_max_connected_components_from_graph(
            ArrayList<Integer> graph)
    {
        ArrayList<TreeSet<Integer>> max_connected_components = new ArrayList<TreeSet<Integer>>();
        int max_connected_component_size = 1;
        while (!graph.isEmpty())
        {
            Integer member = graph.remove(0);
            TreeSet<Integer> connected_component = new TreeSet<Integer>();
            TreeSet<Integer> queue = new TreeSet<Integer>();
            queue.add(member);
            while (!queue.isEmpty())
            {
                Integer first = queue.pollFirst();
                connected_component.add(first);
                ArrayList<Integer> line = this.adjacencyTable.get(first);
                for (int i = 1; i < line.size(); i++)
                {
                    Integer neighbor = line.get(i);
                    if (!connected_component.contains(neighbor)
                            && graph.contains(neighbor))
                    {
                        queue.add(neighbor);
                    }
                }
            }
            graph.removeAll(connected_component);
            if (connected_component.size() > max_connected_component_size)
            {
                max_connected_components.clear();
                max_connected_component_size = connected_component.size();
            }
            if (connected_component.size() == max_connected_component_size)
            {
                max_connected_components.add(connected_component);
            }
        }
        // for (Iterator<TreeSet<Integer>> iter = max_connected_components
        // .iterator(); iter.hasNext();)
        // {
        // System.out.println(iter.next());
        // }
        return max_connected_components;
    }

    /* (Tested) Get cluster core of core node */
    public ArrayList<TreeSet<Integer>> get_cluster_core_of_core(int core)
    {
        ArrayList<Integer> line = this.adjacencyTable.get(core);
        ArrayList<Integer> graph = new ArrayList<Integer>();
        for (int i = 1; i < line.size(); i++)
        {
            Integer neighbor = line.get(i);
            NodeLIV node_liv = this.nodes_liv.get(neighbor);
            if (node_liv.liv > 0)
            {
                graph.add(neighbor);
            }
        }
        // graph.add(core);
        // System.out.println(core + "," + graph);
        return get_max_connected_components_from_graph(graph);
    }

    /* (Tested) Get cluster cores */
    public ArrayList<TreeSet<Integer>> get_cluster_cores(
            TreeSet<Integer> core_nodes)
    {
        ArrayList<TreeSet<Integer>> cluster_cores = new ArrayList<TreeSet<Integer>>();
        for (Iterator<Integer> iter = core_nodes.iterator(); iter.hasNext();)
        {
            Integer core = iter.next();
            ArrayList<TreeSet<Integer>> cluster_core = get_cluster_core_of_core(core);
            for (Iterator<TreeSet<Integer>> iter_cluster = cluster_core
                    .iterator(); iter_cluster.hasNext();)
            {
                TreeSet<Integer> cluster = iter_cluster.next();
                cluster.add(core);
            }
            cluster_cores.addAll(cluster_core);
        }
        return cluster_cores;
    }

    /* (Tested) Get community bone neighbors */
    public TreeSet<Integer> get_community_bone_neighbors(
            TreeSet<Integer> community)
    {
        // System.out.println(community);
        TreeSet<Integer> community_neighbors = new TreeSet<Integer>();
        for (Iterator<Integer> iter = community.iterator(); iter.hasNext();)
        {
            Integer member = iter.next();
            ArrayList<Integer> line = this.adjacencyTable.get(member);
            for (int i = 1; i < line.size(); i++)
            {
                Integer neighbor = line.get(i);
                double neighbor_liv = this.nodes_liv.get(neighbor).liv;
                if (!community.contains(neighbor) && neighbor_liv > 0
                        && neighbor_liv < 1)
                {
                    community_neighbors.add(neighbor);
                    // System.out.print(this.nodes_liv.get(neighbor) + ",");
                    // System.out.print(neighbor + ",");
                }
            }
            // System.out.println();
        }
        return community_neighbors;
    }

    /* (Tested) Get community trivial neighbors */
    public TreeSet<Integer> get_community_trivial_neighbors(
            TreeSet<Integer> community)
    {
        // System.out.println(community);
        TreeSet<Integer> community_neighbors = new TreeSet<Integer>();
        for (Iterator<Integer> iter = community.iterator(); iter.hasNext();)
        {
            Integer member = iter.next();
            ArrayList<Integer> line = this.adjacencyTable.get(member);
            for (int i = 1; i < line.size(); i++)
            {
                Integer neighbor = line.get(i);
                double neighbor_liv = this.nodes_liv.get(neighbor).liv;
                if (!community.contains(neighbor) && neighbor_liv == 0)
                {
                    community_neighbors.add(neighbor);
                    // System.out.print(this.nodes_liv.get(neighbor) + ",");
                    // System.out.print(neighbor + ",");
                }
            }
            // System.out.println();
        }
        return community_neighbors;
    }

    /* (Tested) Calculate tightness */
    public double calculate_tightness(double parameter, int neighbor,
                                      TreeSet<Integer> pos_c, TreeSet<Integer> bnd_c)
    {
        double tightness = 0;
        int nodes_in_pos_c_count = 0;
        int nodes_in_bnd_c_count = 0;
        int nodes_in_vou_c_count = 0;
        ArrayList<Integer> line = this.adjacencyTable.get(neighbor);
        int number_of_neighbors = line.size() - 1;
        for (int i = 1; i < line.size(); i++)
        {
            Integer neighbor_neighbor = line.get(i);
            if (pos_c.contains(neighbor_neighbor))
            {
                nodes_in_pos_c_count++;
            }
            if (bnd_c.contains(neighbor_neighbor))
            {
                nodes_in_bnd_c_count++;
            }
        }
        nodes_in_vou_c_count = number_of_neighbors - nodes_in_pos_c_count
                - nodes_in_bnd_c_count;
        double x = (double) (this.a + this.b) * 0.5;
        tightness = (double) (nodes_in_pos_c_count + nodes_in_bnd_c_count * x)
                / (double) (nodes_in_vou_c_count + 1) - parameter
                * (double) (nodes_in_bnd_c_count) / (nodes_in_pos_c_count + 1);
        // System.out.println(x + "," + parameter + "," + neighbor + ","
        // + nodes_in_pos_c_count + "," + nodes_in_bnd_c_count + ","
        // + nodes_in_vou_c_count + "," + tightness);
        return tightness;
    }

    /* (Tested) Calculate compactness */
    public double calculate_compactness(double parameter,
                                        TreeSet<Integer> pos_c, TreeSet<Integer> bnd_c)
    {
        double compactness = 0;
        int w_in_c_count = 0;
        int w_out_c_count = 0;
        TreeSet<Integer> community = new TreeSet<Integer>();
        community.addAll(pos_c);
        community.addAll(bnd_c);
        for (Iterator<Integer> iter = community.iterator(); iter.hasNext();)
        {
            Integer member = iter.next();
            ArrayList<Integer> line = this.adjacencyTable.get(member);
            for (int i = 1; i < line.size(); i++)
            {
                Integer neighbor = line.get(i);
                if (community.contains(neighbor))
                {
                    w_in_c_count++;
                } else
                {
                    w_out_c_count++;
                }
            }
        }
        w_in_c_count = w_in_c_count / 2;
        compactness = (double) (w_in_c_count)
                / Math.pow((double) (w_in_c_count + w_out_c_count),
                (1.5 - parameter));
        // System.out.println(parameter + "," + w_in_c_count + "," +
        // w_out_c_count
        // + "," + compactness);
        return compactness;
    }

    /* (Tested) Get max a tightness bone */
    public int get_max_a_tightness_bone(TreeSet<Integer> adjbone,
                                        TreeSet<Integer> pos_c, TreeSet<Integer> bnd_c)
    {
        double max_a_tightness = -1;
        int max_a_tightness_bone = -1;
        for (Iterator<Integer> iter = adjbone.iterator(); iter.hasNext();)
        {
            Integer bone = iter.next();
            double a_tightness = calculate_tightness(a, bone, pos_c, bnd_c);
            if (a_tightness > max_a_tightness)
            {
                max_a_tightness = a_tightness;
                max_a_tightness_bone = bone;
            }
        }
        return max_a_tightness_bone;
    }

    /* (Tested) Calculate community similiarity */
    public double calculate_communities_similiarity(
            TreeSet<Integer> community_1, TreeSet<Integer> community_2)
    {
        double similiarity = 0;
        int count = 0;
        for (Iterator<Integer> iter = community_1.iterator(); iter.hasNext();)
        {
            Integer member = iter.next();
            if (community_2.contains(member))
            {
                count++;
            }
        }
        int size_1 = community_1.size();
        int size_2 = community_2.size();
        if (size_1 < size_2)
        {
            similiarity = (double) count / (double) size_1;
        } else
        {
            similiarity = (double) count / (double) size_2;
        }
        return similiarity;
    }

    public ArrayList<TreeSet<Integer>> run_twd()
    {
        /*****************************************************************************************/
        /* Get core clusters */
        calculate_nodes_liv();
        TreeSet<Integer> core_nodes = get_core_nodes();
        ArrayList<TreeSet<Integer>> cluster_cores = get_cluster_cores(core_nodes);
        // System.out.println("core_nodes: " + core_nodes);
        // System.out.println("cluster_cores: ");
        // for (Iterator<TreeSet<Integer>> iter = cluster_cores.iterator(); iter
        // .hasNext();)
        // {
        // System.out.println(iter.next());
        // }
        /*****************************************************************************************/
        /* Extend core clusters to get early communities */
        ArrayList<PBCommunity> early_communities = new ArrayList<PBCommunity>();
        for (Iterator<TreeSet<Integer>> iter = cluster_cores.iterator(); iter
                .hasNext();)
        {
            TreeSet<Integer> pos_c = iter.next();
            TreeSet<Integer> bnd_c = new TreeSet<Integer>();
            TreeSet<Integer> adjbone = get_community_bone_neighbors(pos_c);
            while (!adjbone.isEmpty())
            {
                int bone = get_max_a_tightness_bone(adjbone, pos_c, bnd_c);
                double a_tightness = calculate_tightness(a, bone, pos_c, bnd_c);
                double b_tightness = calculate_tightness(b, bone, pos_c, bnd_c);
                double a_compactness = calculate_compactness(a, pos_c, bnd_c);
                double b_compactness = calculate_compactness(b, pos_c, bnd_c);
                double deta_a = a_tightness - a_compactness;
                double deta_b = b_tightness - b_compactness;
                if (deta_a > 0)
                {
                    pos_c.add(bone);
                } else if (deta_b >= 0)
                {
                    bnd_c.add(bone);
                }
                adjbone.remove((Integer) bone);
            }
            TreeSet<Integer> community = new TreeSet<Integer>();
            community.addAll(pos_c);
            community.addAll(bnd_c);
            /**********************************************/
            // adjbone = get_community_bone_neighbors(community);
            /**********************************************/
            PBCommunity p_b_community = new PBCommunity();
            p_b_community.pos_c = pos_c;
            p_b_community.bnd_c = bnd_c;
            early_communities.add(p_b_community);
        }
        /*****************************************************************************************/
        /* Process trivial vertices based on conition 1 */
        TreeSet<Integer> trivial_nodes = new TreeSet<Integer>();
        for (Iterator<NodeLIV> iter = this.nodes_liv.iterator(); iter.hasNext();)
        {
            NodeLIV node = iter.next();
            if (node.liv == 0)
            {
                trivial_nodes.add(node.id);
            }
        }
        // System.out.println("early communities: ");
        // for (Iterator<PBCommunity> iter = early_communities.iterator(); iter
        // .hasNext();)
        // {
        // System.out.println(iter.next());
        // }
        // System.out.println(trivial_nodes);
        while (true)
        {
            int trivial_nodes_size_old = trivial_nodes.size();
            TreeSet<Integer> removed_trivial_nodes = new TreeSet<Integer>();
            for (Iterator<Integer> iter_trivial_node = trivial_nodes.iterator(); iter_trivial_node
                    .hasNext();)
            {
                Integer trivial_node = iter_trivial_node.next();
                for (Iterator<PBCommunity> iter_pbcommunity = early_communities
                        .iterator(); iter_pbcommunity.hasNext();)
                {
                    PBCommunity early_community = iter_pbcommunity.next();
                    double a_tightness = calculate_tightness(a, trivial_node,
                            early_community.pos_c, early_community.bnd_c);
                    double a_compactness = calculate_compactness(a,
                            early_community.pos_c, early_community.bnd_c);
                    double deta_a = a_tightness - a_compactness;
                    double b_tightness = calculate_tightness(b, trivial_node,
                            early_community.pos_c, early_community.bnd_c);
                    double b_compactness = calculate_compactness(b,
                            early_community.pos_c, early_community.bnd_c);
                    double deta_b = b_tightness - b_compactness;
                    if (deta_a > 0)
                    {
                        early_community.pos_c.add(trivial_node);
                        removed_trivial_nodes.add(trivial_node);
                        break;
                    } else if (deta_b > 0)
                    {
                        early_community.bnd_c.add(trivial_node);
                        removed_trivial_nodes.add(trivial_node);
                        break;
                    }
                }
            }
            trivial_nodes.removeAll(removed_trivial_nodes);
            int trivial_nodes_size_new = trivial_nodes.size();
            if (trivial_nodes_size_new == trivial_nodes_size_old)
            {
                break;
            }
        }
        // System.out.println("early communities: ");
        // for (Iterator<PBCommunity> iter = early_communities.iterator(); iter
        // .hasNext();)
        // {
        // System.out.println(iter.next());
        // }
        // System.out.println(trivial_nodes);
        /*****************************************************************************************/
        /* Process trivial vertices based on conition 2 */
        while (true)
        {
            int trivial_nodes_size_old = trivial_nodes.size();
            TreeSet<Integer> removed_trivial_nodes = new TreeSet<Integer>();
            for (Iterator<Integer> iter_trivial_node = trivial_nodes.iterator(); iter_trivial_node
                    .hasNext();)
            {
                Integer trivial_node = iter_trivial_node.next();
                double max_deta_a = -1;
                double max_deta_b = -1;
                PBCommunity max_deta_a_community = new PBCommunity();
                PBCommunity max_deta_b_community = new PBCommunity();
                for (Iterator<PBCommunity> iter_pbcommunity = early_communities
                        .iterator(); iter_pbcommunity.hasNext();)
                {
                    PBCommunity early_community = iter_pbcommunity.next();
                    double a_tightness = calculate_tightness(a, trivial_node,
                            early_community.pos_c, early_community.bnd_c);
                    double a_compactness = calculate_compactness(a,
                            early_community.pos_c, early_community.bnd_c);
                    double deta_a = a_tightness - a_compactness;
                    double b_tightness = calculate_tightness(b, trivial_node,
                            early_community.pos_c, early_community.bnd_c);
                    double b_compactness = calculate_compactness(b,
                            early_community.pos_c, early_community.bnd_c);
                    double deta_b = b_tightness - b_compactness;
                    if (deta_a > max_deta_a)
                    {
                        max_deta_a = deta_a;
                        max_deta_a_community = early_community;
                    }
                    if (deta_b > max_deta_b)
                    {
                        max_deta_b = deta_b;
                        max_deta_b_community = early_community;
                    }
                }
                max_deta_a_community.bnd_c.add(trivial_node);
                max_deta_b_community.bnd_c.add(trivial_node);
                removed_trivial_nodes.add(trivial_node);
            }
            trivial_nodes.removeAll(removed_trivial_nodes);
            int trivial_nodes_size_new = trivial_nodes.size();
            if (trivial_nodes_size_new == trivial_nodes_size_old)
            {
                break;
            }
        }
        // System.out.println("early communities: ");
        // for (Iterator<PBCommunity> iter = early_communities.iterator(); iter
        // .hasNext();)
        // {
        // System.out.println(iter.next());
        // }
        // System.out.println(trivial_nodes);
        // System.out.println("*********************");
        /*****************************************************************************************/
        /* Process isolate vertices */
        TreeSet<Integer> isolate_nodes = new TreeSet<Integer>();
        TreeSet<Integer> all_signed_nodes = new TreeSet<Integer>();
        for (int i = 0; i < this.n; i++)
        {
            isolate_nodes.add(i);
        }
        for (Iterator<PBCommunity> iter = early_communities.iterator(); iter
                .hasNext();)
        {
            PBCommunity community = iter.next();
            isolate_nodes.removeAll(community.pos_c);
            isolate_nodes.removeAll(community.bnd_c);
            all_signed_nodes.addAll(community.pos_c);
            all_signed_nodes.addAll(community.bnd_c);
        }
        // System.out.println(isolate_nodes);
        // System.out.println(all_signed_nodes);
        // System.out.println("*********************");
        while (!isolate_nodes.isEmpty())
        {
            TreeSet<Integer> removed_isolate_nodes = new TreeSet<Integer>();
            for (Iterator<Integer> iter = isolate_nodes.iterator(); iter
                    .hasNext();)
            {
                Integer isolate_node = iter.next();
                ArrayList<Integer> line = this.adjacencyTable.get(isolate_node);
                for (int j = 1; j < line.size(); j++)
                {
                    Integer neighbor = line.get(j);
                    ArrayList<PBCommunity> communities = new ArrayList<PBCommunity>();
                    for (Iterator<PBCommunity> iter_community = early_communities
                            .iterator(); iter_community.hasNext();)
                    {
                        PBCommunity community = iter_community.next();
                        if (community.pos_c.contains(neighbor)
                                || community.bnd_c.contains(neighbor))
                        {
                            communities.add(community);
                            community.bnd_c.add(isolate_node);
                        }
                    }
                    if (!communities.isEmpty())
                    {
                        removed_isolate_nodes.add(isolate_node);
                    }
                }
            }
            isolate_nodes.removeAll(removed_isolate_nodes);
        }
        // System.out.println("early communities: ");
        // for (Iterator<PBCommunity> iter = early_communities.iterator(); iter
        // .hasNext();)
        // {
        // System.out.println(iter.next());
        // }
        // System.out.println("*********************");
        // System.out.println(isolate_nodes);
        /*****************************************************************************************/
        /* Merge communities */
        // ArrayList<TreeSet<Integer>> connected_community_ids = new
        // ArrayList<TreeSet<Integer>>();
        // for (int i = 0; i < early_communities.size(); i++)
        // {
        // TreeSet<Integer> community_ids = new TreeSet<Integer>();
        // community_ids.add(i);
        // PBCommunity c1 = early_communities.get(i);
        // TreeSet<Integer> community_1 = c1.pos_c;
        // for (int j = i + 1; j < early_communities.size(); j++)
        // {
        // PBCommunity c2 = early_communities.get(j);
        // TreeSet<Integer> community_2 = c2.pos_c;
        // double degpop = calculate_communities_similiarity(community_1,
        // community_2);
        // if (degpop > e)
        // {
        // community_ids.add(j);
        // }
        // }
        // System.out.println(community_ids);
        // connected_community_ids.add(community_ids);
        // }
        while (true)
        {
            double max_degpop = 0;
            PBCommunity pbc_1 = new PBCommunity();
            PBCommunity pbc_2 = new PBCommunity();
            for (int i = 0; i < early_communities.size(); i++)
            {
                PBCommunity c1 = early_communities.get(i);
                TreeSet<Integer> community_1 = c1.pos_c;
                for (int j = i + 1; j < early_communities.size(); j++)
                {
                    PBCommunity c2 = early_communities.get(j);
                    TreeSet<Integer> community_2 = c2.pos_c;
                    double degpop = calculate_communities_similiarity(
                            community_1, community_2);
                    if (degpop > max_degpop)
                    {
                        max_degpop = degpop;
                        pbc_1 = c1;
                        pbc_2 = c2;
                    }
                }
            }
            if (max_degpop > this.e)
            {
                TreeSet<Integer> pos_c = new TreeSet<Integer>();
                pos_c.addAll(pbc_1.pos_c);
                pos_c.addAll(pbc_2.pos_c);
                TreeSet<Integer> bnd_c = new TreeSet<Integer>();
                TreeSet<Integer> other_members = new TreeSet<Integer>();
                other_members.addAll(pbc_1.bnd_c);
                other_members.addAll(pbc_2.bnd_c);
                TreeSet<Integer> members_added_to_pos_c = new TreeSet<Integer>();
                TreeSet<Integer> members_added_to_bnd_c = new TreeSet<Integer>();
                for (Iterator<Integer> iter = other_members.iterator(); iter
                        .hasNext();)
                {
                    Integer other_member = iter.next();
                    double a_tightness = calculate_tightness(a, other_member,
                            pos_c, bnd_c);
                    double a_compactness = calculate_compactness(a, pos_c,
                            bnd_c);
                    double deta_a = a_tightness - a_compactness;
                    if (deta_a > 0)
                    {
                        members_added_to_pos_c.add(other_member);
                    } else
                    {
                        members_added_to_bnd_c.add(other_member);
                    }
                }
                pos_c.addAll(members_added_to_pos_c);
                bnd_c.addAll(members_added_to_bnd_c);
                PBCommunity community_new = new PBCommunity();
                community_new.pos_c = pos_c;
                community_new.bnd_c = bnd_c;
                early_communities.remove(pbc_1);
                early_communities.remove(pbc_2);
                early_communities.add(community_new);
            } else
            {
                break;
            }
        }

        // System.out.println("early communities: ");
        // for (Iterator<PBCommunity> iter = early_communities.iterator(); iter
        // .hasNext();)
        // {
        // System.out.println(iter.next());
        // }
        // System.out.println("*********************");
        /*****************************************************************************************/
        ArrayList<TreeSet<Integer>> finial_communities = new ArrayList<TreeSet<Integer>>();
        for (Iterator<PBCommunity> iter = early_communities.iterator(); iter
                .hasNext();)
        {
            ArrayList<Integer> community = new ArrayList<Integer>();
            PBCommunity pb_c = iter.next();
            community.addAll(pb_c.pos_c);
            community.addAll(pb_c.bnd_c);
            // System.out.println(community);
            TreeSet<Integer> community_temp = new TreeSet<Integer>();
            community_temp.addAll(community);
            finial_communities.add(community_temp);
        }
        return finial_communities;
    }

    public static void main(String[] args) throws IOException
    {
        String file_path = "F:/1papers/20190411 first/data set/";
        String file_name = "karate";
        // karate;dolphin;football;krebsbook;dblp;amazon;youtube
        file_path = file_path + file_name +"/";
        String community = "_community.txt";
        String table = "_table.txt";
        String file_path_community = file_path + file_name + community;
        String file_path_table = file_path + file_name + table;

        DataReaderRealWorldNetwork data_reader = new DataReaderRealWorldNetwork(
                file_path_community, file_path_table);
        data_reader.read_realworld_network();

        double a = 0.48;
        double b = 0.47;
        double e = 0.5;
        TWD twd = new TWD(data_reader.n,
                data_reader.adjacencyTable, a, b, e);
        twd.run_twd();
    }
}
