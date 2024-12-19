package com.example.myapplication.View;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.face.LBPHFaceRecognizer;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class CameraCheckin extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {
    private CameraBridgeViewBase javaCameraView;
    private boolean isUsingFrontCamera = false; // Trạng thái camera
    private CascadeClassifier faceDetector;
    private LBPHFaceRecognizer faceRecognizer;
    private static final double CONFIDENCE_THRESHOLD = 0.7; // Ngưỡng confidence
    private ImageView btnReturn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_camera_checkin);

        // Set window insets for edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize OpenCV
        if (!OpenCVLoader.initDebug()) {
            Log.e("OpenCV", "Unable to load OpenCV");
        } else {
            Log.d("OpenCV", "OpenCV loaded successfully");
            initializeFaceDetector();
            initializeFaceRecognizer();
        }

        // Initialize JavaCameraView
        javaCameraView = findViewById(R.id.CameraView);
        btnReturn = findViewById(R.id.btnReturn);
        javaCameraView.setVisibility(CameraBridgeViewBase.VISIBLE);
        javaCameraView.setCvCameraViewListener(this);
        javaCameraView.setCameraIndex(CameraBridgeViewBase.CAMERA_ID_BACK); // Mặc định camera sau
        javaCameraView.enableView();

        btnReturn.setOnClickListener(v ->{
            finish();
        });

        // Switch camera button
        ImageView btnSwitchCamera = findViewById(R.id.btnSwitchCamera);
        btnSwitchCamera.setOnClickListener(v -> switchCamera());
    }

    private void initializeFaceDetector() {
        try {
            InputStream is = getResources().openRawResource(R.raw.haarcascade_frontalface_default);
            File cascadeDir = getDir("cascade", MODE_PRIVATE);
            File cascadeFile = new File(cascadeDir, "haarcascade_frontalface_default.xml");

            FileOutputStream fos = new FileOutputStream(cascadeFile);
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
            is.close();
            fos.close();

            faceDetector = new CascadeClassifier(cascadeFile.getAbsolutePath());
            if (faceDetector.empty()) {
                Log.e("OpenCV", "Failed to load Haar Cascade");
                faceDetector = null;
            } else {
                Log.d("OpenCV", "Haar Cascade loaded successfully");
            }
            cascadeFile.delete();
            cascadeDir.delete();

        } catch (Exception e) {
            Log.e("OpenCV", "Error loading Haar Cascade", e);
        }
    }

    private void initializeFaceRecognizer() {
        try {
            faceRecognizer = LBPHFaceRecognizer.create();
            InputStream is = getResources().openRawResource(R.raw.classifier);
            File modelFile = new File(getFilesDir(), "classifier.xml");

            FileOutputStream fos = new FileOutputStream(modelFile);
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
            is.close();
            fos.close();

            faceRecognizer.read(modelFile.getAbsolutePath());
            Log.d("OpenCV", "LBPHFaceRecognizer model loaded successfully");
            modelFile.delete();
        } catch (Exception e) {
            Log.e("OpenCV", "Error loading LBPHFaceRecognizer model", e);
        }
    }

    private void switchCamera() {
        if (javaCameraView != null) {
            javaCameraView.disableView();
            isUsingFrontCamera = !isUsingFrontCamera;
            int cameraIndex = isUsingFrontCamera
                    ? CameraBridgeViewBase.CAMERA_ID_FRONT
                    : CameraBridgeViewBase.CAMERA_ID_BACK;
            javaCameraView.setCameraIndex(cameraIndex);
            javaCameraView.enableView();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (javaCameraView != null) {
            javaCameraView.disableView();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (javaCameraView != null) {
            javaCameraView.disableView();
        }
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        Log.d("OpenCV", "Camera view started with resolution: " + width + "x" + height);
    }

    @Override
    public void onCameraViewStopped() {
        Log.d("OpenCV", "Camera view stopped");
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        Mat frame = inputFrame.rgba();
        Mat rotatedFrame = new Mat();
        Core.rotate(frame, rotatedFrame, Core.ROTATE_90_CLOCKWISE);

        if (isUsingFrontCamera) {
            Core.flip(rotatedFrame, rotatedFrame, -1);
        }

        if (faceDetector != null && faceRecognizer != null) {
            MatOfRect faces = new MatOfRect();
            faceDetector.detectMultiScale(rotatedFrame, faces, 1.1, 3, 0, new org.opencv.core.Size(50, 50), new org.opencv.core.Size());

            for (Rect rect : faces.toArray()) {
                Mat face = rotatedFrame.submat(rect);
                Mat grayFace = new Mat();
                Imgproc.cvtColor(face, grayFace, Imgproc.COLOR_RGBA2GRAY);

                int[] label = new int[1];
                double[] confidence = new double[1];
                faceRecognizer.predict(grayFace, label, confidence);

                String name = "Unknown";
                if (confidence[0] < CONFIDENCE_THRESHOLD * 100) { // Chuyển threshold thành 0-100
                    if (label[0] == 1) {
                        name = "Ali";
                    } else if (label[0] == 3) {
                        name = "Nghi";
                    }
                } else {
                    Log.d("OpenCV", "Confidence too high: " + confidence[0]);
                }

                Imgproc.rectangle(rotatedFrame, rect.tl(), rect.br(), new Scalar(0, 255, 0), 3);
                Imgproc.putText(rotatedFrame, name, rect.tl(), 0, 1.0, new Scalar(255, 255, 255), 2);
            }
        }
        return rotatedFrame;
    }
}
