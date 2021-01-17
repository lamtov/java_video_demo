import ij.ImagePlus;

import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import ij.IJ;
import ij.gui.ImageRoi;
import ij.gui.Overlay;
import org.opencv.videoio.VideoWriter;
import org.opencv.videoio.Videoio;
import org.opencv.core.*;
import org.opencv.core.Core;
//import org.bytedeco.javacv.Java2DFrameUtils;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
public class main {
    static BufferedImage Mat2BufferedImage(Mat matrix)throws Exception {
        MatOfByte mob=new MatOfByte();
        Imgcodecs.imencode(".jpg", matrix, mob);
        byte ba[]=mob.toArray();

        BufferedImage bi= ImageIO.read(new ByteArrayInputStream(ba));
        return bi;
    }
    public static VideoWriter videoWriter;

    public static  Mat matify(BufferedImage sourceImg) {

        long millis = System.currentTimeMillis();

        DataBuffer dataBuffer = sourceImg.getRaster().getDataBuffer();
        byte[] imgPixels = null;
        Mat imgMat = null;

        int width = sourceImg.getWidth();
        int height = sourceImg.getHeight();

        if(dataBuffer instanceof DataBufferByte) {
            imgPixels = ((DataBufferByte)dataBuffer).getData();
        }

        if(dataBuffer instanceof DataBufferInt) {

            int byteSize = width * height;
            imgPixels = new byte[byteSize*3];

            int[] imgIntegerPixels = ((DataBufferInt)dataBuffer).getData();

            for(int p = 0; p < byteSize; p++) {
                imgPixels[p*3 + 0] = (byte) ((imgIntegerPixels[p] & 0x00FF0000) >> 16);
                imgPixels[p*3 + 1] = (byte) ((imgIntegerPixels[p] & 0x0000FF00) >> 8);
                imgPixels[p*3 + 2] = (byte) (imgIntegerPixels[p] & 0x000000FF);
            }
        }

        if(imgPixels != null) {
            imgMat = new Mat(height, width, CvType.CV_8UC3);
            imgMat.put(0, 0, imgPixels);
        }

        System.out.println("matify exec millis: " + (System.currentTimeMillis() - millis));

        return imgMat;
    }

//    static {System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}
    public static void main(String args[]) throws Exception {
        nu.pattern.OpenCV.loadShared();
        System.out.println(Core.VERSION);
        Mat frame = new Mat();
        //0; default video device id
        String inputFile = "C:/Users/Lam/IdeaProjects/MERGEIMAGE/src/data/input_cut.mp4";
        String outputFile = "C:/Users/Lam/IdeaProjects/MERGEIMAGE/src/data/output.mp4";


//We have stated that we will use x264 as codec with FourCC
//For writing, we add the following method and it will write the image we give as parameter in this call.


        VideoCapture videoCapture = new VideoCapture();
        videoCapture.open(inputFile);
        Size frameSize = new Size((int) videoCapture.get(Videoio.CAP_PROP_FRAME_WIDTH), (int) videoCapture.get(Videoio.CAP_PROP_FRAME_HEIGHT));
        videoWriter = new VideoWriter(outputFile,  VideoWriter.fourcc ('F', 'M', 'P', '4'),
                videoCapture.get(Videoio.CAP_PROP_FPS), frameSize, true);
//        JFrame jframe = new JFrame("Title");
//        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        JLabel vidpanel = new JLabel();
//        jframe.setContentPane(vidpanel);
//        jframe.setVisible(true);





        while (videoCapture.read(frame)) {
            System.out.println("DSFSDF");
//            if () {

                Imgproc.rectangle (
                        frame,                    //Matrix obj of the image
                        new Point(130, 50),        //p1
                        new Point(300, 280),       //p2
                        new Scalar(0, 0, 255),     //Scalar object for color
                        5                          //Thickness of the line
                );
// Adding Text
//                Imgproc.putText (
//                        frame,                          // Matrix obj of the image
//                        "Ravivarma's Painting",          // Text to be added
//                        new Point(10, 50),               // point
//                        Core.FONT_HERSHEY_SIMPLEX ,      // front face
//                        1,                               // front scale
//                        new Scalar(0, 0, 0),             // Scalar object for color
//                        4                                // Thickness
//                );
//                Mat smallImage=Imgcodecs.imread("C:/Users/Lam/IdeaProjects/MERGEIMAGE/src/data/test2.png");
//////                Core.addWeighted( clothe, 1, frame, 1, 0.0, frame);
////                Mat output= new Mat();
////                overlayImage(frame,smallImage,output,new Point(0,0));
////                Rect roi= new Rect(0,0,frame.width(),frame.height());
////                frame.copyTo( new Mat(smallImage,roi) );
//                //clothe.copyTo(frame.rowRange(1, frame.rows()).colRange(3, frame.cols()));
//                ImageIcon image = new ImageIcon(Mat2BufferedImage(frame));
//
//
//
//                vidpanel.setIcon(image);
//                vidpanel.repaint();




            double alpha = 0.5; double beta; double input;
            Mat src1, src2, dst = new Mat();
//            System.out.println(" Simple Linear Blender ");
//            System.out.println("-----------------------");
//            System.out.println("* Enter alpha [0.0-1.0]: ");
//            Scanner scan = new Scanner( System.in ).useLocale(Locale.US);
            input = 1;
//            if( input >= 0.0 && input <= 1.0 )
            alpha = input;
            src1 = frame;
            src2 = Imgcodecs.imread("C:/Users/Lam/IdeaProjects/MERGEIMAGE/src/data/linux.png");
            if( src1.empty() == true ){ System.out.println("Error loading src1"); return;}
            if( src2.empty() == true ){ System.out.println("Error loading src2"); return;}
//            Mat croppedimage = cropImage(image,rect);
            Mat resizeimage = new Mat();
            Size sz = new Size(src1.width(), src1.height());
            Imgproc.resize( src2, resizeimage, sz,100,100,Imgproc.INTER_CUBIC );
            beta = 1.0  ;
            Core.addWeighted( src1, alpha, resizeimage, beta, 0.0, dst);

//                ImagePlus imp = new ImagePlus("title",Mat2BufferedImage(frame));
//                ImagePlus imp2 = IJ.openImage("http://imagej.nih.gov/ij/images/clown.jpg");
//
//                ImageRoi roi = new ImageRoi(50, 200, imp2.getProcessor());
//                roi.setPosition(1,1,1);
//                roi.setZeroTransparent(false);
//                roi.setOpacity(1);
//                Overlay ovl = new Overlay(roi);
//                ovl.drawLabels(true); // allow labels
//                ovl.drawNames(true);
//                imp.getStack().getProcessor(1).setOverlay(ovl);
//                imp.getOverlay();

//                BufferedImage buff = (BufferedImage) im;
//                Graphics g = jPanel1.getGraphics();
//                g.drawImage(buff, 0, 0, getWidth(), getHeight(), 0, 0, buff.getWidth(), buff.getHeight(), null);
                //record the frame
                videoWriter.write(dst);
//                imp.show();
//                videoWriter.write(frame);
//                TimeUnit.SECONDS.sleep(1);
//                vidpanel.setIcon(new ImageIcon( imp.getBufferedImage()));
//                vidpanel.repaint();
                System.out.println("DSFSDF");

            }
        videoCapture.release();
        videoWriter.release();
        }
//    }

}
