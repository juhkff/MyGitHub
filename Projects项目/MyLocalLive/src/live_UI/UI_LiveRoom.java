package live_UI;

import live_Process.TcpClient;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

public class UI_LiveRoom {
    private inner_UI mainFrame;
    private ImageInputStream imgInputStream = null;
    private DataInputStream dataInputStream = null;
    private ByteArrayInputStream byteArrayInputStream;
    private BufferedImage img = null;
    private String userName;
    private TcpClient tcpClient;

    UI_LiveRoom(/*InputStream inputStream*/TcpClient tcpClient, String userName) {
        this.tcpClient = tcpClient;
        this.userName = userName;
        // this.imgInputStream = ImageIO.createImageInputStream(inputStream);
        this.dataInputStream = new DataInputStream(/*inputStream*/tcpClient.getInputStream());
        this.mainFrame = new inner_UI();
        mainFrame.setTitle(userName + "的直播间");
        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        // 设置窗口大小不可变
        mainFrame.setResizable(false);
        // 设置窗口打开居中
        mainFrame.setLocationRelativeTo(null);
        // 窗口大小
        // mainFrame.setSize(1920, 1080);
        mainFrame.setSize(1760, 1030);
        // 展示窗口
        mainFrame.setVisible(true);
    }

    public void startLive() {
        mainFrame.startLive();
    }

    /*
     * private UI_LiveRoom() { this.mainFrame = new inner_UI();
     * mainFrame.setTitle("直播间"); //
     * ui_Register.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置窗口大小不可变
     * mainFrame.setResizable(false); // 设置窗口打开居中
     * mainFrame.setLocationRelativeTo(null); // 窗口大小 mainFrame.setSize(1920, 1080);
     * // 展示窗口 mainFrame.setVisible(true); }
     */

    private class inner_UI extends JFrame {
        /**
         *
         */
        private static final long serialVersionUID = 1L;
        private JPanel mainPanel = new JPanel();
        private JLabel image = new JLabel();
        private ReceiveLive receiveLive;

        public inner_UI() {
            // TODO Auto-generated constructor stub
            mainPanel.setLayout(null);
            mainPanel.add(image);
            // mainPanel.setBounds(0, 0, 1920, 1080);
            // image.setBounds(0, 0, 1920, 1080);
            image.setBounds(0, 0, 1760, 990);
            getContentPane().add(mainPanel);

            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);
                    dispose();

