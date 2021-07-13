package com.comparision.methods;

import javax.swing.JOptionPane;
public class Remind {

    public void remind() {

        JOptionPane.showMessageDialog(null, "THE PROGRAM HAS FINISHED RUNNING!!!");
//        JOptionPane.showMessageDialog(null, "THE PROGRAM HAS FINISHED RUNNING!!!", "Warning!!", JOptionPane.INFORMATION_MESSAGE);
/*        //不带图标
        Toolkit.getDefaultToolkit().beep();
        JOptionPane.showMessageDialog(null, "信息", "标题", JOptionPane.PLAIN_MESSAGE);
        //带 ？图标
        JOptionPane.showMessageDialog(null, "信息", "标题", JOptionPane.QUESTION_MESSAGE);
        //带 警告 图标
        JOptionPane.showMessageDialog(null, "信息", "标题", JOptionPane.WARNING_MESSAGE);
        // 带 警告 图标
        JOptionPane.showMessageDialog(null, "信息", "标题", JOptionPane.INFORMATION_MESSAGE);
        //带 错误 图标
        JOptionPane.showMessageDialog(null, "信息", "标题", JOptionPane.ERROR_MESSAGE);*/
    }


        public static void main(String[] args) {
            Remind r = new Remind();
            r.remind();
        }

    }
