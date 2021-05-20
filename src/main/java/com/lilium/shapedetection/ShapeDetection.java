package com.lilium.shapedetection;

import com.lilium.shapedetection.util.ShapeDetectionUtil;
import nu.pattern.OpenCV;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;

public class ShapeDetection {

    public static void main (final String args[]) {
        // Load OpenCV
        OpenCV.loadShared();

        // Create panels
        final JPanel cameraFeed = new JPanel();
        final JPanel processedFeed = new JPanel();
        ShapeDetectionUtil.createJFrame(cameraFeed, processedFeed);

        // Create video capture object (index 0 is default camera)
        final VideoCapture camera = new VideoCapture(0);

        // Start shape detection
        ShapeDetection.startShapeDetection(cameraFeed, processedFeed, camera).run();
    }

    private static Runnable startShapeDetection(final JPanel cameraFeed,
                                                final JPanel processedFeed,
                                                final VideoCapture camera) {
        return () -> {
            final Mat frame = new Mat();

            while (true) {
                // Read frame from camera
                camera.read(frame);

                // Process frame
                final Mat processed = ShapeDetectionUtil.processImage(frame);

                // Mark outer contour
                ShapeDetectionUtil.markOuterContour(processed, frame);

                // Draw current frame
                ShapeDetectionUtil.drawImage(frame, cameraFeed);

                // Draw current processed image (for debugging)
                ShapeDetectionUtil.drawImage(processed, processedFeed);
            }
        };
    }
}
