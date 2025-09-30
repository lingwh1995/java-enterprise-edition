package org.bluebridge;


/**
 * @author lingwh
 * @desc
 * @date 2025/9/30 15:50
 */

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

/**
 * opencv开发环境搭建（windows版）
 *      1.下载opencv（本次下载的是4.9.0版本） https://opencv.org/releases/
 *      2.解压后进入 opencv/build/java/x64中，复制dll文件jdk的bin目录中
 *      3.在maven中引入对应版本的opencv依赖
 */
public class OpencvHelloWorld {
    static {
        // 加载OpenCV本地库
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    public static void main(String[] args) {

        // 读取图像
        Mat image = Imgcodecs.imread("d://opencv//national_day.jpg");

        // 检查图像是否成功读取
        if (image.empty()) {
            System.out.println("图像读取失败！");
            return;
        }

        // 显示图像
        HighGui.imshow("Image", image);
        HighGui.waitKey(0);
        HighGui.destroyAllWindows();
    }

}
