package com.comparision.methods;

import javax.swing.JOptionPane;
public class Remind {

    public void remind() {

        JOptionPane.showMessageDialog(null, "THE PROGRAM HAS FINISHED RUNNING!!!");
//        JOptionPane.showMessageDialog(null, "THE PROGRAM HAS FINISHED RUNNING!!!", "Warning!!", JOptionPane.INFORMATION_MESSAGE);
/*        //����ͼ��
        Toolkit.getDefaultToolkit().beep();
        JOptionPane.showMessageDialog(null, "��Ϣ", "����", JOptionPane.PLAIN_MESSAGE);
        //�� ��ͼ��
        JOptionPane.showMessageDialog(null, "��Ϣ", "����", JOptionPane.QUESTION_MESSAGE);
        //�� ���� ͼ��
        JOptionPane.showMessageDialog(null, "��Ϣ", "����", JOptionPane.WARNING_MESSAGE);
        // �� ���� ͼ��
        JOptionPane.showMessageDialog(null, "��Ϣ", "����", JOptionPane.INFORMATION_MESSAGE);
        //�� ���� ͼ��
        JOptionPane.showMessageDialog(null, "��Ϣ", "����", JOptionPane.ERROR_MESSAGE);*/
    }


        public static void main(String[] args) {
            Remind r = new Remind();
            r.remind();
        }

    }
