import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JTextAreaTest {
    private static JSONArray jsa = new JSONArray();

    private static JSONArray jsaori;// 读取的jsa

    // private static JSONObject job=new JSONObject();
    private static int i = 0;// 未點擊

    private static String output = "";

    private static JButton button, button2;

    private static JFrame frame;
    private static JTextField jtext;

    private static JTextArea textAreal, textArea2;

    private static JScrollPane scrollPaneadd, scrollPane;

    private static String original;

    private static final String BASEPATH = "E:\\workspace\\myphone\\assets\\";// 基本路径

    private static String extraPath = "out.txt";// 文件名

    private static void initFrame() {
        JFrame.setDefaultLookAndFeelDecorated(true);
      //  jtext=new JTextField(1);//标题栏,文件输出名
        button = new JButton("添加");
        button2 = new JButton("完成");
        frame = new JFrame("JTextArea Test");
//        GridLayout gridLayout = new GridLayout(3, false);
        
        frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        String text = "";
        textAreal = new JTextArea(text, 4, 10);
        textAreal.setPreferredSize(new Dimension(100, 100));
        scrollPaneadd = new JScrollPane(textAreal,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        textArea2=new JTextArea(text, 30, 40);
//        JScrollPane scrollPane = new JScrollPane(textArea2);
        
        scrollPane = new JScrollPane(textArea2,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        textAreal.setLineWrap(true);
        textArea2.setLineWrap(true);
//        JTextArea textArea = new JTextArea();
//        JScrollPane scrollPane = new JScrollPane(textArea);
//        content.add(scrollPane);
        frame.add(scrollPaneadd);
        frame.add(scrollPane);
       // frame.add(jtext);
        frame.add(button);
        frame.add(button2);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        /**
         * 初始化数据,先读取文件截取字符串
         */
        try {
            FileInputStream is = new FileInputStream(JTextAreaTest.BASEPATH
                    + extraPath);
            StringBuffer sb = new StringBuffer();
            BufferedReader br = new BufferedReader(new InputStreamReader(is,
                    "utf-8"));
            char buffer[] = new char[4096];
            int len;
            while ((len = br.read(buffer)) > 0)
                sb.append(new String(buffer, 0, len));
            original = sb.toString();
            // String deal=original.substring(1, original.length()-1);
            // System.out.println(deal);
            if (original.length() < 1) {
                jsaori = jsa;
            } else {
                jsaori = new JSONArray(original);
            }
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        initFrame();
        initListener();
    }

    private static void initListener() {
        button2.addActionListener(new ActionListener() {// 结束监听

                    @Override
                    public void actionPerformed(ActionEvent arg0) {

                        output = jsaori.toString();
                        System.out.println(output);

                        try {
                            String aa = output;

                            OutputStreamWriter out = new OutputStreamWriter(
                                    new FileOutputStream(JTextAreaTest.BASEPATH
                                            + extraPath), "UTF-8");
                            out.write(aa);
                            out.flush();
                            out.close();

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });
        button.addActionListener(new ActionListener() {// 添加

                    @SuppressWarnings("unchecked")
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Map map = new HashMap<String, String>();

                        String title = "";
                        String content = "";
                        title = textAreal.getText();
                        content = textArea2.getText();
                        map.put(title, content);
                        String mapstr = JsonUtil.map2Json(map);
                        try {
                            JSONObject job = new JSONObject(mapstr);
                            jsaori.put(job);
                            i++;
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                        textAreal.setText("");// 清空
                        textArea2.setText("");// 清空

                    }
                });
        textAreal.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent arg0) {
                System.out.println("removeUpdate");

            }

            @Override
            public void insertUpdate(DocumentEvent arg0) {
                System.out.println("insertUpdate");

            }

            @Override
            public void changedUpdate(DocumentEvent arg0) {
                System.out.println("changedUpdate");

            }
        });

    }
}