                    //receiveLive.stop();
                    try {
                        dataInputStream.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    receiveLive.cancel(true);
                    try {
                        tcpClient.getClient().close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    JFrame ui_HomePage = new UI_HomePage(userName);
                    // 设置窗口大小不可变
                    ui_HomePage.setResizable(false);
                    // 设置窗口打开居中
                    ui_HomePage.setLocationRelativeTo(null);
                    // 窗口大小
                    ui_HomePage.setSize(750, 500);
                    // 展示窗口
                    ui_HomePage.setTitle("局域网直播工具-" + userName);
                    ui_HomePage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    ui_HomePage.setVisible(true);


                }
            });
        }

        public void startLive() {
            receiveLive = new ReceiveLive(img);
            receiveLive.execute();
        }

        private class ReceiveLive extends SwingWorker<Void, BufferedImage> {
            private BufferedImage img;
            private int index = 0;

            /*
            public void stop(){
                this.done();
            }
            */

            public ReceiveLive(BufferedImage img) {
                super();
                this.img = img;
            }

            @Override
            protected Void doInBackground() throws Exception {
                // TODO Auto-generated method stub
                int length;
                int i = 0;
                byte[] b = new byte[65536];
                byte[] sizeByte = new byte[6];
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                dataInputStream.read(sizeByte, 0, 6);
                int photoSize = Integer.parseInt(new String(sizeByte));       //接收到的文件大小
                int currentSize = 0;
                length = 0;
                while (true) {
                    // i++;
                    /*int receiveSize=*/
                    System.out.println("本次接收到的文件大小为: " + photoSize + " Byte");
                    //int currentSize = 0;
                    while ((length = dataInputStream.read(b)) != -1) {
                        // byteArrayOutputStream.reset();
                        System.out.println(length);
                        if (currentSize + length <= photoSize) {
                            //本张图片未接收完或刚好接收完
                            byteArrayOutputStream.write(b, 0, length);
                            if (currentSize + length == photoSize) {
                                i++;
                                byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                                byteArrayOutputStream = new ByteArrayOutputStream();
                                img = ImageIO.read(/* imgInputStream */byteArrayInputStream);
                                //if (img != null) {        //?
                                System.out.println("进行第" + i + "次屏幕读取\t\t");
                                //File file = new File("D:\\新建文件夹 (3)\\新建文件夹\\接收\\test" + i + ".jpg");
                                //FileOutputStream fileOutputStream=new FileOutputStream(file);
                                publish(img);
                                //ImageIO.write(img, "JPG", file);
                                System.out.println("第" + i + "次屏幕读取成功");
                                //从b的当前偏移量向后计6位数为下一个图片的大小
                                dataInputStream.read(sizeByte, 0, 6);
                                photoSize = Integer.parseInt(new String(sizeByte));
                                currentSize = 0;
                                //photoSize = Integer.parseInt(new String(b,photoSize-currentSize,6));
                                //byteArrayOutputStream.write(b, photoSize - currentSize+6, length + currentSize - photoSize-6);
                                break;
                            } else
                                currentSize += length;
                        } else {
                            //读取完一整张图片且有多余数据（发生粘包）
                            byteArrayOutputStream.write(b, 0, photoSize - currentSize);
                            i++;
                            byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                            byteArrayOutputStream = new ByteArrayOutputStream();
                            img = ImageIO.read(/* imgInputStream */byteArrayInputStream);
                            //if (img != null) {        //?
                            System.out.println("进行第" + i + "次屏幕读取\t\t");
                            //File file = new File("D:\\新建文件夹 (3)\\新建文件夹\\接收\\test" + i + ".jpg");
                            //FileOutputStream fileOutputStream=new FileOutputStream(file);
                            publish(img);
                            //ImageIO.write(img, "JPG", file);
                            System.out.println("第" + i + "次屏幕读取成功");
                            //从b的当前偏移量向后计6位数为下一个图片的大小
                            int tempLength = photoSize - currentSize;
                            byteArrayOutputStream.write(b, /*photoSize - currentSize*/tempLength + 6, length /*+ currentSize - photoSize*/ - tempLength - 6);           //currentSize=12,length=28,photoSize=30
                            currentSize = length /*+ currentSize - photoSize*/ - tempLength - 6;
                            photoSize = Integer.parseInt(new String(b, tempLength, 6));
                            break;
                        }
                        /*
                        if (length == 65536) // 还未读取完整张图片
                            byteArrayOutputStream.write(b, 0, length);
                        else {
                            // 读取完了一整张图片
                            // }
                            // System.out.println("跳出循环");
                            byteArrayOutputStream.write(b, 0, length);
                            i++;
                            byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                            byteArrayOutputStream.reset();
                            img = ImageIO.read(/* imgInputStream *//*byteArrayInputStream);
                            //if (img != null) {        //?

                            System.out.println("进行第" + i + "次屏幕读取\t\t");
                            File file = new File("D:\\新建文件夹 (3)\\新建文件夹\\接收\\test" + i + ".jpg");
                            //FileOutputStream fileOutputStream=new FileOutputStream(file);
                            publish(img);
                            ImageIO.write(img, "JPG", file);
                            System.out.println("第" + i + "次屏幕读取成功");
                            //}

                        }
                        */
                    }
                }
            }

            @Override
            protected void process(List<BufferedImage> chunks) {
                // TODO Auto-generated method stub
                System.out.println("chunks目前的大小为:" + chunks.size());
                //BufferedImage img = chunks.get(chunks.size() - 1);
                BufferedImage img = chunks.get(0);

                index++;
                System.out.println("进行第" + index + "次屏幕映射");
                if (img != null) {
                    ImageIcon icon = new ImageIcon(img);
                    icon.setImage(icon.getImage().getScaledInstance(1760, 990, Image.SCALE_FAST));
                    image.setIcon(/* new ImageIcon(img) */icon);
                    System.out.println("第" + index + "次屏幕映射成功");
                }
            }

            /*
            @Override
            protected void done() {
                super.done();
                try {
                    dataInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            */
        }
    }

    public static void main(String[] args) {
        // UI_LiveRoom test = new UI_LiveRoom();
    }
}
