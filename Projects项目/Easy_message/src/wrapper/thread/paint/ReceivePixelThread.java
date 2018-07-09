package wrapper.thread.paint;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.paint.Pixel;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.BlockingQueue;

import static wrapper.StaticVariable.paintDataInputStream;

public class ReceivePixelThread implements Runnable {
    private BlockingQueue<Pixel> pixels;
    private Gson gson = new Gson();
    private Type type = new TypeToken<Pixel>() {
    }.getType();

    public ReceivePixelThread(BlockingQueue<Pixel> pixels) {
        this.pixels = pixels;
    }

    @Override
    public void run() {
        try {
            String pixel_trans;
            int i=0;
            while (true) {
                i++;
                //pixel_trans有可能是over字符串
                pixel_trans = paintDataInputStream.readUTF();
                Pixel pixel = gson.fromJson(pixel_trans, type);
                System.out.println("接受到像素 "+i+" !");
                System.out.println("String类型: "+pixel_trans);
                pixels.put(pixel);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
