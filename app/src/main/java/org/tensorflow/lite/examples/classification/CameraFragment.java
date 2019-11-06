package org.tensorflow.lite.examples.classification;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.tensorflow.lite.examples.classification.tflite.Classifier.Recognition;

/**
 * A placeholder fragment containing a simple view.
 */
public class CameraFragment extends Fragment {

    TextView object;
    TextView confidence;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_camera, container, false);
        object = root.findViewById(R.id.detected_item);
        confidence = root.findViewById(R.id.detected_item_value);
        return root;
    }

    void onItemDetected(Recognition recognition) {
        if (recognition != null) {
            if (recognition.getTitle() != null) {
                object.setText(recognition.getTitle());
            }
            if (recognition.getConfidence() != null)
                confidence.setText(
                        String.format("%.2f", (100 * recognition.getConfidence())) + "%");
        }
    }
}