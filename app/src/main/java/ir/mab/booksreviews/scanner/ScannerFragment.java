package ir.mab.booksreviews.scanner;


import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;

import java.util.Objects;

import ir.mab.booksreviews.R;
import ir.mab.booksreviews.utils.Preview;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScannerFragment extends Fragment implements ScannerContract.View{
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    private ScannerContract.Presenter mPresenter;
    private ScannerPresenter presenter;
    private Camera camera;
    private Preview preview;
    private FrameLayout livePreview;

    public ScannerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scanner,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new ScannerPresenter(this);
        livePreview = view.findViewById(R.id.livePreview);
    }

    @Override
    public boolean hasPermissions() {
        return Objects.requireNonNull(getActivity()).checkSelfPermission(Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void showPermissionDialog() {
        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
    }

    @Override
    public void loadPreview() {
        camera = presenter.getCameraInstance();
        preview = new Preview(getContext(),camera);
        livePreview.addView(preview);

        camera.setPreviewCallback(new Camera.PreviewCallback() {
            @Override
            public void onPreviewFrame(byte[] data, Camera camera) {
                FirebaseVisionImageMetadata metadata = presenter.getFirebaseVisionImageMetadata(480,360,FirebaseVisionImageMetadata.IMAGE_FORMAT_NV21);
                FirebaseVisionImage image = FirebaseVisionImage.fromByteArray(data,metadata);
                mPresenter.detectBarcode(image);
            }
        });
    }

    @Override
    public void loadNoCameraAvailable() {
        Toast.makeText(getContext(),"onLoadNoCameraAvailable",Toast.LENGTH_LONG).show();
    }

    @Override
    public void setPresenter(ScannerContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA){
            if (grantResults.length > 0
            && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                mPresenter.getCameraInstance();
            }

            else {
                Toast.makeText(getContext(),"onLoadNoPermissionsView",Toast.LENGTH_LONG).show();
            }
        }

    }
}


